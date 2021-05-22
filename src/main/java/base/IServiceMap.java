package base;

public interface IServiceMap {
    String getUri();

    ApiMethod getMethodAndRequestType();

    String getJsonSchemaPath();

    String getDes();

}
