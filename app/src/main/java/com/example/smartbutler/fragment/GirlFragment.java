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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartbutler.R;

public class GirlFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_girl, null);
        return view;
    }
}
