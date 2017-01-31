package com.rbricks.appsharing.concept.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.rbricks.appsharing.R;

/* Imp Concepts to Know
*  1. Activity Main Methods like onMeasure , onLayout , onDraw
*  2. Bitmaps , Content Providers , Loaders , Service , Activity/Fragment Lifecycle, BackStacks
*
* */

public class LifeCycleFirstActivity extends AppCompatActivity {

    /* Complete Execution with fragment from FirstActivity to Second
    *   LifeCycleFirstActivity.onCreate Completed Successfully
        LifeCycleFragment.onAttach
        LifeCycleFragment.onCreate
        LifeCycleFragment.onCreateView
        LifeCycleFragment.onActivityCreated

        LifeCycleFirstActivity.onStart
        LifeCycleFragment.onStart
        LifeCycleFirstActivity.onResume
        LifeCycleFragment.onResume

        LifeCycleFragment.onPause
        LifeCycleFirstActivity.onPause

        //Second activity starting
        LifeCycleSecondActivity***.onCreate
        LifeCycleSecond***.onStart
        LifeCycleSecond***.onResume

        // After OnResume of Second first is stopped
        LifeCycleFirstActivity.onSaveInstanceState
        LifeCycleFragment.onStop
        LifeCycleFirstActivity.onStop  ( if come back to activity again onRestart then onStart is called ... )
    *
    * */
    /*  Application LifeCycle methods.
    onConfigurationChanged(Configuration newConfig)
    Called by the system when the device configuration changes while your component is running.

    void 	onCreate()
    Called when the application is starting, before any activity, service, or receiver objects (excluding content providers) have been created.

    void 	onLowMemory()
    This is called when the overall system is running low on memory, and actively running processes should trim their memory usage.

    void 	onTerminate()
    This method is for use in emulated process environments.

    void 	onTrimMemory(int level)
    Called when the operating system has determined that it is a good time for a process to trim unneeded memory from its process.

    void 	registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks callback)

    void 	registerComponentCallbacks(ComponentCallbacks callback)
    Add a new ComponentCallbacks to the base application of the Context, which will be called at the same times as the ComponentCallbacks methods of activities and other components are called.
    void 	registerOnProvideAssistDataListener(Application.OnProvideAssistDataListener callback)
    */
    /* Process Lifecycle
    The foreground activity (the activity at the top of the screen that the user is currently interacting with) is considered the most important. Its process will only be killed as a last resort, if it uses more memory than is available on the device. Generally at this point the device has reached a memory paging state, so this is required in order to keep the user interface responsive.

    A visible activity (an activity that is visible to the user but not in the foreground, such as one sitting behind a foreground dialog) is considered extremely important and will not be killed unless that is required to keep the foreground activity running.

    A background activity (an activity that is not visible to the user and has been paused) is no longer critical, so the system may safely kill its process to reclaim memory for other foreground or visible processes. If its process needs to be killed, when the user navigates back to the activity (making it visible on the screen again), its onCreate(Bundle) method will be called with the savedInstanceState it had previously supplied in onSaveInstanceState(Bundle) so that it can restart itself in the same state as the user last left it.

    An empty process is one hosting no activities or other application components (such as Service or BroadcastReceiver classes). These are killed very quickly by the system as memory becomes low. For this reason, any background operation you do outside of an activity must be executed in the context of an activity BroadcastReceiver or Service to ensure that the system knows it needs to keep your process around.
     */
    /* Service LifeCycle
       onCreate() - The system invokes this method to perform one-time setup procedures when the service is initially created (before it calls either onStartCommand() or onBind()). If the service is already running, this method is not called.
       onStartCommand() - The system invokes this method by calling startService() when another component (such as an activity) requests that the service be started. When this method executes, the service is started and can run in the background indefinitely. If you implement this, it is your responsibility to stop the service when its work is complete by calling stopSelf() or stopService(). If you only want to provide binding, you don't need to implement this method.
       onBind() - The system invokes this method by calling bindService() when another component wants to bind with the service (such as to perform RPC). In your implementation of this method, you must provide an interface that clients use to communicate with the service by returning an IBinder. You must always implement this method; however, if you don't want to allow binding, you should return null.
       onDestory() - The system invokes this method when the service is no longer used and is being destroyed. Your service should implement this to clean up any resources such as threads, registered listeners, or receivers. This is the last call that the service receives.

       The IntentService class does the following:
    It creates a default worker thread that executes all of the intents that are delivered to onStartCommand(), separate from your application's main thread.
    Creates a work queue that passes one intent at a time to your onHandleIntent() implementation, so you never have to worry about multi-threading.
    Stops the service after all of the start requests are handled, so you never have to call stopSelf().
    Provides a default implementation of onBind() that returns null.
    Provides a default implementation of onStartCommand() that sends the intent to the work queue and then to your onHandleIntent() implementation.
     */
    /*
    * 0. Android N features -> Split window, more doze mode , supporting GCM network manager , removal of connectivity_action broadcastreciever
    *    Android M features -> Permissions , doze introduced, Apache HttpClient removal, read/write bookmarks removed.
    *    Android L featues -> Material Design ( Cards , ripple effects , Toolbar, activity transitions. )  , 64 bit architecture ,
    * 1. The order of Execution is FirstOnPause , SecondOnCreate - OnStart - OnResume , FirstOnSaveInstanceState , FirstResume
    * 2. You should avoid performing CPU-intensive work during onPause(), such as writing to a database, because it can slow the visible transition to the next activity (you should instead perform heavy-load shutdown operations during onStop()).
    * 3. .addToBackStack() when u want to undo on back pressed
    * 4. Use for removing all ur backstack fragments ;;  view.setOnKeyListener(new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            Log.i(tag, "keyCode: " + keyCode);
            if( keyCode == KeyEvent.KEYCODE_BACK ) {
                    Log.i(tag, "onKey Back listener is working!!!");
                getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                return true;
            } else {
                return false;
            }
        }
    });
    * 5. If activity is recreated savedInstance wont be null . By default All Views having Id are automatically saved, U
    *    need to handle the rest of widgets. Or u can do this onRestoreInstanceState() => Called only when onSaveInstanceState Called
    *
    *    public onCreate(Bundle savedInstanceState) {
    *    if( savedInstanceState != null ) {
          Toast.makeText(this, savedInstanceState.getString("message"),Long).show();
          } }
    *
    *   public void onSaveInstanceState(Bundle outState) {
            outState.putString("message", "This is my message to be reloaded");
            super.onSaveInstanceState(outState);
        }
    * 6. onSaveInstanceState when using fragment is similarly onSavedInstanceState and onActivityCreate for reverse .
    * 7. Once Fragment is returned from backstack (by removed/replaced ) , its View would be destroyed and recreated. ie
    *
    * onDestroyView is always called but onDestroy will be called when fragment is completely distroyed like Activity finish() . So order is
    * onDestroyView -> onCreateView ( when restored from backstack) but when newly added
    * onCreate -> onCreateView ... -> onDestroyView -> onDestroy
    *
    * 8. Always have noArgument constructor for fragment bcoz in case of low memory system can recreate the fragment. So always pass data n put in bundle to work correct in all cases.
    * 9. ClearTOP => A B C D -> then A B and B becomes top , B will RECREATE  ; if SingleTop then TOP IS B then it willnot restart the B instead
    * reuse the B Activity onNewIntent() method will be called.
    * 10. onMeasure for measuring , onLayout for positioning it , onDraw(canvas) to draw n paint in canvas ( canvas.draw ..)
    * 11. LinearLayout weight for 3 elements => weight 0 , 1 , 2 then first 0 will be drawn until it fits then remaining space
    *  1 takes 33% and 2 takes 66% ; so 2 layout passes
    * 12. onNewIntent() will be called when SingleTop is used ( when activity in backstack ) . It will not call the onCreate .
    *    Also in general onCreate() flow me onNewIntent() won't get called at all.
    *
    *
    * */



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_cycle_first);
        ((Button) findViewById(R.id.lifeCycleButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LifeCycleFirstActivity.this, LifeCycleSecondActivity.class);
                startActivity(intent);
            }
        });

        LifeCycleFragment lifeCycleFragment = new LifeCycleFragment();
        getFragmentManager().beginTransaction().add(lifeCycleFragment, lifeCycleFragment.getClass().getName()).commit();
        System.out.println("LifeCycleFirstActivity.onCreate Completed Successfully");
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("LifeCycleFirstActivity.onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("LifeCycleFirstActivity.onResume");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        System.out.println("LifeCycleFirstActivity.onSaveInstanceState");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("LifeCycleFirstActivity.onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("LifeCycleFirstActivity.onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("LifeCycleFirstActivity.onDestroy");
    }
}
