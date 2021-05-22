package annotation.impl;

import annotation.AnnotationServer;
import annotation.IAnnotationTestMethod;
import annotation.annotations.Blank;
import annotation.annotations.EnumInt;
import lombok.SneakyThrows;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class EnumIntImpl implements IAnnotationTestMethod {
    @SneakyThrows
    @Override
    public void testMethod(Method method, Field field, Annotation a, AnnotationServer at) {
        EnumInt annotation = (EnumInt) a;
        String des;
        for (int iEnum : annotation.value()) {
            des =
                    "类名:" + at.baseCase.getClass().getSimpleName() +
                            ",字段名:" + field.getName() +
                            ",int类型枚举遍历测试,传入：";
            at.fieldTest(method, field, iEnum, des + iEnum, annotation.asserts().newInstance(), annotation.resetAssert());
        }

    }
}
