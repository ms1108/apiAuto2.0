package annotation.impl;

import annotation.AnnotationServer;
import annotation.AnnotationTestEntity;
import annotation.IAnnotationTestMethod;
import annotation.annotations.Length;
import lombok.SneakyThrows;
import utils.RandomUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class LengthDefault extends IAnnotationTestMethod {

    @SneakyThrows
    @Override
    public void testMethod(AnnotationTestEntity annotationTestEntity) {
        Length annotation = (Length) annotationTestEntity.annotation;
        int minLen = annotation.minLen();
        int maxLen = annotation.maxLen();
        String des =
                "类名:" + annotationTestEntity.baseCaseClass.getSimpleName() +
                        ",字段名:" + annotationTestEntity.field.getName() +
                        ",期望长度范围:" + minLen + "-" + maxLen +
                        ",传入值长度:";
        annotationTestEntity.value = RandomUtil.getString(minLen);
        annotationTestEntity.des = des + minLen;
        commonBuild(annotationTestEntity, annotation.assertSuccess().newInstance(), annotation.resetAssert());

        annotationTestEntity.value = RandomUtil.getString(maxLen);
        annotationTestEntity.des = des + maxLen;
        commonBuild(annotationTestEntity, annotation.assertSuccess().newInstance(), annotation.resetAssert());

        if (minLen != 1) {
            annotationTestEntity.value = RandomUtil.getString(minLen - 1);
            annotationTestEntity.des = des + (minLen - 1);
            commonBuild(annotationTestEntity, annotation.assertFail().newInstance(), annotation.resetAssert());
        }
        annotationTestEntity.value = RandomUtil.getString(maxLen + 1);
        annotationTestEntity.des = des + (maxLen + 1);
        commonBuild(annotationTestEntity, annotation.assertFail().newInstance(), annotation.resetAssert());
    }
}
