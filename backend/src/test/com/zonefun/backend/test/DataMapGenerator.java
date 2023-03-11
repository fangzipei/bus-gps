package com.zonefun.backend.test;

import cn.hutool.core.map.MapUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.zonefun.backend.entity.DictionaryCatalog;
import com.zonefun.backend.mapper.DictionaryCatalogMapper;
import com.zonefun.backend.util.CodeTypeTransferUtil;
import com.zonefun.backend.vo.DictionaryExtend;
import jdk.jfr.Description;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName com.zonefun.backend.DataMapGenerator
 * @Description 数据字典生成器
 * @Date 2022/8/22 11:17
 * @Author ZoneFang
 */
@SpringBootTest
@Slf4j
public class DataMapGenerator {

    @Autowired
    private DictionaryCatalogMapper dictionaryCatalogMapper;

    @Description("需要获取枚举的数据库对应实例")
    private static final Class targetClass = DictionaryCatalog.class;

    @Value("${tmp.fileDir}")
    private String fileDir;

    @Test
    public void exportDataMap() {
        List<Map<String, Object>> data = new ArrayList<>();
        log.info("准备装载数据...");
        for (Field field : targetClass.getDeclaredFields()) {
            // 转换成蛇型
            String fieldName = CodeTypeTransferUtil.camelTransToSnake(field.getName()).toLowerCase();
            List<DictionaryExtend> dictionaryExtends = dictionaryCatalogMapper.listItemsByDictEnglishName(fieldName);
            if (!ObjectUtils.isEmpty(dictionaryExtends)) {
                log.info("正在装载数据...");
                // 按照map的顺序进行输出 LinkedHashMap
                Map<String, Object> item = MapUtil.newHashMap(true);
                StringBuilder res = new StringBuilder();
                for (DictionaryExtend dictionaryExtend : dictionaryExtends) {
                    res.append(dictionaryExtend.getItemValue()).append("-").append(dictionaryExtend.getItemDescription()).append(" \n");
                }
                item.put("描述", dictionaryExtends.get(0).getDescription());
                item.put("字段", dictionaryExtends.get(0).getDictEnglishName());
                item.put("枚举", res);
                data.add(item);
                log.info("已装载：{}", dictionaryExtends.get(0).getDictName());
            }
        }
        ExcelWriter writer = ExcelUtil.getWriter();
        log.info("正在写入文件...");
        LocalDateTime start = LocalDateTime.now();

        File excel = new File(fileDir + "Dictionary.xls");
        if (ObjectUtils.isEmpty(excel) || !excel.exists() || ObjectUtils.isEmpty(excel.list())) {
            log.info("文件夹不存在，正在创建...");
            boolean mkdirs = excel.mkdirs();
            if (mkdirs) {
                log.info("成功创建文件夹");
            } else {
                log.error("创建文件夹失败！{}", fileDir);
                return;
            }
        }

        OutputStream os = null;
        try {
            os = new FileOutputStream(excel);
            writer.write(data);
            writer.flush(os, true);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writer.close();
            Duration cost = Duration.between(start, LocalDateTime.now());
            log.info("写入文件耗时：{}ms", cost.getSeconds() * 1000 + cost.getNano() / 1000000);
        }
    }
}