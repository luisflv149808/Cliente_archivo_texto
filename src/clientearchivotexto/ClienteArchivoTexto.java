package clientearchivotexto;

import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClienteArchivoTexto {

    public static void main(String[] args) {
        Socket socket = null;

        PrintWriter escritor = null;
        BufferedReader lector = null;
        String datos;
        String datosEntrada = "";
        String line = "";
        Scanner scanner = new Scanner(System.in);
        PrintWriter pw = null;

        if (args.length == 3) {

            if (Metodos.ValidarIp(args[0])) {
                if (Metodos.Numerico(args[1])) {
                    try {
                        socket = new Socket(args[0], Integer.parseInt(args[1]));
                        socket.setSoTimeout(5000);
                    } catch (IOException e) {
                        System.out.println("Error al crear el socket " + e);
                        System.exit(1);
                    }

                    try {
                        escritor = new PrintWriter(socket.getOutputStream(), true);
                    } catch (IOException e) {
                        System.out.println("Error al crear el escritor " + e);
                        System.exit(2);
                    }

                    try {
                        lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    } catch (IOException e) {
                        System.out.println("Error al crear el lector " + e);
                        System.exit(3);
                    }

                    escritor.println(args[2]);

                    try {
                        line = lector.readLine();
                    } catch (IOException e) {
                        System.out.println("Error al leer una linea " + e);
                        System.exit(4);
                    }

                    File file = new File(line);
                    try {
                        pw = new PrintWriter(file);
                    } catch (FileNotFoundException e) {
                        System.out.println("Error al encontrar el archivo " + e);
                        System.exit(5);
                    }

                    while (datosEntrada != null) {
                        try {
                            datosEntrada = lector.readLine();
                        } catch (IOException e) {
                            System.out.println("Error al leer los datos de entrada " + e);
                            System.exit(6);
                        }

                       
                        if (datosEntrada != null) {

                            pw.println(datosEntrada);

                        }
                    }
                    pw.close();
                    System.out.println("Envio Exitoso");
                } else {
                    System.out.println("!!Error!!. El puerto debe ser númerico (Argumento 2)");
                }
            } else {

                System.out.println("Dirección IP Inválida");
                System.exit(0);

            }

        } else {
            System.out.println("!!Error!!. Ingresa todos los argumentos (Dirección IP, Puerto, Ruta)");
        }

    }

}
