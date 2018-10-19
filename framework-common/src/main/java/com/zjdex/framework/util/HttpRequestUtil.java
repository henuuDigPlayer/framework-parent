package com.zjdex.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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

    /**
     * 发送HttpPost请求
     *
     * @param url    服务地址
     * @param params Map<String, Object>对象
     * @return json字符串
     */
    public static String doPost(String url, Map<String, Object> params,
                                Map<String, String> headers) {
        String result = "";
        try {
            HttpURLConnection connection = getConnection(url, headers);
            result = writeResponse(connection, JsonUtil.objectToJson(params));
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 发送HttpPost请求
     *
     * @param url    服务地址
     * @param params String请求对象
     * @return json字符串
     */
    public static String doPost(String url, String params,
                                Map<String, String> headers) {
        String result = "";
        try {
            HttpURLConnection connection = getConnection(url, headers);
            result = writeResponse(connection, params);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static HttpURLConnection getConnection(String url, Map<String, String> headers) throws IOException {
        URL conUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) conUrl.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Content-Type", "application/json");
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
}
