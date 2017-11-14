package com.rbricks.appsharing.newconcept.debounceWithoutRxJava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.rbricks.appsharing.R;

import java.util.Random;

public class DebounceWithoutRxJavaActivity extends AppCompatActivity {

    private TextView debounceTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debounce_without_rx_java);

        debounceTv = (TextView) findViewById(R.id.debounce_tv);
        View debounceBtn = findViewById(R.id.debounce_btn);
        Random random = new Random();

        debounceBtn.setOnClickListener(DebouncedOnClickListener.wrap(view -> {
            String randomNumb = random.nextInt(100) + "";
            debounceTv.setText(randomNumb);
        }));

        // second way

        new DebounceClick(debounceBtn, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        DebounceClick.clicksOther(debounceBtn, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
