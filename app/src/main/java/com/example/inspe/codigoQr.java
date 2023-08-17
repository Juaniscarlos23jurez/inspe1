package com.example.inspe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.inspe.datos.MainActivityaut;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class codigoQr extends AppCompatActivity {
    private boolean button5WasClicked = false;
    private boolean button7WasClicked = false;

    Button button5,button7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo_qr);

        button5 = findViewById(R.id.button5);
        button7= findViewById(R.id.button7);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button5WasClicked = true;
                button7WasClicked = false;
                initiateQRScan();            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button5WasClicked = false;
                button7WasClicked = true;
                initiateQRScan();            }
        });
     }

    private void initiateQRScan() {
        IntentIntegrator integrator = new IntentIntegrator(codigoQr.this);
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
        if (result != null){
            if (result.getContents()==null){
                Toast.makeText(codigoQr.this, "Lector cancelado ", Toast.LENGTH_SHORT).show();
            }else {
                String scannedData = result.getContents();
                Toast.makeText(codigoQr.this, scannedData, Toast.LENGTH_SHORT).show();

                if (button5WasClicked) { // Suponiendo que tengas un mecanismo para identificar qué botón se presionó
                    Intent intent = new Intent(codigoQr.this, MainActivity.class);
                    intent.putExtra("dato", scannedData);
                    startActivity(intent);
                } else if (button7WasClicked) {
                    Intent intent = new Intent(codigoQr.this, sinInternet.class);
                    intent.putExtra("dato", "60");
                    startActivity(intent);
                }
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);

        }
     }

    }