package component.loginTest.service_constant;

import base.ApiMethod;
import base.IServiceMap;

public enum LoginService implements IServiceMap {
    Login("/test", "jsonschema/loginTest/login.json", "component/login"),
    Config("/depend", "", "depend"),
    Upload("/upload", ApiMethod.UPLOAD, "", "upload"),
    List("/list", ApiMethod.GET, "", "list");


    LoginService(String uri, String jsonSchemaPath, String des) {
        this(uri, ApiMethod.POST, jsonSchemaPath, des);
    }


    LoginService(String uri, ApiMethod methodAndRequestType, String jsonSchemaPath, String des) {
        this.uri = uri;
        this.methodAndRequestType = methodAndRequestType;
        this.jsonSchemaPath = jsonSchemaPath;
        this.des = des;
    }

    private String uri;
    private ApiMethod methodAndRequestType;
    private String jsonSchemaPath;
    private String des;


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
