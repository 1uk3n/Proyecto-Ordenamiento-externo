/**
 * Ordena por mezcla equilibrada un archivo con alumnos de la forma: nombre(s),apellidos,n° de cuenta.
 * 
 */
package proyecto.ordenamiento.externo;

import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Arrays;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * @author Zuriel Zárate García.
 */

public class MezclaE{
    
    /**
     * Divide la colección original en bloques (hasta que deje de estar ordenada).
     * @param alumnos La lista original de alumnos.
     * @param ordenamiento El tipo de ordenamiento. Nombre, apellido ó # de cuenta.
     * @param i posición de los elementos en la colección.
     * @return Devuelve un bloque de la colección original.
     */
    
    public static LinkedList<String> bloque(LinkedList<String> alumnos, int ordenamiento, int i) {

        LinkedList<String> lista = new LinkedList<String>();
        String[] keys = new String[alumnos.size()];
        Double[] llaves = new Double[alumnos.size()];
        int aux, j = i + 1;

        i++;
        j++;

        if (ordenamiento != 2) {

            if (i == alumnos.size() - 1) {
                //System.out.println(i+" es igual a "+(lista.size()-1));
                lista.add(alumnos.get(i++));
            } else {

                for (int k = i; k < alumnos.size(); k++) {
                    String[] datos = alumnos.get(k).split(",");
                    keys[k] = datos[ordenamiento];
                    //System.out.println(keys[k]);
                }

                aux = 1;
                while (j < alumnos.size() && aux != -1) {
                    //System.out.println(keys[i].compareToIgnoreCase(keys[j]));
                    if (keys[i].compareToIgnoreCase(keys[j]) <= 0) {
                        //System.out.println("Se añadio a: "+alumnos.get(i));
                        //System.out.println(keys[i]+" es menor que "+keys[j]);
                        lista.add(alumnos.get(i));
                        i++;
                        j++;
                    } else {
                        aux = -1;
                    }
                }

                if (i <= alumnos.size() - 1) {
                    //System.out.println("Falta agregar a "+(i)+" :v");
                    lista.add(alumnos.get(i));
                }
            }

        } else {

            if (i == alumnos.size() - 1) {
                //System.out.println(i+" es igual a "+(lista.size()-1));
                lista.add(alumnos.get(i++));
            } else {

                for (int k = i; k < alumnos.size(); k++) {
                    //String[] datos = alumnos.get(k).split(",");
                    llaves[k] = Double.parseDouble(alumnos.get(k).split(",")[ordenamiento]);
                    //System.out.println(keys[k]);
                }

                aux = 1;
                while (j < alumnos.size() && aux != -1) {
                    //System.out.println();
                    if (llaves[i] <= llaves[j]) {
                        //System.out.println("Se añadio a: "+alumnos.get(i));
                        //System.out.println(llaves[i]+" es menor que "+llaves[j]);
                        lista.add(alumnos.get(i));
                        i++;
                        j++;
                    } else {
                        aux = -1;
                    }
                }

                if (i <= alumnos.size() - 1) {
                    //System.out.println("Falta agregar a "+(i)+" :v");
                    lista.add(alumnos.get(i));
                }
            }
        }
        
        if (i == alumnos.size() - 1) {
            i++;
        }
        //Se agrega el último índice en i;
        //System.out.println("El indice retornado es: "+i);
        lista.add(String.valueOf(i));
        return lista;
    }

    /**
     * Introduce los bloques en archivos auxiliares y mezcla los bloques de cada archivo. (Archivo A y Archivo B).
     * @param alumnos La lista original de alumnos.
     * @param ordenamiento El tipo de ordenamiento. Nombre, apellido ó # de cuenta.
     * @param archivos Arreglo con los arreglos auxiliares, el archivo final y el archivo con la historia de iteraciones.
     * @return lista con bloques mezclados.
     * @throws IOException 
     */
    
    public static LinkedList<LinkedList<String>> primerFase(LinkedList<String> alumnos, int ordenamiento, String[] archivos) throws IOException{

        LinkedList<LinkedList<String>> listaA = new LinkedList<LinkedList<String>>();
        LinkedList<LinkedList<String>> listaB = new LinkedList<LinkedList<String>>();

        int i = -1;
        int count = 0;
        do {
            //Escribimos bloque por bloque en los archivos A y B.
            if (i < alumnos.size()) {
                listaA.add(bloque(alumnos, ordenamiento, i));
                i = Integer.parseInt(listaA.get(count).removeLast());
                //System.out.println("Nuevo bloque agregado a \"Archivo A\": ");
                //imprimirBloques(listaA.getLast());
            }

            if (i < alumnos.size()) {
                listaB.add(bloque(alumnos, ordenamiento, i));
                i = Integer.parseInt(listaB.get(count).removeLast());
                //System.out.println("Nuevo bloque agregado a \"Archivo B\": ");
                //imprimirBloques(listaB.getLast());
            }

            count++;

        } while (i < alumnos.size());
        
        System.out.println("Archivo A: ");
        imprimirLista(listaA);
        
        System.out.println("Archivo B: ");
        imprimirLista(listaB);
        
        return mezcla(listaA, listaB, ordenamiento, archivos);
    }
    
