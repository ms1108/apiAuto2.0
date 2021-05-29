package annotation.annotations;

import annotation.IAnnotationTestMethod;
import annotation.impl.AutoTestDefault;
import annotation.impl.BlankDefault;

import java.lang.annotation.*;

/**
 * 自动将对象转入apiTest中进行发送
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
@Inherited
public @interface AutoTest {
    String des() default "";

    boolean isOpenAssert() default true;

    int sleep() default 0;

    Class<? extends IAnnotationTestMethod> testMethod() default AutoTestDefault.class;

}
