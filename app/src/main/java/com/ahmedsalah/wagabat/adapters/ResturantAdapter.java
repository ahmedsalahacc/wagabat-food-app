package com.ahmedsalah.wagabat.adapters;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmedsalah.wagabat.R;
import com.ahmedsalah.wagabat.activities.ResturantActivity;
import com.ahmedsalah.wagabat.models.ResturantModel;
import com.bumptech.glide.Glide;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

public class ResturantAdapter extends RecyclerView.Adapter<ResturantAdapter.ViewHolder> {

    private List<ResturantModel> restaurantsList;

    public ResturantAdapter(List<ResturantModel> list){ this.restaurantsList = list;}


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView nameView, categoryClassView, deliveryPriceView, deliveryTimeView, ratingView;
        private View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            imageView = itemView.findViewById(R.id.item_img);
            nameView = itemView.findViewById(R.id.item_name);
            categoryClassView = itemView.findViewById(R.id.item_class);
            deliveryPriceView = itemView.findViewById(R.id.item_delivery_price);
            deliveryTimeView = itemView.findViewById(R.id.item_delivery_time);
            ratingView = itemView.findViewById(R.id.item_rating);
        }

        @Override
        public String toString() {
            return super.toString();
        }

        public void setData(String name, String categoryClass, float rating, String imgAddress, float deliveryPrice, float deliveryTime) throws IOException {
            nameView.setText(name);
            categoryClassView.setText(categoryClass);
            deliveryPriceView.setText("EGP\t"+Float.toString(deliveryPrice));
            deliveryTimeView.setText("Within\t"+Float.toString(deliveryTime)+"\tmins");
            ratingView.setText(Float.toString(rating));
            Glide.with(view)
                    .load(imgAddress)
                    .placeholder(R.drawable.ic_launcher_background)
                    .override(400, 400)
                    .into(imageView);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_item, parent, false);
        view.setOnClickListener((v)->{
            parent.getContext().startActivity(new Intent(parent.getContext(), ResturantActivity.class));
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResturantModel item = this.restaurantsList.get(position);
        String name = item.getName();
        String categoryClass = item.getCategoryClass();
        String imgAddress = item.getImgAddress();
        Float deliveryPrice = item.getDeliveryPrice();
        Float rating = item.getRating();
        int deliveryTime = item.getDeliveryTime();
        try {
            holder.setData(name, categoryClass, rating, imgAddress, deliveryPrice, deliveryTime);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return this.restaurantsList.size();
    }



}
