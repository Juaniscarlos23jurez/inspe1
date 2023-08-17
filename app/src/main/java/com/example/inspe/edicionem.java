package com.example.inspe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.inspe.datos.MainActivityaut;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class edicionem extends AppCompatActivity {
    EditText pas;
    EditText  correote1;
    Button button2,button,button5;
    FirebaseAuth auth;
    String imaghenr;
    private ProgressDialog progressDialog;
    FirebaseUser user;
    private void showProgressDialog() {
        progressDialog = new ProgressDialog(edicionem.this);
        progressDialog.setMessage("Cargando...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicionem);
         // valor
        correote1= findViewById(R.id.correote);
        pas= findViewById(R.id.numero);
        button= findViewById(R.id.button);
        button2= findViewById(R.id.button2);
        button.setEnabled(false);
         auth= FirebaseAuth.getInstance();
        user= auth.getCurrentUser();
        //funcion
          DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("/inspectores/"+ user.getUid() + "/Nombre");
        DatabaseReference Passwordbd = FirebaseDatabase.getInstance().getReference("/inspectores/"+ user.getUid() + "/Password");
        DatabaseReference correobd = FirebaseDatabase.getInstance().getReference("/inspectores/"+ user.getUid() + "/Email");
        DatabaseReference nemoplradobd = FirebaseDatabase.getInstance().getReference("/inspectores/"+ user.getUid() + "/Apellido");
        DatabaseReference FotoURL = FirebaseDatabase.getInstance().getReference("/inspectores/"+ user.getUid() + "/FotoURL");
 // Obtén una referencia al EditText en tu actividad
        EditText editTextMostrarDato = findViewById(R.id.Nombre);
        EditText codigo = findViewById(R.id.codigo);
        ImageView imageView5 =findViewById(R.id.imageView5);
        correobd.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Este método se ejecuta cuando el valor en la base de datos cambia
                // Recuperar el valor desde la base de datos
                String valor = dataSnapshot.getValue(String.class);
                // Establecer el valor en el EditText
                correote1.setText(valor);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Este método se ejecuta si hay un error al obtener los datos desde la base de datos
                Toast.makeText(edicionem.this, "Error al obtener el dato", Toast.LENGTH_SHORT).show();
            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Este método se ejecuta cuando el valor en la base de datos cambia
                // Recuperar el valor desde la base de datos
                String valor = dataSnapshot.getValue(String.class);
                // Establecer el valor en el EditText
                editTextMostrarDato.setText(valor);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Este método se ejecuta si hay un error al obtener los datos desde la base de datos
                Toast.makeText(edicionem.this, "Error al obtener el dato", Toast.LENGTH_SHORT).show();
            }
        });
        nemoplradobd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Este método se ejecuta cuando el valor en la base de datos cambia

                // Recuperar el valor desde la base de datos
                String valor = dataSnapshot.getValue(String.class);

                // Establecer el valor en el EditText
                codigo.setText(valor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Este método se ejecuta si hay un error al obtener los datos desde la base de datos
                Toast.makeText(edicionem.this, "Error al obtener el dato", Toast.LENGTH_SHORT).show();
            }
        });
        Passwordbd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Este método se ejecuta cuando el valor en la base de datos cambia

                // Recuperar el valor desde la base de datos
                String valor = dataSnapshot.getValue(String.class);

                // Establecer el valor en el EditText
                pas.setText(valor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Este método se ejecuta si hay un error al obtener los datos desde la base de datos
                Toast.makeText(edicionem.this, "Error al obtener el dato", Toast.LENGTH_SHORT).show();
            }
        });
        FotoURL.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Este método se ejecuta cuando el valor en la base de datos cambia

                // Recuperar el valor (URL) desde la base de datos
                String imageUrl = dataSnapshot.getValue(String.class);
                imaghenr=imageUrl;
                // Usar Glide para cargar la imagen desde la URL y establecerla en el ImageView
                Glide.with(edicionem.this)
                        .load(imageUrl)
                        .into(imageView5);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Este método se ejecuta si hay un error al obtener los datos desde la base de datos
                Toast.makeText(edicionem.this, "Error al obtener la URL de la imagen", Toast.LENGTH_SHORT).show();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correo =  correote1.getText().toString();
                String pasw = pas.getText().toString();
                String nombre = editTextMostrarDato.getText().toString();
                String numeroem = codigo.getText().toString();
                String imagenpa = imaghenr;
                button.setEnabled(true);
                // Deshabilitar el botón 2
                BitmapDrawable drawable = (BitmapDrawable) imageView5.getDrawable();
                Bitmap imageBitmap = drawable.getBitmap();

                    actualizacion1(correo, pasw,numeroem,nombre,imagenpa);
                button2.setEnabled(false);

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //obtencion
                String correo =  correote1.getText().toString();
                String pasw = pas.getText().toString();
                String nombre = editTextMostrarDato.getText().toString();
                String numeroem = codigo.getText().toString();
                String imagenpa = imaghenr;

                BitmapDrawable drawable = (BitmapDrawable) imageView5.getDrawable();
                Bitmap imageBitmap = drawable.getBitmap();
                /////copia de el registro if ( validacion )
                if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                    correote1.setError("correo no valido");
                    correote1.setFocusable(true);
                }
                else {
                    actualizacion(correo, pasw,numeroem,nombre,imagenpa);
                }
            }
        });
    }

    private void actualizacion1(String correo, String pasw, String numeroem, String nombre, String imagenpa) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://yelsan.com.mx/usuarios.php";

        // correo, numero, nombre, telefono, foto
        FormBody formBody = new FormBody.Builder()
                .add("correo", correo)
                .add("nombre", nombre)
                .add("numero", numeroem)
                .add("telefono",pasw)
                .add("ruta", imagenpa)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Manejar el error de la solicitud HTTP
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // Manejar la respuesta de la solicitud HTTP
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    // Manejar la respuesta del servidor
                } else {
                    // Manejar el error de respuesta del servidor
                }
            }
        });
    }

    ///metodo para el logueo de los suarios
    private void actualizacion(String correo, String pasw, String numeroem, String nombre, String imagenpa) {

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("/inspectores/" + user.getUid());

        // Crear un HashMap con los campos y sus nuevos valores
        Map<String, Object> actualizacionDatos = new HashMap<>();
        actualizacionDatos.put("Email", correo);
        actualizacionDatos.put("Password", pasw);
        actualizacionDatos.put("Apellido", numeroem);
        actualizacionDatos.put("Nombre", nombre);

        // Realizar la actualización en la base de datos
        databaseRef.updateChildren(actualizacionDatos)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Actualización exitosa
                        // Aquí puedes realizar cualquier acción adicional tras la actualización

                        // Moverse a otra actividad (página) cuando la actualización sea exitosa
                        Intent intent = new Intent(edicionem.this, huellajava.class);
                        startActivity(intent);
                        finish(); // Opcional: finaliza la actividad actual para que no se pueda volver atrás
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Ocurrió un error al actualizar los datos
                        // Manejar el error según tus necesidades
                    }
                });
    }
    @Override
    public void onBackPressed() {
        // Mostrar el alert dialog para informar al usuario que no puede regresar
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("No puedes regresar desde aquí. ¿Quieres continuar?")

                .setNegativeButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Si el usuario cancela, no se hace nada y el diálogo se cierra
                    }
                });
        // Crear y mostrar el alert dialog
        builder.create().show();
    }
    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] byteArray = baos.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}
