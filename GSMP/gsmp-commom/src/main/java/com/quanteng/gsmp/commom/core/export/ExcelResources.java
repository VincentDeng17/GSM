/**
 * 文 件 名:  ExcelResources
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/9/29 0029
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.commom.core.export;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <用来在对象的get方法上加入的annotation，通过该annotation说明某个属性所对应的标题>
 *
 * @author dyc
 * @version 2017/9/29 0029
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelResources {
    /**
     * 属性的标题名称
     * @return
     */
    String title();
    /**
     * 在excel的顺序
     * @return
     */
    int order() default 9999;
}