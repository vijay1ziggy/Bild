package vijay.bild;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import vijay.bild.fragments.Home.HomeFragment;
import vijay.bild.gallery.GalleryFragment;

public class AllCategoryActivity extends AppCompatActivity {

    private Button animal;
    private Button potrat;
    private Button fine;
    private Button aerial;
    private Button sports;
    private Button fashion;
    private Button events;
    private Button wedding;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_category);

        aerial = findViewById(R.id.aerialbtn);
        animal = findViewById(R.id.animalbtn);
        fine = findViewById(R.id.fineArtBtn);
        sports = findViewById(R.id.sportsbtn);
        fashion = findViewById(R.id.fashionBtn);
        events = findViewById(R.id.eventsBtn);
        wedding = findViewById(R.id.wedding);
        potrat = findViewById(R.id.potratBtn);
        back = findViewById(R.id.backBtn);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        animal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        aerial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        fashion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        fine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        wedding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        potrat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}