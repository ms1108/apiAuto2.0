package annotation.impl;

import annotation.AnnotationServer;
import annotation.AnnotationTestEntity;
import annotation.IAnnotationTestMethod;
import annotation.annotations.StringToInt;
import lombok.SneakyThrows;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class StringToIntDefault extends IAnnotationTestMethod {

    @SneakyThrows
    @Override
    public void testMethod(AnnotationTestEntity annotationTestEntity) {
        StringToInt annotation = (StringToInt) annotationTestEntity.annotation;
        String des =
                "类名:" + annotationTestEntity.baseCaseClass.getSimpleName() +
                        ",字段名:" + annotationTestEntity.field.getName() +
                        ",StringToInt类型测试,传入整形:";
        int value = isInteger(annotationTestEntity.field.get(annotationTestEntity.baseCaseData) + "") ? Integer.parseInt((String) annotationTestEntity.field.get(annotationTestEntity.baseCaseData)) : 1;
        annotationTestEntity.value = value;
        annotationTestEntity.des = des + value;
        commonBuild(annotationTestEntity, annotation.asserts().newInstance(), annotation.resetAssert());

    }
}
