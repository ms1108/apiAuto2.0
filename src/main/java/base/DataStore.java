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
    public static String defaultAssertPath = "code";
    public static Object defaultAssertValue = 0;
    public static boolean isOpenAnnotation = true;
    public static String downloadDir = "src/main/resources/download";
    //存储原始的调用链依赖的实体
    public static Map<String, Object> dependChainOrigin = new HashMap<>();
    //自定义的调用链依赖使用，即用即清理
    public static Map<String, Object> dependChainDIY = new HashMap<>();

    //获取响应中的值
    public static <T> T getResponseValue(IServiceMap iServiceMap, String path) {
        Response response = res.get(iServiceMap.getUUID());
        if (response == null) {
            Assert.fail(iServiceMap.getUri() + ",接口无响应数据");
        }

        T t = (T) response.path(path);
        if (t == null) {
            Assert.fail("未获取到请求数据，接口：" + iServiceMap.getUri() + ",路径:" + path);
        }
        return t;
    }

    //获取所有响应求头
    public static Headers getResponseHeaders(IServiceMap iServiceMap) {
        Response response = res.get(iServiceMap.getUUID());
        if (response == null) {
            Assert.fail(iServiceMap.getUri() + ",接口无响应数据");
        }

        Headers t = response.headers();
        if (t == null) {
            Assert.fail("未获取到响应头，接口：" + iServiceMap.getUri());
        }
        return t;
    }

    //获取指定响应求头
    public static Header getResponseHeader(IServiceMap iServiceMap, String headerName) {
        Header t = getResponseHeaders(iServiceMap).get(headerName);
        if (t == null) {
            Assert.fail("未获取到响应头，接口：" + iServiceMap.getUri() + ",响应头:" + headerName);
        }
        return t;
    }

    //获取cookies
    public static <T> T getResponseCookies(IServiceMap iServiceMap) {
        Response response = res.get(iServiceMap.getUUID());
        if (response == null) {
            Assert.fail(iServiceMap.getUri() + ",接口无响应数据");
        }

        T t = (T) response.cookies();
        if (t == null) {
            Assert.fail("未获取到cookies，接口：" + iServiceMap.getUri());
        }
        return t;
    }

    //调用接口后再取值
    public static <T> T invokeApiGetValue(BaseCase baseCase, String path) {
        Response response = new ApiTest().apiTest(baseCase);

        T t = (T) response.path(path);
        if (t == null) {
            Assert.fail("未获取到请求数据，接口：" + baseCase.getServerMap().getUri() + ",路径:" + path);
        }
        return t;
    }

    //获取请求过的数据
    public static <T> T getRequestValue(IServiceMap iServiceMap, String path) {
        JsonPath jsonPath = req.get(iServiceMap.getUUID());
        if (jsonPath == null) {
            Assert.fail(iServiceMap.getUri() + ",接口无请求数据");
        }
        T t = (T) jsonPath.get(path);
        if (t == null) {
            Assert.fail("未获取到请求数据，接口：" + iServiceMap.getUri() + ",路径:" + path);
        }
        return t;
    }
}
