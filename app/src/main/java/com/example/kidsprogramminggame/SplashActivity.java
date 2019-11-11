package com.example.kidsprogramminggame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {

    boolean initialScreen = true;
    Button signUp, signIn; //signInChild, signInParent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        signUp = findViewById(R.id.btnSignUp);
        signIn = findViewById(R.id.btnSignIn);
        //signInChild = findViewById(R.id.btnChildSign);
        //signInParent = findViewById(R.id.btnParentSign);
        final Animation fadeIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);

        // get extras from previous intents
        Intent i = getIntent();
        Bundle b = i.getExtras();
        if (b != null) {
            initialScreen = (boolean) b.get("Initial Screen");
            if ((b.get("Success") != null) && (boolean) b.get("Success")) {
                // display toast if registration was successful
                Toast.makeText(this, getResources().getString(R.string.toast_success), Toast.LENGTH_LONG).show();
            }
        }

        if (initialScreen) {
            // if opening app
            // show splash screen for 5 seconds before switching to LoginActivity
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    signUp.startAnimation(fadeIn);
                    signIn.startAnimation(fadeIn);

                    signUp.setVisibility(View.VISIBLE);
                    signIn.setVisibility(View.VISIBLE);
                }
            }, 5000);
        }
        else {
            // if moving from another activity to this activity, show buttons immediately
            signUp.setVisibility(View.VISIBLE);
            signIn.setVisibility(View.VISIBLE);

        }
    }

    public void goToSignUp(View view) {
        startActivity(new Intent(this, SignUpActivity.class));
    }

    public void gotToSignIn(View view) {
        startActivity(new Intent(this, SignInActivity.class));

    }
}
