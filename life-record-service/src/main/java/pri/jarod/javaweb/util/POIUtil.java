package pri.jarod.javaweb.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import static org.apache.poi.ss.usermodel.CellType.NUMERIC;

public class POIUtil {

    private static Logger logger  = LoggerFactory.getLogger(POIUtil.class);
    private final static String xls = "xls";
    private final static String xlsx = "xlsx";

    /**
     * 读入excel文件，解析后返回
     * @param file
     * @throws IOException
     */
    public static Map<String, Object> readExcel(MultipartFile file) throws IOException{
        //检查文件
        checkFile(file);
        //获得Workbook工作薄对象
        Workbook workbook = getWorkBook(file);
        //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
        List<Map<String, Object>> fieldList = new ArrayList<>();
        List<Map<String, Object>> keyList = new ArrayList<>();
        Map<String, Object> maps = new HashMap<>();
        if(workbook != null){
            for(int sheetNum = 0;sheetNum < workbook.getNumberOfSheets();sheetNum++){
                //获得当前sheet工作表
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if(sheet == null){
                    continue;
                }
                //获得当前sheet的开始行
                int firstRowNum  = sheet.getFirstRowNum()+1;
                //获得当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();
                //循环除了第一行的所有行
                //for(int rowNum = firstRowNum+1;rowNum <= lastRowNum;rowNum++){
                for(int rowNum = firstRowNum;rowNum <= lastRowNum;rowNum++){
                    //获得当前行
                    Row row = sheet.getRow(rowNum);
                    if(row == null){
                        continue;
                    }
                    //获得当前行的开始列
                    int firstCellNum = row.getFirstCellNum();
                    //获得当前行的列数
                    int lastCellNum = row.getPhysicalNumberOfCells();
                    // String[] cells = new String[row.getPhysicalNumberOfCells()];
                    Map<String, Object> map = new HashMap<>();
                    //循环当前行
                    for(int cellNum = firstCellNum; cellNum < lastCellNum;cellNum++){
                        Cell cell = row.getCell(cellNum);
                        // 获得当前key值
                        String key = sheet.getRow(0).getCell(cellNum).getStringCellValue();
                        isNull(sheetNum, cellNum, rowNum, getCellValue(cell));
                        map.put(key, getCellValue(cell));
                        // cells[cellNum] = getCellValue(cell);
                    }
                    if(sheetNum == 0){
                        fieldList.add(map);
                    }else {
                        keyList.add(map);
                    }
                }
            }
            // workbook.close();
        }
        Map<String, Integer> stringIntegerMap = toObject(fieldList);
        maps.put("fieldList", fieldList);
        maps.put("keyList", keyList);
        maps.put("tableNum", stringIntegerMap);
        return maps;
    }
    public static void checkFile(MultipartFile file) throws IOException{
        //判断文件是否存在
        if(null == file){
            logger.error("文件不存在！");
            throw new FileNotFoundException("文件不存在！");
        }
        //获得文件名
        String fileName = file.getOriginalFilename();
        //判断文件是否是excel文件
        if(!fileName.endsWith(xls) && !fileName.endsWith(xlsx)){
            logger.error(fileName + "不是excel文件");
            throw new IOException(fileName + "不是excel文件");
        }
    }
    public static Workbook getWorkBook(MultipartFile file) throws IOException {
        //获得文件名
        String fileName = file.getOriginalFilename();
        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        InputStream is = null;
        try {
            //获取excel文件的io流
            is = file.getInputStream();
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if(fileName.endsWith(xls)){
                //2003
                workbook = new HSSFWorkbook(is);
            }else if(fileName.endsWith(xlsx)){
                //2007 及2007以上
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            logger.info(e.getMessage());
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return workbook;
    }
    public static String getCellValue(Cell cell){

        String cellValue = "";
        if(cell == null){
            return cellValue;
        }
        //把数字当成String来读，避免出现1读成1.0的情况
        if(cell.getCellType() == NUMERIC){
            cell.setCellType(CellType.STRING);
        }
        //判断数据的类型
        switch (cell.getCellType()){
            case NUMERIC: //数字
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case STRING: //字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case BOOLEAN: //Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA: //公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case BLANK: //空值
                cellValue = "";
                break;
            case ERROR: //故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }

    /**
     * 校验数据是否为空
     * @param sheetNum
     * @param cellNum
     * @param cellValue
     * @return
     */
    public static void isNull(Integer sheetNum, Integer cellNum, Integer rowNum, String cellValue){

        int i = rowNum+1;
        int j = cellNum+1;
        if(sheetNum == 0){
            // 判断某些必要字段是否为空
            if(cellNum == 1 || cellNum == 2 || cellNum == 3 || cellNum == 13){
                if(cellValue.equals("")){
                    throw new RuntimeException("字段页第"+ i +"行，第"+ j +"列不允许为空");
                }
            }
        }else {
            if(cellNum == 1 || cellNum == 2 || cellNum == 3 || cellNum == 4){
                if(cellValue.equals("")){
                    throw new RuntimeException("索引页第"+ i +"行，第"+ j +"列不允许为空");
                }
            }
        }
    }

    public static Map<String,Integer> toObject(List<Map<String, Object>> fieldList){

        // List<Map<String, Object>> fieldList = maps.get("fieldList");
        Map<String,Integer> map = new HashMap<>();
        fieldList.forEach( fields->{
            //定义一个计数器，用来记录重复数据的个数
            Integer i = 1;
            if(map.get(fields.get("表英文名")) != null){
                i=map.get(fields.get("表英文名"))+1;
            }
                map.put(fields.get("表英文名").toString(),i);
        });
        return map;
    }
}
