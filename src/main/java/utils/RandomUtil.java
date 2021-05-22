package utils;

import java.util.Random;

public class RandomUtil {

    public static String getString() {
        return getString(10);
    }

    //随机相关
    public static String getString(Integer length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; ++i) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static int getInt() {
        return getInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static int getInt(int max) {
        return getInt(0, max);
    }

    /**
     * 获取min到max内的随机一位数，[min,max]
     */
    public static int getInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }

    public static String getPhone() {
        String[] PhoneFirst = {"186", "187", "179", "178", "138", "139", "158", "159"};
        String PhoneEnd = String.valueOf(((int) ((Math.random() * 9) * 10000000)));
        String RandomFirst = PhoneFirst[(int) (Math.random() * 8)];
        return RandomFirst + PhoneEnd;
    }

    public static String getChinese() {
        return getChinese(5);
    }

    public static String getChinese(int len) {
        String chinese = "";
        StringBuilder sb = new StringBuilder(chinese);
        for (int i = 0; i < len; i++) {
            sb.append(new String(new char[]{(char) (new Random().nextInt(20902) + 19968)}));
        }
        return sb.toString();
    }


}
