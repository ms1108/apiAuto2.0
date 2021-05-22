package base;

import config.asserts.AssertMethod;
import config.header.IHeaders;
import config.host.IHost;
import config.preparamhandle.IParamPreHandle;
import lombok.Data;
import lombok.SneakyThrows;

@Data
public class BaseCase extends CommandLogic {
    public String pathParam;//开头需要携带斜杠'/'
    public IHost iHost;
    public IServiceMap serverMap;
    public IHeaders headers;
    public IParamPreHandle iParamPreHandle;
    public AssertMethod assertMethod;

    //根据模块赋默认的实现对象
    @SneakyThrows
    public BaseCase() {
        String packageName = this.getClass().getPackage().getName();
        ComponentDefaultInfo defaultImplEnum = ComponentDefaultInfo.getModuleEnum(packageName);
        iHost = defaultImplEnum.getIHost().newInstance();
        headers = defaultImplEnum.getIHeaders().newInstance();
        iParamPreHandle = defaultImplEnum.getIParamPreHandle().newInstance();
        assertMethod = defaultImplEnum.getAssertMethod().newInstance();
    }
}
