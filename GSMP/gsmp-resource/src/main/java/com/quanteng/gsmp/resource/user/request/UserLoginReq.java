/**
 * 文 件 名:  UserLoginReq
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/9/13 0013
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.resource.user.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <用户登录请求>
 *
 * @author dyc
 * @version 2017/9/13 0013
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Getter
@Setter
public class UserLoginReq implements Serializable {

    private static final long serialVersionUID = -428550342742614500L;

    private String userName;
    private String password;

    @Override
    public String toString() {
        return "UserLoginReq{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
