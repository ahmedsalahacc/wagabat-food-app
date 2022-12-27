package com.ahmedsalah.wagabat.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmedsalah.wagabat.R;
import com.ahmedsalah.wagabat.models.Item;
import com.ahmedsalah.wagabat.utils.OrdersCart;
import com.bumptech.glide.Glide;

public class DishItemActivity extends AppCompatActivity {

    int quantity =1;
    String dishId, resturantId;
    TextView dishNameView, descriptionView, priceView, qtyTextView;
    Button qtyDecrementBtn, qtyIncrementBtn, addToCartBtn;
    EditText specialRequestEdit;
    ImageView imgView;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_item);
        init();
        manageQtyManipulation(qtyIncrementBtn, qtyDecrementBtn, qtyTextView);
        initializeComponentsDataFromIntent();
        manageAddToCartButton();
    }

    public void init(){
        sharedPref = getSharedPreferences("uuidpref", Context.MODE_PRIVATE);
        // text views
        dishNameView = findViewById(R.id.title);
        descriptionView = findViewById(R.id.description);
        priceView = findViewById(R.id.price);
        qtyTextView = findViewById(R.id.qty_textView);
        qtyTextView.setText(Integer.toString(quantity));
        // buttons
        qtyIncrementBtn = findViewById(R.id.qty_increment);
        qtyDecrementBtn = findViewById(R.id.qty_decrement);
        addToCartBtn = findViewById(R.id.addToCartBtn);
        // edit text
        specialRequestEdit = findViewById(R.id.special_requests);
        // Image view
        imgView = findViewById(R.id.img);
    }

    public void manageQtyManipulation(Button btnIncrement, Button btnDecrement, TextView qtyTextView){
        btnIncrement.setOnClickListener(v->{
            quantity++;
            qtyTextView.setText(Integer.toString(quantity));
        });

        btnDecrement.setOnClickListener(v->{
            if(quantity >1){
                quantity--;
                qtyTextView.setText(Integer.toString(quantity));
            }
        });
    }

    public void manageAddToCartButton(){
        addToCartBtn.setOnClickListener(v->{
            addToCart();
            Log.d("cartcontent", OrdersCart.getInstance().getOrders().toString());
            Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show();
//            replaceActivity(MainMenuActivity.class);
        });
    }

    public void initializeComponentsDataFromIntent(){
        Bundle extras = getIntent().getExtras();
        // check if the bundle is null
        if(extras==null){
            Log.e("extrasnull", extras.toString());
            return;
        }
        // initialize components
        dishId = extras.getString("dish_id");
        resturantId = extras.getString("rest_id");
        dishNameView.setText(extras.getString("name"));
        priceView.setText(Float.toString(extras.getFloat("price")));
        descriptionView.setText(extras.getString("desc"));
        Glide.with(this)
                .load(extras.getString("img"))
                .placeholder(R.drawable.ic_launcher_background)
                .override(400, 400)
                .into(imgView);
    }

    public void addToCart(){
        Log.d("cartt", resturantId+"||"+OrdersCart.getInstance().getCurrentResturantId());
        if(OrdersCart.getInstance().getCurrentResturantId()!=null &&
                !OrdersCart.getInstance().getCurrentResturantId().equals(resturantId)){
            OrdersCart.getInstance().reset();
        }
        String userId = sharedPref.getString("uid", null);
        Item item = new Item(
                dishId,
                quantity,
                resturantId,
                userId,
                specialRequestEdit.getText().toString()
        );
        OrdersCart.getInstance().addOrder(item);
        OrdersCart.getInstance().setCurrentResturantId(resturantId);
    }

    private void replaceActivity(Class activityClass){
        Intent intent = new Intent(this, activityClass);
//        intent.putExtra("id", resturantId);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
    }

}