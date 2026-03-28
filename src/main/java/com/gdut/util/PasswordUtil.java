package com.gdut.util;

public class PasswordUtil {

    /**
     * 异或加密
     * @param password 原始密码
     * @return 加密后的密码
     */
    public static String encode(String password) {
        if (password == null || password.isEmpty()) {
            return password;
        }

        char[] chars = password.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) ( chars[i] ^2);
        }
        return new String(chars);
    }

    /**
     * 异或解密
     * @param encrypted 加密后的密码
     * @return 解密后的原始密码
     */
    public static String decode(String encrypted) {
        //在用一次变回原来的数
        return encode(encrypted);
    }

}
