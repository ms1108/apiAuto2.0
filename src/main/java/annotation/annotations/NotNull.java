package annotation.annotations;

import annotation.IAnnotationTestMethod;
import annotation.impl.NotNullDefault;
import config.asserts.AssertMethod;
import config.asserts.FailAssetDefault;

import java.lang.annotation.*;
/**
 * 必填测试
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
@Inherited
public @interface NotNull {

    Class<? extends AssertMethod> asserts() default FailAssetDefault.class;

    String resetAssert() default "";

    String[] group() default "0";//当输入0时则不进行分组考虑

    Class<? extends IAnnotationTestMethod> testMethod() default NotNullDefault.class;

}
