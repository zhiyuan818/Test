package com.jjxt.ssm.utils;

import java.awt.Color;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.protobuf.TextFormat.ParseException;
import com.jjxt.ssm.entity.Statistics;

public class ExcelUtils {  
	private static Logger logger = Logger.getLogger(ExcelUtils.class);
    private final static String excel2003L =".xls";    //2003- 版本的excel    
    private final static String excel2007U =".xlsx";   //2007+ 版本的excel    
    /*************************************文件上传****************************/  
    public static  List<List<Object>> getBankListByExcel(InputStream in,String fileName) throws Exception{    
        List<List<Object>> list = null;    
            
        //创建Excel工作薄    
        Workbook work = getWorkbook(in,fileName);    
        if(null == work){    
            throw new Exception("创建Excel工作薄为空！");    
        }    
        Sheet sheet = null;    
        Row row = null;    
        Cell cell = null;    
            
        list = new ArrayList<List<Object>>();    
        //遍历Excel中所有的sheet    
        for (int i = 0; i < work.getNumberOfSheets(); i++) {    
            sheet = work.getSheetAt(i);    
            if(sheet==null){continue;}    
                
            //遍历当前sheet中的所有行    
            for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {    
                row = sheet.getRow(j);    
                if(row==null||row.getFirstCellNum()==j){continue;}    
                    
                //遍历所有的列    
                List<Object> li = new ArrayList<Object>();    
                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {    
                    cell = row.getCell(y);    
                    li.add(getCellValue(cell));    
                }    
                list.add(li);    
            }    
        }    
//        work.close();    
        return list;    
    }    
    
    public static  List<List<Object>> getBankListByExcelTitle(InputStream in,String fileName) throws Exception{    
        List<List<Object>> list = null;    
            
        //创建Excel工作薄    
        Workbook work = getWorkbook(in,fileName);    
        if(null == work){    
            throw new Exception("创建Excel工作薄为空！");    
        }    
        Sheet sheet = null;    
        Row row = null;    
        Cell cell = null;    
            
        list = new ArrayList<List<Object>>();    
        //遍历Excel中所有的sheet    
        for (int i = 0; i < work.getNumberOfSheets(); i++) {    
            sheet = work.getSheetAt(i);    
            if(sheet==null){continue;}    
                
            //遍历当前sheet中的所有行    
            for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {    
                row = sheet.getRow(j);  
//                if(row==null||row.getFirstCellNum()==j){continue;}    
                if(row==null){continue;}    
                    
                //遍历所有的列    
                List<Object> li = new ArrayList<Object>();    
                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {    
                    cell = row.getCell(y);    
                    li.add(getCellValue(cell));    
                }    
                list.add(li);    
            }    
        }    
//        work.close();    
        return list;    
    }    
        
    /**  
     * 描述：根据文件后缀，自适应上传文件的版本   
     * @param inStr,fileName  
     * @return  
     * @throws Exception  
     */    
    public static  Workbook getWorkbook(InputStream inStr,String fileName) throws Exception{    
        Workbook wb = null;    
        String fileType = fileName.substring(fileName.lastIndexOf("."));    
        if(excel2003L.equals(fileType)){    
            wb = new HSSFWorkbook(inStr);  //2003-    
        }else if(excel2007U.equals(fileType)){    
            wb = new XSSFWorkbook(inStr);  //2007+    
        }else{    
            throw new Exception("解析的文件格式有误！");    
        }    
        return wb;    
    }    
    
