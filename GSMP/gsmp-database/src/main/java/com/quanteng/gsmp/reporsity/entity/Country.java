/**
 * 文 件 名:  Country
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/10/21 0021
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.reporsity.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <国家地区信息>
 *
 * @author dyc
 * @version 2017/10/21 0021
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Getter
@Setter
public class Country implements Serializable {

    private static final long serialVersionUID = -6758086351063891689L;

    private String countryId;
    private String countryAbbreviation;
    private String countryName;
    private String englishName;
    private String telCode;
    private Date createTime;
    private String timeZone;
    private BigDecimal suggestPrice;

    @Override
    public String toString ()
    {
        final StringBuffer sb = new StringBuffer ("Country{");
        sb.append ("countryId='").append (countryId).append ('\'');
        sb.append (", countryAbbreviation='").append (countryAbbreviation).append ('\'');
        sb.append (", countryName='").append (countryName).append ('\'');
        sb.append (", englishName='").append (englishName).append ('\'');
        sb.append (", telCode='").append (telCode).append ('\'');
        sb.append (", createTime=").append (createTime);
        sb.append (", timeZone='").append (timeZone).append ('\'');
        sb.append (", suggestPrice=").append (suggestPrice);
        sb.append ('}');
        return sb.toString ();
    }
}
