package com.example.inspe

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inspe.adaptador.adaptadorsegundo
import com.example.inspe.datos.segundo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.provider.MediaStore
import android.widget.Button
import android.graphics.Bitmap
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import com.example.inspe.adaptador.imageView
import com.example.inspe.adaptador.imageView1
import com.example.inspe.adaptador.segudad1adaptador
import com.example.inspe.adaptador.segunda2adaptador
import com.example.inspe.adaptador.segunda4adaptador
import com.example.inspe.adaptador.segunda5adaptador
import com.example.inspe.adaptador.segunda6adaptador
import com.example.inspe.adaptador.segunda7adaptador
import com.example.inspe.adaptador.user
import com.example.inspe.datos.segunda2
import com.example.inspe.datos.segunda4
import com.example.inspe.datos.segunda5
import com.example.inspe.datos.segunda6
import com.example.inspe.datos.segunda7
import com.example.inspe.datos.segundad1
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.zxing.integration.android.IntentIntegrator


class MainActivity2 : AppCompatActivity() {
    private var forsegundo: ArrayList<segundo>? = null
    lateinit var  adaptadorsegundo: adaptadorsegundo
    private val CAMERA_PERMISSION_REQUEST_CODE = 100
    private val CAMERA_REQUEST_CODE = 101
    private var forsegundo2: ArrayList<segundad1>? = null
    lateinit var  segudad1adaptador: segudad1adaptador
    private var forsegundo3: ArrayList<segunda2>? = null
    lateinit var  segunda2adaptador: segunda2adaptador
    //
    private var forsegundo4: ArrayList<segunda4>? = null
    lateinit var  segunda4adaptador: segunda4adaptador
    //
    private var segunda51: ArrayList<segunda5>? = null
    lateinit var  segunda5adaptador: segunda5adaptador
    //
    private var segunda61: ArrayList<segunda6>? = null
    lateinit var  segunda6adaptador: segunda6adaptador
    //
    private var segunda71: ArrayList<segunda7>? = null
    lateinit var  segunda7adaptador: segunda7adaptador
    var valor2dato: String? = null

    private var capturedImage: Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val button4 : Button = findViewById(R.id.button4)

        val datos2 = intent.getStringExtra("dato")
        valor2dato =datos2.toString()
        val datos1 = intent.getStringExtra("dato1")
        val valor1dato = datos1.toString()
//____________________________________________________________________________________
        val zona1 = intent.getStringExtra("zona1")
        val primera = zona1.toString()
//____________________________________________________________________________________
        val zona2 = intent.getStringExtra("zona2")
        val segunda = zona2.toString()
//____________________________________________________________________________________
        val zona3 = intent.getStringExtra("zona3")
        val tercera = zona3.toString()
//____________________________________________________________________________________
        val zona4 = intent.getStringExtra("zona4")
        val cuarta = zona4.toString()

        checkBioMetricSupported()
        val executor = ContextCompat.getMainExecutor(this)
        val biometricPrompt = androidx.biometric.BiometricPrompt(this,
            executor, object : androidx.biometric.BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(this@MainActivity2, "Error de autenticación: $errString", Toast.LENGTH_SHORT).show()
                }

