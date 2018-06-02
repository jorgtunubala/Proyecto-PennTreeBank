/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

/**
 *
 * @author JORGE
 */
public class Etiqueta {
    
    private int idEtiqueta;
    private String simbolo;
    private String etiqueta;

    public Etiqueta(){
        this.idEtiqueta = -1;
        this.simbolo = "";
        this.etiqueta = "";
    }

    public int getIdEtiqueta() {
        return idEtiqueta;
    }

    public void setIdEtiqueta(int idEtiqueta) {
        this.idEtiqueta = idEtiqueta;
    }
    
    public Etiqueta(String simbolo, String etiqueta) {
        this.simbolo = simbolo;
        this.etiqueta = etiqueta;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }    
    
}
