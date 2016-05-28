package org.wh.newsapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.wh.newsapp.cache.AsyncImageLoader;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    ListView listView;
    Button refresh_btn;
    MyAdapter adapter;
    List<NewsInfo> list = new ArrayList<NewsInfo>();

    String httpUrl = "http://apis.baidu.com/txapi/world/world";
    String httpArg = "num=12&page=1";
    String jsonResult ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 0x1234){

                    set();//设置adapter
                }
            }
        };
        listView = (ListView) findViewById(R.id.mylist);
        new Thread(new Runnable() {
            @Override
            public void run() {
                jsonResult = JsonTools.request(httpUrl, httpArg);
                System.out.println(jsonResult);
                list = JsonTools.getData("newslist", jsonResult);
                handler.sendEmptyMessage(0x1234);
            }
        }).start();

        refresh_btn = (Button) findViewById(R.id.refresh_btn);
        refresh_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        refresh();
                    }
                }
        );

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                NewsInfo info = adapter.getItem(position);
                Intent intent = new Intent(MainActivity.this, WebviewActivity.class);
                intent.putExtra("url", info.getUrl());
                startActivity(intent);

            }
        });

    }

    private void set() {
        adapter = new MyAdapter(this, URLS, list);
        listView.setAdapter(adapter);
    }

    private void refresh() {
        onCreate(null);
    }

    @Override
    protected void onDestroy() {
        AsyncImageLoader imageLoader = adapter.getImageLoader();
        if (imageLoader != null) {
            imageLoader.shutDownThreadPool();
            imageLoader.clearCache();

        }
        super.onDestroy();
    }

    private String[] URLS = {"http://s3-us-west-1.amazonaws.com/realisticshots/2016/0233.jpg",
            "http://s3-us-west-1.amazonaws.com/realisticshots/2016/0234.jpg",
            "http://s3-us-west-1.amazonaws.com/realisticshots/2016/0235.jpg",
            "http://s3-us-west-1.amazonaws.com/realisticshots/2016/0236.jpg",
            "http://s3-us-west-1.amazonaws.com/realisticshots/2016/0237.jpg",
            "http://s3-us-west-1.amazonaws.com/realisticshots/2016/0238.jpg",
            "http://s3-us-west-1.amazonaws.com/realisticshots/2016/0239.jpg",
            "http://s3-us-west-1.amazonaws.com/realisticshots/2016/0240.jpg",
            "http://s3-us-west-1.amazonaws.com/realisticshots/2016/0241.jpg",
            "http://s3-us-west-1.amazonaws.com/realisticshots/2016/0242.jpg",
            "http://s3-us-west-1.amazonaws.com/realisticshots/2016/0243.jpg",
            "http://s3-us-west-1.amazonaws.com/realisticshots/2016/0244.jpg"};



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
