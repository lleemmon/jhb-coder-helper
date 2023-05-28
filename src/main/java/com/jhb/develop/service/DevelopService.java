package com.jhb.develop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jhb.develop.entity.SysGeneratorTable;
import com.jhb.develop.vo.*;

import java.util.List;

public interface DevelopService extends IService<SysGeneratorTable> {
    List<SelectVo<String, String>> getElementComponentSelection();
    List<SysGeneratorTable> getSysGeneratorTableList(SysGeneratorTableQueryVo queryVo);

    void endGenerator(EndGeneratorVo endGeneratorVo);
    void frontGenerator(String tableName, String moduleName);
    List<AutoCompleteVo>getAutoCompleteForModule();
    List<TableFieldVo> getTableFieldList(String tableName);

    List<TableFieldVo> getTableFieldListForJava(String tableName);

    List<SelectVo<String, String>> getMysqlFieldSelection();

    void processTableFieldForGenerator(List<TableFieldVo> tableFieldVoList);
}
