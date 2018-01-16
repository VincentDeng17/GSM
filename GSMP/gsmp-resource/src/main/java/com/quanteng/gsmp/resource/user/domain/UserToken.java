/**
 * 文 件 名:  UserToken
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/9/14 0014
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.resource.user.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <用户token对象类>
 *
 * @author dyc
 * @version 2017/9/14 0014
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Getter
@Setter
public class UserToken implements Serializable {

    private static final long serialVersionUID = -1726693635126957042L;

    private String userName;
    private String pwdEncode;
    private String loginTime;

    @Override
    public String toString() {
        return "UserToken{" +
                "userName='" + userName + '\'' +
                ", pwdEncode='" + pwdEncode + '\'' +
                ", loginTime='" + loginTime + '\'' +
                '}';
    }
}
