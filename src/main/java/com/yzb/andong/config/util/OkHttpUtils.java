package com.yzb.andong.config.util;

import okhttp3.*;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 网路访问
 *
 * @author wangban
 * @date 11:59 2018/9/10
 */
public class OkHttpUtils {

    private static OkHttpClient mOkHttpClient;


    /**
     * 网络访问连接池
     */
    private static ConnectionPool pool;

    /**
     * get 请求
     *
     * @param url 请求地址
     * @return 返回结果
     */
    public static String getRequest(String url) {
        try {

            Request.Builder builder = new Request.Builder();
            builder.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36");
            builder.url(url);
            final Request request = builder.build();
            //new call
            Call call = getOkHttpInstance().newCall(request);
            //请求加入调度
            Response response = call.execute();
            return response.body().string();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return "";
        }
    }

    /**
     * get 请求
     *
     * @param url 请求地址
     * @return 返回结果
     */
    public static String getRequest(String url, String appKey) {
        try {

            Request.Builder builder = new Request.Builder();
            if (!StringUtils.isEmpty(appKey)) {
                builder.addHeader("Authorization", "APPCODE" + appKey);
            }
            builder.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36");
            builder.url(url);
            final Request request = builder.build();
            //new call
            Call call = getOkHttpInstance().newCall(request);
            //请求加入调度
            Response response = call.execute();
            return response.body().string();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return "";
        }
    }

    private static OkHttpClient getOkHttpInstance() {
        if (mOkHttpClient == null) {
            synchronized (OkHttpUtils.class) {
                //创建okHttpClient对象
                mOkHttpClient = new OkHttpClient();
                //创建一个Request
                OkHttpClient.Builder b = new OkHttpClient.Builder();
                b.connectionPool(getConnectionPool());
            }
        }
        return mOkHttpClient;
    }

    /**
     * 获取网络访问连接池
     *
     * @return
     */
    private static ConnectionPool getConnectionPool() {
        if (pool == null) {
            synchronized (OkHttpUtils.class) {
                pool = new ConnectionPool(5, 10, TimeUnit.MINUTES);
            }
        }
        return pool;
    }


    /**
     * get 请求 含代理
     *
     * @param url 请求地址
     * @return 返回结果
     */
    public static String getRequestByProxy(String url, String host, int port) {
        try {
            //创建okHttpClient对象
            OkHttpClient.Builder b = new OkHttpClient.Builder();
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));
            b.proxy(proxy);
            //创建一个Request
            Request.Builder builder = new Request.Builder();
            builder.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36");
            builder.url(url);
            final Request request = builder.build();
            //new call
            Call call = getOkHttpInstance().newCall(request);
            //请求加入调度
            Response response = call.execute();
            return response.body().string();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return "";
        }
    }

    /**
     * 一般post请求
     *
     * @param url    请求路径
     * @param params 请求参数
     * @param token  消息头
     * @return 返回结果
     */
    public static String postRequest(String url, Map<String, String> params, String token) {
        try {

            FormBody.Builder formBodyBuilder = new FormBody.Builder();
            Set<String> keySet = params.keySet();
            for (String key : keySet) {
                String value = params.get(key);
                formBodyBuilder.add(key, value);
            }
            FormBody formBody = formBodyBuilder.build();

            Request.Builder builder = new Request.Builder();
            if (!StringUtils.isEmpty(token)) {
                builder.addHeader("Authorization", token);
            }
            Request request = builder.url(url)
                    .post(formBody).build();

            Call call = getOkHttpInstance().newCall(request);

            Response response = call.execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * post传json参数
     *
     * @param url  请求路径
     * @param json 请求的json数据
     * @return 返回结果
     */
    public static String postJson(String url, String json) {
        try {

            //MediaType  设置Content-Type 标头中包含的媒体类型值
            RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                    , json);

            Request request = new Request.Builder()
                    //请求的url
                    .url(url)
                    .post(requestBody)
                    .build();

            //创建/Call
            Call call = getOkHttpInstance().newCall(request);
            Response response = call.execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

}
