package annotation.annotations;

import java.lang.annotation.*;
/**
 * 注册接口，用于前端获取该接口的基础描述
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
@Inherited
public @interface Registry {
    //作者
    String author();

    //归属组，划分模块，中文名称
    String groupName();

    //是否可用
    boolean isValid() default true;

    //接口描述通过serviceMap获取

}
