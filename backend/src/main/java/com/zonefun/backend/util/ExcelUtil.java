package com.zonefun.backend.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.zonefun.backend.annotations.ExcelField;
import com.zonefun.backend.vo.ExcelVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName ExcelUtil
 * @Description Excel工具
 * @Date 2022/9/9 9:31
 * @Author ZoneFang
 */
@Slf4j
public class ExcelUtil {

    /**
     * 通过列表数据获取Excel导出的写入对象
     *
     * @param list 列表数据 子项必须以ExcelVO为基类
     * @return cn.hutool.poi.excel.ExcelWriter
     * @date 2022/9/9 10:11
     * @author ZoneFang
     */
    public static <T extends ExcelVO> ExcelWriter getExcelWriter(List<T> list) {
        // Excel生成器
        ExcelWriter writer = cn.hutool.poi.excel.ExcelUtil.getWriter();
        // 用于记录数据，其中每个item都是一行的数据，格式为：字段名 - 字段值
        List<Map<String, Object>> data = new ArrayList<>();
        // 获取泛型类的各个字段
        if (ObjectUtils.isEmpty(list)) {
            log.error("导出Excel时传入List为空！");
            return writer;
        }
        Class targetClass = list.get(0).getClass();
        Field[] declaredFields = targetClass.getDeclaredFields();
        // 需要导出Excel的字段
        List<Field> needExportFields = new ArrayList<>();
        // 需要转换的各字段及对应的enum
        Map<String, Class> needTransfer = new HashMap<>();

        // 遍历所有字段，将需要导出Excel的字段提出来
        for (Field field : declaredFields) {
            ExcelField annotation = field.getAnnotation(ExcelField.class);
            if (!ObjectUtils.isEmpty(annotation) && !ObjectUtils.isEmpty(annotation.value())) {
                // 添加转换字段
                needExportFields.add(field);
                // 添加需要通过enum转换的字段
                if (!ObjectUtils.isEmpty(annotation.enumClass()) && !Object.class.equals(annotation.enumClass())) {
                    needTransfer.putIfAbsent(field.getName(), annotation.enumClass());
                }
                // 添加需要通过自定义枚举转换的字段 对应String.class
                if (!ObjectUtils.isEmpty(annotation.enumValue())) {
                    needTransfer.putIfAbsent(field.getName(), String.class);
                }
            } else {
                log.info("将{}类导出成Excel时，其中{}字段未进行导出！", targetClass, field.getName());
                // 继续导出
            }
        }
        // 对需导出的字段进行排序 是按照添加别名的顺序排序的 所以排序需要在添加别名前
        needExportFields = needExportFields.stream().sorted((f1, f2) -> {
            ExcelField f1ExcelField = f1.getAnnotation(ExcelField.class);
            ExcelField f2ExcelField = f2.getAnnotation(ExcelField.class);
            return f1ExcelField.sort() - f2ExcelField.sort();
        }).collect(Collectors.toList());

        needExportFields.forEach(field -> {
            // 添加列的别名
            writer.addHeaderAlias(field.getName(), field.getAnnotation(ExcelField.class).value());
        });

        // 装填行数据
        if (!ObjectUtil.isEmpty(list)) {
            int count = 0;
            for (T excelVO : list) {
                // 每行的数据
                Map<String, Object> item = new LinkedHashMap<>();
                item.put("序号", ++count);
                // 遍历所有字段，以字段的name为key，它所对应字段的值作为value（行数据）
                for (Field field : needExportFields) {
                    field.setAccessible(true);
                    Class enumClass = null;
                    try {
                        // 判断是否需要转换
                        enumClass = needTransfer.get(field.getName());
                        if (!ObjectUtils.isEmpty(enumClass) && !String.class.equals(enumClass)) {
                            Method getDescription = enumClass.getDeclaredMethod("getDescription", String.class);
                            Object value = getDescription.invoke(excelVO, String.valueOf(field.get(excelVO)));
                            // 以注解的value为key，它所对应字段的值作为value（行数据）
                            item.putIfAbsent(field.getName(), value);
                        } else if (String.class.equals(enumClass)) {
                            // 如果是String.class 则表示是自定义枚举 对其进行解析
                            Object value = field.get(excelVO);
                            try {
                                value = getCustomEnumValue(field.getAnnotation(ExcelField.class).enumValue(), field.get(excelVO));
                                if (ObjectUtils.isEmpty(value)) {
                                    value = field.get(excelVO);
                                    log.error("在将【{}】类导出Excel文件中，字段【{}】的自定义枚举没有包含字典值为【{}】的字典项", targetClass.getSimpleName(), field.getName(), value);
                                }
                            } catch (Exception e) {
                                log.error("在将【{}】类导出Excel文件中，字段【{}】的自定义枚举无法解析导致无法导出！", targetClass.getSimpleName(), field.getName());
                                e.printStackTrace();
                            }
                            item.putIfAbsent(field.getName(), value);
                        } else {
                            // 以注解的value为key，它所对应字段的值作为value（行数据）
                            item.putIfAbsent(field.getName(), field.get(excelVO));
                        }
                    } catch (IllegalAccessException e) {
                        log.error("在转换字段【{}】时出错！", field.getName());
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        log.error("在使用枚举类【{}】转换字段【{}】时出错，请检查枚举类是否有getDescription(String)方法！", enumClass, field.getName());
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        log.error("在使用枚举类【{}】getDescription方法时出错！", enumClass);
                        e.printStackTrace();
                    }
                }
                // 记录行数据
                data.add(item);
            }
        }
        writer.write(data);
        return writer;
    }

