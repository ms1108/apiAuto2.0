package business.loginTest.component;

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
import utils.RandomUtil;

import java.util.List;

import static base.DataStore.*;
import static base.InvokeDependNewInstance.invokeDependNewInstance;
import static business.loginTest.apienum.DemoApiEnum.AddData;
import static utils.set.PropertiesUtil.get;

@Data
@Accessors(fluent = true)
public class AddDataCase extends BaseCase {

    @Unique(assertFail = SuccessAssertDefault.class, group = "1")
    @EnumString
    @NotNull(asserts = SuccessAssertDefault.class)
    @NotEmpty(asserts = SuccessAssertDefault.class)
    @Blank(assertFail = SuccessAssertDefault.class)
    public String name = get("g_loginName");


    @Length(minLen = 1, maxLen = 8, assertFail = SuccessAssertDefault.class)
    public String pwd = get("g_loginPwd");

    @EnumInt
    public Integer isManage;

    public Type type = new Type();

    @StringToInt(asserts = SuccessAssertDefault.class)
    @IntToString(resetAssert = "assertRightLogin")
    public String depend = getResponseValue(DemoApiEnum.Config, "res.depend", String.class);//依赖config接口返回的结果

    public String userName = RandomUtil.getString();

    @Data
    public static class Type {
        @Range(maxNum = "10", minInfinite = true, assertFail = SuccessAssertDefault.class)
        @Unique(assertFail = SuccessAssertDefault.class)
        public TypeIn role = new TypeIn();
    }

    @Data
    public static class TypeIn {
        @Range(minNum = "0.1", maxNum = "1", floatValue = "0.1", assertFail = SuccessAssertDefault.class)//测试范围(0,1]
        @EnumInt
        @EnumString
        public Integer TypeIn = getResponseValue(DemoApiEnum.Config, "res.depend", Integer.class);
    }

    public AddDataCase() {
        iApi = AddData;
    }

    @DataDepend
    public void dataDepend() {
        apiTest(invokeDependNewInstance(ConfigCase.class).config());
    }

    @BaseCaseData
    @MultiRequest(multiThreadNum = 10)
    @DataFactory(listApi = ListCase.class, des = "数据被创建")
    public AddDataCase rightCase() {
        depend = "123";
        return this;
    }

    @BaseCaseData(group = "1")
    @DataFactory(listApi = ListCase.class, des = "数据被创建2")
    public AddDataCase rightCase1() {
        depend = "123456";
        type.role.TypeIn = 2;
        return this;
    }

    @Description(name = "用例的名称", des = "用例的描述")
    public AddDataCase errorCase() {
        AddDataCase addDataCase = rightCase();
        addDataCase.pwd = "";
        return this;
    }

    @AutoTest(des = "注解自动测试")
    public AddDataCase autoTestCase() {
        rightCase();
        pwd = "111";
        return this;
    }

    public AddDataCase dependCase1() {
        rightCase();
        depend = null;
        //从其他响应中获取值，需要事先调用相应接口
        depend = getResponseValue(DemoApiEnum.Config, "res.depend");
        depend = invokeApiGetValue(new ConfigCase(), "res.depend");
        //从其他的请求参数中获取值
        depend = getRequestValue(DemoApiEnum.Config, "depend");
        return this;
    }

    public AssertMethod assertRightLogin() {
        //headers = new DefaultHeaders();
        //assertMethod = new SuccessAssertGather(new EqualAssert("res", "test success"));
        return new SuccessAssertGather(new EqualAssert("res", "test success"),
                new ByOtherApiAssert(new ConfigCase().config()), new EqualAssert("res.depend", "123"));
    }

    //调试注解测试
    @SneakyThrows
    public static void main(String[] args) {
        //注解测试
        //获取当前类
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
