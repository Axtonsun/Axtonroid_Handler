package com.example.axtonsun.axtonroid_handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by AxtonSun on 2016/6/2.
 */
public class ThreeActivity extends AppCompatActivity {

    private TextView textView;

    private HandlerThread thread;
    private Handler handler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView= new TextView(this);
        textView.setText("handler Thread");
        setContentView(textView);

        thread = new HandlerThread("handlerthread");
        thread.start();
        handler=new Handler(thread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                System.out.println("current thread------>"+Thread.currentThread());
            }
        };
        handler.sendEmptyMessage(1);
    }
}
