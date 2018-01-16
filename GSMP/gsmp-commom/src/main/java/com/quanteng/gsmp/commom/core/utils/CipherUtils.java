/**
 * 文 件 名:  CipherUtils
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/9/11 0011
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.commom.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * <加密算法工具类>
 *
 * @author dyc
 * @version 2017/9/11 0011
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class CipherUtils {
    private static final char[] HEXES = {
            '0', '1', '2', '3',
            '4', '5', '6', '7',
            '8', '9', 'a', 'b',
            'c', 'd', 'e', 'f'
    };

    /**
     * 根据指定的算法加密任意长度的数据, 返回固定长度的十六进制小写哈希值
     *
     * @param data      需要加密的数据
     * @param algorithm 加密算法, 例如: MD5, SHA-1, SHA-256, SHA-512 等
     */
    public static String encrypt(byte[] data, String algorithm) throws Exception {
        // 1. 根据算法名称获实现了算法的加密实例
        MessageDigest digest = MessageDigest.getInstance(algorithm);

        // 2. 加密数据, 计算数据的哈希值
        byte[] cipher = digest.digest(data);

        // 3. 将结果转换为十六进制小写
        return bytes2Hex(cipher);
    }

    /**
     * 根据指定的算法加密文件数据, 返回固定长度的十六进制小写哈希值
     *
     * @param file      需要加密的文件
     * @param algorithm 加密算法, 例如: MD5, SHA-1, SHA-256, SHA-512 等
     */
    public static String encrypt(File file, String algorithm) throws Exception {
        InputStream in = null;

        try {
            // 1. 根据算法名称获实现了算法的加密实例
            MessageDigest digest = MessageDigest.getInstance(algorithm);

            in = new FileInputStream(file);
            byte[] buf = new byte[1024];
            int len = -1;
            while ((len = in.read(buf)) != -1) {
                // 2. 文件数据通常比较大, 使用 update() 方法逐步添加
                digest.update(buf, 0, len);
            }

            // 3. 计算数据的哈希值, 添加完数据后 digest() 方法只能被调用一次
            byte[] cipher = digest.digest();

            // 4. 将结果转换为十六进制小写
            return bytes2Hex(cipher);

        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    // nothing
                }
            }
        }
    }

    private static String bytes2Hex(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }

        StringBuilder sb = new StringBuilder();

        for (byte b : bytes) {
            sb.append(HEXES[(b >> 4) & 0x0F]);
            sb.append(HEXES[b & 0x0F]);
        }

        return sb.toString();
    }


}
