package annotation.impl;

import annotation.AnnotationServer;
import annotation.IAnnotationTestMethod;
import annotation.annotations.StringToInt;
import lombok.SneakyThrows;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class StringToIntDefaultImpl implements IAnnotationTestMethod {
    @SneakyThrows
    @Override
    public void testMethod(Method method, Field field, Annotation a, AnnotationServer at) {
        StringToInt annotation = (StringToInt) a;
        String des =
                "类名:" + at.baseCase.getClass().getSimpleName() +
                        ",字段名:" + field.getName() +
                        ",StringToInt类型测试,传入整形:";
        Integer value = at.isInteger(field.get(at.getBaseCaseObject(method)) + "") ? Integer.parseInt((String) field.get(at.getBaseCaseObject(method))) : 1;
        at.fieldTest(method, field, value, des + value, annotation.asserts().newInstance(), annotation.resetAssert());

    }
}
