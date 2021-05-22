package annotation.annotations;

import annotation.IAnnotationTestMethod;
import annotation.impl.SearchDefaultImpl;
import base.BaseCase;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
@Inherited
public @interface Search {

    int expectListLen() default 1;//搜索后列表只有一条数据

    //列表根路径可以根据项目写默认的
    String listRootPath() default "data.rows";
    //searchValuePath 获取addDataBaseCase 请求中的某个数据
    String searchValuePath() default "";

    Class<? extends BaseCase> addDataBaseCase() default BaseCase.class;

    String resetAssert() default "";

    String[] group() default "0";//当输入0时则不进行分组考虑

    Class<? extends IAnnotationTestMethod> testMethod() default SearchDefaultImpl.class;

}
