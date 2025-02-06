package Model;

import java.io.File;
import java.util.Map;

/**
 * BUILTINS: Funciones o características que vienen incluidas sin necesidad de importar bibliotecas
 */

public class Builtins {
    public static boolean exec(String comando, String[] argumento) { // diferenciamos comando de argumento Token[0] es el comando y token[1] es el argumento

        switch (comando) {
            case "cd":
                return CD(argumento);
            case "unset":
                return Unset(argumento);
            case "export":
                return Export(argumento);
            case "exit":
                return Exit(argumento);
            case "echo":
                return Echo(argumento);
            default:
                return false;
        }
    }
    private static boolean CD(String[] argumento) {
        String newPath;

        if(argumento.length == 0) { // si el argumento va solo
            newPath = "/"; // esto nos lleva a la raiz
        }
        else if (argumento[0].equals("..")){
            newPath = new File(System.getProperty("user.dir")).getParent(); //que directorio tiene antes
            if(newPath == null) // si es raiz, no cambia
                newPath = "/";
        }
        else
            newPath = argumento[0];
        /* microshell/home/> cd ismael/utad/
        *
        *  microshell/home/sara/
        *
        * */
        File newDirectory = new File(newPath);
        if(newDirectory.exists() && newDirectory.isDirectory()) {
            System.setProperty("user.dir", newDirectory.getAbsolutePath());
            return true;
        }
        else { // si no existe el directorio
            System.out.println("No such path or directory: " + newPath);
            return false;
        }
    }
    private static boolean Echo(String[] argumento) {
        if (argumento.length == 0) {
            System.out.println(); // echo sin args devuelve salto de linea
            return true;
        }
        System.out.println(String.join(" ", argumento)); // te devolverá los argumentos que van después de echo
        return true;
    }

    private static boolean Export(String[] argumento) { // crear variables de entorno
        if (argumento.length != 1 || !argumento[0].contains("=")) { // porque export solo admite 1 argumento
            System.out.println("Wrong use for export");
            return false;
        }

        // SARETUS="hola mundo"
        // SARETUS (variable)
        // "hola mundo" (valor)
        String[] troceado = argumento[0].split("="); //separar en el =
        String variable = troceado[0]; //guardamos la variable
        String valor = troceado[1]; // guardamos valor de la variable

        System.getenv().put(variable, valor);
        System.out.println("exported: " + variable + "=" + valor);//se puede quitar
        return true;
    }
/**
 * UNSET: Borra la variable de entorno indicada
 */

    private static boolean Unset(String[] argumento) {
        if (argumento.length != 1) { //solo 1 argumento
            System.out.println("Wrong use for unset");
            return false;
        }
        String key = argumento[0];
        Map<String, String> environment = System.getenv();  // copiará todo el environment y lo mete en la variable // reserva el espacio estimado de map
        if(environment.containsKey(key)) {
            environment.remove(key);
            System.out.println(key + " has been removed");
            return true;
        } else {
            System.out.println(key + "Was not found");
            return false;
        }
    }

    private static boolean Exit(String[] argumento) {
        int exitCode = 0; // si solo ponemos exit, el codigo es 0

        if(argumento.length == 1) { // si incluimos segundo argumento, se guardará en exitcode
            try{
                exitCode = Integer.parseInt(argumento[0]);
            } catch (NumberFormatException exception) {
                System.out.println("Numeric arg required");
                return false;
            }
        }
        else if (argumento.length > 1) {
            System.out.println("Wrong number of arguments");
            return false;
        }

        System.exit(exitCode);
        return true;
    }
}
