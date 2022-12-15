package com.ahmedsalah.wagabat;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.ViewHolder> {

    private List<DishModel> dishesList;

    public DishAdapter(List<DishModel> list){ this.dishesList = list;}


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView nameView, priceView, descriptionView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.dish_item_img);
            nameView = itemView.findViewById(R.id.dish_item_name);
            priceView = itemView.findViewById(R.id.dish_item_price);
            descriptionView = itemView.findViewById(R.id.dish_item_description);
        }

        @Override
        public String toString() {
            return super.toString();
        }

        public void setData(String name, String imgAddress, String description, float price){
            nameView.setText(name);
            priceView.setText("EGP\t"+Float.toString(price));
            descriptionView.setText(description);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dish_item, parent, false);
        view.setOnClickListener((v)->{
            parent.getContext().startActivity(new Intent(parent.getContext(), DishItemActivity.class));
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DishModel item = this.dishesList.get(position);
        String name = item.getName();
        String description = item.getDescription();
        String imgAddress = item.getImgAddress();
        float price = item.getPrice();
        holder.setData(name, imgAddress, description, price);
    }

    @Override
    public int getItemCount() {
        return this.dishesList.size();
    }



}
