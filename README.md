# EncriptadorAES
###### Este proyecto se ha creado para el Hackathon de OpenBootCamp.

 
Programa que permite encriptar cualquier fichero siguiendo el estándar de seguridad AES. Sólo abre un terminal, abre el directorio donde hayas copiado
el programa y ejecuta la clase Encripta tecleando:
java Encripta. A continuación se te preguntará si quieres encriptar o desencriptar y por último la ruta y la clave y ¡listo!


## ¿Que se necesita?

Tener java instalado y ¡nada más!


## ¿Como descargar la aplicación?

Descarga los ficheros .class del directorio out/production/EncriptadoAES en tu ordenador


## ¿Como ejecutar la aplicación?

Abre tu terminal y accede al directorio donde hayas descargado el programa y ejecutalo con:
```java Encripta```

## Encriptando

En el caso de que el fichero que quieres encriptar no esté en el directorio donde están las clases debes indicar el fichero con su ruta completa y extensión. Si está en el mismo directorio simplemente indica el nombre del fichero con su extensión.
El password que indiques debe tener suficiente seguridad puesto que de poco sirve un protocolo de encriptado como AES si la clave es obvia. Esta clave la tendrás que recordar para poder desencriptar el fichero.dat que se generará.
El fichero encriptado se creará en el directorio del programa de encriptado.

## Desencriptando

Introduce la clave y el nombre del fichero .dat que quieres desencriptar. En el caso de que el fichero a desencriptar no se encuentre en la misma ruta que el programa de encriptado deberás indicar la ruta completa. Asegúrate de incluir también la extensión .dat. Tras el proceso de desencriptado obtendrás el fichero original en el mismo directorio del programa de encriptado.

## Protocolo usado

Se usa el protocolo AES con cifrado por bloques (CBC) de 128 bits lo que ofrece la mejor seguridad. La clave usa protocolo SHA de 512 bits.
