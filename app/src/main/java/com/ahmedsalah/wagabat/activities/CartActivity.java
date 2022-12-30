package com.ahmedsalah.wagabat.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.ahmedsalah.wagabat.R;
import com.ahmedsalah.wagabat.adapters.CartAdapter;
import com.ahmedsalah.wagabat.db.databases.UserDatabase;
import com.ahmedsalah.wagabat.db.entities.User;
import com.ahmedsalah.wagabat.models.CartModel;
import com.ahmedsalah.wagabat.models.Item;
import com.ahmedsalah.wagabat.models.OrderModel;
import com.ahmedsalah.wagabat.utils.OrdersCart;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CartActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button cancelBtn, checkoutBtn;
    TextView itemSubTotalView, deliverySubTotalView, totalView, addressTextView;
    LinearLayoutManager layoutManager;
    DatabaseReference databaseNameRef, dishesDatabaseRef, databaseOrderRef;
    List<CartModel> cartItemsList;
    CartAdapter adapter;
    SharedPreferences prefs;

    float dishesSubTotal, deliverySubTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        init();
        populateCart();
        handleBtns();
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
        addressTextView = findViewById(R.id.cart_address);
        // recycler view
        cartItemsList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CartAdapter(cartItemsList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        // shared pref
        prefs = getSharedPreferences(getResources().getString(R.string.shared_pref_name),
                Context.MODE_PRIVATE);
        //setting data
        setAddressData();
    }

    public void populateCart(){
        ArrayList<Item> itemsList = OrdersCart.getInstance().getOrders();
        String restId = OrdersCart.getInstance().getCurrentResturantId();
        if (restId==null){
            return;
        }
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

    private void handleBtns(){
        cancelBtn.setOnClickListener(v->{
            OrdersCart.getInstance().reset();
            replaceActivity(MainMenuActivity.class);
        });

        checkoutBtn.setOnClickListener(v->{
            handleCheckoutClick();
        });
    }

    //@TODO: handle order clicks
    private void handleCheckoutClick(){
//        cartItemsList
        String orderId = UUID.randomUUID().toString();
        String userId = prefs.getString("uid", null);
        String restId = OrdersCart.getInstance().getCurrentResturantId();
        // @TODO create an OrderModel object for every order
        databaseOrderRef = FirebaseDatabase.getInstance()
                .getReference("orders").child(orderId);
        databaseOrderRef.child("userID").setValue(userId);
        databaseOrderRef.child("restID").setValue(restId);
        databaseOrderRef.child("price").setValue(getTotal());
        databaseOrderRef.child("status").setValue(OrderModel.Status.WAITING_FOR_APPROVAL.ordinal());
        databaseOrderRef = databaseOrderRef.child("items");
        List<Item> items = OrdersCart.getInstance().getOrders();
        for(Item item:items){
            databaseOrderRef.child(item.getDishId()).setValue(item.getCount());
        }

    }

    private void setAddressData(){
        String userId = prefs.getString("uid", null);
        DatabaseReference emailRef = FirebaseDatabase.getInstance().getReference("users/"+userId+"/email");
        UserDatabase db = Room.databaseBuilder(getApplicationContext(),
                UserDatabase.class, "user-database").allowMainThreadQueries().build();
        emailRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String email = snapshot.getValue(String.class);
                Log.d("fbdb", email);
                User user = db.userDao().getUserByEmaiL(email);
                addressTextView.setText(
                        getString(R.string.cart_address)+
                        user.address
                );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void notifyTotalPriceChanged(){
        totalView.setText(Float.toString(getTotal()));
        itemSubTotalView.setText(Float.toString(dishesSubTotal));
    }

    private void replaceActivity(Class activityClass){
        Intent intent = new Intent(this, activityClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
    }

    private float getTotal(){
        return deliverySubTotal+dishesSubTotal;
    }
}