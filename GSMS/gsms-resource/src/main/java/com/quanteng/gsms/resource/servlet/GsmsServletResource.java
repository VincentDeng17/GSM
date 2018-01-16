/**
 * 文 件 名:  GsmsServletResource
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/10/17 0017
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsms.resource.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author dyc
 * @version 2017/10/17 0017
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Path("/s")
public interface GsmsServletResource {

    @GET
    @Path("/promotional")
    @Produces(MediaType.APPLICATION_JSON)
    void promotional(@Context HttpServletRequest request, @Context HttpServletResponse response);

}