package com.jhb.develop.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jhb.common.result.Result;
import com.jhb.common.result.ResultCodeEnum;
import com.jhb.config.execption.MyException;
import com.jhb.develop.entity.SysGeneratorField;
import com.jhb.develop.service.SysGeneratorFieldService;
import com.jhb.develop.vo.SysGeneratorFieldVo;
import com.jhb.util.MyIdUtil;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 江辉彬
 * @since Sun Apr 09 02:15:23 CST 2023
 */
@RestController
@RequestMapping("/develop/sys-generator-field")
public class SysGeneratorFieldController {
    @Resource
    private SysGeneratorFieldService sysGeneratorFieldService;

    @GetMapping("/get/{id}")
    public Result<SysGeneratorField> get(@PathVariable String id){
        return Result.ok(sysGeneratorFieldService.getById(id));
    }

    @PostMapping("/insert")
    public Result<String> insert(@RequestBody SysGeneratorField sysGeneratorField){
        String id = MyIdUtil.nextIdStr();
        sysGeneratorField.setCreateTime(LocalDateTime.now());
        sysGeneratorField.setId(id);
        sysGeneratorField.setIsDeleted(false);
        boolean isSuccess = sysGeneratorFieldService.save(sysGeneratorField);
        if(isSuccess){
            return Result.ok(id);
        }else{
            throw new MyException(ResultCodeEnum.DATA_SAVE_FAIL);
        }
    }

    @PostMapping("/update")
    public Result<String> update(@RequestBody SysGeneratorField sysGeneratorField){
        sysGeneratorField.setUpdateTime(LocalDateTime.now());
        boolean isSuccess = sysGeneratorFieldService.updateById(sysGeneratorField);
        if(isSuccess){
            return Result.ok("修改成功");
        }else{
            throw new MyException(ResultCodeEnum.DATA_SAVE_FAIL);
        }
    }

    @PostMapping("/delete")
    public Result<String> delete(String id){
        boolean isSuccess = sysGeneratorFieldService.removeById(id);
        if(isSuccess){
            return Result.ok("删除成功");
        }else{
            throw new MyException(ResultCodeEnum.DATA_DELETE_FAIL);
        }
    }

    @PostMapping("/page")
    public Result<Page<SysGeneratorField>> page(@RequestBody SysGeneratorFieldVo sysGeneratorFieldVo){
        Page<SysGeneratorField> sysGeneratorFieldPage = new Page<>(sysGeneratorFieldVo.getPage(), sysGeneratorFieldVo.getLimit());
        return Result.ok(sysGeneratorFieldService.page(sysGeneratorFieldPage));
    }
}