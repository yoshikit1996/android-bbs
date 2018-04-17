package com.example.yoshiki.bbs.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class JsonPoster {
    // サーバー側にコメントをPOST送信する
    public void post(String name, String comment){
        Runnable runnable = new Post(name, comment);
        // メインスレッドではネットワークに接続できないため、サブスレッドでPOST送信する
        Thread t = new Thread(runnable);
        t.start();
        try{
            // コメントが送信し終わるのを待つ
            t.join();
        }catch (InterruptedException e){
            Log.e("Post Json", e.toString());
        }
    }

    class Post implements Runnable{
        private String name;
        private String comment;

        public Post(String name, String comment){
            this.name = name;
            this.comment = comment;
        }

        @Override
        public void run(){
            String jsonString
                    = String.format("{\"name\":\"%s\",\"comment\":\"%s\"}", name, comment);
            try {
                String buffer = "";
                HttpURLConnection con = null;
                URL url = new URL(WebApiUrls.NEW_POSTS_URL);
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setInstanceFollowRedirects(false);
                con.setRequestProperty("Accept-Language", "jp");
                con.setDoOutput(true);
                con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                OutputStream os = con.getOutputStream();
                PrintStream ps = new PrintStream(os);
                ps.print(jsonString);
                ps.close();

                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                buffer = reader.readLine();

                JSONObject jsonObject = new JSONObject(buffer);
                Log.d("Http Req", jsonObject.toString(4));

                con.disconnect();
            } catch (MalformedURLException e) {
                Log.e("Post Json", e.toString());
            } catch (ProtocolException e) {
                Log.e("Post Json", e.toString());
            } catch (IOException e) {
                Log.e("Post Json", e.toString());
            } catch (Exception e) {
                Log.e("Post Json", e.toString());
            }
        }
    }
}
