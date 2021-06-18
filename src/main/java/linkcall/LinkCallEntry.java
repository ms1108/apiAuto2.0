package linkcall;

import lombok.Data;

@Data
public class LinkCallEntry {
    private String className;
    private String packagePath;
    private String methodName;
    private String diyParam;
}
