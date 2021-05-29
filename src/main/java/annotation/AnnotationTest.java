package annotation;

import annotation.AnnotationServer;
import annotation.AnnotationTestEntity;
import annotation.IAnnotationTestMethod;
import annotation.annotations.DataDepend;
import base.BaseCase;
import lombok.SneakyThrows;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.ReportUtil;

import java.lang.reflect.Method;
import java.util.List;

public class AnnotationTest extends AnnotationServer {
    //business.loginTest.testcase格式
    private String packagePath;

    public AnnotationTest() {
        packagePath = this.getClass().getPackage().getName() + ".testcase";
    }

    public AnnotationTest(String packagePath) {
        this.packagePath = packagePath.trim();
    }

    @DataProvider
    public Object[][] executeAnnotationAble() {
        return getDataProvider();
    }

    @Test(dataProvider = "executeAnnotationAble")
    public void annotationTest(String testName, AnnotationTestEntity annotationTestEntity) {
        ReportUtil.log("PackagePath       : " + packagePath);
        ReportUtil.log("BaseCase          : " + annotationTestEntity.baseCaseClass.getSimpleName());
        String MethodOrFieldName = annotationTestEntity.field == null ? annotationTestEntity.method.getName() : annotationTestEntity.field.getName();
        ReportUtil.log("MethodOrFieldName : " + MethodOrFieldName);
        ReportUtil.log("AnnotationName    : " + annotationTestEntity.annotation.annotationType().getSimpleName());
        //换行
        ReportUtil.log("");
        executeAnnotationTest(annotationTestEntity);
    }

    //构造成这种格式:object[][] objects = {{"baseCaseData的方法名,annotation名称在该字段上的",Class<? extends BaseCase>}};
    @SneakyThrows
    private Object[][] getDataProvider() {
        List<AnnotationTestEntity> annotationTestEntities = createAnnotationTestEntity(packagePath);
        //将List转成object[][],后边的括号定义内层元素
        Object[][] array = new Object[annotationTestEntities.size()][2];
        for (int i = 0; i < annotationTestEntities.size(); i++) {
            array[i][0] = testNgMethodName(annotationTestEntities.get(i));
            array[i][1] = annotationTestEntities.get(i);
        }
        return array;
    }

    public String testNgMethodName(AnnotationTestEntity annotationTestEntity) {
        StringBuilder sb = new StringBuilder();
        if (annotationTestEntity.baseCaseClass != null)
            sb.append("测试的类：").append(annotationTestEntity.baseCaseClass.getSimpleName()).append("，");
        if (annotationTestEntity.field != null)
            sb.append("测试字段：").append(annotationTestEntity.field.getName()).append("，");
        if (annotationTestEntity.method != null)
            sb.append("测试方法：").append(annotationTestEntity.method.getName()).append("，");
        if (annotationTestEntity.annotation != null && annotationTestEntity.field != null)
            sb.append("字段注解名称：").append(annotationTestEntity.annotation.annotationType().getSimpleName()).append("，");
        if (annotationTestEntity.annotation != null && annotationTestEntity.method != null)
            sb.append("方法注解名称：").append(annotationTestEntity.annotation.annotationType().getSimpleName()).append("，");
        if (annotationTestEntity.executeDataDependMethod)
            sb.append("前置方法名称：").append(annotationTestEntity.dataDependMethod.getName()).append("，");
        if (annotationTestEntity.baseCaseDataMethod != null) {
            sb.append("基础数据方法命名：").append(annotationTestEntity.baseCaseDataMethod.getName()).append("，");
        } else if (annotationTestEntity.field != null) {
            sb.append("基础数据方法为：该类的无参初始化").append("，");
        }

        return sb.toString().substring(0, sb.length() - 1);
    }

}