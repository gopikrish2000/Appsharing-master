package com.rbricks.appsharing.concept.constraintlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.rbricks.appsharing.R;

import java.security.acl.Group;


public class ConstraintLayoutAdvancedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraintlayout_advanced);

        final android.support.constraint.Group firstGroup = findViewById(R.id.first_group_id);
        firstGroup.setOnClickListener(view -> {
            Toast.makeText(BasicActivity.this, "clicked ", Toast.LENGTH_LONG).show();
        });
        firstGroup.setVisibility(View.GONE);
        final int[] referencedIds = firstGroup.getReferencedIds();
        System.out.println("referencedIds = " + referencedIds);
    }
}
