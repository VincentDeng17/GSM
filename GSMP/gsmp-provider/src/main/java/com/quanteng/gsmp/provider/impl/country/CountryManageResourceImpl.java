/**
 * 文 件 名:  CountryManageResourceImpl
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/10/23 0023
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.provider.impl.country;

import com.quanteng.gsmp.commom.core.annotation.Resource;
import com.quanteng.gsmp.commom.core.constants.Constants;
import com.quanteng.gsmp.commom.core.utils.DateManager;
import com.quanteng.gsmp.provider.impl.BasicMethod;
import com.quanteng.gsmp.reporsity.entity.Country;
import com.quanteng.gsmp.reporsity.mapper.CountryMapper;
import com.quanteng.gsmp.resource.country.CountryManageResource;
import com.quanteng.gsmp.resource.country.domain.CountryInfo;
import com.quanteng.gsmp.resource.country.request.DelCountryReq;
import com.quanteng.gsmp.resource.country.request.GetCountryReq;
import com.quanteng.gsmp.resource.country.response.CountryResp;
import com.quanteng.gsmp.resource.country.response.GetCountryResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
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
public class CountryManageResourceImpl extends BasicMethod implements CountryManageResource
{

	private static final Logger LOGGER = LoggerFactory.getLogger (CountryManageResourceImpl.class);

	@Autowired
	private CountryMapper countryMapper;

	@Override
	public GetCountryResp query (HttpServletRequest request, GetCountryReq req)
	{
		LOGGER.debug (String.format ("[CountryManageResourceImpl].[query]---->[GetCountryReq]:%s", req));
		GetCountryResp resp = new GetCountryResp ();

		//判断是否具有权限，无权限则不允许操作
		boolean hasAuth = auth (request);
		if (! hasAuth)
		{
			resp.setResultCode (Constants.AUTH_FAIL_CODE);
			resp.setResultMsg (Constants.AUTH_FAIL_MSG);
			LOGGER.debug ("[CountryManageResourceImpl].[query]---->ERROR![Auth is Fail!]");
			return resp;
		}
		if (null == req)
		{
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug ("[CountryManageResourceImpl].[query]---->ERROR![GetCountryReq is null]");
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

		Map<String, Object> params = new HashMap<> (15);
		if (! StringUtils.isEmpty (req.getCountryAbbreviation ()))
		{
			params.put ("countryAbbreviation", req.getCountryAbbreviation ());
		}
		if (! StringUtils.isEmpty (req.getCountryName ()))
		{
			params.put ("countryName", req.getCountryName ());
		}
		if (! StringUtils.isEmpty (req.getEnglishName ()))
		{
			params.put ("englishName", req.getEnglishName ());
		}
		if (! StringUtils.isEmpty (req.getTelCode ()))
		{
			params.put ("telCode", req.getTelCode ());
		}
		if (! StringUtils.isEmpty (req.getTimeZone ()))
		{
			params.put ("timeZone", req.getTimeZone ());
		}
		if (! StringUtils.isEmpty (req.getSuggestPrice ()))
		{
			params.put ("suggestPrice", req.getSuggestPrice ());
		}
		//查询全部
		if (Constants.QUERY_ALL_FLAG.equals (req.getFlag ()))
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
		try
		{
			List<Country> countries = countryMapper.query (params);
			List<CountryInfo> countryInfos = new ArrayList<> (countries.size ());
			countries.parallelStream ().forEachOrdered (country -> countryInfos.add (transferToVO (country)));
			int total = countryMapper.count (params);

			resp.setResultCode (Constants.SUCCESS_CODE);
			resp.setResultMsg (Constants.SUCCESS_MSG);
			resp.setCountryInfos (countryInfos);
			resp.setTotal (total);
			LOGGER.debug (String.format ("[CountryManageResourceImpl].[query]---->SUCCESS![GetCountryResp]:%s", resp));
			return resp;

		}
		catch (Exception e)
		{
			e.printStackTrace ();
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug (String.format ("[CountryManageResourceImpl].[query]---->ERROR!%s", e.getMessage ()));
			return resp;
		}
	}

	@Override
	public CountryResp add (HttpServletRequest request, CountryInfo countryInfo)
	{
		LOGGER.debug (String.format ("[CountryManageResourceImpl].[add]---->[CountryInfo]:%s", countryInfo));
		CountryResp resp = new CountryResp ();

		//判断是否具有权限，无权限则不允许操作
		boolean hasAuth = auth (request);
		if (! hasAuth)
		{
			resp.setResultCode (Constants.AUTH_FAIL_CODE);
			resp.setResultMsg (Constants.AUTH_FAIL_MSG);
			LOGGER.debug ("[CountryManageResourceImpl].[add]---->ERROR![Auth is Fail!]");
			return resp;
		}

		if (null == countryInfo)
		{
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug ("[CountryManageResourceImpl].[add]---->ERROR![countryInfo is null]");
			return resp;
		}

		if (StringUtils.isEmpty (countryInfo.getCountryAbbreviation ()))
		{
			resp.setResultCode (Constants.PARAMETER_MISS_CODE);
			resp.setResultMsg (Constants.PARAMETER_MISS_MSG + "countryAbbreviation !");
			LOGGER.debug ("[CountryManageResourceImpl].[add]---->ERROR![Missing parameter : countryAbbreviation]");
			return resp;
		}

		if (StringUtils.isEmpty (countryInfo.getTimeZone ()))
		{
			resp.setResultCode (Constants.PARAMETER_MISS_CODE);
			resp.setResultMsg (Constants.PARAMETER_MISS_MSG + "timeZone !");
			LOGGER.debug ("[CountryManageResourceImpl].[add]---->ERROR![Missing parameter : timeZone]");
			return resp;
		}

		if (StringUtils.isEmpty (countryInfo.getSuggestPrice ()))
		{
			resp.setResultCode (Constants.PARAMETER_MISS_CODE);
			resp.setResultMsg (Constants.PARAMETER_MISS_MSG + "suggestPrice !");
			LOGGER.debug ("[CountryManageResourceImpl].[add]---->ERROR![Missing parameter : suggestPrice]");
			return resp;
		}
		try
		{

			boolean existFlag = isExist (countryInfo);
			if (existFlag)
			{
				resp.setResultCode (Constants.DATA_EXSIST_CODE);
				resp.setResultMsg (Constants.DATA_EXSIST_MSG);
				LOGGER.debug ("[CountryManageResourceImpl].[add]---->ERROR! CountryInfo is exist!");
			}
			else
			{
				Country country = transferToEntity (countryInfo, 1);
				countryMapper.add (country);
				resp.setResultCode (Constants.SUCCESS_CODE);
				resp.setResultMsg (Constants.SUCCESS_MSG);
				LOGGER.debug ("[CountryManageResourceImpl].[add]---->SUCCESS!");
			}
			return resp;
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug (String.format ("[CountryManageResourceImpl].[add]---->ERROR!%s", e.getMessage ()));
			return resp;
		}
	}

	@Override
	public CountryResp mod (HttpServletRequest request, CountryInfo countryInfo)
	{
		LOGGER.debug (String.format ("[CountryManageResourceImpl].[mod]---->[CountryInfo]:%s", countryInfo));
		CountryResp resp = new CountryResp ();
		//判断是否具有权限，无权限则不允许操作
		boolean hasAuth = auth (request);
		if (! hasAuth)
		{
			resp.setResultCode (Constants.AUTH_FAIL_CODE);
			resp.setResultMsg (Constants.AUTH_FAIL_MSG);
			LOGGER.debug ("[CountryManageResourceImpl].[mod]---->ERROR![Auth is Fail!]");
			return resp;
		}

		if (null == countryInfo)
		{
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug ("[CountryManageResourceImpl].[mod]---->ERROR![CountryInfo is null]");
			return resp;
		}

		if (StringUtils.isEmpty (countryInfo.getCountryId ()))
		{
			resp.setResultCode (Constants.PARAMETER_MISS_CODE);
			resp.setResultMsg (Constants.PARAMETER_MISS_MSG + "countryId !");
			LOGGER.debug ("[CountryManageResourceImpl].[mod]---->ERROR![Missing parameter : countryId]");
			return resp;
		}

		try
		{
			Country country = transferToEntity (countryInfo, 0);
			countryMapper.modify (country);
			resp.setResultCode (Constants.SUCCESS_CODE);
			resp.setResultMsg (Constants.SUCCESS_MSG);
			LOGGER.debug ("[CountryManageResourceImpl].[mod]---->SUCCESS!");
			return resp;
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug (String.format ("[CountryManageResourceImpl].[mod]---->ERROR!%s", e.getMessage ()));
			return resp;
		}
	}

	@Override
	public CountryResp del (HttpServletRequest request, DelCountryReq req)
	{
		LOGGER.debug (String.format ("[CountryManageResourceImpl].[del]---->[DelCountryReq]:%s", req));
		CountryResp resp = new CountryResp ();
		//判断是否具有权限，无权限则不允许操作
		boolean hasAuth = auth (request);
		if (! hasAuth)
		{
			resp.setResultCode (Constants.AUTH_FAIL_CODE);
			resp.setResultMsg (Constants.AUTH_FAIL_MSG);
			LOGGER.debug ("[CountryManageResourceImpl].[del]---->ERROR![Auth is Fail!]");
			return resp;
		}

		if (null == req)
		{
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug ("[CountryManageResourceImpl].[del]---->ERROR![DelCountryReq is null]");
			return resp;
		}

		if (CollectionUtils.isEmpty (req.getCountryIdList ()))
		{
			resp.setResultCode (Constants.PARAMETER_MISS_CODE);
			resp.setResultMsg (Constants.PARAMETER_MISS_MSG + "countryId !");
			LOGGER.debug ("[CountryManageResourceImpl].[del]---->ERROR![Missing parameter : countryId]");
			return resp;
		}

		try
		{
			countryMapper.delete (req.getCountryIdList ());
		}
		catch (Exception e)
		{
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug (String.format ("[CountryManageResourceImpl].[del]---->ERROR!%s", e.getMessage ()));
			return resp;
		}


		LOGGER.debug ("[CountryManageResourceImpl].[del]---->SUCCESS!");
		return resp;
	}

	private Country transferToEntity (CountryInfo countryInfo, int operateType)
	{
		Country country = new Country ();
		String countryId ;
		if (operateType == 1)
		{
			countryId = UUID.randomUUID ().toString ();
		}
		else
		{
			countryId = countryInfo.getCountryId ();
		}
		country.setCountryId (countryId);
		country.setCountryAbbreviation (countryInfo.getCountryAbbreviation ());
		country.setCountryName (countryInfo.getCountryName ());
		country.setEnglishName (countryInfo.getEnglishName ());
		country.setTelCode (countryInfo.getTelCode ());
		country.setTimeZone (countryInfo.getTimeZone ());
		//校验字段suggestPrice
		if (StringUtils.isEmpty (countryInfo.getSuggestPrice ()))
		{
			country.setSuggestPrice (null);
		}
		else
		{
			country.setSuggestPrice (new BigDecimal (countryInfo.getSuggestPrice ()));
		}
		return country;
	}

	private CountryInfo transferToVO (Country country)
	{
		CountryInfo countryInfo = new CountryInfo ();
		if (country == null)
		{
			return countryInfo;
		}
		countryInfo.setCountryId (country.getCountryId ());
		countryInfo.setCountryAbbreviation (country.getCountryAbbreviation ());
		countryInfo.setCountryName (country.getCountryName ());
		countryInfo.setCreateTime (DateManager.dateToString (country.getCreateTime (),"yyyy-MM-dd HH:mm:ss"));
		countryInfo.setEnglishName (country.getEnglishName ());
		countryInfo.setTelCode (country.getTelCode ());
		countryInfo.setTimeZone (country.getTimeZone ());
		countryInfo.setSuggestPrice (country.getSuggestPrice () == null ? "" : country.getSuggestPrice ().toString ());
		return countryInfo;
	}

	/**
	 * 判断该国家信息是否存在
	 * 说明：因为国家信息都是唯一的，所以只需要根据缩写判断是否有数据即可
	 *
	 * @param countryInfo
	 * @return
	 */
	private boolean isExist (CountryInfo countryInfo)
	{
		boolean exist = false;
		Map<String, Object> params = new HashMap<>(1);
		params.put ("countryAbbreviation", countryInfo.getCountryAbbreviation ());
		List<Country> countryList = countryMapper.query (params);
		if (countryList.size () > 0)
		{
			exist = true;
		}
		return exist;
	}
}
