package proyecto.ordenamiento.externo;
import java.util.Queue;
import java.util.LinkedList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
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
        
        String nombreArchivo = "/original.txt";
       
        //Arreglo de colas a-z + ' ' || 0-9 + ' '
        Queue[] caracteres = new Queue[28];
        for(int i = 0; i < 28; i++){
            caracteres[i] = new LinkedList<String>(); 
        }
         
        /*      
        ##### Prueba de creación de archivos #####    
        */
        
        Path rutaBase = Paths.get(".").normalize().toAbsolutePath();
        
        Path directorioBase = Paths.get(rutaBase.toString(), "Archivos ordenamientos");
       
        String nombreCarpeta = null;
        
        switch(ordenamiento){
            case 0-> {nombreCarpeta = "Iteraciones (Ord. por Nombre)"; break;}   
            case 1-> {nombreCarpeta = "Iteraciones (Ord. por Apellido)"; break;} 
            case 2-> {nombreCarpeta = "Iteraciones (Ord. por # Cuenta)"; break;}
        }
        
        File directorioRadix = new File(Paths.get(directorioBase.toString(), "Radix").toString());
        
        if(!directorioRadix.exists()){
            directorioRadix.mkdir();
        }
        
        File directorioIteraciones = new File(Paths.get(directorioRadix.toString(), nombreCarpeta).toString());
        Utilidades.borrarDirectorio(directorioIteraciones);
        directorioIteraciones.mkdir(); 
        
        /*
        -------------------------------------------------
        */
        

        try{
            n = maxSize(directorioBase.toString() + nombreArchivo, ordenamiento);
        }catch(IOException e){
            System.out.println("No pude abrir el archivo original");
        }
        
        while(n - recorrerIzq > -1){
            
            System.out.println("* Iteración " + recorrerIzq);
                        
            Scanner sc = new Scanner(new File(directorioBase.toString() + nombreArchivo));
                    
            while(sc.hasNextLine()){
                int cola = 0;
                // Nombre, apellido, num
                datos = sc.nextLine().split(",");


                if(n - recorrerIzq < datos[ordenamiento].length()){
                    
                    // a = 97, z = 122,
                    // A = 65, Z = 90
                    // 0 = 48, 9 = 57
                    caracter = datos[ordenamiento].charAt(n - recorrerIzq); 
                   
                                
                    // Determinando a que cola pertenece el elemento actual 
                    if(caracter > 64 && caracter < 91){
                        //Mayuscula
                        cola = (int)caracter - 64;
                       
                    }else if(caracter > 96 && caracter < 123){
                        //Minuscula
                        cola = (int)caracter - 96;
                        
                    }else if (caracter > 47 && caracter < 58){
                        //Numero
                        cola =(int)caracter - 47;
                        
                    }   
                }
                
                caracteres[cola].add(datos[0] + "," + datos[1] + ","+ datos[2]);
                    
            }    
              
           
            
            directorioBase = directorioIteraciones.toPath();
            
            
            nombreArchivo = "/iteracion " + recorrerIzq + ".txt"; 
            
       
            writer = new FileWriter(new File(directorioIteraciones.toString() + nombreArchivo));
            
            for(int i = 0; i < 28; i ++){

                while(caracteres[i].isEmpty() != true){
                    //prints (estado despues de iterar)
                    System.out.println(caracteres[i].peek());
                    writer.write(caracteres[i].poll() + "\n");
                }
            }

            writer.close();  
            sc.close();
            recorrerIzq ++;
        }   
    }



}