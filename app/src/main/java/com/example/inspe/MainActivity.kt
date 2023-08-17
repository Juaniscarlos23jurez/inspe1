package com.example.inspe


import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.inspe.adaptador.adaptadorsegundo
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.zxing.integration.android.IntentIntegrator
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone


class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    var strinnumero: String? = null
     private lateinit var databaseRef: DatabaseReference
    private val tiendafirebase = ArrayList<String>()
    private lateinit var map: GoogleMap
    private val PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    private val REQUEST_LOCATION_PERMISSION = 1
    private var laltitude: Double = 0.0 // Declarar laltitude como una variable de clase
    private var longi: Double = 0.0 // Declarar laltitude como una variable de clase
    private lateinit var resultManager: adaptadorsegundo
    private var user: FirebaseUser? = null
    private var auth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //-----------DECLARACIONES ------------------------------------------------------------------------

        val siguiente : Button = findViewById(R.id.button3)
        val seccion2: Button = findViewById(R.id.seccion2)
        val seccion3: Button = findViewById(R.id.seccion3)
        val seccion4: Button = findViewById(R.id.seccion4)
        val seccion5: Button = findViewById(R.id.seccion5)
        val seccion6: Button = findViewById(R.id.seccion6)
        val seccion7: Button = findViewById(R.id.seccion7)
        val seccion8: Button = findViewById(R.id.seccion8)
        val seccion9: Button = findViewById(R.id.seccion9)
        val seccion10: Button = findViewById(R.id.seccion10)
        val seccion11: Button = findViewById(R.id.seccion11)
        val seccion12: Button = findViewById(R.id.seccion12)
        val seccion13: Button = findViewById(R.id.seccion13)
        val seccion14: Button = findViewById(R.id.seccion14)
        val numerorespues : EditText = findViewById(R.id.numerorespues)


         strinnumero = intent.getStringExtra("dato")

        // Toast.makeText(this@MainActivity,strinnumero, Toast.LENGTH_SHORT).show()
         val  auto: AutoCompleteTextView = findViewById(R.id.AutoCompleteTextView1)
        val items111 = arrayOf(
            "Mensual",
            "Llamado de emergencia",
            "Especial",
            "Otros"

            )
        val adapterItems = ArrayAdapter(this, R.layout.texlist_large, items111)
         val nombreDtienda: TextView = findViewById(R.id.respuesta3)
        val  Nomtienda: TextView = findViewById(R.id.respuesta4)
        val  Fortienda: TextView = findViewById(R.id.respuesta5)
         val  comentarios: EditText = findViewById(R.id.respuesta10)
        val finalizar: Button= findViewById(R.id.button6)
        val database6 = FirebaseDatabase.getInstance()
        val database7 = FirebaseDatabase.getInstance()
        val database1 = FirebaseDatabase.getInstance()
        val database8 = FirebaseDatabase.getInstance()
        val database9 = FirebaseDatabase.getInstance()
        auto.setAdapter(adapterItems)
        auto.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            val item = adapterView.getItemAtPosition(i).toString()
            Toast.makeText(applicationContext, item, Toast.LENGTH_SHORT).show()
        }
        checkBioMetricSupported()
        val executor = ContextCompat.getMainExecutor(this)
        val biometricPrompt = androidx.biometric.BiometricPrompt(this,
            executor, object : androidx.biometric.BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(this@MainActivity, "Error de autenticación: $errString", Toast.LENGTH_SHORT).show()
                }
                override fun onAuthenticationSucceeded(result: androidx.biometric.BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    initiateQRScan()
                 }
                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(this@MainActivity, "Autenticación fallida", Toast.LENGTH_SHORT).show()
                }
            })
        auth = FirebaseAuth.getInstance()
        user = auth?.currentUser

        // Verificar si el usuario está autenticado
              val userId = user!!.uid
         val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("/inspectores/"+userId+"/formulario")
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var tieneDatosEnRango = false

                for (data in snapshot.children) {
                    val posision = data.child("posicion").getValue(String::class.java)
                    if (posision != null) {
                        val posisionInt = posision.toInt()
                        if (posisionInt in 1..54) {
                            tieneDatosEnRango = true
                            break
                        }
                    }
                }

                if (tieneDatosEnRango) {
                    // Inhabilitar el botón
                    siguiente.isEnabled = false
                } else {
                    // Realiza la acción que deseas hacer si no hay 20 raíces
                    // Por ejemplo, mostrar un Toast
                    siguiente.setOnClickListener {
                     //   /inspectores/horIHZJGDOSkBD54WRxbxtB2RdE2/formulario
                         val promptInfoBuilder = dialogMetric()
                        promptInfoBuilder.setNegativeButtonText("cancelar")
                         val promptInfo = promptInfoBuilder.build()
                         biometricPrompt.authenticate(promptInfo)
                    }
                    seccion2.setOnClickListener {

                        val intent = Intent(this@MainActivity, MainActivity2::class.java)
                        intent.putExtra("zona1", "alimentos")
                        intent.putExtra("zona2", "seccion2")
                        intent.putExtra("zona3", "seccion3")
                        intent.putExtra("zona4", "seccion4")
                        intent.putExtra("dato", strinnumero)

                        startActivity(intent)
                        //   /inspectores/horIHZJGDOSkBD54WRxbxtB2RdE2/formulario
                        //val promptInfoBuilder = dialogMetric()
                        //promptInfoBuilder.setNegativeButtonText("cancelar")
                        //val promptInfo = promptInfoBuilder.build()
                        //biometricPrompt.authenticate(promptInfo)
                    }
                    seccion3.setOnClickListener {

                        val intent = Intent(this@MainActivity, MainActivity2::class.java)
                        intent.putExtra("zona1", "alimentos")
                        intent.putExtra("zona2", "seccion2")
                        intent.putExtra("zona3", "seccion3")
                        intent.putExtra("zona4", "seccion4")
                        intent.putExtra("dato", strinnumero)

                        startActivity(intent)
                        //   /inspectores/horIHZJGDOSkBD54WRxbxtB2RdE2/formulario
                        //val promptInfoBuilder = dialogMetric()
                        //promptInfoBuilder.setNegativeButtonText("cancelar")
                        //val promptInfo = promptInfoBuilder.build()
                        //biometricPrompt.authenticate(promptInfo)
                    }
                    seccion4.setOnClickListener {

                        val intent = Intent(this@MainActivity, MainActivity2::class.java)
                        intent.putExtra("zona1", "alimentos")
                        intent.putExtra("zona2", "seccion2")
                        intent.putExtra("zona3", "seccion3")
                        intent.putExtra("zona4", "seccion4")
                        intent.putExtra("dato", strinnumero)

                        startActivity(intent)
                        //   /inspectores/horIHZJGDOSkBD54WRxbxtB2RdE2/formulario
                        //val promptInfoBuilder = dialogMetric()
                        //promptInfoBuilder.setNegativeButtonText("cancelar")
                        //val promptInfo = promptInfoBuilder.build()
                        //biometricPrompt.authenticate(promptInfo)
                    }
                    seccion5.setOnClickListener {

                        val intent = Intent(this@MainActivity, MainActivity2::class.java)
                        intent.putExtra("zona1", "alimentos")
                        intent.putExtra("zona2", "seccion2")
                        intent.putExtra("zona3", "seccion3")
                        intent.putExtra("zona4", "seccion4")
                        intent.putExtra("dato", strinnumero)

                        startActivity(intent)
                        //   /inspectores/horIHZJGDOSkBD54WRxbxtB2RdE2/formulario
                        //val promptInfoBuilder = dialogMetric()
                        //promptInfoBuilder.setNegativeButtonText("cancelar")
                        //val promptInfo = promptInfoBuilder.build()
                        //biometricPrompt.authenticate(promptInfo)
                    }
                    seccion6.setOnClickListener {

                        val intent = Intent(this@MainActivity, MainActivity2::class.java)
                        intent.putExtra("zona1", "alimentos")
                        intent.putExtra("zona2", "seccion2")
                        intent.putExtra("zona3", "seccion3")
                        intent.putExtra("zona4", "seccion4")
                        intent.putExtra("dato", strinnumero)

                        startActivity(intent)
                        //   /inspectores/horIHZJGDOSkBD54WRxbxtB2RdE2/formulario
                        //val promptInfoBuilder = dialogMetric()
                        //promptInfoBuilder.setNegativeButtonText("cancelar")
                        //val promptInfo = promptInfoBuilder.build()
                        //biometricPrompt.authenticate(promptInfo)
                    }
                    seccion7.setOnClickListener {

                        val intent = Intent(this@MainActivity, MainActivity2::class.java)
                        intent.putExtra("zona1", "alimentos")
                        intent.putExtra("zona2", "seccion2")
                        intent.putExtra("zona3", "seccion3")
                        intent.putExtra("zona4", "seccion4")
                        intent.putExtra("dato", strinnumero)

                        startActivity(intent)
                        //   /inspectores/horIHZJGDOSkBD54WRxbxtB2RdE2/formulario
                        //val promptInfoBuilder = dialogMetric()
                        //promptInfoBuilder.setNegativeButtonText("cancelar")
                        //val promptInfo = promptInfoBuilder.build()
                        //biometricPrompt.authenticate(promptInfo)
                    }
                    seccion8.setOnClickListener {

                        val intent = Intent(this@MainActivity, MainActivity2::class.java)
                        intent.putExtra("zona1", "alimentos")
                        intent.putExtra("zona2", "seccion2")
                        intent.putExtra("zona3", "seccion3")
                        intent.putExtra("zona4", "seccion4")
                        intent.putExtra("dato", strinnumero)

                        startActivity(intent)
                        //   /inspectores/horIHZJGDOSkBD54WRxbxtB2RdE2/formulario
                        //val promptInfoBuilder = dialogMetric()
                        //promptInfoBuilder.setNegativeButtonText("cancelar")
                        //val promptInfo = promptInfoBuilder.build()
                        //biometricPrompt.authenticate(promptInfo)
                    }
                    seccion9.setOnClickListener {

                        val intent = Intent(this@MainActivity, MainActivity2::class.java)
                        intent.putExtra("zona1", "alimentos")
                        intent.putExtra("zona2", "seccion2")
                        intent.putExtra("zona3", "seccion3")
                        intent.putExtra("zona4", "seccion4")
                        intent.putExtra("dato", strinnumero)

                        startActivity(intent)
                        //   /inspectores/horIHZJGDOSkBD54WRxbxtB2RdE2/formulario
                        //val promptInfoBuilder = dialogMetric()
                        //promptInfoBuilder.setNegativeButtonText("cancelar")
                        //val promptInfo = promptInfoBuilder.build()
                        //biometricPrompt.authenticate(promptInfo)
                    }
                    seccion10.setOnClickListener {

                        val intent = Intent(this@MainActivity, MainActivity2::class.java)
                        intent.putExtra("zona1", "alimentos")
                        intent.putExtra("zona2", "seccion2")
                        intent.putExtra("zona3", "seccion3")
                        intent.putExtra("zona4", "seccion4")
                        intent.putExtra("dato", strinnumero)

                        startActivity(intent)
                        //   /inspectores/horIHZJGDOSkBD54WRxbxtB2RdE2/formulario
                        //val promptInfoBuilder = dialogMetric()
                        //promptInfoBuilder.setNegativeButtonText("cancelar")
                        //val promptInfo = promptInfoBuilder.build()
                        //biometricPrompt.authenticate(promptInfo)
                    }
                    seccion11.setOnClickListener {

                        val intent = Intent(this@MainActivity, MainActivity2::class.java)
                        intent.putExtra("zona1", "alimentos")
                        intent.putExtra("zona2", "seccion2")
                        intent.putExtra("zona3", "seccion3")
                        intent.putExtra("zona4", "seccion4")
                        intent.putExtra("dato", strinnumero)

                        startActivity(intent)
                        //   /inspectores/horIHZJGDOSkBD54WRxbxtB2RdE2/formulario
                        //val promptInfoBuilder = dialogMetric()
                        //promptInfoBuilder.setNegativeButtonText("cancelar")
                        //val promptInfo = promptInfoBuilder.build()
                        //biometricPrompt.authenticate(promptInfo)
                    }
                    seccion12.setOnClickListener {

                        val intent = Intent(this@MainActivity, MainActivity2::class.java)
                        intent.putExtra("zona1", "alimentos")
                        intent.putExtra("zona2", "seccion2")
                        intent.putExtra("zona3", "seccion3")
                        intent.putExtra("zona4", "seccion4")
                        intent.putExtra("dato", strinnumero)

                        startActivity(intent)
                        //   /inspectores/horIHZJGDOSkBD54WRxbxtB2RdE2/formulario
                        //val promptInfoBuilder = dialogMetric()
                        //promptInfoBuilder.setNegativeButtonText("cancelar")
                        //val promptInfo = promptInfoBuilder.build()
                        //biometricPrompt.authenticate(promptInfo)
                    }
                    seccion13.setOnClickListener {

                        val intent = Intent(this@MainActivity, MainActivity2::class.java)
                        intent.putExtra("zona1", "alimentos")
                        intent.putExtra("zona2", "seccion2")
                        intent.putExtra("zona3", "seccion3")
                        intent.putExtra("zona4", "seccion4")
                        intent.putExtra("dato", strinnumero)

                        startActivity(intent)
                        //   /inspectores/horIHZJGDOSkBD54WRxbxtB2RdE2/formulario
                        //val promptInfoBuilder = dialogMetric()
                        //promptInfoBuilder.setNegativeButtonText("cancelar")
                        //val promptInfo = promptInfoBuilder.build()
                        //biometricPrompt.authenticate(promptInfo)
                    }
                    seccion14.setOnClickListener {

                        val intent = Intent(this@MainActivity, MainActivity2::class.java)
                        intent.putExtra("zona1", "alimentos")
                        intent.putExtra("zona2", "seccion2")
                        intent.putExtra("zona3", "seccion3")
                        intent.putExtra("zona4", "seccion4")
                        intent.putExtra("dato", strinnumero)

                        startActivity(intent)
                        //   /inspectores/horIHZJGDOSkBD54WRxbxtB2RdE2/formulario
                        //val promptInfoBuilder = dialogMetric()
                        //promptInfoBuilder.setNegativeButtonText("cancelar")
                        //val promptInfo = promptInfoBuilder.build()
                        //biometricPrompt.authenticate(promptInfo)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Maneja el error si ocurre uno al acceder a la base de datos
                Toast.makeText(this@MainActivity, "Error al acceder a la base de datos", Toast.LENGTH_SHORT).show()
            }
        })


        databaseRef = FirebaseDatabase.getInstance().reference
              val reference6 = database6.getReference("/tiendas/" + strinnumero + "/contacto")
              val reference7 = database7.getReference("/tiendas/"+strinnumero+ "/Ntienda")
            reference7.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // Verificamos si el valor existe en la base de datos
                    if (snapshot.exists()) {
                        // Obtenemos el valor del DataSnapshot y lo convertimos a String
                        val valor = snapshot.getValue(String::class.java)

                        // Establecemos el valor en el EditText "cliente"
                        nombreDtienda.setText(valor)
                    } else {
                        // Si el valor no existe, podemos establecer un mensaje predeterminado o dejar el EditText en blanco
                        nombreDtienda.setText("Valor no encontrado")
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    // Manejamos el error en caso de que ocurra algún problema con la base de datos
                    nombreDtienda.setText("Error al acceder a la base de datos")
                }
            })
             val reference1 = database1.getReference("/tiendas/"+strinnumero+ "/tien")
            reference1.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // Verificamos si el valor existe en la base de datos
                    if (snapshot.exists()) {
                        // Obtenemos el valor del DataSnapshot y lo convertimos a String
                        val valor = snapshot.getValue(String::class.java)

                        // Establecemos el valor en el EditText "cliente"
                        Nomtienda.setText(valor)
                    } else {
                        // Si el valor no existe, podemos establecer un mensaje predeterminado o dejar el EditText en blanco
                        Nomtienda.setText("Valor no encontrado")
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    // Manejamos el error en caso de que ocurra algún problema con la base de datos
                    Nomtienda.setText("Error al acceder a la base de datos")
                }
            })
             val reference8 = database8.getReference("/tiendas/"+strinnumero+ "/formato")
            reference8.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // Verificamos si el valor existe en la base de datos
                    if (snapshot.exists()) {
                        // Obtenemos el valor del DataSnapshot y lo convertimos a String
                        val valor = snapshot.getValue(String::class.java)

                        // Establecemos el valor en el EditText "cliente"
                        Fortienda.setText(valor)
                    } else {
                        // Si el valor no existe, podemos establecer un mensaje predeterminado o dejar el EditText en blanco
                        Fortienda.setText("Valor no encontrado")
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    // Manejamos el error en caso de que ocurra algún problema con la base de datos
                    Fortienda.setText("Error al acceder a la base de datos")
                }
            })
             val reference9 = database9.getReference("/tiendas/"+strinnumero+ "/formato")

        val sharedPreferences = getSharedPreferences("ResultadosPrefs", Context.MODE_PRIVATE)
        val resultadosSet = sharedPreferences.getStringSet("resultados", HashSet<String>())

        val resultadosList = ArrayList(resultadosSet)
        val resultadosString = resultadosList.joinToString("\n")
         Toast.makeText(this@MainActivity, resultadosString, Toast.LENGTH_SHORT).show()

