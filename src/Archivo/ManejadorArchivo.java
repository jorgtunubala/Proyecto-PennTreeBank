/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Archivo;

import Logica.Etiqueta;
import Logica.Frase;
import Logica.Termino;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author JORGE
 */
public class ManejadorArchivo {

    private final Archivo file;
    private ArrayList<Etiqueta> etiquetas;
    private ArrayList<Etiqueta> etiquetasEsp;
    private ArrayList<Termino> terminos;

    public ManejadorArchivo() {
        file = new Archivo();
        etiquetas = new ArrayList<>();
        etiquetasEsp = new ArrayList<>();
    }

    /*Método que carga las etiquetas con su respectivo identificador.*/
    public ArrayList<Etiqueta> cargarEtiquetas() {
        Etiqueta etiqueta;
        String[] parametros;
        try {
            file.abrirArchivo("en-ptb.txt", false);
            while (file.puedeLeer()) {
                String linea = file.leerArchivo();
                parametros = linea.split("\t");
                etiqueta = new Etiqueta(parametros[0], parametros[1]);
                etiqueta.setIdEtiqueta(this.getIdEtiqueta(parametros[1]));
                if (etiquetas.isEmpty()) {
                    etiquetas.add(etiqueta);
                } else {
                    boolean esta = false;
                    for (Etiqueta etiqueta1 : etiquetas) {
                        if (etiqueta1.getIdEtiqueta() == etiqueta.getIdEtiqueta()) {
                            esta = true;
                            break;
                        }
                    }
                    if (!esta) {
                        etiquetas.add(etiqueta);
                    }
                }
            }
            file.cerrarArchivo();
            etiquetas = this.ordenarEtiquetas(etiquetas);
            this.crearArchivoEtiquetas();
            return etiquetas;
        } catch (IOException ex) {
            System.out.println("Error al abrir archivo.");
        }
        return null;
    }

    /*Metodo que le asigna un identificador a cada etiqueta segun el mapeo dado en el archivo.*/
    private int getIdEtiqueta(String valorEtiqueta) {
        int id = -1;
        switch (valorEtiqueta) {
            case "VERB":
                id = 1;
                break;
            case "NOUN":
                id = 2;
                break;
            case "ADJ":
                id = 3;
                break;
            case "ADV":
                id = 4;
                break;
            case "ADP":
                id = 5;
                break;
            case "CONJ":
                id = 6;
                break;
            case "DET":
                id = 7;
                break;
            case "NUM":
                id = 8;
                break;
            case "PRT":
                id = 9;
                break;
            case "X":
                id = 10;
                break;
            case ".":
                id = 11;
                break;
            case "PRON":
                id = 12;
                break;
        }
        return id;
    }

    /*Metodo que ordena las etiquetas de forma ascendente por su identificador*/
    private ArrayList<Etiqueta> ordenarEtiquetas(ArrayList<Etiqueta> etiquetas) {
        Collections.sort(etiquetas, (Etiqueta et1, Etiqueta et2) -> new Integer(et1.getIdEtiqueta()).compareTo(et2.getIdEtiqueta()));
        return etiquetas;
    }

    /*Metodo que permite escribir en un archivo los datos a insertar en la base de datos.*/
    private boolean crearArchivoEtiquetas() {
        file.borrarArchivo("./Insert Files/etiquetas.sql");
        try {
            String miEtiqueta;
            file.abrirArchivo("./Insert Files/etiquetas.sql", true);
            for (Etiqueta etiqueta : etiquetas) {
                miEtiqueta = "INSERT INTO dbo.ETIQUETA (ID_ETIQUETA,NOMBRE_ETIQUETA) VALUES(";
                miEtiqueta += +etiqueta.getIdEtiqueta() + ",'" + etiqueta.getEtiqueta() + "')";
                file.escribirArchivo(miEtiqueta);
            }
            file.cerrarArchivo();
            System.out.println("Archivo creado satisfactoriamente.");
            return true;
        } catch (IOException ex) {
            System.out.println("Error al crear archivo.");
        }
        return false;
    }

