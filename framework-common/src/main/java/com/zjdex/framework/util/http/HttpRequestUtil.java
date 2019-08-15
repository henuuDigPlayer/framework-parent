package com.zjdex.framework.util.http;

import com.zjdex.framework.util.constant.ConstantUtil;
import com.zjdex.framework.util.data.JsonUtil;
import com.zjdex.framework.util.data.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author lindj
 * @create 2018/9/11
 * @desc 功能描述
 **/
public class HttpRequestUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestUtil.class);
    private static final String JSON = "application/json";

    private static final String FORM_URLENCODED = "application/x-www-form-urlencoded";

    /**
     * 发送HttpPost请求 json
     *
     * @param url    服务地址
     * @param params String 请求参数
     * @return json字符串
     */
    public static String doPost(String url, String params,
                                Map<String, String> headers, Integer timeout) {
        LOGGER.info("url={}", url);
        LOGGER.info("headerParams={}", JsonUtil.objectToJson(headers));
        LOGGER.info("bodyParams={}", JsonUtil.objectToJson(params));
        String result = "";
        try {
            HttpURLConnection connection = getConnection(url, JSON, headers, timeout);
            result = writeResponse(connection, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("result={}", result);
        return result;
    }

    /**
     * 发送HttpPost请求  x-www-form-urlencoded
     *
     * @param url    String
     * @param params String
     * @return String
     */
    public static String doPost(String url, String params, Integer timeout) {
        String result = "";
        try {
            HttpURLConnection connection = getConnection(url, FORM_URLENCODED, null, timeout);
            result = writeResponse(connection, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取请求 connection
     *
     * @param url     请求地址
     * @param headers 请求头
     * @return
     * @throws IOException
     */
    public static HttpURLConnection getConnection(String url, String contentType, Map<String,
            String> headers, Integer timeout) throws IOException {
        URL conUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) conUrl.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Accept", contentType);
        connection.setRequestProperty("Content-Type", contentType);
        connection.setConnectTimeout(timeout);
        if (headers != null) {
            Set<Map.Entry<String, String>> sets = headers.entrySet();
            if (!StringUtil.isEmpty(sets)) {
                Iterator<Map.Entry<String, String>> iterator = sets.iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, String> entry = iterator.next();
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
        }
        return connection;
    }


    public static String writeResponse(HttpURLConnection connection, String requestContent) throws IOException {
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();

        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
        out.append(requestContent);
        out.flush();
        out.close();
        try {
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }

    /**
     * 获取当前网络ip
     *
     * @param request HttpServletRequest
     * @return String
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        Integer ipAddressLength = 15;
        String division = ",";
        String unknown = "unknown";

        String localhost = "127.0.0.1";

        String address = "0:0:0:0:0:0:0:1";
        if (ipAddress == null || ipAddress.length() == 0 || unknown.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || unknown.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || unknown.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals(localhost) || ipAddress.equals(address)) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > ipAddressLength) {
            if (ipAddress.indexOf(division) > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(division));
            }
        }
        return ipAddress;
    }

    /**
     * 获取post请求参数
     *
     * @param request HttpServletRequest
     * @return String
     */
    public static String getPostArgs(HttpServletRequest request) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder("");
        try {
            br = request.getReader();
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    /**
     * ip地址转成long型数字
     * 将IP地址转化成整数的方法如下：
     * 1、通过String的split方法按.分隔得到4个长度的数组
     * 2、通过左移位操作（<<）给每一段的数字加权，第一段的权为2的24次方，第二段的权为2的16次方，第三段的权为2的8次方，最后一段的权为1
     *
     * @param strIp String ip地址
     * @return long
     */
    public static long ipToLong(String strIp) {
        String[] ip = strIp.split("\\.");
        return (Long.parseLong(ip[0]) << 24) + (Long.parseLong(ip[1]) << 16) + (Long.parseLong(ip[2]) << 8) + Long.parseLong(ip[3]);
    }

    /**
     * 将十进制整数形式转换成127.0.0.1形式的ip地址
     * 将整数形式的IP地址转化成字符串的方法如下：
     * 1、将整数值进行右移位操作（>>>），右移24位，右移时高位补0，得到的数字即为第一段IP。
     * 2、通过与操作符（&）将整数值的高8位设为0，再右移16位，得到的数字即为第二段IP。
     * 3、通过与操作符把整数值的高16位设为0，再右移8位，得到的数字即为第三段IP。
     * 4、通过与操作符把整数值的高24位设为0，得到的数字即为第四段IP。
     *
     * @param longIp long
     * @return String
     */
    public static String longToIP(long longIp) {
        StringBuffer sb = new StringBuffer("");
        // 直接右移24位
        sb.append((longIp >>> 24));
        sb.append(".");
        // 将高8位置0，然后右移16位
        sb.append((longIp & 0x00FFFFFF) >>> 16);
        sb.append(".");
        // 将高16位置0，然后右移8位
        sb.append((longIp & 0x0000FFFF) >>> 8);
        sb.append(".");
        // 将高24位置0
        sb.append((longIp & 0x000000FF));
        return sb.toString();
    }
}
