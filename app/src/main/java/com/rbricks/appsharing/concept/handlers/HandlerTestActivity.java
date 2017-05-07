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
    private Handler backgroundThreadHandler;
    private Handler mainThreadHandler;
    private Handler secondBackgroundHandler;

    /* Handler used to send the message or run runnable after certain Delay .
     Also if u start a new thread after started/ completed it will be gone u cannot reuse it unless u create a new thread that is a big overhead.
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_test);
        handlerTv = ((TextView) findViewById(R.id.handler_tv));

        doMainThreadHandling();
        doBackgroundHandling();
        interThreadCommunication();

    }

    private void doMainThreadHandling() {
        mainThreadHandler = new Handler(getMainLooper(), (msg) -> {
            switch (msg.what) {
                    case 5:
                        System.out.println("MAIN handled message " + msg.getData());
                        break;
                    default:
                        System.out.println("default MAIN handled message " + msg);
                }
            return false;
        });

        mainThreadHandler.sendEmptyMessage(5);

    }

    private void doBackgroundHandling() {
        HandlerThread handlerThread = new HandlerThread("backgroundthread", 6);
        handlerThread.start();  // u have to start the thread.
        backgroundThreadHandler = new Handler(handlerThread.getLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 3:
                        System.out.println("handled message " + msg.getData());
                        break;
                    default:
                        System.out.println("dafault handled message " + msg);
                }
                return false;
            }
        });  //background thread
//        Handler backgroundThreadHandler = new Handler(getMainLooper());  // for main thread.

        backgroundThreadHandler.postDelayed(() -> {
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
        backgroundThreadHandler.sendMessageDelayed(msg, 3000);
        backgroundThreadHandler.sendEmptyMessageDelayed(4, 6000);
    }

    private void interThreadCommunication() {
        HandlerThread handlerThread = new HandlerThread("NEWBACKGROUNDTHREAD", 7);
        handlerThread.start();

        secondBackgroundHandler = new Handler(handlerThread.getLooper(), (msg) -> {
            return false;
        });

        secondBackgroundHandler.postDelayed(() -> {  // u can send to main queue or other background thread this way.
                                                     // U don't have to write runOnUIThread. ( context.runOnUiThread(Runnable) )
            backgroundThreadHandler.sendEmptyMessage(9);
            mainThreadHandler.sendEmptyMessage(11);
        }, 7000);

    }


}
