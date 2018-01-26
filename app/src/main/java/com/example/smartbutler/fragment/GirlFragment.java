package com.example.smartbutler.fragment;
/*
 * 项目名：SmartButler
 * 包名：com.example.smartbutler.fragment
 * 文件名：UserFragment
 * 创建者：LB
 * 创建时间：2018/1/17 下午1:55
 * 描述：   TODO
 */

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.smartbutler.R;
import com.example.smartbutler.adapter.GridAdapter;
import com.example.smartbutler.entity.GirlData;
import com.example.smartbutler.utils.L;
import com.example.smartbutler.utils.PicassoUtils;
import com.example.smartbutler.view.CustomDialog;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class GirlFragment extends Fragment {

    private GridView mGridView;
    private GirlData girlData;
    private List<GirlData>mList;
    private CustomDialog dialog;
    private ImageView iv_img;
    private List<String>mListUrl;
    private PhotoViewAttacher mAttacher;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_girl, null);
        findView(view);
        return view;
    }

    private void findView(View view) {

        mList = new ArrayList<>();
        mListUrl = new ArrayList<>();
        mGridView = view.findViewById(R.id.mGridView);
        dialog = new CustomDialog(getActivity(), LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, R.layout.dialog_girl,
                R.style.Theme_dialog, Gravity.CENTER,R.style.pop_anim_style);
        iv_img = dialog.findViewById(R.id.iv_img);


//        {
//            "error": false,
//                "results": [
//            {
//                "_id": "5a65381a421aa91156960022",
//                    "createdAt": "2018-01-22T09:02:18.715Z",
//                    "desc": "1-22",
//                    "publishedAt": "2018-01-23T08:46:45.132Z",
//                    "source": "chrome",
//                    "type": "\u798f\u5229",
//                    "url": "http://7xi8d6.com1.z0.glb.clouddn.com/20180122090204_A4hNiG_Screenshot.jpeg",
//                    "used": true,
//                    "who": "daimajia"
//            },
//            {
//                "_id":

        String url = "http://gank.io/api/data/福利/50/1";
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                L.d(t);
                prasingData(t);
            }
        });

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PicassoUtils.loadImageView(getActivity(), mListUrl.get(position), iv_img);
                mAttacher = new PhotoViewAttacher(iv_img);
                mAttacher.update();
                dialog.show();
            }
        });

    }

    private void prasingData(String t) {

        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONArray results = jsonObject.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject json = (JSONObject) results.get(i);
                String url = json.getString("url");
                girlData = new GirlData();
                girlData.setImageUrl(url);
                mListUrl.add(url);
                mList.add(girlData);
                GridAdapter adapter = new GridAdapter(mList, getActivity());
                mGridView.setAdapter(adapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
