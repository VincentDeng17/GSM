/**
 * 文 件 名:  GetClickRecordResp
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/10/19 0019
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.resource.click.response;

import com.quanteng.gsmp.commom.core.basicmodule.BasicResult;
import com.quanteng.gsmp.resource.click.domain.ClickRecordInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author dyc
 * @version 2017/10/19 0019
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Getter
@Setter
public class GetClickRecordResp extends BasicResult {

    private List<ClickRecordInfo> clickRecordInfos;
    private int total;

    @Override
    public String
    toString() {
        return "GetClickRecordResp{" +
                "clickRecordInfos=" + clickRecordInfos +
                ", total=" + total +
                '}';
    }
}
