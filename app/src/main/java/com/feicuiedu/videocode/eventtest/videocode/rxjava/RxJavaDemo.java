package com.feicuiedu.videocode.eventtest.videocode.rxjava;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

public class RxJavaDemo {
    private static RxJavaDemo rxJavaStudy;
    private OkHttpClient okHttpClient;
    private RxJavaDemo(){
        okHttpClient = new OkHttpClient();
    }
    public static synchronized RxJavaDemo getInstance(){
        if (rxJavaStudy == null) {
            rxJavaStudy = new RxJavaDemo();
        }
        return rxJavaStudy;
    }

    public Observable<List<String>> query(String key){
        return Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                if(!subscriber.isUnsubscribed()){
                    List<String > list = new ArrayList<String>();
                    //搜索功能将搜索到的数据添加到list中
                    // TODO: 2016/8/30
                    subscriber.onNext(list);
                    subscriber.onCompleted();
                }else{
                    subscriber.onError(new RuntimeException());
                }
            }
        });
    }
    public Observable<byte[]> getImageByte(String imagUrl){
        return Observable.create(new Observable.OnSubscribe<byte[]>() {
            @Override
            public void call(Subscriber<? super byte[]> subscriber) {
                //观察者和订阅者存在订阅关系
                if(!subscriber.isUnsubscribed()){
                    Request request = new Request.Builder()
                            .get()
                            .url(imagUrl)
                            .build();
                    Call call = okHttpClient.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {
                            subscriber.onError(e);
                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
                            byte[] bytes = response.body().bytes();
                            subscriber.onNext(bytes);
                            subscriber.onCompleted();
                        }
                    });
                }
            }
        });
    }

}
