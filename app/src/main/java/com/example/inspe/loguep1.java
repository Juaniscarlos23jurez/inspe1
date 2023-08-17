package com.example.inspe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class loguep1 extends AppCompatActivity {
    TextView pas;
    TextView  correote1;
    Button buttonlo2,button,buttons5;
    FirebaseAuth auth;
    String imaghenr;
    String datoid ;

    private ProgressDialog progressDialog;
    FirebaseUser user;
    private void showProgressDialog() {
        progressDialog = new ProgressDialog(loguep1.this);
        progressDialog.setMessage("Cargando...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loguep1);
        // valor
         correote1= findViewById(R.id.correote);
        pas= findViewById(R.id.numero);
        button= findViewById(R.id.button);
        auth= FirebaseAuth.getInstance();
        user= auth.getCurrentUser();
         //funcion
        String datos = getIntent().getStringExtra("nombreUsuario");
// Supongamos que ya tienes una referencia a la base de datos
        String Email2 = getIntent().getStringExtra("Email2");
        DatabaseReference id = FirebaseDatabase.getInstance().getReference("/inspectores/"+datos + "/Uid");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("/inspectores/"+datos + "/Nombre");
        DatabaseReference Passwordbd = FirebaseDatabase.getInstance().getReference("/inspectores/"+datos + "/Password");
        DatabaseReference correobd = FirebaseDatabase.getInstance().getReference("/inspectores/"+datos + "Email2");
        DatabaseReference nemoplradobd = FirebaseDatabase.getInstance().getReference("/inspectores/"+datos + "/Apellido");
        DatabaseReference FotoURL = FirebaseDatabase.getInstance().getReference("/inspectores/"+datos + "/FotoURL");
        correote1.setText(Email2);
// Obtén una referencia al EditText en tu actividad
        TextView editTextMostrarDato = findViewById(R.id.Nombre);
        TextView codigo = findViewById(R.id.codigo);
        ImageView imageView5 =findViewById(R.id.imageView5);
        buttons5= findViewById(R.id.buttons5);

        id.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String valor = dataSnapshot.getValue(String.class);
                // Establecer el valor en el EditText
                datoid = valor;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
                Toast.makeText(loguep1.this, "Error al obtener el dato", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(loguep1.this, "Error al obtener el dato", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(loguep1.this, "Error al obtener el dato", Toast.LENGTH_SHORT).show();
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
                Glide.with(loguep1.this)
                        .load(imageUrl)
                        .into(imageView5);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Este método se ejecuta si hay un error al obtener los datos desde la base de datos
                Toast.makeText(loguep1.this, "Error al obtener la URL de la imagen", Toast.LENGTH_SHORT).show();
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
                    LogeoUsuario(correo, pasw,numeroem,nombre,imagenpa,datoid);
                }
            }
        });
        buttons5.setOnClickListener(new View.OnClickListener() {
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
                    LogeoUsuario1(correo, pasw,numeroem,nombre,imagenpa,datoid);
                }
            }
        });
    }

    private void LogeoUsuario1(String correo, String pasw, String numeroem, String nombre, String imagenpa, String datoid) {
        auth.signInWithEmailAndPassword(correo, pasw)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            user = auth.getCurrentUser();
                            showProgressDialog();
                            Toast.makeText(loguep1.this, "Inicio de session", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(loguep1.this, edicionem.class));
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(loguep1.this, "CORREO O CONTRASEÑA INCORECTA"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    ///metodo para el logueo de los suarios
    private void LogeoUsuario(String correo, String pasw , String numeroem, String nombre, String imagenpa, String datoid) {
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

        auth.signInWithEmailAndPassword(correo, pasw)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                         if (task.isSuccessful()){
                            user = auth.getCurrentUser();
                             showProgressDialog();
                             Toast.makeText(loguep1.this, "Inicio de session", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(loguep1.this, huellajava.class));
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(loguep1.this, "CORREO O CONTRASEÑA INCORECTA"+e.getMessage(), Toast.LENGTH_SHORT).show();
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
