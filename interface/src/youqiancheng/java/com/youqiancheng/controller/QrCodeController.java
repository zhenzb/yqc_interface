package com.youqiancheng.controller;

import com.youqiancheng.util.QrCodeUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

@RestController
@RequestMapping("/qrCode")
public class QrCodeController {
    /**
     * 生成 普通二维码
     */
    @GetMapping(value = "/getCommonQRCode")
    public void getCommonQRCode(HttpServletResponse response, String url) throws Exception {
        ServletOutputStream stream = null;
        try {
            stream = response.getOutputStream();
            //使用工具类生成二维码
            QrCodeUtil.encode(url, stream);
        } finally {
            if (stream != null) {
                stream.flush();
                stream.close();
            }
        }
    }

    /**
     * 生成 带有logo二维码
     */
    @GetMapping(value = "/getQRCodeWithLogo")
    public void getQRCodeWithLogo(HttpServletResponse response, String url) throws Exception {
        ServletOutputStream stream = null;
        try {
            stream = response.getOutputStream();
            // logo 地址
            String logoPath = Thread.currentThread().getContextClassLoader().getResource("").getPath()
                    + "templates" + File.separator + "advator.jpg";
            // String logoPath = "springboot-demo-list/qr-code/src/main/resources/templates/advator.jpg";
            //使用工具类生成二维码
            QrCodeUtil.encode(url, logoPath, stream, true);
        } finally {
            if (stream != null) {
                stream.flush();
                stream.close();
            }
        }
    }
}
