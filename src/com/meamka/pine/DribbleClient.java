package com.meamka.pine;

/**
 * Created by IntelliJ IDEA.
 * User: Amka
 * Date: 11.02.12
 * Time: 18:08
 * To change this template use File | Settings | File Templates.
 */

import org.json.*;
import com.loopj.android.http.*;


public class DribbleClient {

    RequestParams params;
    private int per_page;
    private int page;

    public int getPerPage() {
        return per_page;
    }

    public void setPerPage(int per_page) {
        this.per_page = per_page;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public DribbleClient() {
    }

    public void getPopularShots(AsyncHttpResponseHandler handler, int page) throws JSONException {
        DribbleApiClient.get("shots/popular", null, handler, page);
    }

    public void getEveryoneShots(AsyncHttpResponseHandler handler, int page) throws JSONException {
        DribbleApiClient.get("shots/everyone", null, handler, page);
    }

    public void getDebutsShots(AsyncHttpResponseHandler handler, int page) throws JSONException {
        DribbleApiClient.get("shots/debuts", null, handler, page);
    }

    public void getShotComments(int shotId, AsyncHttpResponseHandler handler, int page) throws JSONException {
        DribbleApiClient.get("shots/" + Integer.toString(shotId) + "/comments", null, handler, page);
    }
}
