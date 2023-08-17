package com.example.inspe;

import static com.example.inspe.pdf.STORAGE_PERMISSION_CODE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.inspe.adaptador.DrawingView;
import com.example.inspe.adaptador.adaptadorprevisualizacion;
import com.example.inspe.datos.previsualizaciondatos;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class previsualizacion extends AppCompatActivity {
     FirebaseUser user;
    FirebaseAuth auth;
    RecyclerView car;
    adaptadorprevisualizacion carritoAdaptador;
    DatabaseReference base;
    DatabaseReference base1;
    private DatabaseReference databaseReference;

    ImageView imageView6;
    private SignaturePad signaturePad;
    private SignaturePad dibujooo;

    Button Finalizar,button10, button11,  button12 , button13;
    ArrayList<previsualizaciondatos> carrito1;
    TextView conteo;
    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previsualizacion);
         imageView6= findViewById(R.id.imageView6);
        auth = FirebaseAuth.getInstance();
        user= auth.getCurrentUser();
         String foto=getIntent().getStringExtra("foto");
        String imageUrl = ""+foto; // Reemplaza con la URL real de la imagen

        Glide.with(this)
                .load(imageUrl)
                 .into(imageView6);
        dibujooo = findViewById(R.id.drawing_container1);

        button12= findViewById(R.id.button12);
        button12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dibujooo.clear(); // Borra el dibujo
            }
        });
        button13= findViewById(R.id.button13);

        button13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Captura la firma como bitmap
                Bitmap signatureBitmap = dibujooo.getSignatureBitmap();

                // Convertir el bitmap en un array de bytes
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                signatureBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] data = baos.toByteArray();

                // Subir el bitmap a Firebase Storage
                StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                String imageName = "firma_" + System.currentTimeMillis() + ".png";
                StorageReference imageRef = storageRef.child("firma/" + imageName);
                UploadTask uploadTask = imageRef.putBytes(data);
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Aquí puedes obtener la URL de descarga de la imagen subida
                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String imageUrl = uri.toString();
                                String tiempo=getIntent().getStringExtra("tiempo");

                                base1= FirebaseDatabase.getInstance().getReference( "/empresa/enlace/"+user.getUid()+"/"+tiempo);
                                base1.child("firmaUrl2").setValue(imageUrl).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // Manejar el éxito de guardar la URL en la base de datos
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Manejar el error si falla la actualización en la base de datos
                                    }
                                });
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Manejar el error si la carga falla
                    }
                });
            }
        });

        Finalizar= findViewById(R.id.Finalizar);
        Finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // Cierra la actividad actual
                startActivity(new Intent(previsualizacion.this, huellajava.class));

            }
        });
        button10=findViewById(R.id.button10);
        signaturePad = findViewById(R.id.drawing_container);

        button11=findViewById(R.id.button11);
        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signaturePad.clear(); // Borra el dibujo
            }
        });




        button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Captura la firma como bitmap
                Bitmap signatureBitmap = signaturePad.getSignatureBitmap();

                // Convertir el bitmap en un array de bytes
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                signatureBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] data = baos.toByteArray();

                // Subir el bitmap a Firebase Storage
                StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                String imageName = "firma_" + System.currentTimeMillis() + ".png";
                StorageReference imageRef = storageRef.child("firma/" + imageName);

                UploadTask uploadTask = imageRef.putBytes(data);
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Aquí puedes obtener la URL de descarga de la imagen subida
                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String imageUrl = uri.toString();
                                String tiempo=getIntent().getStringExtra("tiempo");

                                base1= FirebaseDatabase.getInstance().getReference( "/empresa/enlace/"+user.getUid()+"/"+tiempo);
                                base1.child("firmaUrl").setValue(imageUrl).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // Manejar el éxito de guardar la URL en la base de datos
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Manejar el error si falla la actualización en la base de datos
                                    }
                                });
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Manejar el error si la carga falla
                    }
                });
            }
        });
         int numero = getIntent().getIntExtra("numero", 0);

        if(1 == numero){
            button11.setVisibility(View.GONE);
            button12.setVisibility(View.GONE);
            button10.setVisibility(View.GONE);
            signaturePad.setVisibility(View.GONE); // Ocultar el signaturePad
        }
        databaseReference = FirebaseDatabase.getInstance().getReference();

        contarValores();

          Button generatePdfButton = findViewById(R.id.pdf);
        generatePdfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(previsualizacion.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    generatePdf();
                } else {
                    ActivityCompat.requestPermissions(previsualizacion.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            STORAGE_PERMISSION_CODE);
                }
            }
        });


         car=findViewById(R.id.recyclerView);

        String datos1=getIntent().getStringExtra("dato");
        // Verificar si el usuario está autenticado
         base= FirebaseDatabase.getInstance().getReference( ""+datos1);
        //  base = FirebaseDatabase.getInstance().getReference("/empresa/formulario/horIHZJGDOSkBD54WRxbxtB2RdE2/13-08-2023 19:17:25");
        car.setHasFixedSize(true);
        car.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        carrito1 = new ArrayList<>();
        carritoAdaptador = new adaptadorprevisualizacion(this, carrito1);
        car.setAdapter(carritoAdaptador);

        base= FirebaseDatabase.getInstance().getReference( ""+datos1);
        car.setHasFixedSize(true);
        car.setLayoutManager(new LinearLayoutManager( this,LinearLayoutManager.VERTICAL,false));
        carrito1=new ArrayList<>();
        carritoAdaptador=new adaptadorprevisualizacion(this,carrito1);
        car.setAdapter(carritoAdaptador);
        base.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Vaciar la lista de datos
                carrito1.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    previsualizaciondatos carrito2 = dataSnapshot.getValue(previsualizaciondatos.class);
                    carrito1.add(carrito2);
                }
                carritoAdaptador.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
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


    private void generatePdf() {
        Document document = new Document();

        try {
            File pdfFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "pdffffff.pdf");
            PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            document.open();

            document.add(new Paragraph("Hello, this is a sample PDF generated using iText library!"));

            document.close();
            Toast.makeText(this, "PDF generated successfully!", Toast.LENGTH_SHORT).show();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error generating PDF", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                generatePdf();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void contarValores() {
        String tiempo=getIntent().getStringExtra("tiempo");

        databaseReference.child("empresa/formulario/"+user.getUid()+"/"+tiempo)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int totalValores = 0;
                        int totalValores2 = 0;
                        int totalValores3 = 0;
                        int totalValores4 = 0;

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String posision = snapshot.child("posision").getValue(String.class);
                            String valor = snapshot.child("valor").getValue(String.class);

                            if (posision != null && valor != null) {
                                int posisionInt = Integer.parseInt(posision);

                                // Verifica si el valor de "posision" está entre 1 y 20
                                if (posisionInt >= 1 && posisionInt <= 20) {
                                    totalValores += Integer.parseInt(valor);
                                } else if (posisionInt >= 21 && posisionInt <= 34) {
                                    totalValores2 += Integer.parseInt(valor);

                                }else if (posisionInt >= 35 && posisionInt <= 41) {
                                    totalValores3 += Integer.parseInt(valor);

                                }else if (posisionInt >= 42 && posisionInt <= 52) {
                                    totalValores4 += Integer.parseInt(valor);

                                }
                            }
                        }

                        // Actualiza el TextView con el total de valores
                        TextView conteo = findViewById(R.id.conteo);
                        conteo.setText("Total de seccion 1: " + totalValores);
                        TextView conteo2 = findViewById(R.id.conteo2);
                        conteo2.setText("Total de seccion 2: " + totalValores2);
                        TextView conteo3 = findViewById(R.id.conteo3);
                        conteo3.setText("Total de seccion 3: " + totalValores3);
                        TextView conteo4 = findViewById(R.id.conteo4);
                        conteo4.setText("Total de seccion 4: " + totalValores4);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Manejo de error si es necesario
                    }
                });
    }

}