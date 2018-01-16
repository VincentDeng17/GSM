/**
 * 文 件 名:  CHReq
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2018/2/8 0008
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.resource.task.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author dyc
 * @version 2018/2/8 0008
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Getter
@Setter
public class CHReq implements Serializable {

    private static final long serialVersionUID = -7965363618508883256L;

    private String opType;
    private String startTime;
    private String endTime;

    @Override
    public String toString() {
        return "CHReq{" +
                "opType='" + opType + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
