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
public class Termino {
    
    private int idTermino;
    private String valor_Termino;

    public Termino() {
        idTermino = -1;
        valor_Termino = "";
    }

    public Termino(int idTermino, String valor_Termino) {
        this.idTermino = idTermino;
        this.valor_Termino = valor_Termino;
    }

    public int getIdTermino() {
        return idTermino;
    }

    public void setIdTermino(int idTermino) {
        this.idTermino = idTermino;
    }

    public String getValor_Termino() {
        return valor_Termino;
    }

    public void setValor_Termino(String valor_Termino) {
        this.valor_Termino = valor_Termino;
    }        
    
}
