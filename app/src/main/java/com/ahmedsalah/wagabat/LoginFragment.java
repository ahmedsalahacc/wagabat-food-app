package com.ahmedsalah.wagabat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class LoginFragment extends Fragment {

    View view;
    Button signInBtn, signUpBtn, loginWithGoogleBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_login, container, false);

        //elements
        Log.i("mmsg", "here0");
        signInBtn = view.findViewById(R.id.signInBtn);
        Log.i("mmsg", "here1");
        signUpBtn = view.findViewById(R.id.goToSignupBtn);
        Log.i("mmsg", "here2");
        loginWithGoogleBtn = view.findViewById(R.id.loginWithGoogle);
        Log.i("mmsg", "here3");

        // event listeners
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new SignUpFragment());
            }
        });
        return view;
    }

    public void replaceFragment(Fragment newFragment){
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, newFragment).addToBackStack(null).commit();
    }
}