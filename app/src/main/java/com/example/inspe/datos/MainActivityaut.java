package com.example.inspe.datos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.biometrics.BiometricManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.inspe.R;
import com.example.inspe.loguep1;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.Executor;

import android.Manifest;

public class MainActivityaut extends AppCompatActivity {
    Button   button;
    EditText Nombre,codigo,correote,numero,codigofire;
    FirebaseAuth auth;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    private static final int CAMERA_REQUEST_CODE = 101;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    // Obtén una referencia al Storage
    StorageReference storageRef = storage.getReference();
    ImageView imageView;
    int  valoe;
    private Bitmap capturedImage ;
    private ProgressDialog progressDialog;
    DatabaseReference fingerprintRef;
    DatabaseReference database;
    private void showProgressDialog() {
        progressDialog = new ProgressDialog(MainActivityaut.this);
        progressDialog.setMessage("Cargando...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activityaut);
        Nombre = findViewById(R.id.Nombre);
        codigo = findViewById(R.id.codigo);
        correote = findViewById(R.id.correote);
        numero = findViewById(R.id.numero);
        button= findViewById(R.id.button);
        imageView =findViewById(R.id.imageView5);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
         FloatingActionButton agreim = findViewById(R.id.floatingActionButton);
        agreim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(MainActivityaut.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    // El permiso ya ha sido concedido
                    // Puedes iniciar la funcionalidad de la cámara aquí
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
                } else {
                    // El permiso aún no ha sido concedido, así que solicítalo
                    ActivityCompat.requestPermissions(
                            MainActivityaut.this,
                            new String[]{Manifest.permission.CAMERA},
                            CAMERA_PERMISSION_REQUEST_CODE
                    );
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = correote.getText().toString();
                String numerodeemple = codigo.getText().toString();
                String nombre = Nombre.getText().toString();
                String numerodetele = numero.getText().toString();
                // imageView
                if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                    correote.setError("Correo no válido");
                    correote.requestFocus();
                } else if (numerodetele.length() != 10) {
                    numero.setError("El número debe tener 10 dígitos");
                    numero.requestFocus();
                } else if (nombre.trim().isEmpty() || numerodeemple.trim().isEmpty()) {
                    Toast.makeText(MainActivityaut.this, "Rellene todos los datos", Toast.LENGTH_SHORT).show();
                } else if (capturedImage == null) {
                    // No se ha subido una imagen
                    Toast.makeText(MainActivityaut.this, "Por favor, agrega una imagen primero", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    // Si pasa todas las validaciones, mostrar el diálogo
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivityaut.this);
                    builder.setTitle("Aviso");
                    builder.setMessage("La foto no se puede editar es importante que verifique que los datos sean correctos");

                    builder.setPositiveButton("Los datos son correctos", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            RegistrarUsuario(Email, numerodetele, nombre, numerodeemple);
                            // uploadImageToFirebase();
                            dialogInterface.dismiss();
                        }
                    });

                    builder.setNegativeButton("Editar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // El permiso fue concedido por el usuario
                // No se necesita ninguna acción adicional aquí
            } else {
                // El permiso fue denegado por el usuario
                // Aquí puedes mostrar un mensaje o realizar alguna otra acción
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            if (image != null) {
                capturedImage = image;
                imageView.setImageBitmap(capturedImage);
            } else {
                Toast.makeText(MainActivityaut.this, "erro", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void RegistrarUsuario(String Email, String numerodetele, String nombre, String numerodeemple) {
        auth = FirebaseAuth.getInstance();
        if (capturedImage != null) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                .child("imagenes")
                .child(Email);

        // Convertir la imagen capturada a un byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        capturedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageData = baos.toByteArray();

        // Subir la imagen a Firebase Storage
        UploadTask uploadTask = storageReference.putBytes(imageData);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // La imagen se subió exitosamente
                // Ahora puedes obtener la URL de descarga de la imagen

                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri downloadUri) {
                        // Aquí puedes obtener la URL de descarga de la imagen
                        String imageUrl = downloadUri.toString();
                        auth.createUserWithEmailAndPassword(Email, numerodetele)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            FirebaseUser user = auth.getCurrentUser();
                                            assert user != null;
                                            DatabaseReference contadorRef = FirebaseDatabase.getInstance().getReference().child("contador");

                                            String uidString = user.getUid();
                                            HashMap<String, Object> DatosUsuario = new HashMap<>();
                                            DatosUsuario.put("Uid", uidString);
                                            DatosUsuario.put("Email", Email);
                                            DatosUsuario.put("Password", numerodetele);
                                            DatosUsuario.put("Nombre", nombre);
                                            DatosUsuario.put("Apellido", numerodeemple);
                                             DatosUsuario.put("FotoURL", imageUrl); // Agregar la URL de la foto
                                            DatosUsuario.put("Email2", Email); // Agregar la URL de la foto

                                            FirebaseDatabase database= FirebaseDatabase.getInstance();
                                            DatabaseReference reference = database.getReference("inspectores");
                                            reference.child(uidString).setValue(DatosUsuario).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        showProgressDialog();
                                                        Intent intent = new Intent(MainActivityaut.this, loguep1.class);
                                                        intent.putExtra("nombreUsuario", uidString);
                                                        intent.putExtra("Email2", Email);
                                                        startActivity(intent);
                                                        String ruta = getIntent().getStringExtra("ruta");
                                                        DatabaseReference databaseReference11 = FirebaseDatabase.getInstance().getReference(ruta + "/activo");
                                                        databaseReference11.setValue("no");
                                                        Toast.makeText(MainActivityaut.this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
                                                        finish();
                                                    } else {
                                                        Toast.makeText(MainActivityaut.this, "Error usuario ya regisrado", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        } else {
                                            // Error al crear el usuario
                                            Toast.makeText(MainActivityaut.this, "Error al crear el usuario", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Error en el proceso de registro
                                        Toast.makeText(MainActivityaut.this, "Error en el proceso de registro", Toast.LENGTH_SHORT).show();
                                    }
                                });
                     }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Ocurrió un error al obtener la URL de descarga de la imagen
                        Toast.makeText(MainActivityaut.this, "Error al obtener la URL de descarga de la imagen.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Ocurrió un error al subir la imagen
                Toast.makeText(MainActivityaut.this, "Error al subir la imagen a  Storage.", Toast.LENGTH_SHORT).show();
            }
        });
        }

    }    // Método para obtener el URI de una imagen en formato Bitmap
    private void uploadImageToFirebase() {

                 DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("/fotos");

                // Verificar si la imagen ya ha sido capturada
                if (capturedImage != null) {
                    // Crear una referencia al storage en Firebase usando una ruta única
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                            .child("imagenes")
                            .child("imagene");

                    // Convertir la imagen capturada a un byte array
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    capturedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageData = baos.toByteArray();

                    // Subir la imagen a Firebase Storage
                    UploadTask uploadTask = storageReference.putBytes(imageData);
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // La imagen se subió exitosamente
                            // Ahora puedes obtener la URL de descarga de la imagen

                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri downloadUri) {
                                    // Aquí puedes obtener la URL de descarga de la imagen
                                    String imageUrl = downloadUri.toString();


                                    databaseReference.child("imagenes").push().setValue(imageUrl);

                                    Toast.makeText(MainActivityaut.this, "Imagen guardada exitosamente en  Storage.", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Ocurrió un error al obtener la URL de descarga de la imagen
                                    Toast.makeText(MainActivityaut.this, "Error al obtener la URL de descarga de la imagen.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Ocurrió un error al subir la imagen
                            Toast.makeText(MainActivityaut.this, "Error al subir la imagen a  Storage.", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(MainActivityaut.this, "Captura una imagen primero.", Toast.LENGTH_SHORT).show();
                }
            }


 }


