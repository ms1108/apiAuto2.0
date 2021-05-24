package base;

public interface IServiceMap {
    String getHost();

    String getUri();

    ApiMethod getMethodAndRequestType();

    String getJsonSchemaPath();

    String getDes();

}
