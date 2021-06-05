package annotation.annotations;

import java.lang.annotation.*;

//方法(用例)的名称和描述
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
@Inherited
public @interface Description {
    String name() default "";

    String des() default "";
}
