package vijay.bild;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.api.LogDescriptor;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import org.jetbrains.annotations.NotNull;

import static android.content.ContentValues.TAG;
import static android.text.TextUtils.isEmpty;
import static vijay.bild.util.Check.doStringsMatch;
import java.util.HashMap;

import vijay.bild.model.User;

public class RegisterActivity extends AppCompatActivity {
    private TextView login;
    private EditText email;
    private EditText password;
    private EditText FullName;
    private EditText UserName;
    private ImageView reg;
    private ProgressBar progressBar;
    private FirebaseFirestore mDb;
    private DatabaseReference mRootRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mDb = FirebaseFirestore.getInstance();
        login = findViewById(R.id.loginpage);
        email = findViewById(R.id.emailReg);
        password = findViewById(R.id.passwordreg);
        FullName = findViewById(R.id.Fullnamereg);
        UserName = findViewById(R.id.usernamereg);
        reg = findViewById(R.id.regBtn);
        progressBar = findViewById(R.id.progrss);
        mRootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        //change activity to login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        //register Button
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtUsername = UserName.getText().toString();
                String txtName = FullName.getText().toString();
                String txtEmail = email.getText().toString();
                String txtPass = password.getText().toString();

                if(TextUtils.isEmpty(txtUsername) || TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtName) || TextUtils.isEmpty(txtPass)){
                    Toast.makeText(RegisterActivity.this, "Empty credentials", Toast.LENGTH_SHORT).show();
                }
                else if (txtPass.length()<6){
                    Toast.makeText(RegisterActivity.this, "Password is Too Short", Toast.LENGTH_SHORT).show();
                } else{
                    RegisterUser(txtEmail , txtName , txtUsername, txtPass);
                }
            }

            private void RegisterUser(String email, String name, String username, String pass) {
                progressBar.setVisibility(View.VISIBLE);



                mAuth.createUserWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {



                        HashMap<String,Object>map=new HashMap<>();

                        map.put("id",mAuth.getCurrentUser().getUid());
                        map.put("name",name);
                        map.put("email",email);
                        map.put("pass",pass);
                        map.put("Username",username);
                        map.put("bio","hey there!, i'm on Bild");
                        map.put("imageurl","default");


                        User user = new User();
                        user.setEmail(email);
                        user.setUsername(username);
                        user.setName(name);
                        user.setBio("hey there!, i'm on Bild");
                        user.setId(FirebaseAuth.getInstance().getUid());

                        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                                .build();
                        mDb.setFirestoreSettings(settings);

                        DocumentReference newUserRef = mDb
                                .collection(getString(R.string.collection_users))
                                .document(FirebaseAuth.getInstance().getUid());

                        newUserRef.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                Log.d(TAG, "onComplete: set to the firebase store");
                            }
                        });


                        mRootRef.child("Users").child(mAuth.getCurrentUser().getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    progressBar.setVisibility(View.GONE);
                                    mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()) {
                                                Toast.makeText(RegisterActivity.this, "Register successfully. Please check your mail for verification", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(intent);
                                                finish();
                                            }
                                            else{
                                                Toast.makeText(RegisterActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                }
                            }
                        });


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}






