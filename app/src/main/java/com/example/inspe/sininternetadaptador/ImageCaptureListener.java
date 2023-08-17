package com.example.inspe.sininternetadaptador;

import android.graphics.Bitmap;
import android.widget.ImageView;

public interface ImageCaptureListener {
    void onImageCaptured(Bitmap capturedImage);
    static final int CAMERA_REQUEST_CODE = 101;
    static final int CAMERA_PERMISSION_REQUEST_CODE = 200;

    // Variable para almacenar la imagen capturada
    Bitmap capturedImage = null; // Variable para almacenar la imagen capturada
    ImageView imageView2 = null; // ImageView donde se mostrará la imagen capturada
}
