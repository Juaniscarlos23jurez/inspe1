package com.example.inspe.dininternet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inspe.DataItem;
import com.example.inspe.R;
import com.example.inspe.adaptador.adaptadorprevisualizacion;
import com.example.inspe.datos.previsualizaciondatos;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class previdatossin  extends RecyclerView.Adapter<previdatossin.ViewHolder> {
    private List<DataItem> data;

    public previdatossin(List<DataItem> data) {
        this.data = data;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.previsualisacion, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
     public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataItem rowData = data.get(position);

        holder.textView.setText(rowData.getText());
        holder.valor.setText("Cantidad: " + rowData.getCantidad());
        holder.respuesta.setText("Respuesta: " + rowData.getRespuesta());
        holder.descripcion.setText("Comentario: " + rowData.getComentario());

        if (rowData.getImageBytes() != null) {
            byte[] imageBytes = rowData.getImageBytes();
            Bitmap imageBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.imageView4.setImageBitmap(imageBitmap);
            holder.imageView4.setVisibility(View.VISIBLE);
        } else {
            holder.imageView4.setVisibility(View.INVISIBLE);
        }
    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView,valor,respuesta,descripcion;
ImageView imageView4;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView4= itemView.findViewById(R.id.imageView4);
            textView = itemView.findViewById(R.id.pregunta);
            valor = itemView.findViewById(R.id.valor);
            respuesta = itemView.findViewById(R.id.respuesta);
            descripcion= itemView.findViewById(R.id.descripcion);
        }
    }
}