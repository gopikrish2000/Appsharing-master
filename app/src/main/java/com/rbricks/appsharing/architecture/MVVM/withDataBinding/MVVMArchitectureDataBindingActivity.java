package com.rbricks.appsharing.architecture.MVVM.withDataBinding;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding2.view.RxView;
import com.rbricks.appsharing.BaseActivity;
import com.rbricks.appsharing.R;
import com.rbricks.appsharing.databinding.ActivityMvvmarchitectureDatabindingBinding;
import com.rbricks.appsharing.utils.CommonUtils;

public class MVVMArchitectureDataBindingActivity extends BaseActivity {
    private Button submitBtn;
    ActivityMvvmarchitectureDatabindingBinding binding;

    // If both email & mobile are valid then Submit button is enabled ( should happen dynamically with each letter typed )

    /* Model shouldn't know about ViewModel
    * ViewModel shouldnot know about the View
    * Only Views KNOWS ViewModel observable n act on it.
    * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mvvmarchitecture_databinding);
        MVVMDataBindingViewModel viewModel = new MVVMDataBindingViewModel();
        binding.setObj(viewModel);
        initViews();
    }

    private void initViews() {
//        phoneEt = (EditText) findViewById(R.id.mvvm_phone_et);
//        emailEt = (EditText) findViewById(R.id.mvvm_email_et);
        submitBtn = ((Button) findViewById(R.id.mvvm_submit_btn));
        RxView.clicks(submitBtn).subscribe(s -> {
            CommonUtils.showToast("Submit button clicked");
        });

        initLogic();

    }

    private void initLogic() {

//        viewModel.setEmail(RxTextView.textChanges(emailEt).map(CharSequence::toString));
//        viewModel.setPhone(RxTextView.textChanges(phoneEt).map(CharSequence::toString));

//        viewModel.getResultObservable().subscribe(s -> {
//            submitBtn.setEnabled(s);
//        });

    }

    @Override
    protected void onDestroy() {
//        getDefaultBinder().bind(binding, null);
        binding.executePendingBindings();
        binding.unbind();
        binding = null;
        super.onDestroy();
    }


}
