package com.examplefourthjuly.guessthenumber;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashActivity extends AppCompatActivity {
    private TextView txtview;
    private ImageView imgview;

    private Animation imgAnimation, txtAnimation;
    private static final int SPLASH_DURATION = 5000; // 5 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        txtview = findViewById(R.id.textView);
        imgview = findViewById(R.id.imageView);

        // Load animations with try-catch to prevent crashes
        try {
            imgAnimation = AnimationUtils.loadAnimation(this, R.anim.image_anim);
            txtAnimation = AnimationUtils.loadAnimation(this, R.anim.text_anim);

            imgview.startAnimation(imgAnimation);
            txtview.startAnimation(txtAnimation);
        } catch (Exception e) {
            e.printStackTrace();
        }

        txtview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SplashActivity.this,MainActivity.class);
            }
        });

        // Apply insets only if view exists
        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }
    }
}
