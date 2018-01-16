/**
 * 文 件 名:  ActionResult
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/10/17 0017
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.commom.core.utils;

import java.io.Serializable;

/**
 * <返回值>
 *
 * @author dyc
 * @version 2017/10/17 0017
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ActionResult<T> implements Serializable {

    /**
     * The Constant ActionResult.java
     */
    private static final long serialVersionUID = 8088693343698340331L;

    private int code;// 返回码

    private T result;// 返回结果

    private String msg;// 返回提示

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "ActionResult [code=" + code + ", result=" + result + ", msg=" + msg + "]";
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
