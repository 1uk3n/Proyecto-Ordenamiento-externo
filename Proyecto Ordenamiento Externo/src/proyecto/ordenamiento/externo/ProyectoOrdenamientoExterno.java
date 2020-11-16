
package proyecto.ordenamiento.externo;
import java.util.Scanner;
import java.io.IOException;
/**
 * Clase principal del proyecto, contiene el menú principal del programa.
 * 
 * @author Nuñez Quintana, Luis Axel
 * @author Zçarate García, Zuriel
 * @author Rosales López, Luis André
 */


public class ProyectoOrdenamientoExterno {
    /**
     * Método principal que contiene las instrucciones necesarias para mostrar el menú principal
     */
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
                    System.out.println("\n=*=*=*= Polifase =*=*=*=\n");
                    String[] subOpciones = {"Ordenamiento por nombre", "Ordenamiento por apellido", "Ordenamiento por # de cuenta", "Volver"};
                    int subselector = Menu.menu(subOpciones);

                    try {

                        switch (subselector) {
                            case 1: {
                                System.out.println("Puede encontrar el archivo con la lista ordenada en: \n");
                                Polifase.polifase(4, 0);
                                break;
                            }
                            case 2: {
                                System.out.println("Puede encontrar el archivo con la lista ordenada en: \n");
                                Polifase.polifase(4, 1);
                                break;
                            }
                            case 3: {
                                System.out.println("Puede encontrar el archivo con la lista ordenada en: \n");
                                Polifase.polifase(4, 2);
                                break;
                            }
                            case 4: {
                                selector = 0;
                                break;
                            }

                        }

                    } catch (IOException e) {
                        System.out.println("No pude abrir un archivo");
                    }
                    
                   
                    selector = 0;
                    break;
                }
                
                case 2->{
                    System.out.println("\n=*=*=*= Mezcla Equilibrada =*=*=*=\n");
                    String[] subOpciones = {"Ordenamiento por nombre", "Ordenamiento por apellido", "Ordenamiento por # de cuenta", "Volver"};
                    int subselector = Menu.menu(subOpciones);

                    try {

                        switch (subselector) {
                            case 1: {
                                System.out.println("Puede encontrar el archivo con la lista ordenada en: \n" + MezclaE.mezclaEquilibrada(0));
                                break;
                            }
                            case 2: {
                                System.out.println("Puede encontrar el archivo con la lista ordenada en: \n" + MezclaE.mezclaEquilibrada(1));
                                break;
                            }
                            case 3: {
                                System.out.println("Puede encontrar el archivo con la lista ordenada en: \n" + MezclaE.mezclaEquilibrada(2));
                                break;
                            }
                            case 4: {
                                selector = 0;
                                break;
                            }

                        }

                    } catch (IOException e) {
                        System.out.println("No pude abrir un archivo");
                    }
                    break;
                }
                case 3->{
                    System.out.println("\n=*=*=*= Radix Sort =*=*=*=\n");
                    String[] subOpciones = {"Ordenamiento por nombre", "Ordenamiento por apellido", "Ordenamiento por # de cuenta", "Volver"};
                    int subselector = Menu.menu(subOpciones);
                    
                    try{
                        
                        switch(subselector){
                            case 1 ->{Radix.radixSort(0); break;}
                            case 2 ->{Radix.radixSort(1); break;}
                            case 3 ->{Radix.radixSort(2); break;}
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
