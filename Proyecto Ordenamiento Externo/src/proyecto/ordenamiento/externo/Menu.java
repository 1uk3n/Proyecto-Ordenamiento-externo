/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto.ordenamiento.externo;

import java.util.Scanner;

/**
 * 
 * @author Zuriel Zárate García.
 */

public class Menu{

    public Menu(){

    }

    /**
     * 
     * @param texto: Un string que indica las opciones para acceder a las funciones del programa. (incluyendo "salir").
     * @param n: El número de opciones que hay en tu menú (incluyendo "salir").
     * @return opcion: Un entero que almacena la opción elegida por el usuario.
     */
    public int menu (String texto, int n) {
            Scanner entrada = new Scanner(System.in);
            int opcion = 0;
            do {
           System.out.println(texto);
           opcion = entrada.nextInt();
           if (opcion < 1 || opcion > n){
                    System.out.println("Error: opci\u00f3n no valida...\n");
           }
            }while (opcion < 1 || opcion > n);
            return opcion;
    }

}