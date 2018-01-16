/**
 * 文 件 名:  UrlManageInfoExp
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/9/29 0029
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.resource.url.domain;

import com.quanteng.gsmp.commom.core.export.ExcelResources;

/**
 * <URl配置导出实体>
 *
 * @author dyc
 * @version 2017/9/29 0029
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class UrlManageInfoExp {

    private String mId;

    private String appName;

    private String hwUrl;

    private String clickId;

    private String subChannel;

    private String thirdSubChannel;

//    private String channelUrl;

    private String promotionalUrl;

//    private String linkId;

//    /**
//     * 构造函数
//     *
//     * @param mId
//     * @param appName
//     * @param hwUrl
//     * @param clickId
//     * @param subChannel
//     * @param thirdSubChannel
//     * @param channelUrl
//     * @param linkId
//     */
//    public UrlManageInfoExp(String mId, String appName, String hwUrl, String clickId, String subChannel, String thirdSubChannel, String channelUrl, String linkId) {
//        this.mId = mId;
//        this.appName = appName;
//        this.hwUrl = hwUrl;
//        this.clickId = clickId;
//        this.subChannel = subChannel;
//        this.thirdSubChannel = thirdSubChannel;
//        this.channelUrl = channelUrl;
//        this.linkId = linkId;
//    }


    @ExcelResources(title = "ID", order = 1)
    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    @ExcelResources(title = "应用名称", order = 2)
    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @ExcelResources(title = "华为URL", order = 3)
    public String getHwUrl() {
        return hwUrl;
    }

    public void setHwUrl(String hwUrl) {
        this.hwUrl = hwUrl;
    }

    @ExcelResources(title = "一级渠道", order = 4)
    public String getClickId() {
        return clickId;
    }

    public void setClickId(String clickId) {
        this.clickId = clickId;
    }

    @ExcelResources(title = "二级渠道", order = 5)
    public String getSubChannel() {
        return subChannel;
    }

    public void setSubChannel(String subChannel) {
        this.subChannel = subChannel;
    }

    @ExcelResources(title = "三级渠道", order = 6)
    public String getThirdSubChannel() {
        return thirdSubChannel;
    }

    public void setThirdSubChannel(String thirdSubChannel) {
        this.thirdSubChannel = thirdSubChannel;
    }

//    @ExcelResources(title="渠道URL",order=7)
//    public String getChannelUrl() {
//        return channelUrl;
//    }
//
//    public void setChannelUrl(String channelUrl) {
//        this.channelUrl = channelUrl;
//    }

    @ExcelResources(title = "推广URL", order = 6)
    public String getPromotionalUrl() {
        return promotionalUrl;
    }

    public void setPromotionalUrl(String promotionalUrl) {
        this.promotionalUrl = promotionalUrl;
    }

//    @ExcelResources(title="LinkId",order=8)
//    public String getLinkId() {
//        return linkId;
//    }
//
//    public void setLinkId(String linkId) {
//        this.linkId = linkId;
//    }


    @Override
    public String toString() {
        return "UrlManageInfoExp{" +
                "mId='" + mId + '\'' +
                ", appName='" + appName + '\'' +
                ", hwUrl='" + hwUrl + '\'' +
                ", clickId='" + clickId + '\'' +
                ", subChannel='" + subChannel + '\'' +
                ", thirdSubChannel='" + thirdSubChannel + '\'' +
                ", promotionalUrl='" + promotionalUrl + '\'' +
                '}';
    }
}
