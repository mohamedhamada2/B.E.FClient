package com.mz.befclient.privacyterms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mz.befclient.R;
import com.mz.befclient.about.AboutActivity;
import com.mz.befclient.about.Info;
import com.mz.befclient.databinding.ActivityPrivacyTermsBinding;
import com.mz.befclient.main.MainActivity;

public class PrivacyTermsActivity extends AppCompatActivity {
    ActivityPrivacyTermsBinding activityPrivacyTermsBinding;
    PrivacyPolicyViewModel privacyPolicyViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_terms);
        activityPrivacyTermsBinding = DataBindingUtil.setContentView(this,R.layout.activity_privacy_terms);
        privacyPolicyViewModel = new PrivacyPolicyViewModel(this);
        activityPrivacyTermsBinding.setPrivacypolicyviewmodel(privacyPolicyViewModel);
        privacyPolicyViewModel.get_privacyPolicy();
        activityPrivacyTermsBinding.backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrivacyTermsActivity.this, MainActivity.class);
                intent.putExtra("flag",1);
                startActivity(intent);
                finish();
            }
        });
    }

    public void setData(Info info) {
        activityPrivacyTermsBinding.txtTerms.setText(info.getPolicy());
    }
}