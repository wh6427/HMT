package org.wh.newsapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 解析JSON数据
 * Created by kkk on 2016/5/25.
 */
public class JsonTools {



    public static List<NewsInfo> getData(String key, String jsonResult) {
        List<NewsInfo> list = new ArrayList<NewsInfo>();
        try {
            JSONObject jsonObject = new JSONObject(jsonResult);
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            for (int i = 0; i < jsonArray.length(); i++) {
                NewsInfo info = new NewsInfo();
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                info.setTitle(jsonObject1.getString("title"));
                info.setDescription(jsonObject1.getString("description"));
                info.setUrl(jsonObject1.getString("url"));
                list.add(info);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }



    public static String request(String httpUrl, String httpArg) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?" + httpArg;

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            // 填入apikey到HTTP header
            connection.setRequestProperty("apikey", "c92b918ff6c815a64a37fe1a82b75d34");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                System.out.println(strRead);
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
