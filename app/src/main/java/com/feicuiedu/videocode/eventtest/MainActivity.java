package com.feicuiedu.videocode.eventtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.feicuiedu.videocode.eventtest.videocode.rxjava.RxJavaDemo;
import com.feicuiedu.videocode.eventtest.videocode.utils.BitmapUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String imgUrl = "http://blog.gxnews.com.cn/upload/images/2008/12/u21427/200812522202235987.jpg";
    @Bind(R.id.img) ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        //imageView = (ImageView) findViewById(R.id.img);
        showInetImg();
    }

    private void showInetImg() {
        RxJavaDemo instance = RxJavaDemo.getInstance();
        instance.getImageByte(imgUrl)
                .map(bytes -> {return BitmapUtil.getBitmapByBytes(bytes);})
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bitmap->{imageView.setImageBitmap(bitmap);});
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
