/**
 * 文 件 名:  AppResource
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  zhengbinggui
 * 修改时间:  2017/11/6
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.resource.app;

import com.quanteng.gsmp.resource.app.request.*;
import com.quanteng.gsmp.resource.app.response.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author zhengbinggui
 * @version 2017/11/6
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Path ("/app")
public interface AppResource
{
	@POST
	@Path ("/addApp")
	@Produces (MediaType.APPLICATION_JSON)
	AddAppResp addApp (@Context HttpServletRequest request, AddAppReq req);

	@POST
	@Path ("/modifyApp")
	@Produces (MediaType.APPLICATION_JSON)
	ModifyAppResp modifyApp (@Context HttpServletRequest request, ModifyAppReq req);

	@POST
	@Path ("/batchDelApp")
	@Produces (MediaType.APPLICATION_JSON)
	BatchDelAppResp batchDelApp (@Context HttpServletRequest request, BatchDelAppReq req);

	@POST
	@Path ("/qryAppByPara")
	@Produces (MediaType.APPLICATION_JSON)
	QryAppByParaResp qryAppByPara (@Context HttpServletRequest request, QryAppByParaReq req);

	@POST
	@Path ("/getAppById")
	@Produces (MediaType.APPLICATION_JSON)
	GetAppByIdResp getAppById (@Context HttpServletRequest request, GetAppByIdReq req);
}