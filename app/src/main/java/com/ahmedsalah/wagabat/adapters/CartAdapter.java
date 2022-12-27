package com.ahmedsalah.wagabat.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmedsalah.wagabat.R;
import com.ahmedsalah.wagabat.models.CartModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private final List<CartModel> cartItemsList;

    public CartAdapter(List<CartModel> list){this.cartItemsList = list;}

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final ImageView imageView;
        private final TextView itemNameView, restaurantNameView, qtyView, priceView;
        private final View view;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            view = itemView;
            imageView = itemView.findViewById(R.id.dish_item_img);
            itemNameView = itemView.findViewById(R.id.dish_item_name);
            restaurantNameView = itemView.findViewById(R.id.resturant_name);
            priceView = itemView.findViewById(R.id.dish_item_price);
            qtyView = itemView.findViewById(R.id.text_qty);
        }

        public void setData(String name, String restaurantName, String img, int qty, float price){
            itemNameView.setText(name);
            restaurantNameView.setText(restaurantName);
            qtyView.setText("X"+qty);
            priceView.setText(price+" "+view.getContext().getString(R.string.currency));
            Glide.with(view)
                    .load(img)
                    .placeholder(R.drawable.ic_launcher_background)
                    .override(400, 400)
                    .into(imageView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartModel item = this.cartItemsList.get(position);
        String name = item.getItemName();
        String restaurantName = item.getResturantName();
        String img = item.getImg();
        int qty = item.getQty();
        float price = item.getItemPrice();
        holder.setData(name, restaurantName, img, qty, price);
    }

    @Override
    public int getItemCount() {
        return this.cartItemsList.size();
    }

}
