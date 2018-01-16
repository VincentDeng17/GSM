/**
 * 文 件 名:  UrlManageResourceImpl
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/9/11 0011
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.provider.impl.url;

import com.quanteng.gsmp.commom.core.annotation.Resource;
import com.quanteng.gsmp.commom.core.constants.Constants;
import com.quanteng.gsmp.commom.core.export.ExcelUtil;
import com.quanteng.gsmp.commom.core.utils.DateManager;
import com.quanteng.gsmp.provider.impl.BasicMethod;
import com.quanteng.gsmp.reporsity.entity.AppUrlMapping;
import com.quanteng.gsmp.reporsity.entity.Config;
import com.quanteng.gsmp.reporsity.mapper.AppUrlMappingMapper;
import com.quanteng.gsmp.reporsity.mapper.ConfigMapper;
import com.quanteng.gsmp.resource.url.UrlManageResource;
import com.quanteng.gsmp.resource.url.domain.UrlManageInfo;
import com.quanteng.gsmp.resource.url.domain.UrlManageInfoExp;
import com.quanteng.gsmp.resource.url.request.DelUrlManageInfoReq;
import com.quanteng.gsmp.resource.url.request.GetUrlManageInfoListReq;
import com.quanteng.gsmp.resource.url.response.GetUrlManageInfoListResp;
import com.quanteng.gsmp.resource.url.response.UrlManageResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author dyc
 * @version 2017/9/11 0011
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Resource
public class UrlManageResourceImpl extends BasicMethod implements UrlManageResource
{

	private static final Logger LOGGER = LoggerFactory.getLogger (UrlManageResourceImpl.class);

	@Autowired
	AppUrlMappingMapper appUrlMappingMapper;

	@Autowired
	ConfigMapper configMapper;

	@Override
	public GetUrlManageInfoListResp queryUrlInfo (HttpServletRequest request, GetUrlManageInfoListReq req)
	{
		LOGGER.debug (String.format ("[UrlManageResourceImpl].[queryUrlInfo]---->[GetUrlManageInfoListReq]:%s", req));
		GetUrlManageInfoListResp resp = new GetUrlManageInfoListResp ();
		List<UrlManageInfo> urlManageInfos = new ArrayList<> ();
		//判断是否具有权限，无权限则不允许操作
		boolean hasAuth = auth (request);
		if (! hasAuth)
		{
			resp.setResultCode (Constants.AUTH_FAIL_CODE);
			resp.setResultMsg (Constants.AUTH_FAIL_MSG);
			LOGGER.debug ("[UrlManageResourceImpl].[queryUrlInfo]---->ERROR![Auth is Fail!]");
			return resp;
		}

		if (null == req)
		{
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug ("[UrlManageResourceImpl].[queryUrlInfo]---->ERROR![GetUrlManageInfoListReq is null]");
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
			if (null != req.getAppName () && ! "".equals (req.getAppName ()))
			{
				params.put ("appName", req.getAppName ());
			}
			if (null != req.getClickId () && ! "".equals (req.getClickId ()))
			{
				params.put ("clickId", req.getClickId ());
			}
			if (null != req.getSubChannel () && ! "".equals (req.getSubChannel ()))
			{
				params.put ("subChannel", req.getSubChannel ());
			}
			if (null != req.getThirdSubChannel () && ! "".equals (req.getThirdSubChannel ()))
			{
				params.put ("thirdSubChannel", req.getThirdSubChannel ());
			}
			params.put ("start", (req.getPageIndex () - 1) * req.getPageSize ());
			params.put ("psize", req.getPageSize ());

			List<AppUrlMapping> appUrlMappings = appUrlMappingMapper.query (params);
			urlManageInfos = getUrlManageInfos (appUrlMappings);

			int total = appUrlMappingMapper.getTotalByCondition (params);

			resp.setResultCode (Constants.SUCCESS_CODE);
			resp.setResultMsg (Constants.SUCCESS_MSG);
			resp.setUrlManageInfos (urlManageInfos);
			resp.setTotal (total);
			LOGGER.debug (
					String.format ("[UrlManageResourceImpl].[queryUrlInfo]---->SUCCESS![GetUrlManageInfoListResp]:%s",
							resp));
			return resp;
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug (String.format ("[UrlManageResourceImpl].[queryUrlInfo]---->ERROR!%s", e.getMessage ()));
			return resp;
		}
	}

	@Override
	public UrlManageResp addUrlInfo (HttpServletRequest request, UrlManageInfo urlManageInfo)
	{
		LOGGER.debug (String.format ("[UrlManageResourceImpl].[addUrlInfo]---->[UrlManageInfo]:%s", urlManageInfo));
		UrlManageResp resp = new UrlManageResp ();
		//判断是否具有权限，无权限则不允许操作
		boolean hasAuth = auth (request);
		if (! hasAuth)
		{
			resp.setResultCode (Constants.AUTH_FAIL_CODE);
			resp.setResultMsg (Constants.AUTH_FAIL_MSG);
			LOGGER.debug ("[UrlManageResourceImpl].[addUrlInfo]---->ERROR![Auth is Fail!]");
			return resp;
		}

		if (null == urlManageInfo)
		{
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug ("[UrlManageResourceImpl].[addUrlInfo]---->ERROR![UrlManageInfo is null]");
			return resp;
		}
		if (null == urlManageInfo.getAppName () || "".equals (urlManageInfo.getAppName ()))
		{
			resp.setResultCode (Constants.PARAMETER_MISS_CODE);
			resp.setResultMsg (Constants.PARAMETER_MISS_MSG + "appName !");
			LOGGER.debug ("[UrlManageResourceImpl].[addUrlInfo]---->ERROR![Missing parameter : appName]");
			return resp;
		}
		if (null == urlManageInfo.getHwUrl () || "".equals (urlManageInfo.getHwUrl ()))
		{
			resp.setResultCode (Constants.PARAMETER_MISS_CODE);
			resp.setResultMsg (Constants.PARAMETER_MISS_MSG + "hwUrl !");
			LOGGER.debug ("[UrlManageResourceImpl].[addUrlInfo]---->ERROR![Missing parameter : hwUrl]");
			return resp;
		}

		try
		{
			AppUrlMapping appUrlMapping = transferToEntity (urlManageInfo, 1);
			//要先判断是否已存在该配置：应用名和hwUrl完全一致视为相同
			boolean exist = isExist (appUrlMapping);
			if (exist)
			{
				resp.setResultCode (Constants.DATA_EXSIST_CODE);
				resp.setResultMsg (Constants.DATA_EXSIST_MSG);
				LOGGER.debug ("[UrlManageResourceImpl].[addUrlInfo]---->ERROR! appUrlMapping is exist!");
			}
			else
			{
				appUrlMappingMapper.add (appUrlMapping);
				resp.setResultCode (Constants.SUCCESS_CODE);
				resp.setResultMsg (Constants.SUCCESS_MSG);
				LOGGER.debug ("[UrlManageResourceImpl].[addUrlInfo]---->SUCCESS!");
			}
			return resp;
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug (String.format ("[UrlManageResourceImpl].[addUrlInfo]---->ERROR!%s", e.getMessage ()));
			return resp;
		}
	}

	@Override
	public UrlManageResp modUrlInfo (HttpServletRequest request, UrlManageInfo urlManageInfo)
	{
		LOGGER.debug (String.format ("[UrlManageResourceImpl].[modUrlInfo]---->[UrlManageInfo]:%s", urlManageInfo));
		UrlManageResp resp = new UrlManageResp ();
		//判断是否具有权限，无权限则不允许操作
		boolean hasAuth = auth (request);
		if (! hasAuth)
		{
			resp.setResultCode (Constants.AUTH_FAIL_CODE);
			resp.setResultMsg (Constants.AUTH_FAIL_MSG);
			LOGGER.debug ("[UrlManageResourceImpl].[modUrlInfo]---->ERROR![Auth is Fail!]");
			return resp;
		}

		if (null == urlManageInfo)
		{
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug ("[UrlManageResourceImpl].[modUrlInfo]---->ERROR![UrlManageInfo is null]");
			return resp;
		}
		if (null == urlManageInfo.getmId () || "".equals (urlManageInfo.getmId ()))
		{
			resp.setResultCode (Constants.PARAMETER_MISS_CODE);
			resp.setResultMsg (Constants.PARAMETER_MISS_MSG + "mId !");
			LOGGER.debug ("[UrlManageResourceImpl].[modUrlInfo]---->ERROR![Missing parameter : mId]");
			return resp;
		}
		if (null == urlManageInfo.getAppName () || "".equals (urlManageInfo.getAppName ()))
		{
			resp.setResultCode (Constants.PARAMETER_MISS_CODE);
			resp.setResultMsg (Constants.PARAMETER_MISS_MSG + "appName !");
			LOGGER.debug ("[UrlManageResourceImpl].[modUrlInfo]---->ERROR![Missing parameter : appName]");
			return resp;
		}
		if (null == urlManageInfo.getHwUrl () || "".equals (urlManageInfo.getHwUrl ()))
		{
			resp.setResultCode (Constants.PARAMETER_MISS_CODE);
			resp.setResultMsg (Constants.PARAMETER_MISS_MSG + "hwUrl !");
			LOGGER.debug ("[UrlManageResourceImpl].[modUrlInfo]---->ERROR![Missing parameter : hwUrl]");
			return resp;
		}

		try
		{
			AppUrlMapping appUrlMapping = transferToEntity (urlManageInfo, 2);
			appUrlMappingMapper.modify (appUrlMapping);
			resp.setResultCode (Constants.SUCCESS_CODE);
			resp.setResultMsg (Constants.SUCCESS_MSG);
			LOGGER.debug ("[UrlManageResourceImpl].[modUrlInfo]---->SUCCESS!");
			return resp;
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug (String.format ("[UrlManageResourceImpl].[modUrlInfo]---->ERROR!%s", e.getMessage ()));
			return resp;
		}
	}

	@Override
	public UrlManageResp delUrlInfo (HttpServletRequest request, DelUrlManageInfoReq req)
	{
		LOGGER.debug (String.format ("[UrlManageResourceImpl].[delUrlInfo]---->[mId]:%s", req));
		UrlManageResp resp = new UrlManageResp ();
		//判断是否具有权限，无权限则不允许操作
		boolean hasAuth = auth (request);
		if (! hasAuth)
		{
			resp.setResultCode (Constants.AUTH_FAIL_CODE);
			resp.setResultMsg (Constants.AUTH_FAIL_MSG);
			LOGGER.debug ("[UrlManageResourceImpl].[delUrlInfo]---->ERROR![Auth is Fail!]");
			return resp;
		}

		if (null == req || CollectionUtils.isEmpty (req.getMidList ()))
		{
			resp.setResultCode (Constants.PARAMETER_MISS_CODE);
			resp.setResultMsg (Constants.PARAMETER_MISS_MSG + "mId !");
			LOGGER.debug ("[UrlManageResourceImpl].[delUrlInfo]---->ERROR![Missing parameter : mId]");
			return resp;
		}

		try
		{
			appUrlMappingMapper.delete (req.getMidList ());
			resp.setResultCode (Constants.SUCCESS_CODE);
			resp.setResultMsg (Constants.SUCCESS_MSG);
			LOGGER.debug ("[UrlManageResourceImpl].[delUrlInfo]---->SUCCESS!");
			return resp;
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug (String.format ("[UrlManageResourceImpl].[delUrlInfo]---->ERROR!%s", e.getMessage ()));
			return resp;
		}
	}

	@Override
	public UrlManageResp exportExcl (HttpServletRequest request, GetUrlManageInfoListReq req,
			HttpServletResponse response)
	{
		LOGGER.debug (String.format ("[UrlManageResourceImpl].[exportExcl]---->[GetUrlManageInfoListReq]:%s", req));
		UrlManageResp resp = new UrlManageResp ();
		List<UrlManageInfoExp> urlManageInfoExps = new ArrayList<> ();
		//判断是否具有权限，无权限则不允许操作
		boolean hasAuth = auth (request);
		if (! hasAuth)
		{
			resp.setResultCode (Constants.AUTH_FAIL_CODE);
			resp.setResultMsg (Constants.AUTH_FAIL_MSG);
			LOGGER.debug ("[UrlManageResourceImpl].[exportExcl]---->ERROR![Auth is Fail!]");
			return resp;
		}
		if (null == req)
		{
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug ("[UrlManageResourceImpl].[exportExcl]---->ERROR![GetUrlManageInfoListReq is null]");
			return resp;
		}

		try
		{
			// 告诉浏览器用什么软件可以打开此文件
			response.setHeader ("content-Type", "application/vnd.ms-excel");
			// 下载文件的默认名称
			response.setHeader ("Content-Disposition", "attachment;filename=URL配置.xls");

			//查询数据
			Map<String, Object> params = new HashMap ();
			if (null != req.getAppName () && ! "".equals (req.getAppName ()))
			{
				params.put ("appName", req.getAppName ());
			}
			if (null != req.getSubChannel () && ! "".equals (req.getSubChannel ()))
			{
				params.put ("subChannel", req.getSubChannel ());
			}
			if (null != req.getThirdSubChannel () && ! "".equals (req.getThirdSubChannel ()))
			{
				params.put ("thirdSubChannel", req.getThirdSubChannel ());
			}

			List<AppUrlMapping> appUrlMappings = appUrlMappingMapper.query (params);
			urlManageInfoExps = getUrlManageInfoExps (appUrlMappings);

			String template = "url-mapping-template.xls";
			String newFilepath = "D:/配置链接列表.xls";

			Map<String, String> map = new HashMap<String, String> ();
			map.put ("title", "配置链接表");
			map.put ("total", urlManageInfoExps.size () + " 条");
			map.put ("date", DateManager.getSystemTime ("yyyy年MM月dd日"));

			ExcelUtil.getInstance ()
					.exportObj2ExcelByTemplate (map, template, new FileOutputStream (newFilepath), urlManageInfoExps,
							UrlManageInfoExp.class, true);

			resp.setResultCode (Constants.SUCCESS_CODE);
			resp.setResultMsg (Constants.SUCCESS_MSG);
			LOGGER.debug ("[UrlManageResourceImpl].[exportExcl]---->SUCCESS!");
			return resp;
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug (String.format ("[UrlManageResourceImpl].[exportExcl]---->ERROR!%s", e.getMessage ()));
			return resp;
		}
	}

	/**
	 * 将请求对象转换构造成entity实体类
	 * operateType: 1-新增，2-修改
	 *
	 * @param urlManageInfo
	 * @param operateType
	 * @return
	 */

	private AppUrlMapping transferToEntity (UrlManageInfo urlManageInfo, int operateType)
	{
		AppUrlMapping appUrlMapping = new AppUrlMapping ();
		try
		{
			String mId = "";
			if (operateType == 1)
			{
				mId = String.valueOf (System.currentTimeMillis ());
			}
			else if (operateType == 2)
			{
				mId = urlManageInfo.getmId ();
			}

			String hwUrl = urlManageInfo.getHwUrl ();
			String clickId = Constants.DEFAULT_CHANNEL_VALUE;
			if (null != urlManageInfo.getClickId () || ! "".equals (urlManageInfo.getClickId ()))
			{
				clickId = urlManageInfo.getClickId ();
			}
			else
			{
				clickId = Constants.DEFAULT_CHANNEL_VALUE;
			}
			String subChannel = urlManageInfo.getSubChannel ();
			String thirdSubChannel = urlManageInfo.getThirdSubChannel ();

			//新增推广URL--promotionalUrl
			//域名通过配置表读取
			Config config = configMapper.queryByName (Constants.GSMS_DOMAIN_KEY);
			String promotionalUrl = config.getParamValue () + "?uid=" + mId;

			appUrlMapping.setMId (mId);
			appUrlMapping.setAppName (urlManageInfo.getAppName ());
			appUrlMapping.setHwUrl (hwUrl);
			appUrlMapping.setClickId (clickId);
			appUrlMapping.setSubChannel (subChannel);
			appUrlMapping.setThirdSubChannel (thirdSubChannel);
			//价格
			BigDecimal price = new BigDecimal (urlManageInfo.getPrice ());
			appUrlMapping.setPrice (price);
			//            appUrlMapping.setChannelUrl(channelUrl);
			appUrlMapping.setPromotionalUrl (promotionalUrl);
			//            appUrlMapping.setLinkId(linkId);

		}
		catch (Exception e)
		{
			e.printStackTrace ();
		}
		return appUrlMapping;
	}

	/**
	 * 将entity转换成响应的实体类
	 *
	 * @param appUrlMappinga
	 * @return
	 */
	private List<UrlManageInfo> getUrlManageInfos (List<AppUrlMapping> appUrlMappinga)
	{
		List<UrlManageInfo> urlManageInfoList = new ArrayList<> ();
		if (appUrlMappinga.size () > 0)
		{
			for (int i = 0; i < appUrlMappinga.size (); i++)
			{
				AppUrlMapping appUrlMapping = appUrlMappinga.get (i);
				UrlManageInfo urlManageInfo = new UrlManageInfo ();
				urlManageInfo.setmId (appUrlMapping.getMId ());
				urlManageInfo.setAppName (appUrlMapping.getAppName ());
				urlManageInfo.setHwUrl (appUrlMapping.getHwUrl ());
				urlManageInfo.setClickId (appUrlMapping.getClickId ());
				urlManageInfo.setSubChannel (appUrlMapping.getSubChannel ());
				urlManageInfo.setThirdSubChannel (appUrlMapping.getThirdSubChannel ());
				urlManageInfo.setPrice (appUrlMapping.getPrice ().toString ());
				//                urlManageInfo.setChannelUrl(appUrlMapping.getChannelUrl());
				urlManageInfo.setPromotionalUrl (appUrlMapping.getPromotionalUrl ());
				//                urlManageInfo.setLinkId(appUrlMapping.getLinkId());
				urlManageInfoList.add (urlManageInfo);
			}
		}
		return urlManageInfoList;
	}

	/**
	 * 转换成导出的实体类
	 *
	 * @param appUrlMappinga
	 * @return
	 */
	private List<UrlManageInfoExp> getUrlManageInfoExps (List<AppUrlMapping> appUrlMappinga)
	{
		List<UrlManageInfoExp> urlManageInfoExpList = new ArrayList<> ();
		if (appUrlMappinga.size () > 0)
		{
			for (int i = 0; i < appUrlMappinga.size (); i++)
			{
				AppUrlMapping appUrlMapping = appUrlMappinga.get (i);
				UrlManageInfoExp urlManageInfoExp = new UrlManageInfoExp ();
				urlManageInfoExp.setmId (appUrlMapping.getMId ());
				urlManageInfoExp.setAppName (appUrlMapping.getAppName ());
				urlManageInfoExp.setHwUrl (appUrlMapping.getHwUrl ());
				urlManageInfoExp.setClickId (appUrlMapping.getClickId ());
				urlManageInfoExp.setSubChannel (appUrlMapping.getSubChannel ());
				urlManageInfoExp.setThirdSubChannel (appUrlMapping.getThirdSubChannel ());
				//                urlManageInfoExp.setChannelUrl(appUrlMapping.getChannelUrl());
				urlManageInfoExp.setPromotionalUrl (appUrlMapping.getPromotionalUrl ());
				//                urlManageInfoExp.setLinkId(appUrlMapping.getLinkId());
				urlManageInfoExpList.add (urlManageInfoExp);
			}
		}
		return urlManageInfoExpList;
	}

	/**
	 * 判断是否已存在相同的应用URL映射关系
	 * 判断标准：应用名称和hwUrl必须全相同
	 *
	 * @param appUrlMapping
	 * @return
	 */
	private boolean isExist (AppUrlMapping appUrlMapping)
	{
		boolean exist = false;
		Map<String, Object> params = new HashMap ();
		params.put ("appName", appUrlMapping.getAppName ());
		params.put ("hwUrl", appUrlMapping.getHwUrl ());
		List<AppUrlMapping> appUrlMappings = appUrlMappingMapper.query (params);
		if (appUrlMappings.size () > 0)
		{
			for (int i = 0; i < appUrlMappings.size (); i++)
			{
				AppUrlMapping appUrlMappingTemp = appUrlMappings.get (0);
				if (appUrlMappingTemp.getAppName ().equals (appUrlMapping.getAppName ()) && appUrlMappingTemp
						.getHwUrl ().equals (appUrlMapping.getHwUrl ()) && appUrlMappingTemp.getClickId ()
						.equals (appUrlMapping.getClickId ()) && appUrlMappingTemp.getSubChannel ()
						.equals (appUrlMapping.getSubChannel ()) && appUrlMappingTemp.getThirdSubChannel ()
						.equals (appUrlMapping.getThirdSubChannel ()))
				{
					exist = true;
					break;
				}
			}
		}
		return exist;
	}
}
