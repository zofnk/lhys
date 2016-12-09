package com.lh16808.app.lhys.other;

import android.util.Xml;

import com.lh16808.app.lhys.model.Lottery;
import com.lh16808.app.lhys.utils.AppLog;
import com.lh16808.app.lhys.utils.http.H;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2016/11/14.
 */

public class KaiJianLoad {
    public Lottery lottery;
    OnNetData onNetData;

    public KaiJianLoad() {
        lottery = Lottery.getLottery();
    }

    public void setOnNetData(OnNetData onNetData) {
        this.onNetData = onNetData;
//        initXmlData();
        kj();
    }

    private void kj() {
        H.LOADKJ(new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    lottery.bq = jsonObject.getString("bq");
                    lottery.zt = jsonObject.getString("zt");
                    lottery.z1m = jsonObject.getString("z1m");
                    lottery.z1sx = jsonObject.getString("z1sx");
                    lottery.z2m = jsonObject.getString("z2m");
                    lottery.z2sx = jsonObject.getString("z2sx");
                    lottery.z3m = jsonObject.getString("z3m");
                    lottery.z3sx = jsonObject.getString("z3sx");
                    lottery.z4m = jsonObject.getString("z4m");
                    lottery.z4sx = jsonObject.getString("z4sx");
                    lottery.z5m = jsonObject.getString("z5m");
                    lottery.z5sx = jsonObject.getString("z5sx");
                    lottery.z6m = jsonObject.getString("z6m");
                    lottery.z6sx = jsonObject.getString("z6sx");
                    lottery.tm = jsonObject.getString("tm");
                    lottery.tmsx = jsonObject.getString("tmsx");
                    lottery.sxsj = jsonObject.getString("sxsj");
                    lottery.xyqsx = jsonObject.getString("xyqsj");
                    lottery.xq = jsonObject.getString("xq");
                    lottery.xyq = jsonObject.getString("xyq");
                    lottery.xyqsjc = jsonObject.getString("xyqsjc");
//                    lottery.setData(bq, zt, z1m, z1sx, z2m, z2sx, z3m, z3sx, z4m, z4sx, z5m, z5sx, z6m, z6sx, tm, tmsx, sxsj, xyqsj, xq, xyq);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AppLog.redLog("KJ", "" + lottery.toString());
//                time();
                onNetData.onScuss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                onNetData.onError();
            }
        });
    }

    public interface OnNetData {
        void onScuss();

        void onError();

        void onTime();
    }



    /*public void initXmlData() {
        H.initXmlData(new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String s = new String(responseBody);
                int eventType;
                try {
                    XmlPullParser parser = Xml.newPullParser();
                    parser.setInput(new StringReader(s));
                    eventType = parser.getEventType();
                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        if (eventType == XmlPullParser.START_TAG) {
                            String name = parser.getName();
                            if (name.equals("kjbm")) {
                                lottery.bq = parser.getAttributeValue("", "bq");
                                lottery.zt = parser.getAttributeValue("", "zt");
                                lottery.z1m = parser.getAttributeValue("", "z1m");
                                lottery.z1sx = parser.getAttributeValue("", "z1sx");
                                lottery.z2m = parser.getAttributeValue("", "z2m");
                                lottery.z2sx = parser.getAttributeValue("", "z2sx");
                                lottery.z3m = parser.getAttributeValue("", "z3m");
                                lottery.z3sx = parser.getAttributeValue("", "z3sx");
                                lottery.z4m = parser.getAttributeValue("", "z4m");
                                lottery.z4sx = parser.getAttributeValue("", "z4sx");
                                lottery.z5m = parser.getAttributeValue("", "z5m");
                                lottery.z5sx = parser.getAttributeValue("", "z5sx");
                                lottery.z6m = parser.getAttributeValue("", "z6m");
                                lottery.z6sx = parser.getAttributeValue("", "z6sx");
                                lottery.tm = parser.getAttributeValue("", "tm");
                                lottery.tmsx = parser.getAttributeValue("", "tmsx");
                                lottery.sxsj = parser.getAttributeValue("", "sxsj");
                                lottery.xyqsx = parser.getAttributeValue("", "xyqsj");
                                lottery.xq = parser.getAttributeValue("", "xq");
                                lottery.xyq = parser.getAttributeValue("", "xyq");
                            }
                        }
                        eventType = parser.next();
                    }
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                time();
                onNetData.onScuss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                onNetData.onError();
            }
        });
    }

    public void time() {
        H.Time(new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    Lottery.RefreshTime = new JSONObject(new String(responseBody)).getLong("time");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                onNetData.onTime();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                onNetData.onError();
            }
        });
    }*/
}
