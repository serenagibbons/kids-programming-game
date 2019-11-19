package com.example.kidsprogramminggame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class GameActivity extends AppCompatActivity {

    private ImageView robot;
    private ImageView move1, move2, move3, move4; // User-created moves by dropping arrows
    private ImageView left, up, right, down;	// Arrow options to drag

    // User-created sequence
    private ImageView[] sequence = {move1, move2, move3, move4};
    //private String[] stringSeq = {"", "", "", ""};
    private ArrayList<String> sequenceList = new ArrayList<>();

    final private static String RIGHT = "right";
    final private static String LEFT = "left";
    final private static String UP = "up";
    final private static String DOWN = "down";

    // Solution sequence
    private String[][] solution = {
            {RIGHT, DOWN, RIGHT, DOWN}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // make activity full screen (hide actionbar)
        if (getSupportActionBar() != null)
            this.getSupportActionBar().hide();

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
                        addToSequence(((ImageView) view).getDrawable(), (ImageView) v);

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
        move1.setImageDrawable(getDrawable(R.drawable.drag_drop_square));
        move2.setImageDrawable(getDrawable(R.drawable.drag_drop_square));
        move3.setImageDrawable(getDrawable(R.drawable.drag_drop_square));
        move4.setImageDrawable(getDrawable(R.drawable.drag_drop_square));

        // clear string sequence list
        sequenceList.clear();

    }

    public void playGame(View view) {
        boolean isCorrectSequence = true;

        //String[] sequenceArray = (String []) sequenceList.toArray();
        //Toast.makeText(this, sequenceArray[0], Toast.LENGTH_LONG).show();

        if (sequenceList.isEmpty()) {
            return;
        }

        for (int i = 0; i < 4; ++i) {
            Toast.makeText(this, sequenceList.get(i), Toast.LENGTH_LONG).show();
            if (!sequenceList.get(i).equals(solution[0][i])) {
                isCorrectSequence = false;
            }
        }

        if (isCorrectSequence) {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate_1);
            robot.startAnimation(animation);
        }
    }

    private void addToSequence(Drawable drawable, ImageView imageView) {
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


        /*
        for (int i = 0; i < 4; ++i) {
            if (imageView.equals(sequence[i])){
                if (drawable.equals(right.getDrawable())) {
                    stringSeq[i] = RIGHT;
                }
                if (drawable.equals(left.getDrawable())) {
                    stringSeq[i] = LEFT;
                }
                if (drawable.equals(down.getDrawable())) {
                    stringSeq[i] = DOWN;
                }
                if (drawable.equals(up.getDrawable())) {
                    stringSeq[i] = UP;
                }
            }
        }
         */
    }
    /*
    private void createSequence() {
        for (int i = 0; i < 4; ++i) {
            String value = sequence[i].getResources().getResourceEntryName(sequence[i].getId());
            //int drawableId = (Integer) sequence[i].getTag();
            switch (value) {
                case "imageArrowRight":
                    seq[i] = R.drawable.arrow_right;
                    break;
                case "imageArrowLeft":
                    seq[i] = R.drawable.arrow_left;
                    break;
                case "imageArrowDown":
                    seq[i] = R.drawable.arrow_down;
                    break;
                case "imageArrowUp":
                    seq[i] = R.drawable.arrow_up;
                    break;
            }
        }
    }*/
}

