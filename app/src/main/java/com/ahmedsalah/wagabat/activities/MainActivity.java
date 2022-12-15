package com.ahmedsalah.wagabat.activities;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.ahmedsalah.wagabat.R;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragMan;
    FragmentTransaction fragTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragMan = getSupportFragmentManager();
        fragTransaction = fragMan.beginTransaction();
    }

} 