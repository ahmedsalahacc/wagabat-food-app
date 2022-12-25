package com.ahmedsalah.wagabat.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmedsalah.wagabat.R;
import com.bumptech.glide.Glide;

public class DishItemActivity extends AppCompatActivity {

    int currentCount=1;
    String id;
    TextView dishNameView, descriptionView, priceView, qtyTextView;
    Button qtyDecrementBtn, qtyIncrementBtn, addToCartBtn;
    EditText specialRequestEdit;
    ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_item);
        init();
        manageQtyManipulation(qtyIncrementBtn, qtyDecrementBtn, qtyTextView);
        initializeComponentsDataFromIntent();
    }

    public void init(){
        // text views
        dishNameView = findViewById(R.id.title);
        descriptionView = findViewById(R.id.description);
        priceView = findViewById(R.id.price);
        qtyTextView = findViewById(R.id.qty_textView);
        qtyTextView.setText(Integer.toString(currentCount));
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
            currentCount++;
            qtyTextView.setText(Integer.toString(currentCount));
        });

        btnDecrement.setOnClickListener(v->{
            if(currentCount>1){
                currentCount--;
                qtyTextView.setText(Integer.toString(currentCount));
            }
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
        id = extras.getString("id");
        dishNameView.setText(extras.getString("name"));
        priceView.setText(Float.toString(extras.getFloat("price")));
        descriptionView.setText(extras.getString("desc"));
        Glide.with(this)
                .load(extras.getString("img"))
                .placeholder(R.drawable.ic_launcher_background)
                .override(400, 400)
                .into(imgView);
    }
}