package component.loginTest.testcase;

import annotation.AnnotationServer;
import annotation.AnnotationTestEntity;
import annotation.annotations.*;
import api.RequestData;
import base.BaseCase;
import base.IServiceMap;
import component.loginTest.service_constant.LoginConstant;
import component.loginTest.service_constant.LoginService;
import config.asserts.*;
import config.header.DefaultHeaders;
import datafactory.DataFactoryEntity;
import datafactory.DataFactoryTest;
import datafactory.annotation.DataFactory;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import utils.RandomUtil;

import java.util.List;
import java.util.stream.Collectors;

import static base.DataStore.*;
import static component.loginTest.service_constant.LoginConstant.IS_MENAGE;
import static component.loginTest.service_constant.LoginService.Login;
import static utils.set.PropertiesUtil.get;

@Data
public class LoginCase extends BaseCase {
    public IServiceMap serverMap = Login;

    @Unique(assertFail = SuccessAssertDefault.class, group = "1")
    @EnumString
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
        @Unique(assertFail = SuccessAssertDefault.class)
        public TypeIn role;
    }

    @Data
    @Accessors(fluent = true)
    public static class TypeIn {
        @Range(minNum = "0.1", maxNum = "1", floatValue = "0.1", assertFail = SuccessAssertDefault.class)//测试范围(0,1]
        @EnumInt
        @EnumString
        public Integer TypeIn;
    }

    @DataDepend
    public void dependBeforeClass() {
        //当前置调用链过长时建议封装到CommonLogic类中方便其他接口去使用，或者直接new对应的BaseCase类执行接口
        apiTest(new RequestData(new ConfigCase().dependCase()));
    }

    @BaseCaseData
    @MultiRequest(multiThreadNum = 10)
    @DataFactory(listApi = ListCase.class, des = "数据被创建")
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
    @DataFactory(listApi = ListCase.class, des = "数据被创建2")
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

    //调试注解测试
    @SneakyThrows
    public static void main(String[] args) {
        //注解测试
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        Class<? extends BaseCase> BaseCaseClass = (Class<? extends BaseCase>) Class.forName(className);

        AnnotationServer annotationServer = new AnnotationServer();
        List<AnnotationTestEntity> annotationTestEntities = annotationServer.createAnnotationTestEntity(BaseCaseClass);

        if (annotationTestEntities != null && annotationTestEntities.size() > 0) {
            //只想执行某个注解可以这么写，想执行全部则注释掉这个过滤
            //annotationTestEntities = annotationTestEntities.stream()
            //        .filter(x -> x.annotation.annotationType().getSimpleName().equals(AutoTest.class.getSimpleName()))
            //        .collect(Collectors.toList());
            ////第一个对象必须执行依赖测试
            //annotationTestEntities.get(0).setExecuteDataDependMethod(true);

            for (AnnotationTestEntity testEntity : annotationTestEntities) {
                annotationServer.executeAnnotationTest(testEntity);
            }
        }

        System.out.println("-----------------------------------执行DataFactory-----------------------------------");

        DataFactoryTest dataFactoryTest = new DataFactoryTest();
        List<DataFactoryEntity> dataFactoryBaseCase = dataFactoryTest.getDataFactoryBaseCase(BaseCaseClass);
        if (dataFactoryBaseCase != null && dataFactoryBaseCase.size() > 0) {
            for (DataFactoryEntity dataFactoryEntity : dataFactoryBaseCase) {
                dataFactoryTest.executeDataFactoryTest(dataFactoryEntity);
            }
        }
    }
}
