package component.user.userCase;

import annotation.annotations.BaseCaseData;
import annotation.annotations.Chinese;
import annotation.annotations.SpecialCharacters;
import api.RequestData;
import base.BaseCase;
import base.BaseListCase;
import component.user.service_constant.UserConstant;
import component.user.service_constant.UserService;
import config.asserts.SuccessAssertDefault;
import io.restassured.response.Response;
import utils.RandomUtil;

public class UserCase extends BaseCase {
    public String id;
    @SpecialCharacters(allowCharacters = "~!@", denyCharacters = "$%^", assertFail = SuccessAssertDefault.class)
    public String name;
    public String sex;
    @Chinese(group = "1")
    public Integer role;

    public UserCase() {
        serverMap = UserService.ADD_USER;
    }

    //连续发送的接口可以封装起来
    //方法名不能以get开头,JSON.toJSONString会报错，除非加上注解@JSONField(serialize = false)
    public Response user() {
        return user(addUser());
    }

    public Response user(BaseCase baseCase) {
        //--此处还可以调用其他接口--
        apiTest(new RequestData(baseCase));
        return apiTest(new RequestData(new BaseListCase()));
    }
    @BaseCaseData(group = "1")
    public UserCase addUser() {
        name = RandomUtil.getString();
        sex = RandomUtil.getString();
        return this;
    }

    //新增管理员case
    public UserCase addMenageUser(){
        UserCase userCase = addUser();
        userCase.role = UserConstant.MENAGE;
        return userCase;
    }
}
