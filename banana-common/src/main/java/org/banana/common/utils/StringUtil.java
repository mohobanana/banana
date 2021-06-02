package org.banana.common.utils;

public class StringUtil {

    public static String toCamel(String str) {
        if (str.contains("_") || str.toUpperCase().equals(str)) {
            return underlineToCamel(str);
        } else {
            return str;
        }
    }

    private static String underlineToCamel(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '_' && i != str.length() - 1) {
                i++;
                sb.append(Character.toUpperCase(str.charAt(i)));
            } else {
                sb.append(Character.toLowerCase(str.charAt(i)));
            }
        }
        return sb.toString();
    }
}
