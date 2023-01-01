package com.ahmedsalah.wagabat.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmedsalah.wagabat.R;
import com.ahmedsalah.wagabat.activities.CartActivity;
import com.ahmedsalah.wagabat.activities.MainActivity;
import com.ahmedsalah.wagabat.activities.OrderHistory;
import com.ahmedsalah.wagabat.db.databases.UserDatabase;
import com.ahmedsalah.wagabat.db.entities.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;


public class AccountFragment extends Fragment {
    View view;
    Button ordersHistoryBtn, cartBtn, vouchersBtn,
            paymentsBtn, helpBtn, aboutBtn, signoutBtn;
    TextView profileNameView, profileNumberView;
    FirebaseAuth auth;
    ProgressDialog progressDialog;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    DatabaseReference usersRef;

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
//        vouchersBtn = view.findViewById(R.id.account_vouchers);
//        paymentsBtn = view.findViewById(R.id.account_payment);
//        helpBtn = view.findViewById(R.id.account_help);
        aboutBtn = view.findViewById(R.id.account_about);
        signoutBtn = view.findViewById(R.id.btn_signout);
        profileNameView = view.findViewById(R.id.profile_name);
        profileNumberView = view.findViewById(R.id.profile_mobile);
        progressDialog = new ProgressDialog(view.getContext());
        //firebase objects
        auth = FirebaseAuth.getInstance();
        Context context = getContext();
        sharedPref = context.getSharedPreferences(context.getResources().getString(R.string.shared_pref_name),
                getContext().MODE_PRIVATE);
        editor = sharedPref.edit();
        usersRef = FirebaseDatabase.getInstance().getReference("users/"+sharedPref.getString("uid", null));
        // setting data
        setInfo();
    }

    private void replaceActivityAndClearTasl(Class activity){
        Intent intent = new Intent(view.getContext(), activity);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        view.getContext().startActivity(intent);
    }

    private void replaceActivity(Class activity){
        Intent intent = new Intent(view.getContext(), activity);
        view.getContext().startActivity(intent);
    }

    private void signOut(){
        auth.signOut();
        editor.remove("uid");
        editor.apply();
        Toast.makeText(view.getContext(), "Signing you out", Toast.LENGTH_SHORT).show();
        replaceActivityAndClearTasl(MainActivity.class);
    }

    private void setInfo(){
        UserDatabase db = Room.databaseBuilder(getActivity().getApplicationContext(),
            UserDatabase.class, "user-database").allowMainThreadQueries().build();
        final User[] user = {db.userDao().getUserByAuthID(sharedPref.getString("uid", null))};

        // user not found -> install data from firebase database
        if (user[0] ==null){
            usersRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        Map<String, Object> data = (Map<String, Object>) snapshot.getValue();
                        user[0] = new User(
                                sharedPref.getString("uid", null),
                                data.get("name").toString(),
                                data.get("email").toString(),
                                data.get("mobile").toString()
                        );
                        db.userDao().insertAll(user);
                        profileNameView.setText(user[0].name);
                        profileNumberView.setText(user[0].mobile);
                    } else{
                        Toast.makeText(view.getContext(),
                                "Can't retrieve data [fatal error -> restart the app]", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else{
            //user found -> directly upload the user data from the database
            Log.d("fbdb", user[0].name+"|name");
            profileNameView.setText(user[0].name);
            profileNumberView.setText(user[0].mobile);
        }

    }
}