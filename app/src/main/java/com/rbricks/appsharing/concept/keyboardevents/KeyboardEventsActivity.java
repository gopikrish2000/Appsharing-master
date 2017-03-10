package com.rbricks.appsharing.concept.keyboardevents;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rbricks.appsharing.R;

public class KeyboardEventsActivity extends AppCompatActivity {

    private ActionEditText etSecond;
    private ActionEditText etFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard_events);
        initViews();
    }

    private void initViews() {
        etSecond = (ActionEditText) findViewById(R.id.keyboard_et_second);
        etFirst = (ActionEditText) findViewById(R.id.keyboard_et_first);

        etFirst.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_NUMPAD_ENTER) {
                Toast.makeText(KeyboardEventsActivity.this, "Enter clicked", Toast.LENGTH_SHORT).show();
            }
            return false;
        });

        TextView.OnEditorActionListener exampleListener = new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    Toast.makeText(KeyboardEventsActivity.this, "NEXT ***", Toast.LENGTH_SHORT).show();
                    etSecond.requestFocus();
                    return true;
                }
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Toast.makeText(KeyboardEventsActivity.this, "DONE ***", Toast.LENGTH_SHORT).show();
                    etSecond.requestFocus();
                    return true;
                }
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    Toast.makeText(KeyboardEventsActivity.this, "DONE ***", Toast.LENGTH_SHORT).show();
                    etSecond.requestFocus();
                    return true;
                }
                return false;
            }
        };
        etFirst.setOnEditorActionListener(exampleListener);
        etSecond.setOnEditorActionListener(exampleListener);


    }
}