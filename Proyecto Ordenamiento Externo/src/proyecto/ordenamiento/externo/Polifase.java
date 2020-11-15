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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

/**
 *
 * @author andy
 */
public class Polifase {
    
    static void polifase(int blockSize, int ordenamiento) throws FileNotFoundException, IOException{
        
        //Declaración de variables
        
        String nombreCarpeta = null;
        Scanner sc = null;
        String archivoActual = File.separatorChar + "original.txt";
        int iteraciones = 1;
        int size = 0;
        
        LinkedList<LinkedList<String>> archivoF1 = new LinkedList<>();
        LinkedList<LinkedList<String>> archivoF12 = new LinkedList<>();
        LinkedList<LinkedList<String>> archivoF2 = new LinkedList<>();
        
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
        //Utilidades.borrarDirectorioRecursivamente(directorioIteraciones);
        directorioIteraciones.mkdir(); 
        
        File archivo = new File(directorioBase.toString() + archivoActual);
        
               
        do{
            
            File archivo1 = new File(Paths.get(directorioIteraciones.toString(), "Iteración " + iteraciones).toString() +" - F1" + ".txt");
            File archivo2 = new File(Paths.get(directorioIteraciones.toString(), "Iteración " + iteraciones).toString() +" - F2 "+ ".txt");
            
            if(!archivo1.exists()) archivo1.createNewFile();
            if(!archivo2.exists()) archivo2.createNewFile();
         
            sc = new Scanner(archivo);
            
            int alternador = 0;
             
            while(sc.hasNextLine()){
                LinkedList<String> bloque = new LinkedList<>();     
                
                int contador = 0;      
                
                while(sc.hasNextLine() && contador < blockSize){
                    bloque.add(sc.nextLine());
                    contador++;
                }
                
                if(alternador == 0){
                    archivoF1.add(bloque); 
                    alternador = 1;                    
                }else{  
                    archivoF2.add(bloque); 
                    alternador = 0;
                    
                } 
                
            }
            
            archivoF12.add(radixSortSimple(archivoF1.getFirst(), 1));
            
            
            for (int i = 0; i < 4; i++) {
                System.out.println(archivoF12.getLast().get(i));
            }
           /*
            COMO DESENCAPSULAR
                for (ArrayList a : archivoF1) {
                    for (Object b : a) {

                        for (int i = 0; i < 3; i++) {
                            System.out.println(((String[])b)[i]);
                        }
                    }
                }
            */ 
            
          
          
            
            // ORDENAR 
            
            
            // GUARDAR ARCHIVO DE LA ITERACION           
            guardarBloquesEnArchivo(archivo1, archivoF1);
            guardarBloquesEnArchivo(archivo2, archivoF2);
            
            iteraciones++;
            
            
        }while(iteraciones < 3);
        
        
            
        
    }
    
    public static int maxSizeSimple(LinkedList<String> lista, int ordenamiento) throws IOException{
       
        int n = 0;               
        String[] datos = new String[3];
        LinkedList<String> listaCopia = lista;
        
        while(!lista.isEmpty()){
            // Nombre, apellido, num    
            datos = listaCopia.getLast().split(",");

            if(datos[ordenamiento].length() > n){
                n = datos[ordenamiento].length();
            }
        }

        return n;
    } 
    
    public static LinkedList<String> radixSortSimple(LinkedList<String> lista, int ordenamiento) throws FileNotFoundException, IOException{
    
        FileWriter writer = null;
        int recorrerIzq = 1;
        char caracter;
        String[] datos = new String[3];
        LinkedList<String> resultados = new LinkedList<>();  
        int n = 0;
        String linea;

        
        //Arreglo de colas a-z + ' ' || 0-9 + ' '
        Queue[] caracteres = new Queue[28];
        for (int i = 0; i < 28; i++) {
            caracteres[i] = new LinkedList<String>();
        }
        
        /* ------------------------------------------------- */
        
        try {
            //Conocemos la cadena de mayor longitud para poder llevar a cabo el ordenamiento
            n = maxSizeSimple(lista, ordenamiento);
            
        } catch (IOException e) {
            System.out.println("No pude abrir el archivo original");
        }
        

        while (n - recorrerIzq > -1) {
            System.out.println("\n*Iteracion " + recorrerIzq);
            

            while (!lista.isEmpty()) {
                //Por defecto es archivo auxiliar espacio pos 0
                int identificador = 0;
                // Nombre, apellido, num
                datos = lista.getLast().split(",");

                if (n - recorrerIzq < datos[ordenamiento].length()) {

                    // a = 97, z = 122,
                    // A = 65, Z = 90
                    // 0 = 48, 9 = 57
                    caracter = datos[ordenamiento].charAt(n - recorrerIzq);

                    if (caracter > 64 && caracter < 91) {
                        //Mayuscula archivo pos 11 - 38
                        identificador = (int) caracter - 54;

                    } else if (caracter > 96 && caracter < 123) {
                        //Minuscula archivo pos 11 - 38
                        identificador = (int) caracter - 86;

                    } else if (caracter > 47 && caracter < 58) {
                        //Numero    archivo pos 1 - 10
                        identificador = (int) caracter - 47;

                    }
                }
                             
                caracteres[identificador].add(datos[0] + "," + datos[1] + ","+ datos[2]);
             
            }
            
            for(int i = 0; i < caracteres.length; i++) {
                resultados.add(caracteres[i].poll() + "\n");
            }
                
        }
        
        System.out.println("\nPresione una letra para continuar...");
        System.in.read();
        
        return resultados;
        
    }
    
    public static void guardarBloquesEnArchivo(File archivo, LinkedList<LinkedList<String>> archivoEnMemoria) throws IOException{
         
            FileWriter writer = new FileWriter(archivo);
           
            System.out.println(archivoEnMemoria.size());
            int size = archivoEnMemoria.size();
            for (int i = 0; i < size; i++) {
                
                LinkedList<String> bloqueOrdenado = archivoEnMemoria.getFirst();
                System.out.println("a" + i);
                for(int j = 0; j < bloqueOrdenado.size(); j++){
                    String elemento = bloqueOrdenado.get(j);
                    writer.write(elemento + "//");
                }
                writer.write("\n");
               
            }
            
            writer.close();
    
    }
        
    
}
