package com.jhb.develop.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jhb.common.result.Result;
import com.jhb.common.result.ResultCodeEnum;
import com.jhb.config.execption.MyException;
import com.jhb.develop.entity.SysDict;
import com.jhb.develop.service.SysDictService;
import com.jhb.develop.vo.SelectVo;
import com.jhb.develop.vo.SysDictVo;
import com.jhb.util.MyIdUtil;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 江辉彬
 * @since Mon Apr 10 22:07:48 CST 2023
 */
@RestController
@RequestMapping("/develop/sys-dict")
public class SysDictController {
    @Resource
    private SysDictService sysDictService;

    @GetMapping("/get/{id}")
    public Result<SysDict> get(@PathVariable String id){
        return Result.ok(sysDictService.getById(id));
    }

    @PostMapping("/insert")
    public Result<String> insert(@RequestBody SysDict sysDict){
        String id = MyIdUtil.nextIdStr();
        sysDict.setCreateTime(LocalDateTime.now());
        sysDict.setId(id);
        sysDict.setIsDeleted(false);
        boolean isSuccess = sysDictService.save(sysDict);
        if(isSuccess){
            return Result.ok(id);
        }else{
            throw new MyException(ResultCodeEnum.DATA_SAVE_FAIL);
        }
    }

    @PostMapping("/update")
    public Result<String> update(@RequestBody SysDict sysDict){
        sysDict.setUpdateTime(LocalDateTime.now());
        boolean isSuccess = sysDictService.updateById(sysDict);
        if(isSuccess){
            return Result.ok("修改成功");
        }else{
            throw new MyException(ResultCodeEnum.DATA_SAVE_FAIL);
        }
    }

    @PostMapping("/delete")
    public Result<String> delete(String id){
        boolean isSuccess = sysDictService.removeById(id);
        if(isSuccess){
            return Result.ok("删除成功");
        }else{
            throw new MyException(ResultCodeEnum.DATA_DELETE_FAIL);
        }
    }

    @PostMapping("/page")
    public Result<Page<SysDict>> page(@RequestBody SysDictVo sysDictVo){
        Page<SysDict> sysDictPage = new Page<>(sysDictVo.getPage(), sysDictVo.getLimit());
        LambdaQueryWrapper<SysDict>wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDict::getIsDeleted, false);
        if(StrUtil.isNotBlank(sysDictVo.getType())){
            wrapper.eq(SysDict::getType, sysDictVo.getType());
        }
        if(StrUtil.isNotBlank(sysDictVo.getDescRibe())){
            wrapper.eq(SysDict::getDescRibe, sysDictVo.getDescRibe());
        }
        return Result.ok(sysDictService.page(sysDictPage, wrapper));
    }

    @GetMapping("/selection")
    public Result<List<SelectVo<String, String>>> selection(){
        List<SelectVo<String, String>>list = new ArrayList<>();
        LambdaQueryWrapper<SysDict> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(SysDict::getType, SysDict::getDescRibe);
        wrapper.eq(SysDict::getIsDeleted, "0");
        List<SysDict> sysDictList = sysDictService.list(wrapper);
        sysDictList.forEach(sysDict -> {
            SelectVo<String, String> selectVo = new SelectVo<>();
            selectVo.setValue(sysDict.getType());
            selectVo.setLabel(sysDict.getDescRibe());
            list.add(selectVo);
        });
        return Result.ok(list);
    }
}