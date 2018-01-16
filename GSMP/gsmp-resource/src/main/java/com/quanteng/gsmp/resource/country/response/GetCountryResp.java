/**
 * 文 件 名:  GetCountryResp
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/10/23 0023
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.resource.country.response;

import com.quanteng.gsmp.commom.core.basicmodule.BasicResult;
import com.quanteng.gsmp.resource.country.domain.CountryInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
public class GetCountryResp extends BasicResult {

    private List<CountryInfo> countryInfos;
    private int total;

    @Override
    public String toString() {
        return "GetCountryResp{" +
                "countryInfos=" + countryInfos +
                ", total=" + total +
                '}';
    }
}