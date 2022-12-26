package com.ahmedsalah.wagabat.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmedsalah.wagabat.activities.DishItemActivity;
import com.ahmedsalah.wagabat.models.DishModel;
import com.ahmedsalah.wagabat.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.ViewHolder> {

    private List<DishModel> dishesList;

    public DishAdapter(List<DishModel> list){ this.dishesList = list;}


    public class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        private ImageView imageView;
        private TextView nameView, priceView, descriptionView;
        private final int maxCharsAllowed=60;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
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
            Glide.with(view.getContext())
                    .load(imgAddress)
                    .placeholder(R.drawable.ic_launcher_background)
                    .override(400, 400)
                    .into(imageView);
            nameView.setText(name);
            priceView.setText("EGP\t"+Float.toString(price));
            if (description.length()<=this.maxCharsAllowed){
                descriptionView.setText(description);
            }else{
                descriptionView.setText(description.substring(0,this.maxCharsAllowed)+"...");
            }

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dish_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DishModel item = this.dishesList.get(position);
        String id = item.getID();
        String name = item.getName();
        String description = item.getDescription();
        String imgAddress = item.getImgAddress();
        float price = item.getPrice();
        holder.setData(name, imgAddress, description, price);

        holder.view.setOnClickListener((v)->{
            Intent intent = new Intent(v.getContext(), DishItemActivity.class);
            intent.putExtra("dish_id", id);
            intent.putExtra("rest_id", item.getResturantId());
            intent.putExtra("name", name);
            intent.putExtra("desc", description);
            intent.putExtra("img", imgAddress);
            intent.putExtra("price", price);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return this.dishesList.size();
    }



}
