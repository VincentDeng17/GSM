/**
 * 文 件 名:  GetCountryReq
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/10/23 0023
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.resource.country.request;

import com.quanteng.gsmp.commom.core.basicmodule.BasicRequset;
import lombok.Getter;
import lombok.Setter;

/**
 * 获取国家信息请求
 *
 * @author dyc
 * @version 2017/10/23 0023
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Getter
@Setter
public class GetCountryReq extends BasicRequset{

    private String countryAbbreviation;
    private String countryName;
    private String englishName;
    private String telCode;
    /**
     * 1、查询全部 ；2、分页查询
     */
    private String flag;
    private String timeZone;
    private String suggestPrice;

    @Override
    public String toString ()
    {
        final StringBuffer sb = new StringBuffer ("GetCountryReq{");
        sb.append ("countryAbbreviation='").append (countryAbbreviation).append ('\'');
        sb.append (", countryName='").append (countryName).append ('\'');
        sb.append (", englishName='").append (englishName).append ('\'');
        sb.append (", telCode='").append (telCode).append ('\'');
        sb.append (", flag='").append (flag).append ('\'');
        sb.append (", timeZone='").append (timeZone).append ('\'');
        sb.append (", suggestPrice='").append (suggestPrice).append ('\'');
        sb.append ('}');
        return sb.toString ();
    }
}
