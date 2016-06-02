package com.example.axtonsun.axtonroid_handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by AxtonSun on 2016/6/2.
 */
public class FourActivity extends AppCompatActivity implements View.OnClickListener{

    /**
     * 创建主线程的Handler
     */
    private Handler handler = new Handler(){//主线程
        @Override
        public void handleMessage(Message msg) {
            Message message = new Message();
            System.out.println("Main Handler");
            //向子线程发送消息
            threadHandler.sendMessageDelayed(message,1000);

        }
    };

    private Handler threadHandler;//子线程Handler

    private Button btn1,btn2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.four);

        btn1=(Button)findViewById(R.id.btn1);
        btn2=(Button)findViewById(R.id.btn2);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);

        HandlerThread thread = new HandlerThread("handlerThread");
        thread.start();
        /**
         * 创建子线程的Handler 与HandlerThread有关联
         */
        threadHandler=new Handler(thread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                Message message = new Message();
                System.out.println("Thread Handler");
                //向主线程发送消息
                handler.sendMessageDelayed(message,1000);

            }
        };

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn1:
                handler.sendEmptyMessage(1);
                break;
            case R.id.btn2:
                handler.removeMessages(1);
                break;
            default:
                break;
        }

    }
}
