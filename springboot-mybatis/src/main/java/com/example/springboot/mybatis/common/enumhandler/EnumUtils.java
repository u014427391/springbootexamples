package com.example.springboot.mybatis.common.enumhandler;


public class EnumUtils {

    public static <T extends Enum<?> & IEnum> T codeOf(Class<T> enumCls , String code) {
        T[] enumConstants = enumCls.getEnumConstants();
        for (T t : enumConstants) {
            if (t.getCode().equals(code)) return t;
        }
        return null;
    }

    public static <T extends Enum<?> & IEnum> T nameOf(Class<T> enumCls , String name) {
        T[] enumConstants = enumCls.getEnumConstants();
        for (T t : enumConstants) {
            if (t.getName().equals(name)) return t;
        }
        return null;
    }

}
