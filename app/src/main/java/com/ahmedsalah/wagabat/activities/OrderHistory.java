package com.ahmedsalah.wagabat.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.ahmedsalah.wagabat.R;
import com.ahmedsalah.wagabat.adapters.OrderHistoryAdapter;
import com.ahmedsalah.wagabat.models.OrderHistoryItem;
import com.ahmedsalah.wagabat.models.OrderModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderHistory extends AppCompatActivity {
    RecyclerView recyclerView;
    OrderHistoryAdapter adapter;
    LinearLayoutManager layoutManager;
    List<OrderHistoryItem> orderHistoryItemsList;
    DatabaseReference dbOrdersRef;
    SharedPreferences sharedPref;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        init();
        listenToRTDatabaseChanges();
    }

    private void init(){
        progressDialog = new ProgressDialog(this);
        progressDialogueSetter();
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
        dbOrdersRef = FirebaseDatabase.getInstance()
                .getReference("users/"+sharedPref.getString("uid", null)+"/orders");
    }

    private void listenToRTDatabaseChanges(){
        dbOrdersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderHistoryItemsList.clear();
                DatabaseReference databaseOrdersRef;
                for(DataSnapshot each: snapshot.getChildren()){
                    String orderID = each.getKey();
                    databaseOrdersRef = FirebaseDatabase.getInstance()
                            .getReference("orders/"+orderID);
                    Log.d("ohdbg","eachID: "+each.getKey());
                    databaseOrdersRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Map<String, Object> result = (Map<String, Object>)snapshot.getValue();
                            Log.d("ohdbg","resobj:"+result);
                            OrderHistoryItem orderHistoryItem = new OrderHistoryItem(
                                    orderID,
                                    CartActivity.getPeriodValueOf(
                                    Integer.parseInt(result.get("period").toString())
                                    ),
                                    CartActivity.getLocationValueOf(
                                            Integer.parseInt(result.get("location").toString())
                                    )
                                    ,
                                    OrderModel.mapNumberToStatusEnum(
                                            Integer.parseInt(result.get("status").toString())
                                    ),
                                    result.get("datetime").toString()
                            );
                            // check if order item already exists in the db
                            for(int i=0;i<orderHistoryItemsList.size();i++){
                                if(orderHistoryItemsList.get(i).getOrderID().equalsIgnoreCase(orderID)){
                                    orderHistoryItemsList.remove(i);
                                }
                            }
                            orderHistoryItemsList.add(orderHistoryItem);
                            Collections.sort(orderHistoryItemsList, new Comparator<OrderHistoryItem>() {
                                @Override
                                public int compare(OrderHistoryItem orderHistoryItem, OrderHistoryItem t1) {
                                    return t1.getDatetime().compareTo(orderHistoryItem.getDatetime());
                                }
                            });
                            adapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void progressDialogueSetter(){
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("Loading Content");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }
}