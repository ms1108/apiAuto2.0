package annotation.impl;

import annotation.AnnotationServer;
import annotation.IAnnotationTestMethod;
import annotation.annotations.NotEmpty;
import lombok.SneakyThrows;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class NotEmptyDefaultImpl implements IAnnotationTestMethod {
    @SneakyThrows
    @Override
    public void testMethod(Method method, Field field, Annotation a, AnnotationServer at) {
        NotEmpty annotation = (NotEmpty) a;
        String des =
                "类名:" + at.baseCase.getClass().getSimpleName() +
                ",字段名:" + field.getName() +
                ",空字符串校验";
        at.fieldTest(method, field, "", des, annotation.asserts().newInstance(), annotation.resetAssert());

    }
}
