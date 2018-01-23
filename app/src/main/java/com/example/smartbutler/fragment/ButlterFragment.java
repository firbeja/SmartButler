package com.example.smartbutler.fragment;
/*
 * 项目名：SmartButler
 * 包名：com.example.smartbutler.fragment
 * 文件名：UserFragment
 * 创建者：LB
 * 创建时间：2018/1/17 下午1:55
 * 描述：   TODO
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.smartbutler.R;
import com.example.smartbutler.adapter.ChatListAdapter;
import com.example.smartbutler.entity.ChatListData;
import com.example.smartbutler.utils.L;
import com.example.smartbutler.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ButlterFragment extends Fragment implements View.OnClickListener {

    private ListView mListView;
    private List<ChatListData>mList;
    private ChatListAdapter adapter;
    private EditText et_text;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_butler, null);
        findView(view);
        return view;
    }

    private void findView(View view) {

//        Button btn_left = view.findViewById(R.id.btn_left);
//        btn_left.setOnClickListener(this);
//        Button btn_right = view.findViewById(R.id.btn_right);
//        btn_right.setOnClickListener(this);

        mListView = view.findViewById(R.id.mListView);
        et_text = view.findViewById(R.id.et_text);
        Button btn_send = view.findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);

        mList = new ArrayList<>();
        adapter = new ChatListAdapter(getActivity(), mList);
        mListView.setAdapter(adapter);
        addLeftItem("你好，我是小优");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.btn_left :
//                addLeftItem("左边");
//                break;
//            case R.id.btn_right :
//                addRightItem("右边");
//                break;
            case R.id.btn_send :
                String text = et_text.getText().toString().trim();
                if (!TextUtils.isEmpty(text)){
                    if (text.length() > 30){
                        Toast.makeText(getActivity(), "输入长度超出限制", Toast.LENGTH_SHORT).show();
                    }else {
                        addRightItem(text);
                        et_text.setText("");
                        String url = "http://op.juhe.cn/robot/index?info=" + text + "&key=" + StaticClass.CHAT_LIST_KEY;
                        RxVolley.get(url, new HttpCallback() {
                            @Override
                            public void onSuccess(String t) {
                                L.d(t);
                                parsingJson(t);
                            }
                        });
                    }
                }else {
                    Toast.makeText(getActivity(), "输入框不能为空", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

//    {
//        "reason":"成功的返回",
//            "result": /*根据code值的不同，返回的字段有所不同*/
//        {
//            "code":100000, /*返回的数据类型，请根据code的值去数据类型API查询*/
//                "text":"你好啊，希望你今天过的快乐"
//        },
//        "error_code":0
//    }
    private void parsingJson(String t) {

        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject result = jsonObject.getJSONObject("result");
            String text = result.getString("text");
            addLeftItem(text);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void addRightItem(String text) {

        ChatListData data = new ChatListData();
        data.setType(ChatListAdapter.VALUE_RIGHT_TEXT);
        data.setText(text);
        mList.add(data);
        adapter.notifyDataSetChanged();
        mListView.setSelection(mListView.getBottom());

    }

    private void addLeftItem(String text) {

        ChatListData data = new ChatListData();
        data.setType(ChatListAdapter.VALUE_LEFT_TEXT);
        data.setText(text);
        mList.add(data);
        adapter.notifyDataSetChanged();
        mListView.setSelection(mListView.getBottom());

    }


}
