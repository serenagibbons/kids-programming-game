package com.example.kidsprogramminggame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    private ImageView robot;
    private ImageView move1, move2, move3, move4; // User-created moves by dropping arrows
    private ImageView left, up, right, down;	// Arrow options to drag

    private ArrayList<String> seq = new ArrayList<>();	// User-created sequence
    private ArrayList<String> soln = new ArrayList<>();	// Solution sequence

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // make activity full screen (hide actionbar)
        if(getSupportActionBar()!=null)
            this.getSupportActionBar().hide();

        robot = findViewById(R.id.imgRobot);

        move1 = findViewById(R.id.imageDrop1);
        move2 = findViewById(R.id.imageDrop2);
        move3 = findViewById(R.id.imageDrop3);
        move4 = findViewById(R.id.imageDrop4);
    }

    // exit game, return to homepage
    public void exitGame(View view) {
        startActivity(new Intent(this, HomepageActivity.class));
    }
}
