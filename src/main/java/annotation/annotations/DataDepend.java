package annotation.annotations;

import java.lang.annotation.*;
/**
 * 每个接口所要依赖的数据
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
@Inherited
public @interface DataDepend {
    //该信息定义了是否在每个字段注解测试时都执行一遍DataDepend标记的方法
    boolean isAlwaysExecute() default false;
}
