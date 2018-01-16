/**
 * 文 件 名:  Constants
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/9/11 0011
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.commom.core.constants;

/**
 * <常量类>
 *
 * @author dyc
 * @version 2017/9/11 0011
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class Constants {

    public static final String SUCCESS_CODE = "0";
    public static final String SUCCESS_MSG = "SUCCESS";

    public static final String FAIL_CODE = "-1";
    public static final String FAIL_MSG = "OPERATE FAIL";

    public static final String PARAMETER_MISS_CODE = "900";
    public static final String PARAMETER_MISS_MSG = " MISSING PARAMETER ";

    public static final String LOGIN_FAIL_CODE = "901";
    public static final String LOGIN_FAIL_MSG = "userName and password is not match! ";

    public static final String AUTH_FAIL_CODE = "902";
    public static final String AUTH_FAIL_MSG = "You have no auth to operate this action! ";

    public static final String DATA_EXSIST_CODE = "903";
    public static final String DATA_EXSIST_MSG = "The data is exist in database! ";

    public static final String CHARACTER_ENCODING_FORMAT_UTF8 = "UTF-8";

    public static final String ALGORITHM_MD5 = "MD5";

    /**
     * 自建渠道默认的渠道名称：Quanten，渠道ID：10076
     */
    public static final String DEFAULT_CHANNEL_VALUE = "10076";

    /**
     * 一级渠道
     */
    public static final String CHANNEL_LEVEL_1 = "1";
    /**
     * 二级渠道
     */
    public static final String CHANNEL_LEVEL_2 = "2";
    /**
     * 三级渠道
     */
    public static final String CHANNEL_LEVEL_3 = "3";

    //GSMS域名
//    public static final String GSMS_DOMAIN_NAME = "http://118.190.159.157:28888/gsms/s/promotional";
//    public static final String GSMS_DOMAIN_NAME = "http://track.quanten.cn/gsms/s/promotional";

    public static final String GSMS_DOMAIN_KEY = "gsmsDomain";

    //youmi的回调接口链接，例;http://track.miadx.net/aff_lsr?transaction_id=1234566
//    public static final String NOTIFY_URL = "http://track.miadx.net/aff_lsr";

    /**
     * 查询所有标志
     */
    public static final String QUERY_ALL_FLAG = "1";

    /**
     * 分页查询标志
     */
    public static final String QUERY_PAGE_FLAG = "2";

}
