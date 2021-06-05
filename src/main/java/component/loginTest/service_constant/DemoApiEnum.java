package component.loginTest.service_constant;

import base.ApiMethod;
import base.IApi;
import utils.set.PropertiesUtil;

import java.util.UUID;

public enum DemoApiEnum implements IApi {
    AddData("/test", "jsonschema/test/test.json", "addData"),
    Config("/depend", "", "depend"),
    Upload("/upload", ApiMethod.UPLOAD, "", "upload"),
    List("/list", ApiMethod.GET, "", "list");


    DemoApiEnum(String host, String apiPath, String jsonSchemaPath, String des) {
        this(host, apiPath, ApiMethod.POST, jsonSchemaPath, des);
    }

    DemoApiEnum(String apiPath, String jsonSchemaPath, String des) {
        this("g_host", apiPath, ApiMethod.POST, jsonSchemaPath, des);
    }

    DemoApiEnum(String apiPath, ApiMethod methodAndRequestType, String jsonSchemaPath, String des) {
        this("g_host", apiPath, methodAndRequestType, jsonSchemaPath, des);
    }

    DemoApiEnum(String host, String apiPath, ApiMethod methodAndRequestType, String jsonSchemaPath, String des) {
        this.host = PropertiesUtil.get(host);
        this.apiPath = apiPath;
        this.methodAndRequestType = methodAndRequestType;
        this.jsonSchemaPath = jsonSchemaPath;
        this.des = des;
    }

    private String host;
    private String apiPath;
    private ApiMethod methodAndRequestType;
    private String jsonSchemaPath;
    private String des;
    private String uuid;


    @Override
    public String getHost() {
        return host;
    }

    @Override
    public String getApiPath() {
        return apiPath;
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
