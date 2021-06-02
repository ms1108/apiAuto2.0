package component.loginTest;

import api.RequestData;
import annotation.AnnotationTest;
import component.loginTest.testcase.ConfigCase;
import component.loginTest.testcase.AddDataCase;
import component.loginTest.testcase.AddDataCaseExtend;
import component.loginTest.testcase.TestUploadCase;
import config.asserts.EqualAssert;
import config.asserts.SuccessAssertDefault;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.listeners.RetryAnalyzer;

import static base.DataStore.dependChainDIY;

/**
 * Test类，建议用于多流程，多场景测试
 */
public class DemoTest extends AnnotationTest {

    //更多断言方法http://testingpai.com/article/1599472747188
    @Test
    public void test() {
        dependChainDIY.put(ConfigCase.class.getSimpleName(), new ConfigCase() {
            @Override
            public void dataDepend() {
                System.out.println("自定义依赖调用链");
            }
        });
        AddDataCase addDataCase = new AddDataCase();
        addDataCase.dataDepend();
        apiTest(new RequestData(addDataCase.rightCase())
                .setStepDes("这是我的测试步骤")
                .setAssertMethod(new SuccessAssertDefault()
                        .setAssert(new EqualAssert("res", "test success"))));
        //.then().body("res", equalTo("test success"));

        ////从响应中取值,常用于case类中
        //System.out.println("resValue " + BaseData.res.get(LoginService.Login.getUri()).path("code"));
        //Integer s = getResponse(LoginService.Login, "code");
        //System.out.println(s);
        ////从请求中取值,常用于case类中
        //System.out.println("reqValue " + BaseData.req.get(LoginService.Login.getUri()).get("loginName"));
        //String s1 = getRequest(LoginService.Login,"loginName");
        //System.out.println(s1);
    }

    @Test
    public void test11() {
        apiTest(new RequestData(new ConfigCase().config()));
        apiTest(new AddDataCase().rightCase());
        //.then().body("res", equalTo("test success"));

        ////从响应中取值,常用于case类中
        //System.out.println("resValue " + BaseData.res.get(LoginService.Login.getUri()).path("code"));
        //Integer s = getResponse(LoginService.Login, "code");
        //System.out.println(s);
        ////从请求中取值,常用于case类中
        //System.out.println("reqValue " + BaseData.req.get(LoginService.Login.getUri()).get("loginName"));
        //String s1 = getRequest(LoginService.Login,"loginName");
        //System.out.println(s1);
    }

    @Test
    public void test2() {
        apiTest(new RequestData(new AddDataCase().errorCase()));
    }

    @Test
    public void test3() {
        apiTest(new RequestData(new ConfigCase().config()));
        apiTest(new RequestData(new AddDataCase().dependCase()));
    }

    @Test
    public void test4() {
        apiTest(new RequestData(new ConfigCase().config()));
        apiTest(new RequestData(new AddDataCase().dependCase1()));
    }

    @Test
    public void test7() {
        apiTest(new RequestData(new TestUploadCase().uploadCase()));
    }

    //对该用例开启重试功能
    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void retryTest() {
        System.out.println("start");
        Assert.fail();
    }

    //跳过该用例的执行
    @Test(enabled = false)
    public void retryTest1() {
        System.out.println("start");

    }

    @Test
    public void testextend() {
        AddDataCaseExtend loginCaseExtend = new AddDataCaseExtend();
        loginCaseExtend.rightLoginCaseExtend();
        apiTest(new RequestData(loginCaseExtend));
    }
}
