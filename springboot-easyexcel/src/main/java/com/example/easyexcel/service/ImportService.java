package com.example.easyexcel.service;

import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.alibaba.excel.metadata.CellExtra;
import com.example.easyexcel.core.excel.ExcelMergeHelper;
import com.example.easyexcel.core.excel.UserEasyExcelListener;
import com.example.easyexcel.model.dto.UserExcelDto;
import com.example.easyexcel.model.vo.ExcelValidateError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

    @Autowired
    private Validator validator;

    public List<ExcelValidateError> importExcel(MultipartFile file) throws IOException {
        List<UserExcelDto> excelDtoList = obtainExcelData(file);
        final List<ExcelValidateError> errs = doValidateExcel(excelDtoList);
        if (errs.isEmpty()) {
            System.out.println("开始保存数据!");
        }
        return errs;
    }

    private List<ExcelValidateError> doValidateExcel(final List<UserExcelDto> excelDtoList) {
        final List<ExcelValidateError> errs = new ArrayList<ExcelValidateError>();
        if (!excelDtoList.isEmpty()) {
            excelDtoList.stream().forEach(dto->{
                Set<ConstraintViolation<UserExcelDto>> violations = validator.validate(dto);
                List<String> errMsgs = new ArrayList<>();
                if (!violations.isEmpty()) {
                    errMsgs = violations.stream().map(e -> e.getMessage()).collect(Collectors.toList());
                }
                if (!errMsgs.isEmpty()) {
                    errs.add(ExcelValidateError
                            .builder()
                            .index(dto.getSeq())
                            .errMsgs(errMsgs)
                            .build());
                }
            });
//            errs.stream().collect(
//                    Collectors.collectingAndThen(
//                            Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(ExcelValidateError::getIndex))), ArrayList::new)
//            );
        }
        return errs.stream().filter(distinctByKey(ExcelValidateError::getIndex)).collect(Collectors.toList());
    }

    private List<UserExcelDto> obtainExcelData(MultipartFile file) throws IOException {
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
        System.out.println(JSONUtil.toJsonPrettyStr(easyExcelListener.getData()));
        List<CellExtra> extraMergeInfoList = easyExcelListener.getExtraMergeInfoList();
        List<UserExcelDto> data = new ExcelMergeHelper().explainMergeData(easyExcelListener.getData(), extraMergeInfoList, headRowNumber);

        System.out.println(JSONUtil.toJsonPrettyStr(data));

        return data;
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
