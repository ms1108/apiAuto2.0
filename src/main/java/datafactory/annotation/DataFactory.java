package datafactory.annotation;

import base.BaseCase;
import component.loginTest.service_constant.LoginService;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
@Inherited
public @interface DataFactory {
    String des() default "";
    //创建数据后数据所在的列表
    Class<? extends BaseCase> listApi() default BaseCase.class;
}
