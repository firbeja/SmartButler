package com.example.smartbutler;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.smartbutler.fragment.ButlterFragment;
import com.example.smartbutler.fragment.GirlFragment;
import com.example.smartbutler.fragment.UserFragment;
import com.example.smartbutler.fragment.WechatFragment;
import com.example.smartbutler.ui.BaseActivity;
import com.example.smartbutler.ui.SettingActivity;
import com.example.smartbutler.utils.L;
import com.example.smartbutler.utils.ShareUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private FloatingActionButton fab_setting;
    //选项卡控件
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    //选项卡 四个标题 服务管家 微信精选 美女社区 个人中心
    private List<String> mTitle;
    //选项卡 四个fragment
    private List<Fragment> mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化 选项卡的四个标题 服务管家 微信精选 美女社区 个人中心
        //同时 也初始化List<Fragment> ，把四个fragment放进去
        initData();

        //初始化各个View ，给TabLayout 设置 Adapter
        initView();

        ShareUtils.putString(this, "username", "时代");
        String string = ShareUtils.getString(this, "usernamem", "默认值");
        L.d(string + "...........................");

    }

    private void initView() {

        fab_setting = (FloatingActionButton) findViewById(R.id.fab_setting);
        fab_setting.setOnClickListener(this);
        //默认隐藏，因为进来就是 服务管家 ，所以会挡到，要隐藏
        fab_setting.setVisibility(View.GONE);
        //去除ActionBar下面的阴影
        getSupportActionBar().setElevation(0);

        mTabLayout = (TabLayout) findViewById(R.id.mTabLayout);
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);

        //预加载
        mViewPager.setOffscreenPageLimit(mFragment.size());

        //滑动监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected: -------- position----" + position);
                if (position == 0){
                    fab_setting.setVisibility(View.GONE);
                }else {
                    fab_setting.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //设置适配器
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public android.support.v4.app.Fragment getItem(int position) {
                return mFragment.get(position);
            }

            @Override
            public int getCount() {
                return mFragment.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }
        });

        mTabLayout.setupWithViewPager(mViewPager);

    }

    private void initData() {

        mTitle = new ArrayList<>();
        mTitle.add("服务管家");
        mTitle.add("微信精选");
        mTitle.add("美女社区");
        mTitle.add("个人中心");

        mFragment = new ArrayList<>();
        mFragment.add(new ButlterFragment());
        mFragment.add(new WechatFragment());
        mFragment.add(new GirlFragment());
        mFragment.add(new UserFragment());

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //点击悬浮按钮 跳转到 设置界面
            case R.id.fab_setting:
                startActivity(new Intent(this, SettingActivity.class));
                L.d("点击悬浮按钮 跳转到 设置界面");
                break;
        }
    }
}
