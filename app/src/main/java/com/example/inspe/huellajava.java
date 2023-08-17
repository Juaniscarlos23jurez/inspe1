
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
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.inspe.datos.MainActivityaut;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.concurrent.Executor;

public class huellajava extends AppCompatActivity {
    Button btn_fp, button;
    FirebaseUser user;
    FirebaseAuth auth;
Button button13;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huellajava);
        btn_fp = findViewById(R.id.button);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        button13 = findViewById(R.id.button13);

        button13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(huellajava.this,  perfil.class));
            }
        });
        if (user != null) {
            // El usuario está autenticado, puedes dejar que acceda a la app.
        } else {
            // El usuario no está autenticado, redirige al usuario a la pantalla de inicio de sesión o registro.
            Intent intent = new Intent(this, Huella1iActivity.class);
            startActivity(intent);
            finish();
        }

        checkBioMetricSupported();
        Executor executor = ContextCompat.getMainExecutor(this);
        androidx.biometric.BiometricPrompt biometricPrompt = new androidx.biometric.BiometricPrompt(huellajava.this,
                executor, new androidx.biometric.BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(huellajava.this, "Autheticacion error"+errString  , Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onAuthenticationSucceeded(@NonNull androidx.biometric.BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                startActivity(new Intent(huellajava.this,codigoQr.class));


            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(huellajava.this, "Autheticacion fallida"  , Toast.LENGTH_SHORT).show();

            }
        });


        btn_fp.setOnClickListener(view -> {

            BiometricPrompt.PromptInfo.Builder promptInfo = dialogMetric();
            promptInfo.setNegativeButtonText("cancelar");
            biometricPrompt.authenticate(promptInfo.build());
        });

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
