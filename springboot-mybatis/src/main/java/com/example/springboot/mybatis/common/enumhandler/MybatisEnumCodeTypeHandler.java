package com.example.springboot.mybatis.common.enumhandler;


import cn.hutool.core.lang.Assert;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MybatisEnumCodeTypeHandler<E extends Enum<E> & IEnum> extends BaseTypeHandler<E>{

    private final Class<E> type;

    public MybatisEnumCodeTypeHandler(Class<E> type) {
        Assert.notNull(type , "type argument can not be null");
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, E e, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i , e.getCode());
    }

    @Override
    public E getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String code = resultSet.getString(s);
        return resultSet.wasNull() ? null : EnumUtils.codeOf(this.type , code);
    }

    @Override
    public E getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String code = resultSet.getString(i);
        return resultSet.wasNull() ? null : EnumUtils.codeOf(this.type , code);
    }

    @Override
    public E getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String code = callableStatement.getString(i);
        return callableStatement.wasNull() ? null : EnumUtils.codeOf(this.type , code);
    }
}
