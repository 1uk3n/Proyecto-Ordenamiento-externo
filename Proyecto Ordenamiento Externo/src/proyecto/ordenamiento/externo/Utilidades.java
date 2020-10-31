/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto.ordenamiento.externo;

import java.io.File;

/**
 *
 * @author andy
 */
public class Utilidades {
       
    static boolean borrarDirectorio(File directorio) {
	   
        File[] contenido = directorio.listFiles();
	    if (contenido != null) {
	        for (File archivo : contenido) {
	            borrarDirectorio(archivo);
	        }
	    }
	    return false;
	}
   
}
