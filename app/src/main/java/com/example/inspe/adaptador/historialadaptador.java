package com.example.inspe.adaptador;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inspe.R;
import com.example.inspe.datos.historialdatos;
import com.example.inspe.datos.previsualizaciondatos;
import com.example.inspe.previsualizacion;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class historialadaptador extends RecyclerView.Adapter<historialadaptador.Myholder>{
    FirebaseUser user;
    FirebaseAuth auth;
    Context context;
    ArrayList<historialdatos> carrito1;

    public historialadaptador(Context context, ArrayList<historialdatos> carrito) {
        this.context = context;
        this.carrito1 = carrito;
    }

    @NonNull
    @Override
    public historialadaptador.Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.historialadaptador,parent,false);
        auth= FirebaseAuth.getInstance();
        user= auth.getCurrentUser();

        return new historialadaptador.Myholder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull historialadaptador.Myholder holder, int position) {
         historialdatos carrito=carrito1.get(position);
String enl = carrito.getenlace();

        String foto = carrito.getfirmaUrl();
        // Aquí establecemos la fecha como el texto del botón
        holder.direccion.setText(carrito.getenlace());
        holder.direccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent i = new Intent(context, previsualizacion.class);
                i.putExtra("dato","/empresa/formulario/horIHZJGDOSkBD54WRxbxtB2RdE2/"+enl);
                i.putExtra("numero",1);
                 i.putExtra("foto",foto);

                context.startActivity(i);
            }
        });

    }




    @Override
    public int getItemCount() {
        return carrito1.size();
    }

    public class Myholder extends RecyclerView.ViewHolder {
     Button direccion;

        public Myholder(@NonNull View itemView) {
            super(itemView);
            direccion=itemView.findViewById(R.id.button3);



        }

    }
}
