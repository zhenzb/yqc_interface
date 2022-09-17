package com.youqiancheng.controller.file;

import io.swagger.annotations.Api;
import org.junit.Test;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.FileOutputStream;

@Api(tags = "分享链接")
@RestController
public class PdfApplicationTests {

    @Test
    public void contextLoads() {
        String filePath = "D:\\WorkSpace\\IdeaProjects\\pdf\\src\\main\\resources\\templates\\index.html";
        String text ="哈哈";
        String disrPath = "D:\\WorkSpace\\IdeaProjects\\pdf\\src\\main\\resources\\templates";
        String fileName = "t";
        MakeHtml(filePath,text,disrPath,fileName);
    }
    /**
     * @Title: MakeHtml
     * @Description: 创建html
     * @param    filePath 设定模板文件
     * @param    text 添加的内容
     * @param    disrPath  生成html的存放路径
     * @param    fileName  生成html名字
     * @return void    返回类型
     * @throws
     */
    public static void MakeHtml(String filePath,String text,String disrPath,String fileName ){
        try {
            String title = "<h2>"+text+"</h2>";
            System.out.print(filePath);
            String templateContent = "";
            FileInputStream fileinputstream = new FileInputStream(filePath);// 读取模板文件
            int lenght = fileinputstream.available();
            byte bytes[] = new byte[lenght];
            fileinputstream.read(bytes);
            fileinputstream.close();
            templateContent = new String(bytes);
            System.out.print(templateContent);
            //把模板页面上的 ###text### 替换成 title 里的内容
            templateContent = templateContent.replaceAll("###text###", title);
            System.out.print(templateContent);

            String fileame = fileName + ".html";
            fileame = disrPath+"/" + fileame;// 生成的html文件保存路径。
            FileOutputStream fileoutputstream = new FileOutputStream(fileame);// 建立文件输出流
            System.out.print("文件输出路径:");
            System.out.print(fileame);
            byte tag_bytes[] = templateContent.getBytes();
            fileoutputstream.write(tag_bytes);
            fileoutputstream.close();
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }
}
