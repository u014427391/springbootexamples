package com.example.scala.controller

import com.example.scala.model.User
import com.example.scala.service.IUserService
import javax.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation._

@RestController
class UserController @Autowired()(var iUserSerive: IUserService){

  @GetMapping(value = {
    Array("/users/{id}")
  })
  def getUser(@PathVariable(value = "id") id: Integer): User  = {
    iUserSerive.getById(id)
  }

  @PostMapping(value = {
    Array("/users")
  })
  def save(@Valid @RequestBody user: User) = {
    iUserSerive.save(user);
  }

}
