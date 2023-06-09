package com.mz.befclient.signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.mz.befclient.R;
import com.mz.befclient.databinding.ActivityVerificationCodeBinding;
import com.mz.befclient.forgetpassword.NewPasswordActivity;

import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;

public class VerificationCodeActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    ActivityVerificationCodeBinding activityVerificationCodeBinding;
    VerificationCodeViewModel verificationCodeViewModel;
    String name, phone, password, city_id, govern_id,address,shop, mVerificationId;
    Uri filepath;
    Integer flag;
    Double lat,lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code);
        activityVerificationCodeBinding = DataBindingUtil.setContentView(this, R.layout.activity_verification_code);
        verificationCodeViewModel = new VerificationCodeViewModel(this);
        mAuth = FirebaseAuth.getInstance();
        //mAuth.getFirebaseAuthSettings().setAppVerificationDisabledForTesting(true);
        activityVerificationCodeBinding.setVerificationcodeviewmodel(verificationCodeViewModel);
        getDataIntent();
    }
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            Toast.makeText(VerificationCodeActivity.this, "vertify", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onVerificationCompleted:" + credential);

            signInWithPhoneAuthCredential(credential);
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            //Toast.makeText(VerificationCodeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.w(TAG, "onVerificationFailed", e);

            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                // Invalid request
            } else if (e instanceof FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
            }

            // Show a message and update the UI
        }

        @Override
        public void onCodeSent(@NonNull String verificationId,
                               @NonNull PhoneAuthProvider.ForceResendingToken token) {
            Toast.makeText(VerificationCodeActivity.this, "send", Toast.LENGTH_SHORT).show();
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Log.d(TAG, "onCodeSent:" + verificationId);

            // Save verification ID and resending token so we can use them later
            mVerificationId = verificationId;
        }
    };

    public void sendVerificationCodeToUser(String phone_no) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phone_no)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

   /* public void ReadSms() {

        Cursor cursor = getActivity().getContentResolver().query(Uri.parse("content://sms"), null, null,null,null);
        cursor.moveToFirst();
        String sms = cursor.getString(12);
        String[] separated = sms.split(" ");
        String code = separated[6].trim();
        for(char c : code.toCharArray()) {
            if (c == '.') {
                code = code.substring(code.length() - 1);
            }
        }
        fragmentVerificationCodeBinding.pinView.setText(code);
        //fragmentVerificationCodeBinding.pinView.setText(code);
    }*/

    private void verifycode(String code) {
        activityVerificationCodeBinding.pinView.setText(code);
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId,code);
        signInWithPhoneAuthCredential(credential);
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(VerificationCodeActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if(flag == 1){
                                verificationCodeViewModel.signup(name,govern_id,city_id,shop,phone,address,lat,lon,password);
                            }else if (flag == 3){
                                Intent intent = new Intent(VerificationCodeActivity.this, NewPasswordActivity.class);
                                intent.putExtra("phone",phone);
                                startActivity(intent);
                            }
                            // Sign in success, update UI with the signed-in user's information

                            //Toast.makeText(getActivity(), "verification success", Toast.LENGTH_SHORT).show();
                            // Update UI
                        } else {
                            Toast.makeText(VerificationCodeActivity.this, "الكود الذي ادخلته غير صحيح", Toast.LENGTH_SHORT).show();
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }
    private void getDataIntent() {
        flag = getIntent().getIntExtra("flag",0);
        if (flag == 1){
            name = getIntent().getStringExtra("name");
            shop = getIntent().getStringExtra("shop");
            phone = getIntent().getStringExtra("mobile");
            password = getIntent().getStringExtra("password");
            city_id = getIntent().getStringExtra("city_id");
            govern_id = getIntent().getStringExtra("govern_id");
            address = getIntent().getStringExtra("address");
            activityVerificationCodeBinding.txtPhone.setText("+2"+phone);
            sendVerificationCodeToUser("+2"+phone);
        }else if (flag == 3){
            phone = getIntent().getStringExtra("phone");
            activityVerificationCodeBinding.txtPhone.setText("+2"+phone);
            sendVerificationCodeToUser("+2"+phone);
            //verificationCodeViewModel.check_phone(phone);

        }


        //filepath = Uri.parse(getIntent().getStringExtra("filepath"));

        //verificationCodeViewModel.check_phone(phone);
       /* }else if (flag == 2){
            first_name = getIntent().getStringExtra("first_name");
            last_name = getIntent().getStringExtra("last_name");
            phone = getIntent().getStringExtra("phone");
            password = getIntent().getStringExtra("password");
            city_id = getIntent().getStringExtra("city_id");
            gender_id = getIntent().getStringExtra("gender_id");
            activityVerificationCodeBinding.txtPhone.setText("+2"+phone);
            verificationCodeViewModel.check_phone(phone);
        }else if (flag == 3){
            phone = getIntent().getStringExtra("phone");
            activityVerificationCodeBinding.txtPhone.setText("+2"+phone);
            //verificationCodeViewModel.check_phone(phone);
            sendVerificationCodeToUser("+2"+phone);


        }*/
        activityVerificationCodeBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = activityVerificationCodeBinding.pinView.getText().toString();
                if (!code.isEmpty()){
                    verifycode(code);
                }else{
                    if (flag == 1 ||flag == 2){
                        activityVerificationCodeBinding.pinView.setError("ادخل الكود المرسل حتي يتم تفعيل حسابك");
                    }else {
                        activityVerificationCodeBinding.pinView.setError("ادخل الكود المرسل حتي يتم تعديل كلمة المرور");
                    }
                }
            }
        });
    }
}