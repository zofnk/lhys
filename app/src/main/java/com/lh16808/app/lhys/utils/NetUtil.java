package com.lh16808.app.lhys.utils;

import com.lh16808.app.lhys.model.BannerModel;
import com.lh16808.app.lhys.utils.http.AsyncHttpClientUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by admin on 2016/10/19.
 */
public class NetUtil {

    public interface LoadMainADCallBack {
        void onSuccess(ArrayList<BannerModel> bannerUrl);

        void onFailure();
    }
    private static int num = 0;
    public static void loadMainAd(final String url, final LoadMainADCallBack mCallBack) {
        AsyncHttpClientUtils.getInstance().get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                num = 3;
                String response = new String(responseBody);
                ArrayList<BannerModel> listimg = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String title = jsonObject.getString("title");
                        String titlepic = jsonObject.getString("titlepic");
                        BannerModel bannerUrl = new BannerModel();
                        bannerUrl.setTitle(title);
                        bannerUrl.setTitlepic(titlepic);
                        listimg.add(bannerUrl);
                    }
                    mCallBack.onSuccess(listimg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                num++;
                if (num != 3)
                    loadMainAd( url, mCallBack);
            }
        });
    }
}
