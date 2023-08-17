package com.example.inspe;

public class DataItem {
    private String text;
    private int cantidad;
    private String respuesta;
    private String comentario;
    private byte[] imageBytes;

    public DataItem(String text, int cantidad, String respuesta, String comentario, byte[] imageBytes) {
        this.text = text;
        this.cantidad = cantidad;
        this.respuesta = respuesta;
        this.comentario = comentario;
        this.imageBytes = imageBytes;
    }



    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }
}

