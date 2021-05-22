package utils;

import org.testng.Reporter;

import java.util.ArrayList;
import java.util.List;

public class ReportUtil {
    //用于存储log
    private static List<String> logs;

    public static void log(String msg) {
        Reporter.log(msg, true);
    }

    public static void log(String msg, boolean isPrint) {
        Reporter.log(msg, isPrint);
    }

    //不知道某接口是否调用，所以先预制存储日志
    public static void setPreLog(String log) {
        logs.add(log);
    }

    //接口调用前调用前将预制日志打印出来
    public static void printLog() {
        if (logs != null && logs.size() > 0) {
            for (String log : logs) {
                log(log);
            }
        }
    }

    public static void clearLogs() {
        logs = new ArrayList<>();
    }

    public static void deleteLog(int i) {
        if (logs.size() - 1 >= i) {
            logs.remove(i);
        }
    }

    public static void deleteLog(String log) {
        if (logs.contains(log)) {
            deleteLog(logs.indexOf(log));
        }
    }
}
