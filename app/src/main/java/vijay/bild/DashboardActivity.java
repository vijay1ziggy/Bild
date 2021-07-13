package vijay.bild;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import org.jetbrains.annotations.NotNull;

import vijay.bild.fragments.Home.HomeFragment;
import vijay.bild.fragments.Notification.NotificationFragment;
import vijay.bild.fragments.Post.PostActivity;
import vijay.bild.fragments.Profile.ProfileFragment;
import vijay.bild.fragments.Search.SearchFragment;

public class DashboardActivity extends AppCompatActivity {
    private FirebaseRemoteConfig remoteConfig;
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //remote config
/*        TextView textView = findViewById(R.id.remoteConfig);
        TextView blocker = findViewById(R.id.blocker);
        textView.setText("The Application Your Trying to Access is Been Blocked By Admin");
        remoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings remoteConfigSettings = new FirebaseRemoteConfigSettings.Builder()
                .setFetchTimeoutInSeconds(4)
                .build();
        remoteConfig.setConfigSettingsAsync(remoteConfigSettings);

        remoteConfig.fetchAndActivate().addOnCompleteListener(new OnCompleteListener<Boolean>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Boolean> task) {
                final String test = remoteConfig.getString("test");
                if (blocker.getText().toString().equals("test"))
                            showDialog(test);
            }

            private void showDialog(String test) {
                final AlertDialog dialog = new AlertDialog.Builder(getWindow().getContext())
                        .setTitle("BLOCKED")
                        .setMessage("The Application Your Trying to Access is Been Blocked By Admin \n Try again later")
                        .show();
                dialog.setCancelable(false);
            }


        });*/




        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        navigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new HomeFragment()).commit();
        navigationView.setSelectedItemId(R.id.nav_home);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                FragmentActivity fragmentActivity = null;
                switch (item.getItemId()){

                    case R.id.nav_home:
                        fragment = new HomeFragment();
                        break;

                    case R.id.nav_search:
                        fragment = new SearchFragment();
                        break;

                    case R.id.nav_post:
                        fragment = null;
                        Intent intent = new Intent(DashboardActivity.this, PostActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.nav_notify:
                        fragment = new NotificationFragment();
                        break;

                    case R.id.nav_profile:
                        fragment = new ProfileFragment();
                        break;
                }
                if(fragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.body_container, fragment).commit();
                }
                return true;
            }
        });


    }
}