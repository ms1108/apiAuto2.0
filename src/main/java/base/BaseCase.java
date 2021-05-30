package base;

import config.asserts.AssertMethod;
import config.header.IHeaders;
import config.preparamhandle.IParamPreHandle;
import lombok.Data;
import lombok.SneakyThrows;

@Data
public class BaseCase extends CommonLogic {
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

    //根据模块赋默认的实现对象
    @SneakyThrows
    public BaseCase() {
        String packageName = this.getClass().getPackage().getName();
        ComponentDefaultInfo defaultImplEnum = ComponentDefaultInfo.getModuleEnum(packageName);
        headers = defaultImplEnum.getIHeaders().newInstance();
        iParamPreHandle = defaultImplEnum.getIParamPreHandle().newInstance();
        assertMethod = defaultImplEnum.getAssertMethod().newInstance();
    }

    @Override
    public void dependConstant() {}
}
