package proyecto.ordenamiento.externo;

import java.util.Queue;
import java.util.LinkedList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Radix{

    public static int maxSize(String path, int ordenamiento) throws IOException{
       
        int n = 0;        
        Scanner sc = new Scanner(new File(path));         
        String[] datos = new String[3];

        while(sc.hasNextLine()){
            // Nombre, apellido, num    
            datos = sc.nextLine().split(",");

            if(datos[ordenamiento].length() > n){
                n = datos[ordenamiento].length();
            }
        }

        sc.close();      
        return n;
    }   

   public static void radixSort(int ordenamiento) throws IOException{
        
        //Declaración e inicialización de variables
        String nombreArchivo = File.separatorChar + "original.txt";
        String[] datos = new String[3];
        String nombreCarpeta = null;     
        FileWriter writer = null; 
        int recorrerIzq = 1;
        char caracter;
        int n = 0;
        
          
        /*      
        ##### Creación y carga de archivos y carpetas #####    
        */
        
        Path rutaBase = Paths.get(".").normalize().toAbsolutePath();       
        Path directorioBase = Paths.get(rutaBase.toString(), "Archivos ordenamientos");
       
        System.out.println(directorioBase.toString());
        switch(ordenamiento){
            case 0-> {nombreCarpeta = "Iteraciones (Ord. por Nombre)"; break;}   
            case 1-> {nombreCarpeta = "Iteraciones (Ord. por Apellido)"; break;} 
            case 2-> {nombreCarpeta = "Iteraciones (Ord. por # Cuenta)"; break;}
        }
        
        File directorioRadix = new File(Paths.get(directorioBase.toString(), "Radix").toString());
        
        System.out.println(directorioRadix.toString());
        
        if(!directorioRadix.exists()){
            directorioRadix.mkdir();
        }
        
        File directorioIteraciones = new File(Paths.get(directorioRadix.toString(), nombreCarpeta).toString());
        
        Utilidades.borrarDirectorioRecursivamente(directorioIteraciones);
        directorioIteraciones.mkdir(); 
        
        try {
           n = maxSize(directorioBase.toString() + nombreArchivo, ordenamiento);
       } catch (IOException e) {
           System.out.println("No pude abrir el archivo original");
       }
        
        /*
        -------------------------------------------------
        */
        
        /*Creando un arreglo de colas:
           * 28 elementos si se ordena respecto a letras (a-z + ' ')
           * 11 elementos si se ordena respecto a números (0-9 + ' ')
        */
        
        Queue[] ColaCaracteres = new Queue[(ordenamiento == 2) ? 11 : 28];
        for (int i = 0; i < ColaCaracteres.length; i++) {
            ColaCaracteres[i] = new LinkedList<String>();
        }
        
        
        while(n - recorrerIzq > -1){
            
            System.out.println("\n* Iteración " + recorrerIzq);
                        
            Scanner sc = new Scanner(new File(directorioBase.toString() + nombreArchivo));
                    
            while(sc.hasNextLine()){
                int cola = 0;
                
                // Obteniendo la siguiente linea con formato [Nombre(s), apellidos, # de Cuenta]
                datos = sc.nextLine().split(",");


                if(n - recorrerIzq < datos[ordenamiento].length()){
                    caracter = datos[ordenamiento].charAt(n - recorrerIzq); 
                          
                    // Determinando a que cola pertenece el elemento actual
                    // según el código ASCII que le corresponde al caracter analizado
                    
                    if(caracter > 64 && caracter < 91){
                        //Es mayúscula
                        cola = (int)caracter - 64;
                       
                    }else if(caracter > 96 && caracter < 123){
                        //Es minúscula
                        cola = (int)caracter - 96;
                        
                    }else if (caracter > 47 && caracter < 58){
                        //Es número
                        cola =(int)caracter - 47;
                        
                    }   
                }
                
                ColaCaracteres[cola].add(datos[0] + "," + datos[1] + ","+ datos[2]);
                    
            }    
              
           
            //Cambiando el directorio base
            directorioBase = directorioIteraciones.toPath();      
           
            
            if(n - recorrerIzq == 0){
                nombreArchivo =  File.separatorChar + "Radix Terminado  - " + nombreCarpeta.substring(11) +  ".txt"; 
                File archivoFinal = new File(directorioRadix.toString() + nombreArchivo);
               
                Utilidades.borrarDirectorioRecursivamente(archivoFinal);
                writer = new FileWriter(archivoFinal);
            }else{
                nombreArchivo =  File.separatorChar + "iteracion " + recorrerIzq + ".txt"; 
                writer = new FileWriter(new File(directorioIteraciones.toString() + nombreArchivo));
            }
              
            //Escribiendo el archivo de cada teración
            for(int i = 0; i < ColaCaracteres.length; i ++){
                while(!ColaCaracteres[i].isEmpty()){
                    System.out.println(ColaCaracteres[i].peek());
                    writer.write(ColaCaracteres[i].poll() + "\n");
                }
            }

            writer.close();  
            sc.close();
            recorrerIzq++;
        }   
    
    System.out.println("\nPresione una letra para continuar...");
    System.in.read();
   
   }



}