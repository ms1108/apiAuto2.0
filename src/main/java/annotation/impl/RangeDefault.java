package annotation.impl;

import annotation.AnnotationServer;
import annotation.AnnotationTestEntity;
import annotation.IAnnotationTestMethod;
import annotation.annotations.Range;
import lombok.SneakyThrows;
import utils.RandomUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;

/**
 * 数值范围[min,max]
 */
public class RangeDefault extends IAnnotationTestMethod {

    @SneakyThrows
    @Override
    public void testMethod(AnnotationTestEntity annotationTestEntity) {
        Range annotation = (Range) annotationTestEntity.annotation;
        BigDecimal minNum = new BigDecimal(annotation.minNum());
        BigDecimal maxNum = new BigDecimal(annotation.maxNum());
        BigDecimal floatValue = new BigDecimal(annotation.floatValue());
        String des =
                "类名:" + annotationTestEntity.baseCaseClass.getSimpleName() +
                        ",字段名:" + annotationTestEntity.field.getName() +
                        ",期望大小范围:" + minNum + "-" + maxNum +
                        ",传入值:";
        if (annotation.minInfinite()) {
            minNum = new BigDecimal(RandomUtil.getInt(Integer.MIN_VALUE, maxNum.intValue()) + "");
        } else {
            BigDecimal subtract = minNum.subtract(floatValue);
            annotationTestEntity.value = subtract;
            annotationTestEntity.des = des + subtract;
            commonBuild(annotationTestEntity, annotation.assertFail().newInstance(), annotation.resetAssert());

            if ("0.0".equals(subtract.toString())) {
                annotationTestEntity.value = 0;
                annotationTestEntity.des = des + 0;
                commonBuild(annotationTestEntity, annotation.assertFail().newInstance(), annotation.resetAssert());
            }
        }
        annotationTestEntity.value = minNum;
        annotationTestEntity.des = des + minNum;
        commonBuild(annotationTestEntity, annotation.assertSuccess().newInstance(), annotation.resetAssert());

        if (annotation.maxInfinite()) {
            maxNum = new BigDecimal(RandomUtil.getInt(minNum.intValue(), Integer.MAX_VALUE) + "");
        } else {
            annotationTestEntity.value = maxNum.add(floatValue);
            annotationTestEntity.des = des + maxNum.add(floatValue);
            commonBuild(annotationTestEntity, annotation.assertFail().newInstance(), annotation.resetAssert());
        }
        annotationTestEntity.value = maxNum;
        annotationTestEntity.des = des + maxNum;
        commonBuild(annotationTestEntity, annotation.assertFail().newInstance(), annotation.resetAssert());

    }
}
