import Model.Builtins;
import Model.Execve;
import Model.Prompt;

import java.util.Scanner;

/*Clase principal para la microshell*/
public class Main {
    public static void main(String[] args) {
        Scanner lector = new Scanner(System.in);
        while(true) {
            System.out.print(Prompt.getPrompt());
            String entrada = lector.nextLine().trim(); //trim para no tener en cuenta los espacios
            if(entrada.isEmpty()) // si entra un Enter, que vuelva a preguntar
                continue;
            String[] token = entrada.split("\\s+"); //(\\s+) uno o más espacios en blanco. Cualquier cantidad de espacios separará los tokens

            String comando = token[0]; // guardamos en comando el comando
            String[] argumentos = new String[token.length - 1]; //guarda argumentos
            System.arraycopy(token, 1, argumentos, 0, token.length - 1);

            if(!Builtins.exec(comando, argumentos)) {
                Execve.execve(comando, argumentos);
            }
        }
    }
}


/*Nota para el futuro
Tipos de token (etiqueta)
* "ls" "-la" "|" ("cat" "-e")

            ()
            /\
           |  && ||
*/
/*
* CLASE MicroShell
FUNCIÓN main()
Crear Scanner para leer entrada del usuario
MIENTRAS (true) HACER
Imprimir el prompt con el directorio actual
Leer línea de comando
SI la línea está vacía, continuar

```
 tokens -> comando , argumento[]

        Tokenizar la línea en un array de argumentos
        SI el comando es un builtin
            Ejecutar con Builtins
        SINO
            Ejecutar con CommandExecutor
    FIN MIENTRAS
FIN FUNCIÓN

```

FIN CLASE

---

CLASE Builtins
FUNCIÓN ejecutar(Comando, Argumentos)
SEGÚN (Comando)
CASO "cd":
SI hay argumento
Cambiar directorio con System.setProperty("user.dir", argumento)
SINO
Imprimir "Falta argumento"
CASO "exit":
Imprimir "Saliendo..."
Salir del programa
CASO "echo":
Imprimir los argumentos unidos en una cadena
OTRO:
Devolver falso (no es un builtin)
FIN FUNCIÓN
FIN CLASE

---

CLASE CommandExecutor
FUNCIÓN ejecutar(Comando, Argumentos)
Crear ProcessBuilder con el comando y argumentos
Redirigir salida y error a la consola
INTENTAR
Iniciar el proceso y esperar su finalización
ATRAPAR excepción
Imprimir error
FIN FUNCIÓN
FIN CLASE

---

CLASE Utils
FUNCIÓN obtenerDirectorioActual()
Retornar System.getProperty("user.dir")
FIN FUNCIÓN
FIN CLASE
*
* */