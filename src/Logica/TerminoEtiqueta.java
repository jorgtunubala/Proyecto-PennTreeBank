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
public class TerminoEtiqueta {
    
    private Termino termino;
    private Etiqueta etiqueta;
    
    public TerminoEtiqueta(){
        this.termino = new Termino();
        this.etiqueta = new Etiqueta();
    }

    public Termino getTermino() {
        return termino;
    }

    public void setTermino(Termino termino) {
        this.termino = termino;
    }

    public Etiqueta getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(Etiqueta etiqueta) {
        this.etiqueta = etiqueta;
    }
    
}
