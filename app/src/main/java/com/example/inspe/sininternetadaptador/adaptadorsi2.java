package com.example.inspe.sininternetadaptador;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inspe.DatabaseHelper;
import com.example.inspe.R;
import com.example.inspe.dininternet.datos;
import com.example.inspe.dininternet.datos2;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class adaptadorsi2 extends RecyclerView.Adapter<adaptadorsi2.NotaViewHolder> {
private List<datos2> notasList2;

    private SQLiteDatabase database; // Declarar la instancia de la base de datos
    private Context context; // Agregar esta variable

    public adaptadorsi2(List<datos2> notasList2, Context context) { // Modificar el constructor
        this.notasList2 = notasList2;
        this.context = context; // Inicializar el contexto
    }

@NonNull
@Override
public NotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.datos5, parent, false);
        return new NotaViewHolder(itemView);

        }

@Override
public void onBindViewHolder(@NonNull NotaViewHolder holder, int position) {
    datos2 nota = notasList2.get(position);
    holder.departamento.setText(nota.getTitulo());
            String pregunta = nota.getTitulo();
    String  departamentobd = nota.getContenido();
    int  valor = nota.getvalor();
    int  posiion = nota.getId();
    holder.si.setEnabled(true);
    holder.no.setEnabled(true);
    holder.na.setEnabled(true);
    DatabaseHelper dbHelper = new DatabaseHelper(context);

    holder.si.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String text =  pregunta;
            String depatemantos =  departamentobd;
            int cantidad=valor;
            String respusta = "si";
            String Comentario = "";
            int psisi=posiion;
            byte[] imageBytes = "".getBytes();

            dbHelper.insertImageAndText(text, depatemantos, cantidad, psisi ,respusta, Comentario,imageBytes);

            holder.no.setEnabled(false);
            holder.na.setEnabled(false);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    holder.no.setEnabled(true);
                    holder.na.setEnabled(true);
                }
            }, 30000); // 60000 milisegundos = 1 minuto
        }
    });
    holder.no.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(holder.itemView.getContext());
            alertDialogBuilder.setTitle("Comprobacion");
            View dialogView = LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.dialog_content_layout, null);
            alertDialogBuilder.setView(dialogView);
            //imageView111 = dialogView.findViewById(R.id.imageView);
            EditText editText = dialogView.findViewById(R.id.editText);
            Button captureButton = dialogView.findViewById(R.id.captureButton);

            alertDialogBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String text = pregunta;
                    String departamentos = departamentobd;
                    int cantidad = 0;
                    String respuesta = "no";
                    String comentario = editText.getText().toString(); // Obtener el texto del EditText
                    int psisi = posiion;

                    // Aquí deberías utilizar la imagen capturada previamente y guardada en imageView111
                 //   Bitmap image = ((BitmapDrawable) imageView111.getDrawable()).getBitmap();

                    // Convertir la imagen a un arreglo de bytes (byte array) para guardarla en la base de datos
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                //    image.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] imageBytes = stream.toByteArray();

                    // Guardar la imagen y los demás datos en la base de datos
                    dbHelper.insertImageAndText(text, departamentos, cantidad, psisi, respuesta, comentario, imageBytes);

                    holder.si.setEnabled(false);
                    holder.na.setEnabled(false);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            holder.si.setEnabled(true);
                            holder.na.setEnabled(true);
                        }
                    }, 30000); // 60000 milisegundos = 1 minuto
                }
            });

            alertDialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Hacer algo cuando se cancela
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

            captureButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ContextCompat.checkSelfPermission(holder.itemView.getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (cameraIntent.resolveActivity(holder.itemView.getContext().getPackageManager()) != null) {
                 //           ((Activity) holder.itemView.getContext()).startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
                        }
                    } else {
                 //       ActivityCompat.requestPermissions((Activity) holder.itemView.getContext(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
                    }
                }
            });
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
            holder.si.setEnabled(false);
            holder.no.setEnabled(false);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    holder.no.setEnabled(true);
                    holder.si.setEnabled(true);
                }
            }, 30000); // 60000 milisegundos = 1 minuto


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
        return notasList2.size();
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