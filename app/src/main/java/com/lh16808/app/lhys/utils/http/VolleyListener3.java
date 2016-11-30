package com.lh16808.app.lhys.utils.http;

import com.android.volley.Response;

import org.xmlpull.v1.XmlPullParser;

public interface VolleyListener3 extends Response.ErrorListener {
	void onResponse(XmlPullParser response);
}
