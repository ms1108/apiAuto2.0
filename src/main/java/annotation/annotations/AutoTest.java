package annotation.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
@Inherited
public @interface AutoTest {
    String des() default "";

    boolean isOpenAssert() default true;

    int sleep() default 0;
}
