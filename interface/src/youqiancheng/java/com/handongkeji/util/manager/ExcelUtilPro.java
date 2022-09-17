package com.handongkeji.util.manager;

import org.apache.poi.hssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;


/**
 * 导出excel工具类
 * @author Administrator
 *
 */
public class ExcelUtilPro {
	/**
	 * 根据数据返回excel表格
	 * @return
	 */
	public static HSSFWorkbook getHSSFWorkbook(String sheetName,String []title,String [][]values, HSSFWorkbook wb){
		 // 第一步，创建一个webbook，对应一个Excel文件  
        if(wb == null){
            wb = new HSSFWorkbook();
        }
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet(sheetName);  
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        HSSFRow row = sheet.createRow(0);  
        // 第四步，创建单元格，并设置值表头 设置表头居中  
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        HSSFCell cell = null;
        //创建标题
        for(int i=0;i<title.length;i++){
            cell = row.createCell(i);  
            cell.setCellValue(title[i]);  
            cell.setCellStyle(style);  
        }
        if(values!=null){
            //创建内容
            for(int i=0;i<values.length;i++){
                row = sheet.createRow(i + 1);
                for(int j=0;j<values[i].length;j++){
                    row.createCell(j).setCellValue(values[i][j]);
                }
            }
        }

        
       return wb;
	}
	
	public static Integer outPutExcel(HttpServletResponse response,String fileName,String []title,String sheetName){
		 HSSFWorkbook wb = ExcelUtilPro.getHSSFWorkbook(sheetName, title, null, null);
	      //将文件存到指定位置  
	        try {  
	             setResponseHeader(response, fileName);  
	             OutputStream os = response.getOutputStream();  
	             wb.write(os);  
	             os.flush();  
	             os.close();  
	        } catch (Exception e) {
	             e.printStackTrace();
	             return -3;
	        }  
			return 1;
	}
	
	/**
	 * 将表格转码
	 * @param response
	 * @param fileName
	 */
	public static void setResponseHeader(HttpServletResponse response, String fileName) {  
        try {  
             try {  
                  fileName = new String(fileName.getBytes(),"ISO8859-1");  
             } catch (UnsupportedEncodingException e) {  
                  e.printStackTrace();  
             }  
             response.setContentType("application/octet-stream;charset=ISO8859-1");  
             response.setHeader("Content-Disposition", "attachment;filename="+ fileName);  
             response.addHeader("Pargam", "no-cache");  
             response.addHeader("Cache-Control", "no-cache");  
        } catch (Exception ex) {  
             ex.printStackTrace();  
        }  
   } 
}
