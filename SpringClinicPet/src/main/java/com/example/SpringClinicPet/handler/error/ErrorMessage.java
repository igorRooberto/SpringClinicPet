package com.example.SpringClinicPet.handler.error;

public class ErrorMessage {

    private String titulo;
    private Integer status;
    private String message;

    public ErrorMessage(String titulo, Integer status, String message) {
        this.message = message;
        this.status = status;
        this.titulo = titulo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
