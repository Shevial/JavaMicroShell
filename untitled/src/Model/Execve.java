package Model;
import java.io.IOException;
/**
 * EXECVE ejecuta un programa en el contexto de proceso actual.
 * Reemplaza el proceso en curso con un nuevo proceso
 */

public class Execve {
    public static void execve(String comando, String[] args) {
        try{
            ProcessBuilder coche = new ProcessBuilder(); //creamos un proceso
            coche.command(pegamento(comando, args)); // juntar el comando y argumento
            coche.inheritIO();  //redirigir a la salida

            Process process = coche.start(); //proceso auxiliar para iniciar el proceso coche
            process.waitFor(); // espera a que el proceso acabe
            coche.environment().put("PATH", System.getenv("PATH")); // Asegurarse de que el sistema usa el PATH del entorno

        } catch (IOException | InterruptedException e){
            System.out.println("Error executing command: " + e.getMessage());
        }
    }

    private static String[] pegamento(String comando, String[] args) {
        String[] supercomando = new String[args.length + 1]; //longitud del argumento + siguiente argumento
        supercomando[0] = comando;
        System.arraycopy(args , 0, supercomando , 1, args.length); // copiando al supercomando TODO lo que vaya después de comando
        return supercomando;

    }
}