package com.example.mybatisplus.common.handlers;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * <pre>
 *      Mybatis Plus 自动填充字段数据
 * </pre>
 *
 * <pre>
 * @author nicky
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2023/11/01 17:24  修改内容:
 * </pre>
 */
@Component
@Slf4j
public class FillMetaObjectHandler implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("insertFill");
        this.setFieldValByName("createTime" , LocalDateTime.now(), metaObject);
        this.setFieldValByName("modifyTime" , LocalDateTime.now() , metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("updateFill");
        this.setFieldValByName("modifyTime" , LocalDateTime.now(), metaObject);
    }
}
