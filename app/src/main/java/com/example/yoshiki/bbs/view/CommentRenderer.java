package com.example.yoshiki.bbs.view;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yoshiki.bbs.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CommentRenderer {
    private Activity activity;
    private LinearLayout linearLayout;

    TextView textViewName;
    TextView textViewComment;

    public CommentRenderer(Activity activity){
        this.activity = activity;
        this.linearLayout = (LinearLayout)activity.findViewById(R.id.comments_layout);
        this.textViewName = (TextView)activity.findViewById(R.id.name);
        this.textViewComment = (TextView)activity.findViewById(R.id.comment);
    }

    private void renderComment(String name, String content, String time){
        View view = activity.getLayoutInflater().inflate(R.layout.comment,null);

        TextView textViewTitle = (TextView) view.findViewById(R.id.comment_title);
        TextView textViewContent = (TextView) view.findViewById(R.id.comment_content);

        textViewTitle.setText(name + " " + time);
        textViewContent.setText(content);

        linearLayout.addView(view);
    }

    private void renderComments(JSONArray jsonArray){
        if(jsonArray == null)
            return;
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                String comment = jsonObject.getString("comment");
                String time = jsonObject.getString("time");
                renderComment(name, comment, time);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void reRenderComments(JSONArray jsonArray){
        // 画面に表示されているコメント表示欄のコメントをすべて消去する
        linearLayout.removeAllViews();
        // コメントをコメント表示欄に表示する
        renderComments(jsonArray);
    }
}
