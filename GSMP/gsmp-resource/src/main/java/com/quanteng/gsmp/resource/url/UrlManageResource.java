/**
 * 文 件 名:  UrlManageResource
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/9/11 0011
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.resource.url;

import com.quanteng.gsmp.resource.url.domain.UrlManageInfo;
import com.quanteng.gsmp.resource.url.request.DelUrlManageInfoReq;
import com.quanteng.gsmp.resource.url.request.GetUrlManageInfoListReq;
import com.quanteng.gsmp.resource.url.response.GetUrlManageInfoListResp;
import com.quanteng.gsmp.resource.url.response.UrlManageResp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author dyc
 * @version 2017/9/11 0011
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Path("/urlManage")
public interface UrlManageResource {

    @POST
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    GetUrlManageInfoListResp queryUrlInfo(@Context HttpServletRequest request, GetUrlManageInfoListReq req);

    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    UrlManageResp addUrlInfo(@Context HttpServletRequest request, UrlManageInfo urlManageInfo);

    @POST
    @Path("/mod")
    @Produces(MediaType.APPLICATION_JSON)
    UrlManageResp modUrlInfo(@Context HttpServletRequest request, UrlManageInfo urlManageInfo);

    @POST
    @Path("/del")
    @Produces(MediaType.APPLICATION_JSON)
    UrlManageResp delUrlInfo(@Context HttpServletRequest request, DelUrlManageInfoReq req);

    @POST
    @Path("/export")
    @Produces(MediaType.APPLICATION_JSON)
    UrlManageResp exportExcl(@Context HttpServletRequest request, GetUrlManageInfoListReq req, @Context HttpServletResponse response);
}