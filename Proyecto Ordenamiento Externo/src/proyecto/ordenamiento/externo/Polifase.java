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
        
        
        List<ArrayList> archivoF1 = new LinkedList<>();
        List<ArrayList> archivoF2 = new LinkedList<>();
        
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
        
        do{
            
            File archivo1 = new File(Paths.get(directorioIteraciones.toString(), "Iteración " + iteraciones).toString() +" - F1" + ".txt");
            File archivo2 = new File(Paths.get(directorioIteraciones.toString(), "Iteración " + iteraciones).toString() +" - F2 "+ ".txt");
            
            if(!archivo1.exists()) archivo1.createNewFile();
            if(!archivo2.exists()) archivo2.createNewFile();
         
            sc = new Scanner(archivo);
            
            int alternador = 0;
             
            while(sc.hasNextLine()){
                ArrayList<String> bloque = new ArrayList<>();      
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
    
    public static void guardarBloquesEnArchivo(File archivo, List<ArrayList> archivoEnMemoria) throws IOException{
         
            FileWriter writer = new FileWriter(archivo);
           
            System.out.println(archivoEnMemoria.size());
            int size = archivoEnMemoria.size();
            for (int i = 0; i < size; i++) {
                
                ArrayList<String> bloqueOrdenado = archivoEnMemoria.remove(0);
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
