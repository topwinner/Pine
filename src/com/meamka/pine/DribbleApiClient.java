package com.meamka.pine;

/**
 * Created by IntelliJ IDEA.
 * User: Amka
 * Date: 11.02.12
 * Time: 18:06
 * To change this template use File | Settings | File Templates.
 */

import com.loopj.android.http.*;


public class DribbleApiClient {
    private static final String BASE_URL = "http://api.dribbble.com/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler, int page) {
        client.get(getAbsoluteUrl(url, page), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler, int page) {
        client.post(getAbsoluteUrl(url, page), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl, int page) {
        return BASE_URL + relativeUrl + "?per_page=18&page=" + page;
    }
}
