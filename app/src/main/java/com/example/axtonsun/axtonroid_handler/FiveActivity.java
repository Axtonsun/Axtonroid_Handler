package com.example.axtonsun.axtonroid_handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by AxtonSun on 2016/6/2.
 */
public class FiveActivity extends AppCompatActivity{
    private TextView textView;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            textView.setText("Kooo1");
        }
    };

    /**
     * 通过Handler的post()方法
     */
    private void handler1(){
        handler.post(new Runnable() {
            @Override
            public void run() {
                textView.setText("Okkk2");
            }
        });
    }

    /**
     * 调用Handler.sendMessage()传统的方法
     */
    private void hanlder2(){
        handler.sendEmptyMessage(1);
    }

    /**
     * 重写Activity中的runOnUiThread()方法更新
     */
    private void updateUI(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText("kokoko3");
            }
        });
    }

    /**
     * 调用View自身的post(Runnable run)方法更新
     */
    private void viewUI(){
        textView.post(new Runnable() {
            @Override
            public void run() {
                textView.setText("okokok4");
            }
        });
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.five);
        textView=(TextView)findViewById(R.id.textView);
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    //handler1();
                    //hanlder2();
                    //updateUI();
                    viewUI();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
