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
public class Cola extends Proceso {

    public Cola() {
        raiz = null;
        fondo = null;
    }

    public boolean vacia() {
        return raiz == null;
    }

    public void insertar(int llegada, int rafaga, int comienzo, int tfinal, int retorno, int espera, int bloqueo, String nombre) {
        Proceso nuevo;
        nuevo = new Proceso();
        nuevo.t_llegada = llegada;
        nuevo.t_rafaga = rafaga;
        nuevo.t_comienzo = comienzo;
        nuevo.t_final = tfinal;
        nuevo.t_retorno = retorno;
        nuevo.t_espera = espera;
        nuevo.nombre = nombre;
        nuevo.bloqueado = bloqueo;
        nuevo.siguiente = null;
        if (vacia()) {
            raiz = nuevo;
            fondo = nuevo;
        } else {
            fondo.siguiente = nuevo;
            fondo = nuevo;
        }
    }

    public String extraer() {
        if (!vacia()) {
            String nom = raiz.nombre;
            if (raiz == fondo) {
                raiz = null;
                fondo = null;
            } else {
                raiz = raiz.siguiente;
            }
            return nom;
        } else {
            return null;
        }
    }

    public void imprimir() {
        Proceso aux = raiz;
        System.out.println("Cola de procesos: ");
        while (aux != null) {
            System.out.println(aux.nombre + " llego en " + aux.t_llegada + " con una rafaga de " + aux.t_rafaga + " comenzo en  "
                    + aux.t_comienzo + " acabo en " + aux.t_final + " retorno en " + aux.t_retorno + " con una espera de "
                    + aux.t_espera );
            aux = aux.siguiente;
        }
        System.out.println();
    }

}
