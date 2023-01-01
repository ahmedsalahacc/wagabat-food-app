package com.ahmedsalah.wagabat.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmedsalah.wagabat.R;
import com.ahmedsalah.wagabat.adapters.DishAdapter;
import com.ahmedsalah.wagabat.models.DishModel;
import com.ahmedsalah.wagabat.models.ResturantModel;
import com.ahmedsalah.wagabat.utils.OrdersCart;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResturantActivity extends AppCompatActivity {
    String resturantId;
    RecyclerView recyclerView;
    DishAdapter adapter;
    LinearLayoutManager layoutManager;
    DatabaseReference database;
    List<DishModel> dishesList;
    ImageView imgView;
    TextView restName, category, rating,
            deliveryTime, deliveryCost;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);
        init();
    }

    private void init() {
        // bind UI XML
        restName = findViewById(R.id.rest_item_name);
        category = findViewById(R.id.rest_item_category);
        rating = findViewById(R.id.rest_item_rating);
        deliveryTime = findViewById(R.id.rest_delivery_time);
        deliveryCost = findViewById(R.id.rest_delivery_price);
        imgView = findViewById(R.id.res_img);
        progressDialog = new ProgressDialog(this);
        progressDialogueSetter();
        // recycler view
        dishesList = new ArrayList<>();
        recyclerView = findViewById(R.id.dishes_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DishAdapter(dishesList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        // bundle
        Bundle extras = getIntent().getExtras();
        if(extras==null){
            Log.e("extrasnull", extras.toString());
            return;
        }
        resturantId = extras.getString("id");
        setResturantCardFromBundle(extras);

        // database
        database = FirebaseDatabase.getInstance().getReference("resturants/"+resturantId+"/dishes");
        listenToRTDatabaseChanges();
    }

    private void listenToRTDatabaseChanges() {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dishesList.clear();
                DatabaseReference dishesDatabase;

                for (DataSnapshot each : snapshot.getChildren()) {
//                    Map<String, Object> jsonObj = (Map<String, Object>) each.getValue();
                     dishesDatabase = FirebaseDatabase.getInstance().getReference("dishes/"+each.getKey());
                     dishesDatabase.addValueEventListener(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull DataSnapshot sshot) {
                             Map<String, Object> jsonObj = (Map<String, Object>) sshot.getValue();

                             DishModel dish = new DishModel(
                                     sshot.getKey().toString(),
                                     jsonObj.get("name").toString(),
                                     jsonObj.get("img").toString(),
                                     jsonObj.get("description").toString(),
                                     Float.parseFloat(jsonObj.get("price").toString()),
                                     resturantId
                             );
                             dishesList.add(dish);
                             adapter.notifyDataSetChanged();
                         }

                         @Override
                         public void onCancelled(@NonNull DatabaseError error) {

                         }
                     });
//
                }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ResturantActivity.this, "" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setResturantCardFromBundle(Bundle bundle){
        Glide.with(this)
                .load(bundle.getString("img"))
                .placeholder(R.drawable.ic_launcher_background)
                .override(400, 400)
                .into(imgView);
        restName.setText(bundle.getString("name"));
        category.setText(bundle.getString("category"));
        deliveryCost.setText("EGP "+bundle.getString("deliveryPrice"));
        deliveryTime.setText(bundle.getString("deliveryTime")+" mins");
        rating.setText(bundle.getString("rating"));
    }

    private void progressDialogueSetter(){
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("Loading Content");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

}