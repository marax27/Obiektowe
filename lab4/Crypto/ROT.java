package Crypto;

import java.lang.*;

// Ogolna klasa dla algorytmow ROT. W konstruktorze nalezy podac wartosc przesuniecia.
public class ROT implements Algorithm {
    ROT(int _offset){
        this.offset = _offset % 26;
    }

    @java.lang.Override
    public String crypt(String plain_word) {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < plain_word.length(); ++i){
            char c = plain_word.charAt(i);

            if(Character.isLetter(c)){
                int base = Character.isUpperCase(c) ? (int)'A' : (int)'a';
                int new_char_code = ((int)c - base + this.offset) % 26 + base;
                sb.append((char)new_char_code);
            }
            else
                sb.append(c);
        }
        return sb.toString();
    }

    @java.lang.Override
    public String decrypt(String cipher_word) {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < cipher_word.length(); ++i){
            char c = cipher_word.charAt(i);

            if(Character.isLetter(c)){
                int base = Character.isUpperCase(c) ? (int)'A' : (int)'a';
                int new_char_code = ((int)c - base + 26 - this.offset) % 26 + base;
                sb.append((char)new_char_code);
            }
            else
                sb.append(c);
        }
        return sb.toString();
    }

    int offset;
}
