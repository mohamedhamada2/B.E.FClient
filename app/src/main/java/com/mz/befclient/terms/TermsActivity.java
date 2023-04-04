package com.mz.befclient.terms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mz.befclient.R;
import com.mz.befclient.about.AboutActivity;
import com.mz.befclient.about.Info;
import com.mz.befclient.databinding.ActivityTermsBinding;
import com.mz.befclient.main.MainActivity;

public class TermsActivity extends AppCompatActivity {
    ActivityTermsBinding activityTermsBinding;
    TermsViewModel termsViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        activityTermsBinding = DataBindingUtil.setContentView(this,R.layout.activity_terms);
        termsViewModel = new TermsViewModel(this);
        activityTermsBinding.setTermsviewmodel(termsViewModel);
        termsViewModel.get_terms();
        activityTermsBinding.backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermsActivity.this, MainActivity.class);
                intent.putExtra("flag",1);
                startActivity(intent);
                finish();
            }
        });
    }

    public void setData(Info info) {
        activityTermsBinding.txtTerms.setText(info.getTermsCondition());
    }
}