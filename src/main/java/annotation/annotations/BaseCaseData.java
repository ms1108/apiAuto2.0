package annotation.annotations;


import java.lang.annotation.*;

/**
 * 修饰的方法为基础数据，该数据在每个注解测试时都会生成一份作为基础数据进行修改
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
@Inherited
public @interface BaseCaseData {
    String group() default "0";
}