    /*Metodo que me permite cargar los terminos de cada oracion*/
    public ArrayList<Termino> cargarTerminos() {
        terminos = new ArrayList<>();
        ArrayList<String> frases = cargarOraciones();
        int i = 1;
        String[] cadena;
        Termino termino = null;
        for (String frase : frases) { //Recorrer cada frase para asignarle un identificador a cada termino.
            cadena = frase.split(" ");
            for (String terminoFrase : cadena) { //obtener cada termino de la frase.                
                if (!terminoFrase.isEmpty()) {
                    String[] valor = terminoFrase.split("/");
                    valor[0] = this.arregloComillaSimple(valor[0]);
                    if (terminos.isEmpty()) {
                        termino = new Termino(i, valor[0]);
                        terminos.add(termino);
                        i++;
                    } else if (!existeTermino(valor[0])) {
                        termino = new Termino(i, valor[0]);
                        terminos.add(termino);
                        i++;
                    }
                }
            }
        }
        this.crearArchivoTerminos();
        return terminos;
    }

    /*Metodo que me indica si el termino ya ha sido agregado*/
    private boolean existeTermino(String termino) {
        for (Termino valor : terminos) {
            if (valor.getValor_Termino().equals(termino)) {
                return true;
            }
        }
        return false;
    }

    /*Metodo que retorna una lista de las frases de cada archivo.*/
    private ArrayList<String> cargarOraciones() {
        ArrayList<String> oraciones = new ArrayList<>();
        File carpeta = new File("./tagged");
        int i = 1;
        ArrayList<String> archivos = file.listarFicherosCarpeta(carpeta); //obtengo el nombre de todos los archivos de la carpeta indicada.
        String frase;
        for (String nombreArchivo : archivos) {
            frase = getFrase("./tagged/" + nombreArchivo);
            oraciones.add(frase);
        }
        return oraciones;
    }

    /*Método que lee el archivo recibido y me retorna en una linea la palabra 
      con su etiqueta. y eliminando corchetes y mas de 1 espacio.*/
    private String getFrase(String archivo) {
        String linea;
        String frase = "";
        String[] parametros;
        try {
            file.abrirArchivo(archivo, false);
            while (file.puedeLeer()) {
                linea = file.leerArchivo();
                if (!linea.isEmpty()) {
                    linea = linea.replace("[", "");
                    linea = linea.replace("]", "");
                    linea = linea.replace("  ", "");
                    linea = linea.replace("======================================", "");
                    parametros = linea.split(" ");
                    for (String parametro : parametros) {
                        if (!parametro.equals(" ")) {
                            frase += parametro + " ";
                        }
                    }
                }
            }
            file.cerrarArchivo();
        } catch (IOException ex) {
            System.out.println("Error al abrir archivo.");
        }
        frase = frase.replace("  ", " ");
        return frase;
    }

    /*Metodo que permite crear el termino legible para insertar en sql server*/
    private String arregloComillaSimple(String termino) {
        if (termino.equals("n't")) {
            termino = "n''t";
        } else if (termino.equals("''")) {
            termino = "''''";
        } else if (termino.equals("C'mon")) {
            termino = "C''mon";
        } else if (termino.equals("O'Connor")) {
            termino = "O''Connor";
        } else if (termino.equals("CREATOR'S")) {
            termino = "CREATOR''S";
        } else if (termino.equals("O'Brien")) {
            termino = "O''Brien";
        } else if (termino.equals("O'Loughlin")) {
            termino = "O''Loughlin";
        } else if (termino.equals("D'Amico")) {
            termino = "D''Amico";
        } else if (termino.equals("Dunkin'")) {
            termino = "Dunkin''";
        } else if (termino.equals("O'Neill")) {
            termino = "O''Neill";
        } else if (termino.contains("'")) {
            termino = "'" + termino;
        }
        return termino;
    }

    /*Metodo que permite escribir en un archivo los datos a insertar en la base de datos.*/
    private boolean crearArchivoTerminos() {
        file.borrarArchivo("./Insert Files/terminos.sql");
        try {
            String miTermino;
            file.abrirArchivo("./Insert Files/terminos.sql", true);
            for (Termino termino : terminos) {
                miTermino = "INSERT INTO dbo.TERMINO (ID_TERMINO,VALOR_TERMINO) VALUES(";
                miTermino += +termino.getIdTermino() + ",'" + termino.getValor_Termino() + "')";
                file.escribirArchivo(miTermino);
            }
            file.cerrarArchivo();
            System.out.println("Archivo creado satisfactoriamente.");
            return true;
        } catch (IOException ex) {
            System.out.println("Error al crear archivo.");
        }
        return false;
    }

