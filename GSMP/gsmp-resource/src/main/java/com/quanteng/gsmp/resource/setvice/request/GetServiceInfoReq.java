/**
 * 文 件 名:  GetServiceInfoReq
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/10/23 0023
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.resource.setvice.request;

import com.quanteng.gsmp.commom.core.basicmodule.BasicRequset;
import lombok.Getter;
import lombok.Setter;

/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author dyc
 * @version 2017/10/23 0023
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Getter
@Setter
public class GetServiceInfoReq extends BasicRequset {

    private String serviceCode;
    private String serviceName;
    /*1:查询全部，2：分页查询*/
    private String flag;

    @Override
    public String toString ()
    {
        final StringBuffer sb = new StringBuffer ("GetServiceInfoReq{");
        sb.append ("serviceCode='").append (serviceCode).append ('\'');
        sb.append (", serviceName='").append (serviceName).append ('\'');
        sb.append (", flag='").append (flag).append ('\'');
        sb.append ('}');
        return sb.toString ();
    }
}
