package annotation.impl;

import annotation.AnnotationServer;
import annotation.AnnotationTestEntity;
import annotation.IAnnotationTestMethod;
import annotation.annotations.EnumInt;
import annotation.annotations.EnumString;
import lombok.SneakyThrows;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class EnumStringDefault extends IAnnotationTestMethod {

    @SneakyThrows
    @Override
    public void testMethod(AnnotationTestEntity annotationTestEntity) {
        annotation.annotations.EnumString annotation = (annotation.annotations.EnumString) annotationTestEntity.annotation;
        String des;
        for (String iEnum : annotation.value()) {
            des =
                    "类名:" + annotationTestEntity.baseCaseClass.getSimpleName() +
                            ",字段名:" + annotationTestEntity.field.getName() +
                            ",String类型枚举遍历测试,传入：";
            annotationTestEntity.value = iEnum;
            annotationTestEntity.des = des + iEnum;
            commonBuild(annotationTestEntity, annotation.asserts().newInstance(), annotation.resetAssert());
        }
    }
}