    /**
     * 通过列表数据获取Excel导出的写入对象
     *
     * @param list         列表数据 子项必须以ExcelVO为基类
     * @param needSerialNo 是否需要序号
     * @param needTransfer 是否需要转义
     * @return cn.hutool.poi.excel.ExcelWriter
     * @date 2022/9/9 10:11
     * @author ZoneFang
     */
    public static <T extends ExcelVO> ExcelWriter getExcelWriter(List<T> list, boolean needSerialNo, boolean needTransfer) {
        // Excel生成器
        ExcelWriter writer = cn.hutool.poi.excel.ExcelUtil.getWriter();
        // 用于记录数据，其中每个item都是一行的数据，格式为：字段名 - 字段值
        List<Map<String, Object>> data = new ArrayList<>();
        // 获取泛型类的各个字段
        if (ObjectUtils.isEmpty(list)) {
            log.error("导出Excel时传入List为空！");
            return writer;
        }
        Class targetClass = list.get(0).getClass();
        Field[] declaredFields = targetClass.getDeclaredFields();
        // 需要导出Excel的字段
        List<Field> needExportFields = new ArrayList<>();
        // 需要转换的各字段及对应的enum
        Map<String, Class> transferEnum = new HashMap<>();

        // 遍历所有字段，将需要导出Excel的字段提出来
        for (Field field : declaredFields) {
            ExcelField annotation = field.getAnnotation(ExcelField.class);
            if (!ObjectUtils.isEmpty(annotation) && !ObjectUtils.isEmpty(annotation.value())) {
                // 添加转换字段
                needExportFields.add(field);
                // 添加需要通过enum转换的字段
                if (!ObjectUtils.isEmpty(annotation.enumClass()) && !Object.class.equals(annotation.enumClass())) {
                    transferEnum.putIfAbsent(field.getName(), annotation.enumClass());
                }
                // 添加需要通过自定义枚举转换的字段 对应String.class
                if (!ObjectUtils.isEmpty(annotation.enumValue())) {
                    transferEnum.putIfAbsent(field.getName(), String.class);
                }
            } else {
                log.info("将{}类导出成Excel时，其中{}字段未进行导出！", targetClass, field.getName());
                // 继续导出
            }
        }
        // 对需导出的字段进行排序 是按照添加别名的顺序排序的 所以排序需要在添加别名前
        needExportFields = needExportFields.stream().sorted((f1, f2) -> {
            ExcelField f1ExcelField = f1.getAnnotation(ExcelField.class);
            ExcelField f2ExcelField = f2.getAnnotation(ExcelField.class);
            return f1ExcelField.sort() - f2ExcelField.sort();
        }).collect(Collectors.toList());

        needExportFields.forEach(field -> {
            // 添加列的别名
            writer.addHeaderAlias(field.getName(), field.getAnnotation(ExcelField.class).value());
        });

        // 装填行数据
        if (!ObjectUtil.isEmpty(list)) {
            int count = 0;
            for (T excelVO : list) {
                // 每行的数据
                Map<String, Object> item = new LinkedHashMap<>();
                if (needSerialNo) {
                    item.put("序号", ++count);
                }
                // 遍历所有字段，以字段的name为key，它所对应字段的值作为value（行数据）
                for (Field field : needExportFields) {
                    field.setAccessible(true);
                    Class enumClass = null;
                    try {
                        // 判断是否需要转换
                        enumClass = transferEnum.get(field.getName());
                        if (needTransfer && !ObjectUtils.isEmpty(enumClass) && !String.class.equals(enumClass)) {
                            Method getDescription = enumClass.getDeclaredMethod("getDescription", String.class);
                            Object value = getDescription.invoke(excelVO, String.valueOf(field.get(excelVO)));
                            // 以注解的value为key，它所对应字段的值作为value（行数据）
                            item.putIfAbsent(field.getName(), value);
                        } else if (needTransfer && String.class.equals(enumClass)) {
                            // 如果是String.class 则表示是自定义枚举 对其进行解析
                            Object value = field.get(excelVO);
                            try {
                                value = getCustomEnumValue(field.getAnnotation(ExcelField.class).enumValue(), field.get(excelVO));
                                if (ObjectUtils.isEmpty(value)) {
                                    value = field.get(excelVO);
                                    log.error("在将【{}】类导出Excel文件中，字段【{}】的自定义枚举没有包含字典值为【{}】的字典项", targetClass.getSimpleName(), field.getName(), value);
                                }
                            } catch (Exception e) {
                                log.error("在将【{}】类导出Excel文件中，字段【{}】的自定义枚举无法解析导致无法导出！", targetClass.getSimpleName(), field.getName());
                                e.printStackTrace();
                            }
                            item.putIfAbsent(field.getName(), value);
                        } else {
                            // 以注解的value为key，它所对应字段的值作为value（行数据）
                            item.putIfAbsent(field.getName(), field.get(excelVO));
                        }
                    } catch (IllegalAccessException e) {
                        log.error("在转换字段【{}】时出错！", field.getName());
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        log.error("在使用枚举类【{}】转换字段【{}】时出错，请检查枚举类是否有getDescription(String)方法！", enumClass, field.getName());
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        log.error("在使用枚举类【{}】getDescription方法时出错！", enumClass);
                        e.printStackTrace();
                    }
                }
                // 记录行数据
                data.add(item);
            }
        }
        writer.write(data);
        return writer;
    }

