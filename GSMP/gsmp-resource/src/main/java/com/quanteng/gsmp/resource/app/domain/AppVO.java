/**
 * 文 件 名:  AppVO
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  zhengbinggui
 * 修改时间:  2017/11/6
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.resource.app.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author zhengbinggui
 * @version 2017/11/6
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Getter
@Setter
public class AppVO implements Serializable
{
	private String appId;
	private String appName;
	private String appInfo;
	private String remark;
	private String createTime;

	@Override
	public String toString ()
	{
		final StringBuffer sb = new StringBuffer ("AppVO{");
		sb.append ("appId='").append (appId).append ('\'');
		sb.append (", appName='").append (appName).append ('\'');
		sb.append (", appInfo='").append (appInfo).append ('\'');
		sb.append (", remark='").append (remark).append ('\'');
		sb.append (", createTime='").append (createTime).append ('\'');
		sb.append ('}');
		return sb.toString ();
	}
}