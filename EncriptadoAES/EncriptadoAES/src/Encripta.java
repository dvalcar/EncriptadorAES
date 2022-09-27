
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Programa que permite encriptar/desencriptar en formato AES un fichero cualquiera
 * @author DValcar
 * @email dvalcar@gmail.com
 */
public class Encripta {
    public static void main(String[] args) {
        try {
            EncriptadorAES aes = new EncriptadorAES();
            int opcion;

            do {
                System.out.println("1 - Encripta");
                System.out.println("2 - Desencripta");
                System.out.println("3 - Salir");
                System.out.println("\nElige opcion 1, 2 o 3:");
                Scanner scanner= new Scanner(System.in);
                opcion= scanner.nextInt();

                switch (opcion) {
                    case 1:
                        System.out.println("Introduce la ruta de entrada del archivo a encriptar:");
                        String origen = scanner.next();
                        File file = new File(origen);
                        String nombre=file.getName();
                        System.out.println(nombre);
                        InputStream in = new FileInputStream(origen);
                        byte[] datosIn = in.readAllBytes();

                        //Encriptado
                        aes.encriptar(datosIn, preguntarPwd(), nombre);
                        in.close();
                        break;
                    case 2:
                        System.out.println("Introduce el nombre del fichero a desencriptar");
                        String destino=scanner.next();
                        File ficheroDestino = new File(destino);
                        String nombreDestino=ficheroDestino.getName();


                        //Desencriptado
                        InputStream inEncriptado = new FileInputStream(destino);
                        //Quitar extension .dat
                        nombreDestino=nombreDestino.substring(0,nombreDestino.length()-4);
                        System.out.println(nombreDestino);


                        byte[] datosInEncriptado = inEncriptado.readAllBytes();
                        aes.desencriptar(datosInEncriptado, preguntarPwd(), nombreDestino);
                        inEncriptado.close();
                        break;
                    case 3:
                        System.out.println("Adiós..");
                        break;
                    default:
                        System.out.println("Opción no válida, introduzca 1 para encriptar o 2 para desencriptar.");
                }
            }while (opcion!=3);

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException |
                 NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
            Logger.getLogger(EncriptadorAES.class.getName()).log(Level.SEVERE, null, e);
        } catch (InvalidKeyException e) {
            System.out.println("Invalid key: " + e.getMessage());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }catch (InputMismatchException e){
            System.out.println("Opción no valida");
        }

    }
    private static String preguntarPwd(){
        System.out.println("Introduce la contraseña:");
        Scanner scanner= new Scanner(System.in);
        final String clave = scanner.next();
        return clave;
    }
}
