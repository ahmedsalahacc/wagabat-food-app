package com.ahmedsalah.wagabat.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ahmedsalah.wagabat.R;
import com.ahmedsalah.wagabat.activities.CartActivity;
import com.ahmedsalah.wagabat.activities.MainActivity;
import com.ahmedsalah.wagabat.activities.OrderHistory;
import com.google.firebase.auth.FirebaseAuth;


public class AccountFragment extends Fragment {
    View view;
    Button ordersHistoryBtn, cartBtn, vouchersBtn,
            paymentsBtn, helpBtn, aboutBtn, signoutBtn;
    FirebaseAuth auth;
    ProgressDialog progressDialog;

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
        signoutBtn.setOnClickListener(v->{
            signOut();
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
        progressDialog = new ProgressDialog(view.getContext());
        //firebase objects
        auth = FirebaseAuth.getInstance();
    }
    private void replaceActivity(Class activity){
        view.getContext().startActivity(new Intent(view.getContext(), activity));
    }

    private void signOut(){
        auth.signOut();
        Toast.makeText(view.getContext(), "Signing you out", Toast.LENGTH_SHORT).show();
        replaceActivity(MainActivity.class);
    }
}