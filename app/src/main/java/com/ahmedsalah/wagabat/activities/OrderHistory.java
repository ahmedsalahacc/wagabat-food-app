package com.ahmedsalah.wagabat.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.ahmedsalah.wagabat.R;
import com.ahmedsalah.wagabat.adapters.OrderHistoryAdapter;
import com.ahmedsalah.wagabat.models.OrderHistoryItem;

import java.util.ArrayList;
import java.util.List;

public class OrderHistory extends AppCompatActivity {
    RecyclerView recyclerView;
    OrderHistoryAdapter adapter;
    LinearLayoutManager layoutManager;
    List<OrderHistoryItem> orderHistoryItemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        init();
        listenToRTDatabaseChanges();
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
    }

    private void listenToRTDatabaseChanges(){}
}