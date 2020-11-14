/*
 * Ordena por mezcla equilibrada un archivo con alumnos con la forma: nombre(s),apellidos,n° de cuenta.
 * 
 */
package proyecto.ordenamiento.externo;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.Arrays;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;
/**
 * @author Zuriel Zárate García.
 */


public class MezclaE{

    public MezclaE(){
        
    }
    
    public LinkedList<String> bloque(LinkedList<String> alumnos, int ordenamiento, int i){
        
        
        FileWriter writer = null;
        String linea;
         
        /*      
        ##### Creación de archivo iteraciones y carpetas iniciales #####    
        */
        
        String nombreCarpeta = "";
        
        switch(ordenamiento){
            case 0: {nombreCarpeta = "Archivos (Ord. por Nombre)"; break;}   
            case 1: {nombreCarpeta = "Archivos (Ord. por Apellido)"; break;} 
            case 2: {nombreCarpeta = "Archivos (Ord. por # Cuenta)"; break;}
        }
        
        //inicializarDir crea las carpetas iniciales y el archivo iteraciones.txt
        File iteraciones = Utilidades.inicializarDir(Paths.get("Archivos ordenamientos").toAbsolutePath(), "Mezcla Equilibrada", nombreCarpeta);
        
        //Creamos la carpeta de archivos auxiliares o vaciamos la anterior
        File auxiliares = new File(iteraciones.toPath().getParent().toAbsolutePath().toString() + File.separatorChar + "Archivos auxiliares");
        if(!auxiliares.exists()){
            auxiliares.mkdir();
        }else{
            Utilidades.borrarDirectorio(auxiliares);
        }
        
        LinkedList<String> lista = new LinkedList<String>();
        String[] keys = new String[alumnos.size()];
        Double[] llaves = new Double[alumnos.size()];
        int aux, j = i + 1;

        i++;
        j++;

        if (ordenamiento != 2) {

            if (i == alumnos.size() - 1) {
                //System.out.println(i+" es igual a "+(lista.size()-1));
                lista.add(alumnos.get(i++));
            } else {

                for (int k = i; k < alumnos.size(); k++) {
                    String[] datos = alumnos.get(k).split(",");
                    keys[k] = datos[ordenamiento];
                    //System.out.println(keys[k]);
                }

                aux = 1;
                while (j < alumnos.size() && aux != -1) {
                    System.out.println(keys[i].compareToIgnoreCase(keys[j]));
                    if (keys[i].compareToIgnoreCase(keys[j]) <= 0) {
                        //System.out.println("Se añadio a: "+alumnos.get(i));
                        //System.out.println(keys[i]+" es menor que "+keys[j]);
                        lista.add(alumnos.get(i));
                        i++;
                        j++;
                    } else {
                        aux = -1;
                    }
                }

                if (i <= alumnos.size() - 1) {
                    //System.out.println("Falta agregar a "+(i)+" :v");
                    lista.add(alumnos.get(i));
                }
            }

        } else {

            if (i == alumnos.size() - 1) {
                //System.out.println(i+" es igual a "+(lista.size()-1));
                lista.add(alumnos.get(i++));
            } else {

                for (int k = i; k < alumnos.size(); k++) {
                    //String[] datos = alumnos.get(k).split(",");
                    llaves[k] = Double.parseDouble(alumnos.get(k).split(",")[ordenamiento]);
                    //System.out.println(keys[k]);
                }

                aux = 1;
                while (j < alumnos.size() && aux != -1) {
                    //System.out.println();
                    if (llaves[i] <= llaves[j]) {
                        //System.out.println("Se añadio a: "+alumnos.get(i));
                        //System.out.println(llaves[i]+" es menor que "+llaves[j]);
                        lista.add(alumnos.get(i));
                        i++;
                        j++;
                    } else {
                        aux = -1;
                    }
                }

                if (i <= alumnos.size() - 1) {
                    //System.out.println("Falta agregar a "+(i)+" :v");
                    lista.add(alumnos.get(i));
                }
            }

        }
        if (i == alumnos.size() - 1) {
            i++;
        }
        //Se agrega el último índice en i;
        //System.out.println("El indice retornado es: "+i);
        lista.add(String.valueOf(i));
        return lista;
    }

    public LinkedList<LinkedList<String>> primerFase(LinkedList<String> alumnos, int ordenamiento) {

        LinkedList<LinkedList<String>> listaA = new LinkedList<LinkedList<String>>();
        LinkedList<LinkedList<String>> listaB = new LinkedList<LinkedList<String>>();

        int i = -1;
        int count = 0;
        do {

            if (i < alumnos.size()) {
                listaA.add(bloque(alumnos, ordenamiento, i));
                i = Integer.parseInt(listaA.get(count).removeLast());
                System.out.println("BLOQUE A: ");
                imprimirLista(listaA);
            }

            if (i < alumnos.size()) {
                listaB.add(bloque(alumnos, ordenamiento, i));
                i = Integer.parseInt(listaB.get(count).removeLast());
                System.out.println("BLOQUE B: ");
                imprimirLista(listaB);
            }

            count++;

        } while (i < alumnos.size());

        return mezcla(listaA, listaB, ordenamiento);
    }