    /**
     * Se vuelven a mezclar los bloques mmezclados anteriormente.
     * @param listaC Lista de bloques mezclados.
     * @param ordenamiento Tipo de ordenamiento, por nombre, apellido o # de cuenta.
     * @param archivos Arreglo con los archivos auxiliares, el archivo final y el archivo con la historia de iteraciones.
     * @return La lista con los bloques grandes mezclados.
     * @throws IOException 
     */
    
    public static LinkedList<LinkedList<String>> segundaFase(LinkedList<LinkedList<String>> listaC, int ordenamiento, String[] archivos) throws IOException {

        LinkedList<LinkedList<String>> listaA = new LinkedList<LinkedList<String>>();
        LinkedList<LinkedList<String>> listaB = new LinkedList<LinkedList<String>>();
        while (listaC.size() != 1) {

            for (int i = 0; i < listaC.size(); i++) {
                if (i % 2 == 0) {
                    listaA.add(listaC.get(i));
                } else {
                    listaB.add(listaC.get(i));
                }
            }
            listaC = mezcla(listaA, listaB, ordenamiento, archivos);

            listaA.clear();
            listaB.clear();
        }
        return listaC;
    }

    /**
     * Mezcla los bloques de los archivos auxiliares A y B.
     * @param listaA Bloques de la lista superior.
     * @param listaB Bloques de la lista inferior. 
     * @param ordenamiento Tipo de ordenamiento, por apellido, nombre ó número de cuenta.
     * @param archivos Arreglo con los archivos auxiliares, el archivo final y el archivo con el historial de iteraciones.
     * @return Lista con los bloques mezclados.
     * @throws IOException 
     */
    
    public static LinkedList<LinkedList<String>> mezcla(LinkedList<LinkedList<String>> listaA, LinkedList<LinkedList<String>> listaB, int ordenamiento,  String[] archivos) throws IOException {
        
        Archivos.writeFile("\n ========== MEZCLANDO... ==========", archivos[2]);
        LinkedList<LinkedList<String>> listaC = new LinkedList<LinkedList<String>>();

        int count = 0;
        boolean y;
        Archivos.writeFile("\n Mezcla de bloques...\n", archivos[2]);
        
        do {

            listaC.add(new LinkedList<String>());

            if (listaB.size() > count) {
                y = true;
            } else {
                y = false;
            }

            int pA = 0;
            int pB = 0;

            //Mezcla 
            if (y) {
                
                Archivos.writeFile("* >>>>>>>>>>>>>>>>>>>>>>>>>Se mezclan estos dos", archivos[2]);
                Archivos.writeFile("      Bloque superior: " + Arrays.asList(listaA.get(count)) + "\n", archivos[2]);
                Archivos.writeFile("**>>\nBloque nuevo: " + Arrays.asList(listaA.get(count)), archivos[0]);

                Archivos.writeFile("      Bloque superior: " + Arrays.asList(listaB.get(count)) + "\n", archivos[2]);
                Archivos.writeFile("* >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n", archivos[2]);
                Archivos.writeFile("Bloque nuevo: " + Arrays.asList(listaB.get(count)) + "\n**>>\n", archivos[1]);
                
                while (true) {
                        
                    if (pA == listaA.get(count).size() || pB == listaB.get(count).size()) {
                        break;
                    }

                    String[] elemento1 = listaA.get(count).get(pA).split(",");
                    String[] elemento2 = listaB.get(count).get(pB).split(",");

                    int aux = elemento1[ordenamiento].compareToIgnoreCase(elemento2[ordenamiento]);
                    if (aux <= 0) {
                        listaC.get(count).add(listaA.get(count).get(pA));
                        pA++;
                    } else {;
                        listaC.get(count).add(listaB.get(count).get(pB)); 
                        pB++;
                    }
       
                }

                while (pA < listaA.get(count).size()) {
                    listaC.get(count).add(listaA.get(count).get(pA));
                    pA++;
                }

                while (pB < listaB.get(count).size()) {
                    listaC.get(count).add(listaB.get(count).get(pB));
                    pB++;
                }

            } else {

                while (pA < listaA.get(count).size()) {
                    listaC.get(count).add(listaA.get(count).get(pA));
                    pA++;
                }

            }
            count++;

        } while (count < listaA.size());
        
        //Se agregan los bloques mezclados al archivo C.
        Archivos.writeFile("MEZCLADOS: "+ String.valueOf(listaC)+"\n", archivos[2]);
        
        return listaC;

    }

