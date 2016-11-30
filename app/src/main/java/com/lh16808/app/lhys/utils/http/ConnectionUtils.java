package com.lh16808.app.lhys.utils.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 是否有网络链接
 * 
 * @author Administrator
 *
 */

public class ConnectionUtils {
	public static boolean isWIFI(Context context) {
		if (isConnected(context)) {
			ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = cm.getActiveNetworkInfo();
			int type = info.getType();
			if (ConnectivityManager.TYPE_WIFI == type) {
				return true;
			}
		}
		return false;
	}

	public static boolean isMobile(Context context) {
		if (isConnected(context)) {
			ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = cm.getActiveNetworkInfo();
			int type = info.getType();
			if (ConnectivityManager.TYPE_MOBILE == type) {
				return true;
			}
		}
		return false;
	}

	public static boolean isConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if (info == null) {
			return false;
		}
		boolean available = info.isAvailable();
		return available;
	}

	public static boolean isConnect(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if (info == null) {
			return false;
		} else {
			// 获取NetworkInfo对象
			NetworkInfo[] networkInfo = cm.getAllNetworkInfo();
			if (networkInfo != null && networkInfo.length > 0) {
				for (int i = 0; i < networkInfo.length; i++) {
					if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
