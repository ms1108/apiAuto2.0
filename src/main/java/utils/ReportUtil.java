package utils;

import org.testng.Reporter;

import java.util.ArrayList;
import java.util.List;

public class ReportUtil {

    public static void log(String msg) {
        Reporter.log(msg, true);
    }

    //是否在控制台中打印
    public static void log(String msg, boolean isPrint) {
        Reporter.log(msg, isPrint);
    }
}
