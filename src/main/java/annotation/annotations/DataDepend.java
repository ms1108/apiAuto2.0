package annotation.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
@Inherited
public @interface DataDepend {
    //该信息定义了是否在每个字段注解测试时都执行一遍DataDepend标记的方法
    boolean value() default false;
}
