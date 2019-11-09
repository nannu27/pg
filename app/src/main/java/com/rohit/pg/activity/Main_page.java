package com.rohit.pg.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.rohit.pg.R;

public class Main_page extends AppCompatActivity {
ImageView key;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        key=findViewById(R.id.key);
        Animation animation1 =
                AnimationUtils.loadAnimation(getApplicationContext(), R.anim.roatate);
        key.startAnimation(animation1);
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(Main_page.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }
}
