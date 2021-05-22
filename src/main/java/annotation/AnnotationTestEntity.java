package annotation;

import base.BaseCase;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AnnotationTestEntity {
    public Field file;
    public Method dataDependMethod;
    public Method baseCaseDataMethod;
    public Class<? extends BaseCase> baseCaseClass;
    public Annotation annotation;
    public boolean runDataDependMethod;
    public Method methodName;

}
