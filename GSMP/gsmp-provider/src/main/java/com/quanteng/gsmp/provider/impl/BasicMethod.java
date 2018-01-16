/**
 * 文 件 名:  BasicMethod
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/9/14 0014
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.provider.impl;

import com.quanten.client.RedisClient;
import com.quanteng.gsmp.resource.user.domain.UserToken;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author dyc
 * @version 2017/9/14 0014
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class BasicMethod {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasicMethod.class);

    @Autowired
    RedisClient redisClient;


    /**
     * 鉴权
     *
     * @param request
     * @return
     */
    public boolean auth(HttpServletRequest request) {
        LOGGER.debug(String.format("[BasicMethod].[auth]---->[request]:%s", request));
        boolean authFlag = false;
        Cookie tokenCookie = getCookieByName(request, "userToken");
        if (tokenCookie != null) {
            if (tokenCookie.getMaxAge() > 0 || tokenCookie.getMaxAge() == -1) {
                String token = tokenCookie.getValue();
                authFlag = analysisToken(token);
            }
        }
        LOGGER.debug(String.format("[BasicMethod].[auth]---->[authFlag]:%s", authFlag));
        return authFlag;
    }

    /**
     * 解析token，判断token中各值
     *
     * @param token
     * @return
     */
    private boolean analysisToken(String token) {
        LOGGER.debug(String.format("[BasicMethod].[analysisToken]---->[token]:%s", token));
        boolean flag = false;
        try {
            String tokenStr = new String(Base64.decodeBase64(token));
            LOGGER.debug(String.format("[BasicMethod].[analysisToken]---->[tokenStr]:%s", tokenStr));
            String[] tokenStrs = tokenStr.split(":");
            String userName = tokenStrs[0];
            String pwdEncode = tokenStrs[1];
            String loginTime = tokenStrs[2];

            //获取Redis存储的对象
            UserToken userToken = (UserToken) redisClient.get("user-" + userName, UserToken.class);
            if (userToken != null) {
                if (pwdEncode.equals(userToken.getPwdEncode()) && loginTime.equals(userToken.getLoginTime())) {
                    //时间超过24小时，失效
                    if ((System.currentTimeMillis() - Long.parseLong(loginTime)) < 86400000) {
                        flag = true;
                    }
                }
            }
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(String.format("[BasicMethod].[analysisToken]---->ERROR! [error msg]:%s", e.getMessage()));
        }
        LOGGER.debug(String.format("[BasicMethod].[analysisToken]---->[flag]:%s", flag));
        return flag;
    }

    /**
     * 根据名字获取cookie
     *
     * @param request
     * @param name
     * @return
     */
    public Cookie getCookieByName(HttpServletRequest request, String name) {
        LOGGER.debug(String.format("[BasicMethod].[getCookieByName]---->[name]:%s", name));
        try {
            Map<String, Cookie> cookieMap = ReadCookieMap(request);
            if (cookieMap.containsKey(name)) {
                Cookie cookie = (Cookie) cookieMap.get(name);
                LOGGER.debug("[BasicMethod].[getCookieByName]---->End:");
                return cookie;
            } else {
                LOGGER.debug(String.format("[BasicMethod].[getCookieByName]---->[no this cookie]:%s", name));
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(String.format("[BasicMethod].[getCookieByName]---->ERROR! [error message]:%s", e.getMessage()));
            return null;
        }
    }

    /**
     * 将cookie封装到Map里面
     *
     * @param request
     * @return
     */
    private Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

}
