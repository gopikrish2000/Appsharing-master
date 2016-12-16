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
        LifeCycleFirstActivity.onStop
    *
    * */

    /*
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
          Toast.makeText(this, savedInstanceState .getString("message"),Long).show();
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
