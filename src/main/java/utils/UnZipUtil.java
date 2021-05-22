package utils;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.testng.Assert;

import java.io.File;


public class UnZipUtil {

    public static boolean unZip(String source) {
        return unZip(source, "");
    }

    public static boolean unZip(String source, String password) {
        //解压到当前目录
        String unZipPath = source.substring(0, source.lastIndexOf(File.separator));
        return unZip(source, unZipPath, password);
    }

    public static boolean unZip(String source, String unZipPath, String password) {
        if (!source.endsWith("zip")) {
            Assert.fail("需要解压的文件不是以zip结尾的，当前传入的文件：" + source);
        }
        try {
            File zipFile = new File(source);

            ZipFile zFile = new ZipFile(zipFile); // 首先创建ZipFile指向磁盘上的.zip文件

            zFile.setFileNameCharset("UTF-8");

            File destDir = new File(unZipPath); // 解压目录
            if (!destDir.exists()) {// 目标目录不存在时，创建该文件夹
                destDir.mkdirs();
            }
            if (zFile.isEncrypted()) {
                if (StringUtil.isEmpty(password)) {
                    return false;
                }
                zFile.setPassword(password.toCharArray()); // 输入密码
            }

            zFile.extractAll(unZipPath); // 将文件抽出到解压目录(解压)

            return true;

        } catch (ZipException e) {
            e.printStackTrace();
            Assert.fail("解压失败");
            return false;
        }
    }
}
