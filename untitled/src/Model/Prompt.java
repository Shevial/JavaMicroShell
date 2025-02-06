package Model;

import java.io.File;

/***
 * Texto de la terminal que indica que está esperando una entrada del usuario
 */

public class Prompt {
    public static String getPrompt() {
        return System.getProperty("user.name") + "@" +  //se muestra el usuario
                System.getenv("HOSTNAME") + ":" + //el nombre del equipo
                getCD() + " $ ";  //llama a la función que recoge la ruta actual
    }

    public static String getCD() {
        return new File(System.getProperty("user.dir")).getAbsolutePath(); // recoger el directorio en el que está el usuario
    }
}
