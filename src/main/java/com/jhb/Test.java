package com.jhb;

import com.baomidou.mybatisplus.annotation.TableField;
import com.jhb.common.BasePath;
import com.jhb.common.myEnum.MysqlField;
import com.jhb.develop.entity.SysDict;
import com.jhb.develop.vo.TableFieldVo;
import com.jhb.util.ComponentFieldTransferUtil;
import com.jhb.util.MyFileUtil;
import com.jhb.util.MyStrUtil;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;

public class Test {
    public static void main(String[] args) throws IllegalAccessException, IOException {
        //前端js代码生成

        System.out.println(MyFileUtil.checkAndMkdirs("C:\\Users\\57443\\VueCode\\jhb-code-helper\\src\\api\\develop\\sysDict"));

//        Class<SysDictVo> sysDictClass = SysDictVo.class;
//        SysDictVo sysDictVo = new SysDictVo();
//        sysDictVo.setLimit(1);
//        sysDictVo.setPage(2);
//
//        for (Field declaredField : sysDictClass.getDeclaredFields()) {
//            declaredField.setAccessible(true);
//            Integer o = (Integer)declaredField.get(sysDictVo);
//            System.out.println(o);
//        }

//        System.out.println(BasePath.ROOT_DIR);
//        System.out.println(BasePath.ROOT_FULL_DIR);
//        System.out.println(BasePath.ROOT_RESOURCE_DIR);
//        System.out.println(BasePath.PARENT_PACKAGE_DIR);
//        System.out.println(BasePath.FULL_PARENT_PACKAGE_DIR);
//        System.out.println(BasePath.IGNORED_FILE_NAME_LIST);
//        System.out.println(BasePath.TEMPLATE_ROOT_RESOURCE_PATH);

//        String tableName = "sys_test";
//        String fieldName = MyStrUtil._aToA(tableName);
//
        //tableColumns
//        String templateName = "VueDevelop.vue.vm";
//        String outDirPath = "C:\\Users\\57443\\opt";
//        String outFilePath = outDirPath + "\\" + "index.vue";
//        Map<String, Object> params = new HashMap<>();
//        params.put("fieldName", fieldName);

        //测试部分
//        List<TableFieldVo> tableColumns = new ArrayList<>();
//        TableFieldVo tableFieldVo1 = new TableFieldVo();
//        tableFieldVo1.setColumnName("IS_DELETED");
//        tableFieldVo1.setDataType(MysqlField.getByColumnType("bit").getColumnType());
//        tableFieldVo1.setFieldName(MyStrUtil.I_DCToiDc(tableFieldVo1.getColumnName()));
//        tableFieldVo1.setComponent(ComponentFieldTransferUtil.transferMysqlFieldToElementComponent(MysqlField.getByColumnType("bit")).getComponentType());
//        tableColumns.add(tableFieldVo1);

//        TableFieldVo tableFieldVo2 = new TableFieldVo();
//        tableFieldVo2.setColumnName("TABLE_NAME");
//        tableFieldVo2.setDataType("varchar");
//        tableFieldVo2.setFieldName(MyStrUtil.I_DCToiDc(tableFieldVo2.getColumnName()));
//        tableFieldVo2.setComponent(MyStrUtil.getComponentByDataType(tableFieldVo2.getDataType()));
//        tableColumns.add(tableFieldVo2);
//
//        TableFieldVo tableFieldVo3 = new TableFieldVo();
//        tableFieldVo3.setColumnName("CREATE_TIME");
//        tableFieldVo3.setDataType("datetime");
//        tableFieldVo3.setFieldName(MyStrUtil.I_DCToiDc(tableFieldVo3.getColumnName()));
//        tableFieldVo3.setComponent(MyStrUtil.getComponentByDataType(tableFieldVo3.getDataType()));
//        tableColumns.add(tableFieldVo3);

//        params.put("tableColumns", tableColumns);
//
//        VelocityContext context = new VelocityContext(params);
//        VelocityEngine velocityEngine = new VelocityEngine();
//        Properties p = new Properties();
//        p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, BasePath.TEMPLATE_ROOT_RESOURCE_PATH);
//        velocityEngine.init(p);
//        Template tpl = velocityEngine.getTemplate(templateName, "UTF-8");
//        Writer Writer = new PrintWriter(Files.newOutputStream(new File(outFilePath).toPath()));
//        tpl.merge(context, Writer);
//        Writer.flush();
//        Writer.close();

//        VueDevelop.vue.vm

    }
}
