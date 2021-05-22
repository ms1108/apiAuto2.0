package annotation.annotations;

import config.requestMethod.IRequestMethod;
import config.requestMethod.MultiThreadRequestMethod;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
@Inherited

public @interface MultiRequest {
    int multiThreadNum() default 2;

    Class<? extends IRequestMethod> iRequest() default MultiThreadRequestMethod.class;

    String des() default "";

    boolean isOpenAssert() default true;

    int sleep() default 0;

    String resetAssert() default "";

}
