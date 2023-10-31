package com.example.validated.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddressDto {

    @NotBlank(message = "邮政编码必须传!")
    private String postalCode;

    @NotBlank(message = "地址描述必须传!")
    private String addressDesc;

}
