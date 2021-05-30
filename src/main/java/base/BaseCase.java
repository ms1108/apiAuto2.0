package base;

import api.ApiTest;
import config.asserts.AssertMethod;
import config.header.IHeaders;
import config.preparamhandle.IParamPreHandle;
import lombok.Data;
import lombok.SneakyThrows;

@Data
public abstract class BaseCase extends ApiTest {
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

    //每个接口的依赖都作为一个实体存储，使用时可以动态修改
    public abstract void dataDepend();

    //在数据依赖中调用其他接口时尽量使用该方法进行new对象，可实现动态修改依赖的实现
    @SneakyThrows
    public <T> T newDependInstance(Class<T> baseCaseClass) {
        String name = baseCaseClass.getSimpleName();
        //自定义中没有对应的对象则走默认存储的
        if (DataStore.dependChainDIY.get(name) != null) {
            T dependChain = (T) DataStore.dependChainDIY.get(name);
            DataStore.dependChainDIY.remove(name);
            return dependChain;
        } else if (DataStore.dependChainOrigin.get(name) != null) {//默认存储中没有则说明还没创建对象
            return (T) DataStore.dependChainOrigin.get(name);
        } else {//创建对象并且放入
            Object dependChain = baseCaseClass.newInstance();
            DataStore.dependChainOrigin.put(name, dependChain);
            return (T) dependChain;
        }
    }
}
