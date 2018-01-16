/**
 * 文 件 名:  LogManager
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/10/17 0017
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsms.commom.core.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <日志记录工具类>
 *
 * @author dyc
 * @version 2017/10/17 0017
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class LogManager {
    /**
     * 定义Third MSGLOG
     */
    private static final Log THIRD_MSG_LOGGER = LogFactory.getLog("thirdMsgLogger");

    /**
     * 记录Third MSG日志
     *
     * @tags @param msg 日志信息
     * @tags @param msgLogger return void
     * @see [类、类#方法、类#成员]
     */
    public static void thirdMsgLog(String msg, Log msgLogger) {
        if (null != msg) {
            try {
                Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                Matcher m = p.matcher(msg);
                msg = m.replaceAll("");
            } catch (Exception e) {
                THIRD_MSG_LOGGER.error("Pattern replaceAll Error");
            }
        }
        if (msgLogger == null) {
            if (THIRD_MSG_LOGGER.isInfoEnabled()) {
                THIRD_MSG_LOGGER.info(msg);
            }
        } else {
            if (msgLogger.isInfoEnabled()) {
                msgLogger.info(msg);
            }
        }
    }
}
