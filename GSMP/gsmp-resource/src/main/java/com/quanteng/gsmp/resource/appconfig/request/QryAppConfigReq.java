/**
 * 文 件 名:  QryAppConfigReq
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  zhouhaofeng
 * 修改时间:  2017/11/6
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.resource.appconfig.request;

import com.quanteng.gsmp.commom.core.basicmodule.BasicRequset;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 查询应用配置请求
 *
 * @author zhouhaofeng
 * @version 2017/11/6
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@ToString
@Setter
@Getter
public class QryAppConfigReq extends BasicRequset implements Serializable
{
	private static final long serialVersionUID = - 1L;

	private String configId;

	private String countryId;

	private String serviceId;

	private String appId;

	private String appName;

	private String originalUrl;

	private String startTime;

	private String endTime;

	/**
	 * queryType=1表示查询所有，否则为分页查询
	 */
	private String queryType;
}
