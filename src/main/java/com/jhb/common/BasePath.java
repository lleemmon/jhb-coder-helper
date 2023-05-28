package com.jhb.common;

import com.jhb.MainApplication;
import com.jhb.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class BasePath {
    public static String ROOT_DIR;//C:\Users\57443\JavaCode\jhb-code-helper
    public static String ROOT_FULL_DIR;//C:\Users\57443\JavaCode\jhb-code-helper\src\main\java
    public static String ROOT_RESOURCE_DIR;//C:\Users\57443\JavaCode\jhb-code-helper\target\classes\com\jhb
    public static String PARENT_PACKAGE_DIR;// com.jhb
    public static String FULL_PARENT_PACKAGE_DIR;//C:\Users\57443\JavaCode\jhb-code-helper\src\main\java\com\jhb
    public static List<String> IGNORED_FILE_NAME_LIST;//[common, config, developUtil, util]
    public static String TEMPLATE_ROOT_RESOURCE_PATH;//C:/Users/57443/JavaCode/jhb-code-helper/target/classes/template

    static {
        ROOT_DIR = new File("").getAbsolutePath();
        ROOT_FULL_DIR = ROOT_DIR + "\\src\\main\\java";
        ROOT_RESOURCE_DIR = Objects.requireNonNull(MainApplication.class.getResource("")).getPath();
        ROOT_RESOURCE_DIR = ROOT_RESOURCE_DIR.substring(1, ROOT_RESOURCE_DIR.length() - 1).replace('/', '\\');
        PARENT_PACKAGE_DIR = ROOT_RESOURCE_DIR.replace(ROOT_DIR, "")
                .replace("\\target\\classes\\", "");
        FULL_PARENT_PACKAGE_DIR = ROOT_FULL_DIR + "\\" + PARENT_PACKAGE_DIR;
        PARENT_PACKAGE_DIR = PARENT_PACKAGE_DIR.replace("\\", ".");
        IGNORED_FILE_NAME_LIST = Arrays.asList("common", "config", "developUtil", "util");
        String rootResource = "/template";
        TEMPLATE_ROOT_RESOURCE_PATH = Objects.requireNonNull(BasePath.class.getResource(rootResource)).getPath().substring(1);
    }
}
