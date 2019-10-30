package com.jari.SimpleUsbSerialExample;

/**
 * 版      权 :  jariec.com
 * 包      名 : com.jariec.signal.common.util.encrypt.hex
 * 描      述 :  HexUtils
 * 创建 时 间:  2018/12/10
 * <p>
 *
 * @author :  方超
 */
public class HexUtils {

    private HexUtils() {
    }

    private static final String HEX_CHARS = "0123456789abcdef";

    /**
     * 字符串 字节数数组 转换成16进制字符串  <br/>
     * <p>
     * 创建人：     方超
     * 创建日期：  2018-12-10
     * 描述    :     16进制转String
     *
     * @param byets 字符串字节数数组
     * @return String 16进制字符串
     */
    public static String toHexString(byte[] byets) {
        StringBuilder sb = new StringBuilder();
        for (byte aB : byets) {
            sb.append(HexUtils.HEX_CHARS.charAt(aB >>> 4 & 0x0F));
            sb.append(HexUtils.HEX_CHARS.charAt(aB & 0x0F));
        }
        return sb.toString();
    }

    /**
     * 16进制字符串 转换成 string 字节数组  <br/>
     * <p>
     * 创建人：     方超
     * 创建日期：  2018-12-10
     * 描述    :     16进制字符串 转换成 string 字节数组
     *
     * @param s 16进制字符串
     * @return byte[] string 字节数组
     */
    public static byte[] toByteArray(String s) {
        byte[] buf = new byte[s.length() / 2];
        int j = 0;
        for (int i = 0; i < buf.length; i++) {
            buf[i] = (byte) ((Character.digit(s.charAt(j++), 16) << 4) | Character
                    .digit(s.charAt(j++), 16));
        }
        return buf;
    }


}
