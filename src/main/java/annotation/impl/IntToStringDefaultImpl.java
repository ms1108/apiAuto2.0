package annotation.impl;

import annotation.AnnotationServer;
import annotation.IAnnotationTestMethod;
import annotation.annotations.IntToString;
import lombok.SneakyThrows;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class IntToStringDefaultImpl implements IAnnotationTestMethod {
    @SneakyThrows
    @Override
    public void testMethod(Method method, Field field, Annotation a, AnnotationServer at) {
        IntToString annotation = (IntToString) a;
        String des =
                "类名:" + at.baseCase.getClass().getSimpleName() +
                        ",字段名:" + field.getName() +
                        ",IntToString类型测试,传入字符:";
        String value = field.get(at.getBaseCaseObject(method)) + "";
        at.fieldTest(method, field, value, des + value, annotation.asserts().newInstance(), annotation.resetAssert());

    }
}
