package com.jhb.develop.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jhb.common.BasePath;
import com.jhb.common.myEnum.ElementComponent;
import com.jhb.common.myEnum.MysqlField;
import com.jhb.common.result.ResultCodeEnum;
import com.jhb.config.execption.MyException;
import com.jhb.develop.mapper.DevelopMapper;
import com.jhb.develop.entity.SysGeneratorTable;
import com.jhb.develop.vo.*;
import com.jhb.develop.service.DevelopService;
import com.jhb.developUtil.CodeGenerator;
import com.jhb.util.ComponentFieldTransferUtil;
import com.jhb.util.JavaFieldTransferUtil;
import com.jhb.util.MyStrUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DevelopServiceImpl extends ServiceImpl<DevelopMapper, SysGeneratorTable> implements DevelopService {
    @Resource
    private DevelopMapper developMapper;

    @Override
    public List<SelectVo<String, String>> getElementComponentSelection() {
        return ElementComponent.getSelectVoList();
    }

    @Override
    public List<SysGeneratorTable> getSysGeneratorTableList(SysGeneratorTableQueryVo queryVo) {
        return developMapper.getSysGeneratorTableList(queryVo);
    }

    @Transactional
    @Override
    public void endGenerator(EndGeneratorVo endGeneratorVo) {
        LambdaQueryWrapper<SysGeneratorTable> wrapper = new LambdaQueryWrapper<>();
        String tableName = endGeneratorVo.getTableName();
        wrapper.eq(SysGeneratorTable::getTableName, tableName);
        wrapper.eq(SysGeneratorTable::getIsDeleted, false);
        SysGeneratorTable sysGeneratorTable = developMapper.selectOne(wrapper);
        if (sysGeneratorTable == null) {
            throw new MyException(ResultCodeEnum.ITEM_NOT_FIND_FAIL);
        }
        String author = endGeneratorVo.getAuthor();
        String moduleName = endGeneratorVo.getModuleName();
        String needCrudController = endGeneratorVo.getNeedCrudController();
        sysGeneratorTable.setIsGeneratorEnd("1");
        sysGeneratorTable.setInTable("1");
        sysGeneratorTable.setUpdateTime(new Date());
        sysGeneratorTable.setNeedCrudController(needCrudController);
        developMapper.updateById(sysGeneratorTable);
        //生成详细的crud模板
        if ("1".equals(needCrudController)) {
            try {
                List<TableFieldVo> tableColumns = getTableFieldListForJava(tableName);
                CodeGenerator.generatorCrudController(tableName, author, moduleName, tableColumns);
                String className = MyStrUtil.ab_cToAbC(tableName);
                CodeGenerator.generatorEntity(moduleName, className, tableColumns);
            } catch (IOException e) {
                throw new MyException(ResultCodeEnum.CODE_GENERATOR_FAIL);
            }
        }
    }

    @Override
    public void frontGenerator(String tableName, String moduleName) {
        List<TableFieldVo> tableFieldList = getTableFieldList(tableName);
        processTableFieldForGenerator(tableFieldList);
        try {
            CodeGenerator.generatorWebFile(tableFieldList, tableName, moduleName);
        } catch (IOException e) {
            e.printStackTrace();
            throw new MyException(ResultCodeEnum.CODE_GENERATOR_FAIL);
        }
        LambdaQueryWrapper<SysGeneratorTable> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysGeneratorTable::getTableName, tableName);
        wrapper.eq(SysGeneratorTable::getIsDeleted, false);
        SysGeneratorTable sysGeneratorTable = developMapper.selectOne(wrapper);
        if (sysGeneratorTable == null) {
            throw new MyException(ResultCodeEnum.ITEM_NOT_FIND_FAIL);
        }
        sysGeneratorTable.setIsGeneratorFront("1");
        sysGeneratorTable.setUpdateTime(new Date());
        developMapper.updateById(sysGeneratorTable);
    }

    @Override
    public List<AutoCompleteVo> getAutoCompleteForModule() {
        File rootFile = new File(BasePath.FULL_PARENT_PACKAGE_DIR);
        List<String> fileNameList = Arrays.stream(rootFile.listFiles()).map(item -> item.getAbsolutePath().replace(BasePath.FULL_PARENT_PACKAGE_DIR + "\\", ""))
                .filter(item -> !item.contains("."))
                .collect(Collectors.toList());
        fileNameList.removeAll(BasePath.IGNORED_FILE_NAME_LIST);
        List<AutoCompleteVo> result = new ArrayList<>();
        fileNameList.forEach(fileName -> {
            AutoCompleteVo autoCompleteVo = new AutoCompleteVo();
            autoCompleteVo.setLink(fileName);
            autoCompleteVo.setValue(fileName);
            result.add(autoCompleteVo);
        });
        return result;
    }

    @Override
    public List<TableFieldVo> getTableFieldList(String tableName) {
        List<TableFieldVo> tableFieldList = developMapper.getTableFieldList(tableName);
        for (TableFieldVo tableFieldVo : tableFieldList) {
            String columnName = tableFieldVo.getColumnName();
            tableFieldVo.setFieldName(MyStrUtil.I_DCToiDc(columnName));
//            tableFieldVo.setFieldNameUp(MyStrUtil.ab_cToAbC(tableFieldVo.getFieldName()));
            if (StrUtil.isBlank(tableFieldVo.getComponent())) {
                String dataType = tableFieldVo.getDataType();
                MysqlField mysqlField = MysqlField.getByColumnType(dataType);
                ElementComponent elementComponent = ComponentFieldTransferUtil.transferMysqlFieldToElementComponent(mysqlField);
                tableFieldVo.setComponent(elementComponent.getValue());
            }
            if (tableFieldVo.getNeedList() == null) {
                tableFieldVo.setNeedList("0");
            }
            if (tableFieldVo.getNeedWhere() == null) {
                tableFieldVo.setNeedWhere("0");
            }
            if (tableFieldVo.getNeedForm() == null) {
                tableFieldVo.setNeedForm("0");
            }
        }
        return tableFieldList;
    }

    @Override
    public List<TableFieldVo> getTableFieldListForJava(String tableName) {
        List<TableFieldVo> tableFieldList = developMapper.getTableFieldList(tableName);
        for (TableFieldVo tableFieldVo : tableFieldList) {
            String columnName = tableFieldVo.getColumnName();
            tableFieldVo.setFieldName(MyStrUtil.I_DCToiDc(columnName));
            tableFieldVo.setFieldNameUp(MyStrUtil.ab_cToAbC(tableFieldVo.getFieldName()));
            String dataType = tableFieldVo.getDataType();
            MysqlField mysqlField = MysqlField.getByColumnType(dataType);
            String javaType = JavaFieldTransferUtil.transferMysqlFieldToJavaField(mysqlField).getJavaType();
            tableFieldVo.setJavaType(javaType);
            if (tableFieldVo.getNeedList() == null) {
                tableFieldVo.setNeedList("0");
            }
            if (tableFieldVo.getNeedWhere() == null) {
                tableFieldVo.setNeedWhere("0");
            }
            if (tableFieldVo.getNeedForm() == null) {
                tableFieldVo.setNeedForm("0");
            }
        }
        return tableFieldList;
    }

    @Override
    public List<SelectVo<String, String>> getMysqlFieldSelection() {
        return MysqlField.getSelectVoList();
    }

    @Override
    public void processTableFieldForGenerator(List<TableFieldVo> tableFieldVoList) {
        tableFieldVoList.forEach(tableFieldVo -> {
            String value = tableFieldVo.getComponent();
            ElementComponent elementComponent = ElementComponent.getByValue(value);
            tableFieldVo.setComponent(elementComponent.getComponentType());
            if(StrUtil.isBlank(tableFieldVo.getType())){
                tableFieldVo.setType(elementComponent.getType());
            }
        });
    }
}
