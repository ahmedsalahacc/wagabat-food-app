package com.ahmedsalah.wagabat.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.ahmedsalah.wagabat.R;

public class SignUpFragment extends Fragment{

    Button btnSignUp;
    ImageView googleImg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        // elements
        googleImg = view.findViewById(R.id.img_registerWithGoogle);
        btnSignUp = view.findViewById(R.id.btnSignUp);

        // event listeners
        googleImg.setOnClickListener(v->{
            Toast.makeText(view.getContext(), "reg with google", Toast.LENGTH_SHORT).show();
        });
        btnSignUp.setOnClickListener(v->{
            Toast.makeText(view.getContext(), "signing up", Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    public void replaceFragment(Fragment newFragment){
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, newFragment).commit();
    }
}