package com.android.covidqa;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;


public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler(Looper.getMainLooper()).postDelayed(()->{
            startActivity(new Intent(this,MainActivity.class));
            this.finish();
        },1000);
    }
}
