package com.lh16808.app.lhys.utils.http;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Response.Listener;

public interface VolleyListener2 extends Response.ErrorListener, Listener<String> {

	void parseNetworkResponse(NetworkResponse response);

}