    /**  
     * 描述：对表格中数值进行格式化  
     * @param cell  
     * @return  
     */    
    public static  Object getCellValue(Cell cell){    
        Object value = null;    
        DecimalFormat df = new DecimalFormat("0");  //格式化number String字符    
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");  //日期格式化    
        DecimalFormat df2 = new DecimalFormat("0.00");  //格式化数字    
            
        switch (cell.getCellType()) {    
        case Cell.CELL_TYPE_STRING:    
            value = cell.getRichStringCellValue().getString();    
            break;    
        case Cell.CELL_TYPE_NUMERIC:    
            if("General".equals(cell.getCellStyle().getDataFormatString())){    
                value = df.format(cell.getNumericCellValue());    
            }else if("m/d/yy".equals(cell.getCellStyle().getDataFormatString())){    
                value = sdf.format(cell.getDateCellValue());    
            }else{    
                value = df2.format(cell.getNumericCellValue());    
            }    
            break;    
        case Cell.CELL_TYPE_BOOLEAN:    
            value = cell.getBooleanCellValue();    
            break;    
        case Cell.CELL_TYPE_BLANK:    
            value = "";    
            break;    
        default:    
            break;    
        }    
        return value;    
    }    
    /****************************************上传结束*************************************** 
    /** 
         * 多列头创建EXCEL 
         *  
         * @param sheetName 工作簿名称 
         * @param clazz  数据源model类型 
         * @param objs   excel标题列以及对应model字段名 
         * @param map  标题列行数以及cell字体样式 
         * @return 
         * @throws IllegalArgumentException 
         * @throws IllegalAccessException 
         * @throws InvocationTargetException 
         * @throws ClassNotFoundException 
         * @throws IntrospectionException 
         * @throws ParseException 
         */  
    public static XSSFWorkbook createExcelFile(Class clazz, List objs,Map<Integer, List<ExcelBean>> map,String sheetName) throws IllegalArgumentException,IllegalAccessException,  
    InvocationTargetException, ClassNotFoundException, IntrospectionException, ParseException{  
            // 创建新的Excel 工作簿  
            XSSFWorkbook workbook = new XSSFWorkbook();  
            // 在Excel工作簿中建一工作表，其名为缺省值, 也可以指定Sheet名称  
            XSSFSheet sheet = workbook.createSheet(sheetName);  
            // 以下为excel的字体样式以及excel的标题与内容的创建，下面会具体分析;  
            createFont(workbook);//字体样式  
            createTableHeader(sheet, map);//创建标题（头）  
            createTableRows(sheet, map, objs, clazz);//创建内容  
            return workbook;  
        } 
    /**
     *  出账excel
     * @param findStatisticSum
     * @param statics
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws ClassNotFoundException
     * @throws IntrospectionException
     * @throws ParseException
     */
    public static XSSFWorkbook createExcelBillFile(List<Statistics> findStatisticSum,
			Map<String, List<Statistics>> statics,Map<String, Object> param,String sheetName)  throws IllegalArgumentException,IllegalAccessException,  
    InvocationTargetException, ClassNotFoundException, IntrospectionException, ParseException{
		 // 创建新的Excel 工作簿  
        XSSFWorkbook  workbook = new XSSFWorkbook();
//        createFont(workbook);//字体样式  
//        createHome(workbook,findStatisticSum,param,sheetName);
        createHomeByprovider(workbook,findStatisticSum,param,sheetName);
        
//        createBillFile(workbook,statics,param);
        createBillFileByProvider(workbook,statics,param);
		return workbook;
	}  
    private static void createBillFile(XSSFWorkbook workbook, Map<String, List<Statistics>> statics,
			Map<String, Object> param) {
    	int row=0;
    	Object obj = param.get("lement");
    	if(StringUtil.isEmpty(obj)) {
    		row=7;
    	}
    	boolean lement=false;
    	try {
			lement = (boolean)param.get("lement");
		} catch (Exception e) {
			logger.error("[ERR][EXCELUTIL] EX = "+ e);
		}
    	if(lement) {
			row=6;
		}else {
			row=7;
		}
    	CellRangeAddress callRangeAddress = new CellRangeAddress(0,2,0,row);
    	CellRangeAddress callRangeAddress2 = new CellRangeAddress(3,3,0,row);
    	CellRangeAddress callRangeAddress3 = new CellRangeAddress(4,4,0,row);
		String[] heads=new String[row+1];
		heads[0]="日期";
		heads[1]="账户名称";
		heads[2]="发送条数";
		heads[3]="成功条数";
		heads[4]="失败条数";
		if(lement) {
			heads[5]="单价（元）";
			heads[6]="金额（元）";
		}else {
			heads[5]="未知条数";
			heads[6]="单价（元）";
			heads[7]="金额（元）";
		}
		XSSFCellStyle headStyle = createCellStyle(workbook,(short)18,true,true,false,true,Font.COLOR_NORMAL,Font.U_NONE);
		XSSFCellStyle bootstyle = workbook.createCellStyle();
		bootstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
		bootstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		bootstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		bootstyle.setBottomBorderColor(IndexedColors.BLACK.getIndex()); // 底部边框颜色
		bootstyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		bootstyle.setLeftBorderColor(IndexedColors.BLACK.getIndex()); // 底部边框颜色
		bootstyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		bootstyle.setTopBorderColor(IndexedColors.BLACK.getIndex()); // 底部边框颜色
		bootstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		bootstyle.setRightBorderColor(IndexedColors.BLACK.getIndex()); // 底部边框颜色
		XSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short)11);
		font.setFontName("微软雅黑");
		font.setColor(IndexedColors.WHITE.getIndex());
		bootstyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		bootstyle.setFillForegroundColor(new XSSFColor( new Color(192, 0, 0)));
		bootstyle.setFont(font);
 
		XSSFCellStyle contstyle = workbook.createCellStyle();
		contstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
		contstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		XSSFFont contfont = workbook.createFont();
		contfont.setFontHeightInPoints((short)11);
		contfont.setFontName("微软雅黑");
		contstyle.setFont(contfont);
		contstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		contstyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		contstyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		contstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		XSSFCellStyle rightstyle = workbook.createCellStyle();
		rightstyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);//右边
		rightstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		rightstyle.setFont(contfont);
	     
		for(Entry<String, List<Statistics>> stat:statics.entrySet()) {
			String appName = stat.getKey();
			List<Statistics> stats = stat.getValue();
			XSSFSheet sheet1 = workbook.createSheet(appName);
			sheet1.setDefaultColumnWidth(15);
			sheet1.addMergedRegion(callRangeAddress);
	    	sheet1.addMergedRegion(callRangeAddress2);
	    	sheet1.addMergedRegion(callRangeAddress3);
	    	XSSFRow createRow = sheet1.createRow(0);
	    	XSSFCell cell = createRow.createCell(0);
	    	cell.setCellStyle(headStyle);
	    	cell.setCellValue("短彩信项目对账单明细");
	    	XSSFRow createRow1 = sheet1.createRow(3);
	    	XSSFCell cell1 = createRow1.createCell(0);
	    	cell1.setCellStyle(rightstyle);
	    	cell1.setCellValue("结算周期："+param.get("billingTime"));
	    	XSSFRow createRow2 = sheet1.createRow(5);
	    	for(int i=0;i<heads.length;i++) {
		    	XSSFCell cell2 = createRow2.createCell(i);
		    	cell2.setCellStyle(bootstyle);
		    	cell2.setCellValue(heads[i]);
	    	}
	    	int sendSum=0;
	    	int delivied=0;
	    	int undelivied=0;
	    	int unknow=0;
	    	Double sum=0d;
	    	for(int i=0;i<stats.size();i++) {
	    		XSSFRow createRow3 = sheet1.createRow(6+i);
		    	XSSFCell cell3 = createRow3.createCell(0);
		    	cell3.setCellStyle(contstyle);
		    	Date logDate = stats.get(i).getLogDate();
		    	cell3.setCellValue(DateUtil.formatDate(logDate,"yyyy.MM.dd"));
		    	XSSFCell cell11 = createRow3.createCell(1);
		    	cell11.setCellStyle(contstyle);
		    	cell11.setCellValue(stats.get(i).getAppName());
		    	XSSFCell cell4 = createRow3.createCell(2);
		    	cell4.setCellStyle(contstyle);
		    	cell4.setCellValue(stats.get(i).getSendTotalCharge());
		    	int succ=0;
		    	Double amount=0d;
		    	if(lement) {
		    		XSSFCell cell5 = createRow3.createCell(3);
		    		cell5.setCellStyle(contstyle);
		    		succ=Integer.parseInt(stats.get(i).getReportDelivrdCharge())+Integer.parseInt(stats.get(i).getReportUnknownCharge());
		    		cell5.setCellValue(succ);
			    	XSSFCell cell7 = createRow3.createCell(5);
			    	cell7.setCellStyle(contstyle);
			    	cell7.setCellValue(stats.get(i).getAppPrice()/100);
			    	XSSFCell cell8 = createRow3.createCell(6);
			    	cell8.setCellStyle(contstyle);
			    	amount=stats.get(i).getAppPrice()/100*succ;
			    	amount=(double)Math.round(amount*100)/100;
			    	cell8.setCellValue(amount);
		    	}else {
		    		XSSFCell cell5 = createRow3.createCell(3);
		    		cell5.setCellStyle(contstyle);
		    		succ=Integer.parseInt(stats.get(i).getReportDelivrdCharge());
		    		cell5.setCellValue(succ);
		    		XSSFCell cell9 = createRow3.createCell(5);
		    		cell9.setCellStyle(contstyle);
		    		cell9.setCellValue(stats.get(i).getReportUnknownCharge());
		    		XSSFCell cell7 = createRow3.createCell(6);
		    		cell7.setCellStyle(contstyle);
		    		cell7.setCellValue(stats.get(i).getAppPrice()/100);
			    	XSSFCell cell8 = createRow3.createCell(7);
			    	cell8.setCellStyle(contstyle);
			    	amount=stats.get(i).getAppPrice()/100*succ;
			    	amount=(double) Math.round(amount*100)/100;
			    	cell8.setCellValue(amount);
		    	}
		    	XSSFCell cell6 = createRow3.createCell(4);
		    	cell6.setCellStyle(contstyle);
		    	cell6.setCellValue(stats.get(i).getReportUndelivCharge());
		    	sendSum+=Integer.parseInt(stats.get(i).getSendTotalCharge());
		    	delivied+=succ;
		    	undelivied+=Integer.parseInt(stats.get(i).getReportUndelivCharge());
		    	unknow+=Integer.parseInt(stats.get(i).getReportUnknownCharge());
		    	sum+=amount;
	    	}
	    	CellRangeAddress callRangeAddress4 = new CellRangeAddress(6+stats.size(),6+stats.size(),0,1);
	    	sheet1.addMergedRegion(callRangeAddress4);
	    	XSSFRow createRow10 = sheet1.createRow(6+stats.size());
	    	XSSFCell cell10 = createRow10.createCell(0);
	    	cell10.setCellStyle(bootstyle);
	    	XSSFCell cell11 = createRow10.createCell(1);
	    	cell11.setCellStyle(bootstyle);
	    	cell10.setCellValue("总                计");
	    	XSSFCell cell101 = createRow10.createCell(2);
	    	cell101.setCellStyle(bootstyle);
	    	cell101.setCellValue(sendSum);
	    	XSSFCell cell102 = createRow10.createCell(3);
	    	cell102.setCellStyle(bootstyle);
	    	cell102.setCellValue(delivied);
	    	XSSFCell cell103 = createRow10.createCell(4);
	    	cell103.setCellStyle(bootstyle);
	    	cell103.setCellValue(undelivied);
	    	if(lement) {
	    		XSSFCell cell104 = createRow10.createCell(5);
	    		cell104.setCellStyle(bootstyle);
	    		cell104.setCellValue("-");
	    		XSSFCell cell105 = createRow10.createCell(6);
	    		cell105.setCellStyle(bootstyle);
	    		cell105.setCellValue(sum);
	    	}else {
	    		XSSFCell cell104 = createRow10.createCell(5);
	    		cell104.setCellStyle(bootstyle);
	    		cell104.setCellValue(unknow);
	    		XSSFCell cell105 = createRow10.createCell(6);
	    		cell105.setCellStyle(bootstyle);
	    		cell105.setCellValue("-");
	    		XSSFCell cell106 = createRow10.createCell(7);
	    		cell106.setCellStyle(bootstyle);
	    		cell106.setCellValue(sum);
	    	}
		}
	}

	private static void createHome(XSSFWorkbook workbook,List<Statistics> list,Map<String, Object> param,String sheetName) {
    	CellRangeAddress callRangeAddress = new CellRangeAddress(0,0,0,5);
    	CellRangeAddress callRangeAddress2 = new CellRangeAddress(1,1,0,5);//短彩信结算单
    	CellRangeAddress callRangeAddress3 = new CellRangeAddress(2,3,0,5);
    	CellRangeAddress callRangeAddress4 = new CellRangeAddress(4,4,0,5);//尊敬的合作伙伴
    	CellRangeAddress callRangeAddress5 = new CellRangeAddress(5,7,0,5);
    	CellRangeAddress callRangeAddress6 = new CellRangeAddress(8,8,0,5);//您的结算信息
    	CellRangeAddress callRangeAddress7 = new CellRangeAddress(9,12,0,5);
    	CellRangeAddress callRangeAddress8 = new CellRangeAddress(13,13,0,2);//结算周期
    	CellRangeAddress callRangeAddress9 = new CellRangeAddress(13,13,3,5);//时间
    	CellRangeAddress callRangeAddress10 = new CellRangeAddress(14,14,0,2);//结算标准
    	CellRangeAddress callRangeAddress11 = new CellRangeAddress(14,14,3,5);//成功状态报告
    	CellRangeAddress callRangeAddress12 = new CellRangeAddress(15,15,0,5);
    	CellRangeAddress callRangeAddress13 = new CellRangeAddress((17+list.size()),(17+list.size()),0,4);//total
    	CellRangeAddress callRangeAddress15 = new CellRangeAddress((18+list.size()),(19+list.size()),0,5);
    	CellRangeAddress callRangeAddress16 = new CellRangeAddress((20+list.size()),(20+list.size()),0,5);//若金额
    	CellRangeAddress callRangeAddress17 = new CellRangeAddress((21+list.size()),(21+list.size()),0,5);
    	CellRangeAddress callRangeAddress18 = new CellRangeAddress((22+list.size()),(26+list.size()),0,5);//公司名称
    	XSSFSheet sheet1 = workbook.createSheet(sheetName);                                        
    	sheet1.addMergedRegion(callRangeAddress);
    	sheet1.addMergedRegion(callRangeAddress2);
    	sheet1.addMergedRegion(callRangeAddress3);
    	sheet1.addMergedRegion(callRangeAddress4);
    	sheet1.addMergedRegion(callRangeAddress5);
    	sheet1.addMergedRegion(callRangeAddress6);
    	sheet1.addMergedRegion(callRangeAddress7);
    	sheet1.addMergedRegion(callRangeAddress8);
    	sheet1.addMergedRegion(callRangeAddress9);
    	sheet1.addMergedRegion(callRangeAddress10);
    	sheet1.addMergedRegion(callRangeAddress11);
    	sheet1.addMergedRegion(callRangeAddress12);
    	sheet1.addMergedRegion(callRangeAddress13);
    	sheet1.addMergedRegion(callRangeAddress15);
    	sheet1.addMergedRegion(callRangeAddress16);
    	sheet1.addMergedRegion(callRangeAddress17);
    	sheet1.addMergedRegion(callRangeAddress18);

    	//结算单
        XSSFCellStyle headStyle = createCellStyle(workbook,(short)20,false,true,false,true,Font.COLOR_NORMAL,Font.U_NONE);
        //尊
        XSSFCellStyle erStyle = createCellStyle(workbook,(short)11,false,false, false,false,Font.COLOR_NORMAL,Font.U_NONE);
        //您的
        XSSFCellStyle sanStyle = createCellStyle(workbook,(short)11,false,false, false,false,Font.COLOR_NORMAL,Font.U_SINGLE);
        //结算周期
        XSSFCellStyle colStyle = createCellStyle(workbook,(short)11,true,true, true,false,Font.COLOR_NORMAL,Font.U_NONE);
        //内容样式
        XSSFCellStyle cellStyle = createCellStyle(workbook,(short)11,false,true, true,false,Font.COLOR_NORMAL,Font.U_NONE);
        // total
        XSSFCellStyle totalStyle = createCellStyle(workbook,(short)11,false,true, true,false,Font.COLOR_RED,Font.U_NONE);
        XSSFCellStyle total2Style = createCellStyle(workbook,(short)11,false,true, true,false,Font.COLOR_RED,Font.U_NONE);
    	// 图样式
        XSSFCellStyle tcstyle = workbook.createCellStyle();
        tcstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
        tcstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        tcstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        tcstyle.setBottomBorderColor(IndexedColors.BLACK.getIndex()); // 底部边框颜色
        tcstyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        tcstyle.setLeftBorderColor(IndexedColors.BLACK.getIndex()); // 底部边框颜色
        tcstyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        tcstyle.setTopBorderColor(IndexedColors.BLACK.getIndex()); // 底部边框颜色
        tcstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        tcstyle.setRightBorderColor(IndexedColors.BLACK.getIndex()); // 底部边框颜色
        tcstyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        tcstyle.setFillForegroundColor(new XSSFColor( new Color(255, 255, 209)));
		XSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short)11);
		font.setFontName("微软雅黑");
		font.setColor(IndexedColors.WHITE.getIndex());
		tcstyle.setFont(font);
        //设置列宽
    	sheet1.setDefaultColumnWidth(15);
    	
    	XSSFRow row = sheet1.createRow(0);
    	XSSFCell cell = row.createCell(0);
    	cell.setCellStyle(erStyle);
    	cell.setCellValue("");
    	XSSFRow row1 = sheet1.createRow(1);
    	XSSFCell cell1 = row1.createCell(0);
    	cell1.setCellStyle(headStyle);
    	cell1.setCellValue("短彩信结算单");
    	XSSFRow row2 = sheet1.createRow(2);
    	XSSFCell cell2 = row2.createCell(0);
    	cell2.setCellStyle(erStyle);
    	XSSFRow row3 = sheet1.createRow(3);
    	XSSFCell cell3 = row3.createCell(0);
    	cell3.setCellStyle(erStyle);
    	XSSFRow row4 = sheet1.createRow(4);
    	XSSFCell cell4 = row4.createCell(0);
    	cell4.setCellStyle(erStyle);
    	cell4.setCellValue("尊敬的合作伙伴，"+param.get("companyName")+"，您好！");
    	XSSFRow row5 = sheet1.createRow(5);
    	XSSFCell cell5 = row5.createCell(0);
    	cell5.setCellStyle(sanStyle);
    	XSSFRow row6 = sheet1.createRow(8);
    	XSSFCell cell6 = row6.createCell(0);
    	cell6.setCellStyle(sanStyle);
    	cell6.setCellValue("您的"+param.get("billingTime")+" 的结算信息如下，烦请确认：");
    	XSSFRow row7 = sheet1.createRow(13);
    	XSSFCell cell7 = row7.createCell(0);
    	cell7.setCellStyle(colStyle);
    	XSSFCell cell71 = row7.createCell(1);
    	cell71.setCellStyle(colStyle);
    	XSSFCell cell72 = row7.createCell(2);
    	cell72.setCellStyle(colStyle);
    	cell7.setCellValue("结算周期");
    	XSSFCell cell73 = row7.createCell(3);
    	cell73.setCellStyle(cellStyle);
    	XSSFCell cell74 = row7.createCell(4);
    	cell74.setCellStyle(cellStyle);
    	XSSFCell cell75 = row7.createCell(5);
    	cell75.setCellStyle(cellStyle);
    	cell73.setCellValue(param.get("billingDate")+"");
    	XSSFRow row8 = sheet1.createRow(14);
    	XSSFCell cell8 = row8.createCell(0);
    	cell8.setCellStyle(colStyle);
    	XSSFCell cell81 = row8.createCell(1);
    	cell81.setCellStyle(colStyle);
    	XSSFCell cell82 = row8.createCell(2);
    	cell82.setCellStyle(colStyle);
    	cell8.setCellValue("结算标准");
    	XSSFCell cell83 = row8.createCell(3);
    	cell83.setCellStyle(cellStyle);
    	XSSFCell cell84 = row8.createCell(4);
    	cell84.setCellStyle(cellStyle);
    	XSSFCell cell85 = row8.createCell(5);
    	cell85.setCellStyle(cellStyle);
    	cell83.setCellValue("成功状态报告");
    	XSSFRow row9 = sheet1.createRow(15);
    	XSSFCell cell9 = row9.createCell(0);
    	cell9.setCellStyle(tcstyle);
    	XSSFCell cell91 = row9.createCell(1);
    	cell91.setCellStyle(tcstyle);
    	XSSFCell cell92 = row9.createCell(2);
    	cell92.setCellStyle(tcstyle);
    	XSSFCell cell93 = row9.createCell(3);
    	cell93.setCellStyle(tcstyle);
    	XSSFCell cell94 = row9.createCell(4);
    	cell94.setCellStyle(tcstyle);
    	XSSFCell cell95 = row9.createCell(5);
    	cell95.setCellStyle(tcstyle);
    	cell9.setCellValue("");
    	String[] heads=new String[] {"项目","账号","提交数据（条）","结算数据（条）","结算单价（元）","结算金额"};
    	XSSFRow row10 = sheet1.createRow(16);
    	for(int i=0;i<heads.length;i++) {
    		XSSFCell cell10 = row10.createCell(i);
    		cell10.setCellStyle(colStyle);
    		cell10.setCellValue(heads[i]);
    	}
    	Double sum=0d;
    	if(list.size()>0) {
    		if(list.size()>1) {
    			CellRangeAddress callRangeAddress19 = new CellRangeAddress((17),(16+list.size()),0,0);//total
    			sheet1.addMergedRegion(callRangeAddress19);
    		}
        	for(int i=0;i<list.size();i++) {
        		XSSFRow row111 = sheet1.createRow(17+i);
        		XSSFCell cell11 = row111.createCell(0);
        		cell11.setCellStyle(cellStyle);
        		cell11.setCellValue(param.get("billingString")+"");
        		XSSFCell cell111 = row111.createCell(1);
        		cell111.setCellStyle(cellStyle);
        		cell111.setCellValue(list.get(i).getAppName());
        		XSSFCell cell112 = row111.createCell(2);
        		cell112.setCellStyle(cellStyle);
        		cell112.setCellValue(list.get(i).getSendTotalCharge());
        		XSSFCell cell113 = row111.createCell(3);
        		cell113.setCellStyle(cellStyle);
        		cell113.setCellValue(list.get(i).getReportDelivrdCharge());
        		XSSFCell cell114 = row111.createCell(4);
        		cell114.setCellStyle(cellStyle);
        		cell114.setCellValue(list.get(i).getAppPrice()/100);
        		XSSFCell cell115 = row111.createCell(5);
        		cell115.setCellStyle(cellStyle);
        		Double money=list.get(i).getAppPrice()/100*Double.parseDouble(list.get(i).getReportDelivrdCharge());
        		money = (double) Math.round(money * 100) / 100;
        		cell115.setCellValue(money);
        		sum+=money;
        	}
    	}
    	total2Style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
    	XSSFRow row12 = sheet1.createRow(17+list.size());
    	XSSFCell cell12 = row12.createCell(0);
    	cell12.setCellStyle(total2Style);
    	XSSFCell cell121 = row12.createCell(1);
    	cell121.setCellStyle(total2Style);
    	XSSFCell cell122 = row12.createCell(2);
    	cell122.setCellStyle(total2Style);
    	XSSFCell cell123 = row12.createCell(3);
    	cell123.setCellStyle(total2Style);
    	XSSFCell cell124 = row12.createCell(4);
    	cell124.setCellStyle(total2Style);
    	cell12.setCellValue("TOTAL:");
    	XSSFCell cell13 = row12.createCell(5);
    	cell13.setCellStyle(totalStyle);
    	cell13.setCellValue(sum);
    	XSSFRow row14 = sheet1.createRow(20+list.size());
    	XSSFCell cell14 = row14.createCell(0);
    	cell14.setCellStyle(erStyle);
    	cell14.setCellValue("若金额确认无误，我方将开具发票，请将款项汇至如下账户：");
    	XSSFRow row15 = sheet1.createRow(21+list.size());
    	XSSFCell cell15 = row15.createCell(0);
    	cell15.setCellStyle(erStyle);
    	String str="公司名称：北京久佳信通科技有限公司"+"\r\n"+"开户银行：中国银行股份有限公司北京上地支行"+"\r\n"+"开户行帐号：3350 6512 6829";
    	XSSFRow row16 = sheet1.createRow(22+list.size());
    	XSSFCell cell16 = row16.createCell(0);
    	erStyle.setWrapText(true);
    	cell16.setCellStyle(erStyle);
    	cell16.setCellValue(new XSSFRichTextString(str));
	}
	
	private static void createBillFileByProvider(XSSFWorkbook workbook, Map<String, List<Statistics>> statics,
			Map<String, Object> param) {
    	int row=0;
//    	Object obj = param.get("lement");
//    	if(StringUtil.isEmpty(obj)) {
//    		row=7;
//    	}
    	boolean lement=false;
    	boolean isProvider=false;
    	try {
			lement = (boolean)param.get("lement");
			isProvider = (boolean)param.get("isProvider");
		} catch (Exception e) {
			logger.error("[ERR][EXCELUTIL] EX = "+ e);
		}
    	if(lement) {
			row=6;
		}else {
			row=7;
		}
    	if(isProvider) {
			row++;
		}
    	CellRangeAddress callRangeAddress = new CellRangeAddress(0,2,0,row);
    	CellRangeAddress callRangeAddress2 = new CellRangeAddress(3,3,0,row);
    	CellRangeAddress callRangeAddress3 = new CellRangeAddress(4,4,0,row);
		String[] heads=new String[row+1];
		int index = 2;
		heads[0]="日期";
		heads[1]="账户名称";
		if (isProvider) {
			heads[index]="运营商";
			index++;
		}
		heads[index]="发送条数";
		heads[++index]="成功条数";
		heads[++index]="失败条数";
		if(lement) {
			heads[++index]="单价（元）";
			heads[++index]="金额（元）";
		}else {
			heads[++index]="未知条数";
			heads[++index]="单价（元）";
			heads[++index]="金额（元）";
		}
		XSSFCellStyle headStyle = createCellStyle(workbook,(short)18,true,true,false,true,Font.COLOR_NORMAL,Font.U_NONE);
		XSSFCellStyle bootstyle = workbook.createCellStyle();
		bootstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
		bootstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		bootstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		bootstyle.setBottomBorderColor(IndexedColors.BLACK.getIndex()); // 底部边框颜色
		bootstyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		bootstyle.setLeftBorderColor(IndexedColors.BLACK.getIndex()); // 底部边框颜色
		bootstyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		bootstyle.setTopBorderColor(IndexedColors.BLACK.getIndex()); // 底部边框颜色
		bootstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		bootstyle.setRightBorderColor(IndexedColors.BLACK.getIndex()); // 底部边框颜色
		XSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short)11);
		font.setFontName("微软雅黑");
		font.setColor(IndexedColors.WHITE.getIndex());
		bootstyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		bootstyle.setFillForegroundColor(new XSSFColor( new Color(192, 0, 0)));
		bootstyle.setFont(font);
 
		XSSFCellStyle contstyle = workbook.createCellStyle();
		contstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
		contstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		XSSFFont contfont = workbook.createFont();
		contfont.setFontHeightInPoints((short)11);
		contfont.setFontName("微软雅黑");
		contstyle.setFont(contfont);
		contstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		contstyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		contstyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		contstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		XSSFCellStyle rightstyle = workbook.createCellStyle();
		rightstyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);//右边
		rightstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		rightstyle.setFont(contfont);
	     
		for(Entry<String, List<Statistics>> stat:statics.entrySet()) {
			String appName = stat.getKey();
			List<Statistics> stats = stat.getValue();
			XSSFSheet sheet1 = workbook.createSheet(appName);
			sheet1.setDefaultColumnWidth(15);
			sheet1.addMergedRegion(callRangeAddress);
	    	sheet1.addMergedRegion(callRangeAddress2);
	    	sheet1.addMergedRegion(callRangeAddress3);
	    	XSSFRow createRow = sheet1.createRow(0);
	    	XSSFCell cell = createRow.createCell(0);
	    	cell.setCellStyle(headStyle);
	    	cell.setCellValue("短彩信项目对账单明细");
	    	XSSFRow createRow1 = sheet1.createRow(3);
	    	XSSFCell cell1 = createRow1.createCell(0);
	    	cell1.setCellStyle(rightstyle);
	    	cell1.setCellValue("结算周期："+param.get("billingTime"));
	    	XSSFRow createRow2 = sheet1.createRow(5);
	    	for(int i=0;i<heads.length;i++) {
		    	XSSFCell cell2 = createRow2.createCell(i);
		    	cell2.setCellStyle(bootstyle);
		    	cell2.setCellValue(heads[i]);
	    	}
	    	int sendSum=0;
	    	int delivied=0;
	    	int undelivied=0;
	    	int unknow=0;
	    	Double sum=0d;
	    	for(int i=0;i<stats.size();i++) {
	    		XSSFRow createRow3 = sheet1.createRow(6+i);
		    	XSSFCell cell3 = createRow3.createCell(0);
		    	cell3.setCellStyle(contstyle);
		    	Date logDate = stats.get(i).getLogDate();
		    	cell3.setCellValue(DateUtil.formatDate(logDate,"yyyy.MM.dd"));
		    	XSSFCell cell11 = createRow3.createCell(1);
		    	cell11.setCellStyle(contstyle);
		    	cell11.setCellValue(stats.get(i).getAppName());
		    	int j = 2;
		    	if (isProvider) {
		    		XSSFCell cellProvider = createRow3.createCell(j++);
		    		cellProvider.setCellStyle(contstyle);
		    		cellProvider.setCellValue(stats.get(i).getProvider());
		    	}
		    	XSSFCell cell4 = createRow3.createCell(j++);
		    	cell4.setCellStyle(contstyle);
		    	cell4.setCellValue(stats.get(i).getSendTotalCharge());
		    	int succ=0;
		    	Double amount=0d;
		    	if(lement) {
		    		XSSFCell cell5 = createRow3.createCell(j++);
		    		cell5.setCellStyle(contstyle);
		    		succ=Integer.parseInt(stats.get(i).getReportDelivrdCharge())+Integer.parseInt(stats.get(i).getReportUnknownCharge());
		    		cell5.setCellValue(succ);
		    		XSSFCell cell6 = createRow3.createCell(j++);
			    	cell6.setCellStyle(contstyle);
			    	cell6.setCellValue(stats.get(i).getReportUndelivCharge());
			    	XSSFCell cell7 = createRow3.createCell(j++);
			    	cell7.setCellStyle(contstyle);
			    	cell7.setCellValue(stats.get(i).getAppPrice()/100);
			    	XSSFCell cell8 = createRow3.createCell(j);
			    	cell8.setCellStyle(contstyle);
			    	amount=stats.get(i).getAppPrice()/100*succ;
			    	amount=(double)Math.round(amount*100)/100;
			    	cell8.setCellValue(amount);
		    	}else {
		    		XSSFCell cell5 = createRow3.createCell(j++);
		    		cell5.setCellStyle(contstyle);
		    		succ=Integer.parseInt(stats.get(i).getReportDelivrdCharge());
		    		cell5.setCellValue(succ);
		    		XSSFCell cell6 = createRow3.createCell(j++);
			    	cell6.setCellStyle(contstyle);
			    	cell6.setCellValue(stats.get(i).getReportUndelivCharge());
		    		XSSFCell cell9 = createRow3.createCell(j++);
		    		cell9.setCellStyle(contstyle);
		    		cell9.setCellValue(stats.get(i).getReportUnknownCharge());
		    		XSSFCell cell7 = createRow3.createCell(j++);
		    		cell7.setCellStyle(contstyle);
		    		cell7.setCellValue(stats.get(i).getAppPrice()/100);
			    	XSSFCell cell8 = createRow3.createCell(j);
			    	cell8.setCellStyle(contstyle);
			    	amount=stats.get(i).getAppPrice()/100*succ;
			    	amount=(double) Math.round(amount*100)/100;
			    	cell8.setCellValue(amount);
		    	}
		    	
		    	sendSum+=Integer.parseInt(stats.get(i).getSendTotalCharge());
		    	delivied+=succ;
		    	undelivied+=Integer.parseInt(stats.get(i).getReportUndelivCharge());
		    	unknow+=Integer.parseInt(stats.get(i).getReportUnknownCharge());
		    	sum+=amount;
	    	}
	    	CellRangeAddress callRangeAddress4 = new CellRangeAddress(6+stats.size(),6+stats.size(),0,1);
	    	sheet1.addMergedRegion(callRangeAddress4);
	    	XSSFRow createRow10 = sheet1.createRow(6+stats.size());
	    	XSSFCell cell10 = createRow10.createCell(0);
	    	cell10.setCellStyle(bootstyle);
	    	XSSFCell cell11 = createRow10.createCell(1);
	    	cell11.setCellStyle(bootstyle);
	    	cell10.setCellValue("总                计");
	    	int i = 2;
	    	if (isProvider) {
	    		XSSFCell cellProvider = createRow10.createCell(i++);
	    		cellProvider.setCellStyle(bootstyle);
	    		cellProvider.setCellValue("-");
	    	}
	    	XSSFCell cell101 = createRow10.createCell(i++);
	    	cell101.setCellStyle(bootstyle);
	    	cell101.setCellValue(sendSum);
	    	XSSFCell cell102 = createRow10.createCell(i++);
	    	cell102.setCellStyle(bootstyle);
	    	cell102.setCellValue(delivied);
	    	XSSFCell cell103 = createRow10.createCell(i++);
	    	cell103.setCellStyle(bootstyle);
	    	cell103.setCellValue(undelivied);
	    	if(lement) {
	    		XSSFCell cell104 = createRow10.createCell(i++);
	    		cell104.setCellStyle(bootstyle);
	    		cell104.setCellValue("-");
	    		XSSFCell cell105 = createRow10.createCell(i);
	    		cell105.setCellStyle(bootstyle);
	    		cell105.setCellValue(sum);
	    	}else {
	    		XSSFCell cell104 = createRow10.createCell(i++);
	    		cell104.setCellStyle(bootstyle);
	    		cell104.setCellValue(unknow);
	    		XSSFCell cell105 = createRow10.createCell(i++);
	    		cell105.setCellStyle(bootstyle);
	    		cell105.setCellValue("-");
	    		XSSFCell cell106 = createRow10.createCell(i);
	    		cell106.setCellStyle(bootstyle);
	    		cell106.setCellValue(sum);
	    	}
		}
	}

	private static void createHomeByprovider(XSSFWorkbook workbook,List<Statistics> list,Map<String, Object> param,String sheetName) {
		boolean isProvider=false;
    	try {
			isProvider = (boolean)param.get("isProvider");
		} catch (Exception e) {
			logger.error("[ERR][EXCELUTIL] EX = "+ e);
		}
    	int j = 5;
    	if (isProvider) {
    		j++;
    	}
		CellRangeAddress callRangeAddress = new CellRangeAddress(0,0,0,j);
    	CellRangeAddress callRangeAddress2 = new CellRangeAddress(1,1,0,j);//短彩信结算单
    	CellRangeAddress callRangeAddress3 = new CellRangeAddress(2,3,0,j);
    	CellRangeAddress callRangeAddress4 = new CellRangeAddress(4,4,0,j);//尊敬的合作伙伴
    	CellRangeAddress callRangeAddress5 = new CellRangeAddress(5,7,0,j);
    	CellRangeAddress callRangeAddress6 = new CellRangeAddress(8,8,0,j);//您的结算信息
    	CellRangeAddress callRangeAddress7 = new CellRangeAddress(9,12,0,j);
    	CellRangeAddress callRangeAddress8 = new CellRangeAddress(13,13,0,j>>1);//结算周期
    	CellRangeAddress callRangeAddress9 = new CellRangeAddress(13,13,(j>>1)+1,j);//时间
    	CellRangeAddress callRangeAddress10 = new CellRangeAddress(14,14,0,j>>1);//结算标准
    	CellRangeAddress callRangeAddress11 = new CellRangeAddress(14,14,(j>>1)+1,j);//成功状态报告
    	CellRangeAddress callRangeAddress12 = new CellRangeAddress(15,15,0,j);
    	CellRangeAddress callRangeAddress13 = new CellRangeAddress((17+list.size()),(17+list.size()),0,j-1);//total
    	CellRangeAddress callRangeAddress15 = new CellRangeAddress((18+list.size()),(19+list.size()),0,j);
    	CellRangeAddress callRangeAddress16 = new CellRangeAddress((20+list.size()),(20+list.size()),0,j);//若金额
    	CellRangeAddress callRangeAddress17 = new CellRangeAddress((21+list.size()),(21+list.size()),0,j);
    	CellRangeAddress callRangeAddress18 = new CellRangeAddress((22+list.size()),(26+list.size()),0,j);//公司名称
    	XSSFSheet sheet1 = workbook.createSheet(sheetName);                                        
    	sheet1.addMergedRegion(callRangeAddress);
    	sheet1.addMergedRegion(callRangeAddress2);
    	sheet1.addMergedRegion(callRangeAddress3);
    	sheet1.addMergedRegion(callRangeAddress4);
    	sheet1.addMergedRegion(callRangeAddress5);
    	sheet1.addMergedRegion(callRangeAddress6);
    	sheet1.addMergedRegion(callRangeAddress7);
    	sheet1.addMergedRegion(callRangeAddress8);
    	sheet1.addMergedRegion(callRangeAddress9);
    	sheet1.addMergedRegion(callRangeAddress10);
    	sheet1.addMergedRegion(callRangeAddress11);
    	sheet1.addMergedRegion(callRangeAddress12);
    	sheet1.addMergedRegion(callRangeAddress13);
    	sheet1.addMergedRegion(callRangeAddress15);
    	sheet1.addMergedRegion(callRangeAddress16);
    	sheet1.addMergedRegion(callRangeAddress17);
    	sheet1.addMergedRegion(callRangeAddress18);

    	//结算单
        XSSFCellStyle headStyle = createCellStyle(workbook,(short)20,false,true,false,true,Font.COLOR_NORMAL,Font.U_NONE);
        //尊
        XSSFCellStyle erStyle = createCellStyle(workbook,(short)11,false,false, false,false,Font.COLOR_NORMAL,Font.U_NONE);
        //您的
        XSSFCellStyle sanStyle = createCellStyle(workbook,(short)11,false,false, false,false,Font.COLOR_NORMAL,Font.U_SINGLE);
        //结算周期
        XSSFCellStyle colStyle = createCellStyle(workbook,(short)11,true,true, true,false,Font.COLOR_NORMAL,Font.U_NONE);
        //内容样式
        XSSFCellStyle cellStyle = createCellStyle(workbook,(short)11,false,true, true,false,Font.COLOR_NORMAL,Font.U_NONE);
        // total
        XSSFCellStyle totalStyle = createCellStyle(workbook,(short)11,false,true, true,false,Font.COLOR_RED,Font.U_NONE);
        XSSFCellStyle total2Style = createCellStyle(workbook,(short)11,false,true, true,false,Font.COLOR_RED,Font.U_NONE);
    	// 图样式
        XSSFCellStyle tcstyle = workbook.createCellStyle();
        tcstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
        tcstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        tcstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        tcstyle.setBottomBorderColor(IndexedColors.BLACK.getIndex()); // 底部边框颜色
        tcstyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        tcstyle.setLeftBorderColor(IndexedColors.BLACK.getIndex()); // 底部边框颜色
        tcstyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        tcstyle.setTopBorderColor(IndexedColors.BLACK.getIndex()); // 底部边框颜色
        tcstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        tcstyle.setRightBorderColor(IndexedColors.BLACK.getIndex()); // 底部边框颜色
        tcstyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        tcstyle.setFillForegroundColor(new XSSFColor( new Color(255, 255, 209)));
		XSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short)11);
		font.setFontName("微软雅黑");
		font.setColor(IndexedColors.WHITE.getIndex());
		tcstyle.setFont(font);
        //设置列宽
    	sheet1.setDefaultColumnWidth(15);
    	
    	XSSFRow row = sheet1.createRow(0);
    	XSSFCell cell = row.createCell(0);
    	cell.setCellStyle(erStyle);
    	cell.setCellValue("");
    	XSSFRow row1 = sheet1.createRow(1);
    	XSSFCell cell1 = row1.createCell(0);
    	cell1.setCellStyle(headStyle);
    	cell1.setCellValue("短彩信结算单");
    	XSSFRow row2 = sheet1.createRow(2);
    	XSSFCell cell2 = row2.createCell(0);
    	cell2.setCellStyle(erStyle);
    	XSSFRow row3 = sheet1.createRow(3);
    	XSSFCell cell3 = row3.createCell(0);
    	cell3.setCellStyle(erStyle);
    	XSSFRow row4 = sheet1.createRow(4);
    	XSSFCell cell4 = row4.createCell(0);
    	cell4.setCellStyle(erStyle);
    	cell4.setCellValue("尊敬的合作伙伴，"+param.get("companyName")+"，您好！");
    	XSSFRow row5 = sheet1.createRow(5);
    	XSSFCell cell5 = row5.createCell(0);
    	cell5.setCellStyle(sanStyle);
    	XSSFRow row6 = sheet1.createRow(8);
    	XSSFCell cell6 = row6.createCell(0);
    	cell6.setCellStyle(sanStyle);
    	cell6.setCellValue("您的"+param.get("billingTime")+" 的结算信息如下，烦请确认：");
    	XSSFRow row7 = sheet1.createRow(13);
    	XSSFCell cell7 = row7.createCell(0);
    	cell7.setCellStyle(colStyle);
    	XSSFCell cell71 = row7.createCell(1);
    	cell71.setCellStyle(colStyle);
    	XSSFCell cell72 = row7.createCell(2);
    	cell72.setCellStyle(colStyle);
    	cell7.setCellValue("结算周期");
    	XSSFCell cell73 = row7.createCell(3);
    	cell73.setCellStyle(cellStyle);
    	XSSFCell cell74 = row7.createCell(4);
    	cell74.setCellStyle(cellStyle);
    	XSSFCell cell75 = row7.createCell(5);
    	cell75.setCellStyle(cellStyle);
    	if(isProvider) {
    		cell74.setCellValue(param.get("billingDate")+"");
    		XSSFCell cell76 = row7.createCell(6);
        	cell76.setCellStyle(cellStyle);
    	}else {
    		cell73.setCellValue(param.get("billingDate")+"");
    	}
    	
    	XSSFRow row8 = sheet1.createRow(14);
    	XSSFCell cell8 = row8.createCell(0);
    	cell8.setCellStyle(colStyle);
    	XSSFCell cell81 = row8.createCell(1);
    	cell81.setCellStyle(colStyle);
    	XSSFCell cell82 = row8.createCell(2);
    	cell82.setCellStyle(colStyle);
    	cell8.setCellValue("结算标准");
    	XSSFCell cell83 = row8.createCell(3);
    	cell83.setCellStyle(cellStyle);
    	XSSFCell cell84 = row8.createCell(4);
    	cell84.setCellStyle(cellStyle);
    	XSSFCell cell85 = row8.createCell(5);
    	cell85.setCellStyle(cellStyle);
    	
    	if(isProvider) {
    		cell84.setCellValue("成功状态报告");
    		XSSFCell cell86 = row8.createCell(6);
        	cell86.setCellStyle(cellStyle);
    	}else {
    		cell83.setCellValue("成功状态报告");
    	}
    	XSSFRow row9 = sheet1.createRow(15);
    	XSSFCell cell9 = row9.createCell(0);
    	cell9.setCellStyle(tcstyle);
    	XSSFCell cell91 = row9.createCell(1);
    	cell91.setCellStyle(tcstyle);
    	XSSFCell cell92 = row9.createCell(2);
    	cell92.setCellStyle(tcstyle);
    	XSSFCell cell93 = row9.createCell(3);
    	cell93.setCellStyle(tcstyle);
    	XSSFCell cell94 = row9.createCell(4);
    	cell94.setCellStyle(tcstyle);
    	XSSFCell cell95 = row9.createCell(5);
    	cell95.setCellStyle(tcstyle);
    	if (isProvider) {
    	XSSFCell cell96 = row9.createCell(6);
    	cell96.setCellStyle(tcstyle);
    	}
    	cell9.setCellValue("");
    	String[] heads= null;
    	if(isProvider) {
    		 heads=new String[] {"项目","账号","运营商","提交数据（条）","结算数据（条）","结算单价（元）","结算金额"};
    	}else {
    		 heads=new String[] {"项目","账号","提交数据（条）","结算数据（条）","结算单价（元）","结算金额"};
    	}
    	XSSFRow row10 = sheet1.createRow(16);
    	for(int i=0;i<heads.length;i++) {
    		XSSFCell cell10 = row10.createCell(i);
    		cell10.setCellStyle(colStyle);
    		cell10.setCellValue(heads[i]);
    	}
    	Double sum=0d;
    	if(list.size()>0) {
    		if(list.size()>1) {
    			CellRangeAddress callRangeAddress19 = new CellRangeAddress((17),(16+list.size()),0,0);//total
    			sheet1.addMergedRegion(callRangeAddress19);
    		}
        	for(int i=0;i<list.size();i++) {
        		XSSFRow row111 = sheet1.createRow(17+i);
        		XSSFCell cell11 = row111.createCell(0);
        		cell11.setCellStyle(cellStyle);
        		cell11.setCellValue(param.get("billingString")+"");
        		XSSFCell cell111 = row111.createCell(1);
        		cell111.setCellStyle(cellStyle);
        		cell111.setCellValue(list.get(i).getAppName());
        		j = 2;
        		if (isProvider) {
        		XSSFCell cell112 = row111.createCell(j++);
        		cell112.setCellStyle(cellStyle);
        		cell112.setCellValue(list.get(i).getProvider());
        		}
        		XSSFCell cell113 = row111.createCell(j++);
        		cell113.setCellStyle(cellStyle);
        		cell113.setCellValue(list.get(i).getSendTotalCharge());
        		XSSFCell cell114 = row111.createCell(j++);
        		cell114.setCellStyle(cellStyle);
        		cell114.setCellValue(list.get(i).getReportDelivrdCharge());
        		XSSFCell cell115 = row111.createCell(j++);
        		cell115.setCellStyle(cellStyle);
        		cell115.setCellValue(list.get(i).getAppPrice()/100);
        		XSSFCell cell116 = row111.createCell(j++);
        		cell116.setCellStyle(cellStyle);
        		Double money=list.get(i).getAppPrice()/100*Double.parseDouble(list.get(i).getReportDelivrdCharge());
        		money = (double) Math.round(money * 100) / 100;
        		cell116.setCellValue(money);
        		sum+=money;
        	}
    	}
    	total2Style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
    	XSSFRow row12 = sheet1.createRow(17+list.size());
    	XSSFCell cell12 = row12.createCell(0);
    	cell12.setCellStyle(total2Style);
    	XSSFCell cell121 = row12.createCell(1);
    	cell121.setCellStyle(total2Style);
    	XSSFCell cell122 = row12.createCell(2);
    	cell122.setCellStyle(total2Style);
    	XSSFCell cell123 = row12.createCell(3);
    	cell123.setCellStyle(total2Style);
    	XSSFCell cell124 = row12.createCell(4);
    	cell124.setCellStyle(total2Style);
    	cell12.setCellValue("TOTAL:");
    	XSSFCell cell13 = null;
    	if (isProvider) {
    		XSSFCell cell125 = row12.createCell(5);
    		cell125.setCellStyle(total2Style);
    		 cell13 = row12.createCell(6);
        	
    	}else {
    		 cell13 = row12.createCell(5);
    	}
    	cell13.setCellStyle(totalStyle);
		cell13.setCellValue(sum);
    	XSSFRow row14 = sheet1.createRow(20+list.size());
    	XSSFCell cell14 = row14.createCell(0);
    	cell14.setCellStyle(erStyle);
    	cell14.setCellValue("若金额确认无误，我方将开具发票，请将款项汇至如下账户：");
    	XSSFRow row15 = sheet1.createRow(21+list.size());
    	XSSFCell cell15 = row15.createCell(0);
    	cell15.setCellStyle(erStyle);
    	String str="公司名称：北京久佳信通科技有限公司"+"\r\n"+"开户银行：中国银行股份有限公司北京上地支行"+"\r\n"+"开户行帐号：3350 6512 6829";
    	XSSFRow row16 = sheet1.createRow(22+list.size());
    	XSSFCell cell16 = row16.createCell(0);
    	erStyle.setWrapText(true);
    	cell16.setCellStyle(erStyle);
    	cell16.setCellValue(new XSSFRichTextString(str));
	}
	private static XSSFCellStyle fontStyle;  
    private static XSSFCellStyle fontStyle2;  
    public static void createFont(XSSFWorkbook workbook) {  
        // 表头  
        fontStyle = workbook.createCellStyle();  
        XSSFFont font1 = workbook.createFont();  
        font1.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);  
        font1.setFontName("黑体");  
        font1.setFontHeightInPoints((short) 14);// 设置字体大小  
        fontStyle.setFont(font1);  
        fontStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN); // 下边框  
        fontStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);// 左边框  
        fontStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);// 上边框  
        fontStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);// 右边框  
        fontStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 居中  
  
        // 内容  
        fontStyle2=workbook.createCellStyle();  
        XSSFFont font2 = workbook.createFont();  
        font2.setFontName("宋体");  
        font2.setFontHeightInPoints((short) 10);// 设置字体大小  
        fontStyle2.setFont(font2);       
        fontStyle2.setBorderBottom(XSSFCellStyle.BORDER_THIN); // 下边框  
        fontStyle2.setBorderLeft(XSSFCellStyle.BORDER_THIN);// 左边框  
        fontStyle2.setBorderTop(XSSFCellStyle.BORDER_THIN);// 上边框  
        fontStyle2.setBorderRight(XSSFCellStyle.BORDER_THIN);// 右边框  
        fontStyle2.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 居中  
    }  
      
    /** 
     * 根据ExcelMapping 生成列头（多行列头） 
     *  
     * @param sheet 
     *            工作簿 
     * @param map 
     *            每行每个单元格对应的列头信息 
     */  
    public static final void createTableHeader(XSSFSheet sheet, Map<Integer, List<ExcelBean>> map) {  
        int startIndex=0;//cell起始位置  
        int endIndex=0;//cell终止位置  
  
        for (Map.Entry<Integer, List<ExcelBean>> entry : map.entrySet()) {  
            XSSFRow row = sheet.createRow(entry.getKey());  
            List<ExcelBean> excels = entry.getValue();  
            for (int x = 0; x < excels.size(); x++) {  
                //合并单元格  
                if(excels.get(x).getCols()>1){  
                    if(x==0){                                       
                        endIndex+=excels.get(x).getCols()-1;  
                        CellRangeAddress range=new CellRangeAddress(0,0,startIndex,endIndex);  
                        sheet.addMergedRegion(range);  
                        startIndex+=excels.get(x).getCols();  
                    }else{  
                        endIndex+=excels.get(x).getCols();  
                        CellRangeAddress range=new CellRangeAddress(0,0,startIndex,endIndex);  
                        sheet.addMergedRegion(range);  
                        startIndex+=excels.get(x).getCols();  
                    }  
                    XSSFCell cell = row.createCell(startIndex-excels.get(x).getCols());  
                    cell.setCellValue(excels.get(x).getHeadTextName());// 设置内容  
                    if (excels.get(x).getCellStyle() != null) {  
                        cell.setCellStyle(excels.get(x).getCellStyle());// 设置格式  
                    }  
                    cell.setCellStyle(fontStyle);  
                }else{  
  
                    XSSFCell cell = row.createCell(x);  
                    cell.setCellValue(excels.get(x).getHeadTextName());// 设置内容  
                    if (excels.get(x).getCellStyle() != null) {  
                        cell.setCellStyle(excels.get(x).getCellStyle());// 设置格式  
                    }  
                    cell.setCellStyle(fontStyle);  
                }  
  
            }  
        }  
    }  
      
    /** 
     *  
     * @param sheet 
     * @param map 
     * @param objs 
     * @param clazz 
     */  
    @SuppressWarnings("rawtypes")  
    public static void createTableRows(XSSFSheet sheet, Map<Integer, List<ExcelBean>> map, List objs, Class clazz)  
            throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, IntrospectionException,  
            ClassNotFoundException, ParseException {  
  
        int rowindex = map.size();  
        int maxKey = 0;  
        List<ExcelBean> ems = new ArrayList<>();  
        for (Map.Entry<Integer, List<ExcelBean>> entry : map.entrySet()) {  
            if (entry.getKey() > maxKey) {  
                maxKey = entry.getKey();  
            }  
        }  
        ems = map.get(maxKey);  
  
        List<Integer> widths = new ArrayList<Integer>(ems.size());  
        for (Object obj : objs) {  
            XSSFRow row = sheet.createRow(rowindex);  
            for (int i = 0; i < ems.size(); i++) {  
                ExcelBean em = (ExcelBean) ems.get(i);  
            // 获得get方法   
                PropertyDescriptor pd = new PropertyDescriptor(em.getPropertyName(), clazz);  
                Method getMethod = pd.getReadMethod();  
                Object rtn = getMethod.invoke(obj);  
                String value = "";  
                // 如果是日期类型 进行 转换  
                if (rtn != null) {  
                    if (rtn instanceof Date) {  
                        value = DateUtil.convertDate3((Date)rtn);  
                    } else if(rtn instanceof BigDecimal){  
                        NumberFormat nf = new DecimalFormat("#,##0.00");  
                        value=nf.format((BigDecimal)rtn).toString();  
                    } else if((rtn instanceof Integer) && (Integer.valueOf(rtn.toString())<0 )){  
                        value="--";  
                    }else {  
                        value = rtn.toString();  
                    }  
                }  
                XSSFCell cell = row.createCell(i);  
                cell.setCellValue(value);  
                cell.setCellType(XSSFCell.CELL_TYPE_STRING);  
                cell.setCellStyle(fontStyle2);  
                // 获得最大列宽  
                int width = value.getBytes().length * 300;  
                // 还未设置，设置当前  
                if (widths.size() <= i) {  
                    widths.add(width);  
                    continue;  
                }  
                // 比原来大，更新数据  
                if (width > widths.get(i)) {  
                    widths.set(i, width);  
                }  
            }  
            rowindex++;  
        }  
        // 设置列宽  
        for (int index = 0; index < widths.size(); index++) {  
            Integer width = widths.get(index);  
            width = width < 2500 ? 2500 : width + 300;  
            width = width > 10000 ? 10000 + 300 : width + 300;  
            sheet.setColumnWidth(index, width);  
        }  
    }
    /**
     * 
     * @param workbook 实例
     * @param fontsize 字体大小
     * @param flag  是否加粗
     * @param flag1 是否居中
     * @param flag2 是否有边框，还是图白
     * @param flag3 是黑体还是微软雅黑
     * @param color 字体颜色
     * @param underline 是否有下划线
     * @return
     */
    private static XSSFCellStyle createCellStyle(XSSFWorkbook workbook, short fontsize,boolean flag,boolean flag1,boolean flag2,boolean flag3,short color, byte underline) {
        // TODO Auto-generated method stub
        XSSFCellStyle style = workbook.createCellStyle();
        //是否水平居中
        if(flag1){
        	style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
        }
       
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        //创建字体
        XSSFFont font = workbook.createFont();
        //是否加粗字体
        if(flag){
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        }
        if(flag3) {
        	 font.setFontName("黑体");  
        }else {
        	font.setFontName("微软雅黑");  
        }
        font.setFontHeightInPoints(fontsize);
        font.setColor(color);
        font.setUnderline(underline);
        //加载字体
        style.setFont(font);
        if(flag2) {
        	style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        	style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        	style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        	style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        }else {
        	style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        	style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        }
        
        return style;
    }


}  