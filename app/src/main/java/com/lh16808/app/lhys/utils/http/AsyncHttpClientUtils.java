package com.lh16808.app.lhys.utils.http;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import java.net.SocketTimeoutException;

import javax.net.ssl.SSLException;

/**
 * AsyncHttpClientUtils的单例
 *
 * @Author finddreams
 * @Address http://blog.csdn.net/finddreams
 * @Time 2016/3/14
 */
public class AsyncHttpClientUtils {
    public static final String TAG = AsyncHttpClientUtils.class.getSimpleName();
    public static final int SOCKET_TIMEOUT = 20 * 1000;//默认超时时间
    //    public static final int DEFAULT_SOCKET_TIMEOUT = 10 * 1000;
    private volatile static AsyncHttpClientUtils instance = new AsyncHttpClientUtils();
    // 实例话对象
    private static AsyncHttpClient client = new AsyncHttpClient();

    static {
//        client.setConnectTimeout(SOCKET_TIMEOUT);  //连接时间
//        client.setResponseTimeout(SOCKET_TIMEOUT); //响应时间
        client.setTimeout(SOCKET_TIMEOUT); // 设置连接超时，如果不设置，默认为10s
    }

    private PersistentCookieStore cookieStore;

    private AsyncHttpClientUtils() {

    }

    public static AsyncHttpClientUtils getInstance() {
        return instance;
    }

    public AsyncHttpClient getAsyncHttpClient() {
        return client;
    }

    /**
     * get方法带参数
     */
    public RequestHandle get(String url, RequestParams params,
                             AsyncHttpResponseHandler httpCallBack) {
        Log.i(TAG, client.getUrlWithQueryString(true, url, params));
        RequestHandle requestHandle = client.get(url, params, httpCallBack);
        return requestHandle;
    }
    /**
     * get方法bu带参数
     */
    public RequestHandle get(String url,
                             AsyncHttpResponseHandler httpCallBack) {
        RequestHandle requestHandle = client.get(url,httpCallBack);
        return requestHandle;
    }
    /**
     * post请求，带参数
     */
    public RequestHandle post(String url, RequestParams params,
                              AsyncHttpResponseHandler httpCallBack) {
        Log.i(TAG, client.getUrlWithQueryString(true, url, params));
        RequestHandle requestHandle = client.post(url, params, httpCallBack);
        return requestHandle;
    }

    /**
     * 设置Cookie
     *
     * @param context
     */
    public void setCookie(Context context) {
        cookieStore = new PersistentCookieStore(context);
        client.setCookieStore(cookieStore);
    }

    /**
     * 清楚Cookie
     */
    public void clearSession() {
        if (cookieStore != null) {
            cookieStore.clear();
        }
    }

    /**
     * 设置重试机制
     */
    public void setRetry() {
        client.setMaxRetriesAndTimeout(2, SOCKET_TIMEOUT);
        client.allowRetryExceptionClass(SocketTimeoutException.class);
        client.blockRetryExceptionClass(SSLException.class);
    }

    /**
     * 取消所有请求
     *
     * @param context
     */
    public void cancelAllRequests(Context context) {
        if (client != null) {
            Log.i(TAG, "cancel");
            client.cancelRequests(context, true); //取消请求
            client.cancelAllRequests(true);
        }
    }

    /*
     * 文件下载
     *
     * @param paramString
     * @param paramBinaryHttpResponseHandler
     */
    public void downFile(String paramString,
                         BinaryHttpResponseHandler paramBinaryHttpResponseHandler) {
        try {
            client.get(paramString, paramBinaryHttpResponseHandler);
            return;
        } catch (IllegalArgumentException localIllegalArgumentException) {
            Log.d("hhxh", "URL路径不正确！！");
        }
    }
}