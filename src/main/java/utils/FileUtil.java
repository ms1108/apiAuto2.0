package utils;

import java.io.*;

import static base.DataStore.DownloadDir;

public class FileUtil {

    public static boolean writeFile(InputStream is, String contentPath) {
        File file = new File(contentPath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        InputStreamReader isr = null;
        FileOutputStream fileout = null;
        OutputStreamWriter oStreamWriter = null;
        try {
            isr = new InputStreamReader(is, "UTF-8");
            fileout = new FileOutputStream(file);
            oStreamWriter = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.print("==========" + e.toString());
            return false;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        /**
         * 根据实际运行效果 设置缓冲区大小
         */
        char[] buffer = new char[10 * 1024];
        int ch = 0;
        try {
            while ((ch = isr.read(buffer)) != -1) {
                //fileout.write(buffer, 0, ch);
                oStreamWriter.write(buffer, 0, ch);
            }
            return true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } finally {
            try {
                oStreamWriter.flush();
                isr.close();
                is.close();
                fileout.close();
                oStreamWriter.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static String getNewestFileContentPath() {
        return getNewestFileContentPath(DownloadDir);
    }

    public static String getNewestFileContentPath(String contentPath) {
        File file = new File(contentPath);
        if (file != null && file.isDirectory()) {
            File[] list = file.listFiles();
            int size = list.length;
            if (size > 0) {
                String currentFileName = list[0].getName();
                long lastModify = list[0].lastModified();

                for (int i = 1; i < size; ++i) {
                    File currentFile = list[i];
                    if (currentFile.lastModified() > lastModify) {
                        currentFileName = currentFile.getName();
                        lastModify = currentFile.lastModified();
                    }
                }
                return contentPath + "/" + currentFileName;
            }
        }
        return "";
    }
}
