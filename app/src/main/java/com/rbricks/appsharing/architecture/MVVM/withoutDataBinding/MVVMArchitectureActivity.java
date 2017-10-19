package com.rbricks.appsharing.architecture.MVVM.withoutDataBinding;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.rbricks.appsharing.BaseActivity;
import com.rbricks.appsharing.R;
import com.rbricks.appsharing.utils.CommonUtils;


public class MVVMArchitectureActivity extends BaseActivity {
    private EditText phoneEt; // Login Check Application
    private EditText emailEt;
    private Button submitBtn;
    // If both email & mobile are valid then Submit button is enabled ( should happen dynamically with each letter typed )

    /* Model shouldn't know about ViewModel
    * ViewModel shouldnot know about the View
    * Only Views KNOWS ViewModel observable n act on it.
    * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvvmarchitecture);

        initViews();
    }

    private void initViews() {
        phoneEt = (EditText) findViewById(R.id.mvvm_phone_et);
        emailEt = (EditText) findViewById(R.id.mvvm_email_et);
        submitBtn = ((Button) findViewById(R.id.mvvm_submit_btn));
        RxView.clicks(submitBtn).subscribe(s -> {
            CommonUtils.showToast("Submit button clicked");
        });

        initLogic();

    }

    private void initLogic() {
        MVVMViewModel viewModel = new MVVMViewModel();
        viewModel.setEmail(RxTextView.textChanges(emailEt).map(CharSequence::toString));
        viewModel.setPhone(RxTextView.textChanges(phoneEt).map(CharSequence::toString));

        viewModel.getResultObservable().subscribe(s -> {
            submitBtn.setEnabled(s);
        });

    }


}
