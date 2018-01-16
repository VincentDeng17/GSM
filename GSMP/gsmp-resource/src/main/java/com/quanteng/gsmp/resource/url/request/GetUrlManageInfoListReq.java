/**
 * 文 件 名:  GetUrlManageInfoListReq
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/9/11 0011
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.resource.url.request;

import com.quanteng.gsmp.commom.core.basicmodule.BasicRequset;
import lombok.Getter;
import lombok.Setter;

/**
 * <查询URL管理信息List请求类>
 *
 * @author dyc
 * @version 2017/9/11 0011
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Getter
@Setter
public class GetUrlManageInfoListReq extends BasicRequset {

    private String appName;
    private String clickId;
    private String subChannel;
    private String thirdSubChannel;

    @Override
    public String toString() {
        return "GetUrlManageInfoListReq{" +
                "appName='" + appName + '\'' +
                ", clickId='" + clickId + '\'' +
                ", subChannel='" + subChannel + '\'' +
                ", thirdSubChannel='" + thirdSubChannel + '\'' +
                '}';
    }


}