    /**
     * 通过自定义枚举转义字段值
     *
     * @param customEnum 自定义枚举
     * @param fieldValue 字段值
     * @return java.lang.String
     * @date 2022/9/28 15:51
     * @author ZoneFang
     */
    private static String getCustomEnumValue(String customEnum, Object fieldValue) {
        List<String> enums = List.of(customEnum.split(";"));
        Map<String, String> enumMap = enums.stream()
                .collect(Collectors.toMap(
                        e -> e.split("-")[0].trim()
                        , e -> e.split("-")[1].trim())
                );
        return enumMap.get(fieldValue.toString());
    }

    /**
     * 自定义设置单元格样式
     *
     * @param writer hutool-Excel写入器
     * @param x      x_坐标
     * @param y      y_坐标
     * @param isTrue true ->设置为绿色;  false ->设置为红色;
     * @return void
     * @date 2022/11/15 15:49
     * @author ZoneFang
     */
    public static void setCellStyle(ExcelWriter writer, int x, int y, Boolean isTrue) {
        CellStyle cellStyle = writer.createCellStyle(x, y);
        // 顶边栏
        cellStyle.setBorderTop(BorderStyle.NONE);
        // 右边栏
        cellStyle.setBorderRight(BorderStyle.THIN);
        // 底边栏
        cellStyle.setBorderBottom(BorderStyle.THIN);
        // 左边栏
        cellStyle.setBorderLeft(BorderStyle.THIN);
        // 填充前景色(两个一起使用)
        cellStyle.setFillForegroundColor(isTrue ? IndexedColors.BRIGHT_GREEN1.getIndex() : IndexedColors.PINK1.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    }
}
