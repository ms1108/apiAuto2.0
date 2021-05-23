package annotation.impl;

import annotation.AnnotationServer;
import annotation.AnnotationTestEntity;
import annotation.IAnnotationTestMethod;
import annotation.annotations.NotNull;
import lombok.SneakyThrows;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class NotNullDefault extends IAnnotationTestMethod {

    @SneakyThrows
    @Override
    public void testMethod(AnnotationTestEntity annotationTestEntity) {
        NotNull annotation = (NotNull) annotationTestEntity.annotation;
        String des =
                "类名:" + annotationTestEntity.baseCaseClass.getSimpleName() +
                        ",字段名:" + annotationTestEntity.field.getName() +
                        ",输入null值校验";
        annotationTestEntity.value = null;
        annotationTestEntity.des = des;
        commonBuild(annotationTestEntity, annotation.asserts().newInstance(), annotation.resetAssert());

    }
}
