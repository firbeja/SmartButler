package com.example.smartbutler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.smartbutler.R;
import com.example.smartbutler.entity.CourierData;

import java.util.List;

/**
 * Created by john on 2018/1/23.
 */

public class CourierAdapter extends BaseAdapter {

    private Context mContext;
    private List<CourierData> mList;
    //布局加载器
    private LayoutInflater inflater;
    private CourierData courierData;

    public CourierAdapter(Context mContext, List<CourierData> mList) {
        this.mContext = mContext;
        this.mList = mList;
        //获取系统服务
        inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
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

        ViewHolder viewHolder = null;
        //第一次加载
        if (convertView == null){
            convertView = inflater.inflate(R.layout.layout_courier_item, null);
            viewHolder = new ViewHolder();
            viewHolder.tvRemake = convertView.findViewById(R.id.tv_remake);
            viewHolder.tvZone = convertView.findViewById(R.id.tv_zone);
            viewHolder.tvDatetime = convertView.findViewById(R.id.tv_datetime);
            //设置缓存
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

//        View view = inflater.inflate(R.layout.layout_courier_item, null);
//        TextView tvRemake = view.findViewById(R.id.tv_remake);
//        TextView tvZone = view.findViewById(R.id.tv_zone);
//        TextView tvDatetime = view.findViewById(R.id.tv_datetime);
//
//        CourierData courierData = mList.get(position);
//        tvRemake.setText(courierData.getRemark());
//        tvZone.setText(courierData.getZone());
//        tvDatetime.setText(courierData.getDatetime());

        //设置数据源
        courierData = mList.get(position);

        viewHolder.tvRemake.setText(courierData.getRemark());
        viewHolder.tvZone.setText(courierData.getZone());
        viewHolder.tvDatetime.setText(courierData.getDatetime());

        return convertView;
    }

    class ViewHolder{

        private TextView tvRemake;
        private TextView tvZone;
        private TextView tvDatetime;

    }

}
