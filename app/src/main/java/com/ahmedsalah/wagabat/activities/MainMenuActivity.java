package com.ahmedsalah.wagabat.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.ahmedsalah.wagabat.R;
import com.ahmedsalah.wagabat.fragments.AccountFragment;
import com.ahmedsalah.wagabat.fragments.HomeFragment;
import com.ahmedsalah.wagabat.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainMenuActivity extends AppCompatActivity {
    BottomNavigationView bottomNavBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        init();
        initEventListeners();
    }

    public void init(){
        bottomNavBar = findViewById(R.id.bottomNavigationView);
        this.replaceFragment(new HomeFragment());
    }

    public void initEventListeners(){
        bottomNavBar.setOnItemSelectedListener(item->{
            switch(item.getItemId()){
                case R.id.mn_home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.mn_search:
                    replaceFragment(new SearchFragment());
                    break;
                case R.id.mn_account:
                    replaceFragment(new AccountFragment());
                    break;
            }
            return true;
        });
    }

    public void replaceFragment(Fragment frag){
        super.getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, frag).commit();
    }
}