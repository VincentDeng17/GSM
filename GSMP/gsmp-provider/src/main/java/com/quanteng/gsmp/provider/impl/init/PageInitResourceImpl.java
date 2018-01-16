/**
 * 文 件 名:  PageInitResourceImpl
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/9/27 0027
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.provider.impl.init;

import com.quanteng.gsmp.commom.core.annotation.Resource;
import com.quanteng.gsmp.commom.core.constants.Constants;
import com.quanteng.gsmp.provider.impl.BasicMethod;
import com.quanteng.gsmp.reporsity.entity.Channel;
import com.quanteng.gsmp.reporsity.mapper.ChannelMapper;
import com.quanteng.gsmp.resource.channel.domain.ChannelInfo;
import com.quanteng.gsmp.resource.init.PageInitResource;
import com.quanteng.gsmp.resource.init.request.InitReq;
import com.quanteng.gsmp.resource.init.response.InitResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author dyc
 * @version 2017/9/27 0027
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Resource
public class PageInitResourceImpl extends BasicMethod implements PageInitResource
{

	private static final Logger LOGGER = LoggerFactory.getLogger (PageInitResourceImpl.class);

	//    private static final String PAGE_ADDRESS = "http://localhost:8080/";

	@Autowired
	ChannelMapper channelMapper;

	@Override
	public InitResp init (HttpServletRequest request, InitReq req, HttpServletResponse response)
	{
		LOGGER.debug (String.format ("[PageInitResourceImpl].[init]---->[InitReq]:%s", req));
		InitResp resp = new InitResp ();

		//        String url = PAGE_ADDRESS;

		//判断是否具有权限，无权限则不允许操作
		boolean hasAuth = auth (request);
		if (! hasAuth)
		{
			resp.setResultCode (Constants.AUTH_FAIL_CODE);
			resp.setResultMsg (Constants.AUTH_FAIL_MSG);
			LOGGER.debug ("[PageInitResourceImpl].[init]---->ERROR![Auth is Fail!]");
			return resp;
		}

		if (null == req)
		{
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug ("[PageInitResourceImpl].[init]---->ERROR![InitReq is null]");
			return resp;
		}

		if (null == req.getInitType () || "".equals (req.getInitType ()))
		{
			resp.setResultCode (Constants.PARAMETER_MISS_CODE);
			resp.setResultMsg (Constants.PARAMETER_MISS_MSG + "initType !");
			LOGGER.debug ("[PageInitResourceImpl].[init]---->ERROR![Missing parameter : initType]");
			return resp;
		}

		try
		{
			//initType 1-渠道管理，2-下载统计，3-下载详情
			String initType = req.getInitType ();
			switch (initType)
			{
				case "1":
					resp = getInitChannel (resp);
					//                    url += "/channel.html";
					break;
				case "2":
					resp = getInitChannel (resp);
					//                    url += "/download-statistics.html";
					break;
				case "3":
					resp = getInitChannel (resp);
					//                    url += "/download-details.html";
					break;
				default:
					resp = getInitChannel (resp);
					break;
			}
			LOGGER.debug (String.format ("[PageInitResourceImpl].[init]---->End![InitResp]%s", resp));
			//            response.sendRedirect(url);
			return resp;
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug (String.format ("[PageInitResourceImpl].[init]---->ERROR!%s", e.getMessage ()));
			return resp;
		}
	}

	/**
	 * 获取初始化渠道信息
	 *
	 * @param resp
	 * @return
	 */
	private InitResp getInitChannel (InitResp resp)
	{
		LOGGER.debug (String.format ("[PageInitResourceImpl].[getInitChannel]---->Begin![InitResp]:%s", resp));
		Map<String, Object> params = new HashMap ();
		List<Channel> channels = channelMapper.query (params);
		if (channels.size () > 0)
		{
			List<ChannelInfo> subChannels = getChannelInfo (channels, Constants.CHANNEL_LEVEL_2);
			List<ChannelInfo> thirdSubChannels = getChannelInfo (channels, Constants.CHANNEL_LEVEL_3);
			resp.setResultCode (Constants.SUCCESS_CODE);
			resp.setResultMsg (Constants.SUCCESS_MSG);
			resp.setSubChannels (subChannels);
			resp.setThirdSubChannels (thirdSubChannels);
			LOGGER.debug (String.format ("[PageInitResourceImpl].[getInitChannel]---->SUCCESS![InitResp]:%s", resp));
		}
		else
		{
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug ("[PageInitResourceImpl].[getInitChannel]---->[query channel infomation is null !]");
		}
		LOGGER.debug (String.format ("[PageInitResourceImpl].[getInitChannel]---->End![InitResp]:%s", resp));
		return resp;
	}

	/**
	 * 获取不同级别的渠道信息
	 *
	 * @param channels
	 * @param level
	 * @return
	 */
	private List<ChannelInfo> getChannelInfo (List<Channel> channels, String level)
	{
		List<ChannelInfo> channelInfos = new ArrayList<> ();
		for (int i = 0; i < channels.size (); i++)
		{
			Channel channel = channels.get (i);
			if (level.equals (channel.getLevel ()))
			{
				ChannelInfo channelInfo = new ChannelInfo ();
				channelInfo.setChannelId (channel.getChannelId ());
				channelInfo.setChannelName (channel.getChannelName ());
				channelInfo.setLevel (channel.getLevel ());
				channelInfos.add (channelInfo);
			}
		}
		return channelInfos;
	}

}
