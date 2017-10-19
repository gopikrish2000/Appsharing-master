package com.rbricks.appsharing.architecture.dataBinding;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jakewharton.rxbinding2.view.RxView;
import com.rbricks.appsharing.BaseActivity;
import com.rbricks.appsharing.R;
import com.rbricks.appsharing.databinding.ActivityDataBindingBinding;

import java.util.Random;

public class DataBindingActivity extends BaseActivity {

    ActivityDataBindingBinding binding;
    private Random random;
    DataViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding);
        viewModel = new DataViewModel();
        viewModel.first.set("initial setting value");
        binding.setObj(viewModel);
        initViews();
    }

    private void initViews() {
        random = new Random();
        add(RxView.clicks(findViewById(R.id.bing_genrate_rand_btn)).subscribe(s -> {
            String numb = random.nextInt(90) + "";
            viewModel.first.set(numb);
        }));
    }

    @Override
    protected void onDestroy() {
        binding.executePendingBindings();
        binding.unbind();
        super.onDestroy();
    }
}
