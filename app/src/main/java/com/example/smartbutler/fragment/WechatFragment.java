package com.example.smartbutler.fragment;
/*
 * 项目名：SmartButler
 * 包名：com.example.smartbutler.fragment
 * 文件名：UserFragment
 * 创建者：LB
 * 创建时间：2018/1/17 下午1:55
 * 描述：   TODO
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.smartbutler.R;
import com.example.smartbutler.adapter.WechatAdapter;
import com.example.smartbutler.entity.WechatData;
import com.example.smartbutler.ui.WebViewActivity;
import com.example.smartbutler.utils.L;
import com.example.smartbutler.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WechatFragment extends Fragment {

    private ListView mListView;
    private List<WechatData>mList;
    private List<String>mListTitle;
    private List<String>mListUrl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wechat, null);
        findView(view);
        return view;
    }

    private void findView(View view) {

        mListView = view.findViewById(R.id.mListView);
        mList = new ArrayList<>();
        mListTitle = new ArrayList<>();
        mListUrl = new ArrayList<>();

//        "result":{
//            "list":[
//            {
//                "id":"wechat_20180125010912",
//                    "title":"有鱼，有鸟，算不算生态河道？山东十六项指标确立“标尺”",
//                    "source":"山东环境",
//                    "firstImg":"http://zxpic.gtimg.com/infonew/0/wechat_pics_-34292156.static/640",
//                    "mark":"",
//                    "url":"http://v.juhe.cn/weixin/redirect?wid=wechat_20180125010912"
//            },
        String url = "http://v.juhe.cn/weixin/query?key=" + StaticClass.WECHAT_KEY;
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                L.d(t);
                parsingData(t);
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("title", mListTitle.get(position));
                intent.putExtra("url", mListUrl.get(position));
                startActivity(intent);
            }
        });

    }

    private void parsingData(String t) {

        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject result = jsonObject.getJSONObject("result");
            JSONArray list = result.getJSONArray("list");
            for (int i = 0; i < list.length(); i++) {
                JSONObject json = (JSONObject) list.get(i);
                String title = json.getString("title");
                String source = json.getString("source");
                String firstImg = json.getString("firstImg");
                String url = json.getString("url");
                WechatData wechatData = new WechatData();
                wechatData.setTitle(title);
                wechatData.setSource(source);
                wechatData.setFirstImg(firstImg);
                wechatData.setUrl(url);
                mList.add(wechatData);

                mListTitle.add(title);
                mListUrl.add(url);
            }
            WechatAdapter adapter = new WechatAdapter(getActivity(), mList);
            mListView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