    /*Metodo que me permite cargar las frases de cada archivo*/
    public ArrayList<Frase> cargarFrases() {
        ArrayList<Frase> misFrases = new ArrayList<>();
        int i = 1;
        File carpeta = new File("./raw");
        ArrayList<String> archivos = file.listarFicherosCarpeta(carpeta); //obtengo el nombre de todos los archivos de la carpeta indicada.
        String frase;
        for (String nombreArchivo : archivos) {
            Frase miFrase = new Frase();
            miFrase.setId_Frase(i);
            miFrase.setFolder_Frase(i);
            i++;
            miFrase.setValor_Frase(getFraseArchivo(nombreArchivo));
            miFrase.setValor_FraseEtix48(getFrase("./tagged/"+nombreArchivo));
            miFrase.setValor_FraseEtix12(mapearFrase(nombreArchivo));
            misFrases.add(miFrase);            
        }        
        return misFrases;
    }

    private ArrayList<String> misOraciones() {
        ArrayList<String> misOraciones = new ArrayList<>();
        File carpeta = new File("./raw");
        ArrayList<String> archivos = file.listarFicherosCarpeta(carpeta); //obtengo el nombre de todos los archivos de la carpeta indicada.
        String frase;
        for (String nombreArchivo : archivos) {
            frase = getFraseArchivo(nombreArchivo);
            misOraciones.add(frase);
        }
        return misOraciones;
    }

    /*Metodo que me retorna la frase del archivo en una sola linea.*/
    private String getFraseArchivo(String archivo) {
        String frase = "", cadena;
        try {
            file.abrirArchivo("./raw/" + archivo, false);
            while (file.puedeLeer()) {
                cadena = file.leerArchivo();
                if (cadena.equals(".START ") || cadena.isEmpty()) {
                    frase += "";
                } else {
                    frase += cadena;
                }
            }
        } catch (IOException ex) {
            System.out.println("Error al abrir archivo.");
        }
        return frase;
    }

    /*Método que permite realizar el nuevo mapeo para cada palabra, por cada archivo.*/
    public String mapearFrase(String archivo) {
        String mapeo = "";
        ArrayList<String> frasesMapeadas = new ArrayList<>();
        if (!this.mapeoEtiquetas().isEmpty()) {
            String[] etiquetado;
            String[] palabra;
            String frase = getFrase("./tagged/" + archivo); //obtener la frase del archivo en un mejor formato.                 
            etiquetado = frase.split(" ");
            for (String etiquetado1 : etiquetado) {
                if (!etiquetado1.isEmpty()) {
                    palabra = etiquetado1.split("/");
                    for (Etiqueta etiqueta : etiquetasEsp) {  //buscar las etiquetas y mapearlas al formato de 12 etiquetas.
                        if (etiqueta.getSimbolo().contains(palabra[1])) {
                            mapeo += palabra[0] + "/" + etiqueta.getEtiqueta() + " ";
                            break;
                        }
                    }
                }
            }         
        } else {
            System.out.println("Error al cargar el nuevo mapeo de las etiquetas.");
        }        
        return mapeo;
    }

    /*Genera una lista de las etiquetas con sus nuevo mapeo.*/
    public ArrayList<Etiqueta> mapeoEtiquetas() {
        Etiqueta etiqueta;
        String[] parametros;
        try {
            file.abrirArchivo("en-ptb.txt", false);
            while (file.puedeLeer()) {
                String linea = file.leerArchivo();
                parametros = linea.split("\t");
                etiqueta = new Etiqueta(parametros[0], parametros[1]);
                etiquetasEsp.add(etiqueta);
            }
            file.cerrarArchivo();
            return etiquetasEsp;
        } catch (IOException ex) {
            System.out.println("Error al abrir archivo.");
        }
        return null;
    }

    public void mapeoOriginal() {
        String mapeo = "";
        ArrayList<String> frasesMapeadas = new ArrayList<>();
        if (!this.mapeoEtiquetas().isEmpty()) {
            System.out.println("Se cargo el nuevo mapeo de las etiquetas con exito.");
            File carpeta = new File("./tagged");
            int i = 1;
            ArrayList<String> archivos = file.listarFicherosCarpeta(carpeta); //obtengo el nombre de todos los archivos de la carpeta indicada.
            for (String nombreArchivo : archivos) {
                String[] etiquetado;
                String[] palabra;
                String frase = getFrase("./tagged/" + nombreArchivo); //obtener la frase del archivo en un mejor formato.               
                System.out.println("Frase " + i);
                i++;
                System.out.println(frase);
            }
        } else {
            System.out.println("Error al cargar el nuevo mapeo de las etiquetas.");
        }
    }

}
