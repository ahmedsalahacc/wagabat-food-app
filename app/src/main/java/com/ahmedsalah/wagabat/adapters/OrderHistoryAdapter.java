package com.ahmedsalah.wagabat.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmedsalah.wagabat.models.OrderHistoryItem;
import com.ahmedsalah.wagabat.R;
import com.ahmedsalah.wagabat.models.OrderModel;

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

        public void setData(String orderId, String dateTime, OrderModel.Status orderStatus){
            orderIDView.setText(orderId);
            orderDateView.setText(dateTime);
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
        String orderDateTime = item.getOrderDateTime();
        OrderModel.Status orderStatus = item.getOrderStatus();
        holder.setData(orderID, orderDateTime, orderStatus);
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
