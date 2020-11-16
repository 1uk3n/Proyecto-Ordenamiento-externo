/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author andy
 */
public class Polifase {
    
    static void polifase(int blockSize, int ordenamiento) throws FileNotFoundException, IOException{
        
        //Declaración de variables
        int alternador = 0; 
        int iteraciones = 0;
        String nombreCarpeta = null;
        Scanner sc = null;
        String archivoActual = File.separatorChar + "original.txt";
        
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
        
        switch(ordenamiento){
            case 0-> {nombreCarpeta = "Iteraciones (Ord. por Nombre)"; break;}   
            case 1-> {nombreCarpeta = "Iteraciones (Ord. por Apellido)"; break;} 
            case 2-> {nombreCarpeta = "Iteraciones (Ord. por # Cuenta)"; break;}
        }
       
        File directorioIteraciones = new File(Paths.get(directorioPolifase.toString(), nombreCarpeta).toString()); 
        Utilidades.borrarDirectorio(directorioIteraciones);
        directorioIteraciones.mkdir(); 
        
        File archivo = new File(directorioBase.toString() + archivoActual);
        sc = new Scanner(archivo);
       
       
        
         
        do{
            
            int subAlternador = 0;
            
            File archivo0 = new File(Paths.get(directorioIteraciones.toString(), "Iteración " + (iteraciones + 1)).toString() +" - F0" + ".txt");
            File archivo1 = new File(Paths.get(directorioIteraciones.toString(), "Iteración " + (iteraciones + 1)).toString() +" - F1" + ".txt");
            File archivo2 = new File(Paths.get(directorioIteraciones.toString(), "Iteración " + (iteraciones + 1)).toString() +" - F2" + ".txt");
            File archivo3 = new File(Paths.get(directorioIteraciones.toString(), "Iteración " + (iteraciones + 1)).toString() +" - F3" + ".txt");
            
            if(!archivo0.exists()) archivo0.createNewFile();
            if(!archivo1.exists()) archivo1.createNewFile();
            if(!archivo2.exists()) archivo2.createNewFile();
            if(!archivo3.exists()) archivo3.createNewFile();
          
            if(iteraciones == 0){
                // división inicial
                
                LinkedList<String> auxiliar = new LinkedList<>(); 
                while (sc.hasNextLine()) {
                    auxiliar.add(sc.nextLine());
                }

                archivoF0.add((LinkedList<String>) auxiliar.clone());
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
               
               System.out.println("A1: " + archivoF1.peek());
               guardarBloquesEnArchivo(archivo1, archivoF1);
               guardarBloquesEnArchivo(archivo2, archivoF2);
               guardarBloquesEnArchivo(archivo3, archivoF3);
               
                System.out.println("A2:  " + archivoF1.peek());
               
                alternador = 0;
                
                
            }else{
                
                System.out.println("Iteracion " + (iteraciones));
                System.out.println("Archivo 0: " + archivoF0.size());
                System.out.println("Archivo 1: " + archivoF1.size()); 
                System.out.println("Archivo 2: " + archivoF2.size());                 
                System.out.println("Archivo 3: " + archivoF3.size());
                
              
                if( alternador == 0){
                   
                    while(!archivoF1.isEmpty() || !archivoF2.isEmpty()){
                        if(subAlternador == 0){
                            if(!archivoF1.isEmpty() && !archivoF2.isEmpty()){
                                LinkedList<String> bloquesUnidos = unirBloques(archivoF1.remove(0), archivoF2.remove(0), ordenamiento);
                                bloquesUnidos = radixSortSimple(bloquesUnidos, ordenamiento);
                                archivoF0.add(bloquesUnidos);
                            
                            }else{
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
 
                guardarBloquesEnArchivo(archivo0, archivoF0);
                guardarBloquesEnArchivo(archivo1, archivoF1);
                guardarBloquesEnArchivo(archivo2, archivoF2);
                guardarBloquesEnArchivo(archivo3, archivoF3);

            }

            iteraciones++;
            System.out.println("\nPresione una letra para continuar...");
            System.in.read();
            
        }while(archivoF0.size() != 1);
        
        
            
        
    }
    
    private static int maxSizeSimple(LinkedList<String> lista, int ordenamiento) throws IOException{
       
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
    
    private static LinkedList<String> radixSortSimple(LinkedList<String> lista, int ordenamiento) throws FileNotFoundException, IOException{
    
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
    
    public static void guardarBloquesEnArchivo(File archivo, LinkedList<LinkedList<String>> lista) throws IOException{
            
            LinkedList<LinkedList<String>> archivoEnMemoria = (LinkedList<LinkedList<String>>) lista.clone();
       
            FileWriter writer = new FileWriter(archivo);
  
            for (LinkedList<String> linkedList : archivoEnMemoria) {
                for (String string : linkedList) {
                      writer.write(string + "|");
                }
            }
           
            writer.write("\n\n");
            
            writer.close();
    
    }
        
    private static LinkedList<String> unirBloques(LinkedList<String> bloqueA, LinkedList<String> bloqueB, int ordenamiento){
        
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
