package proyecto.ordenamiento.externo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * 
 * Clase Polifase, no modela ningún tipo de dato abstracto en específico. Esta clase implementa los métodos necesarios para la simulación del 
 * algoritmo de ordenamiento externo "Polifase".
 * 
 * @author Nuñez Quintana, Luis Axel
 * @author Zarate Garcia, Zuriel
 * @author Rosales López, Luis André
 */
public class Polifase {
    
   
    /**
     * Método que implementa las instrucciones para realizar polifase, entre ellas: Inicializar los archivos necesarios,
     * realizar la separación de bloques, el ordenamiento y la unión de los mismos.
     * 
     * @param blockSize entero que indica el número de elementos por bloque
     * @param ordenamiento entero que indica si el indicamiento será por nombre, apellido o número de cuenta
     */
    
    public static void polifase(int blockSize, int ordenamiento) throws FileNotFoundException, IOException{
        
        //Declaración de variables
        int alternador = 0; 
        int iteraciones = 0;
        String nombreCarpeta = null;
        Scanner sc = null;
        String archivoActual = File.separatorChar + "original.txt";
        
        // Listas para alojar los bloques de cada archivo
        LinkedList<LinkedList<String>> archivoF0 = new LinkedList<>();
        LinkedList<LinkedList<String>> archivoF1 = new LinkedList<>();
        LinkedList<LinkedList<String>> archivoF2 = new LinkedList<>();
        LinkedList<LinkedList<String>> archivoF3 = new LinkedList<>();
        
        Path rutaBase = Paths.get(".").normalize().toAbsolutePath();       
        Path directorioBase = Paths.get(rutaBase.toString(), "Archivos ordenamientos");
     
        File directorioPolifase = new File(Paths.get(directorioBase.toString(), "Polifase").toString());
        
        
        if(!directorioPolifase.exists()){
            directorioPolifase.mkdir();
        }
      
        // Defiendo el nombre de la carpeta según el tipo de ordenamiento 
        switch(ordenamiento){
            case 0-> {nombreCarpeta = "Iteraciones (Ord. por Nombre)"; break;}   
            case 1-> {nombreCarpeta = "Iteraciones (Ord. por Apellido)"; break;} 
            case 2-> {nombreCarpeta = "Iteraciones (Ord. por # Cuenta)"; break;}
        }
       
        // Inicializando las carpetas
        File directorioIteraciones = new File(Paths.get(directorioPolifase.toString(), nombreCarpeta).toString()); 
        Utilidades.borrarDirectorio(directorioIteraciones);
        directorioIteraciones.mkdir(); 
        
        File archivo = new File(directorioBase.toString() + archivoActual);
        sc = new Scanner(archivo);
       
        // En este ciclo do-while se realiza el algoritmos, sigue la secuencia:
        // Fase 1: Inicialización de archivos, ordenamiento y separación de bloques.
        // Fase 2: Unión de bloques
        
        do{
            
            int subAlternador = 0;
            
            // Asignación de nombre para cada archivo según la iteración actual
            File archivo0 = new File(Paths.get(directorioIteraciones.toString(), "Iteración " + (iteraciones + 1)).toString() +" - F0" + ".txt");
            File archivo1 = new File(Paths.get(directorioIteraciones.toString(), "Iteración " + (iteraciones + 1)).toString() +" - F1" + ".txt");
            File archivo2 = new File(Paths.get(directorioIteraciones.toString(), "Iteración " + (iteraciones + 1)).toString() +" - F2" + ".txt");
            File archivo3 = new File(Paths.get(directorioIteraciones.toString(), "Iteración " + (iteraciones + 1)).toString() +" - F3" + ".txt");
            
            // Si el archivo no existe, lo crea
            if(!archivo0.exists()) archivo0.createNewFile();
            if(!archivo1.exists()) archivo1.createNewFile();
            if(!archivo2.exists()) archivo2.createNewFile();
            if(!archivo3.exists()) archivo3.createNewFile();
          
            
            if(iteraciones == 0){
               // FASE 1
                System.out.println("FASE 1 --");
                LinkedList<String> auxiliar = new LinkedList<>(); 
                
                // Se obtienen los elementos del archivo de texto
                while (sc.hasNextLine()) {
                    auxiliar.add(sc.nextLine());
                }
                
                // Se separan y ordenan los bloques, posteriormente se asignan a su lista/archivo respectivo
                archivoF0.add((LinkedList<String>) auxiliar.clone());
                System.out.println("\n---------------------");
                System.out.println("Archivo 0 -");
                guardarBloquesEnArchivo(archivo0, archivoF0);
                archivoF0.clear();
                 
                 while (!auxiliar.isEmpty()) {
                    int contador = 0;
                    LinkedList<String> bloque = new LinkedList<>();   
                   
                   
                    while (!auxiliar.isEmpty() && contador < blockSize) {
                        bloque.add(auxiliar.poll());
                        contador++;
                    }
            
                    bloque = radixSortSimple(bloque, ordenamiento); 
                    
                    if (alternador == 0) {
                        archivoF1.add(bloque);
                        alternador = 1;
                    } else {
                        archivoF2.add(bloque);
                        alternador = 0;

                    }    
                }
                       
                // Guardando y mostrando los bloques pertenecientes a cada archivo
                System.out.println("\n---------------------");
                System.out.println("Archivo 1 -");
                guardarBloquesEnArchivo(archivo1, archivoF1);
                System.out.println("\n---------------------");
                System.out.println("Archivo 2 -");
                guardarBloquesEnArchivo(archivo2, archivoF2);
                System.out.println("\n---------------------");
                System.out.println("Archivo 3 -");
                guardarBloquesEnArchivo(archivo3, archivoF3);
                   
                alternador = 0;
                           
            }else{
                // FASE 2
                
                System.out.println("\n=========================================");
                System.out.println("** Iteración " + (iteraciones));

                // Se alterna entre los archivos F1, F2 y F0, F3 para actuar
                // como archivos origen y archivos destino.
                
                if(alternador == 0){
                   
 
                    while(!archivoF1.isEmpty() || !archivoF2.isEmpty()){
                        if(subAlternador == 0){
                            if(!archivoF1.isEmpty() && !archivoF2.isEmpty()){
                                // Se unen los bloques indicados
                                LinkedList<String> bloquesUnidos = unirBloques(archivoF1.remove(0), archivoF2.remove(0), ordenamiento);
                                // Se vuelve a ordenar el nuevo bloque 
                                bloquesUnidos = radixSortSimple(bloquesUnidos, ordenamiento);
                                // Agregando el bloque unido a la lista/archivo indivado
                                archivoF0.add(bloquesUnidos);
                            
                            }else{
                                // Si solo queda un bloque en alguna de las listas, se asigna directamente a la
                                // lista/archivo indicado.
                                
                                LinkedList<String> bloquesUnidos = unirBloques(archivoF1.remove(0), archivoF2.remove(0), ordenamiento);
                                bloquesUnidos = radixSortSimple(bloquesUnidos, ordenamiento);
                                archivoF0.add(bloquesUnidos);
                            }
                            subAlternador = 1;
                        }else{
                            if (!archivoF1.isEmpty() && !archivoF2.isEmpty()) {
                                LinkedList<String> bloquesUnidos = unirBloques(archivoF1.remove(0), archivoF2.remove(0), ordenamiento);
                                bloquesUnidos = radixSortSimple(bloquesUnidos, ordenamiento);
                                archivoF3.add(bloquesUnidos);

                            } else {
                                LinkedList<String> bloquesUnidos = unirBloques(archivoF1.remove(0), archivoF2.remove(0), ordenamiento);
                                bloquesUnidos = radixSortSimple(bloquesUnidos, ordenamiento);
                                archivoF3.add(bloquesUnidos);
                            }                           
                            subAlternador = 0;                           
                        }                  
                   }  
                    alternador = 1;
                    
                }else{
                    while (!archivoF0.isEmpty() || !archivoF3.isEmpty()) {
                        if (subAlternador == 0) {
                            if (!archivoF0.isEmpty() && !archivoF3.isEmpty()) {

                                archivoF1.add(unirBloques(archivoF0.remove(0), archivoF3.remove(0), ordenamiento));

                            } else {
                                archivoF1.add((archivoF0.isEmpty()) ? (LinkedList<String>) archivoF3.remove(0) : archivoF0.remove(0));
                            }
                            subAlternador = 1;
                        } else {
                            if (!archivoF1.isEmpty() && !archivoF2.isEmpty()) {

                                archivoF2.add(unirBloques(archivoF0.remove(0), archivoF3.remove(0), ordenamiento));

                            } else {
                                archivoF2.add((archivoF0.isEmpty()) ? (LinkedList<String>) archivoF3.remove(0) : archivoF0.remove(0));
                            }
                            subAlternador = 0;
                        }

                    }
                    alternador = 0;
                }
                
                
                // Se guardan las listas en los archivos y se imprimen los bloques que conforman cada una.
                System.out.println("\n---------------------");
                System.out.println("Archivo 0 -");
                guardarBloquesEnArchivo(archivo0, archivoF0);
                System.out.println("\n---------------------");
                System.out.println("Archivo 1 -");
                guardarBloquesEnArchivo(archivo1, archivoF1);
                System.out.println("\n---------------------");
                System.out.println("Archivo 2 -");
                guardarBloquesEnArchivo(archivo2, archivoF2);
                System.out.println("\n---------------------");
                System.out.println("Archivo 3 -");
                guardarBloquesEnArchivo(archivo3, archivoF3);
                
                sc.close();

            }

            iteraciones++;

          
            
        }while(archivoF0.size() != 1);
        
        // Guardando el archivo final ordenado
        
        System.out.println("Archivo Final -");
        File archivoFinal = new File(Paths.get(directorioPolifase.toString(), "Polifase Terminado -").toString() + nombreCarpeta.substring(21, nombreCarpeta.length() -1) + ".txt");
        guardarBloquesEnArchivo(archivoFinal, archivoF0);
        System.out.println("Podras encontrar el archivo en la siguiente dirección");
        System.out.println(directorioIteraciones.toPath().getParent().toString() + File.separatorChar + "Polifase Terminado -" + nombreCarpeta.substring(21, nombreCarpeta.length() - 1) + ".txt");
            
        System.out.println("\nPresione una letra para continuar...");
        System.in.read();
    }
    
    
     /**
     * Método para conocer la cadena de caracteres de mayor longitud de la categoria de datos a
     * ordenar. Es una modificación que trabaja con Listas de caracteres en lugar de rutas de acceso a archivos.
     *
     * @param lista Lista de cadenas de caracteres.
     * @param ordenamiento Tipo de ordenamiento a realizar (0.Nombre,1.Apellidos,2.Número de Cuenta)
     * @return El tamaño de la cadena más grande de la categoria de datos a ordenar
     */  
    
