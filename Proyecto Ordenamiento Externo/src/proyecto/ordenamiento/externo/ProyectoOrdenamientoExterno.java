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

    public static void main(String[] args) throws IOException {      
        Scanner dir = new Scanner(System.in);
        int selector = 0;
        
        while(selector != 4){
             
            switch(selector){
                    
                case 0->{
                    System.out.println("\n ##### Proyecto - Ordenamiento Externo ##### \n");
                    String[] opciones = {"Polifase", "Mezcla equilibrada", "Radix", "Salir"};
                    selector = Menu.menu(opciones); 
                    break;                    
                }

                case 1->{
                    System.out.println("\nHas elegido polifase :v");
                    break;
                }
                
                case 2->{
                    System.out.println("\nHas elegido mezcla equilibrada :v");
                    break;
                }
                case 3->{
                    System.out.println("\n*** Radix Sort ***\n");
                    String[] subOpciones = {"Ordenamiento por nombre", "Ordenamiento por apellido", "Ordenamiento por # de cuenta", "Volver"};
                    int subselector = Menu.menu(subOpciones);

                    try{
                        switch(subselector){
                            case 1 ->{radix.radixSort(0); break;}
                            case 2 ->{radix.radixSort(1); break;}
                            case 3 ->{radix.radixSort(2); break;}
                            case 4 ->{selector = 0; break;}

                        }

                    }catch(IOException e){
                        System.out.println("No pude abrir un archivo");
                    }
                    
                    break;                  
                }
            }
        }
    }
}
