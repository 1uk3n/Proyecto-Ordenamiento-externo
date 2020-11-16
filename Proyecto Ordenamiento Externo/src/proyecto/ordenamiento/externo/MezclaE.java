/*
 * Ordena por mezcla equilibrada un archivo con alumnos con la forma: nombre(s),apellidos,n° de cuenta.
 * 
 */
package proyecto.ordenamiento.externo;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.Arrays;
/**
 * @author Zuriel Zárate García.
 */


public class MezclaE{

    public MezclaE(){
        
    }
    
    public LinkedList<String> bloque(LinkedList<String> alumnos, int ordenamiento, int i){
        LinkedList<String> lista = new LinkedList<String>();
        String[] keys = new String[alumnos.size()];
        Double[] llaves = new Double[alumnos.size()];
        int aux,j=i+1;

        i++;
        j++;

        if(ordenamiento!=2){

            if(i == alumnos.size()-1){
                //System.out.println(i+" es igual a "+(lista.size()-1));
                lista.add(alumnos.get(i));
            }
            else{

                for(int k=i; k<alumnos.size(); k++){
                    String[] datos = alumnos.get(k).split(",");
                    keys[k] = datos[ordenamiento]; 
                    //System.out.println(keys[k]);
                }

                aux = 1;
                while(j<alumnos.size() && aux != -1){
                    System.out.println(keys[i].compareToIgnoreCase(keys[j]));
                    if(keys[i].compareToIgnoreCase(keys[j]) <= 0){
                        //System.out.println("Se añadio a: "+alumnos.get(i));
                        //System.out.println(keys[i]+" es menor que "+keys[j]);
                        lista.add(alumnos.get(i));
                        i++;
                        j++;
                    }
                    else{
                        aux=-1;
                    }
                }

                if(i<=alumnos.size()-1){
                    //System.out.println("Falta agregar a "+(i)+" :v");
                    lista.add(alumnos.get(i));
                }
            }

        }
        else{

            if(i == alumnos.size()-1){
                //System.out.println(i+" es igual a "+(lista.size()-1));
                lista.add(alumnos.get(i));
            }
            else{

                for(int k=i; k<alumnos.size(); k++){
                    String[] datos = alumnos.get(k).split(",");
                    llaves[k] = Double.parseDouble(datos[ordenamiento]); 
                    //System.out.println(keys[k]);
                }

                aux = 1;
                while(j<alumnos.size() && aux != -1){
                    //System.out.println();
                    if(llaves[i]<=llaves[j]){
                        //System.out.println("Se añadio a: "+alumnos.get(i));
                        //System.out.println(llaves[i]+" es menor que "+llaves[j]);
                        lista.add(alumnos.get(i));
                        i++;
                        j++;
                    }
                    else{
                        aux=-1;
                    }
                }

                if(i<=alumnos.size()-1){
                    //System.out.println("Falta agregar a "+(i)+" :v");
                    lista.add(alumnos.get(i));
                }
            }

        }
        //Se agrega el último índice en i;
        //System.out.println("El indice retornado es: "+i);
        lista.add(String.valueOf(i));
        return lista;
    }

    public LinkedList<String> intercalacion(LinkedList<String> alumnos, int ordenamiento){

        LinkedList<String> listaA = new LinkedList<String>();
        LinkedList<String> listaB = new LinkedList<String>();
        LinkedList<String> listaC = new LinkedList<String>();
        int i=-1;

        do{  

            if(i<alumnos.size()){   
                listaA.addAll(bloque(alumnos,ordenamiento,i));
                i = Integer.parseInt(listaA.remove(listaA.size()-1));
                //System.out.println("A: "+"");
                //imprimirLista(listaA);
            }

            if(i<alumnos.size()){
                listaB.addAll(bloque(alumnos,ordenamiento,i));
                i = Integer.parseInt(listaB.remove(listaB.size()-1));
                //System.out.println("B: "+"");
                //imprimirLista(listaB);
            }

        }while(i<alumnos.size());

        System.out.println("A: "+"");
        imprimirLista(listaA);
        System.out.println("B: "+"");
        imprimirLista(listaB);

        listaC = mezcla(listaA, listaB, ordenamiento);

        return listaC;
    }

    public static LinkedList<String> mezcla(LinkedList<String> listaA, LinkedList<String> listaB, int ordenamiento){
       
        LinkedList<String> listaC = new LinkedList<String>();

        int min=0,k=0;
        int x = listaA.size();
        int y = listaB.size();

        if(x==y || x<y){
            min = x;
        }
        else if(y<x){
            min = y;
        }

        //Mezcla 

        if(min>0){

            while(k<min){
                System.out.println("\n========== Mezclando...===========");


                String[] elemento1 = listaA.get(k).split(",");
                String[] elemento2 = listaB.get(k).split(",");

                int aux = elemento1[ordenamiento].compareToIgnoreCase(elemento2[ordenamiento]);
                if(aux <= 0){ 
                    System.out.println(elemento1[ordenamiento]+" es menor o igual que "+elemento2[ordenamiento]);
                    listaC.add(listaA.get(k)); //elemento 1 es menor y va primero.
                    //imprimirLista(listaC);
                    listaC.add(listaB.get(k)); // este después.
                    imprimirLista(listaC);
                }
                else{
                    System.out.println(elemento1[ordenamiento]+" es mayor que "+elemento2[ordenamiento]);
                    listaC.add(listaB.get(k)); //elemento 2 es menor y va primero.
                    //imprimirLista(listaC);
                    listaC.add(listaA.get(k)); // este después.
                    imprimirLista(listaC);
                }
                k++;
            }

        }

        //El resto de la lista se pone.
        //System.out.println("hagamos el ciclo con: "+k);
        while(k<listaA.size()){
            listaC.add(listaA.get(k));
            k++;
        }

        while(k<listaB.size()){
            listaC.add(listaB.get(k));
            k++;
        }
        System.out.println("\n______________Mezclada_____________ ");
        imprimirLista(listaC);

        return listaC;

    }
    
    
    
    
    public LinkedList<String> mezclaEquilibrada(LinkedList<String> alumnos, int ordenamiento, int i){
        LinkedList<String> lista = new LinkedList<String>();
        System.out.println("Lista a ordenar: ");
        lista = bloque(alumnos,ordenamiento,i);
        int it = Integer.parseInt(lista.remove(lista.size()-1));
        imprimirLista(alumnos);

        //System.out.println("Con tamaño: "+lista.size());

        if(it == alumnos.size()-1){
            //System.out.println(it+" = "+(alumnos.size()-1));
            System.out.println("La lista ya esta ordenada! :D");
            imprimirLista(lista);
            return lista;
        }
        else{
            lista = intercalacion(alumnos,ordenamiento);
            imprimirLista(alumnos);
            lista = mezclaEquilibrada(lista,ordenamiento,-1);
        }
        
        return lista;
    }

    public static void imprimirLista(LinkedList<String> lista){
        String[] elementos = new String[lista.size()];
        for(int i=0; i<lista.size(); i++){
            elementos[i] = lista.get(i);
        }
        System.out.println("lista = "+Arrays.asList(elementos)+"\n");
    }
 
}
