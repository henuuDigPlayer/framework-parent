package com.boot.framework.util.http;

import com.boot.framework.util.constant.ConstantUtil;
import com.boot.framework.util.data.JsonUtil;
import com.boot.framework.util.data.StringUtil;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
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

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestUtil.class);

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
                                Map<String, String> headers) {
        logger.info("url={}", url);
        logger.info("headerParams={}", JsonUtil.objectToJson(headers));
        logger.info("bodyParams={}", JsonUtil.objectToJson(params));
        String result = "";
        try {
            HttpURLConnection connection = getConnection(url, JSON, headers);
            result = writeResponse(connection, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("result={}", result);
        return result;
    }

    /**
     * 发送HttpPost请求  x-www-form-urlencoded
     *
     * @param url    String
     * @param params String
     * @return String
     */
    public static String doPost(String url, String params) {
        String result = "";
        try {
            HttpURLConnection connection = getConnection(url, FORM_URLENCODED, null);
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
            String> headers) throws IOException {
        URL conUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) conUrl.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Accept", contentType);
        connection.setRequestProperty("Content-Type", contentType);
        connection.setConnectTimeout((int) ConstantUtil.CONNECTION_TIMEOUT);
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
     * @param request
     * @return
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
                //根据网卡取本机配置的IP
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


    private static String xml2JSON(byte[] xml) {
        InputStream is = new ByteArrayInputStream(xml);
        SAXBuilder sb = new SAXBuilder();
        org.jdom2.Document doc = null;
        try {
            doc = sb.build(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Element root = doc.getRootElement();
        return root.getValue();
    }

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
}
