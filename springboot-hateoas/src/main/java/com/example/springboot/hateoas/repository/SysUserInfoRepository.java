package com.example.springboot.hateoas.repository;

import com.example.springboot.hateoas.bean.SysUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * <pre>
 *
 * </pre>
 *
 * @author nicky
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2019年10月26日  修改内容:
 * </pre>
 */
@Repository
public interface SysUserInfoRepository extends JpaRepository<SysUserInfo,Long>{

    @Query(value = "select u from SysUserInfo u where u.userId=:userId")
    SysUserInfo findByUserId(@Param("userId") long userId);
}
