package com.example.kidsprogramminggame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Path;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

public class GameActivity extends AppCompatActivity {

    private ImageView robot;
    private ImageView move1, move2, move3, move4; // User-created moves by dropping arrows
    private ImageView left, up, right, down;	// Arrow options to drag

    // User-created sequence
    //private ImageView[] sequence = {move1, move2, move3, move4};

    // Solution sequence
    private Integer[][] solution = {
            {R.drawable.arrow_right, R.drawable.arrow_down, R.drawable.arrow_right, R.drawable.arrow_down}
    };

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

    public void resetSequence(View view) {
        move1.setImageDrawable(getDrawable(R.drawable.drag_drop_square));
        move2.setImageDrawable(getDrawable(R.drawable.drag_drop_square));
        move3.setImageDrawable(getDrawable(R.drawable.drag_drop_square));
        move4.setImageDrawable(getDrawable(R.drawable.drag_drop_square));

        /*for (int i = 0; i < 4; ++i) {
            sequence[i] = null;
        }*/
    }

    public void playGame(View view) {
        /*TranslateAnimation animation = new TranslateAnimation(0, 100, 0, 0);
        animation.setDuration(1000);
        animation.setFillAfter(true);
        //animation.setAnimationListener(new MyAnimationListener());
        */

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate);
        robot.startAnimation(animation);

    }
}

