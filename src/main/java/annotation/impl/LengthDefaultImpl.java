package annotation.impl;

import annotation.AnnotationServer;
import annotation.IAnnotationTestMethod;
import annotation.annotations.Length;
import lombok.SneakyThrows;
import utils.RandomUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class LengthDefaultImpl implements IAnnotationTestMethod {
    @SneakyThrows
    @Override
    public void testMethod(Method method, Field field, Annotation a, AnnotationServer at) {
        Length annotation = (Length) a;
        int minLen = annotation.minLen();
        int maxLen = annotation.maxLen();
        String des =
                "类名:" + at.baseCase.getClass().getSimpleName() +
                        ",字段名:" + field.getName() +
                        ",期望长度范围:" + minLen + "-" + maxLen +
                        ",传入值长度:";
        at.fieldTest(method, field, RandomUtil.getString(minLen), des + minLen, annotation.assertSuccess().newInstance(), annotation.resetAssert());
        at.fieldTest(method, field, RandomUtil.getString(maxLen), des + maxLen, annotation.assertSuccess().newInstance(), annotation.resetAssert());
        if (minLen != 1) {
            at.fieldTest(method, field, RandomUtil.getString(minLen - 1), des + (minLen - 1), annotation.assertFail().newInstance(), annotation.resetAssert());
        }
        at.fieldTest(method, field, RandomUtil.getString(maxLen + 1), des + (maxLen + 1), annotation.assertFail().newInstance(), annotation.resetAssert());

    }
}
