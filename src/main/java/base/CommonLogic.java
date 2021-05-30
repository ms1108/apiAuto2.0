package base;

import api.ApiTest;
import lombok.Data;


public abstract class CommonLogic extends ApiTest {
    //被BaseCase类继承了的类中的属性一定要写私有的如下,因为在实体类转json串时公有属性也会被写入json中。并且不能加上对应各get方法
    //private Integer test = 1;

    //每个接口的依赖都作为一个实体存储，使用时可以动态修改
    public abstract void dependConstant();

}
