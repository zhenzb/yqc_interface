package com;

import com.handongkeji.config.configuration.UniqueNameGenerator;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.MultipartConfigElement;
import javax.sql.DataSource;

// Springboot便捷注解
@SpringBootApplication
// 扫描dao层目录
@MapperScan({"com.youqiancheng.mybatis.dao"})
// 设置扫描整个应用程序并解决不同包结构下，同名类冲突
@ComponentScan(basePackages = {"com.*"}, nameGenerator = UniqueNameGenerator.class)
// 默认开启事务管理
@EnableTransactionManagement
//定时器任务执行
@EnableScheduling
@EnableSwagger2
public class Application {
    @Autowired
    DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        //关闭启动Banner
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
//    @Bean
//    public SqlSessionFactory sqlSessionFactory() throws Exception {
//        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
//        factoryBean.setDataSource(dataSource);
//        return factoryBean.getObject();
//    }
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大
        factory.setMaxFileSize("300MB"); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("500MB");

//        File tmpFile = new File("D:/mijiatemp/");
//        if (!tmpFile.exists()) {
//            tmpFile.mkdirs();
//        }
//        factory.setLocation("D:/mijiatemp/");
        return factory.createMultipartConfig();
    }



}

