package annotation.impl;

import annotation.AnnotationServer;
import annotation.IAnnotationTestMethod;
import annotation.annotations.EnumInt;
import annotation.annotations.EnumString;
import lombok.SneakyThrows;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class EnumStringImpl implements IAnnotationTestMethod {
    @SneakyThrows
    @Override
    public void testMethod(Method method, Field field, Annotation a, AnnotationServer at) {
        EnumString annotation = (EnumString) a;
        String des;
        for (String iEnum : annotation.value()) {
            des =
                    "类名:" + at.baseCase.getClass().getSimpleName() +
                            ",字段名:" + field.getName() +
                            ",String类型枚举遍历测试,传入：";
            at.fieldTest(method, field, iEnum, des + iEnum, annotation.asserts().newInstance(), annotation.resetAssert());
        }

    }
}