    public static int maxSizeSimple(LinkedList<String> lista, int ordenamiento) throws IOException{
       
        int n = 0;               
        String[] datos = new String[3];
        LinkedList<String> listaCopia = (LinkedList<String>)lista.clone();
        
        while(!listaCopia.isEmpty()){
         
            // Nombre, apellido, num    
            datos = listaCopia.poll().split(",");
            if(datos[ordenamiento].length() > n){
                n = datos[ordenamiento].length();
            }
        }

        return n;
    } 
    
    /**
     * 
     * Método para realizar el ordenamiento radix en listas de cadenas de caracteres, utiliza colas
     * 
     * @param lista Lista de cadenas caracteres
     * @param ordenamiento Tipo de ordenamiento (por nombre, apellido o número de cuenta)
     * @return Lista de cadenas de caracteres ordenadas
     * 
     */
    
    public static LinkedList<String> radixSortSimple(LinkedList<String> lista, int ordenamiento) throws FileNotFoundException, IOException{
    
        FileWriter writer = null;
        int recorrerIzq = 1;
        char caracter;
        String[] datos = new String[3];
        LinkedList<String> auxiliar = (LinkedList<String>) lista.clone();  
        int n = 0;
        String linea;

        //Arreglo de colas a-z + ' ' || 0-9 + ' '
        Queue[] caracteres = new Queue[28];
        for (int i = 0; i < 28; i++) {
            caracteres[i] = new LinkedList<String>();
        }
        
        /* ------------------------------------------------- */
        //Conocemos la cadena de mayor longitud para poder llevar a cabo el ordenamiento
        
        n = maxSizeSimple(auxiliar, ordenamiento);
            
        while (n - recorrerIzq > -1) {
          

            while (!auxiliar.isEmpty()) {
                //Por defecto es archivo auxiliar espacio pos 0
                int identificador = 0;
                // Nombre, apellido, num
                datos = auxiliar.poll().split(",");
                
                if (n - recorrerIzq < datos[ordenamiento].length()) {

                    // a = 97, z = 122,
                    // A = 65, Z = 90
                    // 0 = 48, 9 = 57
                    caracter = datos[ordenamiento].charAt(n - recorrerIzq);
                    
                    if (caracter > 64 && caracter < 91) {
                        //Mayuscula archivo pos 11 - 38
                        identificador = (int) caracter - 64;

                    } else if (caracter > 96 && caracter < 123) {
                        //Minuscula archivo pos 11 - 38
                        identificador = (int) caracter - 96;

                    } else if (caracter > 47 && caracter < 58) {
                        //Numero    archivo pos 1 - 10
                        identificador = (int) caracter - 47;

                    }
                }
                             
                caracteres[identificador].add(datos[0] + "," + datos[1] + ","+ datos[2]);
             
            }
                       
            recorrerIzq ++;
            
            for (int i = 0; i < caracteres.length; i++) {
                LinkedList<String> actual = (LinkedList<String>) caracteres[i];
                while(!actual.isEmpty()){
                    auxiliar.add(actual.poll());               
                }            
            }        
        }
        
        return auxiliar;
        
    }
    
    
    /**
     * Método que guarda los bloques de una lista en un archivo, también imprime los bloques que está guardando.
     * @param archivo Objeto de tipo File que contiene la referencia al archivo donde se desea guardar los bloques
     * @param lista Lista que contiene los bloques a guardar
     */
    
