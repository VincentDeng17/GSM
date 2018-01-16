/**
 * 文 件 名:  CountryInfo
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/10/21 0021
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.resource.country.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 国家信息vo
 *
 * @author dyc
 * @version 2017/10/21 0021
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Getter
@Setter
public class CountryInfo implements Serializable {

    private static final long serialVersionUID = 5469376462075272712L;

    private String countryId;
    private String countryAbbreviation;
    private String countryName;
    private String englishName;
    private String telCode;
    private String createTime;
    private String timeZone;
    private String suggestPrice;

    @Override
    public String toString ()
    {
        final StringBuffer sb = new StringBuffer ("CountryInfo{");
        sb.append ("countryId='").append (countryId).append ('\'');
        sb.append (", countryAbbreviation='").append (countryAbbreviation).append ('\'');
        sb.append (", countryName='").append (countryName).append ('\'');
        sb.append (", englishName='").append (englishName).append ('\'');
        sb.append (", telCode='").append (telCode).append ('\'');
        sb.append (", createTime='").append (createTime).append ('\'');
        sb.append (", timeZone='").append (timeZone).append ('\'');
        sb.append (", suggestPrice='").append (suggestPrice).append ('\'');
        sb.append ('}');
        return sb.toString ();
    }
}
