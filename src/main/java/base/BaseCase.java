package base;

import api.ApiTest;
import config.asserts.AssertMethod;
import config.header.IHeaders;
import config.preparamhandle.IParamPreHandle;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;

@Data
public class BaseCase extends ApiTest {
    //拼接路径参数
    public String pathParam;//开头需要携带斜杠'/'
    //接口地址
    public IApi iApi;
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
        BusinessDefaultInfo defaultImplEnum = BusinessDefaultInfo.getModuleEnum(packageName);
        headers = defaultImplEnum.getIHeaders().newInstance();
        iParamPreHandle = defaultImplEnum.getIParamPreHandle().newInstance();
        assertMethod = defaultImplEnum.getAssertMethod().newInstance();
    }

    //每个接口的依赖都作为一个实体存储，使用时可以动态修改
    //public abstract void dataDepend();

    //在数据依赖中调用其他接口时尽量使用该方法进行new对象，可实现动态修改依赖的实现
    @SneakyThrows
    public <T> T newDependInstance(Class<T> baseCaseClass) {
        String name = baseCaseClass.getSimpleName();
        //自定义中没有对应的对象则自行创建对象
        if (DataStore.dependChainDIY.get(name) != null) {
            T dependChain = (T) DataStore.dependChainDIY.get(name);
            //因为自定义调用链中的对象创建过早，初始化的数据有可能被修改了，所以要重新创建对象，并把默认值拷贝给旧的对象
            BeanUtils.copyProperties(baseCaseClass.newInstance(), dependChain);
            //只使用一次所以删除
            DataStore.dependChainDIY.remove(name);
            return dependChain;
        } else {//创建对象并且放入
            Object dependChain = baseCaseClass.newInstance();
            return (T) dependChain;
        }
    }
}
