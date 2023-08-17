package com.example.inspe

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
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
import com.example.inspe.dininternet.datos
import com.google.firebase.auth.FirebaseAuth
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream


@JvmField
var imageView: ImageView = TODO("initialize me")
private val CAMERA_PERMISSION_REQUEST_CODE = 100

private var capturedImage: Bitmap? = null
private val CAMERA_REQUEST_CODE = 101

@SuppressLint("StaticFieldLeak")
 private val resultadosNa = ArrayList<String>()

class kotlin1(private val notasList: ArrayList<datos>, private val valor1dato: String, private val valor2dato: String,
              private val context: Context
) : RecyclerView.Adapter<kotlin1.Myholder>() {

    @SuppressLint("SuspiciousIndentation")
    var selectedImage: Bitmap? = null
    fun setImage(image: Bitmap) {
        selectedImage = image
        fun getResultados(): ArrayList<String> {
            return resultadosNa
        }

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
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.datos3,parent,false)
        return Myholder(itemView)


    }
    override fun onBindViewHolder(holder: Myholder, position: Int) {
        val nota: datos = notasList.get(position)
        holder.departamento.text = nota.titulo
        val pregunta = nota.titulo
        val departamentobd = nota.contenido
        val valor = nota.getvalor()
        val posiion = nota.id
        val botonSeleccionado: Button? = null

        // Restablecer el estado de los botones
        holder.si.isEnabled = true
        holder.no.isEnabled = true
        holder.na.isEnabled = true

        holder.si.setOnClickListener {


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

                alertDialogBuilder.setPositiveButton("Aceptar") { dialog, which ->
                    val image = imageView?.drawable?.toBitmap()
                    if (image != null) {
                        capturedImage = image

                        // Convierte la imagen capturada en un byte array
                        val outputStream = ByteArrayOutputStream()
                        capturedImage?.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                        val imageBytes = outputStream.toByteArray()

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
        }

    }
    override fun getItemCount(): Int {
        return  notasList.size
    }


    class Myholder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val departamento: TextView =itemView.findViewById(R.id.departamento)
        val si: Button =itemView.findViewById(R.id.respuestasi)
        val no: Button =itemView.findViewById(R.id.respuestano)
        val na: Button =itemView.findViewById(R.id.respuestana)

    }
}