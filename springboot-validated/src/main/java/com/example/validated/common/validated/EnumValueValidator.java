package com.example.validated.common.validated;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;


@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValueValidator.Validator.class)
public @interface EnumValueValidator {

    Logger log = LoggerFactory.getLogger(EnumValueValidator.class);

    String message() default "参数有误";

    Class<? extends Enum<?>> enumClass();

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String enumMethod();
    
    class Validator implements ConstraintValidator<EnumValueValidator , Object> {
        private Class<? extends Enum<?>> enumClass;
        private String enumMethod;

        @Override
        public void initialize(EnumValueValidator constraintAnnotation) {
            enumMethod = constraintAnnotation.enumMethod();
            enumClass = constraintAnnotation.enumClass();
        }
        @Override
        public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
          // 值没传的情况，直接返回true
            if (StringUtils.isEmpty(o)) return Boolean.TRUE;
            if (enumClass == null || StringUtils.isEmpty(enumMethod)) return Boolean.TRUE;
            Class<?> vclass = o.getClass();
            try {
            // 反射机制获取具体的校验方法
                Method method = enumClass.getMethod(enumMethod,vclass);
                if (!Boolean.TYPE.equals(method.getReturnType()) &&
                        !Boolean.class.equals(method.getReturnType())) {
                    throw new RuntimeException("校验方法不是布尔类型!");
                }
                if (!Modifier.isStatic(method.getModifiers())) {
                    throw new RuntimeException("校验方法不是静态方法!");
                }
                method.setAccessible(true);
                // 调用具体的方法
                Boolean res = (Boolean) method.invoke(null,o);
                return res != null ? res : false;
            } catch (NoSuchMethodException e) {
                log.error("NoSuchMethodException:{}" ,e);
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                log.error("IllegalAccessException:{}" ,e);
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                log.error("InvocationTargetException:{}" ,e);
                throw new RuntimeException(e);
            }
        }
    }

}

