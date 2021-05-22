package annotation.annotations;

import annotation.IAnnotationTestMethod;
import annotation.impl.RangeDefaultImpl;
import config.asserts.AssertMethod;
import config.asserts.FailAssetDefault;
import config.asserts.SuccessAssertDefault;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
@Inherited
public @interface Range {
    String maxNum() default "1";

    String minNum() default "0";

    //浮动值
    String floatValue() default "1";

    //正无穷
    boolean maxInfinite() default false;

    //负无穷
    boolean minInfinite() default false;

    Class<? extends AssertMethod> assertSuccess() default SuccessAssertDefault.class;

    Class<? extends AssertMethod> assertFail() default FailAssetDefault.class;

    String resetAssert() default "";

    String[] group() default "0";//当输入0时则不进行分组考虑

    //自定义注解中的测试流程，示例
    Class<? extends IAnnotationTestMethod> testMethod() default RangeDefaultImpl.class;


}
