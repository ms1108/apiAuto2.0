package base;

import api.ApiTest;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

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
     * 下载路径
     */
    public static String downloadDir = "src/main/resources/download";

    //自定义的调用链依赖使用，即用即清理
    public static Map<String, Object> dependChainDIY = new HashMap<>();

    //获取响应中的值，类型为什么类型就返回什么
    public static <T> T getResponseValue(IApi iApi, String path) {
        Response response = res.get(iApi.getUUID());
        if (response == null) {
            return null;
        }
        T t = (T) response.path(path);
        if (t == null) {
            return null;
        }
        return t;
    }

    public static <T> T getResponseValue(IApi iApi, String path, Class<T> clazz) {
        Object value = getResponseValue(iApi, path);
        if (value == null) {
            return null;
        }
        //返回的类型由外边控制
        if (clazz.getSimpleName().equals(String.class.getSimpleName())) {
            return (T) (value + "");
        }
        return (T) Integer.valueOf(value + "");
    }

    //获取所有响应求头
    public static Headers getResponseHeaders(IApi iApi) {
        Response response = res.get(iApi.getUUID());
        if (response == null) {
            return null;
        }
        Headers t = response.headers();
        if (t == null) {
            return null;
        }
        return t;
    }

    //获取指定响应求头
    public static Header getResponseHeader(IApi iApi, String headerName) {
        Header t = getResponseHeaders(iApi).get(headerName);
        if (t == null) {
            return null;
        }
        return t;
    }

    //获取cookies
    public static <T> T getResponseCookies(IApi iApi) {
        Response response = res.get(iApi.getUUID());
        if (response == null) {
            return null;
        }
        T t = (T) response.cookies();
        if (t == null) {
            return null;
        }
        return t;
    }

    //调用接口后再取值
    public static <T> T invokeApiGetValue(BaseCase baseCase, String path) {
        Response response = new ApiTest().apiTest(baseCase);
        T t = (T) response.path(path);
        if (t == null) {
            return null;
        }
        return t;
    }

    //获取请求过的数据
    public static <T> T getRequestValue(IApi iApi, String path) {
        JsonPath jsonPath = req.get(iApi.getUUID());
        if (jsonPath == null) {
            return null;
        }
        T t = (T) jsonPath.get(path);
        if (t == null) {
            return null;
        }
        return t;
    }

    public static <T> T getRequestValue(IApi iApi, String path, Class<T> clazz) {
        Object value = getRequestValue(iApi, path);
        if (value == null) {
            return null;
        }
        //返回的类型由外边控制
        if (clazz.getSimpleName().equals(String.class.getSimpleName())) {
            return (T) (value + "");
        }
        return (T) Integer.valueOf(value + "");
    }

    //存入调用链的map中
    public static void putDependChainDIY(BaseCase baseCase){
        dependChainDIY.put(baseCase.iApi.getUUID(),baseCase);
    }
}
