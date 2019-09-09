package com.example.tapapp;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements ContactsFragment.OnFragmentInteractionListener,
        HistoryFragment.OnFragmentInteractionListener,
        HomeFragment.OnFragmentInteractionListener,
        ProfileFragment.OnFragmentInteractionListener,
        EditProfileFragment.OnFragmentInteractionListener{


   private FragmentManager fragmentManager;








    HomeFragment homeFragment = new HomeFragment();
    ProfileFragment profileFragment  = new ProfileFragment();
    HistoryFragment historyFragment = new HistoryFragment();
    ContactsFragment contactsFragment = new ContactsFragment();
    EditProfileFragment editProfileFragment = new EditProfileFragment();









    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    ft.replace(R.id.fragment_container,homeFragment);
                    ft.commit();
                    return true;


                case R.id.navigation_history:
                    ft.replace(R.id.fragment_container,historyFragment);
                    ft.commit();

                    return true;


                case R.id.navigation_contacts:
                    ft.replace(R.id.fragment_container,contactsFragment);
                    ft.commit();

                    return true;


                case R.id.navigation_profile:


                    Bundle bundle = new Bundle();
                    profileFragment.setArguments(bundle);
                    ft.replace(R.id.fragment_container,profileFragment);
                    ft.commit();

                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.bottomNavigationView);





        fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container,homeFragment);
        ft.commit();

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }






    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    public void open_alarm_settings_activity(View view) {

        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, view, "transition");
        int revealX = (int) view.getX();
        int revealY = (int) view.getY();

        Intent intent = new Intent(this, AlarmSettingsActivity.class);
        intent.putExtra(AlarmSettingsActivity.EXTRA_CIRCULAR_REVEAL_X, revealX);
        intent.putExtra(AlarmSettingsActivity.EXTRA_CIRCULAR_REVEAL_Y, revealY);

        ActivityCompat.startActivity(this, intent, options.toBundle());
    }


    @Override
    public void onBackPressed() {

        final ConstraintLayout main_activity_constraint_layout = findViewById(R.id.main_activity_constraint_layout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int cx = main_activity_constraint_layout.getRight();
            int cy = main_activity_constraint_layout.getBottom();
            float finalRadius = Math.max(main_activity_constraint_layout.getWidth(), main_activity_constraint_layout.getHeight());
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(main_activity_constraint_layout, cx, cy, finalRadius, 0);

            circularReveal.addListener(new Animator.AnimatorListener() {


                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    main_activity_constraint_layout.setVisibility(View.INVISIBLE);
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
        }else{
            super.onBackPressed();
        }
    }

    public void editProfile(View view) {

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();

        ft.replace(R.id.fragment_container, editProfileFragment);
        ft.commit();

    }

    public void change_profile_pic(View view) {


        Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        if(i.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(i, 100);
        }



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {


        ImageView profile_pic = findViewById(R.id.Profile_pic);
        if (requestCode == 100 &&
                resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                profile_pic.setImageBitmap(imageBitmap);
            }
        }
    }



}
