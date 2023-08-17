package com.example.inspe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.Intent;
import android.hardware.biometrics.BiometricManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;
import java.util.concurrent.Executor;

public class sinInternet extends AppCompatActivity {
    Button siguiente,seccion2,seccion3,seccion4,seccion5,seccion6,seccion7,seccion8,seccion9,seccion10,seccion11,seccion12,seccion13,seccion14;
TextView respuesta3;
EditText numerorespues;
Button finalizar;
    AutoCompleteTextView auto3;
    ArrayAdapter<String> adapterItems3;
    String [] items3  ={"Mensual",
            "Llamado de emergencia",
            "Especial",
            "Otros"};
    String Sucursal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sin_internet);
        auto3 = findViewById(R.id.AutoCompleteTextView1);
        auto3.setEnabled(false);
        adapterItems3 = new ArrayAdapter<String>(getApplicationContext(), R.layout.texlist_large, items3);
        auto3.setAdapter(adapterItems3);
        auto3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getApplicationContext(), item, Toast.LENGTH_SHORT).show();
                Sucursal = item;

            }
        });

        respuesta3= findViewById(R.id.respuesta3);
         siguiente = findViewById(R.id.button3);
         seccion2 = findViewById(R.id.seccion2);
          seccion3 = findViewById(R.id.seccion3);
         seccion4 = findViewById(R.id.seccion4);
          seccion5 = findViewById(R.id.seccion5);
         seccion6 = findViewById(R.id.seccion6);
        seccion7 = findViewById(R.id.seccion7);
         seccion8 = findViewById(R.id.seccion8);
         seccion9 = findViewById(R.id.seccion9);
          seccion10 = findViewById(R.id.seccion10);
          seccion11 = findViewById(R.id.seccion11);
          seccion12 = findViewById(R.id.seccion12);
          seccion13 = findViewById(R.id.seccion13);
        numerorespues=findViewById(R.id.numerorespues);
          seccion14 = findViewById(R.id.seccion14);
        finalizar= findViewById(R.id.button6);
        finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numeroRespuesta = numerorespues.getText().toString();

                if (!TextUtils.isEmpty(numeroRespuesta)) {
                    startActivity(new Intent(sinInternet.this,Previsualizacionsininternet.class));
                } else {
                    // El EditText está vacío, muestra un mensaje de error o realiza alguna otra acción
                    // Puedes mostrar un Toast, un diálogo, etc.
                    Toast.makeText(sinInternet.this, "Ingrese un número de respuesta", Toast.LENGTH_SHORT).show();
                }
            }
        });

        String strinnumero = getIntent().getStringExtra("dato");
        respuesta3.setText(strinnumero);

        checkBioMetricSupported();
        Executor executor = ContextCompat.getMainExecutor(this);
        androidx.biometric.BiometricPrompt biometricPrompt = new androidx.biometric.BiometricPrompt(sinInternet.this,
                executor, new androidx.biometric.BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(sinInternet.this, "Autheticacion error"+errString  , Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onAuthenticationSucceeded(@NonNull androidx.biometric.BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                initiateQRScan();
                //startActivity(new Intent(sinInternet.this,codigoQr.class));


            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(sinInternet.this, "Autheticacion fallida"  , Toast.LENGTH_SHORT).show();

            }
        });

        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BiometricPrompt.PromptInfo.Builder promptInfo = dialogMetric();
                promptInfo.setNegativeButtonText("cancelar");
                biometricPrompt.authenticate(promptInfo.build());
              }
        });
        seccion2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BiometricPrompt.PromptInfo.Builder promptInfo = dialogMetric();
                promptInfo.setNegativeButtonText("cancelar");
                biometricPrompt.authenticate(promptInfo.build());
            }
        });
        seccion3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BiometricPrompt.PromptInfo.Builder promptInfo = dialogMetric();
                promptInfo.setNegativeButtonText("cancelar");
                biometricPrompt.authenticate(promptInfo.build());
            }
        });
        seccion4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BiometricPrompt.PromptInfo.Builder promptInfo = dialogMetric();
                promptInfo.setNegativeButtonText("cancelar");
                biometricPrompt.authenticate(promptInfo.build());
            }
        });
        seccion5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BiometricPrompt.PromptInfo.Builder promptInfo = dialogMetric();
                promptInfo.setNegativeButtonText("cancelar");
                biometricPrompt.authenticate(promptInfo.build());
            }
        });
        seccion6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BiometricPrompt.PromptInfo.Builder promptInfo = dialogMetric();
                promptInfo.setNegativeButtonText("cancelar");
                biometricPrompt.authenticate(promptInfo.build());
            }
        });
        seccion7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BiometricPrompt.PromptInfo.Builder promptInfo = dialogMetric();
                promptInfo.setNegativeButtonText("cancelar");
                biometricPrompt.authenticate(promptInfo.build());
            }
        });
        seccion8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BiometricPrompt.PromptInfo.Builder promptInfo = dialogMetric();
                promptInfo.setNegativeButtonText("cancelar");
                biometricPrompt.authenticate(promptInfo.build());
            }
        });
        seccion9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BiometricPrompt.PromptInfo.Builder promptInfo = dialogMetric();
                promptInfo.setNegativeButtonText("cancelar");
                biometricPrompt.authenticate(promptInfo.build());
            }
        });
        seccion10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BiometricPrompt.PromptInfo.Builder promptInfo = dialogMetric();
                promptInfo.setNegativeButtonText("cancelar");
                biometricPrompt.authenticate(promptInfo.build());
            }
        });
        seccion11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BiometricPrompt.PromptInfo.Builder promptInfo = dialogMetric();
                promptInfo.setNegativeButtonText("cancelar");
                biometricPrompt.authenticate(promptInfo.build());
            }
        });
        seccion12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BiometricPrompt.PromptInfo.Builder promptInfo = dialogMetric();
                promptInfo.setNegativeButtonText("cancelar");
                biometricPrompt.authenticate(promptInfo.build());
            }
        });
        seccion13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BiometricPrompt.PromptInfo.Builder promptInfo = dialogMetric();
                promptInfo.setNegativeButtonText("cancelar");
                biometricPrompt.authenticate(promptInfo.build());
            }
        });
        seccion14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BiometricPrompt.PromptInfo.Builder promptInfo = dialogMetric();
                promptInfo.setNegativeButtonText("cancelar");
                biometricPrompt.authenticate(promptInfo.build());
            }
        });

        finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper dbHelper = new DatabaseHelper(sinInternet.this);
                List<String> allData = dbHelper.getAllData();

                StringBuilder allDataBuilder = new StringBuilder();
                for (String data : allData) {
                    allDataBuilder.append(data).append("\n");
                }

                String allDataString = allDataBuilder.toString().trim();
                AlertDialog.Builder builder = new AlertDialog.Builder(sinInternet.this);
                builder.setTitle("Todos los Datos Guardados");
                builder.setMessage(allDataString);
                builder.setPositiveButton("Aceptar", null);
                AlertDialog dialog = builder.create();
                dialog.show();
             }
        });
    }

    private void initiateQRScan() {
        IntentIntegrator integrator = new IntentIntegrator(sinInternet.this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("lector - cd ");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Lector cancelado", Toast.LENGTH_SHORT).show();
            } else {
                String qrContent = result.getContents();
                if ("-1".equals(qrContent)) {
                    Intent intent = new Intent(this, sinInternet2.class);
                    String strinnumero = getIntent().getStringExtra("dato");
                    intent.putExtra("dato", strinnumero);
                    startActivity(intent);
                }else  if ("-2".equals(qrContent)) {
                    Intent intent = new Intent(this, sinInternet3.class);
                    String strinnumero = getIntent().getStringExtra("dato");
                    intent.putExtra("dato", strinnumero);
                    startActivity(intent);
                }else  if ("-3".equals(qrContent)) {
                    Intent intent = new Intent(this, sinInternet4.class);
                    String strinnumero = getIntent().getStringExtra("dato");
                    intent.putExtra("dato", strinnumero);
                    startActivity(intent);
                }else  if ("-4".equals(qrContent)) {
                    Intent intent = new Intent(this, sinInternet5.class);
                    String strinnumero = getIntent().getStringExtra("dato");
                    intent.putExtra("dato", strinnumero);
                    startActivity(intent);
                }else  if ("-5".equals(qrContent)) {
                    Intent intent = new Intent(this, sinInternet6.class);
                    String strinnumero = getIntent().getStringExtra("dato");
                    intent.putExtra("dato", strinnumero);
                    startActivity(intent);
                }else  if ("-6".equals(qrContent)) {
                    Intent intent = new Intent(this, sinInternet7.class);
                    String strinnumero = getIntent().getStringExtra("dato");
                    intent.putExtra("dato", strinnumero);
                    startActivity(intent);
                }else  if ("-7".equals(qrContent)) {
                    Intent intent = new Intent(this, sinInternet8.class);
                    String strinnumero = getIntent().getStringExtra("dato");
                    intent.putExtra("dato", strinnumero);
                    startActivity(intent);
                }else  if ("-8".equals(qrContent)) {
                    Intent intent = new Intent(this, sinInternet9.class);
                    String strinnumero = getIntent().getStringExtra("dato");
                    intent.putExtra("dato", strinnumero);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "QR incorrecto", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void finalizarformulario() {
    }

    BiometricPrompt.PromptInfo.Builder  dialogMetric(){
        return  new BiometricPrompt.PromptInfo.Builder()
                .setTitle("biometric login")
                .setSubtitle("loggin using your biometric credential");
    }

    private void checkBioMetricSupported() {
        String info = "";
        androidx.biometric.BiometricManager manager = androidx.biometric.BiometricManager.from(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            switch (manager.canAuthenticate(androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK
                    | androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
                case BiometricManager.BIOMETRIC_SUCCESS:
                    info = "Autenticación biométrica compatible";
                    enableButton(true);
                    break;
                case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                    info = "No hay hardware de autenticación biométrica";
                    enableButton(false);
                    break;
                case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                    info = "El hardware de autenticación biométrica no está disponible";
                    enableButton(false);
                    break;
                case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                    info = "No se ha configurado ninguna huella dactilar";
                    enableButton(false, true); // Mostrar opción de registro
                    break;
                default:
                    info = "Error desconocido";
                    enableButton(false);
                    break;
            }
        }

        //    TextView texinformacion = findViewById(R.id.textView5);
        //   texinformacion.setText(info);
    }
    @Override
    //// 2_ tenemos que crear un onStart para poder llamar a nuestra funcion logueoUsuario
    protected void onStart() {
        super.onStart();

    }

    ///  1 _ primero creamos una funcion

    void enableButton(boolean enable) {
        seccion2.setEnabled(enable);
        //   btn_fppin.setEnabled(true);
    }

    void enableButton(boolean enable, boolean enroll) {
        enableButton(enable);

        if (!enroll) return;

        Intent enrollIntent = new Intent(Settings.ACTION_BIOMETRIC_ENROLL);
        enrollIntent.putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                BiometricManager.Authenticators.BIOMETRIC_STRONG
                        | BiometricManager.Authenticators.BIOMETRIC_WEAK);
        startActivity(enrollIntent);
    }

}
