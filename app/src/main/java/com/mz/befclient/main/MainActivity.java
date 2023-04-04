package com.mz.befclient.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;

import com.google.firebase.messaging.FirebaseMessaging;
import com.mz.befclient.R;
import com.mz.befclient.categories.CategoriesFragment;
import com.mz.befclient.data.DatabaseClass;
import com.mz.befclient.data.MySharedPreference;
import com.mz.befclient.databinding.ActivityMainBinding;
import com.mz.befclient.home.HomeFragment;
import com.mz.befclient.login.UserModel;
import com.mz.befclient.notifications.NotificationsFragment;
import com.mz.befclient.profile.ProfileFragment;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding activityMainBinding;
    BottomNavigationView homeNavigationView;
    Fragment homefragment ;
    Fragment profileFragment ;
    Fragment categoriesFragment;
    Fragment notificationsFragment;
    FragmentManager fm ;
    Fragment active ;
    Integer flag;
    String firebase_token;
    MainViewModel mainViewModel;
    MySharedPreference mySharedPreference;
    UserModel userModel;
    DatabaseClass databaseClass;
    //Integer basket_size;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        homeNavigationView = activityMainBinding.homeBottomnavigation;
        mainViewModel = new MainViewModel(this);
        activityMainBinding.setMainviewmodel(mainViewModel);
        databaseClass = Room.databaseBuilder(getApplicationContext(), DatabaseClass.class, "basket").allowMainThreadQueries().fallbackToDestructiveMigration().build();
      //  basket_size = databaseClass.getDao().getallproducts().size();
        homefragment = new HomeFragment();
        //myorderfragment = new MyordersFragment();
        notificationsFragment = new NotificationsFragment();
        categoriesFragment = new CategoriesFragment();
        profileFragment = new ProfileFragment();
        getfirebasetoken();
        getDataIntent();

    }

    private void getfirebasetoken() {
        FirebaseMessaging.getInstance().subscribeToTopic("bfe")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = getString(R.string.msg_subscribed);
                        if (!task.isSuccessful()) {
                            msg = getString(R.string.msg_subscribe_failed);
                        }
                        Log.d("TAG", msg);
                        //Toast.makeText(HomeActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
        try {
            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull Task<String> task) {
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                                return;
                            }

                            // Get new FCM registration token
                            firebase_token = task.getResult();
                            Log.e("firebase_token",firebase_token);
                            mainViewModel.update_token(firebase_token);

                            // Log and toast
                            /*String msg = getString(R.string.msg_token_fmt, token);
                            Log.d(TAG, msg);
                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();*/
                        }
                    });
        } catch (Exception e) {
            Log.e("exception_e",e.toString());
            e.printStackTrace();
        }
    }

    private void getDataIntent() {
        flag = getIntent().getIntExtra("flag",0);
        if (flag == 1){
            homeNavigationView.setSelectedItemId(R.id.more);
            //mySharedPreference = MySharedPreference.getInstance();
            active = profileFragment;

            fm = getSupportFragmentManager();
            fm.beginTransaction().add(R.id.fragment_container, profileFragment, "4").show(profileFragment).commit();
            fm.beginTransaction().add(R.id.fragment_container, categoriesFragment, "2").hide(categoriesFragment).commit();
            fm.beginTransaction().add(R.id.fragment_container, notificationsFragment, "3").hide(notificationsFragment).commit();
            //fm.beginTransaction().add(R.id.fragment_container, myorderfragment, "2").hide(myorderfragment).commit();
            fm.beginTransaction().add(R.id.fragment_container, homefragment, "1").hide(homefragment).commit();
            homeNavigationView.setOnNavigationItemSelectedListener(nav_listner);
        }else {
            homeNavigationView.setSelectedItemId(R.id.home);
            //mySharedPreference = MySharedPreference.getInstance();
            active = homefragment;
            fm = getSupportFragmentManager();
            fm.beginTransaction().add(R.id.fragment_container, profileFragment, "4").hide(profileFragment).commit();
            fm.beginTransaction().add(R.id.fragment_container, categoriesFragment, "2").hide(categoriesFragment).commit();
            fm.beginTransaction().add(R.id.fragment_container, notificationsFragment, "3").hide(notificationsFragment).commit();
            //fm.beginTransaction().add(R.id.fragment_container, myorderfragment, "2").hide(myorderfragment).commit();
            fm.beginTransaction().add(R.id.fragment_container, homefragment, "1").show(homefragment).commit();
            homeNavigationView.setOnNavigationItemSelectedListener(nav_listner);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener nav_listner = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.home:
                    fm.beginTransaction().hide(active).replace(R.id.fragment_container,homefragment).show(homefragment).commit();
                    active = homefragment;
                    return true;
                case R.id.categories:
                    fm.beginTransaction().hide(active).replace(R.id.fragment_container,categoriesFragment).show(categoriesFragment).commit();
                    active = categoriesFragment;
                    return true;
                //item.setTitle(resources.getString(R.string.home));
                case R.id.notification:
                    fm.beginTransaction().hide(active).replace(R.id.fragment_container,notificationsFragment).show(notificationsFragment).commit();
                    active = notificationsFragment;
                    return true;
                case R.id.more:
                    fm.beginTransaction().hide(active).replace(R.id.fragment_container,profileFragment).show(profileFragment).commit();
                    active = profileFragment;
                    return true;
            }
            return false;
        }
    };
}