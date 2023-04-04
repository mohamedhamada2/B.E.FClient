package com.mz.befclient.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mz.befclient.R;
import com.mz.befclient.about.AboutActivity;
import com.mz.befclient.basket.BasketActivity;
import com.mz.befclient.contactus.ContactUsActivity;
import com.mz.befclient.data.MySharedPreference;
import com.mz.befclient.databinding.FragmentProfileBinding;
import com.mz.befclient.editpassword.EditPasswordActivity;
import com.mz.befclient.editprofile.EditProfileActivity;
import com.mz.befclient.login.LoginActivity;
import com.mz.befclient.login.UserModel;
import com.mz.befclient.orders.OrdersActivity;
import com.mz.befclient.privacyterms.PrivacyTermsActivity;
import com.mz.befclient.splash.SplashActivity;
import com.mz.befclient.terms.TermsActivity;


public class ProfileFragment extends Fragment {
    FragmentProfileBinding fragmentProfileBinding;
    ProfileViewModel profileViewModel;
    MySharedPreference mySharedPreference;
    UserModel userModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentProfileBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_profile, container, false);
        profileViewModel = new ProfileViewModel(getContext(),this);
        fragmentProfileBinding.setProfileviewmodel(profileViewModel);
        View view = fragmentProfileBinding.getRoot();
        mySharedPreference = MySharedPreference.getInstance();
        userModel = mySharedPreference.Get_UserData(getContext());
        fragmentProfileBinding.relativeBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), BasketActivity.class));
            }
        });
        fragmentProfileBinding.relativeOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userModel != null){
                    startActivity(new Intent(getContext(), OrdersActivity.class));
                }else {
                    Toast.makeText(getActivity(), "برجاء تسجيل الدخول أولا", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
            }
        });
        fragmentProfileBinding.relativeEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userModel != null){
                    startActivity(new Intent(getContext(), EditProfileActivity.class));
                }else {
                    Toast.makeText(getActivity(), "برجاء تسجيل الدخول أولا", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
            }
        });
        fragmentProfileBinding.relativeEditPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userModel != null){
                    startActivity(new Intent(getContext(), EditPasswordActivity.class));
                }else {
                    Toast.makeText(getActivity(), "برجاء تسجيل الدخول أولا", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
            }
        });
        fragmentProfileBinding.terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), TermsActivity.class));
            }
        });
        fragmentProfileBinding.relativeAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AboutActivity.class));
            }
        });
        fragmentProfileBinding.contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ContactUsActivity.class));
            }
        });
        fragmentProfileBinding.privacyTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), PrivacyTermsActivity.class));
            }
        });
        fragmentProfileBinding.relativeLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mySharedPreference.ClearData(getActivity());
                startActivity(new Intent(getActivity(), SplashActivity.class));
                getActivity().finish();
            }
        });
        return view;
    }
}