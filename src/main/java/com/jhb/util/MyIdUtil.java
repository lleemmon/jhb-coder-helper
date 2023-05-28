package com.jhb.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

public class MyIdUtil {
    private static Snowflake snowflake;

    static {
        snowflake = IdUtil.getSnowflake(1, 1);
    }

    public static long nextId(){
        return snowflake.nextId();
    }

    public static String nextIdStr(){
        return snowflake.nextIdStr();
    }
}