// Mostrar los resultados en un cuadro de diálogo o en algún otro lugar de tu UI
// Ahora puedes utilizar resultadosList para mostrar los resultados en tu vista

        finalizar.setOnClickListener{
            val respuesta = numerorespues.text.toString().trim()

            if (respuesta.isNotEmpty()) {
                initiateQRScan()
            } else {
                Toast.makeText(this@MainActivity, "coloque el acompañante", Toast.LENGTH_SHORT).show()
            }
        }


         if (!hasPermissions()) {
             ActivityCompat.requestPermissions(
                this,
                PERMISSIONS,
                REQUEST_LOCATION_PERMISSION
            )
        } else {
             getLocation()
        }

     }


     private fun finalizarformulario() {
        auth = FirebaseAuth.getInstance()
        user = auth?.currentUser

        // Verificar si el usuario está autenticado
        val userId = user!!.uid
        val database = FirebaseDatabase.getInstance()
         val reference = database.getReference("/inspectores/"+userId+"/formulario")
        val newReference = database.getReference("/formulario/uno")

         reference.addListenerForSingleValueEvent(object : ValueEventListener {
             override fun onDataChange(snapshot: DataSnapshot) {
                 val database = FirebaseDatabase.getInstance()
                 val reference = database.getReference("/empresa/formulario")
                 val recleccion = database.getReference("/empresa/enlace/"+userId)

                 val newReference = database.getReference("/inspectores/"+userId+"/elace")
                 for (preguntaSnapshot in snapshot.children) {
                     val preguntaKey = preguntaSnapshot.key ?: ""
                     val preguntaData = preguntaSnapshot.value as? Map<*, *> ?: continue
                     val  pregunta= preguntaData["pregunta"] as? String ?: ""
                     val departamento = preguntaData["departamento"] as? String ?: ""
                     val formulario = preguntaData["formulario"] as? String ?: ""
                     val respuesta1 = preguntaData["respuesta1"] as? String ?: ""
                     val tienda = preguntaData["tienda"] as? String ?: ""
                     val userId = preguntaData["userId"] as? String ?: ""
                     val valor1 = preguntaData["valor"] as? String ?: ""
                     val comentario = preguntaData["comentario"] as? String ?: ""
                     val imagen = preguntaData["imagen"] as? String ?: ""
                     val calendar = Calendar.getInstance()
                     val fechaYHoraActual = calendar.time
                     // Formatear la fecha y hora actual en un formato deseado
                     val formatoFechaHora = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
                     val fechaHoraFormateada = formatoFechaHora.format(fechaYHoraActual)
                      val identificador =  userId +"/"+ fechaHoraFormateada
                     val order = HashMap<String, Any>()
                     order["inspector"] = userId
                     order["departamento"] = departamento
                     order["formulario"] = formulario
                     order["pregunta"] = pregunta
                     order["posision"] = preguntaKey
                     order["respuesta1"] = respuesta1
                     order["tienda"] = tienda
                     order["valor"] = valor1
                     order["comentario"] = comentario
                     order["imagen"] = imagen

                     limpieza()
                     reference.child(identificador).child(preguntaKey).setValue(order)

                     recleccion.child(fechaHoraFormateada).child("enlace").setValue(fechaHoraFormateada)

                     newReference.child(fechaHoraFormateada).child("enlace").setValue("/empresa/formulario/"+userId+"/"+fechaHoraFormateada)
                     val intent = Intent(this@MainActivity, previsualizacion::class.java)
                     intent.putExtra("dato", "/empresa/formulario/"+userId+"/"+fechaHoraFormateada)
                     intent.putExtra("tiempo", fechaHoraFormateada)

                     startActivity(intent)
                 }
             }
            override fun onCancelled(error: DatabaseError) {
                // Maneja el error si ocurre uno al acceder a la base de datos
                Toast.makeText(this@MainActivity, "Error al acceder a la base de datos", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun limpieza() {
        auth = FirebaseAuth.getInstance()
        user = auth?.currentUser

        // Verificar si el usuario está autenticado
        val userId = user!!.uid
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("/inspectores/"+userId+"/formulario")
        reference.removeValue()

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
                    enableButton(true)
                }
                BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                    info = "No hay hardware de autenticación biométrica"
                    enableButton(false)
                }
                BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                    info = "El hardware de autenticación biométrica no está disponible"
                    enableButton(false)
                }
                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                    info = "No se ha configurado ninguna huella dactilar"
                    enableButton(false, true) // Mostrar opción de registro
                }
                else -> {
                    info = "Error desconocido"
                    enableButton(false)
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
        val siguiente : Button = findViewById(R.id.button3)

         // btn_fppin.setEnabled(true);
    }

    fun enableButton(enable: Boolean, enroll: Boolean) {
        enableButton(enable)

        if (!enroll) return

        val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL)
        enrollIntent.putExtra(
            Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
            BiometricManager.Authenticators.BIOMETRIC_STRONG or
                    BiometricManager.Authenticators.BIOMETRIC_WEAK
        )
        startActivity(enrollIntent)
    }

    private fun initiateQRScan() {
        val integrator = IntentIntegrator(this@MainActivity)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        integrator.setPrompt("lector - cd ")
        integrator.setCameraId(0)
        integrator.setBeepEnabled(true)
        integrator.setBarcodeImageEnabled(true)
        integrator.initiateScan()
    }
     /// ubicacion -------------------------------------------------------------------------------------------------------------------
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
      ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation()
            } else {
                // Permiso de ubicación denegado, manejarlo adecuadamente
            }
        }
    }

    private fun hasPermissions(): Boolean {
        for (permission in PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }
    @SuppressLint("MissingPermission")
    private fun getLocation() {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        if (locationManager != null) {
            if (!hasPermissions()) {
                return
            }

            val locationListener = object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    val database6 = FirebaseDatabase.getInstance()
                    val strinnumero = intent.getStringExtra("dato")
                     val reference90 = database6.getReference("/tiendas/"+strinnumero+ "/l")
                    val reference = database6.getReference("/tiendas/"+strinnumero+ "/la")
                    reference90.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                             laltitude = (dataSnapshot.getValue(Double::class.java) ?: 0.0)
                            reference.addValueEventListener(object : ValueEventListener {
                                override fun onDataChange(dataSnapshot: DataSnapshot) {
                                    longi = (dataSnapshot.getValue(Double::class.java) ?: 0.0)
                                     val distance = calculateDistance(latitude, longitude, laltitude, longi)
                                    val message1 = " $latitude     $longitude"
                                     val strindistancia: String = distance.toString()

                                }
                                override fun onCancelled(error: DatabaseError) {
                                    // Manejo de errores (opcional)
                                }
                            })
                        }

                        override fun onCancelled(error: DatabaseError) {
                            // Manejo de errores (opcional)
                        }
                    })

                }
                override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
                override fun onProviderEnabled(provider: String) {}
                override fun onProviderDisabled(provider: String) {}
            }

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
        }
    }

    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Float {
        val results = FloatArray(1)
        Location.distanceBetween(lat1, lon1, lat2.toDouble(), lon2.toDouble(), results)
        return results[0]
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this@MainActivity, "Lector cancelado", Toast.LENGTH_SHORT).show()
            } else {
                val qrContent = result.contents
                if (qrContent == "-1") {
                    val intent = Intent(this@MainActivity, MainActivity2::class.java)
                     intent.putExtra("zona1", "alimentos")
                    intent.putExtra("zona2", "seccion2")
                    intent.putExtra("zona3", "seccion3")
                    intent.putExtra("zona4", "seccion4")
                    intent.putExtra("dato", strinnumero)
                     startActivity(intent)
                } else if (qrContent == strinnumero){
                    finalizarformulario()

                }

                else {
                    Toast.makeText(this@MainActivity, "QR incorrecto", Toast.LENGTH_SHORT).show()
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