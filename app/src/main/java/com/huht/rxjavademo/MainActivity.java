package com.huht.rxjavademo;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.huht.rxjavademo.Service.MovieService;
import com.huht.rxjavademo.bean.MovieEntity;
import com.huht.rxjavademo.util.RetrofitClient;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    private ImageView ivIcon;

    String baseUrl = "https://api.douban.com/v2/movie/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivIcon = (ImageView) findViewById(R.id.iv_icon);
        String[] names = new String[]{"Hello", "Hi", "Aloha"};
        Observable.from(names).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println(s);
            }
        });
        setImg(R.mipmap.ic_launcher,ivIcon);
        RetrofitGet();
    }

    private void setImg(int id, final ImageView iv) {
        Observable.just(id).map(new Func1<Integer, Drawable>() {
            @Override
            public Drawable call(Integer id) {
                return MainActivity.this.getResources().getDrawable(id);
            }
        })
        .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
        .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
        .subscribe(new Action1<Drawable>() {
            @Override
            public void call(Drawable bitmap) {
                iv.setImageDrawable(bitmap);
            }
        });
    }

    private void RetrofitGet(){
        RetrofitClient.INSTANCE.build(baseUrl).create(MovieService.class)
                .getTopMovie(0,10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieEntity>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(MainActivity.this, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        //resultTV.setText(e.getMessage());
                        Log.i("MovieEntity","error: "+e.toString());
                    }

                    @Override
                    public void onNext(MovieEntity movieEntity) {
                        Log.i("MovieEntity","Count: "+movieEntity.getSubjects().size());
                    }
                });
    }
}
