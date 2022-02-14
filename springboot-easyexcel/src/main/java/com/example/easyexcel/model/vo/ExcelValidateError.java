package com.example.easyexcel.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NotBlank
@ToString
public class ExcelValidateError {

    private String index;

    private List<String> errMsgs;

}
