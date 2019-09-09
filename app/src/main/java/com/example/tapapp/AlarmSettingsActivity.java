package com.example.tapapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class AlarmSettingsActivity extends AppCompatActivity {
    public static final String EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X";
    public static final String EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y";
    ConstraintLayout alarm_settings_activity_constraint_layout;


    private int revealX;
    private int revealY;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_settings);
        alarm_settings_activity_constraint_layout = findViewById(R.id.alarm_settings_activity_constraint_layout);


        //activity.this opening animation
        activity_opening_animation(savedInstanceState);


    }

    private void activity_opening_animation(final Bundle savedInstanceState) {
        final Intent intent = getIntent();
        if (savedInstanceState == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_X) &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_Y)) {
            alarm_settings_activity_constraint_layout.setVisibility(View.INVISIBLE);

            revealX = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_X, 0);
            revealY = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_Y, 0);


            ViewTreeObserver viewTreeObserver = alarm_settings_activity_constraint_layout.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        revealActivity(revealX, revealY);
                        alarm_settings_activity_constraint_layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
            }
        } else {
            alarm_settings_activity_constraint_layout.setVisibility(View.VISIBLE);
        }


    }


    protected void revealActivity(int x, int y) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            float finalRadius = (float) (Math.max(alarm_settings_activity_constraint_layout.getWidth(), alarm_settings_activity_constraint_layout.getHeight()) * 1.1);

            // create the animator for this view (the start radius is zero)
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(alarm_settings_activity_constraint_layout, x, y, 0, finalRadius);
            circularReveal.setDuration(400);
            circularReveal.setInterpolator(new AccelerateInterpolator());

            // make the view visible and start the animation
            alarm_settings_activity_constraint_layout.setVisibility(View.VISIBLE);
            circularReveal.start();
        } else {
            finish();
        }
    }

    protected void unRevealActivity() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            finish();
        } else {
            float finalRadius = (float) (Math.max(alarm_settings_activity_constraint_layout.getWidth(), alarm_settings_activity_constraint_layout.getHeight()) * 1.1);
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(
                    alarm_settings_activity_constraint_layout, revealX, revealY, finalRadius, 0);

            circularReveal.setDuration(400);
            circularReveal.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    alarm_settings_activity_constraint_layout.setVisibility(View.INVISIBLE);
                    finish();
                }
            });


            circularReveal.start();
        }
    }

    @Override
    public void onBackPressed() {
            closingAnimation(alarm_settings_activity_constraint_layout);


    }

    public void set_alarm_limit(View view) {
        Button set_alarm_limit_btn = findViewById(R.id.set_alarm_limit_btn);
        EditText set_alarm_limit_text;

        set_alarm_limit_text = findViewById(R.id.set_alarm_limit_text);


        try {
            int set_alarm_limit = Integer.parseInt(set_alarm_limit_text.getText().toString());

            Toast.makeText(this, " Alarm set ", Toast.LENGTH_LONG).show();



        } catch (Exception e) {

            Toast.makeText(this, "Please enter the duration", Toast.LENGTH_LONG).show();

        }



        closingAnimation(set_alarm_limit_btn);


    }


    public void disable_alarm(View view) {

        ImageView disable_alarm_btn = findViewById(R.id.disable_alarm_btn);

        closingAnimation(disable_alarm_btn);


    }


    private void closingAnimation(View view) {
        int cx,cy;

        if (view == alarm_settings_activity_constraint_layout)
        {
             cx = (int) view.getRight();
             cy = (int) view.getBottom();

        }
        else {


             cx = (int) (view.getX() + view.getRight()) / 2;
             cy = (int) (view.getY() + view.getBottom()) / 2;
        }

        float finalRadius = Math.max(alarm_settings_activity_constraint_layout.getWidth(), alarm_settings_activity_constraint_layout.getHeight());
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(alarm_settings_activity_constraint_layout, cx, cy, finalRadius, 0);
        circularReveal.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                alarm_settings_activity_constraint_layout.setVisibility(View.INVISIBLE);
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        circularReveal.setDuration(400);
        circularReveal.start();

    }
}

