    /**
     * Ordena los elementos de un archivo por Mezcla Equilibrada.
     * @param ordenamiento El tipo de ordenamiento, por nombre, apellido ó número de cuenta.
     * @return Retorna el path del archivo final en un string. 
     * @throws IOException 
     */
    
    public static String mezclaEquilibrada(int ordenamiento) throws IOException {
        
        String path = Paths.get("Archivos ordenamientos").toAbsolutePath().toString() + File.separatorChar + "original.txt";

        //Se ingresa la información de los alumnos a un arreglo y después a una lista ligada.
        String[] data = Archivos.readFile(path).split("\n");
        LinkedList<String> alumnos = new LinkedList<String>();
        
        for(int i=0; i<data.length; i++){
            alumnos.add(data[i]);
        }
        
        //CREAR CARPETA.
        String nombreCarpeta = "";

        switch (ordenamiento) {
            case 0: {nombreCarpeta = "Archivos (Ord. por Nombre)"; break;}
            case 1: {nombreCarpeta = "Archivos (Ord. por Apellido)"; break;}
            case 2: {nombreCarpeta = "Archivos (Ord. por # Cuenta)"; break;}
        }

        //inicializarDir crea las carpetas iniciales y el archivo iteraciones.txt, retorna la dirección del archivo.
        File iteraciones = Utilidades.inicializarDir(Paths.get("Archivos ordenamientos").toAbsolutePath(), "Mezcla equilibrada", nombreCarpeta);

        //Creamos la carpeta de archivos auxiliares o vaciamos la anterior
        File aux = new File(iteraciones.toPath().getParent().toAbsolutePath().toString() + File.separatorChar + "Archivos auxiliares");
        if (!aux.exists()) {
            aux.mkdir();
        } else {
            Utilidades.borrarDirectorio(aux);
        }
        
        //Creamos nuestros Archivos auxiliares.
        String[] archivos = new String[4];
        archivos[0] = aux.toString() + File.separatorChar + "Archivo_A.txt";
        archivos[1] = aux.toString() + File.separatorChar + "Archivo_B.txt"; 
        archivos[2] = iteraciones.toPath().getParent().toAbsolutePath().toString() + File.separatorChar + "Iteraciones.txt"; 
        archivos[3] = iteraciones.toPath().getParent().toAbsolutePath().toString() + File.separatorChar + "Mezcla Equilibrada terminada.txt";

        LinkedList<LinkedList<String>> listaC = primerFase(alumnos, ordenamiento, archivos);
        listaC = segundaFase(listaC, ordenamiento, archivos);
        
        Archivos.writeFile("       LISTA ORDENADA\n", archivos[2]);
        for(LinkedList<String> bloque:listaC){
            for(int i=0; i<bloque.size(); i++){
                data[i] = bloque.get(i);
                Archivos.writeFile(data[i], archivos[2]);
                Archivos.writeFile(data[i], archivos[3]);
            }
        }

        return archivos[3];
    }

    /**
     * Método auxiliar para imprimir una lista ligada de listas ligadas.
     * @param lista Lista que se quiere imprimir.
     */
    
    public static void imprimirLista(LinkedList<LinkedList<String>> lista) {
        String elementos = "";
        for(LinkedList<String> bloque:lista){
            for(int i=0; i<bloque.size(); i++){
                elementos = elementos + String.valueOf(bloque.get(i) + "\n");
            }
        }
        String[] alumnos = elementos.split("\n");
        System.out.println("{"+Arrays.asList(alumnos)+"}\n");
    }
    
    /**
     * Método auxiliar para imprimir una lista ligada de Strings..
     * @param lista Lista que se quiere imprimir.
     */
    
    public static void imprimirBloques(LinkedList<String> lista){
        String[] elementos = new String[lista.size()];
        for(int i=0; i<lista.size(); i++){
            elementos[i] = lista.get(i);
        }
        System.out.println(Arrays.asList(elementos)+"\n");
        //Archivos.writeFile(Arrays.asList(elementos)+"\n", path);
    }
 
}
