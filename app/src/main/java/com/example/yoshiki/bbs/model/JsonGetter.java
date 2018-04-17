package com.example.yoshiki.bbs.model;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class JsonGetter {
    JSONArray jsonArray;

    // サーバーからJSONArrayを取得する
    public JSONArray getJsonArray(){
        JsonArraySetter jsonArraySetter = new JsonArraySetter();
        // メインスレッドでネットワークに接続できないため、サブスレッドでJSONArrayを取得する
        Thread thread = new Thread(jsonArraySetter);
        thread.start();
        try{
            // JSONArrayの取得が完了するまでを待つ
            thread.join();
        }catch (InterruptedException e){
            Log.e("Get Json", e.toString());
        }
        return jsonArray;
    }

    class JsonArraySetter implements Runnable {
        @Override
        public void run() {
            jsonArray = this.getJsonArray();
        }

        private JSONArray getJsonArray(){
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(WebApiUrls.POSTS_URL);
            try {
                HttpResponse httpResponse = httpClient.execute(httpGet);
                if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    httpResponse.getEntity().writeTo(outputStream);
                    outputStream.close();
                    return new JSONArray(outputStream.toString());
                } else {
                    httpResponse.getEntity().getContent().close();
                    throw new IOException();
                }
            } catch (IOException e) {
                Log.e("Get Json", e.toString());
            } catch (JSONException e) {
                Log.e("Get Json", e.toString());
            }
            return null;
        }
    }
}
