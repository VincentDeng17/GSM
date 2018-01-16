/**
 * 文 件 名:  AppResourceImpl
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  zhengbinggui
 * 修改时间:  2017/11/6
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.provider.impl.app;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.quanten.core.utils.DateUtils;
import com.quanteng.gsmp.commom.core.annotation.Resource;
import com.quanteng.gsmp.commom.core.constants.Constants;
import com.quanteng.gsmp.provider.impl.BasicMethod;
import com.quanteng.gsmp.reporsity.entity.AppInfo;
import com.quanteng.gsmp.reporsity.mapper.AppInfoMapper;
import com.quanteng.gsmp.resource.app.AppResource;
import com.quanteng.gsmp.resource.app.domain.AppVO;
import com.quanteng.gsmp.resource.app.request.*;
import com.quanteng.gsmp.resource.app.response.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author zhengbinggui
 * @version 2017/11/6
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Resource
public class AppResourceImpl extends BasicMethod implements AppResource
{
	private static final Logger LOGGER = LoggerFactory.getLogger (AppResourceImpl.class);

	@Autowired
	private AppInfoMapper appInfoMapper;

	@Override
	public AddAppResp addApp (HttpServletRequest request, AddAppReq req)
	{
		LOGGER.debug (String.format ("[AppResourceImpl].[addApp]---->[AddAppReq]:%s", req));

		AddAppResp resp = new AddAppResp ();

		//判断是否具有权限，无权限则不允许操作
		boolean hasAuth = auth (request);
		if (! hasAuth)
		{
			resp.setResultCode (Constants.AUTH_FAIL_CODE);
			resp.setResultMsg (Constants.AUTH_FAIL_MSG);
			LOGGER.debug ("[AppResourceImpl].[addApp]---->ERROR![Auth is Fail!]");
			return resp;
		}

		if (null == req)
		{
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug ("[AppResourceImpl].[addApp]---->ERROR![AddAppReq is null]");
			return resp;
		}

		if (StringUtils.isEmpty (req.getAppName ()))
		{
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug ("[AppResourceImpl].[addApp]---->ERROR![appName is null]");
			return resp;
		}

		AppInfo appInfo = new AppInfo ();
		appInfo.setAppId (UUID.randomUUID ().toString ().replace ("-", ""));
		appInfo.setAppName (req.getAppName ());
		appInfo.setAppInfo (req.getAppInfo ());
		appInfo.setRemark (req.getRemark ());

		try
		{
			appInfoMapper.add (appInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug (String.format ("[AppResourceImpl].[addApp]---->ERROR!%s"), e.getMessage ());
			return resp;
		}
		LOGGER.debug (String.format ("[AppResourceImpl].[addApp]---->SUCCESS![AddAppResp]:%s", resp));
		return resp;
	}

	@Override
	public ModifyAppResp modifyApp (HttpServletRequest request, ModifyAppReq req)
	{
		LOGGER.debug (String.format ("[AppResourceImpl].[modifyApp]---->[ModifyAppReq]:%s", req));

		ModifyAppResp resp = new ModifyAppResp ();

		//判断是否具有权限，无权限则不允许操作
		boolean hasAuth = auth (request);
		if (! hasAuth)
		{
			resp.setResultCode (Constants.AUTH_FAIL_CODE);
			resp.setResultMsg (Constants.AUTH_FAIL_MSG);
			LOGGER.debug ("[AppResourceImpl].[modifyApp]---->ERROR![Auth is Fail!]");
			return resp;
		}

		if (null == req)
		{
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug ("[AppResourceImpl].[modifyApp]---->ERROR![ModifyAppReq is null]");
			return resp;
		}

		if (StringUtils.isEmpty (req.getAppId ()))
		{
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug ("[AppResourceImpl].[modifyApp]---->ERROR![appId is null]");
			return resp;
		}

		if (StringUtils.isEmpty (req.getAppName ()))
		{
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug ("[AppResourceImpl].[modifyApp]---->ERROR![appName is null]");
			return resp;
		}

		AppInfo appInfo = new AppInfo ();
		appInfo.setAppId (req.getAppId ());
		appInfo.setAppName (req.getAppName ());
		appInfo.setAppInfo (req.getAppInfo ());
		appInfo.setRemark (req.getRemark ());

		try
		{
			appInfoMapper.modify (appInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug (String.format ("[AppResourceImpl].[modifyApp]---->ERROR!%s", e.getMessage ()));
			return resp;
		}
		LOGGER.debug (String.format ("[AppResourceImpl].[modifyApp]---->SUCCESS![ModifyAppResp]:%s", resp));
		return resp;
	}

	@Override
	public BatchDelAppResp batchDelApp (HttpServletRequest request, BatchDelAppReq req)
	{
		LOGGER.debug (String.format ("[AppResourceImpl].[delApp]---->[DelAppReq]:%s", req));

		BatchDelAppResp resp = new BatchDelAppResp ();

		//判断是否具有权限，无权限则不允许操作
		boolean hasAuth = auth (request);
		if (! hasAuth)
		{
			resp.setResultCode (Constants.AUTH_FAIL_CODE);
			resp.setResultMsg (Constants.AUTH_FAIL_MSG);
			LOGGER.debug ("[AppResourceImpl].[delApp]---->ERROR![Auth is Fail!]");
			return resp;
		}

		if (null == req)
		{
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug ("[AppResourceImpl].[delApp]---->ERROR![DelAppReq is null]");
			return resp;
		}

		if (CollectionUtils.isEmpty (req.getAppIds ()))
		{
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug ("[AppResourceImpl].[delApp]---->ERROR![appIds is null]");
			return resp;
		}

		try
		{
			appInfoMapper.delete (req.getAppIds ());
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug ("[AppResourceImpl].[delApp]---->Delete app by appId ERROR!",e);
			return resp;
		}

		LOGGER.debug (String.format ("[AppResourceImpl].[delApp]---->SUCCESS![DelAppResp]:%s", resp));
		return resp;
	}

	@Override
	public QryAppByParaResp qryAppByPara (HttpServletRequest request, QryAppByParaReq req)
	{
		LOGGER.debug (String.format ("[AppResourceImpl].[qryAppByPara]---->[QryAppByParaReq]:%s", req));

		QryAppByParaResp resp = new QryAppByParaResp ();

		//判断是否具有权限，无权限则不允许操作
		boolean hasAuth = auth (request);
		if (! hasAuth)
		{
			resp.setResultCode (Constants.AUTH_FAIL_CODE);
			resp.setResultMsg (Constants.AUTH_FAIL_MSG);
			LOGGER.debug ("[AppResourceImpl].[qryAppByPara]---->ERROR![Auth is Fail!]");
			return resp;
		}

		if (null == req)
		{
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug ("[AppResourceImpl].[qryAppByPara]---->ERROR![QryAppByParaReq is null]");
			return resp;
		}

		Map<String, Object> params = Maps.newHashMap ();
		if (! StringUtils.isEmpty (req.getAppId ()))
		{
			params.put ("appId", req.getAppId ());
		}
		if (! StringUtils.isEmpty (req.getAppName ()))
		{
			params.put ("appName", req.getAppName ());
		}
		if (! StringUtils.isEmpty (req.getAppInfo ()))
		{
			params.put ("appInfo", req.getAppInfo ());
		}
		if (! StringUtils.isEmpty (req.getRemark ()))
		{
			params.put ("remark", req.getRemark ());
		}
		if (! StringUtils.isEmpty (req.getStartTime ()))
		{
			params.put ("startTime", req.getStartTime ());
		}
		if (! StringUtils.isEmpty (req.getEndTime ()))
		{
			params.put ("endTime", req.getEndTime ());
		}
		params.put ("start", (req.getPageIndex () - 1) * req.getPageSize ());
		params.put ("psize", req.getPageSize ());

		List<AppInfo> appInfos = null;
		int total;
		try
		{
			appInfos = appInfoMapper.query (params);
			total = appInfoMapper.count (params);
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug (String.format ("[AppResourceImpl].[qryAppByPara]---->ERROR!%s", e.getMessage ()));
			return resp;
		}

		if (! CollectionUtils.isEmpty (appInfos))
		{
			List<AppVO> appVOs = Lists.newArrayList ();
			for (AppInfo appInfo : appInfos)
			{
				AppVO appVO = transferToAppVO (appInfo);
				appVOs.add (appVO);
			}
			resp.setAppVos (appVOs);
		}
		resp.setTotal (total);

		LOGGER.debug (String.format ("[AppResourceImpl].[qryAppByPara]---->SUCCESS![QryAppByParaResp]:%s", resp));
		return resp;
	}

	@Override
	public GetAppByIdResp getAppById (HttpServletRequest request, GetAppByIdReq req)
	{
		LOGGER.debug (String.format ("[AppResourceImpl].[getAppById]---->[GetAppByIdReq]:%s", req));

		GetAppByIdResp resp = new GetAppByIdResp ();

		//判断是否具有权限，无权限则不允许操作
		boolean hasAuth = auth (request);
		if (! hasAuth)
		{
			resp.setResultCode (Constants.AUTH_FAIL_CODE);
			resp.setResultMsg (Constants.AUTH_FAIL_MSG);
			LOGGER.debug ("[AppResourceImpl].[getAppById]---->ERROR![Auth is Fail!]");
			return resp;
		}

		if (null == req)
		{
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug ("[AppResourceImpl].[getAppById]---->ERROR![GetAppByIdReq is null]");
			return resp;
		}

		if (StringUtils.isEmpty (req.getAppId ()))
		{
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug ("[AppResourceImpl].[getAppById]---->ERROR![appId is null]");
			return resp;
		}

		AppInfo appInfo = null;
		try
		{
			appInfo = appInfoMapper.queryByAppId (req.getAppId ());
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug (String.format ("[AppResourceImpl].[getAppById]---->ERROR!%s", e.getMessage ()));
			return resp;
		}

		if (null != appInfo)
		{
			AppVO appVO = transferToAppVO (appInfo);
			resp.setAppVO (appVO);
		}

		LOGGER.debug (String.format ("[AppResourceImpl].[getAppById]---->SUCCESS![GetAppByIdResp]:%s", resp));
		return resp;
	}

	private AppVO transferToAppVO (AppInfo appInfo)
	{
		AppVO appVO = new AppVO ();

		appVO.setAppId (appInfo.getAppId ());
		appVO.setAppName (appInfo.getAppName ());
		appVO.setAppInfo (appInfo.getAppInfo ());
		appVO.setRemark (appInfo.getRemark ());
		appVO.setCreateTime (DateUtils.formatTime (appInfo.getCreateTime (), DateUtils.STANDARD_19));
		return appVO;
	}
}