package com.example.springboot.hateoas.core.resource;

import org.springframework.hateoas.Identifiable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

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
@MappedSuperclass
public class CustomIdentifiable implements Identifiable<Long>{

    @Id
    @GeneratedValue
    private Long id;

    @Override
    public Long getId() {
        return id;
    }


}