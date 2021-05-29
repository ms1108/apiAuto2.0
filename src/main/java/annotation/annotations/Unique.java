package annotation.annotations;

import annotation.IAnnotationTestMethod;
import annotation.impl.UniqueDefault;
import config.asserts.AssertMethod;
import config.asserts.FailAssetDefault;
import config.asserts.SuccessAssertDefault;

import java.lang.annotation.*;
/**
 * 判重
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
@Inherited
public @interface Unique {

    Class<? extends AssertMethod> assertSuccess() default SuccessAssertDefault.class;

    Class<? extends AssertMethod> assertFail() default FailAssetDefault.class;

    String resetAssert() default "";

    String[] group() default "0";//当输入0时则不进行分组考虑//若子类的BaseCaseData没有这个1号组则子类不执行该字段
    Class<? extends IAnnotationTestMethod> testMethod() default UniqueDefault.class;

}
