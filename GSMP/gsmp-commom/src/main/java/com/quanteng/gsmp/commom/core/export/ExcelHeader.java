/**
 * 文 件 名:  ExcelHeader
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/9/29 0029
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.commom.core.export;

/**
 * <Excel头>
 *
 * @author dyc
 * @version 2017/9/29 0029
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ExcelHeader implements Comparable<ExcelHeader> {

    /**
     * excel的标题名称
     */
    private String title;
    /**
     * 每一个标题的顺序
     */
    private int order;
    /**
     * 说对应方法名称
     */
    private String methodName;

    public int compareTo(ExcelHeader o) {
        return order > o.order ? 1 : (order < o.order ? -1 : 0);
    }

    /**
     * 构造函数
     *
     * @param title
     * @param order
     * @param methodName
     */
    public ExcelHeader(String title, int order, String methodName) {
        this.title = title;
        this.order = order;
        this.methodName = methodName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public String toString() {
        return "ExcelHeader{" +
                "title='" + title + '\'' +
                ", order=" + order +
                ", methodName='" + methodName + '\'' +
                '}';
    }
}
