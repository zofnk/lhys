package com.lh16808.app.lhys.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.drawable.Drawable;

import com.lh16808.app.lhys.R;


public class DialogUtils {

	public static void showNotNetWork(final Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("网路设置").setIcon(R.mipmap.ic_launcher).setMessage("当前网络无连接！\n是否打开wifi设置?")
				.setPositiveButton("确定", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent();
						intent.setAction("android.net.wifi.PICK_WIFI_NETWORK");
						intent.putExtra("extra_prefs_show_button_bar", true);
						// intent.putExtra("extra_prefs_set_next_text", "完成");
						// intent.putExtra("extra_prefs_set_back_text", "返回");
						intent.putExtra("wifi_enable_next_on_connect", true);
						context.startActivity(intent);
					}
				}).setNegativeButton("取消", null).show();
	}
}
