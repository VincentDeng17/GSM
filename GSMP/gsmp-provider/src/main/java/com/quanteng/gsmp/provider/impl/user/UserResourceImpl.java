/**
 * 文 件 名:  UserResourceImpl
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/9/13 0013
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.provider.impl.user;

import com.quanten.client.RedisClient;
import com.quanteng.gsmp.commom.core.annotation.Resource;
import com.quanteng.gsmp.commom.core.constants.Constants;
import com.quanteng.gsmp.commom.core.utils.CipherUtils;
import com.quanteng.gsmp.reporsity.entity.User;
import com.quanteng.gsmp.reporsity.mapper.UserMapper;
import com.quanteng.gsmp.resource.user.UserResource;
import com.quanteng.gsmp.resource.user.domain.UserToken;
import com.quanteng.gsmp.resource.user.request.UserLoginReq;
import com.quanteng.gsmp.resource.user.response.UserLoginResp;
import com.quanteng.gsmp.resource.user.response.UserLogoutResp;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author dyc
 * @version 2017/9/13 0013
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Resource
public class UserResourceImpl implements UserResource
{

	private static final Logger LOGGER = LoggerFactory.getLogger (UserResourceImpl.class);

	@Autowired
	UserMapper userMapper;

	@Autowired
	RedisClient redisClient;

	@Override
	public UserLoginResp login (HttpServletRequest request, UserLoginReq req, HttpServletResponse response)
	{
		LOGGER.debug (String.format ("[UserResourceImpl].[login]---->[UserLoginReq]:%s", req));
		UserLoginResp resp = new UserLoginResp ();
		if (null == req)
		{
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug ("[UserResourceImpl].[login]---->ERROR![UserLoginReq is null]");
			return resp;
		}
		if (null == req.getUserName () || "".equals (req.getUserName ()))
		{
			resp.setResultCode (Constants.PARAMETER_MISS_CODE);
			resp.setResultMsg (Constants.PARAMETER_MISS_MSG + "userName !");
			LOGGER.debug ("[UserResourceImpl].[login]---->ERROR![Missing parameter : userName]");
			return resp;
		}
		if (null == req.getPassword () || "".equals (req.getPassword ()))
		{
			resp.setResultCode (Constants.PARAMETER_MISS_CODE);
			resp.setResultMsg (Constants.PARAMETER_MISS_MSG + "password !");
			LOGGER.debug ("[UserResourceImpl].[login]---->ERROR![Missing parameter : password]");
			return resp;
		}

		try
		{
			Map<String, Object> params = new HashMap ();
			String userName = req.getUserName ();
			String password = req.getPassword ();
			params.put ("userName", userName);
			params.put ("password", password);
			List<User> users = userMapper.query (params);
			if (users.size () == 1)
			{
				String pwdEncode = CipherUtils.encrypt (password.getBytes (Constants.CHARACTER_ENCODING_FORMAT_UTF8),
						Constants.ALGORITHM_MD5);
				String loginTime = String.valueOf (System.currentTimeMillis ());
				String tokenStr = userName + ":" + pwdEncode + ":" + loginTime;
				String token = new String (Base64.encodeBase64 (tokenStr.getBytes ()));

				//保存到Redis里，key的形式：user-+username 。如 user-admin
				UserToken userToken = new UserToken ();
				userToken.setUserName (userName);
				userToken.setPwdEncode (pwdEncode);
				userToken.setLoginTime (loginTime);
				redisClient.set ("user-" + userName, userToken);

				// 创建tokenCookie
				Cookie tokenCookie = new Cookie ("userToken", token);
				tokenCookie.setPath ("/");
				tokenCookie.setMaxAge (24 * 60 * 60);
				tokenCookie.setHttpOnly (true);

				Cookie usernameCookie = new Cookie ("userName", req.getUserName ());
				usernameCookie.setPath ("/");
				usernameCookie.setMaxAge (24 * 60 * 60);
				//页面需要读取userName的值，所以不需要设置httpOnly
				//                usernameCookie.setHttpOnly(true);

				response.addCookie (tokenCookie);
				response.addCookie (usernameCookie);

				resp.setResultCode (Constants.SUCCESS_CODE);
				resp.setResultMsg (Constants.SUCCESS_MSG);
				resp.setUserName (req.getUserName ());
				LOGGER.debug ("[UserResourceImpl].[login]---->SUCCESS!");
			}
			else
			{
				resp.setResultCode (Constants.LOGIN_FAIL_CODE);
				resp.setResultMsg (Constants.LOGIN_FAIL_MSG);
				LOGGER.debug ("[UserResourceImpl].[login]---->FAIL! userName and password is not match!");
			}

			return resp;
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug (String.format ("[UserResourceImpl].[login]---->ERROR!%s", e.getMessage ()));
			return resp;
		}
	}

	@Override
	public UserLogoutResp logout (HttpServletRequest request, HttpServletResponse response)
	{
		LOGGER.debug ("[UserResourceImpl].[logout]---->Begin:");
		UserLogoutResp resp = new UserLogoutResp ();

		try
		{
			Cookie tokenCookie = new Cookie ("userToken", "");
			tokenCookie.setPath ("/");
			tokenCookie.setMaxAge (0);
			tokenCookie.setHttpOnly (true);

			Cookie usernameCookie = new Cookie ("username", "");
			usernameCookie.setPath ("/");
			usernameCookie.setMaxAge (0);
			usernameCookie.setHttpOnly (true);

			response.addCookie (tokenCookie);
			response.addCookie (usernameCookie);

			resp.setResultCode (Constants.SUCCESS_CODE);
			resp.setResultMsg (Constants.SUCCESS_MSG);
			LOGGER.debug ("[UserResourceImpl].[logout]---->SUCCESS!");
			return resp;
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			resp.setResultCode (Constants.FAIL_CODE);
			resp.setResultMsg (Constants.FAIL_MSG);
			LOGGER.debug (String.format ("[UserResourceImpl].[logout]---->ERROR!%s", e.getMessage ()));
			return resp;
		}
	}
}
