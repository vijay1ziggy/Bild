package vijay.bild;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import vijay.bild.model.User;

import static android.content.ContentValues.TAG;

public class LoginActivity extends AppCompatActivity {

    TextView register;
    EditText email;
    EditText pass;
    ImageView login;
    TextView forgotpass;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        register = findViewById(R.id.registerpage);
        email = findViewById(R.id.emaillog);
        pass = findViewById(R.id.passwordlog);
        login = findViewById(R.id.loginbtn);
        forgotpass = findViewById(R.id.forgotp);


        //change activity to register
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        //forgot password fragment calling
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment forgotpassFragment = new ForgotPassword();
                forgotpassFragment.setCancelable(false);
                forgotpassFragment.show(getSupportFragmentManager().beginTransaction(),"Forgot Password");
               // getSupportFragmentManager().beginTransaction().replace().commit();
            }
        });


        //login activity
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = email.getText().toString();
                String txt_pass = pass.getText().toString();

                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_pass)) {
                    Toast.makeText(LoginActivity.this, "Empty credential", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(txt_email, txt_pass);
                }
            }

        });
    }

            private void loginUser(String email, String pass) {
                mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {



                        if (task.isSuccessful()) {
                            if (mAuth.getCurrentUser().isEmailVerified()) {

                                Toast.makeText(LoginActivity.this, "Update the profile" +
                                        "  for better experience", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                                FirebaseUser user = mAuth.getCurrentUser();
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                                        .build();
                                db.setFirestoreSettings(settings);

                                DocumentReference userRef = db.collection(getString(R.string.collection_users))
                                        .document(user.getUid());
                                userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()){
                                            Log.d(TAG, "onComplete: successfully set the user client.");
                                            User user = task.getResult().toObject(User.class);
                                            ((UserClient)(getApplicationContext())).setUser(user);
                                        }
                                    }
                                });
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "Please verify your email address", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

            }
        }

