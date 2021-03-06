package com.yourcloud.yourcloud.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.kii.cloud.storage.KiiUser;
import com.kii.cloud.storage.callback.KiiUserCallBack;
import com.kii.cloud.storage.exception.app.ConflictException;
import com.yourcloud.yourcloud.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignEmailActivity extends AppCompatActivity {

    public static String emailAddress;
    public static String userName;
    public static String password;

    @BindView(R.id.btn_signup)
    Button mSignUp;
    @BindView(R.id.tv_useremail)
    TextView mEmail;
    @BindView(R.id.tv_username)
    TextView mUserName;
    @BindView(R.id.tv_password)
    TextView mPassword;
    @BindView(R.id.container)
    CoordinatorLayout mCoordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_email);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.btn_signup)
    public void doSignUp(View view) {
        switch (view.getId()){
            case R.id.btn_signup:
                getUserInfo();
                signUp();
                break;
            default:
                break;
        }
    }

    public void getUserInfo(){
        emailAddress = mEmail.getText().toString();
        userName = mUserName.getText().toString();
        password = mPassword.getText().toString();

    }

    private void signUp() {
        KiiUser user = KiiUser.createWithEmail(userName,emailAddress);

        user.register(new KiiUserCallBack() {
            @Override
            public void onRegisterCompleted(int token, KiiUser user, Exception exception) {
                if (exception != null) {
                    if (exception instanceof ConflictException) {
                        snackText("该用户已注册");
                        return;
                    }
                } else {
                   snackText("注册成功");
                    Intent loginIntent = new Intent(SignEmailActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                }

            }
        }, password);

    }


    private void snackText(String string) {
        Snackbar.make(mCoordinatorLayout, string, Snackbar.LENGTH_SHORT)
                .show();
    }
}
