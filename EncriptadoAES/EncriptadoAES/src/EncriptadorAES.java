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
    public void encriptar(byte[] datos, String claveSecreta, String nombreFichero) throws NoSuchAlgorithmException, InvalidKeyException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {
        SecretKeySpec secretKey = this.crearClave(claveSecreta);

        // Se obtiene un cifrador AES
        Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");

        // Se inicializa para encriptacion
        aes.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] bytesEncriptados = aes.doFinal(datos);

        //Escritura en fichero
        PrintStream ps = new PrintStream(nombreFichero + ".dat");
        ps.write(bytesEncriptados);
        ps.close();
    }

    public void desencriptar(byte[] datosEncriptados, String claveSecreta, String nombre) throws IOException,
            NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException {
        SecretKeySpec secretKey = this.crearClave(claveSecreta);
        // Se iniciliza el cifrador para desencriptar, con la misma clave y se desencripta
        Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
        aes.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] datosDesencriptados = aes.doFinal(datosEncriptados);

        //Escritura en fichero para obtener el original
        PrintStream ps = new PrintStream(nombre);
        ps.write(datosDesencriptados);
        ps.close();
    }
}