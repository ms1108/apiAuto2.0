package component.loginTest.service_constant;

import base.ApiMethod;
import base.IServiceMap;
import utils.set.PropertiesUtil;

import java.util.UUID;

public enum DemoService implements IServiceMap {
    Login("/test", "jsonschema/loginTest/login.json", "component/login"),
    Config("/depend", "", "depend"),
    Upload("/upload", ApiMethod.UPLOAD, "", "upload"),
    List("/list", ApiMethod.GET, "", "list");


    DemoService(String host, String uri, String jsonSchemaPath, String des) {
        this(host, uri, ApiMethod.POST, jsonSchemaPath, des);
    }

    DemoService(String uri, String jsonSchemaPath, String des) {
        this("g_host", uri, ApiMethod.POST, jsonSchemaPath, des);
    }

    DemoService(String uri, ApiMethod methodAndRequestType, String jsonSchemaPath, String des) {
        this("g_host", uri, methodAndRequestType, jsonSchemaPath, des);
    }

    DemoService(String host, String uri, ApiMethod methodAndRequestType, String jsonSchemaPath, String des) {
        this.host = PropertiesUtil.get(host);
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
    private String uuid;


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

    @Override
    public String getUUID() {
        if (uuid == null) {
            this.uuid = UUID.randomUUID().toString();
        }
        return uuid;
    }

}
