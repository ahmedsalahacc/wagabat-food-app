package com.ahmedsalah.wagabat.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.ahmedsalah.wagabat.R;
import com.ahmedsalah.wagabat.adapters.CartAdapter;
import com.ahmedsalah.wagabat.models.CartModel;
import com.ahmedsalah.wagabat.models.Item;
import com.ahmedsalah.wagabat.utils.OrdersCart;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button cancelBtn, checkoutBtn;
    TextView itemSubTotalView, deliverySubTotalView, totalView;
    LinearLayoutManager layoutManager;
    DatabaseReference databaseNameRef, dishesDatabaseRef;
    List<CartModel> cartItemsList;
    CartAdapter adapter;

    float dishesSubTotal, deliverySubTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        init();
        populateCart();
        itemSubTotalView.setText(Float.toString(dishesSubTotal));
        totalView.setText(Float.toString(dishesSubTotal+deliverySubTotal));
    }

    public void init(){
        deliverySubTotal=0;
        dishesSubTotal=0;
        // bind UI elements
        recyclerView = findViewById(R.id.recyclerView);
        cancelBtn = findViewById(R.id.btn_cancel);
        checkoutBtn = findViewById(R.id.btn_checkout);
        itemSubTotalView = findViewById(R.id.txt_items_subtotal);
        deliverySubTotalView = findViewById(R.id.txt_del_subtotal);
        totalView = findViewById(R.id.txt_total);

        // recycler view
        cartItemsList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CartAdapter(cartItemsList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void populateCart(){
        ArrayList<Item> itemsList = OrdersCart.getInstance().getOrders();
        String restId = OrdersCart.getInstance().getCurrentResturantId();
        databaseNameRef =
                FirebaseDatabase.getInstance().
                getReference("resturants/"+restId);

        // get restaurant info
        databaseNameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cartItemsList.clear();
                // cast data to hashmap
                Map<String, Object> restObj= (Map<String, Object>)snapshot.getValue();
                String restName = restObj.get("name").toString();
                deliverySubTotal = Float.parseFloat(restObj.get("delivery-price").toString());
                deliverySubTotalView.setText(Float.toString(deliverySubTotal));

                // fetch each dish info and set a cart model
                for(Item item:itemsList){
                    dishesDatabaseRef = FirebaseDatabase.getInstance()
                            .getReference("dishes/"+item.getDishId());

                    // get dishes info
                    dishesDatabaseRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot ss) {
                            Map<String, Object> dishObj = (Map<String, Object>)ss.getValue();

                            String dishName = dishObj.get("name").toString();
                            float dishPrice = Float.parseFloat(dishObj.get("price").toString());
                            String img = dishObj.get("img").toString();

                            cartItemsList.add(new CartModel(
                                    dishName,
                                    restName,
                                    img,
                                    item.getCount(),
                                    dishPrice
                            ));
                            // calculate totals and notify changes
                            dishesSubTotal+=dishPrice*item.getCount();
                            adapter.notifyDataSetChanged();
                            notifyTotalPriceChanged();
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

    private void notifyTotalPriceChanged(){
        totalView.setText(Float.toString(deliverySubTotal+dishesSubTotal));
        itemSubTotalView.setText(Float.toString(dishesSubTotal));
    }
}