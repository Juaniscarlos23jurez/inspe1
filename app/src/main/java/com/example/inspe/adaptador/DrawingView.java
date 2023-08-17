package com.example.inspe.adaptador;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inspe.R;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Iterator;

     public class DrawingView extends AppCompatActivity {

        private View drawingView;
        private Bitmap bitmap;
        private Canvas canvas;
        private Paint paint;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_drawing);

            drawingView = findViewById(R.id.drawing_view);
            drawingView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();

                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                            // Inicializa el bitmap y el lienzo si aún no están creados
                            if (bitmap == null) {
                                bitmap = Bitmap.createBitmap(drawingView.getWidth(), drawingView.getHeight(), Bitmap.Config.ARGB_8888);
                                canvas = new Canvas(bitmap);
                                canvas.drawColor(Color.WHITE);
                                paint = new Paint();
                                paint.setColor(Color.BLACK);
                                paint.setStrokeWidth(5);
                            }
                            break;

                        case MotionEvent.ACTION_MOVE:
                            // Dibuja en el lienzo mientras el usuario se desplaza
                            float x = event.getX();
                            float y = event.getY();
                            canvas.drawPoint(x, y, paint);
                            drawingView.invalidate(); // Actualiza la vista
                            break;

                        case MotionEvent.ACTION_UP:
                            // Acción al soltar el dedo (opcional)
                            break;
                    }

                    return true;
                }
            });
        }

        // Método para capturar el dibujo como firma
        public void captureSignature(View view) {
            // Aquí puedes guardar el bitmap como imagen o realizar otras acciones con él
        }
    }