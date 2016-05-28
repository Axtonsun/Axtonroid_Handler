package com.example.axtonsun.axtonroid_handler;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView textView;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            textView.setText(""+msg.obj);
            //textView.setText(" "+msg.arg1+"- "+msg.arg2);
        }
    };
    private ImageView imageView;
    private int images[]={R.drawable.guide_image1,R.drawable.guide_image2,R.drawable.guide_image3};
    private int index;
    private MyRunnable myRunnable = new MyRunnable();

    private Button button;

    class Person{
        public int age;
        public String name;

        @Override
        public String toString() {
            return "name="+name+"age="+age;
        }
    }

    class MyRunnable implements Runnable{
        @Override
        public void run() {
            index++;
            index = index%3;
            imageView.setImageResource(images[index]);
            handler.postDelayed(myRunnable,1000);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.tv);
        imageView = (ImageView)findViewById(R.id.iv);

        button = (Button)findViewById(R.id.btn);
        button.setOnClickListener(this);
        /**
         * 第二种方法
         */
        handler.postDelayed(myRunnable,1000);

        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    /*Message message = new Message();
                    message.arg1 = 88;
                    message.arg2 = 100;*/
                    Message message = handler.obtainMessage();
                    Person person = new Person();
                    person.age = 23;
                    person.name = "Axton";
                    message.obj=person;
                    //handler.sendMessage(message);
                    message.sendToTarget();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        /**
         * 第一种方法
         */
        /*new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText("update thread");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();*/
    }

    @Override
    public void onClick(View view) {
        handler.removeCallbacks(myRunnable);

    }
}
