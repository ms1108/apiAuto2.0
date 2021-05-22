package component.user.service_constant;

import base.ApiMethod;
import base.IServiceMap;

public enum UserService implements IServiceMap {
    ADD_USER("/user/add", "", "user"),
    USER_LIST("/user/list", "", "list");

    UserService(String uri, String jsonSchemaPath, String des) {
        this(uri, ApiMethod.POST, jsonSchemaPath, des);
    }


    UserService(String uri, ApiMethod methodAndRequestType, String jsonSchemaPath, String des) {
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
