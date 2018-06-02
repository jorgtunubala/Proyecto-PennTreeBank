/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Archivo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author JORGE
 */
public class Archivo {

    //Atributos
    private BufferedWriter archivoEscritura;
    private BufferedReader archivoLectura;

    public Archivo() {

    }

    //Metodos
    public void abrirArchivo(String nombre, boolean escritura) throws IOException {
        if (escritura == true) {
            this.archivoEscritura = new BufferedWriter(new FileWriter(nombre, true));
        } else {
            this.archivoLectura = new BufferedReader(new FileReader(nombre));
        }
    }

    public void escribirArchivo(String datos) throws IOException {
        archivoEscritura.write(datos);
        archivoEscritura.newLine();
    }

    public String leerArchivo() throws IOException {
        return archivoLectura.readLine();
    }

    public void cerrarArchivo() throws IOException {
        if (archivoEscritura != null) {
            archivoEscritura.close();
        }
        if (archivoLectura != null) {
            archivoLectura.close();
        }
    }

    public boolean puedeLeer() throws IOException {
        return archivoLectura.ready();
    }

    public String[] LeerPalabras(int cantidad) {
        String[] palabras = new String[cantidad];
        int i = 0;
        try {
            while (this.puedeLeer() && i < cantidad) {
                palabras[i] = this.leerArchivo();
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return palabras;
    }

    //cuenta la cantidad de lineas que tiene el archivo
    public int contarLineas(String nombre) throws IOException {
        abrirArchivo(nombre, false);
        int lineas = 0;
        while (puedeLeer()) {
            leerArchivo();
            lineas++;
        }
        cerrarArchivo();
        return lineas;
    }

    public void crearArchivo(String nombre) throws IOException {
        File archivo = new File(nombre);
        //System.out.println("Archivo creado");
    }

    public void borrarArchivo(String nombre) {
        File archivo = new File(nombre);
        archivo.delete();
        //System.out.println("Archivo borrado");
    }

    public ArrayList<String> listarFicherosCarpeta(File carpeta) {
        ArrayList<String> archivos = new ArrayList<>();
        for (File ficheroEntrada : carpeta.listFiles()) {
            if (ficheroEntrada.isDirectory()) {
                listarFicherosCarpeta(ficheroEntrada);
            } else {                
                archivos.add(ficheroEntrada.getName());
            }
        }
        return archivos;
    }
}
