package proyecto.ordenamiento.externo;

import java.util.Queue;
import java.util.LinkedList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class radix{

    public static int maxSize(String path, int ordenamiento) throws IOException{
        int n = 0;
        Scanner sc = new Scanner(new File(path)); 
        String rawDatos;
        String[] datos = new String[3];

        while(sc.hasNextLine()){
            // Nombre, apellido, num
            rawDatos = sc.nextLine();
            datos = rawDatos.split(",");

            if(datos[ordenamiento].length() > n){
                n = datos[ordenamiento].length();
            }
        }

        sc.close();
        
        return n;
    }

	public static void radixSort(String path, int ordenamiento) throws IOException{
        System.out.println(path);
        //Arreglo de colas a-z + ' ' || 0-9 + ' '
        Queue[] letras = new Queue[28];
        for(int i = 0; i < 28; i++){
            letras[i] = new LinkedList<String>(); 
        }
        
        
        int recorrerIzq = 1;
        char letra;

        String rawDatos;
        String[] datos = new String[3];

        String name = (char)92 + "original.txt";
        int n = 0;

        try{
            n = maxSize(path + (char)92 +"original.txt", ordenamiento);
        }catch(IOException e){
            System.out.println("No pude abrir el archivo original");
        }

        while(n - recorrerIzq > -1){
            
            Scanner sc = new Scanner(new File(path + name));
            
            while(sc.hasNextLine()){
                
                // Nombre, apellido, num
                rawDatos = sc.nextLine();
                datos = rawDatos.split(",");

                System.out.println("Dato a analizar: " + datos[ordenamiento]);
                System.out.println("Letra actual: " + (n - recorrerIzq));

                if(n - recorrerIzq < datos[ordenamiento].length()){
                    
                    // a = 97, z = 122,
                    // A = 65, Z = 90
                    // 0 = 48, 9 = 57
                    letra = datos[ordenamiento].charAt(n - recorrerIzq); 
                    System.out.println("Ya se puede analizar esta letra y es: " + letra);
                    
                    if(letra > 64 && letra < 91){
                        //Mayuscula
                        letras[(int)letra - 64].add(rawDatos);
                    
                    }else if(letra > 96 && letra < 123){
                        //Minuscula
                        letras[(int)letra - 96].add(rawDatos);
                    }else if (letra > 47 && letra < 58){
                        //Numero
                        letras[(int) letra - 47].add(rawDatos);
                    }else{
                        // ' '
                        System.out.println("Se toma como espacio");
                        letras[0].add(rawDatos);
                    }
                }else{
                    // ' '
                    System.out.println("Se toma como espacio");
                    letras[0].add(rawDatos);
                }
            }    

            name = (char)92 +"iteracion " + recorrerIzq + ".txt"; 
            FileWriter writer = new FileWriter(path + name);

            for(int i = 0; i < 28; i ++){

                while(letras[i].isEmpty() != true){
                    //prints (estado despues de iterar)
                    writer.write(letras[i].poll() + "\n");
                }
            }

            writer.close();  
            sc.close();
            recorrerIzq ++;
        }   
    }
}