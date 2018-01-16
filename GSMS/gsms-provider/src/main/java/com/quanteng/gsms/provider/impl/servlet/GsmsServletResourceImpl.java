/**
 * 文 件 名:  GsmsServletResourceImpl
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/10/17 0017
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsms.provider.impl.servlet;

import com.quanteng.gsms.commom.core.annotation.Resource;
import com.quanteng.gsms.reporsity.entity.AppUrlMapping;
import com.quanteng.gsms.reporsity.entity.ClickRecord;
import com.quanteng.gsms.reporsity.mapper.AppUrlMappingMapper;
import com.quanteng.gsms.reporsity.mapper.ChannelMapper;
import com.quanteng.gsms.reporsity.mapper.ClickRecordMapper;
import com.quanteng.gsms.resource.servlet.GsmsServletResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author dyc
 * @version 2017/10/17 0017
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Resource
public class GsmsServletResourceImpl implements GsmsServletResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(GsmsServletResourceImpl.class);

    @Autowired
    AppUrlMappingMapper appUrlMappingMapper;

    @Autowired
    ClickRecordMapper clickRecordMapper;

    @Autowired
    ChannelMapper channelMapper;

    @Override
    public void promotional(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("[GsmsServletResourceImpl].[promotional]---->Begin!");

        String uId = request.getParameter("uid");
        String subChannel = request.getParameter("sub_channel");
        String thirdSubChannel = request.getParameter("third_sub_channel");

        String subClickId = request.getParameter("click_id");

        String reqParams = request.getQueryString();
        LOGGER.debug("[GsmsServletResourceImpl].[promotional]---->[request params]:{}",
                "[uId]:" + uId + ",[subChannel]:" + subChannel + ",[thirdSubChannel]:" + thirdSubChannel + ",[subClickId]:" + subClickId + ",[reqParams]:" + reqParams);
        try {
            if (null == uId || "".equals(uId)) {
                LOGGER.debug("[GsmsServletResourceImpl].[promotional]---->ERROR![Missing parameter : uId]");
            } else {
                AppUrlMapping appUrlMapping = appUrlMappingMapper.queryByMId(uId);
                //不需要判断，本身传值为空就是""
                if (null == subChannel) {
                    subChannel = "";
                }
                if (null == thirdSubChannel) {
                    thirdSubChannel = "";
                }
                //点击记录表主键
                String clickId = UUID.randomUUID().toString();
                //link_id传QT的点击ID，方便华为转化回调时，QT也通过其对应点击ID通知下游渠道方
                String channelUrl = appUrlMapping.getHwUrl() + "?click_id=" + appUrlMapping.getClickId() + "&sub_channel=" + subChannel
                        + "&third_sub_channel=" + thirdSubChannel + "&customer_id=huawei&link_id=" + clickId;
                channelUrl.trim();
                //记录点击统计
                ClickRecord clickRecord = new ClickRecord();

                clickRecord.setClickId(clickId);
                clickRecord.setAppName(appUrlMapping.getAppName());
                clickRecord.setSubClickId(subClickId);
                clickRecord.setChannel(appUrlMapping.getClickId());
                clickRecord.setSubChannel(subChannel);
                clickRecord.setThirdSubChannel(thirdSubChannel);
                clickRecord.setSubReqParams(reqParams);
                clickRecord.setChannelUrl(channelUrl);
                clickRecordMapper.add(clickRecord);
                LOGGER.debug("[GsmsServletResourceImpl].[promotional]---->[Save clickRecord Success][ClickRecord]:{}", clickRecord.toString());
                //跳转到对应页面
                response.sendRedirect(channelUrl);

            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.debug("[GsmsServletResourceImpl].[promotional]---->ERROR! [ error message is ]:{}", e.getMessage());
        }
        LOGGER.debug("[GsmsServletResourceImpl].[promotional]---->End!");
    }

}
