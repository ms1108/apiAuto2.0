package annotation.impl;

import annotation.AnnotationServer;
import annotation.IAnnotationTestMethod;
import annotation.annotations.Chinese;
import lombok.SneakyThrows;
import utils.RandomUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ChineseDefaultImpl implements IAnnotationTestMethod {
    @SneakyThrows
    @Override
    public void testMethod(Method method, Field field, Annotation a, AnnotationServer at) {
        Chinese annotation = (Chinese) a;
        String des =
                "类名:" + at.baseCase.getClass().getSimpleName() +
                        ",字段名:" + field.getName() +
                        ",中文字符测试,传入中文值:";
        String value = RandomUtil.getChinese(annotation.chineseLen());
        at.fieldTest(method, field, value, des + value, annotation.asserts().newInstance(), annotation.resetAssert());

    }
}
