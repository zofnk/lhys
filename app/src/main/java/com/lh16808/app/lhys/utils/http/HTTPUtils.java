package com.lh16808.app.lhys.utils.http;

/**
 * http请求
 * Copyright 2013 Ognyan Bankov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.xmlpull.v1.XmlPullParser;

import java.util.Map;

/**
 * Helper class that is used to provide references to initialized
 * RequestQueue(s) and ImageLoader(s)
 * 
 * @author Ognyan Bankov
 * 
 */
public class HTTPUtils {
	private static RequestQueue mRequestQueue;

	private HTTPUtils() {
	}

	private static void init(Context context) {
		mRequestQueue = Volley.newRequestQueue(context);

	}

	public static void post(Context context, String url, final Map<String, String> params,
							final VolleyListener listener) {

		StringRequest myReq = new UTFStringRequest(Method.POST, url, new Listener<String>() {
			public void onResponse(String response) {
				listener.onResponse(response);
			}
		}, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				listener.onErrorResponse(error);
			}
		}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {

				return params;
			}
		};
		if (mRequestQueue == null) {
			init(context);
		}

		// 请用缓存
		myReq.setShouldCache(true);
		// 设置缓存时间10分钟
		// myReq.setCacheTime(10*60);
		mRequestQueue.add(myReq);
	}

	public static void get(Context context, String url, final VolleyListener listener) {
		// 判断当前如果没有网络，则返回null
		if (!ConnectionUtils.isConnected(context)) {
			VolleyError error = new VolleyError("no connection");
			listener.onErrorResponse(error);
			return;
		}

		StringRequest myReq = new UTFStringRequest(Method.GET, url, new Listener<String>() {
			public void onResponse(String response) {
				listener.onResponse(response);
			}
		}, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				listener.onErrorResponse(error);
			}
		});
		if (mRequestQueue == null) {
			init(context);
		}
		mRequestQueue.add(myReq);
	}

	public static RequestQueue getRequestQueue(Context context) {
		if (mRequestQueue != null) {
			return mRequestQueue;
		} else {
			throw new IllegalStateException("RequestQueue not initialized");
		}
	}

	public static void cancelAll(Context context) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(context);
		}
	}

	/**
	 * Returns instance of ImageLoader initialized with {@see FakeImageCache}
	 * which effectively means that no memory caching is used. This is useful
	 * for images that you know that will be show only once.
	 * 
	 * @return
	 */
	// public static ImageLoader getImageLoader() {
	// if (mImageLoader != null) {
	// return mImageLoader;
	// } else {
	// throw new IllegalStateException("ImageLoader not initialized");
	// }
	// }

	public static void loginPost(Context context, String url, final Map<String, String> params,
								 final VolleyListener2 listener) {

		StringRequest myReq = new UTFStringRequest(Method.POST, url, new Listener<String>() {
			public void onResponse(String response) {
				listener.onResponse(response);
			}
		}, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				listener.onErrorResponse(error);
			}
		}) {
			@Override
			protected Response<String> parseNetworkResponse(NetworkResponse response) {
				listener.parseNetworkResponse(response);
				return super.parseNetworkResponse(response);
			}

			@Override
			protected Map<String, String> getParams() throws AuthFailureError {

				return params;
			}
		};
		if (mRequestQueue == null) {
			init(context);
		}

		// 请用缓存
		myReq.setShouldCache(true);
		// 设置缓存时间10分钟
		// myReq.setCacheTime(10*60);
		mRequestQueue.add(myReq);
	}

	public static void postData(Context context, String url, final VolleyListener3 listener3) {
		XmlRequest xmlRequest = new XmlRequest(url, new Listener<XmlPullParser>() {
			@Override
			public void onResponse(XmlPullParser response) {
				listener3.onResponse(response);
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				listener3.onErrorResponse(error);
			}
		});
		if (mRequestQueue == null) {
			init(context);
		}
		mRequestQueue.add(xmlRequest);
	}
}
