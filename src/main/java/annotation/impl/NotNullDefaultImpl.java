package annotation.impl;

import annotation.AnnotationServer;
import annotation.IAnnotationTestMethod;
import annotation.annotations.NotNull;
import lombok.SneakyThrows;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class NotNullDefaultImpl implements IAnnotationTestMethod {
    @SneakyThrows
    @Override
    public void testMethod(Method method, Field field, Annotation a, AnnotationServer at) {
        NotNull annotation = (NotNull) a;
        String des =
                "类名:" + at.baseCase.getClass().getSimpleName() +
                        ",字段名:" + field.getName() +
                        ",输入null值校验";
        at.fieldTest(method, field, null, des, annotation.asserts().newInstance(), annotation.resetAssert());

    }
}
