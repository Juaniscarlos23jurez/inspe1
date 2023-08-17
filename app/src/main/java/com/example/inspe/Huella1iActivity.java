package com.example.inspe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.hardware.biometrics.BiometricManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.Toast;

import com.example.inspe.datos.MainActivityaut;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

public class Huella1iActivity extends AppCompatActivity {
    Button btn_fp, button;
    FirebaseUser user;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.huella1i);
        btn_fp = findViewById(R.id.button);

         checkBioMetricSupported();
        Executor executor = ContextCompat.getMainExecutor(this);
        androidx.biometric.BiometricPrompt biometricPrompt = new androidx.biometric.BiometricPrompt(Huella1iActivity.this,
                executor, new androidx.biometric.BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(Huella1iActivity.this, "Autheticacion error"+errString  , Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onAuthenticationSucceeded(@NonNull androidx.biometric.BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);

                startActivity(new Intent(Huella1iActivity.this, MainActivity4.class));

            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(Huella1iActivity.this, "Autheticacion fallida"  , Toast.LENGTH_SHORT).show();

            }
        });

            BiometricPrompt.PromptInfo.Builder promptInfo = dialogMetric();
            promptInfo.setNegativeButtonText("cancelar");
            biometricPrompt.authenticate(promptInfo.build());

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
        btn_fp.setEnabled(enable);
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