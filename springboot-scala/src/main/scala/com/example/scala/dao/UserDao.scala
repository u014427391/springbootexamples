package com.example.scala.dao

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.example.scala.model.User
import org.apache.ibatis.annotations.Mapper

@Mapper
trait UserDao extends BaseMapper[User]{

}
