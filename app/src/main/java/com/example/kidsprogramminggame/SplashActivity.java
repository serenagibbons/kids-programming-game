package com.example.kidsprogramminggame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class SplashActivity extends AppCompatActivity {

    Button signUp, signInChild, signInParent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        signUp = findViewById(R.id.btnSignUp);
        signInChild = findViewById(R.id.btnChildSign);
        signInParent = findViewById(R.id.btnParentSign);
        final Animation fadeIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);

        // show splash screen for 5 seconds before switching to LoginActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                signUp.startAnimation(fadeIn);
                signInChild.startAnimation(fadeIn);
                signInParent.startAnimation(fadeIn);

                signUp.setVisibility(View.VISIBLE);
                signInChild.setVisibility(View.VISIBLE);
                signInParent.setVisibility(View.VISIBLE);
            }
        }, 5000);
    }

    public void goToSignUp(View view) {
        startActivity(new Intent(this, SignUpActivity.class));
    }
}
