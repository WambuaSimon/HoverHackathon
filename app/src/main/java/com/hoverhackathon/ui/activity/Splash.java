package com.hoverhackathon.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.hoverhackathon.MainActivity;
import com.hoverhackathon.R;

public class Splash extends AppCompatActivity {
    Animation anim;
    ImageView imageView;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imageView= findViewById(R.id.imageView2); // Declare an imageView to show the animation.
        text= findViewById(R.id.text);
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in); // Create the animation.
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent  = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(intent);
                finish();

                // HomeActivity.class is the activity to go after showing the splash screen.
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        imageView.startAnimation(anim);
        text.startAnimation(anim);
    }
}
