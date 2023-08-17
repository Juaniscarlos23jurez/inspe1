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
import com.example.inspe.datos.segundo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class daptador1  extends RecyclerView.Adapter<daptador1.Myholder>
{
    FirebaseUser user;
    FirebaseAuth auth;
    String dirc;
    ///costructor
    private View.OnClickListener carritp;
    Context context;
    ArrayList<segundo> lista;
     public daptador1(Context context, ArrayList<segundo> lista) {
        this.context = context;
        this.lista = lista;
    }
    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.datos3,parent,false);
        auth= FirebaseAuth.getInstance();
        user= auth.getCurrentUser();
        return new Myholder(v);
    }

    @Override
    public void onBindViewHolder(Myholder holder, int position) {
        segundo datos=lista.get(position);

         holder.departamento.setText(datos.getPregunta());
        final String posicion = datos.getPosicion();
        final Integer valor1 = datos.getValor();
        final String departamento = datos.getDepartamento();
        Button botonSeleccionado = null;

        holder.si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.no.setEnabled(false);
                holder.na.setEnabled(false);



                //   String tienda = valor1dato;
                //  String formulario = valor2dato;
                    String r1 = "si"; // Aquí debes obtener la respuesta seleccionada del adaptador
                    String pregunta = posicion;
                    Map<String, Object> datosUsuario = new HashMap<>();
                //   datosUsuario.put("tienda", tienda);
                //   datosUsuario.put("formulario", formulario);
                    datosUsuario.put("respuesta1", r1);
                    datosUsuario.put("valor", valor1 != null ? valor1.toString() : "");
                    datosUsuario.put("departamento", departamento != null ? departamento : "");
                    datosUsuario.put("pregunta", pregunta != null ? pregunta : "");
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference reference = database.getReference("inspectores/" + (user != null ? user.getUid() : "") + "/formlario");
                    DatabaseReference reference2 = database.getReference("/inspectores/" + (user != null ? user.getUid() : "") + "/formlario");

                    reference2.setValue(datosUsuario)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Maneja el éxito de la operación, si es necesario
                                    System.out.println("Datos guardados exitosamente");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Maneja el error en caso de que ocurra
                                    System.out.println("Error al guardar los datos: " + e.getMessage());
                                }
                            });

            }
        });

        holder.na.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.si.setEnabled(false);
                holder.na.setEnabled(false);
            }
        });
    }



    @Override
    public int getItemCount()
    {
        return lista.size();
    }
    public void setOnClickListener(View.OnClickListener carritp) {
        this.carritp=carritp;
    }
    class Myholder extends RecyclerView.ViewHolder{
        TextView departamento;

        Button si,no  ,na;

        public Myholder(@NonNull View itemView) {
            super(itemView);
            departamento=itemView.findViewById(R.id.departamento);
            si=itemView.findViewById(R.id.respuestasi);
            no=itemView.findViewById(R.id.respuestano);
            na=itemView.findViewById(R.id.respuestana);

        }
    }


}
