## 掲示板アプリ
このアプリはサーバーと通信する掲示板アプリです。
バックエンドの処理は、[こちら](https://github.com/yoshikit1996/go-webapi-bbs)。

## デモ
![アプリ動作例](https://i.imgur.com/XNlIJj7.gif)

## インストールから実行までの流れ
1. git cloneしてください。  
`git clone git@github.com:yoshikit1996/android-bbs.git`

2. Android Studioでプロジェクトを開いてください。

3. WebApiUrls.javaファイルのサーバーのIPアドレスを書き換えてくださいしてください。
```
// src/main/java/com/example/yoshiki/bbs/model/WebApiUrls.java
public class WebApiUrls {
    final static String NEW_POSTS_URL = "http://ipadress:8080/posts/new"; // IPアドレスを書き換えてください。
    final static String POSTS_URL = "http://ipadress:8080/posts/"; // IPアドレスを書き換えてください。
}
```

4. Android Studioで実行してください。
