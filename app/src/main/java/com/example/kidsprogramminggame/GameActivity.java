package com.example.kidsprogramminggame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    private int levelNum = 1;
    Animation animation;

    private ConstraintLayout constraintLayout;
    private ImageView robot;
    private ImageView move1, move2, move3, move4; // User-created moves by dropping arrows
    private ImageView left, up, right, down;	// Arrow options to drag

    // User-created sequence
    private ImageView[] sequence = {move1, move2, move3, move4};
    private ArrayList<String> sequenceList = new ArrayList<>();

    final private static String RIGHT = "right";
    final private static String LEFT = "left";
    final private static String UP = "up";
    final private static String DOWN = "down";

    // Solution sequence
    private String[][] solution = {
            {RIGHT, DOWN, RIGHT, DOWN},
            {RIGHT, DOWN, RIGHT, UP}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // make activity full screen (hide actionbar)
        if (getSupportActionBar() != null)
            this.getSupportActionBar().hide();

        constraintLayout = findViewById(R.id.constraintLayout);

        robot = findViewById(R.id.imgRobot);

        move1 = findViewById(R.id.imageDrop1);
        move2 = findViewById(R.id.imageDrop2);
        move3 = findViewById(R.id.imageDrop3);
        move4 = findViewById(R.id.imageDrop4);

        right = findViewById((R.id.imageArrowRight));
        left = findViewById((R.id.imageArrowLeft));
        up = findViewById((R.id.imageArrowUp));
        down = findViewById((R.id.imageArrowDown));


        View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());
                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};

                ClipData dragData = new ClipData(v.getTag().toString(), mimeTypes, item);
                View.DragShadowBuilder myShadow = new View.DragShadowBuilder(v);

                // start drag
                v.startDragAndDrop(dragData, myShadow, null, 0);

                // set view visibility to INVISIBLE when dragging
                v.setVisibility(View.INVISIBLE);
                return true;
            }
        };

        View.OnDragListener onDragListener = new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        // Determines if this View can accept the dragged data
                        return (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN));

                    case DragEvent.ACTION_DRAG_ENTERED:
                        // Invalidate the view to force a redraw in the new tint
                        v.invalidate();
                        return true;

                    case DragEvent.ACTION_DRAG_LOCATION:
                        // Ignore the event

                    case DragEvent.ACTION_DRAG_EXITED:

                        return true;

                    case DragEvent.ACTION_DRAG_ENDED:
                        // Invalidates the view to force a redraw
                        v.invalidate();
                        return true;

                    case DragEvent.ACTION_DROP:
                        // Gets the item containing the dragged data
                        ClipData.Item item = event.getClipData().getItemAt(0);

                        // Invalidates the view to force a redraw
                        v.invalidate();

                        View view = (View) event.getLocalState();

                        ((ImageView) v).setImageDrawable( ((ImageView)view).getDrawable());

                        // add to sequence
                        addToSequence(((ImageView) view).getDrawable());

                        //((ImageView) v).setImageDrawable(((ImageView)event.getLocalState()).getBackground());
                        // v.setBackground(((ImageView)event.getLocalState()).getBackground());

                        view.setVisibility(View.VISIBLE);//finally set Visibility to VISIBLE
                        return true;

                    default:
                        return true;
                }
            }
        };

        View.OnTouchListener onTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);

                    v.startDragAndDrop(data, shadowBuilder, v, 0);
                    return true;
                } else {
                    return false;
                }
            }
        };

        right.setOnDragListener(onDragListener);
        left.setOnDragListener(onDragListener);
        up.setOnDragListener(onDragListener);
        down.setOnDragListener(onDragListener);

        right.setOnLongClickListener(onLongClickListener);
        left.setOnLongClickListener(onLongClickListener);
        up.setOnLongClickListener(onLongClickListener);
        down.setOnLongClickListener(onLongClickListener);

        right.setOnTouchListener(onTouchListener);
        left.setOnTouchListener(onTouchListener);
        up.setOnTouchListener(onTouchListener);
        down.setOnTouchListener(onTouchListener);

        move1.setOnDragListener(onDragListener);
        move2.setOnDragListener(onDragListener);
        move3.setOnDragListener(onDragListener);
        move4.setOnDragListener(onDragListener);
    }

    // exit game, return to homepage
    public void exitGame(View view) {
        startActivity(new Intent(this, HomepageActivity.class));
    }

    public void resetSequence(View view) {
        reset();
    }

    private void reset() {
        move1.setImageDrawable(getDrawable(R.drawable.drag_drop_square));
        move2.setImageDrawable(getDrawable(R.drawable.drag_drop_square));
        move3.setImageDrawable(getDrawable(R.drawable.drag_drop_square));
        move4.setImageDrawable(getDrawable(R.drawable.drag_drop_square));

        sequenceList.clear();
    }

    public void playGame(View view) {
        boolean isCorrectSequence = true;

        if (sequenceList.isEmpty() || sequenceList.size() < 4) {
            reset();
            incorrectAttempt();
            return;
        }


        for (int i = 0; i < 4; ++i) {
            if (!sequenceList.get(i).equals(solution[levelNum - 1][i])) {
                isCorrectSequence = false;
            }
        }

        if (isCorrectSequence) {
            switch (levelNum) {
                case 1:
                    animation = AnimationUtils.loadAnimation(this, R.anim.translate_1);
                    break;
                case 2:
                    animation = AnimationUtils.loadAnimation(this, R.anim.translate_2);
                    break;
            }

            robot.startAnimation(animation);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    levelComplete();
                }
            }, 4000);

        }
        else {
            reset();
            incorrectAttempt();
        }
    }

    // add moves to the user sequence
    private void addToSequence(Drawable drawable) {
        if (drawable.equals(right.getDrawable())) {
            sequenceList.add(RIGHT);
        }
        if (drawable.equals(left.getDrawable())) {
            sequenceList.add(LEFT);
        }
        if (drawable.equals(down.getDrawable())) {
            sequenceList.add(DOWN);
        }
        if (drawable.equals(up.getDrawable())) {
            sequenceList.add(UP);
        }

    }

    // load level
    public void loadLevel(int n) {
        // reset sequence and ImageViews
        reset();

        // update layout
        switch (n) {
            case 1:
                constraintLayout.setBackground(getDrawable(R.drawable.game_background_level1));
                break;
            case 2:
                constraintLayout.setBackground(getDrawable(R.drawable.game_background_level2));
                break;
        }
    }

    public void incorrectAttempt() {
        AlertDialog.Builder adb = new AlertDialog.Builder(GameActivity.this);
        // show user score
        adb.setMessage("Incorrect attempt\n").setCancelable(false)
                //"Your score is " + score + "/" + questions.length + (".\n" +
                //"Your time is " + strTime))
                //.setCancelable(false)
                // allow user to play game again, restart GameActivity
                .setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // repeat level
                        loadLevel(levelNum);
                    }
                })
                // exit game, return to MainActivity
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // exit game
                        startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
                    }
                });
        AlertDialog alert = adb.create();
        alert.show();
    }

    public void levelComplete() {
        AlertDialog.Builder adb = new AlertDialog.Builder(GameActivity.this);
        // show user score
        if (levelNum < 2) {
            adb.setMessage("Level complete!\n").setCancelable(false)
                    //"Your score is " + score + "/" + questions.length + (".\n" +
                    //"Your time is " + strTime))
                    //.setCancelable(false)
                    // allow user to play game again, restart GameActivity
                    .setPositiveButton("Next level", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // go to next level
                            loadLevel(++levelNum);
                        }
                    })
                    // exit game, return to MainActivity
                    .setNegativeButton("Repeat level", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // play level again
                            loadLevel(levelNum);
                        }
                    });
        }
        else {
            adb.setMessage("Game complete!\n").setCancelable(false)
                    //"Your score is " + score + "/" + questions.length + (".\n" +
                    //"Your time is " + strTime))
                    //.setCancelable(false)
                    // allow user to play game again, restart GameActivity
                    .setPositiveButton("Complete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
                        }
                    })
                    // exit game, return to MainActivity
                    .setNegativeButton("Repeat level", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // play level again
                            loadLevel(levelNum);
                        }
                    });
        }
        AlertDialog alert = adb.create();
        alert.show();
    }
}

