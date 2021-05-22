package annotation.impl;

import annotation.AnnotationServer;
import annotation.IAnnotationTestMethod;
import annotation.annotations.Unique;
import lombok.SneakyThrows;
import utils.RandomUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class UniqueDefaultImpl implements IAnnotationTestMethod {
    @SneakyThrows
    @Override
    public void testMethod(Method method, Field field, Annotation a, AnnotationServer at) {
        Unique annotation = (Unique) a;
        String des =
                "类名:" + at.baseCase.getClass().getSimpleName() +
                        ",字段名:" + field.getName() +
                        ",唯一性校验";
        String uniqueRandom = "Unique" + RandomUtil.getString(8);
        at.fieldTest(method, field, uniqueRandom, des + ",数据准备", annotation.assertSuccess().newInstance(), annotation.resetAssert());
        at.fieldTest(method, field, uniqueRandom, des + ",数据已存在,期望创建失败", annotation.assertFail().newInstance(), annotation.resetAssert());
        at.fieldTest(method, field, " " + uniqueRandom + " ", des + ",首末尾加上空格,校验后端去除了空格,期望创建失败", annotation.assertFail().newInstance(), annotation.resetAssert());
    }
}
