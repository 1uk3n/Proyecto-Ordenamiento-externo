package proyecto.ordenamiento.externo;

import java.io.File;
import java.nio.file.Path;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;
/**
 * Clase Utilidades, no modela ningún tipo de dato abstracto en específico.
 * Es solo un compendio de utilerias varias usadas a lo largo del proyecto.
 * @author Nuñez Quintana, Luis Axel
 * @author Zarate Garia, Zuriel
 * @author Rosales Lopez, Luis André
 */

public class Utilidades {
    /**
     * Método para eliminar todos los archivos de un directorio
     *
     * @param directorio Directorio a vaciar
     */  
    static void borrarDirectorio(File directorio) {
	   
        File[] contenido = directorio.listFiles();
	if (contenido != null) {
	    for (File archivo : contenido) {
                archivo.delete();
	    }
	}
	
    }
    
    /**
     * Método para crear una copia de un archivo
     *
     * @param rutaBase Dirección absoluta del archivo original
     * @param rutaDestino Dirección absoluta en donde se almacenará la copia
     * @param nombreArchivoViejo Nombre y extensión del archivo original
     * @param nombreArchivoNuevo Nombre y extensión de la copia a crear
     * @return La copia creada
     */  
    static File copiarArchivo(Path rutaBase, Path rutaDestino, String nombreArchivoViejo, String nombreArchivoNuevo) throws IOException {
        
        //Archivo copia.txt
        File copia = new File(rutaDestino.toString() + File.separatorChar + nombreArchivoNuevo);
        //Lector de original.txt
        Scanner sc = new Scanner(new File(rutaBase.toString() + File.separatorChar + nombreArchivoViejo));
        //Escritor de copia.txt
        FileWriter writer = new FileWriter(copia);
        
        while(sc.hasNextLine()){
            writer.write(sc.nextLine() + "\n");
        }
        writer.close();
        
        return copia;
    }
    
    /**
     * Método para crear o revisar el estado de los directorios de ordenamiento y tipo de ordenamiento e inicialización de Iteraciones.txt
     *
     * @param rutaBase Dirección absoluta del archivo original
     * @param ordenamiento Ordenamiento a utilizar (Radix, Polifase, MezclaEquilibrada)
     * @param tipoOrd Tipo de ordenamiento a utilizar   (ord por Nombre, Apellido, numCuenta)
     * @return La copia creada localizada en el directorio ordenamiento/tipoDeOrd/
     */ 
    static File inicializarDir(Path rutaBase, String ordenamiento, String tipoDeOrd) {
	//Variable para crear el directorio Radix,polifase,mezcla o conocer el estado de dicho directorio
        File directorioAux = new File(Paths.get(rutaBase.toString(), ordenamiento).toString());

        //Si no existe la carpeta la creamos
        if (!directorioAux.exists()) {
            directorioAux.mkdir();
        }

        //Ahora lo utilizaremos para observar el estado de la carpeta de tipoDeOrd
        directorioAux = new File(Paths.get(rutaBase.toString(), ordenamiento, tipoDeOrd).toString());

        if (!directorioAux.exists()) {
            directorioAux.mkdir();
        } else {
            //Eliminamos los archivos pasados de la carpeta
            Utilidades.borrarDirectorio(directorioAux);
        }
        
        try{
            //Iteraciones.txt
            directorioAux = Utilidades.copiarArchivo(rutaBase, directorioAux.toPath(), "original.txt", "Iteraciones.txt");
        }catch(IOException e){
            System.out.println("No pude crear Iteraciones 0");
        }
            
        return directorioAux;
    }
}
