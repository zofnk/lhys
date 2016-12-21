package com.lh16808.app.lhys.other;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.lh16808.app.lhys.R;
import com.lh16808.app.lhys.utils.CommonUtils;
import com.lh16808.app.lhys.utils.FileUtils;
import com.lh16808.app.lhys.utils.ImageLoader;
import com.lh16808.app.lhys.utils.RequestPermissions;
import com.lh16808.app.lhys.utils.SharedPreUtils;
import com.lh16808.app.lhys.utils.http.H;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class MyDialog extends DialogFragment implements OnClickListener {
    private ImageView qrImgImageView;
    private View view;
    private Activity context;
    String contentString = "";
    String url = "http://www.6happ.com";
    String text = "六合大师是壹款集視頻開獎直播、數據分析、資料分享的六合手機應用";
    private UMImage image;
    Handler handler = new Handler();
    private Bitmap bitmap;

    @SuppressLint("ValidFragment")
    public MyDialog() {
        super();
    }

    @SuppressLint("ValidFragment")
    public MyDialog(Activity context) {
        super();
        this.context = context;
        image = new UMImage(context, R.mipmap.ic_launcher);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RequestPermissions.writeExternalStorage(context, new RequestPermissions.PermissionCallBack() {
            @Override
            public void setOnPermissionListener(Boolean bo) {
                if (bo) {

                } else {
                    RequestPermissions.openPre(context);
                }
            }
        }, 3);
        getDialog().requestWindowFeature(STYLE_NO_TITLE);
        view = inflater.inflate(R.layout.invite_friend, container, false);
        initUI();
        String qrcode = SharedPreUtils.getString("qrcode", "");
        String sharetext = SharedPreUtils.getString("sharetext", "");
        String sharelink = SharedPreUtils.getString("sharelink", "");
        if (!qrcode.isEmpty()) {
            contentString = qrcode;
            image = new UMImage(context, qrcode);
            qrCode();
        }
        if (!sharetext.isEmpty()) {
            text = sharetext;
        }
        if (!sharelink.isEmpty()) {
            url = sharelink;
        }
        if (qrcode.isEmpty() || sharetext.isEmpty() || sharelink.isEmpty()) {
            initData();
        }
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(context).onActivityResult(requestCode, resultCode, data);
    }

    private void initData() {
        H.UPGRUADE(new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String qrcode = jsonObject.getString("qrcode");
                    String sharetext = jsonObject.getString("sharetext");
                    String sharelink = jsonObject.getString("sharelink");
                    if (qrcode != null && !qrcode.isEmpty()) {
                        SharedPreUtils.putString("qrcode", qrcode);
                        image = new UMImage(context, qrcode);
                        contentString = qrcode;
                        qrCode();
                    }
                    if (sharetext != null && !sharetext.isEmpty()) {
                        SharedPreUtils.putString("sharetext", sharetext);
                        text = sharetext;
                    }
                    if (sharelink != null && !sharelink.isEmpty()) {
                        SharedPreUtils.putString("sharelink", sharelink);
                        url = sharelink;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void initUI() {
        view.findViewById(R.id.dialog_liner_weixin).setOnClickListener(this);
        view.findViewById(R.id.dialog_liner_dx).setOnClickListener(this);
        view.findViewById(R.id.dialog_liner_pengyouquan).setOnClickListener(this);
        view.findViewById(R.id.dialog_liner_qq).setOnClickListener(this);
        qrImgImageView = (ImageView) view.findViewById(R.id.inviteTwoCode);
        qrImgImageView.setOnClickListener(this);
    }


    private void qrCode() {
        if (context != null) {
            if (contentString != null && contentString.trim().length() > 0) {
                if (CommonUtils.isNetConnectionAvailable(context)) {
                    ImageLoader.LoaderNet(context, contentString, qrImgImageView);
                }
            }
        }
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(context, platform + " 分享成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Log.e("ssss", "ssdfs", t);
            Toast.makeText(context, platform + " 分享失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(context, platform + " 取消分享", Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_liner_weixin:
                new ShareAction(context).setPlatform(SHARE_MEDIA.WEIXIN)
                        .withTitle(getResources().getString(R.string.app_name))
                        .withText(text)
                        .withTargetUrl(url).withMedia(image)
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.dialog_liner_pengyouquan:   System.out.println("sssss2");
                new ShareAction(context).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .withTitle(getResources().getString(R.string.app_name))
                        .withText(text).withTargetUrl(url).withMedia(image)
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.dialog_liner_qq:
                if (image == null)
                    return;
                new ShareAction(context).setPlatform(SHARE_MEDIA.QQ)
                        .withTitle(getResources().getString(R.string.app_name))
                        .withText(text).withTargetUrl(url).withMedia(image)
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.dialog_liner_dx:
                new ShareAction(context).setPlatform(SHARE_MEDIA.QZONE)
                        .withTitle(getResources().getString(R.string.app_name))
                        .withText(text).withTargetUrl(url).withMedia(image)
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.inviteTwoCode:
                RequestPermissions.writeExternalStorage(context, new RequestPermissions.PermissionCallBack() {
                    @Override
                    public void setOnPermissionListener(Boolean bo) {
                        if (bo) {
                            saveFile();
                        } else {
                            RequestPermissions.openPre(context);
                        }
                    }
                }, 3);
                break;
            default:
                break;
        }
    }

    public void saveFile() {
        long currentTimeMillis = System.currentTimeMillis();
        long long1 = SharedPreUtils.getLong("currentTimeMillis", 0);
        long time = currentTimeMillis - long1;
        if (time < 60000) {
            Toast.makeText(context, "請" + (60 - time / 1000) + "秒後在試！！！", Toast.LENGTH_SHORT).show();
            return;
        }
//                MyProgressDialog.dialogShow(context);
        this.dismiss();
        if (contentString != null && !contentString.isEmpty()) {
            Drawable drawable = qrImgImageView.getDrawable();
            final Bitmap bitmap = drawableToBitamp(drawable);
            if (bitmap != null) {
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        if (context != null) {
                            long currentTimeMillis = System.currentTimeMillis();
                            SharedPreUtils.putLong("currentTimeMillis", currentTimeMillis);
                            FileUtils.saveBitmap(bitmap, "qrcode.png");
                            Toast.makeText(context, "二維碼保存成功！！！", Toast.LENGTH_SHORT).show();
                        }
//                                MyProgressDialog.dialogHide();
                    }
                }, 1000);
            }
        }
    }
    private Bitmap drawableToBitamp(Drawable drawable) {
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        System.out.println("Drawable转Bitmap");
        Bitmap.Config config =
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565;
        bitmap = Bitmap.createBitmap(w, h, config);
        //注意，下面三行代码要用到，否在在View或者surfaceview里的canvas.drawBitmap会看不到图
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }
}
