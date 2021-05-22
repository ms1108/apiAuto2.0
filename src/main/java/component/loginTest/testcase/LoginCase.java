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
public class LoginCase extends BaseCase {
    @Unique(assertFail = SuccessAssertDefault.class)
    @NotNull(asserts = SuccessAssertDefault.class)
    @NotEmpty(asserts = SuccessAssertDefault.class)
    @Blank(assertFail = SuccessAssertDefault.class)
    public String loginName;


    @Length(minLen = 1, maxLen = 8, assertFail = SuccessAssertDefault.class)
    public String pwd;

    @EnumInt
    public Integer isManage;

    public Type type;

    @StringToInt(asserts = SuccessAssertDefault.class)
    @IntToString(resetAssert = "assertRightLogin")
    public String depend;//依赖config接口返回的结果

    public String userName;

    @Data
    @Accessors(fluent = true)
    public static class Type {
        @Range(maxNum = "10", minInfinite = true, assertFail = SuccessAssertDefault.class)
        public TypeIn role;
    }

    @Data
    @Accessors(fluent = true)
    public static class TypeIn {
        @Range(minNum = "0.1", maxNum = "1", floatValue = "0.1", assertFail = SuccessAssertDefault.class)//测试范围(0,1]
        @EnumInt
        public Integer TypeIn;
    }

    public LoginCase() {
        serverMap = Login;
    }

    @DataDepend
    public void dependBeforeClass() {
        System.out.println("执行了LoginCase.dependBeforeClass");
        //当前置调用链过长时建议封装到CommonLogic类中方便其他接口去使用
        apiTest(new RequestData(new ConfigCase().dependCase()));
    }

    @BaseCaseData
    @MultiRequest(multiThreadNum = 10)
    @DataFactory(listApi = ListCase.class,des = "数据被创建")
    public LoginCase rightLoginCase() {
        loginName = get("g_loginName");
        pwd = get("g_loginPwd");
        type = new Type().role(new TypeIn().TypeIn(IS_MENAGE));
        depend = "123";
        userName = RandomUtil.getString();
        assertMethod = new SuccessAssertGather(new EqualAssert("res", "test success"));
        return this;
    }

    @BaseCaseData(group = "1")
    public LoginCase rightLoginCase1() {
        loginName = get("g_loginName");
        pwd = get("g_loginPwd");
        type = new Type().role(new TypeIn().TypeIn(LoginConstant.No_MENAGE));
        depend = "123456";
        userName = RandomUtil.getString();
        assertMethod = new SuccessAssertGather(new EqualAssert("res", "test success"));
        headers = new DefaultHeaders();
        return this;
    }

    public LoginCase errorLoginCase() {
        LoginCase loginCase = rightLoginCase();
        loginCase.pwd = "";
        return this;
    }

    @AutoTest(des = "依赖测试")
    public LoginCase dependCase() {
        LoginCase loginCase = rightLoginCase();
        //从其他的请求参数中获取值
        loginCase.depend = getRequestValue(LoginService.Config, "depend");
        return this;
    }

    public LoginCase dependCase1() {
        LoginCase loginCase = rightLoginCase();
        loginCase.depend = null;
        //从其他响应中获取值，需要事先调用相应接口
        loginCase.depend = getResponseValue(LoginService.Config, "res.depend");
        loginCase.depend = invokeApiGetValue(new ConfigCase().dependCase(), "res.depend");
        return this;
    }

    public AssertMethod assertRightLogin() {
        return new SuccessAssertGather(new EqualAssert("res", "test success"),
                new ByOtherApiAssert(new ConfigCase().dependCase()), new EqualAssert("res.depend", "123"));
    }
}
