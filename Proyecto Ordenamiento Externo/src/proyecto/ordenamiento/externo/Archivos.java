/**
 * Contiene métodos para facilitar la lectura y escritura de archivos.
 */

package proyecto.ordenamiento.externo;

import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Archivos{

    /**
     * Lee un archivo.
     * @param path La ruta de donde se quiere leer el archivo (con el nombre del archivo y extensión añadidos).
     * @return Un String con toda la información del archivo.
     * @throws IOException 
     */
    
    public static String readFile(String path) throws IOException{
        String cadena = "";
        File archivo = new File(path);

        try{

          Scanner entrada = new Scanner(archivo);
          while(entrada.hasNext()){ 
                cadena = cadena.concat(entrada.nextLine()+"\n");
                }
                entrada.close();
                System.out.println("¡El archivo ha sido leído con éxito!\n");
        }
        catch(Exception e){
          System.out.println("No se pudo leer el archivo. Revisa si el archivo existe en la ruta indicada.");
        }
        return cadena;
    }

    /**
     * Escribe un archivo nuevo si no existe, y si existe, agrega información.
     * @param data Información que se quiere escribir.
     * @param path Ruta + nombre del archivo.extensión donde se quiere escribir la información.
     * @throws IOException 
     */
    
    public static void writeFile(String data, String path) throws IOException{

        File archivo = new File(path);
        BufferedWriter bw;

        try{
            if(archivo.exists()) {
                //System.out.println("Se ha añadido la información al archivo exitosamente ");
                bw = new BufferedWriter(new FileWriter(archivo.getAbsoluteFile(), true));
                bw.write(data+"\n");
            } else {
                System.out.println("¡El archivo se ha creado exitosamente!\n");
                bw = new BufferedWriter(new FileWriter(archivo.getAbsoluteFile(), true));
                bw.write(data+"\n");
            }
            bw.close();

        }catch(Exception e){
                System.out.println("\nAlgo salió mal!!!!\n");
        }
    }

}