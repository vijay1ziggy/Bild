package vijay.bild;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    View first,second,third,forth,fifth,sixth;
    TextView logo;
    private static final int SPLASH_TIME_OUT = 3000;
    //animation
    Animation topAnimation,bottomAnimation,middleAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_anim);
        bottomAnimation = AnimationUtils.loadAnimation(this,R.anim.bottom_anim);
        middleAnimation = AnimationUtils.loadAnimation(this,R.anim.middle_anim);

        //lines and textview

        first = findViewById(R.id.firstline);
        second = findViewById(R.id.secondline);
        third = findViewById(R.id.thirdline);
        forth= findViewById(R.id.forthline);
        fifth = findViewById(R.id.fifthline);
        sixth = findViewById(R.id.sixthline);

        logo = findViewById(R.id.logo);

        first.setAnimation(topAnimation);
        second.setAnimation(topAnimation);
        third.setAnimation(topAnimation);
        forth.setAnimation(topAnimation);
        fifth.setAnimation(topAnimation);
        sixth.setAnimation(topAnimation);

        logo.setAnimation(middleAnimation);

        //Splash screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    Intent intent = new Intent(SplashActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },SPLASH_TIME_OUT);
    }
}