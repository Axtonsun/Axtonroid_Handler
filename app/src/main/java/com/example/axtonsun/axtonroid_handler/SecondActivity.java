package com.example.axtonsun.axtonroid_handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by AxtonSun on 2016/6/2.
 */
public class SecondActivity extends AppCompatActivity {

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            System.out.println("UI---------->"+Thread.currentThread());//主线程进行
        }
    };

    class MyThread extends Thread{
        public Handler handler;
        public Looper looper;
        @Override
        public void run() {
            Looper.prepare();
            looper = Looper.myLooper();
            handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    System.out.println("current Thread:"+Thread.currentThread());
                }
            };
            Looper.loop();//否则无法循环处理数据
        }
    }



    private MyThread thread;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textView = new TextView(this);
        textView.setText("Hello Handler");
        setContentView(textView);

        thread = new MyThread();
        thread.start();
        /*try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.handler.sendEmptyMessage(1);
        handler.sendEmptyMessage(1);*/

        handler = new Handler(thread.looper){
            @Override
            public void handleMessage(Message msg) {
                System.out.println(msg);
            }
        };
        handler.sendEmptyMessage(1);

    }
}
