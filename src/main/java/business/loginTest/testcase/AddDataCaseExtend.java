package business.loginTest.testcase;

import annotation.AnnotationServer;
import annotation.AnnotationTestEntity;
import annotation.annotations.*;
import base.BaseCase;
import business.loginTest.apienum.DemoApiEnum;
import config.asserts.*;
import datafactory.DataFactoryEntity;
import datafactory.DataFactoryTest;
import datafactory.annotation.DataFactory;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;

import java.util.List;

import static base.DataStore.getResponseValue;
import static business.loginTest.apienum.DemoApiEnum.AddData;

@Data
@Accessors(fluent = true)
//继承不会执行父类的注解测试
public class AddDataCaseExtend extends AddDataCase {
    @NotNull(asserts = SuccessAssertDefault.class)
    public String id = getResponseValue(DemoApiEnum.Config, "res.depend");

    public AddDataCaseExtend() {
        iApi = AddData;
    }

    @DataDepend(isAlwaysExecute = true)
    public void dataDepend() {
        ConfigCase baseCase = newDependInstance(ConfigCase.class);
        baseCase.dataDepend();
        apiTest(baseCase.config());
    }

    @BaseCaseData
    @MultiRequest(multiThreadNum = 10)
    @DataFactory(listApi = ListCase.class, des = "数据被创建LoginCaseExtend")
    @AutoTest
    public AddDataCaseExtend rightLoginCaseExtend() {
        id = "1";
        depend = "123";
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
