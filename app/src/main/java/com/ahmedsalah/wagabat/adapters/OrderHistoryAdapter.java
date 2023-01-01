package com.ahmedsalah.wagabat.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmedsalah.wagabat.activities.CartActivity;
import com.ahmedsalah.wagabat.activities.OrderTrackingActivity;
import com.ahmedsalah.wagabat.models.OrderHistoryItem;
import com.ahmedsalah.wagabat.R;
import com.ahmedsalah.wagabat.models.OrderModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder> {
    private final List<OrderHistoryItem> orderHistoryItemsList;

    public OrderHistoryAdapter(List<OrderHistoryItem> list){this.orderHistoryItemsList=list;}

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView orderIDView, orderDateView, orderStatusView;
        private final View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            orderIDView = view.findViewById(R.id.order_id);
            orderDateView = view.findViewById(R.id.order_date);
            orderStatusView = view.findViewById(R.id.order_status);
        }

        public void setData(String orderId, CartActivity.DeliveryTime deliveryTime,
                            CartActivity.DeliveryGate deliveryGate, OrderModel.Status orderStatus){
            orderIDView.setText("Order: "+orderId.substring(0,10));
            orderDateView.setText(deliveryTime.toString());
            orderStatusView.setText(OrderModel.getStringForStatus(orderStatus));
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderHistoryItem item = this.orderHistoryItemsList.get(position);
        String orderID = item.getOrderID();
        CartActivity.DeliveryTime deliveryPeriod = item.getDeliveryTime();
        CartActivity.DeliveryGate deliveryLocation = item.getDeliveryLocation();
        String datetime = item.getDatetime();
        OrderModel.Status orderStatus = item.getOrderStatus();
        holder.setData(orderID, deliveryPeriod, deliveryLocation, orderStatus);

        holder.view.setOnClickListener(v->{
            Intent intent = new Intent(v.getContext(), OrderTrackingActivity.class);
            intent.putExtra("ostatus", orderStatus.ordinal());
            intent.putExtra("oid", orderID);
            intent.putExtra("delivery_period", deliveryPeriod.toString());
            intent.putExtra("datetime", datetime);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return orderHistoryItemsList.size();
    }

    public List<OrderHistoryItem> getOrderHistoryItemList(){
        return this.orderHistoryItemsList;
    }
}
