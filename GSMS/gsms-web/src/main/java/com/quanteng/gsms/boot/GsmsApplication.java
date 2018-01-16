/**
 * 文 件 名:  GsmsApplication
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/9/11 0011
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsms.boot;

import com.quanteng.gsms.config.GsmsConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * <启动类>
 *
 * @author dyc
 * @version 2017/9/11 0011
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@SpringBootApplication
@ComponentScan("com.quanteng.gsms")
@ServletComponentScan(basePackages = "com.quanteng.gsms.core")
public class GsmsApplication {
    private static final Logger logger = LoggerFactory.getLogger(GsmsApplication.class);

    @Bean
    public ServletRegistrationBean registResource() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new ServletContainer(),
                "/gsms/*");
        servletRegistrationBean
                .addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, GsmsConfig.class.getName());
        return servletRegistrationBean;
    }

    public static void main(String[] args) {
        SpringApplication.run(GsmsApplication.class, args);
    }

}
