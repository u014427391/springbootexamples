package com.example.springboot.hateoas.repository;

import com.example.springboot.hateoas.model.SysUserInfoModel;
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
public interface SysUserInfoModelRepository extends JpaRepository<SysUserInfoModel,Long>{

    @Query(value = "select u from SysUserInfoModel u where u.userId=:userId")
    SysUserInfoModel findByUserId(@Param("userId") long userId);
}
