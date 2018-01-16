/**
 * 文 件 名:  DelDwnloadInfoReq
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/9/11 0011
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.resource.download.request;

import java.io.Serializable;

/**
 * <删除下载历史统计请求>
 *
 * @author dyc
 * @version 2017/9/11 0011
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DelDwnloadInfoReq implements Serializable {

    private static final long serialVersionUID = -651136746700876679L;

    private String mId;

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    @Override
    public String toString() {
        return "DelDwnloadInfoReq{" +
                "mId='" + mId + '\'' +
                '}';
    }

}
