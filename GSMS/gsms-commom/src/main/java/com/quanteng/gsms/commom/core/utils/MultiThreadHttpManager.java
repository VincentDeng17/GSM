/**
 * 文 件 名:  MultiThreadHttpManager
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/10/17 0017
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsms.commom.core.utils;


import org.apache.commons.logging.Log;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.DeflateDecompressingEntity;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.net.SocketTimeoutException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author dyc
 * @version 2017/10/17 0017
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class MultiThreadHttpManager {

    private static final Logger log = LoggerFactory.getLogger(MultiThreadHttpManager.class);

    private static MultiThreadHttpManager multiThreadHttp;

    private CloseableHttpClient httpClient;

    private PoolingHttpClientConnectionManager connectionManager;

    private MultiThreadHttpManager() {
        // 设置ConnectionConfig
        ConnectionConfig defaultConnectionConfig = ConnectionConfig.custom().setCharset(Consts.UTF_8).build();
        // 设置ConnectionManager
        connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(1000); // 最大请求数10000
        connectionManager.setDefaultMaxPerRoute(200); // 单个地址最大请求数2000
        connectionManager.setDefaultConnectionConfig(defaultConnectionConfig);
        // 生成HttpClient对象
        httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();

    }

    /**
     * 获取单例
     *
     * @return MultiThreadHttpManager
     */
    public static MultiThreadHttpManager getInstance() {
        if (multiThreadHttp == null) {
            multiThreadHttp = new MultiThreadHttpManager();
        }
        return multiThreadHttp;
    }

    /**
     * 向服务器Post XML报文
     *
     * @param url       请求地址
     * @param xml       XML
     * @param headers   请求头
     * @param msgLogger 消息日志对象
     * @return String 异常
     */
    public String postXMLDefault(String url, String xml, Map<String, String> headers, Log msgLogger) {
        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            // 设置请求参数,超时时间10秒
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30 * 1000)
                    .setConnectTimeout(30 * 1000).setConnectionRequestTimeout(30 * 1000).build();
            httpPost.setConfig(requestConfig);
            // 设置请求头
            if (headers != null) {
                Set<String> headerNames = headers.keySet();
                Iterator<String> its = headerNames.iterator();
                while (its.hasNext()) {
                    String headerName = its.next();
                    httpPost.addHeader(headerName, headers.get(headerName));
                }
            }
            // 设置请求体
            StringEntity xmlEntity = new StringEntity(xml, ContentType.create("text/xml", Consts.UTF_8));
            httpPost.setEntity(xmlEntity);

            // 记录请求报文日志
            LogManager.thirdMsgLog(this.getPrefixSeq() + ":" + xml, msgLogger);
            response = httpClient.execute(httpPost);
            log.debug("Response Code :" + response.getStatusLine().getStatusCode());
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    // byte[] bytes = EntityUtils.toByteArray(entity);
                    String responseXML = EntityUtils.toString(entity, "UTF-8");
                    LogManager.thirdMsgLog(this.getPrefixSeq() + ":" + responseXML, msgLogger);
                    return responseXML;
                }
            }
        } catch (Exception e) {
            log.error("MultiThreadHttpManager.postXML Error : " + e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (Exception e) {
                    log.error("Close Http Response Error:" + e);
                }

            }
        }
        return null;
    }

    /**
     * 向服务器Post XML报文
     *
     * @param url     请求地址
     * @param xml     XML
     * @param headers 请求头
     * @return String 异常
     */
    public String postXMLDefault(String url, String xml, Map<String, String> headers) {
        return this.postXMLDefault(url, xml, headers, null);
    }

    /**
     * 向服务器Post XML报文
     *
     * @param url        请求地址
     * @param xml        XML
     * @param contimeOut 连接超时时间
     * @param headers    请求头
     * @param msgLogger  消息日志对象
     * @return ActionResult<String>
     * ：如果失败则返回：result.code=-1,成功返回0,result.code=-2超时;result.
     * result是响应字符串,result.msg是描述
     */
    public ActionResult<String> postXML(String url, String xml, int contimeOut, Map<String, String> headers,
                                        Log msgLogger) {
        ActionResult<String> result = new ActionResult<String>();

        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            // 设置请求参数,超时时间20秒
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(contimeOut)
                    .setConnectTimeout(contimeOut).setConnectionRequestTimeout(30 * 1000).build();
            httpPost.setConfig(requestConfig);
            // 设置请求头
            if (headers != null) {
                Set<String> headerNames = headers.keySet();
                Iterator<String> its = headerNames.iterator();
                while (its.hasNext()) {
                    String headerName = its.next();
                    httpPost.addHeader(headerName, headers.get(headerName));
                }
            }
            // 设置请求体
            StringEntity xmlEntity = new StringEntity(xml, ContentType.create("text/xml", Consts.UTF_8));
            httpPost.setEntity(xmlEntity);

            // 记录请求报文日志
            LogManager.thirdMsgLog(this.getPrefixSeq() + ":" + xml, msgLogger);
            response = httpClient.execute(httpPost);
            log.debug("Response Code :" + response.getStatusLine().getStatusCode());
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    // byte[] bytes = EntityUtils.toByteArray(entity);
                    String responseXML = EntityUtils.toString(entity, "UTF-8");
                    LogManager.thirdMsgLog(this.getPrefixSeq() + ":" + responseXML, msgLogger);
                    result.setCode(0);
                    result.setMsg("statusCode:" + response.getStatusLine().getStatusCode());
                    result.setResult(responseXML);
                    return result;
                }
            }
        } catch (ConnectTimeoutException e) {
            log.error("Conn Timeout:", e);
            result.setCode(-2);
            result.setMsg("Conn Timeout");
            result.setResult(null);
            return result;
        } catch (SocketTimeoutException e) {
            log.error("Request Timeout:", e);
            result.setCode(-2);
            result.setMsg("Request Timeout");
            result.setResult(null);
            return result;
        } catch (Exception e) {
            log.error("MultiThreadHttpManager.postXML Error : " + e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (Exception e) {
                    log.error("Close Http Response Error:" + e);
                }

            }
        }
        result.setCode(-1);
        result.setMsg("处理失败");
        result.setResult(null);
        return result;
    }

    /**
     * 重载postXML方法
     *
     * @param url       请求地址
     * @param xml       请求XML
     * @param headers   请求头信息
     * @param msgLogger 日志对象
     * @return ActionResult
     */
    public ActionResult<String> postXML(String url, String xml, Map<String, String> headers, Log msgLogger) {
        return this.postXML(url, xml, 30 * 1000, headers, msgLogger);
    }

    /**
     * 重载postXML方法,默认超时时间和日志
     *
     * @param url     请求地址
     * @param xml     发送的XML报文
     * @param headers 请求头
     * @return ActionResult
     */
    public ActionResult<String> postXML(String url, String xml, Map<String, String> headers) {
        return this.postXML(url, xml, 30 * 1000, headers, null);
    }

    /**
     * 向服务器get XML报文
     *
     * @param url        请求地址
     * @param headers    请求头
     * @param contimeOut 请求超时时间
     * @param msgLogger  消息日志对象
     * @return ActionResult<String>
     * ：如果失败则返回：result.code=-1,成功返回0,result.code=-2超时;result.
     * result是响应字符串,result.msg是描述
     */
    public ActionResult<String> getXML(String url, Map<String, String> headers, int contimeOut, Log msgLogger) {
        ActionResult<String> result = new ActionResult<String>();
        CloseableHttpResponse response = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            // 设置请求参数,超时时间20秒
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(contimeOut)
                    .setConnectTimeout(contimeOut).setConnectionRequestTimeout(30 * 1000).build();
            httpGet.setConfig(requestConfig);
            // 设置请求头
            if (headers != null) {
                Set<String> headerNames = headers.keySet();
                Iterator<String> its = headerNames.iterator();
                while (its.hasNext()) {
                    String headerName = its.next();
                    httpGet.addHeader(headerName, headers.get(headerName));
                }
            }

            // 记录请求报文日志
            LogManager.thirdMsgLog(this.getPrefixSeq() + ":" + url, msgLogger);
            response = httpClient.execute(httpGet);
            log.debug("Response Code :" + response.getStatusLine().getStatusCode());
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    // byte[] bytes = EntityUtils.toByteArray(entity);
                    String responseXML = EntityUtils.toString(entity, "UTF-8");
                    LogManager.thirdMsgLog(this.getPrefixSeq() + ":" + responseXML, msgLogger);
                    result.setCode(0);
                    result.setMsg("statusCode:" + response.getStatusLine().getStatusCode());
                    result.setResult(responseXML);
                    return result;
                }
            }
        } catch (ConnectTimeoutException e) {
            log.error("Conn Timeout:", e);
            result.setCode(-2);
            result.setMsg("Conn Timeout");
            result.setResult(null);
            return result;
        } catch (SocketTimeoutException e) {
            log.error("Request Timeout:", e);
            result.setCode(-2);
            result.setMsg("Request Timeout");
            result.setResult(null);
            return result;
        } catch (Exception e) {
            log.error("MultiThreadHttpManager.getXML Error : " + e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (Exception e) {
                    log.error("Close Http Response Error:" + e);
                }

            }
        }
        result.setCode(-1);
        result.setMsg("处理失败");
        result.setResult(null);
        return result;
    }

    /**
     * 向服务器get XML报文 重载getXML方法,不需要传递log日志对象
     *
     * @param url     请求地址
     * @param headers 请求头
     * @return ActionResult
     */
    public ActionResult<String> getXML(String url, Map<String, String> headers) {
        return this.getXML(url, headers, 30 * 1000, null);
    }

    /**
     * 创建一个带HTTPS请求的CloseableHttpClient
     *
     * @return CloseableHttpClient
     */
    public CloseableHttpClient createHttpsClient() {

        SSLContext sslcontext = null;
        try {
            // 创建一下不带证书的SSL上下文
            sslcontext = SSLContexts.custom().loadTrustMaterial(null).build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 默认信任HTTPS请求的证书
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,
                SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        return HttpClients.custom().setSSLSocketFactory(sslsf).build();

    }

    /**
     * 向服务器Post XML报文(HTTPS)
     *
     * @param url       请求地址
     * @param xml       XML
     * @param headers   请求头
     * @param type      1 String 2 流
     * @param msgLogger 消息日志对象
     * @return ActionResult<Object>
     * ：如果失败则返回：result.code=-1,成功返回0,result.code=-2超时;result.
     * result是响应字符串,result.msg是描述
     */
    public ActionResult<Object> postXMLForHttps(String url, String xml, Map<String, String> headers, String type,
                                                Log msgLogger) {
        ActionResult<Object> result = new ActionResult<Object>();

        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            // 设置请求参数,超时时间20秒
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30 * 1000)
                    .setConnectTimeout(30 * 1000).setConnectionRequestTimeout(30 * 1000).build();
            httpPost.setConfig(requestConfig);
            // 设置请求头
            if (headers != null) {
                Set<String> headerNames = headers.keySet();
                Iterator<String> its = headerNames.iterator();
                while (its.hasNext()) {
                    String headerName = its.next();
                    httpPost.addHeader(headerName, headers.get(headerName));
                }
            }
            // 设置请求体
            StringEntity xmlEntity = new StringEntity(xml, ContentType.create("text/xml", Consts.UTF_8));
            httpPost.setEntity(xmlEntity);

            // 记录请求报文日志
            LogManager.thirdMsgLog(this.getPrefixSeq() + ":" + xml, msgLogger);
            response = createHttpsClient().execute(httpPost);
            log.debug("Response Code :" + response.getStatusLine().getStatusCode());
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    // 返回字符串
                    if ("1".equals(type)) {
                        String responseXML = EntityUtils.toString(entity, "UTF-8");
                        LogManager.thirdMsgLog(this.getPrefixSeq() + ":" + responseXML, msgLogger);
                        result.setCode(0);
                        result.setMsg("statusCode:" + response.getStatusLine().getStatusCode());
                        result.setResult(responseXML);
                    } else {
                        // 返回流
                        String responseXML = EntityUtils.toString(entity, "UTF-8");
                        LogManager.thirdMsgLog(this.getPrefixSeq() + ":" + responseXML, msgLogger);
                        result.setCode(0);
                        result.setMsg("statusCode:" + response.getStatusLine().getStatusCode());
                        result.setResult(entity.getContent());
                    }
                    return result;
                }
            }
        } catch (ConnectTimeoutException e) {
            log.error("Conn Timeout:", e);
            result.setCode(-2);
            result.setMsg("Conn Timeout");
            result.setResult(null);
            return result;
        } catch (SocketTimeoutException e) {
            log.error("Request Timeout:", e);
            result.setCode(-2);
            result.setMsg("Request Timeout");
            result.setResult(null);
            return result;
        } catch (Exception e) {
            log.error("MultiThreadHttpManager.postXML Error : " + e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (Exception e) {
                    log.error("Close Http Response Error:" + e);
                }

            }
        }
        result.setCode(-1);
        result.setMsg("处理失败");
        result.setResult(null);
        return result;
    }

    /**
     * 向服务器Post XML报文(HTTPS)
     *
     * @param url     请求地址
     * @param xml     XML
     * @param headers 请求头
     * @param type    1 String 2 流
     * @return ActionResult<Object>
     * ：如果失败则返回：result.code=-1,成功返回0,result.code=-2超时;result.
     * result是响应字符串,result.msg是描述
     */
    public ActionResult<Object> postXMLForHttps(String url, String xml, Map<String, String> headers, String type) {
        return this.postXMLForHttps(url, xml, headers, type, null);
    }

    /**
     * 获取日志前缀序列 用于方便关联所有日志
     *
     * @return
     */
    private String getPrefixSeq() {
        StringBuffer prefixBuf = new StringBuffer("");
        try {
            Thread current = Thread.currentThread();
            String seq = String.valueOf(current.getId());
            // 固定前缀
            prefixBuf.append('[');
            prefixBuf.append("Album-" + seq);
            prefixBuf.append(']');
        } catch (Exception e) {
            prefixBuf.append("[no prefix]");
        }
        return prefixBuf.toString();
    }

    // ==============================发送json报文==========

    /**
     * 发送JSON报文
     *
     * @tags @param url
     * @tags @param json
     * @tags @param headers
     * @tags @param msgLogger
     * @tags @return return String
     * @see [类、类#方法、类#成员]
     */
    public String postJSON(String url, String json, Map<String, String> headers, Log msgLogger) {
        log.info("[MultiThreadHttpManager][postJSON].Begin![url]:" + url + ",[json]:" + json + ",[headers]:" + headers
                + ",[msgLogger]:" + msgLogger);
        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            // 设置请求参数,超时时间10秒
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30 * 1000)
                    .setConnectTimeout(30 * 1000).setConnectionRequestTimeout(30 * 1000).build();
            httpPost.setConfig(requestConfig);
            // 设置请求头
            if (headers != null) {
                Set<String> headerNames = headers.keySet();
                Iterator<String> its = headerNames.iterator();
                while (its.hasNext()) {
                    String headerName = its.next();
                    httpPost.addHeader(headerName, headers.get(headerName));
                }
            }
            // 设置请求体
            StringEntity xmlEntity = new StringEntity(json, ContentType.create("application/json", Consts.UTF_8));
            httpPost.setEntity(xmlEntity);
            // 记录请求报文日志
            LogManager.thirdMsgLog(this.getPrefixSeq() + ":" + json, msgLogger);
            response = httpClient.execute(httpPost);
            log.debug("Response Code :" + response.getStatusLine().getStatusCode());
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    // byte[] bytes = EntityUtils.toByteArray(entity);
                    String responseJSON = EntityUtils.toString(entity, "UTF-8");
                    LogManager.thirdMsgLog(this.getPrefixSeq() + ":" + responseJSON, msgLogger);
                    log.info("[MultiThreadHttpManager][postJSON].End![responseJSON]:" + responseJSON);
                    return responseJSON;
                }
            }
        } catch (Exception e) {
            log.error("MultiThreadHttpManager.postJSON Error : " + e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (Exception e) {
                    log.error("Close Http Response Error:" + e);
                }

            }
        }
        return null;
    }

    /**
     * 发送JSON报文
     *
     * @tags @param url
     * @tags @param json
     * @tags @param headers
     * @tags @return return String
     * @see [类、类#方法、类#成员]
     */
    public String postJSON(String url, String json, Map<String, String> headers) {
        log.info(
                "[MultiThreadHttpManager][postJSON].Begin![url]:" + url + ",[json]:" + json + ",[headers]:" + headers);
        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            // 设置请求参数,超时时间10秒
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30 * 1000)
                    .setConnectTimeout(30 * 1000).setConnectionRequestTimeout(30 * 1000).build();
            httpPost.setConfig(requestConfig);
            // 设置请求头
            if (headers != null) {
                Set<String> headerNames = headers.keySet();
                Iterator<String> its = headerNames.iterator();
                while (its.hasNext()) {
                    String headerName = its.next();
                    httpPost.addHeader(headerName, headers.get(headerName));
                }
            }
            // 设置请求体
            StringEntity xmlEntity = new StringEntity(json, ContentType.create("application/json", Consts.UTF_8));
            httpPost.setEntity(xmlEntity);

            // 记录请求报文日志
            LogManager.thirdMsgLog(this.getPrefixSeq() + ":" + json, null);

            response = httpClient.execute(httpPost);

            log.debug("Response Code :" + response.getStatusLine().getStatusCode());
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                HttpEntity entity = response.getEntity();
                if (entity.getContentEncoding() != null) {
                    if ("gzip".equalsIgnoreCase(entity.getContentEncoding().getValue())) {
                        entity = new GzipDecompressingEntity(entity);
                    } else if ("deflate".equalsIgnoreCase(entity.getContentEncoding().getValue())) {
                        entity = new DeflateDecompressingEntity(entity);
                    }
                }
                if (entity != null) {
                    String responseJSON = EntityUtils.toString(entity, "UTF-8");
                    LogManager.thirdMsgLog(this.getPrefixSeq() + ":" + responseJSON, null);

                    log.info("[MultiThreadHttpManager][postJSON].End![responseJSON]:" + responseJSON);
                    return responseJSON;
                }
            }
        } catch (Exception e) {
            log.error("MultiThreadHttpManager.postJSON Error : " + e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (Exception e) {
                    log.error("Close Http Response Error:" + e);
                }

            }
        }
        return null;
    }

    public String getJSON(String url, Map<String, String> headers, int contimeOut, Log msgLogger) {
        log.info("[MultiThreadHttpManager][getJSON].Begin![url]:" + url + ",[contimeOut]:" + contimeOut + ",[headers]:" + headers
                + ",[msgLogger]:" + msgLogger);
        String defualtReturn = "{\"code\":900,\"message\":\"No Response!\"}";
        CloseableHttpResponse response = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            // 设置请求参数,超时时间20秒
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(contimeOut)
                    .setConnectTimeout(contimeOut).setConnectionRequestTimeout(30 * 1000).build();
            httpGet.setConfig(requestConfig);
            // 设置请求头
            if (headers != null) {
                Set<String> headerNames = headers.keySet();
                Iterator<String> its = headerNames.iterator();
                while (its.hasNext()) {
                    String headerName = its.next();
                    httpGet.addHeader(headerName, headers.get(headerName));
                }
            }

            // 记录请求报文日志
            LogManager.thirdMsgLog(this.getPrefixSeq() + ":" + url, msgLogger);
            int resendNum = 0;
            int responsStatus = 0;
            do {
                response = httpClient.execute(httpGet);
                responsStatus = response.getStatusLine().getStatusCode();
                log.debug("[resendNum]:"+resendNum+" ,[Response Code] :" + responsStatus +"[sendUrl]:"+url);
                if (HttpStatus.SC_OK == responsStatus) {
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        String responseJSON = EntityUtils.toString(entity, "UTF-8");
                        LogManager.thirdMsgLog(this.getPrefixSeq() + ":" + responseJSON, msgLogger);
                        return responseJSON;
                    }
                }
                resendNum += 1;
            } while (responsStatus != 200 && resendNum <= 3);

        } catch (ConnectTimeoutException e) {
            log.error("Conn Timeout:", e);
            return defualtReturn;
        } catch (SocketTimeoutException e) {
            log.error("Request Timeout:", e);
            return defualtReturn;
        } catch (Exception e) {
            log.error("MultiThreadHttpManager.getJSON Error : " + e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (Exception e) {
                    log.error("Close Http Response Error:" + e);
                }

            }
        }
        return defualtReturn;
    }

}
