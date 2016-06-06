package com.rbricks.appsharing;

import android.app.IntentService;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rbricks.appsharing.services.GopiIntentService;

public class IntentServiceEmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_service_empty);
        Intent intent = new Intent(this, GopiIntentService.class);
        intent.putExtra("intentServiceParam", " This is IntentServiceParam");
        startService(intent);
    }
}
