package annotation.impl;

import annotation.AnnotationServer;
import annotation.AnnotationTestEntity;
import annotation.IAnnotationTestMethod;
import annotation.annotations.NotEmpty;
import lombok.SneakyThrows;
import utils.RandomUtil;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class NotEmptyDefault extends IAnnotationTestMethod {

    @SneakyThrows
    @Override
    public void testMethod(AnnotationTestEntity annotationTestEntity) {
        NotEmpty annotation = (NotEmpty) annotationTestEntity.annotation;
        String des =
                "类名:" + annotationTestEntity.baseCaseClass.getSimpleName() +
                        ",字段名:" + annotationTestEntity.field.getName() +
                        ",空字符串校验";
        annotationTestEntity.value = "";
        annotationTestEntity.des = des;
        commonBuild(annotationTestEntity, annotation.asserts().newInstance(), annotation.resetAssert());
    }
}
