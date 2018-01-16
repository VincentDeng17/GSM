/**
 * 文 件 名:  DownloadManageResourceImpl
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/9/11 0011
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsms.provider.impl.download;

import com.quanteng.gsms.commom.core.annotation.Resource;
import com.quanteng.gsms.commom.core.constants.Constants;
import com.quanteng.gsms.commom.core.utils.MultiThreadHttpManager;
import com.quanteng.gsms.reporsity.entity.Channel;
import com.quanteng.gsms.reporsity.entity.ClickRecord;
import com.quanteng.gsms.reporsity.entity.DownloadStatistics;
import com.quanteng.gsms.reporsity.mapper.ChannelMapper;
import com.quanteng.gsms.reporsity.mapper.ClickRecordMapper;
import com.quanteng.gsms.reporsity.mapper.DownloadStatisticsMapper;
import com.quanteng.gsms.resource.download.DownloadManageResource;
import com.quanteng.gsms.resource.download.response.DownloadInfoResp;
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
 * @version 2017/9/11 0011
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Resource
public class DownloadManageResourceImpl  implements DownloadManageResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadManageResourceImpl.class);

    @Autowired
    DownloadStatisticsMapper downloadStatisticsMapper;

    @Autowired
    ClickRecordMapper clickRecordMapper;

    @Autowired
    ChannelMapper channelMapper;

    @Override
    public DownloadInfoResp notify(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("[DownloadManageResourceImpl].[notify]---->[HttpServletRequest]:{}", request.toString());
        DownloadInfoResp resp = new DownloadInfoResp();
        String clickId = request.getParameter("click_id");
        String customerId = request.getParameter("customer_id");
        String linkId = request.getParameter("link_id");
        LOGGER.debug("[DownloadManageResourceImpl].[notify]---->[request params]:{}", "[clickId]:"+clickId + ",[customerId]:" + customerId + ",[linkId]:" + linkId);

        if (null == clickId || "".equals(clickId)) {
            resp.setResultCode(Constants.PARAMETER_MISS_CODE);
            resp.setResultMsg(Constants.PARAMETER_MISS_MSG + "click_id !");
            LOGGER.debug("[DownloadManageResourceImpl].[notify]---->ERROR![Missing parameter : click_id]");
            return resp;
        }
        if (null == customerId && "".equals(customerId)) {
            resp.setResultCode(Constants.PARAMETER_MISS_CODE);
            resp.setResultMsg(Constants.PARAMETER_MISS_MSG + "customer_id !");
            LOGGER.debug("[DownloadManageResourceImpl].[notify]---->ERROR![Missing parameter : customer_id]");
            return resp;
        }
        if (null == linkId || "".equals(linkId)) {
            resp.setResultCode(Constants.PARAMETER_MISS_CODE);
            resp.setResultMsg(Constants.PARAMETER_MISS_MSG + "link_id !");
            LOGGER.debug("[DownloadManageResourceImpl].[notify]---->ERROR![Missing parameter : link_id]");
            return resp;
        }

        try {

            //此处linkId对应点击记录表的主键
            ClickRecord clickRecord = clickRecordMapper.queryByClickId(linkId);

            String subClickId = clickRecord.getSubClickId();
            String result = " Not need to notify!";
            //如果subClickId不为空，则通知下游渠道
            if (subClickId != null) {
                Channel channel = channelMapper.queryById(clickRecord.getSubChannel());
                String notifyUrl = channel.getNotifyUrl();
                String callbackParams = channel.getCallbackParams();
                if (callbackParams != null) {
                    String subReqParams = clickRecord.getSubReqParams();
                    String notifyParams = getNotifyParams(callbackParams, subReqParams);
                    notifyUrl = notifyUrl + "?" + notifyParams;
                }
                result = MultiThreadHttpManager.getInstance().getJSON(notifyUrl, null, 30 * 1000, null);
                LOGGER.debug("[DownloadManageResourceImpl].[notify]---->[Notify subChannel result]:{}", result);
            }

            DownloadStatistics downloadStatistics = new DownloadStatistics();
            String mId = UUID.randomUUID().toString();
            downloadStatistics.setMId(mId);
            downloadStatistics.setAppName(clickRecord.getAppName());
            downloadStatistics.setChannel(clickId);
            downloadStatistics.setSubChannel(clickRecord.getSubChannel());
            downloadStatistics.setThirdSubChannel(clickRecord.getThirdSubChannel());
            downloadStatistics.setSource(customerId);
            downloadStatistics.setLinkId(linkId);
            downloadStatistics.setSubNotifyResult(result);
            downloadStatisticsMapper.add(downloadStatistics);

            return resp;
        } catch (Exception e) {
            e.printStackTrace();
            resp.setResultCode(Constants.FAIL_CODE);
            resp.setResultMsg(Constants.FAIL_MSG);
            LOGGER.debug("[DownloadManageResourceImpl].[notify]---->ERROR!{}", e.getMessage());
            return resp;
        }
    }

    /**
     * 根据配置组装notifyUrl后面的参数
     *
     * @param callbackParam
     * @param subReqParam
     * @return
     */
    private String getNotifyParams(String callbackParam, String subReqParam) {
        String notifyparams = "";
        String[] srParams = subReqParam.split("&");
        if (callbackParam.contains(",")) {
            String[] callbackparams1 = callbackParam.split(",");
            for (int i = 0; i < callbackparams1.length; i++) {
                String cbParam = callbackparams1[i];
                String[] cbParams = cbParam.split("-");
                String cbValue = "";
                for (int j = 0; j < srParams.length; j++) {
                    String srParam = srParams[j];
                    if (srParam.contains(cbParams[1])) {
                        String[] srps = srParam.split("=");
                        if (srps[0].equals(cbParams[1])) {
                            cbValue = srps[1];
                        }
                    }
                }
                notifyparams = notifyparams + cbParams[0] + "=" + cbValue + "&";
            }
            notifyparams = notifyparams.substring(0, notifyparams.length() - 1);
        } else {
            String[] cbParams = callbackParam.split("-");
            String cbValue = "";
            for (int i = 0; i < srParams.length; i++) {
                String srParam = srParams[i];
                if (srParam.contains(cbParams[1])) {
                    String[] srps = srParam.split("=");
                    if (srps[0].equals(cbParams[1])) {
                        cbValue = srps[1];
                    }
                }
            }
            notifyparams = cbParams[0] + "=" + cbValue;
        }
        return notifyparams;
    }

}
