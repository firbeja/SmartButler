package com.example.smartbutler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartbutler.R;
import com.example.smartbutler.entity.WechatData;

import java.util.List;

/**
 * Created by john on 2018/1/25.
 */

public class WechatAdapter extends BaseAdapter {

    private Context mContext;
    private List<WechatData>mList;
    private LayoutInflater inflater;
    private WechatData data;
    private ViewHolder viewHolder;

    public WechatAdapter(Context mContext, List<WechatData> mList) {
        this.mContext = mContext;
        this.mList = mList;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        viewHolder = new ViewHolder();
        //初始化各个控件
        if (convertView == null){
            convertView = inflater.inflate(R.layout.wechat_item, null);
//            viewHolder.iv_firstImg = convertView.findViewById(R.id.iv_firstImg);
            viewHolder.tv_title = convertView.findViewById(R.id.tv_title);
            viewHolder.tv_source = convertView.findViewById(R.id.tv_source);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //赋值
        WechatData wechatData = mList.get(position);
        viewHolder.tv_title.setText(wechatData.getTitle());
        viewHolder.tv_source.setText(wechatData.getSource());

        return convertView;
    }

    class ViewHolder {

        private ImageView iv_firstImg;
        private TextView tv_title;
        private TextView tv_source;

    }

}
