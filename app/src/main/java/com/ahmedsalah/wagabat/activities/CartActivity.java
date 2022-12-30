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
import android.widget.Toast;

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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CartActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button cancelBtn, checkoutBtn;
    TextView itemSubTotalView, deliverySubTotalView, totalView, addressTextView;
    LinearLayoutManager layoutManager;
    DatabaseReference databaseRestaurantRef, dishesDatabaseRef, databaseOrderRef, databaseUserRef;
    List<CartModel> cartItemsList;
    CartAdapter adapter;
    SharedPreferences prefs;
    String userAddress;

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
        databaseRestaurantRef =
                FirebaseDatabase.getInstance().
                getReference("resturants/"+restId);

        // get restaurant info
        databaseRestaurantRef.addValueEventListener(new ValueEventListener() {
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
            try {
                handleCheckoutClick();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    //@TODO: handle order clicks
    private void handleCheckoutClick() throws Exception {
        if(OrdersCart.getInstance().getOrders().size()<1){
            Toast.makeText(this, "No Items in the Cart to checkout", Toast.LENGTH_SHORT).show();
            return;
        }
        String userId = prefs.getString("uid", null);
        String restId = OrdersCart.getInstance().getCurrentResturantId();
        OrderModel orderObject = new OrderModel(
                restId, userId
        );
        orderObject.setItemsList(OrdersCart.getInstance().getOrders());
        orderObject.confirm();

        // check if order is noon or afternoon delivery
        boolean isNoon=true; // noon
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime noonTime = LocalDateTime.of(now.getYear(), now.getMonth(),
                    now.getDayOfMonth(), 10, 0);
            LocalDateTime startTime = LocalDateTime.of(now.getYear(), now.getMonth(),
                    now.getDayOfMonth(), 8, 0);
            LocalDateTime afternoonTime = LocalDateTime.of(now.getYear(), now.getMonth(),
                    now.getDayOfMonth(), 13, 0);

            if(orderObject.getDatetime().isAfter(startTime)) {
                if (orderObject.getDatetime().isBefore(noonTime)) {
                    isNoon = true;
                    Log.d("dtime", "noon");
                } else if (orderObject.getDatetime().isBefore(afternoonTime)) {
                    isNoon = false;
                    Log.d("dtime", "afternoon");

                }
            }else{
                Toast.makeText(this, "Cannot order after 1PM", Toast.LENGTH_SHORT).show();
                Log.d("dtime", "can't");
                return;
            }
        }

        // @TODO create an OrderModel object for every order
        databaseOrderRef = FirebaseDatabase.getInstance()
                .getReference("orders").child(orderObject.getId());
        databaseOrderRef.child("userID").setValue(orderObject.getUserID());
        databaseOrderRef.child("restID").setValue(orderObject.getResturantID());
        databaseOrderRef.child("price").setValue(getTotal());
        databaseOrderRef.child("status").setValue(orderObject.getStatus().ordinal());
        databaseOrderRef.child("address").setValue(userAddress);
        databaseOrderRef.child("datetime").setValue(orderObject.getDatetime().toString());
        databaseOrderRef.child("delivery-period").setValue(Boolean.toString(isNoon));
        databaseOrderRef = databaseOrderRef.child("items");
        for(Item item:orderObject.getItemsList()){
            databaseOrderRef.child(item.getDishId()).setValue(item.getCount());
        }
        // add orderid to resturantdatabase
        databaseRestaurantRef.child("orders").child(orderObject.getId())
                .setValue(orderObject.getId());
        // add orderid to userdatabase
        databaseUserRef = FirebaseDatabase.getInstance().getReference("users/"+userId+"/orders");
        databaseUserRef.child(orderObject.getId())
                .setValue(orderObject.getId());
        // reset cart and navigate to orderhistory page
        OrdersCart.getInstance().reset();
        replaceActivity(MainMenuActivity.class);
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
                userAddress = user.address;
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