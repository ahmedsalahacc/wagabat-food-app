package com.ahmedsalah.wagabat.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ahmedsalah.wagabat.R;

public class IntroFragment extends Fragment {
    View view;
    Button btnSignIn, btnRegister;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_intro, container, false);

        // fragment elements
        btnSignIn = view.findViewById(R.id.goToLogin);
        btnRegister = view.findViewById(R.id.goToSignup);

        // event listeners
        btnSignIn.setOnClickListener(v->{
            replaceFragment(new LoginFragment());
        });
        btnRegister.setOnClickListener(v->{
            replaceFragment(new SignUpFragment());
        });

        return view;
    }

    public void replaceFragment(Fragment newFragment){
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, newFragment).addToBackStack(null).commit();
    }
}