/**
 * 文 件 名:  UserResource
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/9/13 0013
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.resource.user;

import com.quanteng.gsmp.resource.user.request.UserLoginReq;
import com.quanteng.gsmp.resource.user.response.UserLoginResp;
import com.quanteng.gsmp.resource.user.response.UserLogoutResp;

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
 * @version 2017/9/13 0013
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Path("/user")
public interface UserResource {

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    UserLoginResp login(@Context HttpServletRequest request, UserLoginReq req, @Context HttpServletResponse response);

    @POST
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    UserLogoutResp logout(@Context HttpServletRequest request, @Context HttpServletResponse response);


}