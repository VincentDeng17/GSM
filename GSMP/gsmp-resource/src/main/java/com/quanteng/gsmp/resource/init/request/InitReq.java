/**
 * 文 件 名:  InitReq
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/9/27 0027
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.resource.init.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <页面初始化请求>
 *
 * @author dyc
 * @version 2017/9/27 0027
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Getter
@Setter
public class InitReq implements Serializable{

    private static final long serialVersionUID = 3987553753533280011L;

    private String initType;

    @Override
    public String toString() {
        return "InitReq{" +
                "initType='" + initType + '\'' +
                '}';
    }
}
