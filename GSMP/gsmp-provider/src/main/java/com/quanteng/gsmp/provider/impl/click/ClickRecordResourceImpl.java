/**
 * 文 件 名:  ClickRecordResourceImpl
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/10/19 0019
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.provider.impl.click;

import com.quanteng.gsmp.commom.core.annotation.Resource;
import com.quanteng.gsmp.commom.core.constants.Constants;
import com.quanteng.gsmp.commom.core.utils.DateManager;
import com.quanteng.gsmp.provider.impl.BasicMethod;
import com.quanteng.gsmp.reporsity.entity.ClickRecord;
import com.quanteng.gsmp.reporsity.mapper.ClickRecordMapper;
import com.quanteng.gsmp.resource.click.ClickRecordResource;
import com.quanteng.gsmp.resource.click.domain.ClickRecordInfo;
import com.quanteng.gsmp.resource.click.request.GetClickRecordReq;
import com.quanteng.gsmp.resource.click.response.GetClickRecordResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author dyc
 * @version 2017/10/19 0019
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Resource
public class ClickRecordResourceImpl extends BasicMethod implements ClickRecordResource
{

	private static final Logger LOGGER = LoggerFactory.getLogger (ClickRecordResourceImpl.class);

	@Autowired
	ClickRecordMapper clickRecordMapper;

	@Override
	public GetClickRecordResp query (HttpServletRequest request, GetClickRecordReq req)
	{
		LOGGER.debug (String.format ("[ClickRecordResourceImpl].[query]---->[GetClickRecordReq]:%s", req));
		GetClickRecordResp resp = new GetClickRecordResp ();
		List<ClickRecordInfo> clickRecordInfos = new ArrayList<> ();

		//判断是否具有权限，无权限则不允许操作
		boolean hasAuth = auth (request);
		if (! hasAuth)
		{
			resp.setResultCode (Constants.AUTH_FAIL_CODE);
			resp.setResultMsg (Constants.AUTH_FAIL_MSG);
			LOGGER.debug ("[ClickRecordResourceImpl].[query]---->ERROR![Auth is Fail!]");
			return resp;
		}

		if (null == req)
		{
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug ("[ClickRecordResourceImpl].[query]---->ERROR![GetClickRecordReq is null]");
			return resp;
		}
		if ("".equals (req.getPageIndex ()))
		{
			req.setPageIndex (1);
		}
		if ("".equals (req.getPageSize ()))
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
			if (null != req.getChannel () && ! "".equals (req.getChannel ()))
			{
				params.put ("channel", req.getChannel ());
			}
			if (null != req.getSubChannel () && ! "".equals (req.getSubChannel ()))
			{
				params.put ("subChannel", req.getSubChannel ());
			}
			if (null != req.getThirdSubChannel () && ! "".equals (req.getThirdSubChannel ()))
			{
				params.put ("thirdSubChannel", req.getThirdSubChannel ());
			}
			if (null != req.getStartTime () && ! "".equals (req.getStartTime ()))
			{
				params.put ("startTime", req.getStartTime ());
			}
			if (null != req.getEndTime () && ! "".equals (req.getEndTime ()))
			{
				params.put ("endTime", req.getEndTime ());
			}
			params.put ("start", (req.getPageIndex () - 1) * req.getPageSize ());
			params.put ("psize", req.getPageSize ());

			List<ClickRecord> clickRecordList = clickRecordMapper.query (params);
			clickRecordInfos = getClickRecordInfoList (clickRecordList);

			int total = clickRecordMapper.getTotal (params);

			resp.setResultCode (Constants.SUCCESS_CODE);
			resp.setResultMsg (Constants.SUCCESS_MSG);
			resp.setClickRecordInfos (clickRecordInfos);
			resp.setTotal (total);
			LOGGER.debug (
					String.format ("[ClickRecordResourceImpl].[query]---->SUCCESS![GetClickRecordResp]:%s", resp));
			return resp;
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug (String.format ("[ClickRecordResourceImpl].[query]---->ERROR!%s", e.getMessage ()));
			return resp;
		}
	}

	/**
	 * Entity转成响应实体
	 *
	 * @param clickRecordList
	 * @return
	 */
	private List<ClickRecordInfo> getClickRecordInfoList (List<ClickRecord> clickRecordList)
	{
		List<ClickRecordInfo> clickRecordInfoList = new ArrayList<> ();
		if (clickRecordList.size () > 0)
		{
			for (int i = 0; i < clickRecordList.size (); i++)
			{
				ClickRecord clickRecord = clickRecordList.get (i);
				ClickRecordInfo clickRecordInfo = new ClickRecordInfo ();
				clickRecordInfo.setClickId (clickRecord.getClickId ());
				clickRecordInfo.setAppName (clickRecord.getAppName ());
				clickRecordInfo.setChannel (clickRecord.getChannel ());
				clickRecordInfo.setSubChannel (clickRecord.getSubChannel ());
				clickRecordInfo.setThirdSubChannel (clickRecord.getThirdSubChannel ());
				clickRecordInfo.setSubClickId (clickRecord.getSubClickId ());
				clickRecordInfo.setSubReqParams (clickRecord.getSubReqParams ());
				clickRecordInfo.setChannelUrl (clickRecord.getChannelUrl ());
				clickRecordInfo
						.setClickTime (DateManager.dateToString (clickRecord.getClickTime (), "yyyy-MM-dd HH:mm:ss"));
				clickRecordInfoList.add (clickRecordInfo);
			}
		}
		return clickRecordInfoList;
	}
}
