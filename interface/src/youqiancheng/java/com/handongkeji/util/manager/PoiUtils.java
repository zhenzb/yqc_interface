package com.handongkeji.util.manager;

import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.mybatis.model.D01OrderDO;
import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class PoiUtils {

    // 将数据导出成excel文件
    public static ResponseEntity<byte[]> exportUser2Excel(String []title,List<D01OrderDO> orderDOList) {
        HttpHeaders headers = null;
        ByteArrayOutputStream baos = null;
        try {
            //1.创建Excel文档
            HSSFWorkbook workbook = new HSSFWorkbook();
            //2.创建文档摘要
           // workbook.createInformationProperties();
            //3.获取文档信息，并配置
//            DocumentSummaryInformation dsi = workbook.getDocumentSummaryInformation();
//            //3.1文档类别
//            dsi.setCategory("人员信息");
//            //3.2设置文档管理员
//            dsi.setManager("hangge");
//            //3.3设置组织机构
//            dsi.setCompany("航歌");
//            //4.获取摘要信息并配置
//            SummaryInformation si = workbook.getSummaryInformation();
//            //4.1设置文档主题
//            si.setSubject("人员信息表");
//            //4.2.设置文档标题
//            si.setTitle("人员信息");
//            //4.3 设置文档作者
//            si.setAuthor("hangge");
//            //4.4设置文档备注
//            si.setComments("备注信息暂无");
            //创建Excel表单
            HSSFSheet sheet = workbook.createSheet("商家订单列表");
            //创建日期显示格式
            HSSFCellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
            //创建标题的显示样式
            HSSFCellStyle headerStyle = workbook.createCellStyle();
           // headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
            //headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            //定义列的宽度
            sheet.setColumnWidth(0, 20 * 256);
//            sheet.setColumnWidth(1, 12 * 256);
//            sheet.setColumnWidth(2, 10 * 256);
//            sheet.setColumnWidth(3, 5 * 256);
            sheet.setColumnWidth(4, 20 * 256);
            //5.设置表头
            HSSFRow headerRow = sheet.createRow(0);
            for(int i=0;i<title.length;i++){
                HSSFCell cell0 = headerRow.createCell(i);
                cell0.setCellValue(title[i]);
                cell0.setCellStyle(headerStyle);
            }
            //6.装数据
            for (int i = 0; i < orderDOList.size(); i++) {
                HSSFRow row = sheet.createRow(i + 1);
                D01OrderDO order = orderDOList.get(i);
                row.createCell(0).setCellValue(order.getOrderNo());

                switch (order.getOrderStatus()){
                    case 1:
                        row.createCell(1).setCellValue(StatusConstant.OrderStatus.un_pay.getMsg());
                        break;
                    case 2:
                        row.createCell(1).setCellValue(StatusConstant.OrderStatus.pay.getMsg());
                        break;
                    case 3:
                        row.createCell(1).setCellValue(StatusConstant.OrderStatus.send.getMsg());
                        break;
                    case 4:
                        row.createCell(1).setCellValue(StatusConstant.OrderStatus.end.getMsg());
                        break;
                    case 5:
                        row.createCell(1).setCellValue(StatusConstant.OrderStatus.apply_refund.getMsg());
                        break;
                    case 6:
                        row.createCell(1).setCellValue(StatusConstant.OrderStatus.pass.getMsg());
                        break;
                    case 7:
                        row.createCell(1).setCellValue(StatusConstant.OrderStatus.refuse.getMsg());
                        break;
                    case 8:
                        row.createCell(1).setCellValue(StatusConstant.OrderStatus.refund.getMsg());
                        break;
                    case 9:
                        row.createCell(1).setCellValue(StatusConstant.OrderStatus.cancel.getMsg());
                        break;
                }
                row.createCell(2).setCellValue(order.getShippingName());
                row.createCell(3).setCellValue(order.getShippingPhone());
                row.createCell(4).setCellValue(order.getShippingAddress());
                row.createCell(5).setCellValue(order.getOrderPrice().toString());
                row.createCell(6).setCellValue(order.getNum());
//                HSSFCell birthdayCell = row.createCell(4);
//                birthdayCell.setCellValue(user.getBirthday());
//                birthdayCell.setCellStyle(dateCellStyle);
            }
            headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment",
                    new String("商家订单.xls".getBytes("UTF-8"), "iso-8859-1"));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            baos = new ByteArrayOutputStream();
            workbook.write(baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<byte[]>(baos.toByteArray(), headers, HttpStatus.CREATED);
    }
}
