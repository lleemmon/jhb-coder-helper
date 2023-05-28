package com.jhb.develop.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jhb.common.result.Result;
import com.jhb.common.result.ResultCodeEnum;
import com.jhb.config.execption.MyException;
import com.jhb.develop.entity.SysDictItems;
import com.jhb.develop.service.SysDictItemsService;
import com.jhb.develop.vo.SelectVo;
import com.jhb.develop.vo.SysDictItemsVo;
import com.jhb.util.MyIdUtil;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 江辉彬
 * @since Mon Apr 10 22:16:21 CST 2023
 */
@RestController
@RequestMapping("/develop/sys-dict-items")
public class SysDictItemsController {
    @Resource
    private SysDictItemsService sysDictItemsService;

    @GetMapping("/get/{id}")
    public Result<SysDictItems> get(@PathVariable String id){
        return Result.ok(sysDictItemsService.getById(id));
    }

    @PostMapping("/insert")
    public Result<String> insert(@RequestBody SysDictItems sysDictItems){
        String id = MyIdUtil.nextIdStr();
        sysDictItems.setCreateTime(LocalDateTime.now());
        sysDictItems.setId(id);
        sysDictItems.setIsDeleted(false);
        boolean isSuccess = sysDictItemsService.save(sysDictItems);
        if(isSuccess){
            return Result.ok(id);
        }else{
            throw new MyException(ResultCodeEnum.DATA_SAVE_FAIL);
        }
    }

    @PostMapping("/update")
    public Result<String> update(@RequestBody SysDictItems sysDictItems){
        sysDictItems.setUpdateTime(LocalDateTime.now());
        boolean isSuccess = sysDictItemsService.updateById(sysDictItems);
        if(isSuccess){
            return Result.ok("修改成功");
        }else{
            throw new MyException(ResultCodeEnum.DATA_SAVE_FAIL);
        }
    }

    @PostMapping("/delete")
    public Result<String> delete(String id){
        boolean isSuccess = sysDictItemsService.removeById(id);
        if(isSuccess){
            return Result.ok("删除成功");
        }else{
            throw new MyException(ResultCodeEnum.DATA_DELETE_FAIL);
        }
    }

    @PostMapping("/getByDictType")
    public Result<List<SelectVo<String, String>>> getByDictType(String dictType){
        List<SelectVo<String, String>> list = sysDictItemsService.getByDictType(dictType);
        return Result.ok(list);
    }

    @PostMapping("/page")
    public Result<Page<SysDictItems>> page(@RequestBody SysDictItemsVo sysDictItemsVo){
        Page<SysDictItems> sysDictItemsPage = new Page<>(sysDictItemsVo.getPage(), sysDictItemsVo.getLimit());
        LambdaQueryWrapper<SysDictItems>wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDictItems::getIsDeleted, false);
        if(StrUtil.isNotBlank(sysDictItemsVo.getLabel())){
            wrapper.eq(SysDictItems::getLabel, sysDictItemsVo.getLabel());
        }
        if(StrUtil.isNotBlank(sysDictItemsVo.getValue())){
            wrapper.eq(SysDictItems::getValue, sysDictItemsVo.getValue());
        }
        if(StrUtil.isNotBlank(sysDictItemsVo.getDictId())){
            wrapper.eq(SysDictItems::getDictId, sysDictItemsVo.getDictId());
        }
        return Result.ok(sysDictItemsService.page(sysDictItemsPage, wrapper));
    }
}