/**
 * 文 件 名:  BatchDelAppConfigResp
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  zhouhaofeng
 * 修改时间:  2017/11/6
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.resource.appconfig.response;

import com.quanteng.gsmp.commom.core.basicmodule.BasicResult;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 批量删除应用配置响应
 *
 * @author zhouhaofeng
 * @version 2017/11/6
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Setter
@Getter
@ToString
public class BatchDelAppConfigResp extends BasicResult implements Serializable
{
	private static final long serialVersionUID = - 1L;

	private List<String> configIdList;
}
