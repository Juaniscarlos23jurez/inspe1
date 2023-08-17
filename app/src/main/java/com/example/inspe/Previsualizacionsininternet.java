package com.example.inspe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.inspe.dininternet.previdatossin;

import java.util.List;

public class Previsualizacionsininternet extends AppCompatActivity {

    private RecyclerView recyclerView;
    private previdatossin dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previsualizacionsininternet);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        List<DataItem> filteredData = dbHelper.getFilteredAndOrderedData();
        dataAdapter = new previdatossin(filteredData);
        recyclerView.setAdapter(dataAdapter);

    }
}