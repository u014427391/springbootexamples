package com.example.easyexcel.core.excel;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellExtra;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2022/02/10 10:31  修改内容:
 * </pre>
 */
@Slf4j
public class UserEasyExcelListener<T> extends AnalysisEventListener<T> {

    private List<T> datas;

    private Integer rowIndex;

    private List<CellExtra> extraMergeInfoList;

    public UserEasyExcelListener(Integer rowIndex) {
        this.rowIndex = rowIndex;
        datas = new ArrayList<>();
        extraMergeInfoList = new ArrayList<>();
    }

    @Override
    public void invoke(T data, AnalysisContext context) {
        log.info("解析到一条数据: ", JSONUtil.toJsonPrettyStr(data));
        context.readWorkbookHolder().setIgnoreEmptyRow(false);
        ReflectUtil.invoke(data, "setIndex", StrUtil.toString(context.readRowHolder().getRowIndex()));
        datas.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("所有数据解析完成！");
    }

    @Override
    public void extra(CellExtra extra, AnalysisContext context) {
        log.info("读取到了一条额外信息:{}", JSONUtil.toJsonPrettyStr(extra));
        switch (extra.getType()) {
            case MERGE:
                if (extra.getRowIndex() >= rowIndex) {
                    extraMergeInfoList.add(extra);
                }
                break;
            default:
        }
    }

    public List<T> getData() {
        return datas;
    }


    public List<CellExtra> getExtraMergeInfoList() {
        return extraMergeInfoList;
    }


}
