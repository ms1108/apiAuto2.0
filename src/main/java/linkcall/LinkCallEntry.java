package linkcall;

import lombok.Data;

@Data
public class LinkCallEntry {
    private String className;
    private String packagePath;
    private String methodName;
    private String diyParam;
    private String dataDependMethodName;
    private String clazzDes;
    private String methodDes;
    private String groupName;
    private String chainName;
}
