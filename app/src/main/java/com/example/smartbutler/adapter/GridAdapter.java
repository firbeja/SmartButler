package com.example.smartbutler.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.smartbutler.R;
import com.example.smartbutler.entity.GirlData;
import com.example.smartbutler.utils.PicassoUtils;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by john on 2018/1/26.
 */

public class GridAdapter extends BaseAdapter {

    private List<GirlData>mList;
    private Context mContext;
    private LayoutInflater inflater;
    private WindowManager wm;
    private final int width;

    public GridAdapter(List<GirlData> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();

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
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.girl_item, null);
            viewHolder.imageView = convertView.findViewById(R.id.imageView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        GirlData girlData = mList.get(position);
        String url = girlData.getImageUrl();

        if (!TextUtils.isEmpty(url)){
        PicassoUtils.loadImageViewSize(mContext, url, width/2, 700, viewHolder.imageView);}

        return convertView;
    }

    class ViewHolder {
        private ImageView imageView;
    }

}
