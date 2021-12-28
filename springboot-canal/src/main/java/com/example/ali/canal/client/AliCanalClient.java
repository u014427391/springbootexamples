package com.example.ali.canal.client;


import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.common.utils.AddressUtils;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.example.ebus.publisher.MyEventPublisher;
import com.google.protobuf.InvalidProtocolBufferException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.InetSocketAddress;
import java.util.List;

public class AliCanalClient implements InitializingBean {

    @Autowired
    private MyEventPublisher eventPublisher;

    @Override
    public void afterPropertiesSet() throws Exception {
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress(AddressUtils.getHostIp(),
                11111), "example", "", "");
        try {
            connector.connect();
            connector.subscribe(".*\\..*");
            connector.rollback();
            while (true) {
                Message message = connector.getWithoutAck(1);
                long batchId = message.getId();
                int size = message.getEntries().size();
                if (batchId == -1 || size == 0) {
                    try {
                        //如果没有数据,线程休眠2秒
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    //如果有数据,处理数据
                    handle(message.getEntries());
                }
                connector.ack(batchId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connector.disconnect();
        }
    }

    private void handle(List<CanalEntry.Entry> entries) {
        entries.stream().forEach(entry ->{
            if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN || entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                return;
            }
            if (entry.getEntryType() == CanalEntry.EntryType.ROWDATA) {
                try {
                    CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
                    if (rowChange.getEventType() == CanalEntry.EventType.UPDATE) {
                        rowChange.getRowDatasList().stream().forEach((row->{
                            row.getAfterColumnsList().stream().forEach(column->{
                                if ("is".equals(column.getName())&&
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

    }

}
