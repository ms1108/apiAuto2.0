package base;

import api.ApiTest;
import lombok.Data;


public class CommandLogic extends ApiTest {
    //该类中的属性一定要写私有的如下,因为在实体类转json串时公有属性也会被写入json中。并且不能加上对应各get方法
    //private Integer test = 1;

}
