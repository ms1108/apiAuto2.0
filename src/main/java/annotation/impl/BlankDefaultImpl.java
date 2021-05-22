package annotation.impl;

import annotation.AnnotationServer;
import annotation.IAnnotationTestMethod;
import annotation.annotations.Blank;
import lombok.SneakyThrows;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class BlankDefaultImpl implements IAnnotationTestMethod {
    @SneakyThrows
    @Override
    public void testMethod(Method method, Field field, Annotation a, AnnotationServer at) {
        Blank annotation = (Blank) a;
        String des =
                "类名:" + at.baseCase.getClass().getSimpleName() +
                        ",字段名:" + field.getName() +
                        ",空格测试,传入空格";
        String value = " ";
        at.fieldTest(method, field, value, des + value, annotation.assertFail().newInstance(), "");

        des =
                "类名:" + at.baseCase.getClass().getSimpleName() +
                        ",字段名:" + field.getName() +
                        ",首末尾空格测试,传入值:";
        value = " " + field.get(at.getBaseCaseObject(method)) + " ";
        at.fieldTest(method, field, value, des + value, annotation.assertSuccess().newInstance(), annotation.resetAssert());

    }
}
