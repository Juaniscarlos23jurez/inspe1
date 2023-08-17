package com.example.inspe.adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.inspe.R;
import com.example.inspe.datos.previsualizaciondatos;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class adaptadorprevisualizacion  extends RecyclerView.Adapter<adaptadorprevisualizacion.Myholder>{
    FirebaseUser user;
    FirebaseAuth auth;
    Context context;
    ArrayList<previsualizaciondatos> carrito1;

    public adaptadorprevisualizacion(Context context, ArrayList<previsualizaciondatos> carrito) {
        this.context = context;
        this.carrito1 = carrito;
    }

    @NonNull
    @Override
    public  Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.previsualisacion,parent,false);
        auth= FirebaseAuth.getInstance();
        user= auth.getCurrentUser();

        return new Myholder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull  Myholder holder, int position) {
        previsualizaciondatos carrito=carrito1.get(position);
        int total= Integer.parseInt(carrito1.get(position).getvalor());

        String t=String.valueOf(total);
        String url = carrito1.get(position).getimagen();

        holder.valor.setText("Valor :"+t);
        holder.pregunta.setText(carrito.getpregunta());
        holder.descripcion.setText(carrito.getcomentario());
        holder.respuesta.setText("Respuesta :"+carrito.getrespuesta1());
        Glide.with(context)
                .load(url)
                .into(holder.foto);
       // holder.nu.setText(carrito.getdepartamento());

    }




    @Override
    public int getItemCount() {
        return carrito1.size();
    }

    public class Myholder extends RecyclerView.ViewHolder {
        TextView pregunta,respuesta,valor, descripcion;
        ImageView foto;

         public Myholder(@NonNull View itemView) {
            super(itemView);
            pregunta=itemView.findViewById(R.id.pregunta);
            respuesta=itemView.findViewById(R.id.respuesta);
            valor=itemView.findViewById(R.id.valor);
            descripcion=itemView.findViewById(R.id.descripcion);
             foto=itemView.findViewById(R.id.imageView4);



        }

    }
}
