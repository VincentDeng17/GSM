/**
 * 文 件 名:  ChannelManageResourceImpl
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/10/10 0010
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.provider.impl.channel;

import com.quanteng.gsmp.commom.core.annotation.Resource;
import com.quanteng.gsmp.commom.core.constants.Constants;
import com.quanteng.gsmp.provider.impl.BasicMethod;
import com.quanteng.gsmp.reporsity.entity.Channel;
import com.quanteng.gsmp.reporsity.mapper.ChannelMapper;
import com.quanteng.gsmp.resource.channel.ChannelManageResource;
import com.quanteng.gsmp.resource.channel.domain.ChannelInfo;
import com.quanteng.gsmp.resource.channel.request.DelChannelInfoReq;
import com.quanteng.gsmp.resource.channel.request.GetChannelInfoListReq;
import com.quanteng.gsmp.resource.channel.response.ChannelManageResp;
import com.quanteng.gsmp.resource.channel.response.GetChannelInfoListResp;
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
 * @version 2017/10/10 0010
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Resource
public class ChannelManageResourceImpl extends BasicMethod implements ChannelManageResource
{

	private static final Logger LOGGER = LoggerFactory.getLogger (ChannelManageResourceImpl.class);

	@Autowired
	ChannelMapper channelMapper;

	@Override
	public GetChannelInfoListResp queryChannelInfos (HttpServletRequest request, GetChannelInfoListReq req)
	{
		LOGGER.debug (
				String.format ("[ChannelManageResourceImpl].[queryChannelInfos]---->[GetChannelInfoListReq]:%s", req));
		GetChannelInfoListResp resp = new GetChannelInfoListResp ();
		List<ChannelInfo> channelInfos = new ArrayList<> ();

		//判断是否具有权限，无权限则不允许操作
		boolean hasAuth = auth (request);
		if (! hasAuth)
		{
			resp.setResultCode (Constants.AUTH_FAIL_CODE);
			resp.setResultMsg (Constants.AUTH_FAIL_MSG);
			LOGGER.debug ("[ChannelManageResourceImpl].[queryChannelInfos]---->ERROR![Auth is Fail!]");
			return resp;
		}
		if (null == req)
		{
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug ("[ChannelManageResourceImpl].[queryChannelInfos]---->ERROR![GetChannelInfoListReq is null]");
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
			if (null != req.getChannelId () && ! "".equals (req.getChannelId ()))
			{
				params.put ("channelId", req.getChannelId ());
			}
			if (null != req.getChannelName () && ! "".equals (req.getChannelName ()))
			{
				params.put ("channelName", req.getChannelName ());
			}
			if (null != req.getLevel () && ! "".equals (req.getLevel ()))
			{
				params.put ("level", req.getLevel ());
			}
			if (null != req.getRemarks () && ! "".equals (req.getRemarks ()))
			{
				params.put ("remarks", req.getRemarks ());
			}
			params.put ("start", (req.getPageIndex () - 1) * req.getPageSize ());
			params.put ("psize", req.getPageSize ());

			List<Channel> channels = channelMapper.query (params);
			channelInfos = getchannelInfos (channels);
			int total = channelMapper.count (params);

			resp.setResultCode (Constants.SUCCESS_CODE);
			resp.setResultMsg (Constants.SUCCESS_MSG);
			resp.setChannelInfos (channelInfos);
			resp.setTotal (total);
			LOGGER.debug (String.format (
					"[ChannelManageResourceImpl].[queryChannelInfos]---->SUCCESS![GetChannelInfoListResp]:%s", resp));
			return resp;

		}
		catch (Exception e)
		{
			e.printStackTrace ();
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug (
					String.format ("[ChannelManageResourceImpl].[queryChannelInfos]---->ERROR!%s", e.getMessage ()));
			return resp;
		}
	}

	@Override
	public ChannelManageResp addChannelInfo (HttpServletRequest request, ChannelInfo channelInfo)
	{
		LOGGER.debug (String.format ("[ChannelManageResourceImpl].[addChannelInfo]---->[ChannelInfo]:%s", channelInfo));
		ChannelManageResp resp = new ChannelManageResp ();

		//判断是否具有权限，无权限则不允许操作
		boolean hasAuth = auth (request);
		if (! hasAuth)
		{
			resp.setResultCode (Constants.AUTH_FAIL_CODE);
			resp.setResultMsg (Constants.AUTH_FAIL_MSG);
			LOGGER.debug ("[ChannelManageResourceImpl].[addChannelInfo]---->ERROR![Auth is Fail!]");
			return resp;
		}

		if (null == channelInfo)
		{
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug ("[ChannelManageResourceImpl].[addChannelInfo]---->ERROR![ChannelInfo is null]");
			return resp;
		}

		if (null == channelInfo.getChannelId () || "".equals (channelInfo.getChannelId ()))
		{
			resp.setResultCode (Constants.PARAMETER_MISS_CODE);
			resp.setResultMsg (Constants.PARAMETER_MISS_MSG + "channelId !");
			LOGGER.debug ("[ChannelManageResourceImpl].[addChannelInfo]---->ERROR![Missing parameter : channelId]");
			return resp;
		}
		if (null == channelInfo.getChannelName () || "".equals (channelInfo.getChannelName ()))
		{
			resp.setResultCode (Constants.PARAMETER_MISS_CODE);
			resp.setResultMsg (Constants.PARAMETER_MISS_MSG + "channelName !");
			LOGGER.debug ("[ChannelManageResourceImpl].[addChannelInfo]---->ERROR![Missing parameter : channelName]");
			return resp;
		}
		if (null == channelInfo.getLevel () || "".equals (channelInfo.getLevel ()))
		{
			resp.setResultCode (Constants.PARAMETER_MISS_CODE);
			resp.setResultMsg (Constants.PARAMETER_MISS_MSG + "level !");
			LOGGER.debug ("[ChannelManageResourceImpl].[addChannelInfo]---->ERROR![Missing parameter : level]");
			return resp;
		}
		if (null == channelInfo.getChannelName () || "".equals (channelInfo.getChannelName ()))
		{
			resp.setResultCode (Constants.PARAMETER_MISS_CODE);
			resp.setResultMsg (Constants.PARAMETER_MISS_MSG + "notifyUrl !");
			LOGGER.debug ("[ChannelManageResourceImpl].[addChannelInfo]---->ERROR![Missing parameter : notifyUrl]");
			return resp;
		}

		try
		{
			boolean existFlag = isExist (channelInfo);
			if (existFlag)
			{
				resp.setResultCode (Constants.DATA_EXSIST_CODE);
				resp.setResultMsg (Constants.DATA_EXSIST_MSG);
				LOGGER.debug ("[ChannelManageResourceImpl].[addChannelInfo]---->ERROR! ChannelInfo is exist!");
			}
			else
			{
				Channel channel = transferToEntity (channelInfo);
				channelMapper.add (channel);
				resp.setResultCode (Constants.SUCCESS_CODE);
				resp.setResultMsg (Constants.SUCCESS_MSG);
				LOGGER.debug ("[ChannelManageResourceImpl].[addChannelInfo]---->SUCCESS!");
			}
			return resp;
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug (String.format ("[ChannelManageResourceImpl].[addChannelInfo]---->ERROR!%s", e.getMessage ()));
			return resp;
		}
	}

	@Override
	public ChannelManageResp modChannelInfo (HttpServletRequest request, ChannelInfo channelInfo)
	{
		LOGGER.debug (String.format ("[ChannelManageResourceImpl].[modChannelInfo]---->[ChannelInfo]:%s", channelInfo));
		ChannelManageResp resp = new ChannelManageResp ();
		//判断是否具有权限，无权限则不允许操作
		boolean hasAuth = auth (request);
		if (! hasAuth)
		{
			resp.setResultCode (Constants.AUTH_FAIL_CODE);
			resp.setResultMsg (Constants.AUTH_FAIL_MSG);
			LOGGER.debug ("[ChannelManageResourceImpl].[modChannelInfo]---->ERROR![Auth is Fail!]");
			return resp;
		}

		if (null == channelInfo)
		{
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug ("[ChannelManageResourceImpl].[modChannelInfo]---->ERROR![ChannelInfo is null]");
			return resp;
		}

		if (null == channelInfo.getChannelId () || "".equals (channelInfo.getChannelId ()))
		{
			resp.setResultCode (Constants.PARAMETER_MISS_CODE);
			resp.setResultMsg (Constants.PARAMETER_MISS_MSG + "channelId !");
			LOGGER.debug ("[ChannelManageResourceImpl].[modChannelInfo]---->ERROR![Missing parameter : channelId]");
			return resp;
		}
		if (null == channelInfo.getChannelName () || "".equals (channelInfo.getChannelName ()))
		{
			resp.setResultCode (Constants.PARAMETER_MISS_CODE);
			resp.setResultMsg (Constants.PARAMETER_MISS_MSG + "channelName !");
			LOGGER.debug ("[ChannelManageResourceImpl].[modChannelInfo]---->ERROR![Missing parameter : channelName]");
			return resp;
		}
		if (null == channelInfo.getLevel () || "".equals (channelInfo.getLevel ()))
		{
			resp.setResultCode (Constants.PARAMETER_MISS_CODE);
			resp.setResultMsg (Constants.PARAMETER_MISS_MSG + "level !");
			LOGGER.debug ("[ChannelManageResourceImpl].[modChannelInfo]---->ERROR![Missing parameter : level]");
			return resp;
		}
		if (null == channelInfo.getChannelName () || "".equals (channelInfo.getChannelName ()))
		{
			resp.setResultCode (Constants.PARAMETER_MISS_CODE);
			resp.setResultMsg (Constants.PARAMETER_MISS_MSG + "notifyUrl !");
			LOGGER.debug ("[ChannelManageResourceImpl].[modChannelInfo]---->ERROR![Missing parameter : notifyUrl]");
			return resp;
		}

		try
		{
			Channel channel = transferToEntity (channelInfo);
			channelMapper.modify (channel);
			resp.setResultCode (Constants.SUCCESS_CODE);
			resp.setResultMsg (Constants.SUCCESS_MSG);
			LOGGER.debug ("[ChannelManageResourceImpl].[modChannelInfo]---->SUCCESS!");
			return resp;
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug (String.format ("[ChannelManageResourceImpl].[modChannelInfo]---->ERROR!%s", e.getMessage ()));
			return resp;
		}
	}

	@Override
	public ChannelManageResp delChannelInfo (HttpServletRequest request, DelChannelInfoReq req)
	{
		LOGGER.debug (String.format ("[ChannelManageResourceImpl].[delChannelInfo]---->[DelChannelInfoReq]:%s", req));
		ChannelManageResp resp = new ChannelManageResp ();
		//判断是否具有权限，无权限则不允许操作
		boolean hasAuth = auth (request);
		if (! hasAuth)
		{
			resp.setResultCode (Constants.AUTH_FAIL_CODE);
			resp.setResultMsg (Constants.AUTH_FAIL_MSG);
			LOGGER.debug ("[ChannelManageResourceImpl].[delChannelInfo]---->ERROR![Auth is Fail!]");
			return resp;
		}

		if (null == req)
		{
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug ("[ChannelManageResourceImpl].[delChannelInfo]---->ERROR![DelChannelInfoReq is null]");
			return resp;
		}

		if (null == req.getChannelId () || "".equals (req.getChannelId ()))
		{
			resp.setResultCode (Constants.PARAMETER_MISS_CODE);
			resp.setResultMsg (Constants.PARAMETER_MISS_MSG + "channelId !");
			LOGGER.debug ("[ChannelManageResourceImpl].[delChannelInfo]---->ERROR![Missing parameter : channelId]");
			return resp;
		}
		try
		{
			channelMapper.delete (req.getChannelId ());
			resp.setResultCode (Constants.SUCCESS_CODE);
			resp.setResultMsg (Constants.SUCCESS_MSG);
			LOGGER.debug ("[ChannelManageResourceImpl].[delChannelInfo]---->SUCCESS!");
			return resp;
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug (String.format ("[ChannelManageResourceImpl].[delChannelInfo]---->ERROR!%s", e.getMessage ()));
			return resp;
		}
	}

	/**
	 * 将查询结果转换成响应结果格式
	 *
	 * @param channelList
	 * @return
	 */
	private List<ChannelInfo> getchannelInfos (List<Channel> channelList)
	{
		List<ChannelInfo> channelInfoList = new ArrayList<> ();
		if (channelList.size () > 0)
		{
			for (int i = 0; i < channelList.size (); i++)
			{
				Channel channel = channelList.get (i);
				ChannelInfo channelInfo = new ChannelInfo ();
				channelInfo.setChannelId (channel.getChannelId ());
				channelInfo.setChannelName (channel.getChannelName ());
				channelInfo.setLevel (channel.getLevel ());
				channelInfo.setRemarks (channel.getRemarks ());
				channelInfo.setNotifyUrl (channel.getNotifyUrl ());
				channelInfo.setCallbackParams (channel.getCallbackParams ());
				channelInfoList.add (channelInfo);
			}
		}
		return channelInfoList;
	}

	/**
	 * 将入参转换成entity实体
	 *
	 * @param channelInfo
	 * @return
	 */
	private Channel transferToEntity (ChannelInfo channelInfo)
	{
		Channel channel = new Channel ();
		channel.setChannelId (channelInfo.getChannelId ());
		channel.setChannelName (channelInfo.getChannelName ());
		channel.setLevel (channelInfo.getLevel ());
		channel.setRemarks (channelInfo.getRemarks ());
		channel.setNotifyUrl (channelInfo.getNotifyUrl ());
		channel.setCallbackParams (channelInfo.getCallbackParams ());
		return channel;
	}

	/**
	 * 判断是否已存在
	 *
	 * @param channelInfo
	 * @return
	 */
	private boolean isExist (ChannelInfo channelInfo)
	{
		boolean exist = false;
		Map<String, Object> params = new HashMap ();
		params.put ("channelId", channelInfo.getChannelId ());
		Channel channel = channelMapper.queryById (channelInfo.getChannelId ());
		if (channel != null)
		{
			String channelId = channel.getChannelId ();
			String channelName = channel.getChannelName ();
			String level = channel.getLevel ();
			if (channelId.equals (channelInfo.getChannelId ()) && channelName.equals (channelInfo.getChannelName ())
					&& level.equals (channelInfo.getLevel ()))
			{
				exist = true;
			}
		}
		return exist;
	}
}
