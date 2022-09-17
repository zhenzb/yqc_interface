package com.handongkeji.util.manager;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;


public class ExcelUtil
{
	// 模板路径
	
	public static ExcelUtil getInstance()
	{
		return new ExcelUtil();
	}
	
	/**
	 * 读excel
	 * 
	 * @throws Exception
	 */
	public List<String[]> read(InputStream ins, Integer colNum)
	{
		try
		{
			Workbook wkbook = WorkbookFactory.create(ins);
			Sheet sheet = wkbook.getSheetAt(0);
			
			int rowNum = sheet.getLastRowNum();
			if (colNum == null)
			{
				colNum = sheet.getDefaultColumnWidth();
			}
			List<String[]> results = new ArrayList<String[]>();
			for (int i = 1; i <= rowNum; i++)
			{
				Row row = sheet.getRow(i);
				if (row == null)
					continue;
				String[] result = new String[colNum];
				for (int j = 0; j < colNum; j++)
				{
					Cell cell = row.getCell(j);
					result[j] = getCellValue(cell);
				}
				results.add(result);
			}
			return results;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public List<String[]> read(String path)
	{
		try
		{
			Workbook wkbook = WorkbookFactory.create(new FileInputStream(path));
			// 获取第一个表�?
			Sheet sheet = wkbook.getSheetAt(0);
			
			int rowNum = sheet.getLastRowNum();
			int colNum = sheet.getColumnWidth(9);
			System.out.println(colNum + "''''");
			List<String[]> results = new ArrayList<String[]>();
			// 第一行是标题,从第二行�?��读取
			for (int i = 1; i <= rowNum; i++)
			{
				Row row = sheet.getRow(i);
				if (row == null)
					continue;
				String[] result = new String[colNum];
				for (int j = 0; j < colNum; j++)
				{
					Cell cell = row.getCell(j);
					result[j] = getCellValue(cell);
				}
				results.add(result);
			}
			return results;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public List<String[]> read(String path, Integer colNum)
	{
		try
		{
			Workbook wkbook = WorkbookFactory.create(new FileInputStream(path));
			// 获取第一个表�?
			Sheet sheet = wkbook.getSheetAt(0);
			
			int rowNum = sheet.getLastRowNum();
			if (colNum == null)
			{
				colNum = sheet.getDefaultColumnWidth();
			}
			List<String[]> results = new ArrayList<String[]>();
			// 第一行是标题,从第二行�?��读取
			for (int i = 1; i <= rowNum; i++)
			{
				Row row = sheet.getRow(i);
				if (row == null)
					continue;
				String[] result = new String[colNum];
				for (int j = 0; j < colNum; j++)
				{
					Cell cell = row.getCell(j);
					result[j] = getCellValue(cell);
				}
				results.add(result);
			}
			return results;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 写excel
	 * 
	 * @throws Exception
	 */
	public void write(String readPath, String writepath, List<String[]> params)
	{
		try
		{
			InputStream ins = new FileInputStream(readPath);
			Workbook wkbook = WorkbookFactory.create(ins);
			
			Sheet sheet = wkbook.getSheetAt(0); // 读取第一个工作簿
			// Sheet sheet = wkbook.cloneSheet(0);
			Row row;
			Cell cell = null;
			int rowNum = sheet.getLastRowNum();
			// 添加的起始行
			for (int i = 0; i < params.size(); i++)
			{
				CellStyle style = getStyle(wkbook);
				
				row = sheet.createRow(i + 1);
				for (int j = 0; j < params.get(i).length; j++)
				{
					myCreateCell(j, params.get(i)[j], row, cell, style);
				}
			}
			
			for (int i = params.size() + 1; i < rowNum + 1; i++)
			{
				sheet.removeRow(sheet.getRow(i));
			}
			
			FileOutputStream os = new FileOutputStream(writepath);
			wkbook.write(os);
			os.flush();
			os.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void writeStream(OutputStream out, List<String[]> params)
	{
		try
		{
			Workbook wkbook = new HSSFWorkbook();
			
			Sheet sheet = wkbook.createSheet(); // 读取第一个工作簿
			// Sheet sheet = wkbook.cloneSheet(0);
			Row row;
			Cell cell = null;
			// 添加的起始行
			for (int i = 0; i < params.size(); i++)
			{
				CellStyle style = getStyle(wkbook);
				
				row = sheet.createRow(i);
				for (int j = 0; j < params.get(i).length; j++)
				{
					String value = params.get(i)[j];
					value = StringUtils.isEmpty(value) ? "" : value;
					// sheet.setColumnWidth(i, value.getBytes().length*2*256);
					sheet.setColumnWidth(i, 5000);
					
					myCreateCell(j, value, row, cell, style);
				}
			}
			wkbook.write(out);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private String getCellValue(Cell cell)
	{
		if (cell == null)
			return "";
		String cellValue = "";
		DecimalFormat df = new DecimalFormat("#.##");
		switch (cell.getCellType())
		{
			case HSSFCell.CELL_TYPE_STRING:
				cellValue = cell.getRichStringCellValue().getString().trim();
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:
				if (HSSFDateUtil.isCellDateFormatted(cell))
				{
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					return sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
				}
				cellValue = df.format(cell.getNumericCellValue()).toString();
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:
				cellValue = String.valueOf(cell.getBooleanCellValue()).trim();
				break;
			case HSSFCell.CELL_TYPE_FORMULA:
				cellValue = cell.getCellFormula();
				break;
			default:
				cellValue = "";
		}
		return cellValue;
	}
	
	private void myCreateCell(int cellnum, String value, Row row, Cell cell, CellStyle style, Sheet sheet)
	{
		
		cell = row.createCell((short) cellnum);
		cell.setCellValue(String.valueOf(value));
		cell.setCellStyle(style);
	}
	
	private void myCreateCell(int cellnum, String value, Row row, Cell cell, CellStyle style)
	{
		myCreateCell(cellnum, value, row, cell, style, null);
	}
	
	private CellStyle getStyle(Workbook workbook)
	{
		// 设置字体;
		Font font = workbook.createFont();
		// 设置字体大小;
		font.setFontHeightInPoints((short) 12);
		// 设置字体名字;
		font.setFontName("Courier New");
		// font.setItalic(true);
		// font.setStrikeout(true);
		// 设置样式;
		CellStyle style = workbook.createCellStyle();
		// 设置底边�?
		style.setBorderBottom(CellStyle.BORDER_THIN);
		// 设置底边框颜�?
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		// 设置左边�?
		style.setBorderLeft(CellStyle.BORDER_THIN);
		// 设置左边框颜�?
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		// 设置右边�?
		style.setBorderRight(CellStyle.BORDER_THIN);
		// 设置右边框颜�?
		style.setRightBorderColor(HSSFColor.BLACK.index);
		// 设置顶边�?
		style.setBorderTop(CellStyle.BORDER_THIN);
		// 设置顶边框颜�?
		style.setTopBorderColor(HSSFColor.BLACK.index);
		// 在样式用应用设置的字�?
		style.setFont(font);
		// 设置自动换行;
		style.setWrapText(false);
		// 设置水平对齐的样式为居中对齐;
		style.setAlignment(CellStyle.ALIGN_CENTER);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		return style;
	}
	
	public static void main(String[] args)
	{
		String readPath = "D:\\test.xlsx";
		List<String[]> aa = ExcelUtil.getInstance().read(readPath, 9);
		for (int i = 0; i < aa.size(); i++)
		{
			System.out.println(aa.get(i)[0]);
			System.out.println(aa.get(i)[1]);
			System.out.println(aa.get(i)[2]);
			System.out.println(aa.get(i)[3]);
			System.out.println(aa.get(i)[4]);
			System.out.println(aa.get(i)[5]);
			System.out.println(aa.get(i)[6]);
			System.out.println(aa.get(i)[7]);
			System.out.println(aa.get(i)[8]);
		}
	}
	
	private static HSSFWorkbook wb;
	
	private static CellStyle titleStyle; // 标题行样式
	private static Font titleFont; // 标题行字体
	private static CellStyle dateStyle; // 日期行样式
	private static Font dateFont; // 日期行字体
	private static CellStyle headStyle; // 表头行样式
	private static Font headFont; // 表头行字体
	private static CellStyle contentStyle; // 内容行样式
	private static Font contentFont; // 内容行字体
	
	/**
	 * 导出文件
	 * 
	 * @param setInfo
	 * @param outputExcelFileName
	 * @return
	 * @throws IOException
	 */
	public static boolean export2File(ExcelExportData setInfo, String outputExcelFileName) throws Exception
	{
		return FileUtil.write(outputExcelFileName, export2ByteArray(setInfo), true, true);
	}
	
	/**
	 * 导出到byte数组
	 * 
	 * @param setInfo
	 * @return
	 * @throws Exception
	 */
	public static byte[] export2ByteArray(ExcelExportData setInfo) throws Exception
	{
		return export2Stream(setInfo).toByteArray();
	}
	
	/**
	 * 导出到流
	 * 
	 * @param setInfo
	 * @return
	 * @throws Exception
	 */
	public static ByteArrayOutputStream export2Stream(ExcelExportData setInfo) throws Exception
	{
		init();
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		Set<Entry<String, List<?>>> set = setInfo.getDataMap().entrySet();
		String[] sheetNames = new String[setInfo.getDataMap().size()];
		int sheetNameNum = 0;
		for (Entry<String, List<?>> entry : set)
		{
			sheetNames[sheetNameNum] = entry.getKey();
			sheetNameNum++;
		}
		HSSFSheet[] sheets = getSheets(setInfo.getDataMap().size(), sheetNames);
		int sheetNum = 0;
		for (Entry<String, List<?>> entry : set)
		{
			// Sheet
			List<?> objs = entry.getValue();
			
			// 标题行
			createTableTitleRow(setInfo, sheets, sheetNum);
			
			// 日期行
			createTableDateRow(setInfo, sheets, sheetNum);
			
			// 表头
			creatTableHeadRow(setInfo, sheets, sheetNum);
			
			// 表体
			String[] fieldNames = setInfo.getFieldNames().get(sheetNum);
			
			int rowNum = 3;
			for (Object obj : objs)
			{
				HSSFRow contentRow = sheets[sheetNum].createRow(rowNum);
				contentRow.setHeight((short) 300);
				HSSFCell[] cells = getCells(contentRow, setInfo.getFieldNames().get(sheetNum).length);
				int cellNum = 1; // 去掉一列序号，因此从1开始
				if (fieldNames != null)
				{
					for (int num = 0; num < fieldNames.length; num++)
					{
						
						Object value = ReflectionUtil.invokeGetterMethod(obj, fieldNames[num]);
						cells[cellNum].setCellValue(value == null ? "" : value.toString());
						cellNum++;
					}
				}
				rowNum++;
			}
			adjustColumnSize(sheets, sheetNum, fieldNames); // 自动调整列宽
			sheetNum++;
		}
		wb.write(outputStream);
		return outputStream;
	}
	
	/**
	 * @Description: 初始化
	 */
	private static void init()
	{
		wb = new HSSFWorkbook();
		
		titleFont = wb.createFont();
		titleStyle = wb.createCellStyle();
		dateStyle = wb.createCellStyle();
		dateFont = wb.createFont();
		headStyle = wb.createCellStyle();
		headFont = wb.createFont();
		contentStyle = wb.createCellStyle();
		contentFont = wb.createFont();
		
		initTitleCellStyle();
		initTitleFont();
		initDateCellStyle();
		initDateFont();
		initHeadCellStyle();
		initHeadFont();
		initContentCellStyle();
		initContentFont();
	}
	
	/**
	 * @Description: 自动调整列宽
	 */
	private static void adjustColumnSize(HSSFSheet[] sheets, int sheetNum, String[] fieldNames)
	{
		for (int i = 0; i < fieldNames.length + 1; i++)
		{
			sheets[sheetNum].autoSizeColumn(i, true);
		}
	}
	
	/**
	 * @Description: 创建标题行(需合并单元格)
	 */
	private static void createTableTitleRow(ExcelExportData setInfo, HSSFSheet[] sheets, int sheetNum)
	{
		CellRangeAddress titleRange = new CellRangeAddress(0, 0, 0, setInfo.getFieldNames().get(sheetNum).length);
		sheets[sheetNum].addMergedRegion(titleRange);
		HSSFRow titleRow = sheets[sheetNum].createRow(0);
		titleRow.setHeight((short) 800);
		HSSFCell titleCell = titleRow.createCell(0);
		titleCell.setCellStyle(titleStyle);
		titleCell.setCellValue(setInfo.getTitles()[sheetNum]);
	}
	
	/**
	 * @Description: 创建日期行(需合并单元格)
	 */
	private static void createTableDateRow(ExcelExportData setInfo, HSSFSheet[] sheets, int sheetNum)
	{
		CellRangeAddress dateRange = new CellRangeAddress(1, 1, 0, setInfo.getFieldNames().get(sheetNum).length);
		sheets[sheetNum].addMergedRegion(dateRange);
		HSSFRow dateRow = sheets[sheetNum].createRow(1);
		dateRow.setHeight((short) 350);
		HSSFCell dateCell = dateRow.createCell(0);
		dateCell.setCellStyle(dateStyle);
		// dateCell.setCellValue("导出时间：" + new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
		// .format(new Date()));
		dateCell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
	}
	
	/**
	 * @Description: 创建表头行(需合并单元格)
	 */
	private static void creatTableHeadRow(ExcelExportData setInfo, HSSFSheet[] sheets, int sheetNum)
	{
		// 表头
		HSSFRow headRow = sheets[sheetNum].createRow(2);
		headRow.setHeight((short) 350);
		// 序号列
		HSSFCell snCell = headRow.createCell(0);
		snCell.setCellStyle(headStyle);
		snCell.setCellValue("序号");
		// 列头名称
		for (int num = 1, len = setInfo.getColumnNames().get(sheetNum).length; num <= len; num++)
		{
			HSSFCell headCell = headRow.createCell(num);
			headCell.setCellStyle(headStyle);
			headCell.setCellValue(setInfo.getColumnNames().get(sheetNum)[num - 1]);
		}
	}
	
	/**
	 * @Description: 创建所有的Sheet
	 */
	private static HSSFSheet[] getSheets(int num, String[] names)
	{
		HSSFSheet[] sheets = new HSSFSheet[num];
		for (int i = 0; i < num; i++)
		{
			sheets[i] = wb.createSheet(names[i]);
		}
		return sheets;
	}
	
	/**
	 * @Description: 创建内容行的每一列(附加一列序号)
	 */
	private static HSSFCell[] getCells(HSSFRow contentRow, int num)
	{
		HSSFCell[] cells = new HSSFCell[num + 1];
		
		for (int i = 0, len = cells.length; i < len; i++)
		{
			cells[i] = contentRow.createCell(i);
			cells[i].setCellStyle(contentStyle);
		}
		
		// 设置序号列值，因为出去标题行和日期行，所有-2
		cells[0].setCellValue(contentRow.getRowNum() - 2);
		
		return cells;
	}
	
	/**
	 * @Description: 初始化标题行样式
	 */
	private static void initTitleCellStyle()
	{
		titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
		titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		titleStyle.setFont(titleFont);
		titleStyle.setFillBackgroundColor(IndexedColors.SKY_BLUE.getIndex());
	}
	
	/**
	 * @Description: 初始化日期行样式
	 */
	private static void initDateCellStyle()
	{
		dateStyle.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);
		dateStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		dateStyle.setFont(dateFont);
		dateStyle.setFillBackgroundColor(IndexedColors.SKY_BLUE.getIndex());
	}
	
	/**
	 * @Description: 初始化表头行样式
	 */
	private static void initHeadCellStyle()
	{
		headStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		headStyle.setFont(headFont);
		headStyle.setFillBackgroundColor(IndexedColors.YELLOW.getIndex());
		headStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
		headStyle.setBorderBottom(CellStyle.BORDER_THIN);
		headStyle.setBorderLeft(CellStyle.BORDER_THIN);
		headStyle.setBorderRight(CellStyle.BORDER_THIN);
		headStyle.setTopBorderColor(IndexedColors.BLUE.getIndex());
		headStyle.setBottomBorderColor(IndexedColors.BLUE.getIndex());
		headStyle.setLeftBorderColor(IndexedColors.BLUE.getIndex());
		headStyle.setRightBorderColor(IndexedColors.BLUE.getIndex());
	}
	
	/**
	 * @Description: 初始化内容行样式
	 */
	private static void initContentCellStyle()
	{
		contentStyle.setAlignment(CellStyle.ALIGN_CENTER);
		contentStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		contentStyle.setFont(contentFont);
		contentStyle.setBorderTop(CellStyle.BORDER_THIN);
		contentStyle.setBorderBottom(CellStyle.BORDER_THIN);
		contentStyle.setBorderLeft(CellStyle.BORDER_THIN);
		contentStyle.setBorderRight(CellStyle.BORDER_THIN);
		contentStyle.setTopBorderColor(IndexedColors.BLUE.getIndex());
		contentStyle.setBottomBorderColor(IndexedColors.BLUE.getIndex());
		contentStyle.setLeftBorderColor(IndexedColors.BLUE.getIndex());
		contentStyle.setRightBorderColor(IndexedColors.BLUE.getIndex());
		contentStyle.setWrapText(true); // 字段换行
	}
	
	/**
	 * @Description: 初始化标题行字体
	 */
	private static void initTitleFont()
	{
		titleFont.setFontName("华文楷体");
		titleFont.setFontHeightInPoints((short) 20);
		titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		titleFont.setCharSet(Font.DEFAULT_CHARSET);
		titleFont.setColor(IndexedColors.BLUE_GREY.getIndex());
	}
	
	/**
	 * @Description: 初始化日期行字体
	 */
	private static void initDateFont()
	{
		dateFont.setFontName("隶书");
		dateFont.setFontHeightInPoints((short) 10);
		dateFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		dateFont.setCharSet(Font.DEFAULT_CHARSET);
		dateFont.setColor(IndexedColors.BLUE_GREY.getIndex());
	}
	
	/**
	 * @Description: 初始化表头行字体
	 */
	private static void initHeadFont()
	{
		headFont.setFontName("宋体");
		headFont.setFontHeightInPoints((short) 10);
		headFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headFont.setCharSet(Font.DEFAULT_CHARSET);
		headFont.setColor(IndexedColors.BLUE_GREY.getIndex());
	}
	
	/**
	 * @Description: 初始化内容行字体
	 */
	private static void initContentFont()
	{
		contentFont.setFontName("宋体");
		contentFont.setFontHeightInPoints((short) 10);
		contentFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
		contentFont.setCharSet(Font.DEFAULT_CHARSET);
		contentFont.setColor(IndexedColors.BLUE_GREY.getIndex());
	}
	
	/**
	 * Excel导出数据类
	 * 
	 * @author jimmy
	 * 		
	 */
	public static class ExcelExportData
	{
		
		/**
		 * 导出数据 key:String 表示每个Sheet的名称 value:List<?> 表示每个Sheet里的所有数据行
		 */
		private LinkedHashMap<String, List<?>> dataMap;
		
		/**
		 * 每个Sheet里的顶部大标题
		 */
		private String[] titles;
		
		/**
		 * 单个sheet里的数据列标题
		 */
		private List<String[]> columnNames;
		
		/**
		 * 单个sheet里每行数据的列对应的对象属性名称
		 */
		private List<String[]> fieldNames;
		
		public List<String[]> getFieldNames()
		{
			return fieldNames;
		}
		
		public void setFieldNames(List<String[]> fieldNames)
		{
			this.fieldNames = fieldNames;
		}
		
		public String[] getTitles()
		{
			return titles;
		}
		
		public void setTitles(String[] titles)
		{
			this.titles = titles;
		}
		
		public List<String[]> getColumnNames()
		{
			return columnNames;
		}
		
		public void setColumnNames(List<String[]> columnNames)
		{
			this.columnNames = columnNames;
		}
		
		public LinkedHashMap<String, List<?>> getDataMap()
		{
			return dataMap;
		}
		
		public void setDataMap(LinkedHashMap<String, List<?>> dataMap)
		{
			this.dataMap = dataMap;
		}
		
	}
	
}
