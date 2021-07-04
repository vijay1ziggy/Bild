package vijay.bild;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import static android.content.ContentValues.TAG;

public class ForgotPassword extends DialogFragment {
    private EditText email;
    private LayoutInflater inflate;
    private ViewGroup container;
    private FirebaseAuth mAuth;
    private FragmentActivity activity;
    private Context context;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        mAuth = FirebaseAuth.getInstance();
        activity = getActivity();
        context =  getContext();
        View view = inflate.from(getContext()).inflate(R.layout.forgot_pass_dialog, null);
        email = view.findViewById(R.id.eamilid);
        builder.setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String mail = email.getText().toString();
                        if (mail.isEmpty()) {
                            Toast.makeText(getActivity(), "Please enter the Email Address", Toast.LENGTH_SHORT).show();
                        } else {
                            mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    if(getActivity() != null){
                                        //this is an error code but it is working check it later
                                        Toast.makeText(getActivity(),"link has sent",Toast.LENGTH_SHORT).show();
                                    }else {
                                        Log.d("getActivity", "onSuccess: get activity is null this is an error try to check it");
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    //this is an error code but it is working check it later
                                    if (getActivity() != null){

                                        Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
                                    }else{
                                        Log.d(TAG, "onFailure: get activity is null this is an error try to check it ");
                                    }

                                }
                            });
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "try Again..!", Toast.LENGTH_SHORT).show();
                    }
                });

        return builder.create();

    }
}
