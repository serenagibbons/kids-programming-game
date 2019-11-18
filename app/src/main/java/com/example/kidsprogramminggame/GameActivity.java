package com.example.kidsprogramminggame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

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

    private android.widget.LinearLayout.LayoutParams layoutParams;

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

