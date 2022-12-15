package com.ahmedsalah.wagabat.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmedsalah.wagabat.R;
import com.ahmedsalah.wagabat.activities.MainMenuActivity;

public class LoginFragment extends Fragment {

    View view;
    Button signInBtn;
    TextView signUpTxt;
    ImageView loginWithGoogleImg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_login, container, false);

        // elements init
        signInBtn = view.findViewById(R.id.btn_signIn);
        signUpTxt = view.findViewById(R.id.txt_goToSignup);
        loginWithGoogleImg = view.findViewById(R.id.img_loginWithGoogle);

        // event listeners
        signUpTxt.setOnClickListener(v->{
            replaceFragment(new SignUpFragment());
        });
        loginWithGoogleImg.setOnClickListener(v->{
            Toast.makeText(view.getContext(), "login with google", Toast.LENGTH_SHORT).show();
        });
        signInBtn.setOnClickListener(v->{
            Toast.makeText(view.getContext(), "login", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(view.getContext(), MainMenuActivity.class));
        });

        return view;
    }

    public void replaceFragment(Fragment newFragment){
        super.getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, newFragment).addToBackStack(null).commit();
    }
}