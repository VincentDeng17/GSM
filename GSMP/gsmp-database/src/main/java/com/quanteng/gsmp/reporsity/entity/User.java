/**
 * 文 件 名:  User
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/9/13 0013
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.reporsity.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <用户Entity类>
 *
 * @author dyc
 * @version 2017/9/13 0013
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Getter
@Setter
public class User implements Serializable {

    private static final long serialVersionUID = 1430774348417053733L;

    private String uId;
    private String userName;
    private String password;
    private Date createTime;

    @Override
    public String toString() {
        return "User{" +
                "uId='" + uId + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
