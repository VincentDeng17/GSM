/**
 * 文 件 名:  Test
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/11/23 0023
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.provider.impl;

import java.math.BigDecimal;

/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author dyc
 * @version 2017/11/23 0023
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class Test {


    public static double div(double value1,double value2,int scale) throws IllegalAccessException{
        //如果精确范围小于0，抛出异常信息
        if(scale<0){
            throw new IllegalAccessException("精确度不能小于0");
        }
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.divide(b2, scale).doubleValue();
    }

    public static void main(String[] args) throws IllegalAccessException {
        int clicks = 200;
        int conversions = 34;
//        BigDecimal clicks_b = new BigDecimal(clicks);
//        BigDecimal conversions_b = new BigDecimal(conversions);
//        double rate = conversions_b.divide(clicks_b,5).doubleValue();
//        System.out.println("[rate]:"+rate);
        double clicks_d = clicks;
        double conversions_d = conversions;
        System.out.println("[clicks_d]:"+clicks_d+",[conversions_d]:"+conversions_d);
        double rate = div(conversions_d,clicks_d,2);
        System.out.println("[rate]:"+rate);
    }

}
