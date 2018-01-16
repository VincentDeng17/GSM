/**
 * 文 件 名:  LogerChainFilter
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/11/10 0010
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsms.core;

import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * <日志追踪链> <功能详细描述>
 *
 * @author dyc
 * @version 2017/11/10 0010
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@WebFilter(urlPatterns = "/gsms/*", filterName = "LoggerFilterChain")
public class LogerChainFilter implements Filter
{

    /**
     * 打印日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger (LogerChainFilter.class);

    /**
     * 日志链
     */
    private static final String LOG_TRACEID = "traceID";

    @Override
    public void init (FilterConfig filterConfig) throws ServletException
    {
        LOGGER.debug ("Logger chain filter init ... ");
    }

    @Override
    public void doFilter (ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException
    {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String traceID = UUID.randomUUID ().toString ().replace ("-","");
        ThreadContext.put (LOG_TRACEID, traceID);
        response.addHeader (LOG_TRACEID, traceID);
        filterChain.doFilter (servletRequest, response);
        ThreadContext.clearAll ();
    }

    @Override
    public void destroy ()
    {
        LOGGER.debug ("Logger chain filter destroy ... ");
    }
}
