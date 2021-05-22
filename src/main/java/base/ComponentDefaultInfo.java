package base;

import config.asserts.AssertMethod;
import config.asserts.SuccessAssertDefault;
import config.header.DefaultHeaders;
import config.header.IHeaders;
import config.host.DefaultHost;
import config.host.IHost;
import config.preparamhandle.IParamPreHandle;
import config.preparamhandle.ParamPreHandleImpl;
import lombok.Getter;
import org.testng.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public enum ComponentDefaultInfo {
    COMPONENT("loginTest", DefaultHost.class, DefaultHeaders.class, ParamPreHandleImpl.class, SuccessAssertDefault.class);
    private String packageName;
    private Class<? extends IHost> iHost;
    private Class<? extends IHeaders> iHeaders;
    private Class<? extends IParamPreHandle> iParamPreHandle;
    private Class<? extends AssertMethod> assertMethod;
    private Map<ComponentDefaultInfo, List<String>> enumAndPackageNames;

    ComponentDefaultInfo(String packageName, Class<? extends IHost> iHost, Class<? extends IHeaders> iHeaders, Class<? extends IParamPreHandle> iParamPreHandle, Class<? extends AssertMethod> assertMethod) {
        this.packageName = packageName;
        this.iHost = iHost;
        this.iHeaders = iHeaders;
        this.iParamPreHandle = iParamPreHandle;
        this.assertMethod = assertMethod;
    }
    //通过二级目录精确匹配
    //public static ComponentDefaultInfo getModuleEnum(String packageName) {
    //    packageName = packageName.split("\\.")[1];
    //    for (ComponentDefaultInfo value : ComponentDefaultInfo.values()) {
    //        if (value.getPackageName().equals(packageName)) {
    //            return value;
    //        }
    //    }
    //    Assert.fail("没有找到对应的枚举模块," + packageName);
    //    return COMPONENT;
    //}

    //模糊匹配，谁命中率高就返回谁
    public static ComponentDefaultInfo getModuleEnum(String packageName) {
        //枚举存的包名，和传进来的包名做对比，那个看命中率高就返回那个枚举
        int similarity = 0;
        ComponentDefaultInfo possibleEnum = null;
        for (ComponentDefaultInfo value : ComponentDefaultInfo.values()) {
            List<String> enumPackageNames = Arrays.stream(value.getPackageName().split("\\.")).collect(Collectors.toList());
            List<String> packageNames = Arrays.stream(packageName.split("\\.")).collect(Collectors.toList());
            //求列表的交集，即公共部分
            enumPackageNames.retainAll(packageNames);
            //enumPackageNames.size();为命中个数
            if (enumPackageNames.size() > similarity) {
                similarity = enumPackageNames.size();
                possibleEnum = value;
            }
        }
        if (possibleEnum == null) {
            Assert.fail(packageName + "，该包没有配置枚举");
        }
        return possibleEnum;
    }
}
