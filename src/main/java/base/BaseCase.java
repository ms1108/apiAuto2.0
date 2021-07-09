package base;

import annotation.annotations.DataDepend;
import api.ApiTest;
import config.asserts.AssertMethod;
import config.header.IHeaders;
import config.preparamhandle.IParamPreHandle;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Method;

@Data
public abstract class BaseCase extends ApiTest {
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
        Class<? extends BaseCase> aClass = (Class<? extends BaseCase>) baseCaseClass;
        //获取dataDepend
        Method dependMethod = null;
        for (Method declaredMethod : aClass.getDeclaredMethods()) {
            if (declaredMethod.isAnnotationPresent(DataDepend.class)) {
                dependMethod = declaredMethod;
                break;
            }
        }
        //此时new的对象，由于还没调用依赖接口，所以该对象的数据是不完整的
        BaseCase baseCase = aClass.newInstance();
        String uuid = baseCase.iApi.getUUID();
        //自定义中没有对应的对象则自行创建对象
        if (DataStore.dependChainDIY.get(uuid) != null) {
            baseCase = (BaseCase) DataStore.dependChainDIY.get(uuid);
            //只使用一次所以删除
            DataStore.dependChainDIY.remove(uuid);
        }
        //调用依赖方法
        if (dependMethod != null)
            dependMethod.invoke(baseCase);
        return (T) aClass.newInstance();
    }
}
