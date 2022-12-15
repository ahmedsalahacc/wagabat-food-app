package com.ahmedsalah.wagabat.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmedsalah.wagabat.R;
import com.ahmedsalah.wagabat.adapters.ResturantAdapter;
import com.ahmedsalah.wagabat.models.ResturantModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    ResturantAdapter adapter;
    LinearLayoutManager layoutManager;
    List<ResturantModel> userList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_home, container, false);

        init();

        return view;
    }

    public void init(){
        setUserList();
        Log.i("mytag", "1");
        recyclerView = view.findViewById(R.id.rest_recyclerView);
        Log.i("mytag", "2");
        layoutManager = new LinearLayoutManager(getActivity());
        Log.i("mytag", "3");
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        Log.i("mytag", "4");
        recyclerView.setLayoutManager(layoutManager);
        Log.i("mytag", "5");
        adapter = new ResturantAdapter(userList);
        Log.i("mytag", "6");
        recyclerView.setAdapter(adapter);
        Log.i("mytag", "7");
        adapter.notifyDataSetChanged();
        Log.i("mytag", "8");
    }

    public void setUserList(){
        userList = new ArrayList<>();
        userList.add(new ResturantModel("Ma7shy w 7agat", "Oriental",4.8f,"#",11.54f,100));
        userList.add(new ResturantModel("Crepito", "Fast Food",4.1f,"#",7f,30));
        userList.add(new ResturantModel("Sandwitch Wegry", "Fast Food",4.1f,"#",7f,30));
        userList.add(new ResturantModel("Gamoosa Burgers", "Fast Food",4.5f,"#",15f,45));
        userList.add(new ResturantModel("Moshi Sushi", "Sea Food",4.9f,"#",20f,51));
        userList.add(new ResturantModel("Asmak Elarmooty", "Sea Food",3.8f,"#",4f,120));
        userList.add(new ResturantModel("Koshary ElMidan", "Fast Food",4.2f,"#",5.30f,15));
//        userList.add(new ResturantModel("", "Fast Food",4.1f,"#",7f,30));
//        userList.add(new ResturantModel("Sandwitch Wegry", "Fast Food",4.1f,"#",7f,30));
    }
}