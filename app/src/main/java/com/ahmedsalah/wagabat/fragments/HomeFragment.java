package com.ahmedsalah.wagabat.fragments;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_home, container, false);

        init();

        return view;
    }

    public void init(){
//        setUserList();
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
                    Log.d("restd","calling");
                    Map<String, Object> jsonObj = (Map<String, Object>)each.getValue();
                    ResturantModel rest = new ResturantModel(
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(view.getContext(), ""+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setUserList(){
        resturantsList.add(new ResturantModel("Ma7shy w 7agat", "Oriental",4.8f,"#",11.54f,100));
        resturantsList.add(new ResturantModel("Crepito", "Fast Food",4.1f,"#",7f,30));
        resturantsList.add(new ResturantModel("Sandwitch Wegry", "Fast Food",4.1f,"#",7f,30));
        resturantsList.add(new ResturantModel("Gamoosa Burgers", "Fast Food",4.5f,"#",15f,45));
        resturantsList.add(new ResturantModel("Moshi Sushi", "Sea Food",4.9f,"#",20f,51));
        resturantsList.add(new ResturantModel("Asmak Elarmooty", "Sea Food",3.8f,"#",4f,120));
        resturantsList.add(new ResturantModel("Koshary ElMidan", "Fast Food",4.2f,"#",5.30f,15));
        resturantsList.add(new ResturantModel("", "Fast Food",4.1f,"#",7f,30));
        resturantsList.add(new ResturantModel("Sandwitch Wegry", "Fast Food",4.1f,"#",7f,30));
    }
}