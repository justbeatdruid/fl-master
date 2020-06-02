package com.cmcc.algo.common.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {


    /**
     * 替换字符串中的数字为“”
     *
     * @param str
     * @return
     */
    public static String replaceStrToNum(String str) {
//        Pattern p = Pattern.compile("[^0-9]"); // 替换非数字数字
        Pattern p2 = Pattern.compile("[0-9]"); // 替换数字
        Matcher m = p2.matcher(str);
        //替换与模式匹配的所有字符（即非数字的字符将被""替换）
        String trim = m.replaceAll("").trim();
        return trim;

    }

    /**
     * 字符串正则匹配
     *
     * @param str 字符串
     * @param regexp 正则表达式
     * @return
     */
    public static boolean checkString(String str, String regexp) {
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(str);
        boolean rs = matcher.matches();
        return rs;
    }


}


