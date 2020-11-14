package proyecto.ordenamiento.externo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class radix{

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
             
        FileWriter writer = null;
        int recorrerIzq = 1;
        char caracter;
        String[] datos = new String[3];
        int n = 0;
        String linea;
         
        /*      
        ##### Prueba de creación de archivo iteraciones y carpetas iniciales #####    
        */
        
        String nombreCarpeta = "";
        
        switch(ordenamiento){
            case 0-> {nombreCarpeta = "Archivos (Ord. por Nombre)"; break;}   
            case 1-> {nombreCarpeta = "Archivos (Ord. por Apellido)"; break;} 
            case 2-> {nombreCarpeta = "Archivos (Ord. por # Cuenta)"; break;}
        }
        
        //inicializarDir crea las carpetas iniciales y el archivo iteraciones.txt
        File iteraciones = Utilidades.inicializarDir(Paths.get("Archivos ordenamientos").toAbsolutePath(), "Radix", nombreCarpeta);
        
        //Creamos la carpeta de archivos auxiliares o vaciamos la anterior
        File aux = new File(iteraciones.toPath().getParent().toAbsolutePath().toString() + File.separatorChar + "Archivos auxiliares");
        if(!aux.exists()){
            aux.mkdir();
        }else{
            Utilidades.borrarDirectorio(aux);
        }
        
        //Creamos nuestros Archivos auxiliares (archivo a, archivo b, archivo 1, ...)
        File[] archivosAuxiliares = new File[37];
        char[] nombres = new char[37];
        nombres[0] = ' ';
        
        for(int i = 48; i < 58; i ++){
            nombres[i - 47] = (char)(i);
        }
        
        for(int i = 65; i < 91; i ++){
            nombres[i - 54] = (char)i;
        }
        
        for(int i = 0; i < 37; i++){
            archivosAuxiliares[i] = new File(aux.toString() + File.separatorChar + nombres[i] + ".txt"); 
        } 
        
        /*
        -------------------------------------------------
        */
        
        try{
            //Conocemos la cadena de mayor longitud para poder llevar a cabo el ordenamiento
            n = maxSize(iteraciones.toString(), ordenamiento);
        }catch(IOException e){
            System.out.println("No pude abrir el archivo original");
        }
        
        //Posicionamos el lector en iteraciones para comenzar el ordenamiento (iteraciones inicialmente es copia de original.txt)
        Scanner sc = new Scanner(iteraciones);
        
        while(n - recorrerIzq > -1){
            System.out.println("\n*Iteracion " + recorrerIzq);
            
            //Escribimos en cada archivo auxiliar que iteracion trabajamos
            for(int i = 0; i < 37; i ++){
                writer = new FileWriter(archivosAuxiliares[i], true);
                writer.write("\nIteración " + recorrerIzq + "\n");
                writer.close();
            }
            
            while(sc.hasNextLine()){
                //Por defecto es archivo auxiliar espacio pos 0
                int identificador = 0;
                // Nombre, apellido, num
                datos = sc.nextLine().split(",");
                
                if(n - recorrerIzq < datos[ordenamiento].length()){
                    
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
                //Escribimos la linea en el archivo correspondiente de acuerdo al caracter que analizamos
                writer = new FileWriter(archivosAuxiliares[identificador], true);
                writer.write(datos[0] + "," + datos[1] + ","+ datos[2] + "\n"); 
                writer.close();
            }    
            sc.close();
            
            //true = escribiremos al final del archivo de iteraciones
            writer = new FileWriter(iteraciones, true);
            //escribimos que iteracion trabajamos
            writer.write("\nIteración " + recorrerIzq + "\n");
            
            //Leeremos todos los archivos
            for(int i = 0; i < 37; i ++){
                sc = new Scanner(archivosAuxiliares[i]); 
                //Nos localizamos en los datos de esta iteración
                while (sc.hasNextLine() && !sc.nextLine().equals("Iteración " + recorrerIzq)) {
                }
                //Copiamos la información de esta iteración de este archivo Auxiliar
                while(sc.hasNextLine()){
                    linea = sc.nextLine();
                    System.out.println(linea);
                    writer.write(linea + "\n");
                }
                sc.close();
            }
            
            writer.close();
            
            sc = new Scanner(iteraciones);
            //Nos localizamos en los datos de esta iteración para la siguiente escritura en auxiliares o final
            while (sc.hasNextLine() && !sc.nextLine().equals("Iteración " + recorrerIzq)) {
            }
            //Ahora analizaremos el siguiente caracter
            recorrerIzq ++;
        }   

    writer = new FileWriter(new File(iteraciones.toPath().getParent().toString() + File.separatorChar + "Radix Terminado -" + nombreCarpeta.substring(8) + ".txt"));
    //Sc esta localizado en el inicio de los datos de la última iteración
    //copiamos los valores de la última iteración realizada (valores ordenados)
    while (sc.hasNextLine()){
        writer.write(sc.nextLine() + "\n");
    }
    
    sc.close();
    writer.close();
    
    System.out.println("\nPresione una letra para continuar...");
    System.in.read();
   
   }

}
