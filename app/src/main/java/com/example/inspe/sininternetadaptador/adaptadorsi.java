package com.example.inspe.sininternetadaptador;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inspe.DatabaseHelper;
import com.example.inspe.MainActivity5;
import com.example.inspe.R;
import com.example.inspe.dininternet.datos;

import java.io.ByteArrayOutputStream;
import java.util.List;


public class adaptadorsi extends RecyclerView.Adapter<adaptadorsi.NotaViewHolder> {
private List<datos> notasList;
    private static final int CAMERA_REQUEST_CODE = 101;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 200;
    private boolean botonHabilitado = true;

    // Variable para almacenaprivate boolean botonHabilitado = true;r la imagen capturada
    Bitmap capturedImage;
    ImageView imageView;
    private SQLiteDatabase database;
    private Context context;
    public adaptadorsi(List<datos> notasList, Context context) { // Modificar el constructor
        this.notasList = notasList;
        this.context = context; // Inicializar el contexto
    }
    private boolean isButtonClickable = true;
    private static final long CLICK_DELAY = 1000;
@NonNull
@Override
public NotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.datos3, parent, false);
        return new NotaViewHolder(itemView);

        }

@Override
public void onBindViewHolder(@NonNull NotaViewHolder holder, int position) {
    datos nota = notasList.get(position);
    holder.departamento.setText(nota.getTitulo());
            String pregunta = nota.getTitulo();
    String  departamentobd = nota.getContenido();
    int  valor = nota.getvalor();
    int  posiion = nota.getId();
 
    DatabaseHelper dbHelper = new DatabaseHelper(context);

    boolean isAlreadyAnswered = dbHelper.checkIfAnsweredWithPsisi(1);
    holder.si.setEnabled(true);
    holder.no.setEnabled(true);
    holder.na.setEnabled(true);
// Deshabilitar los botones si ya se ha respondido con psisi = 1
    if (isAlreadyAnswered && posiion == 1) {
        holder.si.setEnabled(false);
        holder.no.setEnabled(false);
        holder.na.setEnabled(false);
    }
    if (isAlreadyAnswered && posiion == 2) {
        holder.si.setEnabled(false);
        holder.no.setEnabled(false);
        holder.na.setEnabled(false);
    }
    if (isAlreadyAnswered && posiion == 3) {
        holder.si.setEnabled(false);
        holder.no.setEnabled(false);
        holder.na.setEnabled(false);
    }
    if (isAlreadyAnswered && posiion == 4) {
        holder.si.setEnabled(false);
        holder.no.setEnabled(false);
        holder.na.setEnabled(false);
    }
    if (isAlreadyAnswered && posiion == 5) {
        holder.si.setEnabled(false);
        holder.no.setEnabled(false);
        holder.na.setEnabled(false);
    }
    if (isAlreadyAnswered && posiion == 6) {
        holder.si.setEnabled(false);
        holder.no.setEnabled(false);
        holder.na.setEnabled(false);
    }
    if (isAlreadyAnswered && posiion == 7) {
        holder.si.setEnabled(false);
        holder.no.setEnabled(false);
        holder.na.setEnabled(false);
    }
    if (isAlreadyAnswered && posiion == 8) {
        holder.si.setEnabled(false);
        holder.no.setEnabled(false);
        holder.na.setEnabled(false);
    }
    if (isAlreadyAnswered && posiion == 9) {
        holder.si.setEnabled(false);
        holder.no.setEnabled(false);
        holder.na.setEnabled(false);
    }
    if (isAlreadyAnswered && posiion == 10) {
        holder.si.setEnabled(false);
        holder.no.setEnabled(false);
        holder.na.setEnabled(false);
    }
    if (isAlreadyAnswered && posiion == 11) {
        holder.si.setEnabled(false);
        holder.no.setEnabled(false);
        holder.na.setEnabled(false);
    }
    if (isAlreadyAnswered && posiion == 12) {
        holder.si.setEnabled(false);
        holder.no.setEnabled(false);
        holder.na.setEnabled(false);
    }
    if (isAlreadyAnswered && posiion == 13) {
        holder.si.setEnabled(false);
        holder.no.setEnabled(false);
        holder.na.setEnabled(false);
    }if (isAlreadyAnswered && posiion == 14) {
        holder.si.setEnabled(false);
        holder.no.setEnabled(false);
        holder.na.setEnabled(false);
    }if (isAlreadyAnswered && posiion == 15) {
        holder.si.setEnabled(false);
        holder.no.setEnabled(false);
        holder.na.setEnabled(false);
    }

    holder.si.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (botonHabilitado) {
                // Deshabilita el botón para evitar clics múltiples
                botonHabilitado = false;


                String text = pregunta;
                String departamento = departamentobd;
                int cantidad = valor;
                String respuesta = "si";
                String comentario = "";
                int psisi = posiion;
                byte[] imageBytes = "".getBytes();



                dbHelper.insertImageAndText(text, departamento, cantidad, psisi, respuesta, comentario, imageBytes);

                // Usar un Handler para habilitar el botón después del intervalo de tiempo
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        botonHabilitado = true;
                    }
                }, 3000); // 3000 milisegundos (3 segundos)
            }
        }

    });


    holder.no.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            // Crear un Intent para lanzar ManActivity5
            Intent intent = new Intent(v.getContext(), MainActivity5.class);

            // Agregar los datos como extras al Intent
            intent.putExtra("pregunta", pregunta);
            intent.putExtra("departamento", departamentobd);
            intent.putExtra("posicion", posiion);

            // Iniciar la actividad ManActivity5 con el Intent
            v.getContext().startActivity(intent);
         }
    });

    holder.na.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            String text =  pregunta;
            String depatemantos =  departamentobd;
            int cantidad=0;
            String respusta = "na";
            String Comentario = "";
            int psisi=posiion;

            byte[] imageBytes = "".getBytes();

            dbHelper.insertImageAndText(text, depatemantos, cantidad, psisi ,respusta, Comentario,imageBytes);
        }
    });

    }



    private void insertarEnBaseDeDatos(String departamento, int valor, int posicion) {
        ContentValues values = new ContentValues();
        values.put("departamento", departamento);
        values.put("valor", valor);
        values.put("posicion", posicion);

        long newRowId = database.insert("NombreTabla", null, values);

         if (newRowId != -1) {
            Toast.makeText(context, "Datos guardados en la base de datos", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Error al guardar los datos", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
public int getItemCount() {
        return notasList.size();
        }

public class NotaViewHolder extends RecyclerView.ViewHolder {
    TextView departamento;
    Button si;
    Button no;
    Button na;

    public NotaViewHolder(View itemView) {
        super(itemView);
        departamento = itemView.findViewById(R.id.departamento);
        si = itemView.findViewById(R.id.respuestasi);
        no = itemView.findViewById(R.id.respuestano);
        na = itemView.findViewById(R.id.respuestana);
    }
}

}
