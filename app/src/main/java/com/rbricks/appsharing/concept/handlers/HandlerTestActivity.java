package com.rbricks.appsharing.concept.handlers;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.rbricks.appsharing.R;

public class HandlerTestActivity extends AppCompatActivity  {

    private TextView handlerTv;

    /* Handler used to send the message or run runnable after certain Delay .
     Also if u start a new thread after started/ completed it will be gone u cannot reuse it unless u create a new thread that is a big overhead.
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_test);
        handlerTv = ((TextView) findViewById(R.id.handler_tv));

        HandlerThread handlerThread = new HandlerThread("backgroundthread", 6);
        handlerThread.start();  // u have to start the thread.
        Handler handler = new Handler(handlerThread.getLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 3:
                        System.out.println("handled message " + msg.getData());
                        break;
                    default:
                        System.out.println("not handled message " + msg);
                }
                return false;
            }
        });  //background thread
//        Handler handler = new Handler(getMainLooper());  // for main thread.

        handler.postDelayed(() -> {
            System.out.println("this is background thread , trying to update the UI");
            try {
                handlerTv.setText("updatedText");   // throws exception   android.view.ViewRootImpl$CalledFromWrongThreadException: Only the original thread that created a view hierarchy can touch its views.
            } catch (Exception e) {
                e.printStackTrace();
            }

        }, 3000);

        Message msg = new Message();
        msg.what = 3;
        Bundle bundle = new Bundle();
        bundle.putString("key", "sample gopi value");
        msg.setData(bundle);
        handler.sendMessageDelayed(msg, 3000);

        handler.sendEmptyMessageDelayed(4, 6000);

    }

}
