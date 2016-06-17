# Axtonroid_Handler
###学习来源
[Handler](http://www.jianshu.com/p/6bf88b69c13f " 我欲举头望明月")   
[Handler解析](http://www.jianshu.com/p/2e3bd373d2df " phoenixsky")   
[Android_Handler](http://www.jianshu.com/p/8f0e1dbd6e8e " ben_speed")   
[Android消息处理机制(Handler、Looper、MessageQueue与Message)](http://www.cnblogs.com/angeldevil/p/3340644.html " AngelDevil") 

####什么是Handler
android处理用来更新UI的一套机制,也是消息处理的一套机制,可以用来发送消息,也可以用来处理消息.
####为什么要用Handler
主要被用于解决子线程上执行更新UI的操作
####为什么要设计只能通过Handler来更新UI
* 更新界面错乱  
 `多个线程更新UI，并且不加锁`
* 性能下降  
 `加锁后性能就会下降`
 
####基本要素
#####Looper负责的就是创建一个MessageQueue，然后进入一个无限循环体不断从该MessageQueue中读取消息，
#####而消息的创建者就是一个或多个Handler 
* Message：消息的表示
* MessageQueue：消息队列(一个存储消息的容器)
* Looper：消息循环，用于从消息队列中取出消息(负责接收Handler发送的消息 并直接把消息回传给Handler自己)
* Handler：消息处理(负责发送消息)

####Handler的使用
* 初始化:为了使用Handler，必须初始化。
* 在主线程中，已经预先初始化了，所以可以直接使用Handler.
* 而在自己创建的线程中，需要**首先调用Looper.prepare();方法用来初始化MessageQueue和实例化Looper并设置Looper作为ThreadLocal的值**，然后调用Looper.loop()；方法进行消息的循环和处理。
* 或者在构造时使用已经初始化的Looper，这样的话，消息的处理依然是在Looper初始化时所在的线程。
* 扩展Handler或者实现Handler.Callback接口作为Handler的构造参数，并实现handleMessage方法。实例化Handler子类时，应该在prepare和loop之间进行。
* 获得Handler对象实例，调用方法发送消息到消息队列。消息可以是Runnable或Message类型，分别是使用post和send方法。  
1.Handler初始化
```
 private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            textView.setText(""+msg.obj);
            //textView.setText(" "+msg.arg1+"- "+msg.arg2);
        }
    };
```
2.在`onCreate`中新建Thread
```
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
                    message.sendToTarget();//调用了自己的sendMessage
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
```        
