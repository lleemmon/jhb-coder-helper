package com.jhb.util;

import java.util.HashMap;
import java.util.Map;

public class MyStrUtil {

    public static String _aToA(String str){
        StringBuilder sb = new StringBuilder(str);
        int length = sb.length();
        for(int i = 0;i < length;i++){
            if(sb.charAt(i) == '_'){
                if(i + 1 < length){
                    int charAsc = sb.charAt(i + 1);
                    if(charAsc >= 'a' && charAsc <= 'z'){
                        charAsc -= 32;
                    }
                    sb.setCharAt(i + 1, (char)charAsc);
                }
            }
        }
        String s = sb.toString();
        s = s.replace("_", "");
        return s;
    }

    public static String ab_cToAbC(String str){
        str = _aToA(str);
        StringBuilder sb = new StringBuilder(str);
        if(sb.length() > 0){
            int charAsc = sb.charAt(0);
            if(charAsc >= 'a' && charAsc <= 'z'){
                sb.setCharAt(0, (char)(charAsc - 32));
            }
        }
        return sb.toString();
    }

    //"_" 转 "-"
    public static String ab_cToab__c(String str){
        return str.replace('_', '-');
    }

    //IS_DELETE
    public static String I_DCToiDc(String str){
        str = str.toLowerCase();
        return _aToA(str);
    }
}