    public LinkedList<LinkedList<String>> mezcla(LinkedList<LinkedList<String>> listaA, LinkedList<LinkedList<String>> listaB, int ordenamiento) {

        LinkedList<LinkedList<String>> listaC = new LinkedList<LinkedList<String>>();

        int count = 0;
        boolean y;
        do {

            listaC.add(new LinkedList<String>());

            if (listaB.size() > count) {
                y = true;
            } else {
                y = false;
            }

            int pA = 0;
            int pB = 0;

            //Mezcla 
            if (y) {

                while (true) {
                    System.out.println("\n========== Mezclando...===========");
                    //System.out.println("pA: "+ pA);
                    //System.out.println("pB: "+ pB);

                    if (pA == listaA.get(count).size() || pB == listaB.get(count).size()) {
                        break;
                    }

                    String[] elemento1 = listaA.get(count).get(pA).split(",");
                    String[] elemento2 = listaB.get(count).get(pB).split(",");

                    int aux = elemento1[ordenamiento].compareToIgnoreCase(elemento2[ordenamiento]);
                    if (aux <= 0) {
                        System.out.println(elemento1[ordenamiento] + " es menor o igual que " + elemento2[ordenamiento]);
                        listaC.get(count).add(listaA.get(count).get(pA)); //elemento 1 es menor y va primero.
                        //imprimirLista(listaC);
                        pA++;
                    } else {
                        System.out.println(elemento1[ordenamiento] + " es mayor que " + elemento2[ordenamiento]);
                        listaC.get(count).add(listaB.get(count).get(pB)); //elemento 2 es menor y va primero.
                        //imprimirLista(listaC);
                        pB++;
                    }
                }

                while (pA < listaA.get(count).size()) {
                    listaC.get(count).add(listaA.get(count).get(pA));
                    pA++;
                }

                while (pB < listaB.get(count).size()) {
                    listaC.get(count).add(listaB.get(count).get(pB));
                    pB++;
                }
                System.out.println("\n______________Mezclada_____________ ");
                imprimirBloques(listaC.get(count));

            } else {

                while (pA < listaA.get(count).size()) {
                    listaC.get(count).add(listaA.get(count).get(pA));
                    pA++;
                }

            }

            //El resto de la lista se pone.
            count++;
            //System.out.println("hagamos el ciclo con: "+count);
            //System.out.println("sizeof A: "+listaA.size());

        } while (count < listaA.size());

        return listaC;

    }

    public LinkedList<LinkedList<String>> segundaFase(LinkedList<LinkedList<String>> listaC, int ordenamiento){

        LinkedList<LinkedList<String>> listaA = new LinkedList<LinkedList<String>>();
        LinkedList<LinkedList<String>> listaB = new LinkedList<LinkedList<String>>();
        while(listaC.size() != 1){

            for(int i=0; i<listaC.size(); i++){
                if(i%2 == 0){
                    listaA.add(listaC.get(i));
                }else{
                    listaB.add(listaC.get(i));
                }
            }
            listaC = mezcla(listaA,listaB, ordenamiento);

            listaA.clear();
            listaB.clear();
        }

        return listaC;

    }
    
    public LinkedList<LinkedList<String>> mezclaEquilibrada(LinkedList<String> alumnos, int ordenamiento, int i) {

        System.out.println("=======================INICIAL DESORDENADA >:v====================\n");
        imprimirBloques(alumnos);

        LinkedList<LinkedList<String>> listaC = primerFase(alumnos, ordenamiento);
        listaC = segundaFase(listaC, ordenamiento);
        System.out.println("=======================FINAL ORDENADA====================");

        return listaC;
    }

    public static void imprimirLista(LinkedList<LinkedList<String>> lista) {
        for (LinkedList<String> it : lista) {
            //System.out.println("BLOQUE: ");
            String[] elementos = new String[it.size()];
            for (int i = 0; i < it.size(); i++) {
                elementos[i] = it.get(i);
            }
            System.out.println(Arrays.asList(elementos) + "\n");
        }
    }
    
    public static void imprimirBloques(LinkedList<String> lista){
        String[] elementos = new String[lista.size()];
        for(int i=0; i<lista.size(); i++){
            elementos[i] = lista.get(i);
        }
        System.out.println(Arrays.asList(elementos)+"\n");
    }
 
}
