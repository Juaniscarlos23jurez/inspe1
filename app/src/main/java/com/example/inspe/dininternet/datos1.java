package com.example.inspe.dininternet;

public class datos1 {
    private int valor;
    private int posision;

    private String pregunta;
    private String departamento;

    public datos1(String departamento, int posision, String pregunta, int valor) {
        this.departamento = departamento;

        this.posision = posision;
        this.pregunta = pregunta;
        this.valor = valor;

    }

    public int getId() {
        return posision;
    }
    public int getvalor() {
        return valor;
    }

    public String getTitulo() {
        return pregunta;
    }

    public String getContenido() {
        return departamento;
    }
}
