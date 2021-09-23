package com.example.registroclientesdmr.clase;

public class Cliente {

    String id = "";
    String nombre = "";
    String empresa = "";
    String cargo = "";
    String telefono = "";
    String email = "";
    String nota = "";
    String email_usuario = "";
    String fecha_registro;

    public Cliente() {
    }

    public Cliente(String nombre, String empresa, String cargo, String telefono, String email, String nota, String email_usuario, String fecha_registro) {
        this.nombre = nombre;
        this.empresa = empresa;
        this.cargo = cargo;
        this.telefono = telefono;
        this.email = email;
        this.nota = nota;
        this.email_usuario = email_usuario;
        this.fecha_registro = fecha_registro;
    }

    public Cliente(String id, String nombre, String empresa, String cargo, String telefono, String email, String nota, String email_usuario, String fecha_registro) {
        this.id = id;
        this.nombre = nombre;
        this.empresa = empresa;
        this.cargo = cargo;
        this.telefono = telefono;
        this.email = email;
        this.nota = nota;
        this.email_usuario = email_usuario;
        this.fecha_registro = fecha_registro;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getEmail_usuario() {
        return email_usuario;
    }

    public void setEmail_usuario(String email_usuario) {
        this.email_usuario = email_usuario;
    }
}
