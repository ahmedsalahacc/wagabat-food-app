package com.ahmedsalah.wagabat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ResturantActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DishAdapter adapter;
    LinearLayoutManager layoutManager;
    List<DishModel> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);
        init();
    }

    public void init(){
        setUserList();
        Log.i("mytag", "1");
        recyclerView = findViewById(R.id.dishes_recycler_view);
        Log.i("mytag", "2");
        layoutManager = new LinearLayoutManager(this);
        Log.i("mytag", "3");
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        Log.i("mytag", "4");
        recyclerView.setLayoutManager(layoutManager);
        Log.i("mytag", "5");
        adapter = new DishAdapter(userList);
        Log.i("mytag", "6");
        recyclerView.setAdapter(adapter);
        Log.i("mytag", "77");
        adapter.notifyDataSetChanged();
        Log.i("mytag", "8");
    }

    public void setUserList(){
        userList = new ArrayList<>();
        userList.add(new DishModel("Sandwitch", "#", getResources().getString(R.string.dish_description), 200f));
        userList.add(new DishModel("Pizza", "#", getResources().getString(R.string.dish_description), 100f));
        userList.add(new DishModel("Koshary", "#", getResources().getString(R.string.dish_description), 50f));
        userList.add(new DishModel("Spagetti", "#", getResources().getString(R.string.dish_description), 20f));
        userList.add(new DishModel("Crepe", "#", getResources().getString(R.string.dish_description), 40f));
        userList.add(new DishModel("Nutella Waffle", "#", getResources().getString(R.string.dish_description), 60f));
        userList.add(new DishModel("Cinnabon", "#", getResources().getString(R.string.dish_description), 80f));
        userList.add(new DishModel("GODZILLA", "#", getResources().getString(R.string.dish_description), 800f));
    }
}