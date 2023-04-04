package com.mz.befclient.profile;

import android.content.Context;

public class ProfileViewModel {
    Context context;
    ProfileFragment profileFragment;

    public ProfileViewModel(Context context, ProfileFragment profileFragment) {
        this.context = context;
        this.profileFragment = profileFragment;
    }
}
