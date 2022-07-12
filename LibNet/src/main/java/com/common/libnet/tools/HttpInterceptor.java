package com.common.libnet.tools;

import android.util.Log;
import com.blankj.utilcode.util.NetworkUtils;
import com.common.libnet.NetWorkManager;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * @Author: Sun
 * @CreateDate: 2022/7/5
 * @Description: java类作用描述
 */
public class HttpInterceptor implements Interceptor, NetworkUtils.OnNetworkStatusChangedListener {

    {
        NetworkUtils.registerNetworkStatusChangedListener(this);
    }

    private boolean isConnected = NetworkUtils.isConnected();
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private static String REQUEST_TAG = "请求";

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (!isConnected) {
            throw new HttpException(HttpConstants.NO_NET,"网络连接异常，请检查网络后重试");
        }
        Request request = chain.request();
        request = getHeaderRequest(request);
        request = getUrlRequest(request);
        logRequest(request);
        Response response = chain.proceed(request);
        /*if (!response.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();
            for (String header : response.headers("Set-Cookie")) {
                cookies.add(header);
            }
            Hawk.put(Constants.HawkCode.COOKIE, cookies);
        }*/
        logResponse(response);
        return response;
    }

    private Request getUrlRequest(Request request) {
        String token = NetWorkManager.getInstance().userToken();
        HttpUrl.Builder builder = request.url().newBuilder().addEncodedQueryParameter("token",token);
        return  request.newBuilder().url(builder.build()).build();
    }

    /**
     * 添加header
     */
    public Request getHeaderRequest(Request request) {
        Request headRequest = request
                .newBuilder()
                .addHeader("apiversion","10")
                .build();
        return headRequest;
    }

    /**
     * 打印请求信息
     *
     * @param request
     */
    private void logRequest(Request request) {
        Log.d(REQUEST_TAG + "method", request.method());
        Log.d(REQUEST_TAG + "url", request.url().toString());
        Log.d(REQUEST_TAG + "header", request.headers().toString());
        if (request.method().equals("GET")) {
            return;
        }
        try {
            RequestBody requestBody = request.body();
            String parameter = null;
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            parameter = buffer.readString(UTF8);
            buffer.flush();
            buffer.close();
            Log.d(REQUEST_TAG + "参数", parameter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 打印返回结果
     *
     * @param response
     */
    private void logResponse(Response response) {
        try {
            ResponseBody responseBody = response.body();
            String rBody = null;
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE);
            Buffer buffer = source.buffer();
            Charset charset = UTF8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(UTF8);
                } catch (UnsupportedCharsetException e) {
                    e.printStackTrace();
                }
            }
            rBody = buffer.clone().readString(charset);
            Log.d(REQUEST_TAG + "返回值", rBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisconnected() {
        isConnected = false;
    }

    @Override
    public void onConnected(NetworkUtils.NetworkType networkType) {
        isConnected = true;
    }
}

