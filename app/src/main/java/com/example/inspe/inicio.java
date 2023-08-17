package com.example.inspe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.inspe.datos.MainActivityaut;
import com.example.inspe.dininternet.previdatossin;

public class inicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        int duracion = 1500;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(inicio.this, sinInternet2.class);
                 startActivity(intent);
            }
            //huellajava previsualizacion MainActivity4    MainActivityaut codigoQr MainActivity2

        }, duracion );
    }

}
