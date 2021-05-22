package annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public interface IAnnotationTestMethod {
    void testMethod(Method method, Field field, Annotation a, AnnotationServer at);
}
