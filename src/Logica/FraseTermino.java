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
public class FraseTermino {
    
    private int id_Orden;
    private Termino termino;
    private Frase frase;
    private int etiquetaPredecesora;
    private int etiquetaSucesora;

    public FraseTermino() {
        this.id_Orden = -1;
        this.termino = new Termino();
        this.frase = new Frase();
        this.etiquetaPredecesora = -1;
        this.etiquetaSucesora = -1;
    }        

    public int getId_Orden() {
        return id_Orden;
    }

    public void setId_Orden(int id_Orden) {
        this.id_Orden = id_Orden;
    }

    public Termino getTermino() {
        return termino;
    }

    public void setTermino(Termino termino) {
        this.termino = termino;
    }

    public Frase getFrase() {
        return frase;
    }

    public void setFrase(Frase frase) {
        this.frase = frase;
    }

    public int getEtiquetaPredecesora() {
        return etiquetaPredecesora;
    }

    public void setEtiquetaPredecesora(int etiquetaPredecesora) {
        this.etiquetaPredecesora = etiquetaPredecesora;
    }

    public int getEtiquetaSucesora() {
        return etiquetaSucesora;
    }

    public void setEtiquetaSucesora(int etiquetaSucesora) {
        this.etiquetaSucesora = etiquetaSucesora;
    }
    
}
