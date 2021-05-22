package annotation.annotations;

import annotation.IAnnotationTestMethod;
import annotation.impl.NotEmptyDefaultImpl;
import config.asserts.AssertMethod;
import config.asserts.FailAssetDefault;

import java.lang.annotation.*;

/**
 * 空字符串校验
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
@Inherited
public @interface NotEmpty {

    Class<? extends AssertMethod> asserts() default FailAssetDefault.class;

    String resetAssert() default "";

    String[] group() default "0";//当输入0时则不进行分组考虑

    Class<? extends IAnnotationTestMethod> testMethod() default NotEmptyDefaultImpl.class;

}
