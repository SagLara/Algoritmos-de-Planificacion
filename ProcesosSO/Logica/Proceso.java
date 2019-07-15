/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

/**
 *
 * @author USUARIO
 */
public class Proceso {

    public int t_llegada;
    public int t_rafaga;
    public int t_comienzo;
    public int t_final;
    public int t_retorno;
    public int t_espera;
    public int bloqueado;
    public int prioridad;
    public String nombre;
    public Proceso siguiente;
    public Proceso raiz;
    public Proceso fondo;

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public int getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(int bloqueado) {
        this.bloqueado = bloqueado;
    }

    public int getT_llegada() {
        return t_llegada;
    }

    public void setT_llegada(int t_llegada) {
        this.t_llegada = t_llegada;
    }

    public int getT_rafaga() {
        return t_rafaga;
    }

    public void setT_rafaga(int t_rafaga) {
        this.t_rafaga = t_rafaga;
    }

    public int getT_comienzo() {
        return t_comienzo;
    }

    public void setT_comienzo(int t_comienzo) {
        this.t_comienzo = t_comienzo;
    }

    public int getT_final() {
        return t_final;
    }

    public void setT_final(int t_final) {
        this.t_final = t_final;
    }

    public int getT_retorno() {
        return t_retorno;
    }

    public void setT_retorno(int t_retorno) {
        this.t_retorno = t_retorno;
    }

    public int getT_espera() {
        return t_espera;
    }

    public void setT_espera(int t_espera) {
        this.t_espera = t_espera;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Proceso getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Proceso siguiente) {
        this.siguiente = siguiente;
    }

    public Proceso getRaiz() {
        return raiz;
    }

    public void setRaiz(Proceso raiz) {
        this.raiz = raiz;
    }

    public Proceso getFondo() {
        return fondo;
    }

    public void setFondo(Proceso fondo) {
        this.fondo = fondo;
    }

}
