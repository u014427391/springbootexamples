package com.example.scala.model

import com.baomidou.mybatisplus.annotation.{TableId, TableName}
import javax.validation.constraints.{Email, NotBlank, NotEmpty}
import lombok.{Data, ToString}

import scala.beans.BeanProperty

@Data
@TableName(value = "user")
@ToString
class User {

  @TableId
  @BeanProperty
  var id : Integer = _

  @BeanProperty
  @NotBlank(message = "用户名必须填！")
  var name : String = _

  @BeanProperty
  var age : Integer = _

  @BeanProperty
  @Email(message = "邮箱格式不对")
  var email : String = _

}
