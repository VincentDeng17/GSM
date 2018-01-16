/**
 * 文 件 名:  AppConfigResourceImpl
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  zhouhaofeng
 * 修改时间:  2017/11/6
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.provider.impl.appconfig;

import com.quanteng.gsmp.commom.core.annotation.Resource;
import com.quanteng.gsmp.commom.core.constants.Constants;
import com.quanteng.gsmp.commom.core.utils.DateManager;
import com.quanteng.gsmp.provider.impl.BasicMethod;
import com.quanteng.gsmp.reporsity.entity.AppConfig;
import com.quanteng.gsmp.reporsity.mapper.AppConfigMapper;
import com.quanteng.gsmp.resource.appconfig.AppConfigResource;
import com.quanteng.gsmp.resource.appconfig.domain.AppConfigVO;
import com.quanteng.gsmp.resource.appconfig.request.*;
import com.quanteng.gsmp.resource.appconfig.response.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 应用配置接口实现
 *
 * @author zhouhaofeng
 * @version 2017/11/6
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Resource
public class AppConfigResourceImpl extends BasicMethod implements AppConfigResource
{
	private static final Logger logger = LoggerFactory.getLogger (AppConfigResourceImpl.class);

	@Autowired
	private AppConfigMapper appConfigMapper;

	@Override
	public AddAppConfigResp addAppConfig (HttpServletRequest request, AddAppConfigReq addAppConfigReq)
	{
		AddAppConfigResp resp = new AddAppConfigResp ();
		if (! auth (request))
		{
			resp.setResultCode (Constants.AUTH_FAIL_CODE);
			resp.setResultMsg (Constants.AUTH_FAIL_MSG);
			logger.debug ("[AppConfigResourceImpl].[addAppConfig]---->ERROR![Auth is Fail!]");
			return resp;
		}

		if (addAppConfigReq == null)
		{
			resp.setResultCode (Constants.PARAMETER_MISS_CODE);
			resp.setResultMsg (Constants.PARAMETER_MISS_MSG);
			logger.debug ("[AppConfigResourceImpl].[addAppConfig]---->ERROR![addAppConfigReq is missing!]");
			return resp;
		}

		//参数校验
		if (StringUtils.isEmpty (addAppConfigReq.getCountryId ()))
		{
			resp.setResultCode (Constants.PARAMETER_MISS_CODE);
			resp.setResultMsg (Constants.PARAMETER_MISS_MSG);
			logger.debug ("[AppConfigResourceImpl].[addAppConfig]---->ERROR![countryId is missing!]");
			return resp;
		}
		if (StringUtils.isEmpty (addAppConfigReq.getServiceId ()))
		{
			resp.setResultCode (Constants.PARAMETER_MISS_CODE);
			resp.setResultMsg (Constants.PARAMETER_MISS_MSG);
			logger.debug ("[AppConfigResourceImpl].[addAppConfig]---->ERROR![serviceId is missing!]");
			return resp;
		}
		if (StringUtils.isEmpty (addAppConfigReq.getAppId ()))
		{
			resp.setResultCode (Constants.PARAMETER_MISS_CODE);
			resp.setResultMsg (Constants.PARAMETER_MISS_MSG);
			logger.debug ("[AppConfigResourceImpl].[addAppConfig]---->ERROR![appId is missing!]");
			return resp;
		}
		if (StringUtils.isEmpty (addAppConfigReq.getOriginalUrl ()))
		{
			resp.setResultCode (Constants.PARAMETER_MISS_CODE);
			resp.setResultMsg (Constants.PARAMETER_MISS_MSG);
			logger.debug ("[AppConfigResourceImpl].[addAppConfig]---->ERROR![originalUrl is missing!]");
			return resp;
		}
		logger.debug (String.format ("[AppConfigResourceImpl].[addAppConfig]---->AddAppConfigReq:%s", addAppConfigReq));
		AppConfig appConfig = new AppConfig ();
		appConfig.setConfigId (UUID.randomUUID ().toString ());
		appConfig.setServiceId (addAppConfigReq.getServiceId ());
		appConfig.setCountryId (addAppConfigReq.getCountryId ());
		appConfig.setAppId (addAppConfigReq.getAppId ());
		appConfig.setAppName (addAppConfigReq.getAppName ());
		appConfig.setOriginalUrl (addAppConfigReq.getOriginalUrl ());

		try
		{
			appConfigMapper.add (appConfig);
		}
		catch (Exception e)
		{
			logger.error ("[AppConfigResourceImpl].[addAppConfig]---->Exception!{}", e.toString ());
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			return resp;
		}
		return resp;
	}

	@Override
	public DelAppConfigResp delAppConfig (HttpServletRequest request, DelAppConfigReq delAppConfigReq)
	{
		DelAppConfigResp resp = new DelAppConfigResp ();
		if (! auth (request))
		{
			resp.setResultCode (Constants.AUTH_FAIL_CODE);
			resp.setResultMsg (Constants.AUTH_FAIL_MSG);
			logger.debug ("[AppConfigResourceImpl].[delAppConfig]---->ERROR![Auth is Fail!]");
			return resp;
		}

		if (delAppConfigReq == null)
		{
			resp.setResultCode (Constants.PARAMETER_MISS_CODE);
			resp.setResultMsg (Constants.PARAMETER_MISS_MSG);
			logger.debug ("[AppConfigResourceImpl].[delAppConfig]---->ERROR![delAppConfigReq is missing!]");
			return resp;
		}

		if (null == delAppConfigReq.getConfigId () || "".equals (delAppConfigReq.getConfigId ()))
		{
			resp.setResultCode (Constants.PARAMETER_MISS_CODE);
			resp.setResultMsg (Constants.PARAMETER_MISS_MSG);
			logger.debug ("[AppConfigResourceImpl].[delAppConfig]---->ERROR![configId is missing!]");
			return resp;
		}
		logger.debug (String.format ("[AppConfigResourceImpl].[delAppConfig]---->configId:%s",
				delAppConfigReq.getConfigId ()));
		try
		{
			appConfigMapper.delete (delAppConfigReq.getConfigId ());
		}
		catch (Exception e)
		{
			logger.error ("[AppConfigResourceImpl].[delAppConfig]---->Exception!{}", e.toString ());
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			return resp;
		}
		return resp;
	}

	@Override
	public ModifyAppConfigResp modifyAppConfig (HttpServletRequest request, ModifyAppConfigReq modifyAppConfigReq)
	{
		ModifyAppConfigResp resp = new ModifyAppConfigResp ();

		if (! auth (request))
		{
			resp.setResultCode (Constants.AUTH_FAIL_CODE);
			resp.setResultMsg (Constants.AUTH_FAIL_MSG);
			logger.debug ("[AppConfigResourceImpl].[modifyAppConfig]---->ERROR![Auth is Fail!]");
			return resp;
		}

		if (modifyAppConfigReq == null)
		{
			resp.setResultCode (Constants.PARAMETER_MISS_CODE);
			resp.setResultMsg (Constants.PARAMETER_MISS_MSG);
			logger.debug ("[AppConfigResourceImpl].[modifyAppConfig]---->ERROR![modifyAppConfigReq is missing!]");
			return resp;
		}

		//参数校验
		if (StringUtils.isEmpty (modifyAppConfigReq.getConfigId ()))
		{
			resp.setResultCode (Constants.PARAMETER_MISS_CODE);
			resp.setResultMsg (Constants.PARAMETER_MISS_MSG);
			logger.debug ("[AppConfigResourceImpl].[modifyAppConfig]---->ERROR![configId is missing!]");
			return resp;
		}
		if (StringUtils.isEmpty (modifyAppConfigReq.getCountryId ()))
		{
			resp.setResultCode (Constants.PARAMETER_MISS_CODE);
			resp.setResultMsg (Constants.PARAMETER_MISS_MSG);
			logger.debug ("[AppConfigResourceImpl].[modifyAppConfig]---->ERROR![countryId is missing!]");
			return resp;
		}
		if (StringUtils.isEmpty (modifyAppConfigReq.getServiceId ()))
		{
			resp.setResultCode (Constants.PARAMETER_MISS_CODE);
			resp.setResultMsg (Constants.PARAMETER_MISS_MSG);
			logger.debug ("[AppConfigResourceImpl].[modifyAppConfig]---->ERROR![serviceId is missing!]");
			return resp;
		}
		if (StringUtils.isEmpty (modifyAppConfigReq.getAppId ()))
		{
			resp.setResultCode (Constants.PARAMETER_MISS_CODE);
			resp.setResultMsg (Constants.PARAMETER_MISS_MSG);
			logger.debug ("[AppConfigResourceImpl].[modifyAppConfig]---->ERROR![appId is missing!]");
			return resp;
		}
		if (StringUtils.isEmpty (modifyAppConfigReq.getOriginalUrl ()))
		{
			resp.setResultCode (Constants.PARAMETER_MISS_CODE);
			resp.setResultMsg (Constants.PARAMETER_MISS_MSG);
			logger.debug ("[AppConfigResourceImpl].[modifyAppConfig]---->ERROR![originalUrl is missing!]");
			return resp;
		}
		logger.debug (String.format ("[AppConfigResourceImpl].[modifyAppConfig]---->ModifyAppConfigReq:%s",
				modifyAppConfigReq));
		AppConfig appConfig = new AppConfig ();
		appConfig.setConfigId (modifyAppConfigReq.getConfigId ());
		appConfig.setServiceId (modifyAppConfigReq.getServiceId ());
		appConfig.setCountryId (modifyAppConfigReq.getCountryId ());
		appConfig.setAppId (modifyAppConfigReq.getAppId ());
		appConfig.setAppName (modifyAppConfigReq.getAppName ());
		appConfig.setOriginalUrl (modifyAppConfigReq.getOriginalUrl ());

		try
		{
			appConfigMapper.modify (appConfig);
		}
		catch (Exception e)
		{
			logger.error ("[AppConfigResourceImpl].[modifyAppConfig]---->Exception!{}", e.toString ());
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			return resp;
		}
		return resp;
	}

	@Override
	public BatchDelAppConfigResp batchDelAppConfig (HttpServletRequest request,
			BatchDelAppConfigReq batchDelAppConfigReq)
	{
		BatchDelAppConfigResp resp = new BatchDelAppConfigResp ();

		if (! auth (request))
		{
			resp.setResultCode (Constants.AUTH_FAIL_CODE);
			resp.setResultMsg (Constants.AUTH_FAIL_MSG);
			logger.debug ("[AppConfigResourceImpl].[batchDelAppConfig]---->ERROR![Auth is Fail!]");
			return resp;
		}

		if (batchDelAppConfigReq == null)
		{
			resp.setResultCode (Constants.PARAMETER_MISS_CODE);
			resp.setResultMsg (Constants.PARAMETER_MISS_MSG);
			logger.debug ("[AppConfigResourceImpl].[batchDelAppConfig]---->ERROR![batchDelAppConfigReq is missing!]");
			return resp;
		}

		if (batchDelAppConfigReq.getConfigIdList () == null || batchDelAppConfigReq.getConfigIdList ().size () == 0)
		{
			resp.setResultCode (Constants.PARAMETER_MISS_CODE);
			resp.setResultMsg (Constants.PARAMETER_MISS_MSG);
			logger.debug ("[AppConfigResourceImpl].[batchDelAppConfig]---->ERROR![configIdList is missing!]");
			return resp;
		}

		logger.debug (String.format ("[AppConfigResourceImpl].[batchDelAppConfig]---->BatchDelAppConfigReq:%s",
				batchDelAppConfigReq));

		//批量删除操作
		try
		{
			appConfigMapper.batchDelete (batchDelAppConfigReq.getConfigIdList ());
		}
		catch (Exception e)
		{
			logger.error ("[AppConfigResourceImpl].[batchDelAppConfig]---->Exception!{}", e.toString ());
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			return resp;
		}
		resp.setConfigIdList (batchDelAppConfigReq.getConfigIdList ());
		return resp;
	}

	@Override
	public QryAppConfigResp qryAppConfig (HttpServletRequest request, QryAppConfigReq qryAppConfigReq)
	{
		QryAppConfigResp resp = new QryAppConfigResp ();

		if (! auth (request))
		{
			resp.setResultCode (Constants.AUTH_FAIL_CODE);
			resp.setResultMsg (Constants.AUTH_FAIL_MSG);
			logger.debug ("[AppConfigResourceImpl].[qryAppConfig]---->ERROR![Auth is Fail!]");
			return resp;
		}

		if (qryAppConfigReq == null)
		{
			resp.setResultCode (Constants.PARAMETER_MISS_CODE);
			resp.setResultMsg (Constants.PARAMETER_MISS_MSG);
			logger.debug ("[AppConfigResourceImpl].[qryAppConfig]---->ERROR![qryAppConfigReq is missing!]");
			return resp;
		}
		logger.debug (String.format ("[AppConfigResourceImpl].[qryAppConfig]---->QryAppConfigReq:%s", qryAppConfigReq));

		Map<String, Object> params = new HashMap<> (10);
		if (! StringUtils.isEmpty (qryAppConfigReq.getConfigId ()))
		{
			params.put ("configId", qryAppConfigReq.getConfigId ());
		}
		if (! StringUtils.isEmpty (qryAppConfigReq.getCountryId ()))
		{
			params.put ("countryId", qryAppConfigReq.getCountryId ());
		}
		if (! StringUtils.isEmpty (qryAppConfigReq.getServiceId ()))
		{
			params.put ("serviceId", qryAppConfigReq.getServiceId ());
		}
		if (! StringUtils.isEmpty (qryAppConfigReq.getAppId ()))
		{
			params.put ("appId", qryAppConfigReq.getAppId ());
		}
		if (! StringUtils.isEmpty (qryAppConfigReq.getAppName ()))
		{
			params.put ("appName", qryAppConfigReq.getAppName ());
		}
		if (! StringUtils.isEmpty (qryAppConfigReq.getOriginalUrl ()))
		{
			params.put ("originalUrl", qryAppConfigReq.getOriginalUrl ());
		}
		if (! StringUtils.isEmpty (qryAppConfigReq.getStartTime ()))
		{
			params.put ("startTime", qryAppConfigReq.getStartTime ());
		}
		if (! StringUtils.isEmpty (qryAppConfigReq.getEndTime ()))
		{
			params.put ("endTime", qryAppConfigReq.getEndTime ());
		}
		//判断是否为查询所有
		if (Constants.QUERY_ALL_FLAG.equals (qryAppConfigReq.getQueryType ()))
		{
			params.put ("start", null);
			params.put ("psize", null);
		}
		else
		{
			params.put ("start", (qryAppConfigReq.getPageIndex () - 1) * qryAppConfigReq.getPageSize ());
			params.put ("psize", qryAppConfigReq.getPageSize ());
		}
		try
		{
			List<AppConfig> appConfigList = appConfigMapper.query (params);
			List<AppConfigVO> appConfigVOList = new ArrayList<> (appConfigList.size ());
			appConfigList.parallelStream ()
					.forEachOrdered (appConfig -> appConfigVOList.add (transferEntity (appConfig)));
			resp.setAppConfigs (appConfigVOList);

			//查询总数
			int count = appConfigMapper.count (params);
			resp.setTotal (count);

		}
		catch (Exception e)
		{
			logger.error ("[AppConfigResourceImpl].[qryAppConfig]---->Exception!{}", e.toString ());
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			return resp;
		}
		return resp;
	}

	/**
	 * 应用配置 entity转vo
	 *
	 * @param appConfig 应用配置entity
	 * @return appConfigVO 应用配置vo
	 */
	private AppConfigVO transferEntity (AppConfig appConfig)
	{
		AppConfigVO appConfigVO = new AppConfigVO ();
		appConfigVO.setConfigId (appConfig.getConfigId ());
		appConfigVO.setAppId (appConfig.getAppId ());
		appConfigVO.setAppName (appConfig.getAppName ());
		appConfigVO.setCountryId (appConfig.getCountryId ());
		appConfigVO.setCountryAbbreviation (appConfig.getCountryAbbreviation ());
		appConfigVO.setServiceId (appConfig.getServiceId ());
		appConfigVO.setServiceName (appConfig.getServiceName ());
		appConfigVO.setOriginalUrl (appConfig.getOriginalUrl ());
		appConfigVO.setCreateTime (DateManager.dateToString (appConfig.getCreateTime (), "yyyy-MM-dd HH:mm:ss"));
		appConfigVO.setOriginalAppName (appConfig.getOriginalAppName ());
		return appConfigVO;
	}
}
