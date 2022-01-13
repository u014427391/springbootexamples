package com.example.validated.model.dto;


import com.example.validated.common.validated.EnumValueValidator;
import com.example.validated.interfaces.BasicInfo;
import com.example.validated.interfaces.UserInfo;
import com.example.validated.model.enumCls.SexTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserGroupDto implements Serializable {

    private Long id;

    @NotBlank( message = "用户名name必须传!" ,groups = {BasicInfo.class , UserInfo.class})
    private String name;

    @NotBlank(message = "密码password必须传!")
    @Size(min = 6 ,message = "密码password必须6位以上" ,groups = {BasicInfo.class , UserInfo.class})
    private String password;

    private Integer age;

    @Email( message = "email invalid!",groups = {UserInfo.class})
    @NotBlank(message = "邮箱email必须传!" ,groups = {UserInfo.class})
    private String email;

    @NotBlank( message = "手机号contactNumber必须传" ,groups = {UserInfo.class})
    @Pattern(regexp = "1[3|4|5|7|8][0-9]\\d{8}" ,message = "手机号格式不对",groups = {UserInfo.class})
    private String contactNumber;

    @EnumValueValidator(enumClass = SexTypeEnum.class , enumMethod = "isValueValid" ,message = "性别类型校验有误!",groups = {UserInfo.class})
    private Integer sex;

}
