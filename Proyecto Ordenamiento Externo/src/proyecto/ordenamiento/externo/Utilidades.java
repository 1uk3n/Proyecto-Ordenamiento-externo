/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto.ordenamiento.externo;

import java.io.File;
import java.nio.file.Path;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 *
 * @author andy
 */
public class Utilidades {
       
    static void borrarDirectorio(File directorio) {
	   
        File[] contenido = directorio.listFiles();
	if (contenido != null) {
	    for (File archivo : contenido) {
                archivo.delete();
	    }
	}
	
    }
    
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
