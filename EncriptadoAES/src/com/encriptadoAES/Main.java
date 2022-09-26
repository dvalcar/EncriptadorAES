package com.encriptadoAES;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Programa que permite encriptar/desencriptar en formato AES un fichero cualquiera
 * @author DValcar
 * @email dvalcar@gmail.com
 */
public class Main {
    public static void main(String[] args) {
        try {
            Scanner scanner= new Scanner(System.in);
            System.out.println("Introduce la ruta de entrada del archivo a encriptar:");
            String origen = scanner.next();
            System.out.println(origen);

            System.out.println("Introduce la contraseña:");
            final String clave = scanner.next();
            System.out.println(clave);

            InputStream in = new FileInputStream(origen);
            byte[] datosIn = in.readAllBytes();

            //Encriptado
            EncriptadorAES aes = new EncriptadorAES();
            String crypt = aes.encriptar(datosIn, clave);
            in.close();

            //Desencriptado
            InputStream inEncriptado = new FileInputStream("cifrado.dat");
            byte[] datosInEncriptado = inEncriptado.readAllBytes();

            String decrypt = aes.desencriptar(datosInEncriptado, clave);
            inEncriptado.close();

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException |
                 NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
            Logger.getLogger(EncriptadorAES.class.getName()).log(Level.SEVERE, null, e);
        } catch (InvalidKeyException e) {
            System.out.println("Invalid key: " + e.getMessage());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
