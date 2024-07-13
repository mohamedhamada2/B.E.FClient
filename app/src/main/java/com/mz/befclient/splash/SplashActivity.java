package com.mz.befclient.splash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.mz.befclient.BuildConfig;
import com.mz.befclient.R;
import com.mz.befclient.Utilities.Utilities;
import com.mz.befclient.api.GetDataService;
import com.mz.befclient.api.RetrofitClientInstance;
import com.mz.befclient.contactus.ContactusModel;
import com.mz.befclient.data.MySharedPreference;
import com.mz.befclient.forceDialog.ForceUpdateDialogFragment;
import com.mz.befclient.login.LoginActivity;
import com.mz.befclient.login.UserModel;
import com.mz.befclient.main.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    ImageView logo_img;
    MySharedPreference mySharedPreference;
    UserModel loginModel;
    String versionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logo_img = findViewById(R.id.logo_img);
        mySharedPreference = MySharedPreference.getInstance();
        loginModel = mySharedPreference.Get_UserData(SplashActivity.this);
        versionName = BuildConfig.VERSION_NAME;
        callForceUpdateApi();
    }

    private void callForceUpdateApi() {
        if (Utilities.isNetworkAvailable(this)) {
            GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<ContactusModel> call = getDataService.updateVersion(versionName);
            call.enqueue(new Callback<ContactusModel>() {
                @Override
                public void onResponse(@NonNull Call<ContactusModel> call, Response<ContactusModel> response) {
                    if (response.isSuccessful()){
                        if(response.body().getSuccess()==0){
                            showForceUpdateDialog(true);
                        }else {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    logo_img.animate().rotationYBy(360f);
                                }
                            }, 1000);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (loginModel != null) {
                                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                        finish();
                                    } else {
                                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                                        finish();
                                    }
                                }
                            }, 2500);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ContactusModel> call, Throwable t) {

                }
            });
        }
    }

    private void showForceUpdateDialog(boolean b) {
        ForceUpdateDialogFragment forceDialog = new ForceUpdateDialogFragment();

// Set dialog to not cancelable when touching outside
        forceDialog.setCancelable(false);

// Begin a transaction to add the dialog fragment to the fragment manager
        FragmentManager fragmentManager = getSupportFragmentManager(); // Replace with your actual FragmentManager instance
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(forceDialog, "forceDialog");
        transaction.commitAllowingStateLoss();
    }
}