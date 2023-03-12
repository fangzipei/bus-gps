package com.zonefun.backend.test;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.Scanner;

/**
 * @ClassName MapperGenerator
 * @Description MybatisPlus自动生成Mapper文件
 * @Date 2022/11/21 16:42
 * @Author ZoneFang
 */
@SpringBootTest
@Slf4j
public class MapperGenerator {

    @Autowired
    private DataSourceProperties dataSourceProperties;

    @Test
    public void generator() {
        FastAutoGenerator.create(dataSourceProperties.getUrl(), dataSourceProperties.getUsername(), dataSourceProperties.getPassword())
                .templateConfig(builder -> {
                    builder.disable(TemplateType.CONTROLLER, TemplateType.SERVICE, TemplateType.MAPPER);
                })
                .globalConfig((scanner, builder) -> {
                    builder.author(scanner.apply("请输入作者名称？")) // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .disableOpenDir()
                            .fileOverride() // 文件覆盖已经开启
                            .outputDir("D:\\Idea_workspace\\bus-gps\\backend\\src\\main\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.zonefun") // 设置父包名
                            .moduleName("backend") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "D:\\Idea_workspace\\bus-gps\\backend\\src\\main\\resources\\mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder
//                            .addExclude("flyway_schema_history") // 排除表
//                            .addInclude("all_log") // 包括表 与排除表只能有一个存在
                            // 3.5.2中fileOverride()好像被弃用了 要用的话得改成3.5.1
                            .mapperBuilder().enableMapperAnnotation()
                            .serviceBuilder()
                            .entityBuilder().formatFileName("%sEntity").enableLombok().addTableFills(new Column("create_time", FieldFill.INSERT), new Column("update_time", FieldFill.INSERT_UPDATE));
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
