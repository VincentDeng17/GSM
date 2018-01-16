/**
 * 文 件 名:  ClickRecordResource
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/10/19 0019
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.resource.click;

import com.quanteng.gsmp.resource.click.response.GetClickRecordResp;
import com.quanteng.gsmp.resource.click.request.GetClickRecordReq;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author dyc
 * @version 2017/10/19 0019
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Path("/click")
public interface ClickRecordResource {

    @POST
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    GetClickRecordResp query(@Context HttpServletRequest request, GetClickRecordReq req);
}