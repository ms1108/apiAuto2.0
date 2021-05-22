package datafactory;

import base.BaseCase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataFactoryEntity {
    private Class<? extends BaseCase> baseCase;
    private Method dependMethod;
    private Method dataFactoryMethod;
    private Annotation annotationInfo;
}
