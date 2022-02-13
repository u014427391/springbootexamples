package com.example.easyexcel.service;

import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.alibaba.excel.metadata.CellExtra;
import com.example.easyexcel.core.excel.ExcelMergeHelper;
import com.example.easyexcel.core.excel.UserEasyExcelListener;
import com.example.easyexcel.model.dto.UserExcelDto;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <pre>
 *      文件导入
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2022/02/10 10:28  修改内容:
 * </pre>
 */
@Service
public class ImportService {

    public void doImportExcel(@RequestPart("file") MultipartFile file) throws IOException {
        Integer sheetNo = 0;
        Integer headRowNumber = 1;
        UserEasyExcelListener easyExcelListener = new UserEasyExcelListener(headRowNumber);
//        ExcelReaderBuilder read = EasyExcel.read(
//                file.getInputStream(),
//                UserExcelDto.class,
//                easyExcelListener);
//        read.doReadAll();

        EasyExcel.read(file.getInputStream(), UserExcelDto.class, easyExcelListener)
                .extraRead(CellExtraTypeEnum.MERGE)
                .sheet(sheetNo)
                .headRowNumber(headRowNumber)
                .doRead();
        List<CellExtra> extraMergeInfoList = easyExcelListener.getExtraMergeInfoList();
        List<UserExcelDto> data = new ExcelMergeHelper().explainMergeData(easyExcelListener.getData(), extraMergeInfoList, headRowNumber);
        System.out.println(JSONUtil.toJsonPrettyStr(data));
    }
}
