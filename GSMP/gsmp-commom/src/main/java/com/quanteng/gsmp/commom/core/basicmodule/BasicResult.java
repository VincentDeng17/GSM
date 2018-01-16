/**
 * 文 件 名:  BasicResult
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/9/11 0011
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.commom.core.basicmodule;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <基础响应类>
 *
 * @author dyc
 * @version 2017/9/11 0011
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Getter
@Setter
public class BasicResult implements Serializable {

    private static final long serialVersionUID = -7776510412502250033L;

    private String resultCode = "0";

    private String resultMsg = "SUCCESS";

    @Override
    public String toString() {
        return "BasicResult{" +
                "resultCode='" + resultCode + '\'' +
                ", resultMsg='" + resultMsg + '\'' +
                '}';
    }
}
