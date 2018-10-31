package Crypto;

import java.util.Scanner;
import java.io.*;

public class Cryptographer {
    // Tresc pliku 'plain_filename', zaszyfrowana za pomoca 'algo', zapisz w 'cipher_filename'.
    static public void cryptfile(
            String plain_filename, String cipher_filename, Algorithm algo){
        String plaintext = null;

        try {
            plaintext = new Scanner(new File(plain_filename)).useDelimiter("\\A").next();
        }catch(FileNotFoundException exc){
            throw new CryptoException(exc.toString());
        }

        String ciphertext = algo.crypt(plaintext);

        try {
            FileWriter out = new FileWriter(cipher_filename);
            out.write(ciphertext);
            out.close();
        }catch(IOException exc){
            throw new CryptoException(exc.toString());
        }
    }

    // Tresc pliku 'cipher_filename', zdeszyfrowana za pomoca 'algo', zapisz w 'plain_filename'.
    static public void decryptfile(
            String cipher_filename, String plain_filename, Algorithm algo){
        String ciphertext = null;

        try {
            ciphertext = new Scanner(new File(cipher_filename)).useDelimiter("\\A").next();
        }catch(FileNotFoundException exc){
            throw new CryptoException(exc.toString());
        }

        String plaintext = algo.decrypt(ciphertext);

        try {
            FileWriter out = new FileWriter(plain_filename);
            out.write(plaintext);
            out.close();
        }catch(IOException exc){
            throw new CryptoException(exc.toString());
        }
    }

    public static class CryptoException extends RuntimeException {
        public CryptoException(String error_message){
            super(error_message);
        }
    }
}
