package com.example.ruben.blue;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


import org.w3c.dom.Text;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class SplashScreen extends AppCompatActivity {
    ImageView imageView;
    TextView tvLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        imageView = (ImageView) findViewById(R.id.imageView);
        tvLogo = (TextView) findViewById(R.id.tvLogo);
        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.grow_disappear_animation);
        Animation myanim2= AnimationUtils.loadAnimation(this, R.anim.animlogo);

        imageView.startAnimation(myanim);
        tvLogo.startAnimation(myanim2);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent= new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },5000);
    }
}
