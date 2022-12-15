package com.ahmedsalah.wagabat.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ahmedsalah.wagabat.R;
import com.ahmedsalah.wagabat.activities.CartActivity;
import com.ahmedsalah.wagabat.activities.OrderHistory;


public class AccountFragment extends Fragment {
    View view;
    Button ordersHistoryBtn, cartBtn, vouchersBtn,
            paymentsBtn, helpBtn, aboutBtn, signoutBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_account, container, false);

        // init components
        initComponents();

        // event listeners
        ordersHistoryBtn.setOnClickListener(v->{
            replaceActivity(OrderHistory.class);
        });
        cartBtn.setOnClickListener(v->{
            replaceActivity(CartActivity.class);
        });

        return view;
    }

    private void initComponents(){
        ordersHistoryBtn = view.findViewById(R.id.account_orders);
        cartBtn = view.findViewById(R.id.btn_cart);
        vouchersBtn = view.findViewById(R.id.account_vouchers);
        paymentsBtn = view.findViewById(R.id.account_payment);
        helpBtn = view.findViewById(R.id.account_help);
        aboutBtn = view.findViewById(R.id.account_about);
        signoutBtn = view.findViewById(R.id.btn_signout);
    }
    private void replaceActivity(Class activity){
        view.getContext().startActivity(new Intent(view.getContext(), activity));
    }
}