    public static void guardarBloquesEnArchivo(File archivo, LinkedList<LinkedList<String>> lista) throws IOException{
            
            LinkedList<LinkedList<String>> archivoEnMemoria = (LinkedList<LinkedList<String>>) lista.clone();
       
            FileWriter writer = new FileWriter(archivo);
  
            for (LinkedList<String> linkedList : archivoEnMemoria) {
                writer.write("\n#### BLOQUE ####");
                System.out.println("\n#### BLOQUE ####");
                for (String string : linkedList) {
                      System.out.println(string + "|");
                      writer.write("\n" + string + "|");
                }
                
                writer.write("\n\n");
            }
           
            
            
            writer.close();
    
    }
    
    
    /**
     * Método que une dos bloques en uno solo, determinando el orden que en el que deben ser insertados los elementos.
     * @param bloqueA Primera lista de cadenas de caracteres a unir
     * @param bloqueB Segunda lista de cadenras de caracteres a unir
     * @param ordenamiento Tipo de ordenamiento que se está usando (por nombre, apellido, númer de cuenta)
     * @return Lista de cadenas de caracteres que contiene el bloque unido.
     */
        
    public static LinkedList<String> unirBloques(LinkedList<String> bloqueA, LinkedList<String> bloqueB, int ordenamiento){
        
        LinkedList<String> bloqueUnido = new LinkedList<>();
        String seleccionado = "";
      
        
        while(!bloqueA.isEmpty() || !bloqueB.isEmpty()){
           
            if(!bloqueA.isEmpty() && !bloqueB.isEmpty()){
                 String[] elementoA = bloqueA.peek().split(",");
                 String[] elementoB = bloqueB.peek().split(",");
                 
                 char caracterA = elementoA[ordenamiento].charAt(0);
                 char caracterB = elementoB[ordenamiento].charAt(0);

                 seleccionado = ((int)caracterA > (int) caracterB) ? bloqueA.remove(0) :bloqueA.remove(0);
               
            }else{
                
                seleccionado = (bloqueA.isEmpty()) ? bloqueB.remove(0): bloqueA.remove(0);
            }
            
             bloqueUnido.add(seleccionado);
        }
       
        return bloqueUnido;
    }
    
    
}
