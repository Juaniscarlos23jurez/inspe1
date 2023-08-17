package com.example.inspe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import com.example.inspe.adaptador.adaptadorprevisualizacion;
import com.example.inspe.adaptador.historialadaptador;
import com.example.inspe.datos.historialdatos;
import com.example.inspe.datos.previsualizaciondatos;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class perfil extends AppCompatActivity {
    FirebaseUser user;
    FirebaseAuth auth;
    RecyclerView car;
    historialadaptador carritoAdaptador;
    DatabaseReference base;
     Button button12;
    ArrayList<historialdatos> carrito1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        auth = FirebaseAuth.getInstance();
        user= auth.getCurrentUser();
        car=findViewById(R.id.prueba);

        String datos1=getIntent().getStringExtra("dato");

        // Verificar si el usuario está autenticado
        base= FirebaseDatabase.getInstance().getReference( "/empresa/enlace/horIHZJGDOSkBD54WRxbxtB2RdE2");
        car.setHasFixedSize(true);
        car.setLayoutManager(new LinearLayoutManager( this,LinearLayoutManager.VERTICAL,false));
        carrito1=new ArrayList<>();
        carritoAdaptador=new historialadaptador(this,carrito1);
        car.setAdapter(carritoAdaptador);
        base.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Vaciar la lista de datos
                carrito1.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    historialdatos carrito2 = dataSnapshot.getValue(historialdatos.class);
                    carrito1.add(carrito2);
                }
                carritoAdaptador.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}