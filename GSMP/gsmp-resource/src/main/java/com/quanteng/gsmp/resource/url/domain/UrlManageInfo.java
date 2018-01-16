/**
 * 文 件 名:  UrlManageInfo
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/9/11 0011
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.resource.url.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <URL管理信息类>
 *
 * @author dyc
 * @version 2017/9/11 0011
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UrlManageInfo implements Serializable {

    private static final long serialVersionUID = 4222194043976238441L;

    private String mId;

    private String appName;

    private String hwUrl;

    private String clickId;

    private String subChannel;

    private String thirdSubChannel;

    private String price;

//    private String channelUrl;

    private String promotionalUrl;

//    private String linkId;

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    @Override
    public String toString() {
        return "UrlManageInfo{" +
                "mId='" + mId + '\'' +
                ", appName='" + appName + '\'' +
                ", hwUrl='" + hwUrl + '\'' +
                ", clickId='" + clickId + '\'' +
                ", subChannel='" + subChannel + '\'' +
                ", thirdSubChannel='" + thirdSubChannel + '\'' +
                ", price='" + price + '\'' +
                ", promotionalUrl='" + promotionalUrl + '\'' +
                '}';
    }
}
