package annotation.impl;

import annotation.AnnotationServer;
import annotation.AnnotationTestEntity;
import annotation.IAnnotationTestMethod;
import annotation.annotations.Unique;
import lombok.SneakyThrows;
import utils.RandomUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class UniqueDefault extends IAnnotationTestMethod {

    @SneakyThrows
    @Override
    public void testMethod(AnnotationTestEntity annotationTestEntity) {
        Unique annotation = (Unique) annotationTestEntity.annotation;
        String des =
                "类名:" + annotationTestEntity.baseCaseClass.getSimpleName() +
                        ",字段名:" + annotationTestEntity.field.getName() +
                        ",唯一性校验";
        String uniqueRandom = "Unique" + RandomUtil.getString(8);
        annotationTestEntity.value = uniqueRandom;
        annotationTestEntity.des = des + ",数据准备";
        commonBuild(annotationTestEntity, annotation.assertSuccess().newInstance(), annotation.resetAssert());

        annotationTestEntity.value = uniqueRandom;
        annotationTestEntity.des = des + ",数据已存在,期望创建失败";
        commonBuild(annotationTestEntity, annotation.assertFail().newInstance(), annotation.resetAssert());

        annotationTestEntity.value = uniqueRandom;
        annotationTestEntity.des = des + ",首末尾加上空格,校验后端去除了空格,期望创建失败";
        commonBuild(annotationTestEntity, annotation.assertFail().newInstance(), annotation.resetAssert());

    }
}
