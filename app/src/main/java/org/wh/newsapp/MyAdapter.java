package org.wh.newsapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.wh.newsapp.cache.AsyncImageLoader;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by kkk on 2016/5/22.
 */
public class MyAdapter extends BaseAdapter {

    private Activity activity;
    private String[] mUrls;
    private static LayoutInflater inflater = null;
    private AsyncImageLoader imageLoader;
    private List<NewsInfo> list = new ArrayList<>();


    public MyAdapter(Activity a, String[] urls, List<NewsInfo> list) {
        activity = a;
        mUrls = urls;
        this.list = list;
        inflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = new AsyncImageLoader(activity.getApplicationContext());
    }
    public AsyncImageLoader getImageLoader(){
        return imageLoader;
    }
    @Override
    public int getCount() {
        return mUrls.length;
    }

    @Override
    public NewsInfo getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listtview_item, null);
            vh = new ViewHolder();
            vh.ivImg = (ImageView) convertView.findViewById(R.id.img);
            vh.tvDesc = (TextView) convertView.findViewById(R.id.desc);
            vh.tvTitle = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        String url = mUrls[position];
        vh.ivImg.setTag(url);
        if (vh.ivImg.getTag() != null
                && vh.ivImg.getTag().equals(url)) {
            imageLoader.DisplayImage(url, vh.ivImg);
        }


        NewsInfo data =  list.get(position);
        vh.tvTitle.setText(data.getTitle());
        vh.tvDesc.setText(data.getDescription());



        return convertView;
    }

    private class ViewHolder {
        ImageView ivImg;
        TextView tvDesc;
        TextView tvTitle;
    }


}
