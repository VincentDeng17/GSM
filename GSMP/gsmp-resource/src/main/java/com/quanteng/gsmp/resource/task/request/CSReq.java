/**
 * 文 件 名:  CSReq
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/11/23 0023
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
 * @version 2017/11/23 0023
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Getter
@Setter
public class CSReq implements Serializable {

    private static final long serialVersionUID = 4405781808365042802L;

    private String opType;
    private String startTime;
    private String endTime;

    @Override
    public String toString() {
        return "CSReq{" +
                "opType='" + opType + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
