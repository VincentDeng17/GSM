/**
 * 文 件 名:  GetDownloadDetailsListResp
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/9/20 0020
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.resource.download.response;

import com.quanteng.gsmp.commom.core.basicmodule.BasicResult;
import com.quanteng.gsmp.resource.download.domain.DownloadDetails;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <查询下载详情列表响应>
 *
 * @author dyc
 * @version 2017/9/20 0020
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Getter
@Setter
public class GetDownloadDetailsListResp extends BasicResult {

    private List<DownloadDetails> downloadDetailsList;

    private int total;

    @Override
    public String toString() {
        return "GetDownloadDetailsListResp{" +
                "downloadDetailsList=" + downloadDetailsList +
                ", total=" + total +
                '}';
    }
}
