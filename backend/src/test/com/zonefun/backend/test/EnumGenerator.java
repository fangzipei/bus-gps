package com.zonefun.backend.test;

import com.zonefun.backend.AlgorithmApplication;
import com.zonefun.backend.mapper.DictionaryCatalogMapper;
import com.zonefun.backend.util.CodeTypeTransferUtil;
import com.zonefun.backend.vo.DictionaryExtend;
import com.zonefun.backend.vo.EnumItem;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ObjectUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @ClassName GeneratorEnum
 * @Description 枚举类生成工具
 * @Date 2022/8/22 15:18
 * @Author ZoneFang
 */
@Slf4j
@SpringBootTest(classes = AlgorithmApplication.class)
public class EnumGenerator {
    // 数据库表对应生成的mapper
    @Autowired
    private DictionaryCatalogMapper dictionaryCatalogMapper;
    // 文件后缀
    private final String suffix = ".java";
    // 生成枚举类里面的包名
    private final String packageName = "com.zonefun.algorithm";
    // 类上的日期
    private final String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    // 枚举类描述
    private final String enumDescription = "";
    // 作者
    private final String author = "ZoneFang";
    // 枚举类名称 （默认为字典项英文名的大驼峰形式+Enum）
    private final String enumClassName = "";
    // freemarker 模板的名字
    private final String templateName = "enum.ftl";
    // enum文件生成路径
    private final String path = "src/main/java/com/zonefun/algorithm/enums/";
    // 字典项英文名 // fixme
    private final String dictionaryEnglishName = "file_use_type";

    /**
     * 生成enum文件
     *
     * @return void
     * @date 2022/8/22 18:10
     * @author ZoneFang
     */
    @Test
    public void buildEnumFile() throws Exception {
        final String className = ObjectUtils.isEmpty(enumClassName) ? CodeTypeTransferUtil.snakeToUpperCamelCase(dictionaryEnglishName) + "Enum" : enumClassName;
        //生成枚举类的路径
        String filePath = path + className + suffix;
        log.info("文件生成路径：{}", filePath);
        File mapperFile = new File(filePath);
        // 用于填充模板
        Map<String, Object> dataMap = new HashMap<>();
        // 查找数据库获取相应英文名对应的字典项
        List<DictionaryExtend> list = dictionaryCatalogMapper.listItemsByDictEnglishName(dictionaryEnglishName);
        // 存枚举信息
        List<EnumItem> result = new ArrayList<>();
        log.info("开始组装enum数据...");
        list.forEach(dictionary -> {
            log.info("正在组装字典项【{}】的【{}】", dictionary.getItemEnglishName(), dictionary.getItemValue());
            EnumItem enumItem = new EnumItem();
            enumItem.setEnumItemName(ObjectUtils.isEmpty(dictionary.getItemEnglishName()) || " ".equals(dictionary.getItemEnglishName())
                    ? "_" + dictionary.getItemValue()
                    : dictionary.getItemEnglishName().toUpperCase());
            enumItem.setEnumItemValue(dictionary.getItemValue());
            enumItem.setEnumItemDesc(dictionary.getItemDescription());
            result.add(enumItem);
        });
        log.info("enum组装结束！");
        // enum字典项
        dataMap.put("enum_list", result);
        //其他的参数
        dataMap.put("date", currentDate);
        dataMap.put("package_name", packageName);
        dataMap.put("enum_class_name", ObjectUtils.isEmpty(enumClassName) ? CodeTypeTransferUtil.snakeToUpperCamelCase(dictionaryEnglishName) + "Enum" : enumClassName);
        dataMap.put("author", author);
        dataMap.put("enum_description", enumDescription);
        generateFileByTemplate(mapperFile, dataMap);
    }

    /**
     * 通过模板生成enum
     *
     * @param file    文件名
     * @param dataMap 文件内容
     * @return void
     * @date 2022/8/22 18:10
     * @author ZoneFang
     */
    private void generateFileByTemplate(File file, Map<String, Object> dataMap) throws Exception {
        // 创建配置
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
        // 这个/template是在我们resources目录下的一个文件夹名，然后把模板类templateName.ftl放在这个文件夹下面就好
        cfg.setClassForTemplateLoading(this.getClass(), "/template");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        Template template = cfg.getTemplate(templateName);
        FileOutputStream fos = new FileOutputStream(file);
        log.info("开始生成文件...");
        LocalDateTime start = LocalDateTime.now();
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, StandardCharsets.UTF_8), 10240);
        template.process(dataMap, out);
        Duration cost = Duration.between(start, LocalDateTime.now());
        log.info("文件生成成功【{}】！花费：{}ms", file.getName(), cost.getSeconds() * 1000 + cost.getNano() / 1000000);
    }

}

