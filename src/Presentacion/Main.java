/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentacion;

import Archivo.ManejadorArchivo;
import Logica.Etiqueta;
import Logica.Frase;
import Logica.Termino;
import Logica.TerminoEtiqueta;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author JORGE
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here                        
        ManejadorArchivo ma = new ManejadorArchivo();
        int opc;
        do {
            opc = menu();
            switch (opc) {
                case 1:
                    ArrayList<Termino> terminos = ma.cargarTerminos();
                    for (Termino termino : terminos) {
                        System.out.println("Id: " + termino.getIdTermino() + "\tTermino: " + termino.getValor_Termino());
                    }
                    break;
                case 2:
                    ArrayList<Etiqueta> etiquetas = ma.cargarEtiquetas();
                    for (Etiqueta mapeoEtiqueta : etiquetas) {
                        System.out.print("Id: " + mapeoEtiqueta.getIdEtiqueta());
                        System.out.print("\tSimbolo: " + mapeoEtiqueta.getSimbolo());
                        System.out.println("\tEtiqueta: " + mapeoEtiqueta.getEtiqueta());
                    }
                    System.out.println("Total Etiquetas Mapeadas: " + etiquetas.size());
                    break;
                case 3:
                    for (Frase cargarFrase : ma.cargarFrases()) {
                        System.out.println("Id: " + cargarFrase.getId_Frase() + " Folder: " + cargarFrase.getFolder_Frase() + " Frase: " + cargarFrase.getValor_FraseEtix12());
                    }
                    break;
                case 4:
                    ArrayList<TerminoEtiqueta> terminoEtiqueta = ma.cargarEtiquetaTermino();
                    for (TerminoEtiqueta tereti : terminoEtiqueta) {
                        System.out.println("Id Termino: " + tereti.getTermino().getIdTermino() + "Etiqueta: "+tereti.getTermino().getValor_Termino()+"\tId Etiqueta: " + tereti.getEtiqueta().getIdEtiqueta()+"\tEtiqueta: "+tereti.getEtiqueta().getEtiqueta());
                    }
                    System.out.println("Total terminos etiquetas: " + terminoEtiqueta.size());
                    break;
                case 5:
                    ma.crearArchivoFraseTermino();
                    break;
                case 0:
                    break;
            }
        } while (opc != 0);

    }

    private static int menu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("1. Ver todas los Terminos.");
        System.out.println("2. Ver todas las etiquetas.");
        System.out.println("3. Ver todas las frases.");
        System.out.println("4. Ver etiquetas_terminos");
        System.out.println("5. Ver frases_terminos");
        System.out.println("0. para Terminar.");
        return sc.nextInt();
    }

}
