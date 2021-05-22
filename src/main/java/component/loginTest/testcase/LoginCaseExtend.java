package component.loginTest.testcase;

import annotation.annotations.*;
import api.RequestData;
import base.BaseCase;
import component.loginTest.service_constant.LoginConstant;
import component.loginTest.service_constant.LoginService;
import config.asserts.*;
import config.header.DefaultHeaders;
import datafactory.annotation.DataFactory;
import lombok.Data;
import lombok.experimental.Accessors;
import utils.RandomUtil;

import static base.DataStore.*;
import static component.loginTest.service_constant.LoginConstant.IS_MENAGE;
import static component.loginTest.service_constant.LoginService.Login;
import static utils.set.PropertiesUtil.get;

@Data
@Accessors(fluent = true)
public class LoginCaseExtend extends LoginCase {

    public Integer id;

    public LoginCaseExtend() {
        serverMap = Login;
    }

    @BaseCaseData
    @MultiRequest(multiThreadNum = 10)
    @DataFactory(listApi = ListCase.class, des = "数据被创建")
    public LoginCase rightLoginCaseExtend() {
        id = 1;
        loginName = get("g_loginName");
        pwd = get("g_loginPwd");
        type = new Type().role(new TypeIn().TypeIn(IS_MENAGE));
        depend = "123";
        userName = RandomUtil.getString();
        assertMethod = new SuccessAssertGather(new EqualAssert("res", "test success"));
        return this;
    }
}
