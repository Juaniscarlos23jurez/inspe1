package com.example.inspe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.inspe.datos.MainActivityaut
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)
        lateinit var edit: EditText
        lateinit var entrar: Button
        lateinit var database: DatabaseReference

        edit = findViewById(R.id.correote2)
        entrar = findViewById(R.id.button)
        database = FirebaseDatabase.getInstance().reference

        entrar.setOnClickListener {
            val code = edit.text.toString()

            // Consultar el código en Firebase Realtime Database
            val codeRef = database.child("/códigos").orderByChild("código").equalTo(code)
            codeRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (snapshot in dataSnapshot.children) {
                            val codigo = snapshot.child("código").value.toString()
                            val activo = snapshot.child("activo").value.toString()
                            val ruta = snapshot.child("ruta").value.toString()
                            if (activo == "si") {
                                if (codigo == code ){
                                    snapshot.ref.child("activo").setValue("si")

                                    val intent = Intent(this@MainActivity4, MainActivityaut::class.java)
                                    intent.putExtra("ruta", ruta )

                                    startActivity(intent)

                                }
                                // El código existe y está activo, navegar a la siguiente actividad
                                // Aquí debes iniciar la nueva actividad deseada
                                 return
                            } else {
                                // El código existe pero no está activo
                                Toast.makeText(applicationContext, "Código no activo", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        // El código no existe
                        Toast.makeText(applicationContext, "Código inválido", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Manejo de errores en caso de que ocurra algún problema de conexión
                    Toast.makeText(applicationContext, "Error de conexión", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}