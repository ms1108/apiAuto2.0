package annotation.annotations;

import annotation.IAnnotationTestMethod;
import annotation.impl.MultiRequestDefault;
import config.invokerequest.InvokeRequest;
import config.invokerequest.MultiThreadInvokeRequest;

import java.lang.annotation.*;
/**
 * 幂等性测试
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
@Inherited
public @interface MultiRequest {
    int multiThreadNum() default 2;

    Class<? extends InvokeRequest> iRequest() default MultiThreadInvokeRequest.class;

    String des() default "";

    boolean isOpenAssert() default true;

    int sleep() default 0;

    String resetAssert() default "";

    Class<? extends IAnnotationTestMethod> testMethod() default MultiRequestDefault.class;

}
