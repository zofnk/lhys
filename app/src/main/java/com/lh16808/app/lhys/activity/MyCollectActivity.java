package com.lh16808.app.lhys.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.lh16808.app.lhys.R;
import com.lh16808.app.lhys.base.BaseActivity;
import com.lh16808.app.lhys.marco.ApiConfig;
import com.lh16808.app.lhys.marco.Constants;
import com.lh16808.app.lhys.model.CateModel;
import com.lh16808.app.lhys.model.CollectModel;
import com.lh16808.app.lhys.model.User;
import com.lh16808.app.lhys.other.MyProgressDialog;
import com.lh16808.app.lhys.utils.AppLog;
import com.lh16808.app.lhys.utils.ToastUtil;
import com.lh16808.app.lhys.utils.http.H;
import com.lh16808.app.lhys.widget.imagelook.ui.ImagePagerActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MyCollectActivity extends BaseActivity {

    private View mLLCollect;
    private SwipeMenuListView mSwipeMenuListView;
    private View mNotData;
    private User mUser;
    private int showType;
    private CollectAdapter mAdapter;
    String urlValue = "";
    ArrayList<CollectModel> listCollect = new ArrayList<>();
    private int type;
    private static final String KEY_TITLE_TYPE = "title";

    public static void start(Context context, int titleType) {
        Intent starter = new Intent(context, MyCollectActivity.class);
        starter.putExtra(KEY_TITLE_TYPE, titleType);
        context.startActivity(starter);
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_collect);
        mUser = User.getUser();

        mNotData = findViewById(R.id.tv_myCollect_neirong);
        mLLCollect = findViewById(R.id.ll_collect_list);

        mSwipeMenuListView = (SwipeMenuListView) findViewById(R.id.list_myCollect);
        final Intent intent = getIntent();
        showType = intent.getIntExtra("title", -1);
        if (showType == 0) {
            initData();
        }

        mAdapter = new CollectAdapter();
        mSwipeMenuListView.setAdapter(mAdapter);

        mSwipeMenuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CollectModel collectModel = listCollect.get(position);
                ToastUtil.toastShow(MyCollectActivity.this, collectModel.getClassid());
                int type = getType(collectModel);
                switch (type) {
                    case -1:
                        ToastUtil.toastShow(MyCollectActivity.this, "该收藏无法使用~");
                        break;
                    case 0:
                        //帖子
                        Intent intent = new Intent(MyCollectActivity.this, ForumDetailActivity.class);
                        intent.putExtra("forumModel", collectModel.getTid());
                        MyCollectActivity.this.startActivity(intent);
                        break;
                    case 1:
                        //资料
                        Intent intent1 = new Intent(MyCollectActivity.this, ZiliaoDetailActivity.class);
                        intent1.putExtra("CateDetailModel", collectModel.getTid());
                        intent1.putExtra("classid", collectModel.getClassid());
                        startActivity(intent1);
                        break;
                    case 2:
                        //图库
                        getTuKu(2, collectModel.getTid(), collectModel.getClassid());
                        break;
                    case 3:
                        getTuKu(3, collectModel.getTid(), collectModel.getClassid());
                        break;

                }
            }
        });


        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @SuppressWarnings("deprecation")
            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete2);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        mSwipeMenuListView.setMenuCreator(creator);
        // step 2. listener item click event
        mSwipeMenuListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                if (index == 0) {
                    if (showType == 0) {
                        delete(position);
                        listCollect.remove(position);
                        mAdapter.notifyDataSetChanged();
                    }
                }
                return false;
            }
        });
    }

    private void getTuKu(final int i, final String tid, final String classid) {
        RequestParams params = new RequestParams();
        params.put("enews", "photoshow");
        params.put("sid", tid);
        MyProgressDialog.dialogShow(this);
        H.TuKu(params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                MyProgressDialog.dialogHide();
                String s = new String(responseBody);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int zt = jsonObject.getInt("zt");
                    if (zt == 1) {
                        String title = jsonObject.getString("title");
                        String url = jsonObject.getString("url");
                        if (i == 2) {
                            String qishu = jsonObject.getString("qishu");
                            String type = jsonObject.getString("type");
                            CateModel cateModel = new CateModel(qishu, url, title, type, "", tid);
                            Intent intent = new Intent(MyCollectActivity.this, CategoryDetailActivity.class);
                            intent.putExtra("type", 0);
                            intent.putExtra("CateModel", cateModel);
                            intent.putExtra("des", classid);
                            ArrayList<String> urls = getUrl(cateModel);
                            intent.putStringArrayListExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls);
                            startActivity(intent);
                        } else {
                            Intent intent3 = new Intent(MyCollectActivity.this, ImagePagerActivity.class);
                            intent3.putExtra("type", 0);
                            intent3.putExtra("des", title);
                            ArrayList<String> urls = new ArrayList<>();
                            urls.add(url);
                            intent3.putStringArrayListExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls);
                            startActivity(intent3);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                MyProgressDialog.dialogHide();
            }
        });
    }

    private ArrayList<String> getUrl(CateModel cateModel) {
        ArrayList<String> urls = new ArrayList<>();
        Integer integer = Integer.valueOf(cateModel.getQishu());
        for (int j = integer; j > 0; j--) {
            String s = cateModel.getUrl() + getStr(j) + "/" + cateModel.getType() + ".jpg";
            urls.add(s);
        }
        return urls;
    }

    private String getStr(int a) {
        StringBuffer sb = new StringBuffer();
        if (a < 100) {
            sb.append("0");
        } else if (a < 10) {
            sb.append("0");
        }
        sb.append(a);
        return sb.toString();
    }

    private void initData() {
        urlValue = ApiConfig.MYFAVA1[0];
        if (TextUtils.isEmpty(urlValue)) {
            ToastUtil.toastShow(this, "出错~");
            return;
        }
        MyProgressDialog.dialogShow(this);
        RequestParams params = new RequestParams();
        params.put("enews", urlValue);
        params.put("uid", mUser.getUserid());
        params.put("uname", mUser.getHym());
        params.put("rnd", mUser.getRnd());
        H.ForumData(params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                MyProgressDialog.dialogHide();
                String s = new String(responseBody);
                AppLog.redLog("TTTTT", s);
                if (s.length() > 10) {
                    try {
                        JSONArray jsonArray = new JSONArray(s);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String sid = jsonObject.getString("sid");
                            String bt = jsonObject.getString("bt");
                            String lj = jsonObject.getString("lj");
                            String tid = jsonObject.getString("tid");
                            String classid = jsonObject.getString("classid");
                            CollectModel collectModel = new CollectModel(sid, bt, lj, tid, classid);
                            listCollect.add(collectModel);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (listCollect.size() == 0) {
                    if (mNotData.getVisibility() != View.VISIBLE) {
                        mNotData.setVisibility(View.VISIBLE);
                        mSwipeMenuListView.setVisibility(View.GONE);
                    }
                } else if (mAdapter != null) {
                    if (mSwipeMenuListView.getVisibility() != View.VISIBLE) {
                        mNotData.setVisibility(View.GONE);
                        mSwipeMenuListView.setVisibility(View.VISIBLE);
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                MyProgressDialog.dialogHide();
                boolean destroyed = MyCollectActivity.this.isFinishing();
                ToastUtil.toastShow(MyCollectActivity.this, "網絡錯誤~");
            }
        });
    }

    @Override
    protected void loadData() {

    }

    public int getType(CollectModel collectModel) {
        String classid = collectModel.getClassid();
        if (TextUtils.isEmpty(classid) && "null".equals(classid))
            return -1;
        else {
            if (classid.equals("1")) {
                return 0;
            }
            for (int i = 0; i < Constants.classid.length; i++) {
                if (classid.equals(Constants.classid[i])) {
                    return 1;
                }
            }
            for (int j = 0; j < Constants.classid_TUKU.length; j++) {
                if (classid.equals(Constants.classid_TUKU[j])) {
                    if (j == Constants.classid_TUKU.length - 1) {
                        return 3;
                    } else {
                        return 2;
                    }
                }
            }
        }
        return -1;
    }

    class CollectAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return listCollect.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolader vHolader = null;
            if (convertView == null) {
                vHolader = new ViewHolader();
                convertView = getLayoutInflater().from(MyCollectActivity.this).inflate(R.layout.item_collect, parent, false);
                vHolader.tv = (TextView) convertView.findViewById(R.id.tv_item_title);
                convertView.setTag(vHolader);
            } else {
                vHolader = (ViewHolader) convertView.getTag();
            }
            vHolader.tv.setText(listCollect.get(position).getBt());
            return convertView;
        }

        class ViewHolader {
            TextView tv;
        }
    }


    private void delete(int position) {
        CollectModel collectModel = listCollect.get(position);
        RequestParams params = new RequestParams();
        params.put("enews", "DelFava");
        params.put("userid", mUser.getUserid());
        params.put("username", mUser.getHym());
        params.put("rnd", mUser.getRnd());
        params.put("favaid", collectModel.getSid());
        H.USER(params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    String ts = jsonObject.getString("ts");
//                            String zt = jsonObject.getString("zt");
                    if (!TextUtils.isEmpty(ts)) {

                    }
//                        MyUtitls.showToast(MyCollectActivity.this, ts);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("AsyncHttpClientUtils:", "" + json);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
