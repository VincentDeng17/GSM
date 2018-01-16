/**
 * 文 件 名:  AppInfo
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/11/6 0006
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.reporsity.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <应用基本信息> <功能详细描述>
 *
 * @author dyc
 * @version 2017/11/6 0006
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Getter
@Setter
public class AppInfo implements Serializable {

    private static final long serialVersionUID = 7026766853976628835L;

    private String appId;
    private String appName;
    private String appInfo;
    private String remark;
    private Date createTime;

    @Override
    public String toString() {
        return "AppInfo{" +
                "appId='" + appId + '\'' +
                ", appName='" + appName + '\'' +
                ", appInfo='" + appInfo + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
