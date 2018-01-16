/**
 * 文 件 名:  UrlManageResp
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/9/11 0011
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.resource.url.response;

import com.quanteng.gsmp.commom.core.basicmodule.BasicResult;
import lombok.Getter;
import lombok.Setter;

/**
 * <URL管理响应类>
 *
 * @author dyc
 * @version 2017/9/11 0011
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Getter
@Setter
public class UrlManageResp extends BasicResult {

    @Override
    public String toString() {
        return "UrlManageResp{}";
    }
}
