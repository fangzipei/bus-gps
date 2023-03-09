package com.zonefun.backend.test;

import com.zonefun.backend.vo.ExcelCreateSqlRow;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ObjectUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @ClassName ExcelCreateOracleSql
 * @Description 通过Excel生成建表Sql(oracle)
 * @Date 2022/8/23 14:25
 * @Author ZoneFang
 */
@SpringBootTest
@Slf4j
public class ExcelCreateOracleSql {

    // excel文件路径
    private static final String filePath = "C:\\Users\\GLSC\\Desktop\\文件\\投顾系统\\设计文档\\预约再平衡调仓表结构.xlsx";
    // excel模板 该变量可以不写死通过读取model.xls文件第一行来做初始化 能更高可配置化
    private static final String[][] excelTemplate = new String[][]{
            {"是否主键", "primaryKey"},
            {"字段名", "columnName"},
            {"字段描述", "columnDescription"},
            {"数据类型(长度)", "dataType"},
            {"是否必填", "isNeed"},
            {"缺省值", "defaultValue"}};
    // 生成的sql文件名
    private static final String targetFile = "ddl.sql";
    // 生成的文件默认为UTF-8编码
    private static final Charset fileCharset = Charset.forName("gbk");

    /**
     * 读取excel文件内容生成数据库表ddl
     *
     * @return void
     * @date 2022/8/23 14:26
     * @author ZoneFang
     */
    @Test
    public void getDataFromExcel() {
        if (!filePath.endsWith(".xls") && !filePath.endsWith(".xlsx")) {
            log.info("文件不是excel类型");
            return;
        }
        InputStream fis = null;
        Workbook wookbook = null;
        // 记录耗时
        LocalDateTime start = LocalDateTime.now();
        // 读取文件数据流
        try {
            fis = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            log.error("未找到文件，请检查文件路径！");
            e.printStackTrace();
        }
        if (filePath.endsWith(".xls")) {
            try {
                // 2003版本的excel，用.xls结尾
                wookbook = new HSSFWorkbook(fis);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (filePath.endsWith(".xlsx")) {
            try {
                // 2007版本的excel，用.xlsx结尾
                wookbook = new XSSFWorkbook(fis);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 获取工作表迭代器
        Iterator<Sheet> sheets = wookbook.sheetIterator();
        while (sheets.hasNext()) {
            // 一张工作表代表一张数据库表
            // 记录最后的ddl
            StringBuilder ddl = new StringBuilder();

            Sheet sheet = sheets.next();
            log.info("当前读取的sheet页：{}", sheet.getSheetName());
            // 当前读取行的行号
            int rowId = 0;
            // 获取当前表的行迭代器
            Iterator<Row> rows = sheet.rowIterator();

            // 解析工作表名 默认为tableName-表名
            String[] tableName = sheet.getSheetName().split("-");
            String tableEnglishName = tableName[0].toUpperCase(); // 表英文名
            String tableChineseName = ""; // 表注释
            if (tableName.length > 1) {
                tableChineseName = tableName[1];
            }
            // 记录这个工作表对应的数据库表数据
            List<ExcelCreateSqlRow> tableInfo = new ArrayList<>();

            while (rows.hasNext()) {
                rowId++;
                Row row = rows.next();
                if (rowId == 1) {
                    // 通过第一行的数据检查是否为模板
                    for (int i = 0; i < excelTemplate.length; i++) {
                        String tableHeader = getCellValueToString(row.getCell(i));
                        if (!excelTemplate[i][0].equals(tableHeader)) {
                            // 如果有一个不同则提示使用模板
                            log.error("【{}】请使用提供的标准模板来进行sql的生成！", sheet.getSheetName());
                            return;
                        }
                    }
                    continue;
                }

                // 行数据转行对象
                ExcelCreateSqlRow excelCreateSqlRow = new ExcelCreateSqlRow();
                Class excelCreateSqlRowClass = ExcelCreateSqlRow.class;
                // 第二行之后便是需要生成的表信息了
                for (int i = 0; i < excelTemplate.length; i++) {
                    Field declaredField = null;
                    try {
                        declaredField = excelCreateSqlRowClass.getDeclaredField(excelTemplate[i][1]);
                        declaredField.setAccessible(true);
                        String cellValueToString = getCellValueToString(row.getCell(i));
                        // 单元格如果是数字类型 获取的数据可能带小数点，这里只需要小数点前的数字就好
                        if (cellValueToString.contains(".")) {
                            cellValueToString = cellValueToString.split("\\.")[0];
                        }
                        declaredField.set(excelCreateSqlRow, cellValueToString);
                    } catch (Exception e) {
                        log.error("在对应工作表{}第{}行第{}列数据时发生错误！已暂时跳过，请核对模板！", sheet.getSheetName(), row.getRowNum(), i + 1);
                        e.printStackTrace();
                    }
                }
                tableInfo.add(excelCreateSqlRow);
            }

            // 工作表数据组装完，通过tableInfo开始对ddl进行组装
            StringBuilder primaryKeys = new StringBuilder(); // 记录主键
            StringBuilder comments = new StringBuilder(); // 记录注释
            // CREATE TABLE `table_name`
            //     (
            ddl.append("CREATE TABLE ").append(tableEnglishName).append("\r\n")
                    .append("\t(\r\n");
            // 对当前表中所有行数据进行分析和组装
            for (ExcelCreateSqlRow excelCreateSqlRow : tableInfo) {
                String columnName = excelCreateSqlRow.getColumnName();
                String dataType = excelCreateSqlRow.getDataType();
                String isNeed = excelCreateSqlRow.getIsNeed();
                String primaryKey = excelCreateSqlRow.getPrimaryKey();
                String columnDescription = excelCreateSqlRow.getColumnDescription();
                String defaultValue = excelCreateSqlRow.getDefaultValue();
                boolean ifNum = false; // 是不是数字类型
                // 如果字段名或数据类型为空则报错并跳过
                if (ObjectUtils.isEmpty(columnName) || ObjectUtils.isEmpty(dataType)) {
                    log.error("行数据:【{}】因缺失字段名而省略", excelCreateSqlRow);
                    continue;
                }
                dataType = dataType.trim().toUpperCase();
                // 判断各种数据类型，对应的length也会不同
                if (dataType.startsWith("NUMBER")) {
                    ifNum = true;
                }
                // 判断缺省值，如果为空则不给
                if (ifNum) {
                    defaultValue = ObjectUtils.isEmpty(defaultValue) || ObjectUtils.isEmpty(defaultValue.trim()) ? "" : "DEFAULT " + defaultValue.trim() + "";
                } else {
                    defaultValue = ObjectUtils.isEmpty(defaultValue) || ObjectUtils.isEmpty(defaultValue.trim()) ? "" : "DEFAULT '" + defaultValue.trim() + "'";
                }
                // 判断是否必填，如果为是、1则表示不可为空
                isNeed = "是".equals(isNeed) || "1".equals(isNeed) ? "NOT NULL" : "";

                // 判断是否有注释
                if (!ObjectUtils.isEmpty(columnDescription)) {
                    // 注释语句，从左到右分别为：表名、字段名、注释
                    comments.append(String.format("COMMENT ON COLUMN %s.%s IS '%s'; \r\n", tableEnglishName, columnName.toUpperCase(), columnDescription));
                }
                // 判断是否是主键
                if (!ObjectUtils.isEmpty(primaryKey) && ("是".equals(primaryKey) || "1".equals(primaryKey))) {
                    primaryKeys.append(",").append(columnName.toUpperCase());
                }

                // 字段的含义从左到右分别是：字段名、数据类型、缺省值、是否允许为空
                ddl.append("\t").append(String.format("%s %s %s %s,\r\n", columnName.toUpperCase(), dataType.toUpperCase(), defaultValue, isNeed));
            }
            // 组装结束后删除最后一句末尾的","
            if (ddl.toString().endsWith(",\r\n")) {
                ddl = ddl.deleteCharAt(ddl.length() - 3);
            }
            //  )
            ddl.append("\t);\r\n");
            // 如果有主键，加上主键的约束
            if (!ObjectUtils.isEmpty(primaryKeys)) {
                ddl.append("\r\n")
                        // 组装主键语句，从左到右分别为： 表名、表名、主键
                        .append(String.format("ALTER TABLE %s ADD CONSTRAINT %s_PK PRIMARY KEY (%s);\r\n", tableEnglishName, tableEnglishName, primaryKeys.substring(1)));
            }
            // 如果有表注释，加上表注释
            if (!ObjectUtils.isEmpty(tableChineseName)) {
                // 表注释语句，从左到右分别是：表名，表注释
                ddl.append(String.format("COMMENT ON TABLE %s IS '%s';\r\n", tableEnglishName, tableChineseName));
            }
            // 如果有注释，加上注释
            if (!ObjectUtils.isEmpty(comments)) {
                ddl.append(comments);
            }

            ddl.append("-- --------------------------------------------------------------------------------\r\n");
            writeMessageToFile(ddl.toString());
        }
        Duration cost = Duration.between(start, LocalDateTime.now());
        log.info("ddl全部生成完毕！耗时：{}ms", cost.getSeconds() * 1000 + cost.getNano() / 1000000);
        try {
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成sql文件
     *
     * @param message sql语句
     * @return void
     * @date 2022/8/23 16:57
     * @author ZoneFang
     */
    public void writeMessageToFile(String message) {
        try {
            log.info("当前生成的sql为：{}", message);
            log.info("开始生成sql文件...");
            LocalDateTime start = LocalDateTime.now();
            File file = new File(targetFile);
            if (!file.exists() && file.createNewFile()) {
                log.info("文件新建成功！");
            }
            FileWriter fileWriter = new FileWriter(file.getName(), fileCharset, true);
            fileWriter.write(message);
            fileWriter.close();
            Duration cost = Duration.between(start, LocalDateTime.now());
            log.info("文件生成成功！耗时：{}ms", cost.getSeconds() * 1000 + cost.getNano() / 1000000);
        } catch (IOException e) {
            log.error("文件生成失败！");
            e.printStackTrace();
        }
    }

    /**
     * 获取单元格数据 转成String
     *
     * @param cell 单元格信息
     * @return java.lang.String
     * @date 2022/8/24 11:17
     * @author ZoneFang
     */
    public String getCellValueToString(Cell cell) {
        String result = "";
        switch (cell.getCellTypeEnum()) {
            case STRING:
                result = cell.getStringCellValue();
                break;
            case NUMERIC:
                short format = cell.getCellStyle().getDataFormat();
                if (DateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf = null;
                    if (format == 20 || format == 32) {
                        sdf = new SimpleDateFormat("HH:mm");
                    } else if (format == 14 || format == 31 || format == 57 || format == 58) {
                        // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
                        sdf = new SimpleDateFormat("yyyy-MM-dd");
                        double value = cell.getNumericCellValue();
                        Date date = DateUtil
                                .getJavaDate(value);
                        result = sdf.format(date);
                    } else {// 日期
                        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    }
                    try {
                        result = sdf.format(cell.getDateCellValue());// 日期
                    } catch (Exception e) {
                        log.error("格式化单元格日期失败！sheet:【{}】,row:【{}】行,cell:【{}】列", cell.getSheet().getSheetName(), cell.getRow().getRowNum(), cell.getColumnIndex());
                        return result;
                    }
                } else {
                    BigDecimal bd = BigDecimal.valueOf(cell.getNumericCellValue());
                    result = bd.toPlainString();// 数值 这种用BigDecimal包装再获取plainString，可以防止获取到科学计数值
                }
                break;
            default:
                break;
        }
        return result;
    }

}
