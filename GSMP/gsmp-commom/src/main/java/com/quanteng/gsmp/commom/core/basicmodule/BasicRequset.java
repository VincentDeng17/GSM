/**
 * 文 件 名:  BasicRequset
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
 * <基础请求类>
 *
 * @author dyc
 * @version 2017/9/11 0011
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Getter
@Setter
public class BasicRequset implements Serializable {

    private static final long serialVersionUID = 7431124369580807046L;

    private Integer pageIndex = 1;

    private Integer pageSize = 10;

    @Override
    public String toString() {
        return "BasicRequset{" +
                "pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                '}';
    }
}
