package com.example.inspe;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "TextDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "text_table";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TEXT = "text";


    private static final String COLUMN_DEPARTAMENTO = "departamento";
    private static final String COLUMN_CANTIDAD = "cantidad";
    private static final String COLUMN_PSISI = "psisi";
    private static final String COLUMN_RESPUESTA = "respuesta";
    private static final String COLUMN_COMENTARIO = "comentario";
    private static final String COLUMN_IMAGEN = "imagen"; // Nueva columna para la imagen

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TEXT + " TEXT, " +
                COLUMN_DEPARTAMENTO + " TEXT, " +
                COLUMN_CANTIDAD + " INTEGER, " +
                COLUMN_PSISI + " INTEGER, " +
                COLUMN_RESPUESTA + " TEXT, " +
                COLUMN_COMENTARIO + " TEXT, " +
                COLUMN_IMAGEN + " BLOB)"; // Definir la nueva columna BLOB para la imagen
        db.execSQL(createTableQuery);
    }

    public void insertImageAndText(String text, String departamento, int cantidad, int psisi, String respuesta, String comentario, byte[] imageBytes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEXT, text);
        values.put(COLUMN_DEPARTAMENTO, departamento);
        values.put(COLUMN_CANTIDAD, cantidad);
        values.put(COLUMN_PSISI, psisi);
        values.put(COLUMN_RESPUESTA, respuesta);
        values.put(COLUMN_COMENTARIO, comentario);
        values.put(COLUMN_IMAGEN, imageBytes); // Almacenar el arreglo de bytes de la imagen en la nueva columna
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertText(String text, String departamento, int cantidad, int psisi, String respuesta, String comentario) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEXT, text);
        values.put("departamento", departamento);
        values.put("cantidad", cantidad);
        values.put("psisi", psisi);
        values.put("respuesta", respuesta);
        values.put("comentario", comentario);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<String> getAllTexts() {
        List<String> texts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_TEXT}, null, null, null, null, null);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") String text = cursor.getString(cursor.getColumnIndex(COLUMN_TEXT));
            texts.add(text);
        }

        cursor.close();
        return texts;
    }
    public List<String> getAllData() {
        List<String> data = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Cambia null a un arreglo de nombres de columnas
        Cursor cursor = db.query(TABLE_NAME, new String[]{
                COLUMN_TEXT, "departamento", "cantidad", "psisi", "respuesta", "comentario"
        }, null, null, null, null, null);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") String text = cursor.getString(cursor.getColumnIndex(COLUMN_TEXT));
            @SuppressLint("Range") String departamento = cursor.getString(cursor.getColumnIndex("departamento"));
            @SuppressLint("Range") int cantidad = cursor.getInt(cursor.getColumnIndex("cantidad"));
            @SuppressLint("Range") int psisi = cursor.getInt(cursor.getColumnIndex("psisi"));
            @SuppressLint("Range") String respuesta = cursor.getString(cursor.getColumnIndex("respuesta"));
            @SuppressLint("Range") String comentario = cursor.getString(cursor.getColumnIndex("comentario"));

            String rowData = "Pregunta: " + text + "\nDepartamento: " + departamento +
                    "\nValor: " + cantidad + "\nPosicion: " + psisi +
                    "\nRespuesta: " + respuesta + "\nComentario: " + comentario;

            data.add(rowData);
        }

        cursor.close();
        return data;
    }


    public List<DataItem> getFilteredAndOrderedData() {
        List<DataItem> data = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = "departamento = ? AND psisi >= ? AND psisi <= ?";
        String[] selectionArgs = {"Alimentos preparados", "1", "68"};

        String orderBy = "CAST(psisi AS INTEGER)"; // Ordenar la columna psisi como número

        Cursor cursor = db.query(TABLE_NAME, new String[]{
                COLUMN_TEXT, "cantidad", "respuesta", "comentario", COLUMN_IMAGEN
        }, selection, selectionArgs, null, null, orderBy);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") String text = cursor.getString(cursor.getColumnIndex(COLUMN_TEXT));
            @SuppressLint("Range") int cantidad = cursor.getInt(cursor.getColumnIndex("cantidad"));
            @SuppressLint("Range") String respuesta = cursor.getString(cursor.getColumnIndex("respuesta"));
            @SuppressLint("Range") String comentario = cursor.getString(cursor.getColumnIndex("comentario"));
            @SuppressLint("Range") byte[] imageBytes = cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGEN));

            DataItem item = new DataItem(text, cantidad, respuesta, comentario, imageBytes);

            data.add(item);
        }

        cursor.close();
        return data;
    }


    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }

    public boolean checkIfAnsweredWithPsisi(int psisi) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_PSISI + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(psisi)});
        boolean isAnswered = cursor.moveToFirst();
        cursor.close();
        db.close();
        return isAnswered;
    }


}

