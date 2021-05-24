package component.loginTest.service_constant;

import base.ApiMethod;
import base.IServiceMap;
import utils.set.PropertiesUtil;

public enum LoginService implements IServiceMap {
    Login("/test", "jsonschema/loginTest/login.json", "component/login"),
    Config("/depend", "", "depend"),
    Upload("/upload", ApiMethod.UPLOAD, "", "upload"),
    List("/list", ApiMethod.GET, "", "list");


    LoginService(String host, String uri, String jsonSchemaPath, String des) {
        this(host, uri, ApiMethod.POST, jsonSchemaPath, des);
    }

    LoginService(String uri, String jsonSchemaPath, String des) {
        this(PropertiesUtil.get("g_host"), uri, ApiMethod.POST, jsonSchemaPath, des);
    }

    LoginService(String uri, ApiMethod methodAndRequestType, String jsonSchemaPath, String des) {
        this(PropertiesUtil.get("g_host"), uri, methodAndRequestType, jsonSchemaPath, des);
    }

    LoginService(String host, String uri, ApiMethod methodAndRequestType, String jsonSchemaPath, String des) {
        this.host = host;
        this.uri = uri;
        this.methodAndRequestType = methodAndRequestType;
        this.jsonSchemaPath = jsonSchemaPath;
        this.des = des;
    }

    private String host;
    private String uri;
    private ApiMethod methodAndRequestType;
    private String jsonSchemaPath;
    private String des;


    @Override
    public String getHost() {
        return host;
    }

    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public ApiMethod getMethodAndRequestType() {
        return methodAndRequestType;
    }

    @Override
    public String getJsonSchemaPath() {
        return jsonSchemaPath;
    }

    @Override
    public String getDes() {
        return des;
    }

}
