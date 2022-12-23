package com.ahmedsalah.wagabat.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.ahmedsalah.wagabat.R;
import com.ahmedsalah.wagabat.adapters.DishAdapter;
import com.ahmedsalah.wagabat.models.DishModel;
import com.ahmedsalah.wagabat.models.ResturantModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ResturantActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DishAdapter adapter;
    LinearLayoutManager layoutManager;
    DatabaseReference database;
    List<DishModel> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);
        init();
    }

    public void init(){
        setUserList();
        //recycler view
        recyclerView = findViewById(R.id.dishes_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DishAdapter(userList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        //database
        // database = FirebaseDatabase.getInstance().getReference("");
    }



    public void setUserList(){
        userList = new ArrayList<>();
        userList.add(new DishModel("0","Sandwitch", "#", getResources().getString(R.string.dish_description), 200f));
        userList.add(new DishModel("1","Pizza", "#", getResources().getString(R.string.dish_description), 100f));
        userList.add(new DishModel("2","Koshary", "#", getResources().getString(R.string.dish_description), 50f));
        userList.add(new DishModel("3","Spagetti", "#", getResources().getString(R.string.dish_description), 20f));
        userList.add(new DishModel("4","Crepe", "#", getResources().getString(R.string.dish_description), 40f));
        userList.add(new DishModel("5","Nutella Waffle", "#", getResources().getString(R.string.dish_description), 60f));
        userList.add(new DishModel("6","Cinnabon", "#", getResources().getString(R.string.dish_description), 80f));
        userList.add(new DishModel("7","GODZILLA", "#", getResources().getString(R.string.dish_description), 800f));
    }
}