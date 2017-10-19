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
        submitBtn = ((Button) findViewById(R.id.mvvm_submit_btn));
        RxView.clicks(submitBtn).subscribe(s -> {
            CommonUtils.showToast("Submit button clicked");
        });
    }

    @Override
    protected void onDestroy() {
        binding.executePendingBindings();
        binding.unbind();
        binding = null;
        super.onDestroy();
    }


}
