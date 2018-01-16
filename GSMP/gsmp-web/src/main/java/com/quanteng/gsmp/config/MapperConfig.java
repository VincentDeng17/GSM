/**
 * 文 件 名:  MapperConfig
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/9/11 0011
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <扫描Mapper，并注入SqlSessionFactory工厂类> <utoConfigureAfter : 必须加上，因为初始化MapperScanConfig类的时候，依赖SqlSessionFactory类>
 *
 * @author dyc
 * @version 2017/9/11 0011
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Configuration
@AutoConfigureAfter(InitConfig.class)
public class MapperConfig {
    private static final String BASE_PACK = "com.quanteng.gsmp.reporsity.mapper";

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer ()
    {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer ();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName ("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage (BASE_PACK);
        return mapperScannerConfigurer;
    }

}
