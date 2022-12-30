package com.ahmedsalah.wagabat.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ahmedsalah.wagabat.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class OrderTrackingActivity extends AppCompatActivity {
    TextView orderIdView, datetimeView, restaurantView, periodView, myDishes, priceView;
    ProgressBar progressBar;
    DatabaseReference orderDatabaseRef, restaurantDatabaseRef;
    final int PROGRESS_MAX=100;
    String  orderDishItemsDetails = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_tracking);
        init();
        initTrackingDetails();
    }

    public void init(){
        orderIdView = findViewById(R.id.orderIDHolder);
        datetimeView = findViewById(R.id.dateTimeHolder);
        restaurantView = findViewById(R.id.restaurantHolder);
        periodView = findViewById(R.id.periodHolder);
        myDishes = findViewById(R.id.myDishes);
        priceView = findViewById(R.id.priceView);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(PROGRESS_MAX);
        progressBar.setProgress(8);
    }

    public void initTrackingDetails(){
        Bundle extras = getIntent().getExtras();
        // check if the bundle is null
        if(extras==null){
            Log.e("extrasnull", extras.toString());
            return;
        }
        String oid = extras.getString("oid", "No ID found");
        orderIdView.setText(oid.substring(0,10));
        String datetime = extras.getString("orderDatetime",
                "No datetime");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime ldt = LocalDateTime.
                    parse(datetime.replace('T',' ').substring(0,19), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String newString = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(ldt); // 9:00
            datetimeView.setText(newString);
        }

        // set database references
        orderDatabaseRef = FirebaseDatabase
                .getInstance()
                .getReference("orders/"+oid);
        orderDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, Object> res = (Map<String, Object>)snapshot.getValue();
                String deliveryPeriod = res.get("delivery-period").toString();
                Map<String, Object> dishItems = (Map<String, Object>) res.get("items");
                String price = res.get("price").toString();
                String restId = res.get("restID").toString();
                int status = Integer.parseInt(res.get("status").toString());
                priceView.setText(
                        getString(R.string.currency)+" "+price
                );
                if(deliveryPeriod.equalsIgnoreCase("true")){
                    periodView.setText("Noon (12PM)");
                } else{
                    periodView.setText("Afternoon (3PM)");
                }

                // control tracking
                switch (status){
                    case 2:
                        progressBar.setProgress(50);
                        break;
                    case 3:
                        progressBar.setProgress(98);
                        break;
                    default:
                        progressBar.setProgress(8);
                        break;
                }

                // get restaurant name
                restaurantDatabaseRef = FirebaseDatabase
                        .getInstance()
                        .getReference("resturants/"+restId+"/name");
                restaurantDatabaseRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        restaurantView.setText(snapshot.getValue(String.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                // get dish items data
                DatabaseReference dishesDatabaseRef;
                for(String each:dishItems.keySet()){
                    dishesDatabaseRef = FirebaseDatabase
                            .getInstance()
                            .getReference("dishes/"+each+"/name");
                    dishesDatabaseRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Log.d("ooo", ""+dishItems.toString());
                            orderDishItemsDetails+=
                                    snapshot.getValue(String.class)+" X"+
                                            dishItems.get(each).toString()+" ";
                            myDishes.setText(orderDishItemsDetails);
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
}