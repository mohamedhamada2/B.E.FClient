package com.mz.befclient.about;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mz.befclient.R;
import com.mz.befclient.databinding.ActivityAboutBinding;
import com.mz.befclient.main.MainActivity;
import com.mz.befclient.orders.OrdersActivity;

public class AboutActivity extends AppCompatActivity {
    ActivityAboutBinding activityAboutBinding;
    AboutViewModel aboutViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        activityAboutBinding = DataBindingUtil.setContentView(this,R.layout.activity_about);
        aboutViewModel = new AboutViewModel(this);
        activityAboutBinding.setAboutviewmodel(aboutViewModel);
        aboutViewModel.get_about();
        activityAboutBinding.backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this, MainActivity.class);
                intent.putExtra("flag",1);
                startActivity(intent);
                finish();
            }
        });
    }

    public void setData(Info info) {
        activityAboutBinding.txtAbout.setText(info.getAbout());
    }
}