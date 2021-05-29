package base;

import config.asserts.AssertMethod;
import config.header.IHeaders;
import config.preparamhandle.IParamPreHandle;
import lombok.Data;
import lombok.SneakyThrows;

import java.util.Map;

@Data
public class BaseCase extends CommandLogic {
    //拼接路径参数
    public String pathParam;//开头需要携带斜杠'/'
    //接口地址
    public IServiceMap serverMap;
    //请求头
    public IHeaders headers;
    //参数前置处理
    public IParamPreHandle iParamPreHandle;
    //断言的方式
    public AssertMethod assertMethod;
    //需要在方法间传递的参数,主要用于sql读取到数据后，其他方法能读取到该数据，或者在数据依赖中直接对字段赋值就不用这么传了
    public Map<String,Object> args;

    //根据模块赋默认的实现对象
    @SneakyThrows
    public BaseCase() {
        String packageName = this.getClass().getPackage().getName();
        ComponentDefaultInfo defaultImplEnum = ComponentDefaultInfo.getModuleEnum(packageName);
        headers = defaultImplEnum.getIHeaders().newInstance();
        iParamPreHandle = defaultImplEnum.getIParamPreHandle().newInstance();
        assertMethod = defaultImplEnum.getAssertMethod().newInstance();
    }
}