                override fun onAuthenticationSucceeded(result: androidx.biometric.BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    initiateQRScan()

                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(this@MainActivity2, "Autenticación fallida", Toast.LENGTH_SHORT).show()
                }
            })
        var user: FirebaseUser? = null
        var auth: FirebaseAuth? = null
        auth = FirebaseAuth.getInstance()
        user = auth!!.currentUser
        val userId = user?.uid

        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("inspectores/"+userId+"/formulario")
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val numberOfRoots = snapshot.childrenCount.toInt()

                if (numberOfRoots != null) {
                    // Realiza la acción que deseas hacer si hay 20 raíces
                    // Por ejemplo, mostrar un Toast
                    button4.setOnClickListener {

                        val promptInfoBuilder = dialogMetric()
                        promptInfoBuilder.setNegativeButtonText("cancelar")
                        val promptInfo = promptInfoBuilder.build()
                        biometricPrompt.authenticate(promptInfo)


                        // val promptInfoBuilder = dialogMetric()
                        //promptInfoBuilder.setNegativeButtonText("cancelar")
                        //val promptInfo = promptInfoBuilder.build()
                        //biometricPrompt.authenticate(promptInfo)

                    }
                 } else {
                    // Realiza la acción que deseas hacer si no hay 20 raíces
                    // Por ejemplo, mostrar un Toast
                     button4.setOnClickListener {
                        //val intent = Intent(this@MainActivity2, MainActivity::class.java)
                       // startActivity(intent)
                        Toast.makeText(this@MainActivity2,
                            "todas las preguntas$numberOfRoots", Toast.LENGTH_SHORT).show()
                         val promptInfoBuilder = dialogMetric()
                         promptInfoBuilder.setNegativeButtonText("cancelar")
                         val promptInfo = promptInfoBuilder.build()
                         biometricPrompt.authenticate(promptInfo)
                        // val promptInfoBuilder = dialogMetric()
                        //promptInfoBuilder.setNegativeButtonText("cancelar")
                        //val promptInfo = promptInfoBuilder.build()
                        //biometricPrompt.authenticate(promptInfo)

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Maneja el error si ocurre uno al acceder a la base de datos
                Toast.makeText(this@MainActivity2, "Error al acceder a la base de datos", Toast.LENGTH_SHORT).show()
            }
        })


        val deparamentosreciclersdd : RecyclerView = findViewById(R.id.tiendas)
            val database3 = FirebaseDatabase.getInstance()
            val reference4 = database3.getReference( "/formularioSegndo/"+primera )
            reference4.addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    if (!snapshot.exists()) {
                        val mensaje = "La ruta no existe"
                    }else{
                        deparamentosreciclersdd.setHasFixedSize(true)
                        deparamentosreciclersdd.layoutManager = LinearLayoutManager(this@MainActivity2, LinearLayoutManager.VERTICAL, false)
                        forsegundo = ArrayList()
                        adaptadorsegundo = adaptadorsegundo(forsegundo!!,valor1dato,
                            valor2dato!!, this@MainActivity2 ) // Pasa el contexto de la actividad principal
                        deparamentosreciclersdd.adapter = adaptadorsegundo
                        val query = reference4
                        query.addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                forsegundo!!.clear()
                                for (data3 in dataSnapshot.children) {
                                    val data3 = data3.getValue(segundo::class.java)
                                    forsegundo!!.add(data3!!)
                                }

                                adaptadorsegundo.notifyDataSetChanged()
                            }
                            override fun onCancelled(error: DatabaseError) {
                            }
                        })
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    // Ocurrió un error al acceder a la base de datos
                    val mensaje = "Error al acceder a la base de datos"
                    //Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
                }
            })
        //------------------------- segndo recicler   ----------------------------------------------
        val deparamentosreciclersdd2 : RecyclerView = findViewById(R.id.formularios)
        val database32 = FirebaseDatabase.getInstance()
        val reference42 = database32.getReference( "/formularioSegndo/"+ segunda)
        reference42.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!snapshot.exists()) {
                    // La ruta no existe, mostrar alerta
                    val mensaje = "La ruta no existe"
                    //  Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
                }else{
                    deparamentosreciclersdd2.setHasFixedSize(true)
                    deparamentosreciclersdd2.layoutManager = LinearLayoutManager(this@MainActivity2, LinearLayoutManager.VERTICAL, false)
                    forsegundo2 = ArrayList()
                    segudad1adaptador = segudad1adaptador(forsegundo2!!,valor1dato,
                        valor2dato!!, this@MainActivity2 ) // Pasa el contexto de la actividad principal
                    deparamentosreciclersdd2.adapter = segudad1adaptador
                    val query = reference42
                    query.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            forsegundo2!!.clear()
                            for (data3 in dataSnapshot.children) {
                                val data3 = data3.getValue(segundad1::class.java)
                                forsegundo2!!.add(data3!!)
                            }
                            segudad1adaptador.notifyDataSetChanged()
                        }
                        override fun onCancelled(error: DatabaseError) {
                        }
                    })
                }
            }
            override fun onCancelled(error: DatabaseError) {
                // Ocurrió un error al acceder a la base de datos
                val mensaje = "Error al acceder a la base de datos"
                //Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
            }
        })
        //------------------------- tercer recicler   ----------------------------------------------
        val recicler3 : RecyclerView = findViewById(R.id.formulariostor)
        val databasr3 = FirebaseDatabase.getInstance()
        val referencer3 = databasr3.getReference(  "/formularioSegndo/"+ tercera)
        referencer3.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!snapshot.exists()) {
                    // La ruta no existe, mostrar alerta
                    val mensaje = "La ruta no existe"
                    //  Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
                }else{
                    recicler3.setHasFixedSize(true)
                    recicler3.layoutManager = LinearLayoutManager(this@MainActivity2, LinearLayoutManager.VERTICAL, false)
                    forsegundo3 = ArrayList()
                    segunda2adaptador = segunda2adaptador(forsegundo3!!,valor1dato,
                        valor2dato!!, this@MainActivity2 ) // Pasa el contexto de la actividad principal
                    recicler3.adapter = segudad1adaptador
                    val query = referencer3
                    query.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            forsegundo3!!.clear()
                            for (data3 in dataSnapshot.children) {
                                val data3 = data3.getValue(segunda2::class.java)
                                forsegundo3!!.add(data3!!)
                            }
                            segudad1adaptador.notifyDataSetChanged()
                        }
                        override fun onCancelled(error: DatabaseError) {
                        }
                    })
                }
            }
            override fun onCancelled(error: DatabaseError) {
                // Ocurrió un error al acceder a la base de datos
                val mensaje = "Error al acceder a la base de datos"
                //Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
            }
        })
        //------------------------- cuarto recicler   ----------------------------------------------
        val recicler4 : RecyclerView = findViewById(R.id.formulariospesc)
        val databasr4 = FirebaseDatabase.getInstance()
        val referencer4 = databasr4.getReference(   "/formularioSegndo/"+cuarta )
        referencer4.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!snapshot.exists()) {
                    // La ruta no existe, mostrar alerta
                    val mensaje = "La ruta no existe"
                    //  Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
                }else{
                    recicler4.setHasFixedSize(true)
                    recicler4.layoutManager = LinearLayoutManager(this@MainActivity2, LinearLayoutManager.VERTICAL, false)
                    forsegundo4 = ArrayList()
                    segunda4adaptador = segunda4adaptador(forsegundo4!!,valor1dato,
                        valor2dato!!, this@MainActivity2 ) // Pasa el contexto de la actividad principal
                    recicler4.adapter = segunda4adaptador
                    val query = referencer4
                    query.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            forsegundo4!!.clear()
                            for (data3 in dataSnapshot.children) {
                                val data3 = data3.getValue(segunda4::class.java)
                                forsegundo4!!.add(data3!!)
                            }
                            segunda4adaptador.notifyDataSetChanged()
                        }
                        override fun onCancelled(error: DatabaseError) {
                        }
                    })
                }
            }
            override fun onCancelled(error: DatabaseError) {
                // Ocurrió un error al acceder a la base de datos
                val mensaje = "Error al acceder a la base de datos"
                //Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
            }
        })
        //-------------------------   Quinto recicler   ----------------------------------------------
        val recicler5 : RecyclerView = findViewById(R.id.formulariosCa)
        val databasr5 = FirebaseDatabase.getInstance()
        val referencer5 = databasr5.getReference( "/formularioSegndo/seccion4" )
        referencer5.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!snapshot.exists()) {
                    // La ruta no existe, mostrar alerta
                    val mensaje = "La ruta no existe"
                    //  Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
                }else{
                    recicler5.setHasFixedSize(true)
                    recicler5.layoutManager = LinearLayoutManager(this@MainActivity2, LinearLayoutManager.VERTICAL, false)
                    segunda51 = ArrayList()
                    segunda5adaptador = segunda5adaptador(segunda51!!,valor1dato, valor2dato!!, this@MainActivity2 ) // Pasa el contexto de la actividad principal
                    recicler5.adapter = segunda5adaptador
                    val query = referencer5
                    query.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            segunda51!!.clear()
                            for (data5 in dataSnapshot.children) {
                                val data5 = data5.getValue(segunda5::class.java)
                                segunda51!!.add(data5!!)
                            }
                            segunda5adaptador.notifyDataSetChanged()
                        }
                        override fun onCancelled(error: DatabaseError) {
                        }
                    })
                }
            }
            override fun onCancelled(error: DatabaseError) {
                // Ocurrió un error al acceder a la base de datos
                val mensaje = "Error al acceder a la base de datos"
                //Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
            }
        })
        //-------------------------   sexto recicler   ----------------------------------------------
        val recicler6 : RecyclerView = findViewById(R.id.formulariosfru)
        val databasr6 = FirebaseDatabase.getInstance()
        val referencer6 = databasr6.getReference( "/formularioSegndo/seccion4" )
        referencer6.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!snapshot.exists()) {
                    // La ruta no existe, mostrar alerta
                    val mensaje = "La ruta no existe"
                    //  Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
                }else{
                    recicler6.setHasFixedSize(true)
                    recicler6.layoutManager = LinearLayoutManager(this@MainActivity2, LinearLayoutManager.VERTICAL, false)
                    segunda61 = ArrayList()
                    segunda6adaptador = segunda6adaptador(segunda61!!,valor1dato, valor2dato!!, this@MainActivity2 ) // Pasa el contexto de la actividad principal
                    recicler6.adapter = segunda6adaptador
                    val query = referencer6
                    query.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            segunda61!!.clear()
                            for (data5 in dataSnapshot.children) {
                                val data5 = data5.getValue(segunda6::class.java)
                                segunda61!!.add(data5!!)
                            }
                            segunda6adaptador.notifyDataSetChanged()
                        }
                        override fun onCancelled(error: DatabaseError) {
                        }
                    })
                }
            }
            override fun onCancelled(error: DatabaseError) {
                // Ocurrió un error al acceder a la base de datos
                val mensaje = "Error al acceder a la base de datos"
                //Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
            }
        })
        //-------------------------   septimo recicler   ----------------------------------------------
        val recicler7 : RecyclerView = findViewById(R.id.formulariosslc)
        val databasr7 = FirebaseDatabase.getInstance()
        val referencer7 = databasr7.getReference( "/formularioSegndo/seccion4" )
        referencer7.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!snapshot.exists()) {
                    // La ruta no existe, mostrar alerta
                    val mensaje = "La ruta no existe"
                    //  Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
                }else{
                    recicler7.setHasFixedSize(true)
                    recicler7.layoutManager = LinearLayoutManager(this@MainActivity2, LinearLayoutManager.VERTICAL, false)
                    segunda71 = ArrayList()
                    segunda7adaptador = segunda7adaptador(segunda71!!,valor1dato, valor2dato!!, this@MainActivity2 ) // Pasa el contexto de la actividad principal
                    recicler7.adapter = segunda5adaptador
                    val query = referencer7
                    query.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            segunda71!!.clear()
                            for (data5 in dataSnapshot.children) {
                                val data5 = data5.getValue(segunda7::class.java)
                                segunda71!!.add(data5!!)
                            }
                            segunda7adaptador.notifyDataSetChanged()
                        }
                        override fun onCancelled(error: DatabaseError) {
                        }
                    })
                }
            }
            override fun onCancelled(error: DatabaseError) {
                // Ocurrió un error al acceder a la base de datos
                val mensaje = "Error al acceder a la base de datos"
                //Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
            }
        })
        //-------------------------   septimo recicler   ----------------------------------------------

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // El permiso ya ha sido concedido
            // Puedes iniciar la funcionalidad de la cámara aquí


        } else {
            // El permiso aún no ha sido concedido, así que solicítalo
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        }
    }
    fun dialogMetric(): BiometricPrompt.PromptInfo.Builder {
        return BiometricPrompt.PromptInfo.Builder()
            .setTitle("Inicio de sesión biométrico")
            .setSubtitle("Iniciar sesión utilizando su credencial biométrica")
    }

    private fun checkBioMetricSupported() {
        var info = ""
        val manager = BiometricManager.from(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            when (manager.canAuthenticate(
                BiometricManager.Authenticators.BIOMETRIC_WEAK or
                        BiometricManager.Authenticators.BIOMETRIC_STRONG
            )) {
                BiometricManager.BIOMETRIC_SUCCESS -> {
                    info = "Autenticación biométrica compatible"
                   // enableButton(true)
                }
                BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                    info = "No hay hardware de autenticación biométrica"
                    //    enableButton(false)
                }
                BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                    info = "El hardware de autenticación biométrica no está disponible"
                    //      enableButton(false)
                }
                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                    info = "No se ha configurado ninguna huella dactilar"
                    //      enableButton(false, true) // Mostrar opción de registro
                }
                else -> {
                    info = "Error desconocido"
                   // enableButton(false)
                }
            }
        }
        // TextView texinformacion = findViewById(R.id.textView5);
        // texinformacion.setText(info);
    }

    override fun onStart() {
        super.onStart()
        // Tenemos que llamar a nuestra función de logueoUsuario aquí
    }

     fun enableButton(enable: Boolean) {

     }

    fun enableButton(enable: Boolean, enroll: Boolean) {
       // enableButton(enable)

        if (!enroll) return

        val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL)
        enrollIntent.putExtra(
            Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
            BiometricManager.Authenticators.BIOMETRIC_STRONG or
                    BiometricManager.Authenticators.BIOMETRIC_WEAK
        )
        startActivity(enrollIntent)
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // El permiso fue concedido por el usuario
                // No se necesita ninguna acción adicional aquí
            } else {
                // El permiso fue denegado por el usuario
                // Aquí puedes mostrar un mensaje o realizar alguna otra acción
            }
        }
    }
    private fun initiateQRScan() {
        val integrator = IntentIntegrator(this@MainActivity2)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        integrator.setPrompt("lector - cd ")
        integrator.setCameraId(0)
        integrator.setBeepEnabled(true)
        integrator.setBarcodeImageEnabled(true)
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val image = data?.extras?.get("data") as? Bitmap
            capturedImage = image
            imageView?.setImageBitmap(capturedImage)

        }
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this@MainActivity2, "Lector cancelado", Toast.LENGTH_SHORT).show()
            } else {
                val qrContent = result.contents
                if (qrContent == "-1") {
                     val intent = Intent(this@MainActivity2, MainActivity::class.java)
                    intent.putExtra("dato", valor2dato)
                    startActivity(intent)
                     startActivity(intent)
                } else {
                    Toast.makeText(this@MainActivity2, "QR incorrecto", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
    override fun onBackPressed() {
        // Mostrar el alert dialog para informar al usuario que no puede regresar
        val builder = AlertDialog.Builder(this)
        builder.setMessage("No puedes regresar desde aquí. ¿Quieres continuar?")
            .setNegativeButton("ok") { dialog, id ->
                // Si el usuario cancela, no se hace nada y el diálogo se cierra
            }
        // Crear y mostrar el alert dialog
        builder.create().show()
    }


}