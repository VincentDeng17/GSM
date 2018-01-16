/**
 * 文 件 名:  GsmpException
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/9/11 0011
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.commom.core.exception;

/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author dyc
 * @version 2017/9/11 0011
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class GsmpException extends RuntimeException {

    private String resultCode;

    private String description;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public GsmpException(String resultCode, String description) {
        this.resultCode = resultCode;
        this.description = description;
    }

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public GsmpException(String resultCode) {
        this.resultCode = resultCode;
    }

    /**
     * Constructs a new runtime exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this runtime exception's detail message.
     *
     * @param description the detail message (which is saved for later retrieval
     *                    by the {@link #getMessage()} method).
     * @param cause       the cause (which is saved for later retrieval by the
     *                    {@link #getCause()} method).  (A <tt>null</tt> value is
     *                    permitted, and indicates that the cause is nonexistent or
     *                    unknown.)
     * @since 1.4
     */
    public GsmpException(Throwable cause, String description) {
        super(description, cause);
        this.description = description;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "GsmpException{" +
                "resultCode='" + resultCode + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
