package com.lh16808.app.lhys.widget;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.lh16808.app.lhys.R;


public class RemindDialogUtil
{
	private static Dialog remindDialog;
	private static TextView tv_content;
	private static Button btn_yes;
	private static Button btn_no;

	/**
	 * 显示提示框
	 */
	public static void showRemindDialog(Context context, String msg, int gravity, final DialogCallBack callBack)
	{
		if (remindDialog == null)
		{
			remindDialog = new Dialog(context, R.style.DialogThemeNoTitle);
			remindDialog.setContentView(R.layout.dialog_remind);
			tv_content = (TextView) remindDialog.findViewById(R.id.tv_dialog_remind_content);
			btn_yes = (Button) remindDialog.findViewById(R.id.btn_dialog_remind_yes);
			btn_no = (Button) remindDialog.findViewById(R.id.btn_dialog_remind_cancel);
		}
		tv_content.setGravity(gravity);
		tv_content.setText(msg);
		OnClickListener clickListener = new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				switch (v.getId())
				{
				case R.id.btn_dialog_remind_cancel:
					if (callBack != null)
					{
						callBack.clickCancel();
					}
					break;
				case R.id.btn_dialog_remind_yes:
					if (callBack != null)
					{
						callBack.clickYes();
					}
					break;

				default:
					break;
				}
			}
		};

		btn_yes.setOnClickListener(clickListener);
		btn_no.setOnClickListener(clickListener);
		remindDialog.setOnDismissListener(new OnDismissListener()
		{
			@Override
			public void onDismiss(DialogInterface dialog)
			{
				remindDialog = null;
			}
		});
		remindDialog.show();
	}
	
	public static void showRemindDialog(Context context, String msg, final DialogCallBack callBack)
	{
		showRemindDialog(context, msg, Gravity.CENTER, callBack);
	}

	/**
	 * 隐藏提示框
	 */
	public static void hideRemindDialog()
	{
		if (remindDialog != null)
		{
			remindDialog.dismiss();
		}
	}

	public interface DialogCallBack
	{
		/**
		 * 点击取消
		 */
		void clickCancel();
		/**
		 * 点击确定
		 */
		void clickYes();
	}


}
