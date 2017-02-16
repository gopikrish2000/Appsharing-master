package com.rbricks.appsharing.concept.java8;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.rbricks.appsharing.R;

/**
 * Created by gopikrishna on 16/02/17.
 */

public class CustomLambdaReferenceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_lambda);
        customLambdaTake((a, b) -> a + b, 3, 5);
    }

    //    Function function = (int a , int b)
    interface LambdaNew {
        int add(int a, int b);
    }

    public void customLambdaTake(LambdaNew lambdaNew, int a, int b) {
        int val = lambdaNew.add(a, b);
        System.out.println(" Custom Lambda val = " + val);
    }
}
