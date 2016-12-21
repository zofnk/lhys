package com.lh16808.app.lhys.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts.Data;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 常用工具类
 * 
 * Description: 包括 1. 判断网络是否连接正常 2. 隐藏键盘 3. 显示EditText错误信息 4. 创建快捷方式 5. 删除快捷方式
 * 6. 图片切换特效 7. px,dip,sp的相互转换 8. 截屏
 * 
 * @author zhouyujing
 * @e-mail 1032668839@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年10月19日 下午2:29:34
 */
public class CommonUtils {
	/**
	 * toast显示
	 * 
	 * @param activity
	 * @param content
	 */
	public static void showToast(final Activity activity, final String content, final int duration) {
		activity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast toast = Toast.makeText(activity, content, duration);
				toast.show();
			}
		});
	}

	/**
	 * toast显示
	 * 
	 * @param activity
	 * @param resId
	 *            字符串id
	 */
	public static void showToast(final Activity activity, final int resId, final int duration) {
		activity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast toast = Toast.makeText(activity, resId, duration);
				toast.show();
			}
		});
	}

	/**
	 * 判断网络是否可用
	 * 
	 * @param context
	 * @return true可用 false 不可用
	 */
	public static boolean isNetConnectionAvailable(Context context) {
		try {
			ConnectivityManager connectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
			if (activeNetInfo != null && activeNetInfo.isAvailable()) {
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

	public static boolean is3g2g(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
			return true;
		}
		return false;
	}

	public static boolean isWifi(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
			return true;
		}
		return false;
	}

	/**
	 * 显示键盘
	 * 
	 * @param mContext
	 * @param v
	 */
	public static void showKeyboard(Context mContext, View v) {
		v.requestFocus();
		((InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(v, 0);
	}

	/**
	 * 隐藏键盘
	 * 
	 * @param a
	 */
	public static void hideKeyboard(final Activity a) {
		if (a == null || a.getCurrentFocus() == null)
			return;
		InputMethodManager inputManager = (InputMethodManager) a.getApplicationContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (inputManager != null) {
			inputManager.hideSoftInputFromWindow(a.getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	/**
	 * 打开软键盘
	 * 
	 * @param mEditText
	 *            输入框
	 * @param mContext
	 *            上下文
	 */
	public static void openKeybord(EditText mEditText, Context mContext) {
		InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
	}

	/**
	 * 关闭软键盘
	 * 
	 * @param mEditText
	 *            输入框
	 * @param mContext
	 *            上下文
	 */
	public static void closeKeybord(EditText mEditText, Context mContext) {
		InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

		imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
	}

	/**
	 * EidtText控件显示错误信息 参数以String传入
	 * 
	 * @param et
	 * @param error
	 *            字符串参数
	 * @param animation
	 */
	public static void showErrorByEditText(EditText et, String error, Animation animation) {
		et.requestFocus();
		SpannableString ss = new SpannableString(error);
		ss.setSpan(new ForegroundColorSpan(Color.BLACK), 0, error.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		et.setError(ss);
		et.startAnimation(animation);
	}

	/**
	 * EditText控件显示错误信息 参数以R.string.xxx传入
	 * 
	 * @param et
	 * @param resId
	 *            资源ID
	 * @param animation
	 */
	public static void showErrorByEditText(EditText et, int resId, Animation animation) {
		String error = et.getResources().getString(resId);
		et.requestFocus();

		SpannableString ss = new SpannableString(error);
		ss.setSpan(new ForegroundColorSpan(Color.BLACK), 0, error.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		et.setError(ss);
		et.startAnimation(animation);
	}

	/**
	 * 隐藏EditText控件错误信息
	 * 
	 * @param et
	 * @param animation
	 */
	public static void hideErrorByEditText(EditText et) {
		et.requestFocus();
		et.setError(null);
	}

	/**
	 * 图片切换特效
	 * 
	 * @param imageView
	 */
	public static void showImageChange(ImageView imageView) {
		AlphaAnimation animation = new AlphaAnimation(0.1f, 1.0f);
		animation.setDuration(1000);
		imageView.startAnimation(animation);
	}

	/**
	 * 判断是否手机号码(6-20位)
	 * 
	 * @param mobileNo
	 * @return
	 */
	public static boolean isMobileNoValid(String mobileNo) {
		// return mobileNo.startsWith("1") && mobileNo.length() >= 6 &&
		// mobileNo.length() <= 20;
		return mobileNo.startsWith("1") && mobileNo.length() == 11;
	}

	/**
	 * 正则匹配是不是身份证
	 * 
	 * @param idCard
	 * @return
	 */
	public static boolean isIdCard(String idCard) {
		String regExp1 = "d{15}$";
		String regExp2 = "d{18}|d{17}[0-9a-zA-Z]$";

		if (idCard.length() == 15) {
			Pattern p = Pattern.compile(regExp1);
			Matcher m = p.matcher(idCard);
			return m.find();
		} else if (idCard.length() == 18) {
			Pattern p = Pattern.compile(regExp2);
			Matcher m = p.matcher(idCard);

			return m.find();
		} else {
			return false;
		}
	}

	/**
	 * 正则匹配是不是密码（不能是纯数字、纯字母、6-16位）
	 * 
	 * @param passWord
	 * @return
	 */
	public static boolean isPassWord(String passWord) {
		String regExp = "^[0-9]*$|^[A-Za-z]+$";

		Pattern p = Pattern.compile(regExp);

		Matcher m = p.matcher(passWord);

		return m.find() || passWord.length() < 6 || passWord.length() > 16;
	}

	/**
	 * 正则匹配输入的为简体汉字、字母、数字
	 * 
	 * @param editText
	 * @return
	 */
	public static boolean isCh_En_Num(String editText) {
		String regExp = "[^a-zA-Z0-9\u4E00-\u9FA5]";

		Pattern p = Pattern.compile(regExp);

		Matcher m = p.matcher(editText);

		return !m.find();
	}

	/**
	 * 正则匹配输入的为字母、数字
	 * 
	 * @param editText
	 * @return
	 */
	public static boolean isEn_Num(String editText) {
		String regExp = "[^a-zA-Z0-9]";

		Pattern p = Pattern.compile(regExp);

		Matcher m = p.matcher(editText);

		return !m.find();
	}

	/**
	 * 正则匹配输入的为纯数字
	 * 
	 * @param editText
	 * @return
	 */
	public static boolean isAllNum(String editText) {
		String regExp = "[0-9]*";

		Pattern p = Pattern.compile(regExp);

		Matcher m = p.matcher(editText);

		return !m.find();
	}

	/**
	 * 正则匹配输入的为纯中文
	 * 
	 * @param editText
	 * @return
	 */
	public static boolean isAllCn(String editText) {
		String regExp = "[\u4e00-\u9fa5]";

		Pattern p = Pattern.compile(regExp);

		Matcher m = p.matcher(editText);

		return !m.find();
	}

	/**
	 * 正则匹配输入的为纯字母
	 * 
	 * @param editText
	 * @return
	 */
	public static boolean isAllEn(String editText) {
		String regExp = "[a-zA-Z]";

		Pattern p = Pattern.compile(regExp);

		Matcher m = p.matcher(editText);

		return !m.find();
	}

	/**
	 * 正则匹配输入的为数字、字母组合
	 * 
	 * @param editText
	 * @return
	 */
	public static boolean isNum_En(String editText) {

		Pattern pat = Pattern.compile("[\\da-zA-Z]{6,20}");
		Pattern patno = Pattern.compile(".*\\d.*");
		Pattern paten = Pattern.compile(".*[a-zA-Z].*");
		Matcher mat = pat.matcher(editText);
		Matcher matno = patno.matcher(editText);
		Matcher maten = paten.matcher(editText);
		if (matno.matches() && maten.matches() && mat.matches()) {
			return true;
		}

		return false;
	}

	/**
	 * 判断是否存在快捷方式
	 * 
	 * @param ctx
	 * @return
	 */
	public static boolean hasShortcut(Context ctx, int app_name) {
		boolean isInstallShortcut = false;
		final ContentResolver cr = ctx.getContentResolver();
		final String AUTHORITY;
		// 在andriod 2.1即SDK7以上，是读取launcher.settings中的favorites表的数据；
		// 在andriod 2.2即SDK8以上，是读取launcher2.settings中的favorites表的数据。
		if (getSystemVersion() < 8) {
			AUTHORITY = "com.android.launcher.settings";
		} else {
			AUTHORITY = "com.android.launcher2.settings";
		}

		final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/favorites?notify=true");
		Cursor c = cr.query(CONTENT_URI, new String[] { "title", "iconResource" }, "title=?",
				new String[] { ctx.getString(app_name).trim() }, null);
		if (c != null && c.getCount() > 0) {
			isInstallShortcut = true;
		}
		return isInstallShortcut;
	}

	/**
	 * 添加快捷方式
	 * 
	 * @param
	 */
	public static void createShortcut(Context ctx, int app_name, int icon, Class<?> clazz) {
		Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
		// 快捷方式的名称
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, ctx.getString(app_name));
		shortcut.putExtra("duplicate", false); // 不允许重复创建
		// 指定当前的Activity为快捷方式启动的对象: 如 com.everest.video.VideoPlayer
		// 注意: ComponentName的第二个参数必须加上点号(.)，否则快捷方式无法启动相应程序
		// String appClass = act.getPackageName() + "." + launchActivity;
		// ComponentName comp = new ComponentName(act.getPackageName(), clazz);
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(Intent.ACTION_MAIN).setClass(ctx, clazz));
		// 快捷方式的图标
		ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(ctx, icon);
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);
		ctx.sendBroadcast(shortcut);
	}

	/**
	 * 删除快捷方式
	 * 
	 * @param act
	 */
	public static void removeShortcut(Context ctx, int app_name, Class<?> clazz) {
		Intent shortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
		// 快捷方式的名称
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, ctx.getString(app_name));
		// 指定当前的Activity为快捷方式启动的对象: 如 com.everest.video.VideoPlayer
		// 注意: ComponentName的第二个参数必须是完整的类名（包名+类名），否则无法删除快捷方式
		// String appClass = act.getPackageName() + ".WelcomeIndexActivity";
		// ComponentName comp = new ComponentName(act.getPackageName(),
		// appClass);
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(Intent.ACTION_MAIN).setClass(ctx, clazz));
		ctx.sendBroadcast(shortcut);
	}

	/**
	 * 返回系统SDK版本号
	 * 
	 * @return
	 */
	public static int getSystemVersion() {
		return android.os.Build.VERSION.SDK_INT;
	}

	/**
	 * 获取网络制式
	 * 
	 * @param context
	 * @return
	 */
	public static String getAccessType(Context context) {
		// 网络类型
		String access = "";
		ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connMgr.getActiveNetworkInfo();
		if (info != null) {
			int netType = info.getType();
			if (netType == ConnectivityManager.TYPE_WIFI) {
				access = "wifi";
			} else if (netType == ConnectivityManager.TYPE_MOBILE) {
				access = "2G/3G";
			}
		}

		return access;
	}

	/**
	 * 转换dip为px
	 * 
	 * @param context
	 * @param dip
	 *            值
	 * @return
	 */
	public static int convertDipToPx(Context context, double dip) {
		float scale = AppHelper.getScreenDensity(context);
		return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
	}

	/**
	 * 转换px为dip
	 * 
	 * @param context
	 * @param px
	 *            值
	 * @return
	 */
	public static int convertPxToDip(Context context, int px) {
		float scale = AppHelper.getScreenDensity(context);
		return (int) (px / scale + 0.5f * (px >= 0 ? 1 : -1));
	}

	/**
	 * 将PX转SP
	 * 
	 * @param context
	 * @param pxValue
	 *            px值
	 * @return
	 */
	public static int convertPxToSp(Context context, float pxValue) {
		float fontScale = AppHelper.getScaledDensity(context);
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 将SP转PX
	 * 
	 * @param context
	 * @param spValue
	 *            sp值
	 * @return
	 */
	public static int convertSpToPx(Context context, float spValue) {
		float fontScale = AppHelper.getScaledDensity(context);
		return (int) (spValue * fontScale + 0.5f);
	}

	/**
	 * 截屏(无状态栏)
	 * 
	 * @param activity
	 *            被截屏的activity
	 * @return 截下后图片的bitmap
	 */
	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	public static Bitmap shot(Activity activity) {
		Activity wisdowsActivity = activity;
		if (activity.getParent() != null) {
			wisdowsActivity = activity.getParent();
		}
		View view = wisdowsActivity.getWindow().getDecorView();
		Display display = wisdowsActivity.getWindowManager().getDefaultDisplay();
		int width = 0;
		int height = 0;
		if (CommonUtils.getSystemVersion() < 13) {
			width = display.getWidth();
			height = display.getHeight();
		} else {
			Point p = new Point();
			display.getSize(p);
			width = p.x;
			height = p.y;
		}

		view.layout(0, 0, width, height);
		view.setDrawingCacheEnabled(true);
		int statusBarHeight = AppHelper.getStatusBarHeight(activity);// 获取状态栏高度
		// 去除状态栏
		Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache(), 0, statusBarHeight, width,
				height - statusBarHeight);
		view.setDrawingCacheEnabled(false);
		return bitmap;
	}

	/**
	 * 保存图片路径
	 * 
	 * @param bitmap
	 * @param dir
	 * @param fileName
	 * @return
	 */
	public static File save(final Bitmap bitmap, String dir, String fileName) {
		if (bitmap == null)
			return null;

		String absolutePath = dir;
		AppLog.greenLog("", "absolutePath " + absolutePath);
		AppLog.greenLog("", "fileName " + fileName);

		File f = new File(absolutePath);
		if (!f.exists()) {
			if (!f.mkdirs()) {
				AppLog.redLog("", "mkdirs error:" + absolutePath);
			}
		}

		File mf = new File(absolutePath + "/" + fileName);
		OutputStream outputStream = null;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 85, out);
		byte[] jpegData = out.toByteArray();

		try {
			outputStream = new FileOutputStream(mf);
			outputStream.write(jpegData);
		} catch (FileNotFoundException e) {
			AppLog.redLog("", "FileNotFoundException error message:" + e.getMessage());
		} catch (IOException e) {
			AppLog.redLog("", "IOException error desc:" + e.getMessage());
		} finally {
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return mf;
	}

	/**
	 * 获取根路径
	 */
	public static String getSdcardDir() {
		return Environment.getExternalStorageDirectory().toString();
	}

	/**
	 * 打电话
	 * 
	 * @param context
	 * @param tel
	 *            电话号码
	 */
	public static void call(Context context, String tel) {
		// Intent intent = new Intent();
		// intent.setAction(Intent.ACTION_CALL);
		// intent.setData(Uri.parse("tel" + tel));// 传入一个URI Uri.parse(“tel:” +
		// 电话号码)
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tel));
		context.startActivity(intent);
	}

	/**
	 * 发短信
	 * 
	 * @param context
	 * @param tel
	 *            电话号码
	 * @param content
	 *            短信内容
	 */
	public static void sendMsg(Context context, String tel, String content) {
		// 发送短信息
		Uri uri = Uri.parse("smsto:" + tel);
		Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
		intent.putExtra("sms_body", content);
		context.startActivity(intent);
	}

	/**
	 * 保存通讯录
	 * 
	 * @param context
	 * @param name
	 * @param tel
	 * @param company
	 */
	public static void saveAdressList(Context context, String name, String tel, String company) {
		// 插入raw_contacts表，并获取_id属性
		Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
		ContentResolver resolver = context.getContentResolver();
		ContentValues values = new ContentValues();
		long contact_id = ContentUris.parseId(resolver.insert(uri, values));
		// 插入data表
		uri = Uri.parse("content://com.android.contacts/data");
		// add Name
		values.put("raw_contact_id", contact_id);
		values.put(Data.MIMETYPE, "vnd.android.cursor.item/name");
		values.put("data1", name);// 姓名
		resolver.insert(uri, values);
		values.clear();
		// add Phone
		values.put("raw_contact_id", contact_id);
		values.put(Data.MIMETYPE, "vnd.android.cursor.item/phone_v2");
		values.put("data1", tel); // 手机
		resolver.insert(uri, values);
		values.clear();
		// add email
		values.put("raw_contact_id", contact_id);
		values.put(Data.MIMETYPE, "vnd.android.cursor.item/organization");
		values.put("data1", company); // 单位
		resolver.insert(uri, values);
	}

	/**
	 * 判断是否获得读取权限
	 * 
	 * @return
	 */
	public static boolean getAuthority(Context context) {
		PackageManager pm = context.getPackageManager();
		boolean permission = (PackageManager.PERMISSION_GRANTED == pm
				.checkPermission("android.permission.READ_EXTERNAL_STORAGE", "com.xlb.joyhelper"));
		return permission;
	}

	/**
	 * 判断是否获得读取权限
	 * 
	 * @return
	 */
	public static boolean getCameraPermission(Context context) {
		PackageManager pm = context.getPackageManager();
		boolean permission = (PackageManager.PERMISSION_GRANTED == pm.checkPermission("android.permission.CAMERA",
				"com.xlb.joyhelper"));
		return permission;
	}

	/**
	 * 查找联系人，判断是否已经添加
	 * 
	 * @param context
	 * @return
	 */
	public static boolean getReadAllContacts(Context context, String phone) {
		Cursor cursor = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null,
				null);
		int contactIdIndex = 0;

		if (cursor.getCount() > 0) {
			contactIdIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID);
		}
		while (cursor.moveToNext()) {
			String contactId = cursor.getString(contactIdIndex);
			/*
			 * 查找该联系人的phone信息
			 */
			Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId, null, null);
			int phoneIndex = 0;
			if (phones.getCount() > 0) {
				phoneIndex = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
			}
			while (phones.moveToNext()) {
				String phoneNumber = phones.getString(phoneIndex);
				if (phone.equals(phoneNumber)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 键盘隐藏
	 * 
	 * @param mContext
	 */
	public static void hideSoftInputView(Context mContext) {
		InputMethodManager manager = ((InputMethodManager) mContext.getSystemService(Activity.INPUT_METHOD_SERVICE));
		if (((Activity) mContext).getWindow()
				.getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
			if (((Activity) mContext).getCurrentFocus() != null)
				manager.hideSoftInputFromWindow(((Activity) mContext).getCurrentFocus().getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	private static long lastClickTime;// 最后点击时间

	/**
	 * 防止重复点击
	 * 
	 * @return
	 */
	public static boolean isFastClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (0 < timeD && timeD < 800) {
			return true;
		}
		lastClickTime = time;
		return false;
	}

	/**
	 * 将value转换成int
	 * 
	 * @param value
	 * @param defaultValue
	 *            默认数据
	 * @return
	 */
	public int convertToInt(Object value, int defaultValue) {
		if (value == null || "".equals(value.toString().trim())) {
			return defaultValue;
		}
		try {
			return Integer.parseInt(value.toString().trim());
		} catch (Exception e) {
			try {
				return Double.valueOf(value.toString()).intValue();
			} catch (Exception e2) {
				return defaultValue;
			}
		}

	}

	/**
	 * 将value转换成Double
	 * 
	 * @param value
	 * @param defaultValue
	 *            默认数据
	 * @return
	 */
	public Double convertToDouble(Object value, Double defaultValue) {
		if (value == null || "".equals(value.toString().trim())) {
			return defaultValue;
		}
		try {
			return Double.parseDouble(value.toString().trim());
		} catch (Exception e) {
			return defaultValue;
		}

	}

	/**
	 * 将value转换成Long
	 * 
	 * @param value
	 * @param defaultValue
	 *            默认数据
	 * @return
	 */
	public long convertToLong(Object value, long defaultValue) {
		if (value == null || "".equals(value.toString().trim())) {
			return defaultValue;
		}
		try {
			return Long.parseLong(value.toString().trim());
		} catch (Exception e) {
			try {
				return Double.valueOf(value.toString()).longValue();
			} catch (Exception e2) {
				return defaultValue;
			}
		}

	}

	/**
	 * 将value转换成String
	 * 
	 * @param value
	 * @param defaultValue
	 *            默认数据
	 * @return
	 */
	public String convertToString(Object value, String defaultValue) {
		if (value == null || "".equals(value.toString().trim())) {
			return defaultValue;
		}
		return value.toString().trim();

	}
}
