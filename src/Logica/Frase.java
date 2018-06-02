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
public class Frase {
    
    private int id_Frase;
    private int folder_Frase;
    private String valor_Frase;
    private String valor_FraseEtix48;
    private String valor_FraseEtix12;

    public Frase() {
        this.id_Frase = -1;
        this.folder_Frase = -1;
        this.valor_Frase = "";
        this.valor_FraseEtix48 = "";
        this.valor_FraseEtix12 = "";
    }        

    public int getId_Frase() {
        return id_Frase;
    }

    public void setId_Frase(int id_Frase) {
        this.id_Frase = id_Frase;
    }

    public int getFolder_Frase() {
        return folder_Frase;
    }

    public void setFolder_Frase(int folder_Frase) {
        this.folder_Frase = folder_Frase;
    }

    public String getValor_Frase() {
        return valor_Frase;
    }

    public void setValor_Frase(String valor_Frase) {
        this.valor_Frase = valor_Frase;
    }

    public String getValor_FraseEtix48() {
        return valor_FraseEtix48;
    }

    public void setValor_FraseEtix48(String valor_FraseEtix28) {
        this.valor_FraseEtix48 = valor_FraseEtix28;
    }

    public String getValor_FraseEtix12() {
        return valor_FraseEtix12;
    }

    public void setValor_FraseEtix12(String valor_FraseEtix12) {
        this.valor_FraseEtix12 = valor_FraseEtix12;
    }
    
}
