/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Archivo;

import Logica.Etiqueta;
import Logica.Frase;
import Logica.FraseTermino;
import Logica.Termino;
import Logica.TerminoEtiqueta;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
/**
 *
 * @author JORGE
 */
public class ManejadorArchivo {

    private final Archivo file;
    private ArrayList<Etiqueta> etiquetas;
    private final ArrayList<Etiqueta> etiquetasEsp;
    private final ArrayList<Termino> terminos;
    private final ArrayList<TerminoEtiqueta> terminoEtiqueta;
    private final ArrayList<Frase> misFrases;
    private final ArrayList<FraseTermino> fraseTerminos;

    public ManejadorArchivo() {
        file = new Archivo();
        etiquetas = new ArrayList<>();
        etiquetasEsp = new ArrayList<>();
        terminos = new ArrayList<>();
        terminoEtiqueta = new ArrayList<>();
        misFrases = new ArrayList<>();
        fraseTerminos = new ArrayList<>();
    }

    /*Método que carga las etiquetas con su respectivo identificador.*/
    public ArrayList<Etiqueta> cargarEtiquetas() {
        if(!etiquetas.isEmpty())
            return etiquetas;
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

    /*Metodo que permite escribir en un archivo los datos a insertar en la base de datos a la tabla de etiquetas.*/
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
        if(!terminos.isEmpty())
            return terminos;
        ArrayList<String> frases = cargarOraciones();
        int i = 1;
        String[] cadena;
        Termino termino = null;
        for (String frase : frases) { //Recorrer cada frase para asignarle un identificador a cada termino.
            cadena = frase.split(" ");
            for (String terminoFrase : cadena) { //obtener cada termino de la frase.                
                if (!terminoFrase.isEmpty()) {
                    String[] valor = terminoFrase.split("/");
                    //valor[0] = valor[0].replace("'", "''");
                    //valor[0] = this.arregloComillaSimple(valor[0]);
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
            if (valor.getValor_Termino().equalsIgnoreCase(termino)) {
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

    /*Metodo que permite escribir en un archivo los datos a insertar en la base de datos a la tabla de terminos.*/
    private boolean crearArchivoTerminos() {
        file.borrarArchivo("./Insert Files/terminos.sql");
        try {
            String miTermino, miTerminoSQL;
            file.abrirArchivo("./Insert Files/terminos.sql", true);
            for (Termino termino : terminos) {
                miTermino = "INSERT INTO dbo.TERMINO (ID_TERMINO,VALOR_TERMINO) VALUES(";
                miTerminoSQL = termino.getValor_Termino().replace("'", "''");
                miTermino += +termino.getIdTermino() + ",'" + miTerminoSQL + "')";
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
        if(!misFrases.isEmpty())
            return misFrases;
        int i = 1;
        File carpeta = new File("./raw");
        ArrayList<String> archivos = file.listarFicherosCarpeta(carpeta); //obtengo el nombre de todos los archivos de la carpeta indicada.        
        for (String nombreArchivo : archivos) {
            Frase miFrase = new Frase();
            miFrase.setId_Frase(i);
            miFrase.setFolder_Frase(i);
            i++;
            miFrase.setValor_Frase(getFraseArchivo(nombreArchivo));
            miFrase.setValor_FraseEtix48(getFrase("./tagged/" + nombreArchivo));
            miFrase.setValor_FraseEtix12(mapearFrase(nombreArchivo));
            misFrases.add(miFrase);
        }
        this.crearArchivoFrases(misFrases);
        return misFrases;
    }

    /*Metodo que retorna la lista de frases obtenida de cada archivo.*/
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
    private String mapearFrase(String archivo) {
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
    private ArrayList<Etiqueta> mapeoEtiquetas() {
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

    /*Metodo que permite escribir en un archivo los datos a insertar en la base de datos relacionados a la tabla de frases.*/
    private boolean crearArchivoFrases(ArrayList<Frase> frases) {
        file.borrarArchivo("./Insert Files/frases.sql");
        try {
            String miFrase;
            file.abrirArchivo("./Insert Files/frases.sql", true);
            for (Frase frase : frases) {
                miFrase = "INSERT INTO dbo.FRASE (ID_FRASE,FOLDER_FRASE,VALOR_FRASE,VALOR_FRASSEETIX48,VALOR_FRASSEETIX12) VALUES(";
                String fraseAux = frase.getValor_Frase().replace("'", "''");
                String fraseEtix48 = frase.getValor_FraseEtix48().replace("'", "''");
                String fraseEtix12 = frase.getValor_FraseEtix12().replace("'", "''");
                miFrase += frase.getId_Frase() + "," + frase.getFolder_Frase() + ",'" + fraseAux + "','" + fraseEtix48 + "','" + fraseEtix12 + "')";
                file.escribirArchivo(miFrase);
            }
            file.cerrarArchivo();
            System.out.println("Archivo creado satisfactoriamente.");
            return true;
        } catch (IOException ex) {
            System.out.println("Error al crear archivo.");
        }
        return false;
    }

    /*Metodo que me permite cargar los terminos de cada frase con su respectiva etiqueta por cada archivo*/
    public ArrayList<TerminoEtiqueta> cargarEtiquetaTermino() {
        if(!terminoEtiqueta.isEmpty())
            return terminoEtiqueta;
        if (terminos.isEmpty()) {
            this.cargarTerminos();
        }
        if (etiquetas.isEmpty()) {
            this.cargarEtiquetas();
        }       
        TerminoEtiqueta te;
        File carpeta = new File("./tagged");
        String frase;
        String[] terminosFrase;
        String[] parametros;
        int posTermino, posEtiqueta, i = 0;
        ArrayList<String> archivos = file.listarFicherosCarpeta(carpeta); //obtengo el nombre de todos los archivos de la carpeta indicada.        
        for (String nombreArchivo : archivos) {
            frase = mapearFrase(nombreArchivo);            
            terminosFrase = frase.split(" ");
            for (String termino : terminosFrase) {
                parametros = termino.split("/");                
                te = new TerminoEtiqueta();
                te.setTermino(getTermino(parametros[0]));
                te.setEtiqueta(getEtiqueta(parametros[1]));
                if (!this.existeTerminoEtiqueta(te)) {
                    terminoEtiqueta.add(te);
                }
            }
            i++;
            if(i==3)
                break;
        }
        this.crearArchivoTerminosEtiqueta();
        return terminoEtiqueta;
    }

    /*Metodo que me retorna una clase Termino segun el valor de su termino. Requerido por terminoEtiqueta*/
    private Termino getTermino(String termino) {
        for (Termino termino1 : terminos) {
            if (termino1.getValor_Termino().equalsIgnoreCase(termino)) {
                return termino1;
            }
        }        
        return null;
    }

    /*Metodo que me retorna una clase Etiqueta segun el valor de su etiqueta. Requerido por terminoEtiqueta*/
    private Etiqueta getEtiqueta(String etiqueta) {
        for (Etiqueta etiqueta1 : etiquetas) {
            if (etiqueta1.getEtiqueta().equalsIgnoreCase(etiqueta)) {
                return etiqueta1;
            }
        }        
        return null;
    }

    /*Metodo que impide agregar a la lista terminos y etiquetas repetidas.*/
    private boolean existeTerminoEtiqueta(TerminoEtiqueta valor) {
        for (TerminoEtiqueta terminoEtiqueta1 : terminoEtiqueta) {
            Termino te = terminoEtiqueta1.getTermino();
            Etiqueta et = terminoEtiqueta1.getEtiqueta();
            if(te.equals(valor.getTermino()) && et.equals(valor.getEtiqueta()))
                return true;
        }
        return false;
    }
    
    /*Metodo que permite escribir en un archivo los datos a insertar en la base de datos a la tabla de TerminoEtiqueta.*/
    private boolean crearArchivoTerminosEtiqueta(){
        file.borrarArchivo("./Insert Files/terminosEtiquetas.sql");
        try {
            String miTerminoEtiqueta;
            file.abrirArchivo("./Insert Files/terminosEtiquetas.sql", true);
            for (TerminoEtiqueta terminoEtiquetas : terminoEtiqueta) {
                miTerminoEtiqueta = "INSERT INTO dbo.TERMINO_ETIQUETA (ID_TERMINO,ID_ETIQUETA) VALUES(";                
                miTerminoEtiqueta += +terminoEtiquetas.getTermino().getIdTermino()+ "," + terminoEtiquetas.getEtiqueta().getIdEtiqueta() + ")";
                file.escribirArchivo(miTerminoEtiqueta);
            }
            file.cerrarArchivo();
            System.out.println("Archivo creado satisfactoriamente.");
            return true;
        } catch (IOException ex) {
            System.out.println("Error al crear archivo.");
        }
        return false;
    }
    
    public void crearArchivoFraseTermino(){
        Archivo file1 = new Archivo();
        file1.borrarArchivo("./Insert Files/fraseTermino.sql");
        try {
            file1.abrirArchivo("./Insert Files/fraseTermino.sql", true);
        } catch (IOException ex) {
            System.out.println("Error al abrir archivo.");
        }
        String miFrase;
        String []linea;
        String []parametros;
        String []parametrosAux;
        int idTermino = 0, orden, idPredecesora = 0, idEtiqueta = 0, idSucesora = 0;
        if(misFrases.isEmpty())
            this.cargarFrases();
        System.out.println("Id Frase\tId Termino\tTermino\tOrden\tEtiqueta\tId Etiqueta Predecesora\tId Etiqueta\tId Etiqueta Sucesora\tFolder");
        for (Frase frase : misFrases) {
            miFrase = frase.getValor_FraseEtix12();
            linea = miFrase.split(" ");
            List<String> lista = Arrays.asList(linea);
            orden = 1;
            for (String linea1 : lista) {               
                parametros = linea1.split("/");
                idTermino = this.getIdTermino(parametros[0]);
                idEtiqueta = this.getIdEtiquetaPreSu(parametros[1]);
                if(orden == 1)
                    idPredecesora = -1;
                else{
                    parametrosAux = lista.get(orden-2).split("/");
                    idPredecesora = this.getIdEtiquetaPreSu(parametrosAux[1]);
                }
                if(orden == lista.size())
                    idSucesora = -1;
                else{
                    parametrosAux = lista.get(orden).split("/");
                    idSucesora = this.getIdEtiquetaPreSu(parametrosAux[1]);
                }
                String miTermino = parametros[0].replace("'", "''");                
                System.out.println(frase.getId_Frase()+"\t"+idTermino+"\t"+parametros[0]+"\t"+orden+"\t"+parametros[1]+"\t"+idPredecesora+"\t"+idEtiqueta+"\t"+idSucesora+"\t"+frase.getFolder_Frase());
                try {                    
                    file1.escribirArchivo("INSERT INTO dbo.FRASE_TERMINO (ID_TERMINO,ID_FRASE,ID_ORDEN,TERMINO,ETIQUETA,ETIQUETA_PREDECESORA_ID,"
                            + "ETIQUETA_ID,ETIQUETA_SUCESORA_ID,FOLDER) "
                            + "VALUES("+idTermino+","+frase.getId_Frase()+","+orden+",'"+miTermino+"','"+parametros[1]+"',"+idPredecesora+","+idEtiqueta
                            +","+idSucesora+","+frase.getFolder_Frase()+")");
                } catch (IOException ex) {
                    System.out.println("Error al escribir en el archivo.");
                }
                orden++;                
            }            
        }
        try {
            file1.cerrarArchivo();
        } catch (IOException ex) {
            System.out.println("Error al cerrar archivo.");
        }
    }
    
    /*Metodo que obtiene el identificador segun el termino recibido.*/
    private int getIdTermino(String termino){
        if(terminos.isEmpty())
            this.cargarTerminos();
        for (Termino termino1 : terminos) {
            if(termino1.getValor_Termino().equalsIgnoreCase(termino))
                return termino1.getIdTermino();
        }
        return -1;  //Si retorna -1 indica que no existe ese termino.
    }       
    
    /*Metodo que obtiene el identificador segun la etiqueta recibida.*/
    private int getIdEtiquetaPreSu(String etiqueta){        
        if(etiquetas.isEmpty())
            this.cargarEtiquetas();
        for (Etiqueta etiqueta1 : etiquetas) {
            if(etiqueta1.getEtiqueta().equalsIgnoreCase(etiqueta))
                return etiqueta1.getIdEtiqueta();
        }
        return -1;  //Si retorna -1 indica que no existe esa etiqueta.
    } 
    
}
