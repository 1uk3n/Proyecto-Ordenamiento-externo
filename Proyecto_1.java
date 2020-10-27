/*
 * Este es el primer proyecto de la materia de Estructuras de datos y algorítmos II.
 * Está enfocado a la implementación de tres algorítmos de ordenamiento externo: Polifase, Mezcla equilibrada y Radix.
 * 
 */
package proyecto_1;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.List;
/**
 *
 * @author Zuriel Zárate García.
 * @author Luis Rosales. 
 * @author Luis Axel Nuñez Quintana.
 */
public class Proyecto_1 {

     
    public static void main(String[] args) {
        Scanner dir = new Scanner(System.in);
        System.out.println("\nIntroduce la ruta del archivo que quieres ordenar: ");
        String ruta = dir.nextLine();
        Files file;
        Path path = Paths.get(ruta);
       // List<String> alumnos = file.readAllLines(path); //Falta arreglar esta parte.
        Menu m = new Menu();
        int opcion = 0;
        do{
                System.out.println("\n    ====== BIENVENIDO ====== \n");
                opcion = m.menu("Selecciona una opcion:  1)Polifase. \n\t\t\t2)Mezcla equilibrada. \n\t\t\t3)Radix. \n\t\t\t4)Salir.", 4);
                switch(opcion){

                        case 1:
                                System.out.println("\nHas elegido polifase :v");
                        break;

                        case 2:
                                System.out.println("\nHas elegido mezcla equilibrada :v");
                        break;

                        case 3:
                                System.out.println("\nHas elegido radix >:v");
                        break;

                        case 4:
                                System.out.println("\nGracias, hasta luego! :D");
                        break;
                }
        }while(opcion != 4);
    }
    
}
