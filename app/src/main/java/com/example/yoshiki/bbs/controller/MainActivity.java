package com.example.yoshiki.bbs.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.yoshiki.bbs.R;
import com.example.yoshiki.bbs.model.JsonGetter;
import com.example.yoshiki.bbs.model.JsonPoster;
import com.example.yoshiki.bbs.view.CommentRenderer;

import org.json.JSONArray;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private CommentRenderer commentView;
    private JsonPoster jsonPoster;
    private JsonGetter jsonGetter;

    private TextView textViewName;
    private TextView textViewComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewName = (TextView)findViewById(R.id.name);
        textViewComment = (TextView)findViewById(R.id.comment);

        commentView = new CommentRenderer(this);
        jsonPoster = new JsonPoster();
        jsonGetter = new JsonGetter();
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.send_button:
                // 画面から名前とコメントを取得する
                String name = textViewName.getText().toString();
                String comment = textViewComment.getText().toString();

                // 名前とコメントが空でなければ、サーバーに送信する
                if(isNotEmpty(name) && isNotEmpty(comment)){
                    jsonPoster.post(name, comment);
                }

                // 画面のコメント入力欄を空欄にする
                textViewComment.setText("");
            case R.id.update_button:
                // JSONArrayをサーバー側から取得して、コメント表示欄を更新する
                JSONArray jsonArray = jsonGetter.getJsonArray();
                commentView.reRenderComments(jsonArray);
                break;
        }
    }

    private boolean isNotEmpty(String text){
        String trimmed = text
                .replaceAll("^[\\s　]*", "")
                .replaceAll("[\\s　]*$", "");
        return !trimmed.isEmpty();
    }
}
