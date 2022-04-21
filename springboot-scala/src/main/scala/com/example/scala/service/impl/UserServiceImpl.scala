package com.example.scala.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.example.scala.dao.UserDao
import com.example.scala.model.User
import com.example.scala.service.IUserService
import org.springframework.stereotype.Service

@Service
class UserServiceImpl extends ServiceImpl[UserDao , User] with IUserService{

}
