package annotation.annotations;

import annotation.IAnnotationTestMethod;
import annotation.impl.ChineseDefaultImpl;
import annotation.impl.EnumIntImpl;
import config.asserts.AssertMethod;
import config.asserts.SuccessAssertDefault;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
@Inherited
public @interface EnumInt {
    int[] value() default {1,2};

    Class<? extends AssertMethod> asserts() default SuccessAssertDefault.class;

    String resetAssert() default "";

    String[] group() default "0";//当输入0时则不进行分组考虑

    Class<? extends IAnnotationTestMethod> testMethod() default EnumIntImpl.class;
}
