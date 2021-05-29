package annotation;

import base.BaseCase;
import lombok.Data;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Data
public class AnnotationTestEntity {
    public Field field;
    public String fieldPath;
    public Method dataDependMethod;
    public Method baseCaseDataMethod;
    public Class<? extends BaseCase> baseCaseClass;
    public BaseCase baseCase;
    public BaseCase baseCaseData;
    public Annotation annotation;
    //是否在每个注解测试都执行一次
    public boolean executeDataDependMethod;
    public Class<? extends IAnnotationTestMethod> iAnnotationTestMethod;
    public Method method;
    //替换成什么值
    public Object value;
    public String des;

}
