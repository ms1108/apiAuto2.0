package component.loginTest.testcase;

import annotation.AnnotationServer;
import annotation.AnnotationTestEntity;
import annotation.annotations.*;
import api.RequestData;
import base.BaseCase;
import config.asserts.*;
import datafactory.DataFactoryEntity;
import datafactory.DataFactoryTest;
import datafactory.annotation.DataFactory;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import utils.RandomUtil;

import java.util.List;

import static component.loginTest.service_constant.LoginConstant.IS_MENAGE;
import static component.loginTest.service_constant.LoginService.Login;
import static utils.set.PropertiesUtil.get;

@Data
@Accessors(fluent = true)
//继承不会执行父类的注解测试
public class LoginCaseExtend extends LoginCase {
    @NotNull(asserts = SuccessAssertDefault.class)
    public Integer id;

    public LoginCaseExtend() {
        serverMap = Login;
    }

    @DataDepend(isAlwaysExecute = true)
    public void dependBeforeClass() {
        //当前置调用链过长时建议封装到CommonLogic类中方便其他接口去使用，或者直接new对应的BaseCase类执行接口
        apiTest(new RequestData(new ConfigCase().dependCase()));
    }

    @BaseCaseData
    @MultiRequest(multiThreadNum = 10)
    @DataFactory(listApi = ListCase.class, des = "数据被创建LoginCaseExtend")
    @AutoTest
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
            //第一个对象必须执行依赖测试
            annotationTestEntities.get(0).setExecuteDataDependMethod(true);

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
