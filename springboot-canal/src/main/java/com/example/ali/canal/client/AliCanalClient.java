package com.example.ali.canal.client;


import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.common.utils.AddressUtils;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.example.ebus.event.ShopOrderEvent;
import com.example.ebus.publisher.MyEventPublisher;
import com.google.protobuf.InvalidProtocolBufferException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Optional;

public class AliCanalClient implements ApplicationRunner {

    @Autowired
    private MyEventPublisher eventPublisher;

    private static final int batchSize = 1;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 创建canal连接器
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress(AddressUtils.getHostIp(),
                11111), "example", "canal", "canal");
        try {
            // 连接canal服务端
            connector.connect();
            // 只订阅*order的表，订阅所有表：".*\\..*"
            connector.subscribe(".*order.*");
            // 回滚到未进行ack确认的地方，下次fetch的时候，可以从最后一个没有ack的地方开始拿
            connector.rollback();
            while (true) {
                // 获取指定数量的数据
                Message message = connector.getWithoutAck(batchSize);
                // 获取批量ID
                long batchId = message.getId();
                // 获取批量的数量
                int size = message.getEntries().size();
                if (batchId == -1 || size == 0) {
                    try {
                        //如果没有数据,线程休眠2秒
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                    }
                } else {
                    //如果有数据,处理数据
                    handle(message.getEntries());
                }
                // ack确认batchId。小于等于这个batchId的消息都会被确认
                connector.ack(batchId);
            }
        } finally {
            // 释放连接
            connector.disconnect();
        }
    }

    private void handle(List<CanalEntry.Entry> entries) {
        entries.stream().forEach(entry ->{
            if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN || entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                // 开启/关闭事务的实体类型，跳过
                return;
            }
            if (entry.getEntryType() == CanalEntry.EntryType.ROWDATA) {
                try {
                    // 获取rowChange
                    CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
                    // 针对更新操作的监听
                    if (rowChange.getEventType() == CanalEntry.EventType.UPDATE) {
                        // 遍历rowChange里的所有的行数据
                        rowChange.getRowDatasList().stream().forEach((row->{
                            row.getAfterColumnsList().stream().forEach(column->{
                                // 对具体字段进行校验，处理业务
                                if ("isOk".equals(column.getName())&&
                                    column.getUpdated() && "1".equals(column.getValue())) {
                                    publishEvent(row);
                                }
                            });
                        }));
                    }
                }catch (InvalidProtocolBufferException e) {
                    throw new RuntimeException("解释Binlog日志出现异常:" + entry, e);
                }
            }
        });
    }

    private void publishEvent(CanalEntry.RowData rowData) {
        ShopOrderEvent orderEvent = new ShopOrderEvent();
        List<CanalEntry.Column> columns = rowData.getAfterColumnsList();
        columns.forEach((column -> {
            String name = column.getName();
            String value = column.getValue();
            Optional.ofNullable(value).ifPresent((v)->{
                if ("orderCode".equals(name)) {
                    orderEvent.setOrderCode(v);
                }
                if ("productName".equals(name)) {
                    orderEvent.setProductName(v);
                }
                if ("price".equals(name)) {
                    orderEvent.setPrice(Float.valueOf(v));
                }
                if ("productDesc".equals(name)) {
                    orderEvent.setProductDesc(v);
                }
                if ("isOk".equals(name)) {
                    orderEvent.setIsOk(Integer.valueOf(v));
                }
            });
        }));
        eventPublisher.publishEvent(orderEvent);
    }

}
