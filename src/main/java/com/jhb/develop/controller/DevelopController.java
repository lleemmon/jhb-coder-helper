package com.jhb.develop.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jhb.common.result.Result;
import com.jhb.common.result.ResultCodeEnum;
import com.jhb.config.execption.MyException;
import com.jhb.develop.service.SysGeneratorFieldService;
import com.jhb.develop.vo.*;
import com.jhb.develop.entity.SysGeneratorTable;
import com.jhb.develop.service.DevelopService;
import com.jhb.developUtil.CodeGenerator;
import com.jhb.util.MyIdUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/develop")
public class DevelopController {
    @Resource
    private DevelopService developService;

    @Resource
    private SysGeneratorFieldService sysGeneratorFieldService;

    @PostMapping("/getSysGeneratorTableList")
    public Result<Page<SysGeneratorTable>> getTableNameSelection(
            @RequestBody SysGeneratorTableQueryVo queryVo
    ){
        Page<SysGeneratorTable> sysGeneratorTablePage = new Page<>(queryVo.getPage(), queryVo.getLimit());
        queryVo.setStart((queryVo.getPage() - 1) * queryVo.getLimit());
        List<SysGeneratorTable> list = developService.getSysGeneratorTableList(queryVo);
        sysGeneratorTablePage.setRecords(list);
        sysGeneratorTablePage.setTotal(list.size());
        return Result.ok(sysGeneratorTablePage);
    }

    private LambdaQueryWrapper<SysGeneratorTable> getSysGeneratorWrapper(EndGeneratorVo endGeneratorVo){
        LambdaQueryWrapper<SysGeneratorTable> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysGeneratorTable::getTableName, endGeneratorVo.getTableName());
        wrapper.eq(SysGeneratorTable::getIsDeleted, false);
        return wrapper;
    }

    @PostMapping("/insert")
    public Result<SysGeneratorTable> insert(
            @RequestBody EndGeneratorVo endGeneratorVo
    ){
        LambdaQueryWrapper<SysGeneratorTable> wrapper = getSysGeneratorWrapper(endGeneratorVo);
        int count = developService.count(wrapper);
        if(count > 0){
            throw new MyException(ResultCodeEnum.DATA_EXISTS_FAIL);
        }
        SysGeneratorTable sysGeneratorTable = new SysGeneratorTable();
        BeanUtils.copyProperties(endGeneratorVo, sysGeneratorTable);
        String id = MyIdUtil.nextIdStr();
        sysGeneratorTable.setId(id);
        sysGeneratorTable.setCreateTime(new Date());
        boolean isSuccess = developService.save(sysGeneratorTable);
        if(isSuccess){
            sysGeneratorTable.setInTable("1");
            return Result.ok(sysGeneratorTable);
        }else{
            throw new MyException(ResultCodeEnum.DATA_SAVE_FAIL);
        }
    }

    @PostMapping("/update")
    public Result<SysGeneratorTable> update(
            @RequestBody EndGeneratorVo endGeneratorVo
    ){
        LambdaQueryWrapper<SysGeneratorTable> wrapper = getSysGeneratorWrapper(endGeneratorVo);
        SysGeneratorTable sysGeneratorTable = developService.getOne(wrapper);
        BeanUtils.copyProperties(endGeneratorVo, sysGeneratorTable);
        sysGeneratorTable.setUpdateTime(new Date());
        boolean isSuccess = developService.updateById(sysGeneratorTable);
        if(isSuccess){
            sysGeneratorTable.setInTable("1");
            return Result.ok(sysGeneratorTable);
        }else{
            throw new MyException(ResultCodeEnum.DATA_SAVE_FAIL);
        }
    }

    @PostMapping("/endGenerator")
    public Result<SysGeneratorTable> endGenerator(
            @RequestBody EndGeneratorVo endGeneratorVo
    ) {
        String moduleName = endGeneratorVo.getModuleName();
        String author = endGeneratorVo.getAuthor();
        String tableName = endGeneratorVo.getTableName();
        CodeGenerator.execute(moduleName, author, tableName);
        developService.endGenerator(endGeneratorVo);
        LambdaQueryWrapper<SysGeneratorTable> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysGeneratorTable::getTableName, tableName);
        wrapper.eq(SysGeneratorTable::getIsDeleted, false);
        SysGeneratorTable sysGeneratorTable = developService.getOne(wrapper);
        sysGeneratorTable.setInTable("1");
        return Result.ok(sysGeneratorTable);
    }

    @GetMapping("/getAutoCompleteForModule")
    public Result<List<AutoCompleteVo>> getAutoCompleteForModule() {
        return Result.ok(developService.getAutoCompleteForModule());
    }

    @GetMapping("/getElementComponentSelection")
    public Result<List<SelectVo<String, String>>> getElementComponentSelection() {
        return Result.ok(developService.getElementComponentSelection());
    }

    @GetMapping("/getMysqlFieldSelection")
    public Result<List<SelectVo<String, String>>> getMysqlFieldSelection() {
        return Result.ok(developService.getMysqlFieldSelection());
    }

    @PostMapping("/getTableFieldList")
    public Result<List<TableFieldVo>> getTableFieldList(String tableName) {
        return Result.ok(developService.getTableFieldList(tableName));
    }

    @PostMapping("/saveGeneratorConfig")
    public Result<String> saveGeneratorConfig(@RequestBody FrontGeneratorVo frontGeneratorVo){
        boolean isSuccess = sysGeneratorFieldService.doSaveGeneratorConfig(frontGeneratorVo);
        if(isSuccess){
            return Result.ok("success");
        }else{
            throw new MyException(ResultCodeEnum.DATA_SAVE_FAIL);
        }
    }

    @PostMapping("/frontGenerator")
    public Result<String> frontGenerator(@RequestBody FrontGeneratorVo frontGeneratorVo){
        String tableName = frontGeneratorVo.getTableName();
        String moduleName = frontGeneratorVo.getModuleName();
        developService.frontGenerator(tableName, moduleName);
        return Result.ok("success");
    }
}
