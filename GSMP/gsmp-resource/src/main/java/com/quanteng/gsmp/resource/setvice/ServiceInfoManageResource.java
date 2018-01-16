/**
 * 文 件 名:  ServiceInfoManageResource
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/10/21 0021
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.resource.setvice;

import com.quanteng.gsmp.resource.setvice.domain.ServiceInfoDto;
import com.quanteng.gsmp.resource.setvice.request.DelServiceInfoReq;
import com.quanteng.gsmp.resource.setvice.request.GetServiceInfoReq;
import com.quanteng.gsmp.resource.setvice.response.GetServiceInfoResp;
import com.quanteng.gsmp.resource.setvice.response.ServiceInfoResp;

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
 * @version 2017/10/21 0021
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Path("/serviceInfo")
public interface ServiceInfoManageResource {

    @POST
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    GetServiceInfoResp query(@Context HttpServletRequest request, GetServiceInfoReq req);

    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    ServiceInfoResp add(@Context HttpServletRequest request, ServiceInfoDto serviceInfo);

    @POST
    @Path("/mod")
    @Produces(MediaType.APPLICATION_JSON)
    ServiceInfoResp mod(@Context HttpServletRequest request, ServiceInfoDto serviceInfo);

    @POST
    @Path("/del")
    @Produces(MediaType.APPLICATION_JSON)
    ServiceInfoResp del(@Context HttpServletRequest request, DelServiceInfoReq req);

}