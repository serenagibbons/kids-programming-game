package com.example.kidsprogramminggame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomepageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
    }

    public void signOut(View view) {
        Intent i = new Intent(this, SplashActivity.class);
        i.putExtra("Initial Screen", false);
        startActivity(i);
    }
}
