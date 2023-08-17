package com.example.inspe.adaptador

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.example.inspe.R
import com.example.inspe.datos.segunda2
import com.example.inspe.datos.segunda4
import com.example.inspe.datos.segunda5
import com.example.inspe.datos.segunda6
import com.example.inspe.datos.segunda7
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.ByteArrayOutputStream
import java.io.IOException


private val CAMERA_PERMISSION_REQUEST_CODE = 100

private var capturedImage: Bitmap? = null
private val CAMERA_REQUEST_CODE = 101

@SuppressLint("StaticFieldLeak")
var imageView7: ImageView? = null

class segunda7adaptador(private val segundo1: ArrayList<segunda7>, private val valor1dato: String, private val valor2dato: String,
                        private val context: Context
) : RecyclerView.Adapter<segunda7adaptador.Myholder>() {
    @SuppressLint("SuspiciousIndentation")
    var selectedImage: Bitmap? = null
    fun setImage(image: Bitmap) {
        selectedImage = image

        notifyDataSetChanged() // Actualiza el adaptador para reflejar el cambio en la imagen
    }
    fun bitmapToRequestBody(bitmap: Bitmap): RequestBody {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val imageBytes = byteArrayOutputStream.toByteArray()
        return RequestBody.create("image/jpeg".toMediaTypeOrNull(), imageBytes)
    }
    // Cambia la visibilidad a public

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myholder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.datos9,parent,false)

        return Myholder(itemView)
    }
    override fun onBindViewHolder(holder: Myholder, position: Int) {
        val segundo = segundo1[position]
        holder.departamento.text = segundo.pregunta
        val posicion: String? = segundo.posicion
        val valor1: Int? = segundo.valor
        val departamento: String? = segundo.departamento
        val pregunta2e2: String? = segundo.pregunta

        // Variable para realizar un seguimiento del botón seleccionado
        var botonSeleccionado: Button? = null

        holder.si.setOnClickListener {
            holder.no.isEnabled = false

            holder.na.isEnabled = false

            // Desmarcar el botón previamente seleccionado (si hay uno)
            // Marcar el botón actual
            auth = FirebaseAuth.getInstance()
            user = auth!!.currentUser
            val userId = user?.uid

            val formulario = valor2dato
            val r1 = "si" // Aquí debes obtener la respuesta seleccionada del adaptador
            val pregunta = posicion
            val pregunta2 = pregunta2e2

            val DatosUsuario = HashMap<String, Any>()
            DatosUsuario["userId"] = userId ?: ""  // Aquí guardamos el UID del usuario
            DatosUsuario["tienda"] = formulario
            DatosUsuario["respuesta1"] = r1
            DatosUsuario["valor"] = valor1?.toString() ?: ""
            DatosUsuario["departamento"] = departamento.toString()
            DatosUsuario["pregunta"] = pregunta2.toString()
            DatosUsuario["posicion"] = pregunta.toString()
            DatosUsuario["comentario"] = " "
            DatosUsuario["imagen"] = ""
            val database = FirebaseDatabase.getInstance()
            val reference = database.getReference("inspectores/"+userId+"/formulario/"+pregunta.toString())
            reference.setValue(DatosUsuario)
                .addOnSuccessListener {
                    // Maneja el éxito de la operación, si es necesario
                    println("Datos guardados exitosamente")
                }
                .addOnFailureListener { error ->
                    // Maneja el error en caso de que ocurra
                    println("Error al guardar los datos: $error")
                }
        }

        holder.no.setOnClickListener {

            if (botonSeleccionado != holder.no) {
                val alertDialogBuilder = AlertDialog.Builder(holder.itemView.context)
                alertDialogBuilder.setTitle("Comprobacion")
                val dialogView = LayoutInflater.from(holder.itemView.context)
                    .inflate(R.layout.dialog_content_layout, null)
                alertDialogBuilder.setView(dialogView)
                imageView = dialogView.findViewById(R.id.imageView)
                val editText = dialogView.findViewById<EditText>(R.id.editText)
                val captureButton = dialogView.findViewById<Button>(R.id.captureButton)
                val storageRef = FirebaseStorage.getInstance().reference

                alertDialogBuilder.setPositiveButton("Aceptar") { dialog, which ->
                    val image = imageView?.drawable?.toBitmap()
                    if (image != null) {
                        capturedImage = image

                        // Convierte la imagen capturada en un byte array
                        val outputStream = ByteArrayOutputStream()
                        capturedImage?.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                        val imageBytes = outputStream.toByteArray()

                        auth = FirebaseAuth.getInstance()
                        user = auth!!.currentUser
                        val userId = user?.uid

                        val imageName = "image_${System.currentTimeMillis()}.png"
                        val imagesRef = storageRef.child("/imagenes/$imageName")

                        imagesRef.putBytes(imageBytes)
                            .addOnSuccessListener { taskSnapshot ->
                                imagesRef.downloadUrl.addOnSuccessListener { uri ->
                                    val imageUrl = uri.toString()

                                    val formulario = valor2dato
                                    val r1 = "no"
                                    val pregunta = posicion
                                    val pregunta2 = pregunta2e2
                                    val DatosUsuario = HashMap<String, Any>()
                                    DatosUsuario["userId"] = userId ?: ""
                                    DatosUsuario["tienda"] = formulario
                                    DatosUsuario["respuesta1"] = r1
                                    DatosUsuario["valor"] = "0"
                                    DatosUsuario["departamento"] = departamento.toString()
                                    DatosUsuario["pregunta"] = pregunta2.toString()
                                    DatosUsuario["posicion"] = pregunta.toString()
                                    DatosUsuario["comentario"] = editText.text.toString()
                                    DatosUsuario["imagen"] = imageUrl

                                    val database = FirebaseDatabase.getInstance()
                                    val reference = database.getReference("inspectores/$userId/formulario/${pregunta.toString()}")
                                    reference.setValue(DatosUsuario)
                                        .addOnSuccessListener {
                                            println("Datos guardados exitosamente")
                                            holder.si.isEnabled = false
                                            holder.na.isEnabled = false
                                        }
                                        .addOnFailureListener { error ->
                                            println("Error al guardar los datos: $error")
                                        }
                                }
                            }
                            .addOnFailureListener { exception ->
                                println("Error al cargar la imagen: $exception")
                            }
                    }
                }

                alertDialogBuilder.setNegativeButton("Cancelar") { dialog, which ->
                    // Código para manejar la cancelación si es necesario
                }

                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()

                captureButton.setOnClickListener {
                    if (ContextCompat.checkSelfPermission(
                            holder.itemView.context,
                            Manifest.permission.CAMERA
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        if (cameraIntent.resolveActivity(holder.itemView.context.packageManager) != null) {
                            (holder.itemView.context as Activity).startActivityForResult(
                                cameraIntent,
                                CAMERA_REQUEST_CODE
                            )
                        }
                    } else {
                        ActivityCompat.requestPermissions(
                            holder.itemView.context as Activity,
                            arrayOf(Manifest.permission.CAMERA),
                            CAMERA_PERMISSION_REQUEST_CODE
                        )
                    }
                }
            }
        }

        holder.na.setOnClickListener {
            holder.si.isEnabled = false
            holder.no.isEnabled = false
            // Desmarcar el botón previamente seleccionado (si hay uno)
            // Marcar el botón actual
            auth = FirebaseAuth.getInstance()
            user = auth!!.currentUser
            val userId = user?.uid

            val formulario = valor2dato
            val r1 = "n/a" // Aquí debes obtener la respuesta seleccionada del adaptador
            val pregunta = posicion
            val pregunta2 = pregunta2e2

            val DatosUsuario = HashMap<String, Any>()
            DatosUsuario["userId"] = userId ?: ""  // Aquí guardamos el UID del usuario
            DatosUsuario["tienda"] = formulario
            DatosUsuario["respuesta1"] = r1
            DatosUsuario["valor"] = "3"
            DatosUsuario["departamento"] = departamento.toString()
            DatosUsuario["pregunta"] = pregunta2.toString()
            DatosUsuario["posicion"] = pregunta.toString()
            DatosUsuario["comentario"] = " "
            DatosUsuario["imagen"] = ""
            val database = FirebaseDatabase.getInstance()
            val reference = database.getReference("inspectores/"+userId+"/formulario/"+pregunta.toString())
            reference.setValue(DatosUsuario)
                .addOnSuccessListener {
                    // Maneja el éxito de la operación, si es necesario
                    println("Datos guardados exitosamente")
                }
                .addOnFailureListener { error ->
                    // Maneja el error en caso de que ocurra
                    println("Error al guardar los datos: $error")
                }
        }


    }

    override fun getItemCount(): Int {
        return  segundo1.size

    }
    class Myholder(itemView : View) : RecyclerView.ViewHolder(itemView){
 val departamento: TextView =itemView.findViewById(R.id.departamento)
        val si: Button =itemView.findViewById(R.id.respuestasi)
        val no: Button =itemView.findViewById(R.id.respuestano)
        val na: Button =itemView.findViewById(R.id.respuestana)

    }

}