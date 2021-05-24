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
    public static Map<String, Response> res = new HashMap<>();
    public static Map<String, JsonPath> req = new HashMap<>();
    public static String currentLoginName;
    public static String currentLoginPwd;
    public static String token;
    public static String defaultAssertPath = "code";
    public static Object defaultAssertValue = 0;
    public static boolean isOpenAnnotation = true;
    public static String DownloadDir = "src/main/resources/download";

    //获取响应中的值
    public static <T> T getResponseValue(IServiceMap iServiceMap, String path) {
        Response response = res.get(iServiceMap.getUri());
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
        Response response = res.get(iServiceMap.getUri());
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
        Response response = res.get(iServiceMap.getUri());
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
        JsonPath jsonPath = req.get(iServiceMap.getUri());
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
