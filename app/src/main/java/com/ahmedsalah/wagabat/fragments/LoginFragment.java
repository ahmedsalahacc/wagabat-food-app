package com.ahmedsalah.wagabat.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmedsalah.wagabat.R;
import com.ahmedsalah.wagabat.activities.MainMenuActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginFragment extends Fragment {

    View view;
    Button signInBtn;
    TextView signUpTxt;
    ImageView loginWithGoogleImg;
    EditText emailField, passwordField;
    String emailRegexPattern = "[a-zA-Z0-9]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    DatabaseReference dbRef;
    FirebaseAuth auth;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_login, container, false);

        // elements init
        emailField = view.findViewById(R.id.textEmail);
        passwordField = view.findViewById(R.id.textPassword);
        signInBtn = view.findViewById(R.id.btn_signIn);
        signUpTxt = view.findViewById(R.id.txt_goToSignup);
        loginWithGoogleImg = view.findViewById(R.id.img_loginWithGoogle);
        progressDialog = new ProgressDialog(view.getContext());
        // firebase objects
        auth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();
        Context context = getContext();
        sharedPref = context.getSharedPreferences(context.getResources().getString(R.string.shared_pref_name),
                getContext().MODE_PRIVATE);
        editor = sharedPref.edit();

        // event listeners
        signUpTxt.setOnClickListener(v->{
            replaceFragment(new SignUpFragment());
        });
        loginWithGoogleImg.setOnClickListener(v->{
            Toast.makeText(view.getContext(), "login with google", Toast.LENGTH_SHORT).show();
        });
        signInBtn.setOnClickListener(v->{
            signIn();
        });

        return view;
    }

    private void replaceFragment(Fragment newFragment){
        super.getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, newFragment).addToBackStack(null).commit();
    }

    private void replaceActivity(View view, Class activityClass){
        Intent intent = new Intent(view.getContext(), activityClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(intent);
    }

    private void signIn(){
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();
        boolean err = false;

        if(email.isEmpty() || !email.matches(emailRegexPattern)){
            emailField.setError("Input must match the email format");
            err = true;
        }
        if(password.isEmpty()){
            passwordField.setError("Password must not be empty");
            err = true;
        }

        if(err)
            Toast.makeText(view.getContext(), "Correct the highlighted fields",
                    Toast.LENGTH_SHORT).show();
        else{
            progressDialog.setMessage("Logging in, please wait");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task->{
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    FirebaseUser user = auth.getCurrentUser();
                    editor.putString("uid", user.getUid());
                    editor.apply();
                    replaceActivity(view, MainMenuActivity.class);
                }else
                    Toast.makeText(view.getContext(), ""+task.getException().getLocalizedMessage(),
                            Toast.LENGTH_SHORT).show();
            });
        }
    }
}