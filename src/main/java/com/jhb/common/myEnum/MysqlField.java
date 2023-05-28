package com.jhb.common.myEnum;

import com.jhb.develop.vo.SelectVo;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public enum MysqlField {
    BIT("bit", "比特"),
    VARCHAR("varchar", "不定长文本"),
    DATE("date",  "日期"),
    DATE_TIME("datetime", "日期时间"),
    INT("int", "整形"),
    BIGINT("bigint", "长整形");
    private static final Map<String, MysqlField>columnTypeToMysqlFieldMap = new HashMap<>();

    private static final List<SelectVo<String, String>> selectVoList = new ArrayList<>();

    static {
        for (MysqlField mysqlField : MysqlField.values()) {
            SelectVo<String, String> selectVo = new SelectVo<>();
            selectVo.setLabel(mysqlField.getColumnTypeName());
            selectVo.setValue(mysqlField.getColumnType());
            selectVoList.add(selectVo);
        }
        for (MysqlField mysqlField : MysqlField.values()) {
            columnTypeToMysqlFieldMap.put(mysqlField.columnType, mysqlField);
        }
    }

    private final String columnTypeName;
    private final String columnType;

    MysqlField(String columnType, String columnTypeName){
        this.columnType = columnType;
        this.columnTypeName = columnTypeName;
    }

    public static MysqlField getByColumnType(String columnType){
        return columnTypeToMysqlFieldMap.get(columnType);
    }

    public static List<SelectVo<String, String>> getSelectVoList(){
        return selectVoList;
    }
}
