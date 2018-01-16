/**
 * 文 件 名:  CountryManageResource
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/10/21 0021
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.resource.country;

import com.quanteng.gsmp.resource.country.request.DelCountryReq;
import com.quanteng.gsmp.resource.country.request.GetCountryReq;
import com.quanteng.gsmp.resource.country.response.CountryResp;
import com.quanteng.gsmp.resource.country.domain.CountryInfo;
import com.quanteng.gsmp.resource.country.response.GetCountryResp;

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
@Path("/country")
public interface CountryManageResource {

    @POST
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    GetCountryResp query(@Context HttpServletRequest request, GetCountryReq req);

    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    CountryResp add(@Context HttpServletRequest request, CountryInfo countryInfo);

    @POST
    @Path("/mod")
    @Produces(MediaType.APPLICATION_JSON)
    CountryResp mod(@Context HttpServletRequest request, CountryInfo countryInfo);

    @POST
    @Path("/del")
    @Produces(MediaType.APPLICATION_JSON)
    CountryResp del(@Context HttpServletRequest request, DelCountryReq req);
}