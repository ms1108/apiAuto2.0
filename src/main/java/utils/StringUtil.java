package utils;

public class StringUtil {
    public static boolean isNotEmpty(String str) {
        return null != str && !"".equals(str);
    }

    public static boolean isEmpty(String str) {
        return null == str || "".equals(str);
    }
}
