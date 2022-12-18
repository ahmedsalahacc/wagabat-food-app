package com.ahmedsalah.wagabat.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ahmedsalah.wagabat.R;
import com.ahmedsalah.wagabat.activities.MainMenuActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpFragment extends Fragment{

    Button btnSignUp;
    ImageView googleImg;
    EditText nameField, emailField, passwordField, mobileField, rePasswordField;
    String emailRegexPattern = "[a-zA-Z0-9]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    DatabaseReference dbRef;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        initComponents(view);
        // event listeners
        googleImg.setOnClickListener(v->{
            Toast.makeText(view.getContext(), "reg with google", Toast.LENGTH_SHORT).show();
        });
        btnSignUp.setOnClickListener(v->{
//            Toast.makeText(view.getContext(), "signing up", Toast.LENGTH_SHORT).show();
            performAuth(view);
        });

        return view;
    }

    private void initComponents(View view){
        // components
        nameField = view.findViewById(R.id.textName);
        emailField = view.findViewById(R.id.textEmail);
        mobileField = view.findViewById(R.id.textMobile);
        passwordField = view.findViewById(R.id.textPassword);
        rePasswordField = view.findViewById(R.id.textRepassword);
        btnSignUp = view.findViewById(R.id.btnSignUp);
        googleImg = view.findViewById(R.id.img_registerWithGoogle);
        progressDialog = new ProgressDialog(view.getContext());
        // firebase objects
        auth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();
    }

    private void replaceFragment(Fragment newFragment) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, newFragment).commit();
    }

    private void replaceActivity(View view, Class activityClass){
        Intent intent = new Intent(view.getContext(), activityClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(intent);
    }

    private void performAuth(View view){
        String name = nameField.getText().toString();
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();
        String rePassword = rePasswordField.getText().toString();
        String mobile = mobileField.getText().toString();

        boolean isError = false;

        if (!email.matches(emailRegexPattern)){
            isError=true;
            emailField.setError("Email Pattern is incorrect");
        }
        if(password.isEmpty()||password.length()<8){
            isError=true;
            passwordField.setError("Password length is less than 8 characters");
        }
        if(!password.equals(rePassword)){
            isError=true;
            rePasswordField.setError("This doesn't match the input password");
        }

        if(!isError){
            progressDialog.setMessage("Wait unitl registration is complete");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            // creating email and password with firebase
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task->{
                if(task.isSuccessful()){
                    user = auth.getCurrentUser();
                    addUserToFirebaseDB(user.getUid(), email, name, mobile);
                    progressDialog.dismiss();
                    Toast.makeText(view.getContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                    replaceActivity(view, MainMenuActivity.class);
                } else{
                    progressDialog.dismiss();
                    Log.d("debugreg", ""+task.getException());
                    Toast.makeText(view.getContext(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void addUserToFirebaseDB(String uniqueUUID, String email, String name, String mobile){
        DatabaseReference userRef = dbRef.child("users").child(uniqueUUID);
        userRef.child("name").setValue(name);
        userRef.child("mobile").setValue(mobile);
        userRef.child("email").setValue(email);
    }

}