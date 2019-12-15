package com.example.springboot.mybatis.mapper;

import com.example.springboot.mybatis.bean.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <pre>
 *
 * </pre>
 *
 * @author nicky
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2019年12月15日  修改内容:
 * </pre>
 */
@Mapper
public interface UserDao {

    User getApiUserById(Integer id);

    void insertApiUser(User user);
}
