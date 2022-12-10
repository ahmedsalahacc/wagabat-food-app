package com.ahmedsalah.wagabat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SignUpFragment extends Fragment{

    View view;
    Button googleBtn, signUpBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        // elements
        googleBtn = view.findViewById(R.id.registerWithGoogle);
        signUpBtn = view.findViewById(R.id.btnSignUp);

        // event listeners

        return view;
    }

    public void replaceFragment(Fragment newFragment){
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, newFragment).commit();
    }
}