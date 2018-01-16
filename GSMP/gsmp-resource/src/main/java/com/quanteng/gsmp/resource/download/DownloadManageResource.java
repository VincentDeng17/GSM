/**
 * 文 件 名:  DownloadManageResource
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/9/11 0011
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.resource.download;

import com.quanteng.gsmp.resource.download.request.GetConversionReq;
import com.quanteng.gsmp.resource.download.request.GetDownloadDetailsReq;
import com.quanteng.gsmp.resource.download.request.GetDownloadInfoReq;
import com.quanteng.gsmp.resource.download.response.GetConversionResp;
import com.quanteng.gsmp.resource.download.response.GetDownloadDetailsListResp;
import com.quanteng.gsmp.resource.download.response.GetDownloadInfoListResp;

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
@Path("/download")
public interface DownloadManageResource {

    @POST
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    GetDownloadInfoListResp queryDownloadInfo(@Context HttpServletRequest request, GetDownloadInfoReq req);

    @POST
    @Path("/queryDetails")
    @Produces(MediaType.APPLICATION_JSON)
    GetDownloadDetailsListResp queryDownloadDetails(@Context HttpServletRequest request, GetDownloadDetailsReq req);

    /*
        @POST
        @Path("/queryConversion")
        @Produces(MediaType.APPLICATION_JSON)
        GetConversionResp queryConversion(@Context HttpServletRequest request, GetConversionReq req);
    */
    @POST
    @Path("/exportExcel")
    @Produces(MediaType.APPLICATION_JSON)
    void exportExcel(@Context HttpServletRequest request, @Context HttpServletResponse response);

    @POST
    @Path("/queryConversion")
    @Produces(MediaType.APPLICATION_JSON)
    GetConversionResp queryConversion(@Context HttpServletRequest request, GetConversionReq req);
}