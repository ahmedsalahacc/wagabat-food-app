package com.ahmedsalah.wagabat.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.ahmedsalah.wagabat.R;
import com.ahmedsalah.wagabat.adapters.OrderHistoryAdapter;
import com.ahmedsalah.wagabat.models.OrderHistoryItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderHistory extends AppCompatActivity {
    RecyclerView recyclerView;
    OrderHistoryAdapter adapter;
    LinearLayoutManager layoutManager;
    List<OrderHistoryItem> orderHistoryItemsList;
    DatabaseReference dbOrdersRef;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        init();
//        listenToRTDatabaseChanges(); // @TODO don't forget to uncomment while testing
    }

    private void init(){
        recyclerView = findViewById(R.id.recyclerView);
        orderHistoryItemsList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new OrderHistoryAdapter(orderHistoryItemsList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        sharedPref = getSharedPreferences(getResources().getString(R.string.shared_pref_name),
                Context.MODE_PRIVATE);
        dbOrdersRef = FirebaseDatabase.getInstance().getReference("users/"+sharedPref.getString("uid", null)+"/orders");
    }

    private void listenToRTDatabaseChanges(){
        dbOrdersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}