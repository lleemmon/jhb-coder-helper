package com.jhb.common.result;

import lombok.Getter;

@Getter
public enum ResultCodeEnum {
    SUCCESS(200, "成功"),
    FAIL(501, "失败"),
    DATA_EXISTS_FAIL(502, "数据已存在"),
    DATA_SAVE_FAIL(503, "数据保存失败"),
    ITEM_NOT_FIND_FAIL(504, "数据不存在"),
    DATA_DELETE_FAIL(505, "数据删除失败"),
    PATH_IS_NOT_DIR_FAIL(506, "路径不是一个文件夹"),
    CODE_GENERATOR_FAIL(507, "代码生成失败");

    private Integer code;
    private String message;
    ResultCodeEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
