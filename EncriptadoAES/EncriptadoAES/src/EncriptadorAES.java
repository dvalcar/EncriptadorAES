import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
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
 * TODO: MANEJAR EXCEPCIONES CON MENSAJES PARA INTENTAR EVITARLAS
 * TODO: PREGUNTAR LA RUTA Y NOMBRE DEL FICHERO ENCRIPTADO
 * TODO: ENCRIPTAR TODOS LOS FICHEROS DE UNA CARPETA
 */

public class EncriptadorAES {

    /**
     * Función para codificar la clave proporcionada por el usuario
     * @param clave de clase String que es la proporcionada por el usuario
     * @return Devuelve la clave codificada en formato SecretKeySpec
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    private SecretKeySpec crearClave(String clave) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] claveEncriptacion = clave.getBytes("UTF-8");

        MessageDigest sha = MessageDigest.getInstance("SHA-1");

        claveEncriptacion = sha.digest(claveEncriptacion);

        return new SecretKeySpec(claveEncriptacion, 0, 16, "AES");
    }

    /**
     * Función que realiza la encriptación creando un archivo con el mismo nombre del fichero original
     * @param datos array de bytes con la información del fichero que se quiere encriptar
     * @param claveSecreta proporcionada por el usuario en formato String
     * @param nombreFichero que se va a encriptar en formato String
     * @param iv Vector de inicialización en formato String
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws IOException
     * @throws InvalidAlgorithmParameterException
     */
    public void encriptar(byte[] datos, String claveSecreta, String nombreFichero, String iv) throws NoSuchAlgorithmException, InvalidKeyException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException, InvalidAlgorithmParameterException {
        SecretKeySpec secretKey = this.crearClave(claveSecreta);

        // Se obtiene un cifrador AES
        Cipher aes = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
        // Se inicializa para encriptacion
        aes.init(Cipher.ENCRYPT_MODE, secretKey,ivParameterSpec);
        byte[] bytesEncriptados = aes.doFinal(datos);

        //Escritura en fichero
        PrintStream ps = new PrintStream(nombreFichero + ".dat");
        ps.write(bytesEncriptados);
        ps.close();
    }

    /**
     * Función para desencriptar que crea un fichero con el nombre del fichero original que se encripto
     * @param datosEncriptados array de bytes que contiene la información del fichero encriptado
     * @param claveSecreta proporcionada por el usuario en formato String
     * @param nombre del fichero que se va a crear cuando ya esté desencriptado
     * @param iv Vector de inicialización en formato String
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidAlgorithmParameterException
     */
    public void desencriptar(byte[] datosEncriptados, String claveSecreta, String nombre, String iv) throws IOException,
            NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException, InvalidAlgorithmParameterException {
        SecretKeySpec secretKey = this.crearClave(claveSecreta);
        // Se iniciliza el cifrador para desencriptar, con la misma clave y se desencripta
        Cipher aes = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
        aes.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
        byte[] datosDesencriptados = aes.doFinal(datosEncriptados);

        //Escritura en fichero para obtener el original
        PrintStream ps = new PrintStream(nombre);
        ps.write(datosDesencriptados);
        ps.close();
    }
}