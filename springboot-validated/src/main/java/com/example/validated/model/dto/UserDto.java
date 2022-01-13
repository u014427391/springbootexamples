package com.example.validated.model.dto;


import com.example.validated.common.validated.EnumValueValidator;
import com.example.validated.model.enumCls.SexTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto implements Serializable {

    private Long id;

    @NotBlank( message = "用户名name必须传!")
    private String name;

    private Integer age;

    @Email( message = "email invalid!")
    @NotBlank(message = "邮箱email必须传!")
    private String email;

    @NotBlank( message = "手机号contactNumber必须传")
    @Pattern(regexp = "1[3|4|5|7|8][0-9]\\d{8}" ,message = "手机号格式不对")
    private String contactNumber;

    @NotBlank(message = "密码password必须传!")
    @Size(min = 6 ,message = "密码password必须6位以上")
    private String password;

    @EnumValueValidator(enumClass = SexTypeEnum.class , enumMethod = "isValueValid" ,message = "性别类型校验有误!")
    private Integer sex;

    @NotNull(message = "地址必须填!" )
    @Size(min = 1 , message = "地址至少要有一个")
    @Valid
    private AddressDto address;

}
