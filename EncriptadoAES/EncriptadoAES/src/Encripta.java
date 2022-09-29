
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Programa que permite encriptar/desencriptar en formato AES un fichero cualquiera
 *
 * @author DValcar
 * @email dvalcar@gmail.com
 */
public class Encripta {
    public static void main(String[] args) {
        String iv = "k-l3wPz=4Lx&0:fD"; // vector de inicialización
        int opcion;
        EncriptadorAES aes = new EncriptadorAES();
        Scanner scanner = new Scanner(System.in);
        do {
            opcion = menu();
            switch (opcion) {
                case 1 -> {
                    System.out.println("Introduce la ruta de entrada del archivo a encriptar:");
                    String origen = scanner.next();
                    File file = new File(origen);
                    String nombre = file.getName();
                    try {
                        InputStream in = new FileInputStream(origen);
                        byte[] datosIn = in.readAllBytes();
                        try {
                            //Encriptado
                            aes.encriptar(datosIn, preguntarPwd(), nombre, iv);
                            in.close();
                            System.out.println("Fichero encriptado.");
                        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException |
                                 UnsupportedOperationException | InvalidAlgorithmParameterException |
                                 IllegalStateException | IllegalBlockSizeException | BadPaddingException |
                                 InvalidKeyException e) {
                            Logger.getLogger(EncriptadorAES.class.getName()).log(Level.SEVERE, null, e);
                        } catch (FileNotFoundException e) {
                            System.out.println("Fallo al crear el fichero encriptado.");
                        }
                    } catch (FileNotFoundException e) {
                        System.out.println("No se encuentra el fichero.");
                    } catch (IOException e) {
                        System.out.println("Fallo al leer el fichero.");
                    }

                }
                case 2 -> {
                    try {
                        int extension = -1;
                        String destino = "";
                        String nombreDestino = "";
                        do {
                            System.out.println("Introduce el nombre del fichero a desencriptar");
                            destino = scanner.next();
                            File ficheroDestino = new File(destino);
                            nombreDestino = ficheroDestino.getName();

                            //Comprobar que el fichero proporcionado tiene extension .dat
                            extension = nombreDestino.indexOf(".dat");
                            if (extension == -1) {
                                System.out.println("Fichero incorrecto. Debe tener extensión .dat");
                            }
                        } while (extension == -1);

                        //Desencriptado
                        InputStream inEncriptado = new FileInputStream(destino);
                        //Quitar extension .dat
                        nombreDestino = nombreDestino.substring(0, nombreDestino.length() - 4);

                        byte[] datosInEncriptado = inEncriptado.readAllBytes();
                        aes.desencriptar(datosInEncriptado, preguntarPwd(), nombreDestino, iv);
                        inEncriptado.close();
                        System.out.println("Fichero desencriptado.");
                    } catch (InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException |
                             NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
                        System.out.println("Fichero no valido, no encriptado.");
                    } catch (FileNotFoundException | NullPointerException e) {
                        System.out.println("No se encuentra el fichero encriptado.");
                    } catch (IOException e) {
                        System.out.println("Fallo al leer el fichero encriptado.");
                    }

                }
                case 3 -> System.out.println("Adiós..");
                default -> System.out.println("Opción no válida, introduzca 1 para encriptar o 2 para desencriptar.");
            }
        } while (opcion != 3);
    }

    /**
     * Función para preguntar por la contraseña
     *
     * @return Devuelve la contraseña en formato String
     */
    private static String preguntarPwd() {
        System.out.println("Introduce la contraseña:");
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }

    /**
     * Función que devuelve la opción elegida por el usuario
     *
     * @return Se devuelve la opción elegida en un int
     */
    private static int menu() {

        int opcion = 0;
        System.out.println("1 - Encripta");
        System.out.println("2 - Desencripta");
        System.out.println("3 - Salir");
        System.out.println("\nElige opcion 1, 2 o 3:");
        do {
            try {
                Scanner scanner = new Scanner(System.in);
                opcion = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("¡Cuidado! Solo puedes insertar 1, 2 o 3.");
            }
        } while (opcion != 1 & opcion != 2 & opcion != 3);
        return opcion;
    }
}
