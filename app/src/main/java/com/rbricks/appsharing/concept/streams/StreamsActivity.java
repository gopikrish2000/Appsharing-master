package com.rbricks.appsharing.concept.streams;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.rbricks.appsharing.R;
import com.rbricks.appsharing.concept.animations.AdvancedAnimationActivity;

import java.util.Collections;
import java.util.List;

import java8.util.stream.Collector;
import java8.util.stream.Collectors;
import java8.util.stream.RefStreams;
import java8.util.stream.StreamSupport;

import static com.jakewharton.rxbinding2.view.RxView.clicks;
import static com.jakewharton.rxbinding2.widget.RxTextView.textChangeEvents;
import static com.rbricks.appsharing.utils.CommonUtils.isNullOrEmpty;

public class StreamsActivity extends AppCompatActivity {

    private TextView resultsTV;
    private EditText implementationEditText;
    private String currentImplementationText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_streams);
        init();
        clicks(findViewById(R.id.processButton)).subscribe(s -> performCurrentImplementation());
        clicks(findViewById(R.id.recentButton)).subscribe(s -> first());
        textChangeEvents(implementationEditText).subscribe(s -> currentImplementationText = s.text().toString());
    }

    private void init() {
        resultsTV = (TextView) findViewById(R.id.results_tv);
        implementationEditText = ((EditText) findViewById(R.id.process_et));
    }

    private void performCurrentImplementation() {
        if (isNullOrEmpty(currentImplementationText)) {
            return;
        }

        switch (currentImplementationText) {
            case "first":
                first();
                break;
        }
    }


    private void first() {
        //For primitive types use mapToInt instead of map to avoid unnecessary boxing n unboxing .
        List<Integer> collect = RefStreams.of(1, -2, 4, 3, 10, 8, 9).filter(s -> s > 1).sorted().collect(Collectors.toList());
        List<String> stringList = StreamSupport.parallelStream(collect).filter(s -> s > 3).map(s -> s + "").collect(Collectors.toList());
        String reduce = StreamSupport.stream(stringList).reduce("", (a, b) -> a + b + ", | ");
        System.out.println("reduced Value = " + reduce);
        resultsTV.setText(reduce + "");
    }

    private void second() {
//        List<String> stringList = RefStreams.of("abc", "def", "ghi", "jkl").collect(Collectors.groupingBy(Collector.F, s -> s.length()));

    }


}


