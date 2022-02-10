package com.example.easyexcel.core.excel;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.example.easyexcel.model.User;
import com.example.easyexcel.model.dto.UserExcelDto;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
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
public class UserEasyExcelListener extends AnalysisEventListener<UserExcelDto> {

    @Getter
    private final String batchId ;

    private final Date date;

    private List<UserExcelDto> datas;

    public UserEasyExcelListener() {
        this.batchId = IdUtil.simpleUUID();
        this.date = DateTime.now();
        datas = new ArrayList<>();
    }

    @Override
    public void invoke(UserExcelDto data, AnalysisContext context) {
        int index = context.readRowHolder().getRowIndex();
        if (index != 1) {
            data.setBatchId(batchId);
            data.setDate(date);
            datas.add(data);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }

}
