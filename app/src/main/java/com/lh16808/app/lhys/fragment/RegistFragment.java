package com.lh16808.app.lhys.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lh16808.app.lhys.R;
import com.lh16808.app.lhys.base.BaseFragment;
import com.lh16808.app.lhys.engine.OnTouchAnim;
import com.lh16808.app.lhys.other.MyProgressDialog;
import com.lh16808.app.lhys.utils.FragmentUtils;
import com.lh16808.app.lhys.utils.RegexValidateUtil;
import com.lh16808.app.lhys.utils.ToastUtil;
import com.lh16808.app.lhys.utils.http.H;
import com.lh16808.app.lhys.widget.MyNumberPicker;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistFragment extends BaseFragment implements View.OnClickListener {


    public static RegistFragment newInstance() {

        Bundle args = new Bundle();

        RegistFragment fragment = new RegistFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public RegistFragment() {
    }

    private boolean isScrolling;
    //    private TextView tvArea;
    private EditText etName;
    private EditText etPassword;
    private PopupWindow expressPop;
    private MyNumberPicker np_pop_express;
    private EditText etPhone;
    private EditText etEmail;
    private ImageView ivEye;

    private void register(String email, String phone, String idCode, String password) {
        boolean setVali = setVali(email, phone, idCode, password);
        if (!setVali) {
            return;
        }
        MyProgressDialog.dialogShow(getActivity());
        RequestParams params = new RequestParams();
        params.put("enews", "register");
        params.put("username", idCode);
        params.put("password", password);
        params.put("repassword", password);
        if (!TextUtils.isEmpty(phone))
            params.put("phone", phone);
        params.put("email", email);
        H.USER(params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                MyProgressDialog.dialogHide();
                String arg0 = new String(responseBody);
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(arg0);
                    int zt = jsonObject.getInt("zt");
                    String ts = jsonObject.getString("ts");
                    ToastUtil.toastShow(getActivity(), ts + "");
                    if (zt == 1) {
                        ToastUtil.toastShow(getActivity(), "注册成功！返回登录");
                        FragmentUtils.backStack(getActivity());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                MyProgressDialog.dialogHide();
                ToastUtil.toastShow(getActivity(), "網路錯誤~");
            }
        });
    }

    private boolean setVali(String email, String phone, String idCode, String password) {
        if (TextUtils.isEmpty(email)) {
            ToastUtil.toastShow(getActivity(), "邮箱不能为空！");
            return false;
        }
        if (TextUtils.isEmpty(idCode)) {
            ToastUtil.toastShow(getActivity(), "帐号不能为空！");
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtil.toastShow(getActivity(), "密码不能为空！");
            return false;
        }
        if (!RegexValidateUtil.checkPassWork(password)) {
            ToastUtil.toastShow(getActivity(), "请输入正确的密码！");
            return false;
        }
        if (!TextUtils.isEmpty(phone)) {
            if (!RegexValidateUtil.checkMobileNumber(phone)) {
                ToastUtil.toastShow(getActivity(), "请输入正确的手机号码！");
                return false;
            }
        }
        if (!RegexValidateUtil.checkEmail(email)) {
            ToastUtil.toastShow(getActivity(), "请输入正确的邮箱格式！");
            return false;
        }
        return true;

    }

    private void hideSoftInput() {
        View view = getActivity().getWindow().peekDecorView();
        if (view == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rltyBack:
                FragmentUtils.backStack(getActivity());
                break;
            case R.id.iv_eye:
                int type = etPassword.getInputType();
                if (type == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ivEye.setImageResource(R.drawable.eyes_close);
                } else {
                    etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ivEye.setImageResource(R.drawable.eyes_open);
                }
                break;
        }
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected View initViews(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootview = inflater.inflate(R.layout.frag_regist, container);
        mRootview.findViewById(R.id.rltyBack).setOnClickListener(this);

        TextView tvToProtocol = (TextView) mRootview.findViewById(R.id.tv_to_protocol);
        tvToProtocol.setOnClickListener(this);

        etEmail = (EditText) mRootview.findViewById(R.id.et_email);
        etPhone = (EditText) mRootview.findViewById(R.id.et_phone);
        etName = (EditText) mRootview.findViewById(R.id.et_name);
        etPassword = (EditText) mRootview.findViewById(R.id.et_password);

        Button btnRegister = (Button) mRootview.findViewById(R.id.btn_register);
        btnRegister.setOnTouchListener(new OnTouchAnim());
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String phone = etPhone.getText().toString();
                String idCode = etName.getText().toString();
                String password = etPassword.getText().toString();
                RegistFragment.this.register(email, phone, idCode, password);
            }
        });

        mRootview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideSoftInput();
                return false;
            }
        });
        ivEye = (ImageView) mRootview.findViewById(R.id.iv_eye);
        ivEye.setOnClickListener(this);
        return mRootview;
    }

    @Override
    public void loadData() {

    }
}
