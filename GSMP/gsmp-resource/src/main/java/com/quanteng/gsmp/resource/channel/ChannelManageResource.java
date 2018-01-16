/**
 * 文 件 名:  ChannelManageResource
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/10/10 0010
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.resource.channel;

import com.quanteng.gsmp.resource.channel.request.DelChannelInfoReq;
import com.quanteng.gsmp.resource.channel.response.ChannelManageResp;
import com.quanteng.gsmp.resource.channel.response.GetChannelInfoListResp;
import com.quanteng.gsmp.resource.channel.domain.ChannelInfo;
import com.quanteng.gsmp.resource.channel.request.GetChannelInfoListReq;

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
 * @version 2017/10/10 0010
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Path("/channelManage")
public interface ChannelManageResource {

    @POST
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    GetChannelInfoListResp queryChannelInfos(@Context HttpServletRequest request, GetChannelInfoListReq req);

    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    ChannelManageResp addChannelInfo(@Context HttpServletRequest request, ChannelInfo channelInfo);

    @POST
    @Path("/mod")
    @Produces(MediaType.APPLICATION_JSON)
    ChannelManageResp modChannelInfo(@Context HttpServletRequest request, ChannelInfo channelInfo);

    @POST
    @Path("/del")
    @Produces(MediaType.APPLICATION_JSON)
    ChannelManageResp delChannelInfo(@Context HttpServletRequest request, DelChannelInfoReq req);
}