package annotation.annotations;

import annotation.IAnnotationTestMethod;
import annotation.impl.EnumStringDefault;
import config.asserts.AssertMethod;
import config.asserts.SuccessAssertDefault;

import java.lang.annotation.*;
/**
 * 该字段支持的枚举，每个枚举值都会作为值发送一遍
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
@Inherited
public @interface EnumString {
    String[] value() default "";

    Class<? extends AssertMethod> asserts() default SuccessAssertDefault.class;

    String resetAssert() default "";

    String[] group() default "0";//当输入0时则不进行分组考虑

    Class<? extends IAnnotationTestMethod> testMethod() default EnumStringDefault.class;
}
