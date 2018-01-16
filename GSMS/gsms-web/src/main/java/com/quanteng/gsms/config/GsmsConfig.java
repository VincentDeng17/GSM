/**
 * 文 件 名:  GsmsConfig
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/9/11 0011
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsms.config;

import com.quanteng.gsms.commom.core.annotation.Resource;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;

import javax.ws.rs.ext.Provider;
import java.util.stream.Collectors;

/**
 * <配置项初始化>
 *
 * @author dyc
 * @version 2017/9/11 0011
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class GsmsConfig extends ResourceConfig {

    private static final String RESTFUL_BASE_PACKAGE = "com.quanteng.gsms.provider.impl";

    public GsmsConfig()
    {
        register (RequestContextFilter.class);
        register (JacksonFeature.class);
        /*启用扫描*/
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider (false);

		/*扫描Path，provider*/
        scanner.addIncludeFilter (new AnnotationTypeFilter(Resource.class));
        scanner.addIncludeFilter (new AnnotationTypeFilter (Provider.class));

        registerClasses (scanner.findCandidateComponents (RESTFUL_BASE_PACKAGE).stream ()
                .map (beanDefinition -> ClassUtils
                        .resolveClassName (beanDefinition.getBeanClassName (), this.getClassLoader ()))
                .collect (Collectors.toSet ()));
    }

}
