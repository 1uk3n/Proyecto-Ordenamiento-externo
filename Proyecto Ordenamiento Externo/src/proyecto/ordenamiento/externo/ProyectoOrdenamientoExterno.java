/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto.ordenamiento.externo;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.List;
import java.io.IOException;
/**
 *
 * @author Luis André Rosales López
 */
public class ProyectoOrdenamientoExterno {


    public static void main(String[] args) {
       
        
        Scanner dir = new Scanner(System.in);
        
        /*
        System.out.println("\nIntroduce la ruta del archivo que quieres ordenar: ");
        String ruta = dir.nextLine();
        Files file;
        Path path = Paths.get(ruta);
       // List<String> alumnos = file.readAllLines(path); //Falta arreglar esta parte.
        */
        
        int selector = 0;
        
        while(selector != 4){
            
             
            System.out.println("\n ##### Proyecto - Ordenamiento Externo ##### \n");       
            String[] opciones = {"Polifase", "Mezcla equilibrada", "Radix", "Salir"};
            
            selector = Menu.menu(opciones);

            System.out.println(selector);

            switch(selector){

                    case 1:
                            System.out.println("\nHas elegido polifase :v");
                    break;

                    case 2:
                            System.out.println("\nHas elegido mezcla equilibrada :v");
                    break;

                    case 3:
                            
                            System.out.println("*** Radix Sort ***");
                            String[] subOpciones = {"Ordenamiento por nombre", "Ordenamiento por apellido", "Ordenamiento por # de cuenta"};
                            int subselector = Menu.menu(opciones);
                           
                           
                            System.out.println("\nHas elegido radix >:v");
                            try{
                                radix.radixSort(0);
                                radix.radixSort(1);
                                radix.radixSort(2);
                            }catch(IOException e){
                                System.out.println("No pude abrir un archivo");
                            }
                    break;

                    case 4:
                         System.out.println("Has elegido el camino de la muerte >:v");
                         break;



            }
        }
    }
}
