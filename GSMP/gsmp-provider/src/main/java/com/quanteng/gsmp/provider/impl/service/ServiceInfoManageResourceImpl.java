/**
 * 文 件 名:  ServiceInfoManageResourceImpl
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/10/23 0023
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.provider.impl.service;

import com.quanteng.gsmp.commom.core.annotation.Resource;
import com.quanteng.gsmp.commom.core.constants.Constants;
import com.quanteng.gsmp.commom.core.utils.DateManager;
import com.quanteng.gsmp.provider.impl.BasicMethod;
import com.quanteng.gsmp.reporsity.entity.ServiceInfo;
import com.quanteng.gsmp.reporsity.mapper.ServiceInfoMapper;
import com.quanteng.gsmp.resource.setvice.ServiceInfoManageResource;
import com.quanteng.gsmp.resource.setvice.domain.ServiceInfoDto;
import com.quanteng.gsmp.resource.setvice.request.DelServiceInfoReq;
import com.quanteng.gsmp.resource.setvice.request.GetServiceInfoReq;
import com.quanteng.gsmp.resource.setvice.response.GetServiceInfoResp;
import com.quanteng.gsmp.resource.setvice.response.ServiceInfoResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author dyc
 * @version 2017/10/23 0023
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Resource
public class ServiceInfoManageResourceImpl extends BasicMethod implements ServiceInfoManageResource
{

	private static final Logger LOGGER = LoggerFactory.getLogger (ServiceInfoManageResourceImpl.class);

	@Autowired
	ServiceInfoMapper serviceInfoMapper;

	@Override
	public GetServiceInfoResp query (HttpServletRequest request, GetServiceInfoReq req)
	{
		LOGGER.debug (String.format ("[ServiceInfoManageResourceImpl].[query]---->[GetServiceInfoReq]:%s", req));
		GetServiceInfoResp resp = new GetServiceInfoResp ();
		List<ServiceInfoDto> serviceInfos = new ArrayList<> ();

		//判断是否具有权限，无权限则不允许操作
		boolean hasAuth = auth (request);
		if (! hasAuth)
		{
			resp.setResultCode (Constants.AUTH_FAIL_CODE);
			resp.setResultMsg (Constants.AUTH_FAIL_MSG);
			LOGGER.debug ("[ServiceInfoManageResourceImpl].[query]---->ERROR![Auth is Fail!]");
			return resp;
		}
		if (null == req)
		{
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug ("[ServiceInfoManageResourceImpl].[query]---->ERROR![GetServiceInfoReq is null]");
			return resp;
		}
		if (req.getPageIndex () == null)
		{
			req.setPageIndex (1);
		}
		if (req.getPageSize () == null)
		{
			req.setPageSize (10);
		}

		try
		{
			Map<String, Object> params = new HashMap ();
			if (null != req.getServiceCode () && ! "".equals (req.getServiceCode ()))
			{
				params.put ("serviceCode", req.getServiceCode ());
			}
			if (null != req.getServiceName () && ! "".equals (req.getServiceName ()))
			{
				params.put ("serviceName", req.getServiceName ());
			}
			//1:查询全部
			if("1".equals (req.getFlag ()))
			{
				params.put ("start", null);
				params.put ("psize", null);
			}
			//分页查询
			else
			{
				params.put ("start", (req.getPageIndex () - 1) * req.getPageSize ());
				params.put ("psize", req.getPageSize ());
			}
			List<ServiceInfo> serviceInfoList = serviceInfoMapper.query (params);
			serviceInfos = getServiceInfos (serviceInfoList);
			int total = serviceInfoMapper.count (params);

			resp.setResultCode (Constants.SUCCESS_CODE);
			resp.setResultMsg (Constants.SUCCESS_MSG);
			resp.setServiceInfos (serviceInfos);
			resp.setTotal (total);
			LOGGER.debug (String.format ("[ServiceInfoManageResourceImpl].[query]---->SUCCESS![GetServiceInfoResp]:%s",
					resp));
			return resp;

		}
		catch (Exception e)
		{
			e.printStackTrace ();
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug (String.format ("[ServiceInfoManageResourceImpl].[query]---->ERROR!%s", e.getMessage ()));
			return resp;
		}
	}

	@Override
	public ServiceInfoResp add (HttpServletRequest request, ServiceInfoDto serviceInfo)
	{
		LOGGER.debug (String.format ("[ServiceInfoManageResourceImpl].[add]---->[ServiceInfoDto]:%s", serviceInfo));
		ServiceInfoResp resp = new ServiceInfoResp ();

		//判断是否具有权限，无权限则不允许操作
		boolean hasAuth = auth (request);
		if (! hasAuth)
		{
			resp.setResultCode (Constants.AUTH_FAIL_CODE);
			resp.setResultMsg (Constants.AUTH_FAIL_MSG);
			LOGGER.debug ("[ServiceInfoManageResourceImpl].[add]---->ERROR![Auth is Fail!]");
			return resp;
		}

		if (null == serviceInfo)
		{
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug ("[ServiceInfoManageResourceImpl].[add]---->ERROR![serviceInfo is null]");
			return resp;
		}

		if (null == serviceInfo.getServiceCode () || "".equals (serviceInfo.getServiceCode ()))
		{
			resp.setResultCode (Constants.PARAMETER_MISS_CODE);
			resp.setResultMsg (Constants.PARAMETER_MISS_MSG + "serviceCode !");
			LOGGER.debug ("[ServiceInfoManageResourceImpl].[add]---->ERROR![Missing parameter : serviceCode]");
			return resp;
		}
		if (null == serviceInfo.getServiceName () || "".equals (serviceInfo.getServiceName ()))
		{
			resp.setResultCode (Constants.PARAMETER_MISS_CODE);
			resp.setResultMsg (Constants.PARAMETER_MISS_MSG + "serviceName !");
			LOGGER.debug ("[ServiceInfoManageResourceImpl].[add]---->ERROR![Missing parameter : serviceName]");
			return resp;
		}

		try
		{

			boolean existFlag = isExist (serviceInfo);
			if (existFlag)
			{
				resp.setResultCode (Constants.DATA_EXSIST_CODE);
				resp.setResultMsg (Constants.DATA_EXSIST_MSG);
				LOGGER.debug ("[ServiceInfoManageResourceImpl].[add]---->ERROR! CountryInfo is exist!");
			}
			else
			{
				ServiceInfo sEntity = transferToEntity (serviceInfo, 1);
				serviceInfoMapper.add (sEntity);
				resp.setResultCode (Constants.SUCCESS_CODE);
				resp.setResultMsg (Constants.SUCCESS_MSG);
				LOGGER.debug ("[ServiceInfoManageResourceImpl].[add]---->SUCCESS!");
			}
			return resp;
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug (String.format ("[ServiceInfoManageResourceImpl].[add]---->ERROR!%s", e.getMessage ()));
			return resp;
		}
	}

	@Override
	public ServiceInfoResp mod (HttpServletRequest request, ServiceInfoDto serviceInfo)
	{
		LOGGER.debug (String.format ("[ServiceInfoManageResourceImpl].[mod]---->[ServiceInfoDto]:%s", serviceInfo));
		ServiceInfoResp resp = new ServiceInfoResp ();
		//判断是否具有权限，无权限则不允许操作
		boolean hasAuth = auth (request);
		if (! hasAuth)
		{
			resp.setResultCode (Constants.AUTH_FAIL_CODE);
			resp.setResultMsg (Constants.AUTH_FAIL_MSG);
			LOGGER.debug ("[ServiceInfoManageResourceImpl].[mod]---->ERROR![Auth is Fail!]");
			return resp;
		}

		if (null == serviceInfo)
		{
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug ("[ServiceInfoManageResourceImpl].[mod]---->ERROR![ServiceInfoDto is null]");
			return resp;
		}

		if (null == serviceInfo.getServiceCode () || "".equals (serviceInfo.getServiceCode ()))
		{
			resp.setResultCode (Constants.PARAMETER_MISS_CODE);
			resp.setResultMsg (Constants.PARAMETER_MISS_MSG + "serviceCode !");
			LOGGER.debug ("[ServiceInfoManageResourceImpl].[mod]---->ERROR![Missing parameter : serviceCode]");
			return resp;
		}
		if (null == serviceInfo.getServiceName () || "".equals (serviceInfo.getServiceName ()))
		{
			resp.setResultCode (Constants.PARAMETER_MISS_CODE);
			resp.setResultMsg (Constants.PARAMETER_MISS_MSG + "serviceName !");
			LOGGER.debug ("[ServiceInfoManageResourceImpl].[mod]---->ERROR![Missing parameter : serviceName]");
			return resp;
		}

		try
		{
			ServiceInfo sEntity = transferToEntity (serviceInfo, 0);
			serviceInfoMapper.modify (sEntity);
			resp.setResultCode (Constants.SUCCESS_CODE);
			resp.setResultMsg (Constants.SUCCESS_MSG);
			LOGGER.debug ("[ServiceInfoManageResourceImpl].[mod]---->SUCCESS!");
			return resp;
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug (String.format ("[ServiceInfoManageResourceImpl].[mod]---->ERROR!%s", e.getMessage ()));
			return resp;
		}
	}

	@Override
	public ServiceInfoResp del (HttpServletRequest request, DelServiceInfoReq req)
	{
		LOGGER.debug (String.format ("[ServiceInfoManageResourceImpl].[del]---->[DelServiceInfoReq]:%s", req));
		ServiceInfoResp resp = new ServiceInfoResp ();
		//判断是否具有权限，无权限则不允许操作
		boolean hasAuth = auth (request);
		if (! hasAuth)
		{
			resp.setResultCode (Constants.AUTH_FAIL_CODE);
			resp.setResultMsg (Constants.AUTH_FAIL_MSG);
			LOGGER.debug ("[ServiceInfoManageResourceImpl].[del]---->ERROR![Auth is Fail!]");
			return resp;
		}

		if (null == req)
		{
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug ("[ServiceInfoManageResourceImpl].[del]---->ERROR![DelServiceInfoReq is null]");
			return resp;
		}

		if (null == req.getServiceId () || "".equals (req.getServiceId ()))
		{
			resp.setResultCode (Constants.PARAMETER_MISS_CODE);
			resp.setResultMsg (Constants.PARAMETER_MISS_MSG + "serviceId !");
			LOGGER.debug ("[ServiceInfoManageResourceImpl].[del]---->ERROR![Missing parameter : serviceId]");
			return resp;
		}

		try
		{
			serviceInfoMapper.delete (req.getServiceId ());
			resp.setResultCode (Constants.SUCCESS_CODE);
			resp.setResultMsg (Constants.SUCCESS_MSG);
			LOGGER.debug ("[ServiceInfoManageResourceImpl].[del]---->SUCCESS!");
			return resp;
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug (String.format ("[ServiceInfoManageResourceImpl].[del]---->ERROR!%s", e.getMessage ()));
			return resp;
		}
	}

	private List<ServiceInfoDto> getServiceInfos (List<ServiceInfo> serviceInfoList)
	{
		List<ServiceInfoDto> serviceInfoDtos = new ArrayList<> ();
		if (serviceInfoList.size () > 0)
		{
			for (int i = 0; i < serviceInfoList.size (); i++)
			{
				ServiceInfo serviceInfo = serviceInfoList.get (i);
				ServiceInfoDto serviceInfoDto = new ServiceInfoDto ();
				serviceInfoDto.setServiceId (serviceInfo.getServiceId ());
				serviceInfoDto.setServiceName (serviceInfo.getServiceName ());
				serviceInfoDto.setServiceCode (serviceInfo.getServiceCode ());
				serviceInfoDto.setRemarks (serviceInfo.getRemarks ());
				serviceInfoDto
						.setCreateTime (DateManager.dateToString (serviceInfo.getCreateTime (), "yyyy-MM-dd HH:mm:ss"));
				serviceInfoDtos.add (serviceInfoDto);
			}
		}
		return serviceInfoDtos;
	}

	private ServiceInfo transferToEntity (ServiceInfoDto serviceInfoDto, int operateType)
	{
		ServiceInfo serviceInfo = new ServiceInfo ();
		String serviceId = "";
		if (operateType == 1)
		{
			serviceId = UUID.randomUUID ().toString ();
		}
		else
		{
			serviceId = serviceInfoDto.getServiceId ();
		}
		serviceInfo.setServiceId (serviceId);
		serviceInfo.setServiceCode (serviceInfoDto.getServiceCode ());
		serviceInfo.setServiceName (serviceInfoDto.getServiceName ());
		serviceInfo.setRemarks (serviceInfoDto.getRemarks ());
		return serviceInfo;
	}

	private boolean isExist (ServiceInfoDto serviceInfoDto)
	{
		boolean exist = false;
		Map<String, Object> params = new HashMap ();
		params.put ("serviceCode", serviceInfoDto.getServiceCode ());
		params.put ("serviceName", serviceInfoDto.getServiceName ());
		List<ServiceInfo> serviceInfoList = serviceInfoMapper.query (params);
		if (serviceInfoList.size () > 0)
		{
			for (int i = 0; i < serviceInfoList.size (); i++)
			{
				ServiceInfo serviceInfo = serviceInfoList.get (0);
				if (serviceInfo.getServiceCode ().equals (serviceInfoDto.getServiceCode ()) && serviceInfo
						.getServiceName ().equals (serviceInfoDto.getServiceName ()))
				{
					exist = true;
					break;
				}
			}
		}
		return exist;
	}
}
