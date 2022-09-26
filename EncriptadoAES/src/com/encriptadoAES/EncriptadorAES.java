package com.encriptadoAES;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Clase para encriptar un fichero en texto plano, sin formato, siguiendo protocolo AES
 *
 * @author DValcar
 * @email dvalcar@gmail.com
 * TODO: PODER PREGUNTAR EL TIPO DE CODIFICACION QUE SE VA A USAR COMO SHA U OTRA
 * TODO: PREGUNTAR CLAVE Y EXTENSION DEL ARCHIVO QUE SE QUIERE RECUPERAR
 * TODO:VER SI ALGUNA FORMA EN EL QUE SE GUARDE LA EXTENSION Y NOMBRE DEL ARCHIVO EN EL FICHERO ENCRIPTADO
 * TODO: SI ERRORES DE SOBREESCRITURA EN LOS FICHEROS
 * TODO: MANEJAR EXCEPCIONES CON MENSAJES PARA INTENTAR EVITARLAS
 * TODO: PREGUNTAR LA RUTA Y NOMBRE DEL FICHERO ENCRIPTADO
 * TODO: ENCRIPTAR TODOS LOS FICHEROS DE UNA CARPETA
 */

public class EncriptadorAES {
    private SecretKeySpec crearClave(String clave) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] claveEncriptacion = clave.getBytes("UTF-8");

        MessageDigest sha = MessageDigest.getInstance("SHA-1");

        claveEncriptacion = sha.digest(claveEncriptacion);

        return new SecretKeySpec(claveEncriptacion, 0, 16, "AES");
    }

    // Texto a encriptar
    public String encriptar(byte[] datos, String claveSecreta) throws NoSuchAlgorithmException, InvalidKeyException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {
        SecretKeySpec secretKey = this.crearClave(claveSecreta);

        // Se obtiene un cifrador AES
        Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");

        // Se inicializa para encriptacion y se encripta el texto, que debemos pasar como bytes.
        //TODO:INIT LANZA UN THROW
        aes.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] bytesEncriptados = aes.doFinal(datos);

        String encriptado = Base64.getEncoder().encodeToString(bytesEncriptados);

        //Escritura en fichero
        PrintStream ps = new PrintStream("cifrado.dat");
        ps.write(bytesEncriptados);
        ps.close();
        return encriptado;
    }

    public String desencriptar(byte[] datosEncriptados, String claveSecreta) throws IOException,
            NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException {
        SecretKeySpec secretKey = this.crearClave(claveSecreta);
        // Se iniciliza el cifrador para desencriptar, con la misma clave y se desencripta
        //TODO: preguntar clave
        Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
        aes.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] datosDesencriptados = aes.doFinal(datosEncriptados);
        String datos = new String(datosDesencriptados);

        //Escritura en fichero para obtener el original
        PrintStream ps = new PrintStream("fichero.mp3");
        ps.write(datosDesencriptados);
        ps.close();
        return datos;
    }
}