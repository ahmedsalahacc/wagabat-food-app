package com.ahmedsalah.wagabat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuActivity extends AppCompatActivity {
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
        replaceFragment(new HomeFragment());
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
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, frag).commit();
    }
}