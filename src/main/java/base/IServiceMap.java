package base;

public interface IServiceMap {
    //主机地址
    String getHost();

    //接口地址
    String getUri();

    //请求方式和参数类型
    ApiMethod getMethodAndRequestType();

    //jsonschema的文件位置
    String getJsonSchemaPath();

    //接口描述
    String getDes();

    //uuid,作为response的key存储
    String getUUID();

}
