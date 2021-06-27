package annotation.annotations;

import annotation.IAnnotationTestMethod;
import annotation.impl.SearchDefault;
import base.BaseCase;

import java.lang.annotation.*;
/**
 * 搜索测试
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
@Inherited
public @interface Search {

    int expectListLen() default 1;//搜索后列表只有一条数据

    //列表根路径可以根据项目写默认的
    String listRootPath() default "data.rows";
    //searchValuePath 获取addDataBaseCase 请求中的某个数据,如：arg.name
    String searchValuePath() default "";
    //获取列表返回总数的路径,如：data.total
    String totalPath() default "data.total";

    Class<? extends BaseCase> addDataBaseCase() default BaseCase.class;

    String resetAssert() default "";

    String[] group() default "0";//当输入0时则不进行分组考虑

    Class<? extends IAnnotationTestMethod> testMethod() default SearchDefault.class;

}
