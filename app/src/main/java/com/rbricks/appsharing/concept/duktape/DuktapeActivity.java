package com.rbricks.appsharing.concept.duktape;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rbricks.appsharing.R;
import com.squareup.duktape.Duktape;

public class DuktapeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duktape);

        sampleDuktape();
    }

    interface GopiDuktapeInterface {
        String sampleMethod(String input);
    }

    class GopiDuktapeImplementation implements GopiDuktapeInterface {
        @Override
        public String sampleMethod(String input) {
            return input+"ADDED";
        }
    }

    private void sampleDuktape() {
        GopiDuktapeImplementation gopiDuktapeImplementation = new GopiDuktapeImplementation();
        Duktape duktape = Duktape.create();
        try {
            // Bind our interface to a JavaScript object called Utf8.
            duktape.bind("InterfaceBinder", GopiDuktapeInterface.class, gopiDuktapeImplementation);
            String result = duktape.evaluate("" +
                    // Here we have a hex encoded string.
                    "var hexEnc = 'EC9588EB8595ED9598EC84B8EC9A9421';\n" +
                    // Call out to Java to decode it!
                    "var message = InterfaceBinder.sampleMethod(hexEnc);\n" +
                    "message;");
            System.out.println("result = " + result);

        } catch (Exception e) {

        }
    }
}
