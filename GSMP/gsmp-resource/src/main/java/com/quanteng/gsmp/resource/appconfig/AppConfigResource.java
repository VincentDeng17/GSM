/**
 * 文 件 名:  AppConfigResource
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  zhouhaofeng
 * 修改时间:  2017/11/6
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.resource.appconfig;

import com.quanteng.gsmp.resource.appconfig.request.*;
import com.quanteng.gsmp.resource.appconfig.response.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * 应用配置接口
 *
 * @author zhouhaofeng
 * @version 2017/11/6
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Path ("/appConfig")
public interface AppConfigResource
{
	/**
	 * 新增应用配置
	 *
	 * @param addAppConfigReq 新增应用配置请求
	 * @param request         http请求
	 * @return 新增应用配置响应
	 */
	@POST
	@Path ("/add")
	@Produces (MediaType.APPLICATION_JSON)
	AddAppConfigResp addAppConfig (@Context HttpServletRequest request, AddAppConfigReq addAppConfigReq);

	/**
	 * 删除应用配置
	 *
	 * @param delAppConfigReq 删除应用配置请求
	 * @param request         http请求
	 * @return 删除应用配置响应
	 */
	@POST
	@Path ("/delete")
	@Produces (MediaType.APPLICATION_JSON)
	DelAppConfigResp delAppConfig (@Context HttpServletRequest request, DelAppConfigReq delAppConfigReq);

	/**
	 * 修改应用配置
	 *
	 * @param modifyAppConfigReq 修改应用配置请求
	 * @param request            http请求
	 * @return 新增应用配置响应
	 */
	@POST
	@Path ("/modify")
	@Produces (MediaType.APPLICATION_JSON)
	ModifyAppConfigResp modifyAppConfig (@Context HttpServletRequest request, ModifyAppConfigReq modifyAppConfigReq);

	/**
	 * 批量删除应用配置
	 *
	 * @param batchDelAppConfigReq 批量删除应用配置请求
	 * @param request         http请求
	 * @return 批量删除应用配置响应
	 */
	@POST
	@Path ("/batchDelete")
	@Produces (MediaType.APPLICATION_JSON)
	BatchDelAppConfigResp batchDelAppConfig (@Context HttpServletRequest request,
			BatchDelAppConfigReq batchDelAppConfigReq);

	/**
	 * 查询应用配置列表
	 *
	 * @param qryAppConfigReq 查询应用配置请求
	 * @param request         http请求
	 * @return 查询应用配置响应
	 */
	@POST
	@Path ("/query")
	@Produces (MediaType.APPLICATION_JSON)
	QryAppConfigResp qryAppConfig (@Context HttpServletRequest request, QryAppConfigReq qryAppConfigReq);
}
