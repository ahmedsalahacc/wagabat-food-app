package com.ahmedsalah.wagabat.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ahmedsalah.wagabat.R;
import com.ahmedsalah.wagabat.adapters.ResturantAdapter;
import com.ahmedsalah.wagabat.models.ResturantModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    ResturantAdapter adapter;
    LinearLayoutManager layoutManager;
    List<ResturantModel> resturantsList;
    DatabaseReference database;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_home, container, false);

        init();

        return view;
    }

    public void init(){
        progressDialog = new ProgressDialog(view.getContext());
        progressDialogueSetter();
        resturantsList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.rest_recyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ResturantAdapter(resturantsList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        database = FirebaseDatabase.getInstance().getReference("resturants");
        listenToRTDatabaseChanges();
    }

    public void listenToRTDatabaseChanges(){
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                resturantsList.clear();
                for(DataSnapshot each:snapshot.getChildren()){
                    Map<String, Object> jsonObj = (Map<String, Object>)each.getValue();
                    ResturantModel rest = new ResturantModel(
                            each.getKey(),
                            jsonObj.get("name").toString(),
                            jsonObj.get("category").toString(),
                            Float.parseFloat(jsonObj.get("rating").toString()),
                            jsonObj.get("img").toString(),
                            Float.parseFloat(jsonObj.get("delivery-price").toString()),
                            Integer.parseInt(jsonObj.get("delivery-time").toString())
                    );
                    resturantsList.add(rest);
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(view.getContext(), ""+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void progressDialogueSetter(){
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("Loading Content");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }
}