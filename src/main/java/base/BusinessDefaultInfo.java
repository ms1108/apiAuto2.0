package base;

import config.asserts.AssertMethod;
import config.asserts.SuccessAssertDefault;
import config.header.DefaultHeaders;
import config.header.IHeaders;
import config.preparamhandle.IParamPreHandle;
import config.preparamhandle.ParamPreHandleImpl;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Getter
public enum BusinessDefaultInfo {
    BUSINESS("loginTest", DefaultHeaders.class, ParamPreHandleImpl.class, SuccessAssertDefault.class);
    private String packageName;
    private Class<? extends IHeaders> iHeaders;
    private Class<? extends IParamPreHandle> iParamPreHandle;
    private Class<? extends AssertMethod> assertMethod;
    private static Map<String, BusinessDefaultInfo> packageNameAndEnum = new HashMap<>();

    BusinessDefaultInfo(String packageName, Class<? extends IHeaders> iHeaders, Class<? extends IParamPreHandle> iParamPreHandle, Class<? extends AssertMethod> assertMethod) {
        this.packageName = packageName;
        this.iHeaders = iHeaders;
        this.iParamPreHandle = iParamPreHandle;
        this.assertMethod = assertMethod;
    }

    //模糊匹配，谁命中率高就返回谁
    public static BusinessDefaultInfo getModuleEnum(String packageName) {
        //获取历史记录的感觉
        if (packageNameAndEnum.get(packageName) != null) {
            return packageNameAndEnum.get(packageName);
        }
        //枚举存的包名，和传进来的包名做对比，那个看命中率高就返回那个枚举
        int similarity = 0;
        BusinessDefaultInfo possibleEnum = null;
        for (BusinessDefaultInfo value : BusinessDefaultInfo.values()) {
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
        packageNameAndEnum.put(packageName, possibleEnum);
        return possibleEnum;
    }
}
