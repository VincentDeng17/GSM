/**
 * 文 件 名:  TemplateFileUtil
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/9/29 0029
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.commom.core.export;

import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * <模板读取工具类>
 *
 * @author dyc
 * @version 2017/9/29 0029
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class TemplateFileUtil {

    public static FileInputStream getTemplates(String tempName) throws FileNotFoundException {
        return new FileInputStream(ResourceUtils.getFile("classpath:excel-templates/" + tempName));
    }
}
