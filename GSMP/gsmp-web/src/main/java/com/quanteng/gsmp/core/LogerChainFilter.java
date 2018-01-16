/**
 * 文 件 名:  LogerChainFilter
 * 版    权:  Quanten Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  zping
 * 修改时间:  2017/10/19 0019
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.core;

import javax.servlet.annotation.WebFilter;

import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * <日志追踪链>
 *
 * @author zping
 * @version 2017/10/19 0019
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@WebFilter (urlPatterns = "/gsmp/*", filterName = "LoggerFilterChain")
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
