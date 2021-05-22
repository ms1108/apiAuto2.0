package annotation.impl;

import annotation.AnnotationServer;
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
public class RangeDefaultImpl implements IAnnotationTestMethod {

    @SneakyThrows
    @Override
    public void testMethod(Method method, Field field, Annotation a, AnnotationServer at) {
        Range annotation = (Range) a;
        BigDecimal minNum = new BigDecimal(annotation.minNum());
        BigDecimal maxNum = new BigDecimal(annotation.maxNum());
        BigDecimal floatValue = new BigDecimal(annotation.floatValue());
        String des =
                "类名:" + at.baseCase.getClass().getSimpleName() +
                        ",字段名:" + field.getName() +
                        ",期望大小范围:" + minNum + "-" + maxNum +
                        ",传入值:";
        if (annotation.minInfinite()) {
            minNum = new BigDecimal(RandomUtil.getInt(Integer.MIN_VALUE, maxNum.intValue()) + "");
        } else {
            BigDecimal subtract = minNum.subtract(floatValue);
            at.fieldTest(method, field, subtract, des + subtract, annotation.assertFail().newInstance(), annotation.resetAssert());
            if ("0.0".equals(subtract.toString())) {
                at.fieldTest(method, field, 0, des + 0, annotation.assertFail().newInstance(), annotation.resetAssert());
            }
        }
        at.fieldTest(method, field, minNum, des + minNum, annotation.assertSuccess().newInstance(), annotation.resetAssert());

        if (annotation.maxInfinite()) {
            maxNum = new BigDecimal(RandomUtil.getInt(minNum.intValue(), Integer.MAX_VALUE) + "");
        } else {
            at.fieldTest(method, field, maxNum.add(floatValue), des + maxNum.add(floatValue), annotation.assertFail().newInstance(), annotation.resetAssert());
        }
        at.fieldTest(method, field, maxNum, des + maxNum, annotation.assertSuccess().newInstance(), annotation.resetAssert());

    }
}
