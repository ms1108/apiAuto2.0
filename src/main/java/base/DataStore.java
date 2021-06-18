package base;

import api.ApiTest;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

public class DataStore {
    //存储响应信息
    public static Map<String, Response> res = new HashMap<>();
    //存储请求信息
    public static Map<String, JsonPath> req = new HashMap<>();
    //需要在方法间传递的参数,主要用于sql读取到数据后，其他方法能读取到该数据，或者在数据依赖中直接对字段赋值就不用这么传了
    public static Map<String, Object> args;
    public static String currentLoginName;
    public static String currentLoginPwd;
    public static String token;
    /**
     * 默认断言路径
     */
    public static String defaultAssertPath = "code";
    /**
     * 默认断言值
     */
    public static Object defaultAssertValue = 0;
    /**
     * 是否开放的注解测试
     */
    public static boolean isOpenAnnotation = true;
    /**
     * 是否收集依赖调用
     */
    public static boolean isCollectDependCall = false;
    /**
     * 下载路径
     */
    public static String downloadDir = "src/main/resources/download";
    //存储原始的调用链依赖的实体
    public static Map<String, Object> dependChainOrigin = new HashMap<>();
    //自定义的调用链依赖使用，即用即清理
    public static Map<String, Object> dependChainDIY = new HashMap<>();

    //获取响应中的值
    public static <T> T getResponseValue(IApi iApi, String path) {
        //收集调用时，返回空，因为依赖接口没有真正的被调用
        if (isCollectDependCall){
            return null;
        }
        Response response = res.get(iApi.getUUID());
        if (response == null) {
            Assert.fail(iApi.getApiPath() + ",接口无响应数据");
        }

        T t = (T) response.path(path);
        if (t == null) {
            Assert.fail("未获取到请求数据，接口：" + iApi.getApiPath() + ",路径:" + path);
        }
        return t;
    }

    //获取所有响应求头
    public static Headers getResponseHeaders(IApi iApi) {
        //收集调用时，返回空，因为依赖接口没有真正的被调用
        if (isCollectDependCall){
            return null;
        }
        Response response = res.get(iApi.getUUID());
        if (response == null) {
            Assert.fail(iApi.getApiPath() + ",接口无响应数据");
        }

        Headers t = response.headers();
        if (t == null) {
            Assert.fail("未获取到响应头，接口：" + iApi.getApiPath());
        }
        return t;
    }

    //获取指定响应求头
    public static Header getResponseHeader(IApi iApi, String headerName) {
        //收集调用时，返回空，因为依赖接口没有真正的被调用
        if (isCollectDependCall){
            return null;
        }
        Header t = getResponseHeaders(iApi).get(headerName);
        if (t == null) {
            Assert.fail("未获取到响应头，接口：" + iApi.getApiPath() + ",响应头:" + headerName);
        }
        return t;
    }

    //获取cookies
    public static <T> T getResponseCookies(IApi iApi) {
        //收集调用时，返回空，因为依赖接口没有真正的被调用
        if (isCollectDependCall){
            return null;
        }
        Response response = res.get(iApi.getUUID());
        if (response == null) {
            Assert.fail(iApi.getApiPath() + ",接口无响应数据");
        }

        T t = (T) response.cookies();
        if (t == null) {
            Assert.fail("未获取到cookies，接口：" + iApi.getApiPath());
        }
        return t;
    }

    //调用接口后再取值
    public static <T> T invokeApiGetValue(BaseCase baseCase, String path) {
        //收集调用时，返回空，因为依赖接口没有真正的被调用
        if (isCollectDependCall){
            return null;
        }
        Response response = new ApiTest().apiTest(baseCase);

        T t = (T) response.path(path);
        if (t == null) {
            Assert.fail("未获取到请求数据，接口：" + baseCase.getIApi().getApiPath() + ",路径:" + path);
        }
        return t;
    }

    //获取请求过的数据
    public static <T> T getRequestValue(IApi iApi, String path) {
        //收集调用时，返回空，因为依赖接口没有真正的被调用
        if (isCollectDependCall){
            return null;
        }
        JsonPath jsonPath = req.get(iApi.getUUID());
        if (jsonPath == null) {
            Assert.fail(iApi.getApiPath() + ",接口无请求数据");
        }
        T t = (T) jsonPath.get(path);
        if (t == null) {
            Assert.fail("未获取到请求数据，接口：" + iApi.getApiPath() + ",路径:" + path);
        }
        return t;
    }
}
