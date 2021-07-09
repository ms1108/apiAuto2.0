package base;

import annotation.annotations.DataDepend;
import lombok.SneakyThrows;

import java.lang.reflect.Method;

public class InvokeDependNewInstance {
    //在数据依赖中调用其他接口时使用该方法进行new对象，可实现动态修改依赖的实现
    @SneakyThrows
    public static <T> T invokeDependNewInstance(Class<T> baseCaseClass) {
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
