package com.jhb.util;

import com.jhb.common.result.ResultCodeEnum;
import com.jhb.config.execption.MyException;

import java.io.File;

public class MyFileUtil {
    //如果不存在文件夹 新建文件夹
    public static Boolean checkAndMkdirs(String dirPath){
        File file = new File(dirPath);
        if(!file.exists()){
            return file.mkdirs();
        }else{
            return false;
        }
    }
}
