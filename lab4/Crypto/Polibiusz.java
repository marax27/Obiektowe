package Crypto;

import java.lang.*;

// Implementacja szyfru Polibiusza.
public class Polibiusz implements Algorithm {
    @java.lang.Override
    public String crypt(String plain_word) {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < plain_word.length(); ++i){
            char c = plain_word.charAt(i);

            if(Character.isLetter(c)){
                IntPair pair = getLetterCoords(c);
                sb.append(pair.first);
                sb.append(pair.second);
            }
            else
                sb.append(c);
        }
        return sb.toString();
    }

    @java.lang.Override
    public String decrypt(String cipher_word) {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < cipher_word.length(); ++i) {
            char c1 = cipher_word.charAt(i);

            if(Character.isDigit(c1) && i+1 < cipher_word.length()){
                // Sprawdzenie drugiego znaku, czy jest cyfra.
                char c2 = cipher_word.charAt(++i);

                if(Character.isDigit(c2)){
                    // Potencjalnie "dwojka Polibiusza".
                    char x = getCharFromCoords(new IntPair(
                            Character.getNumericValue(c1),
                            Character.getNumericValue(c2)
                    ));
                    if(!Character.isLetter(x)){
                        sb.append(c1);
                        sb.append(c2);
                    }
                    else
                        sb.append(x);
                }
                else{
                    sb.append(c1);
                    sb.append(c2);
                }
            }
            else
                sb.append(c1);
        }
        return sb.toString();
    }

    /*static private char[][] table = {
            {'A', 'B', 'C', 'D', 'E'},
            {'F', 'G', 'H', 'I', 'K'},
            {'L', 'M', 'N', 'O', 'P'},
            {'Q', 'R', 'S', 'T', 'U'},
            {'V', 'W', 'X', 'Y', 'Z'}
    };*/

    // Zwraca wspolrzedne (wiersz, kolumna) litery w tabeli Polibiusza.
    IntPair getLetterCoords(char letter){
        char upper = Character.toUpperCase(letter);
        if(upper == 'J')
            upper = 'I';
        int pos = (upper < 'J') ? (upper - 'A') : (upper - 'A' - 1);

        return new IntPair(1 + (pos - pos%5)/5, 1 + pos % 5);
    }

    // Zwraca znak lezacy w tabeli Polibiusza na pozycji (wiersz, kolumna).
    char getCharFromCoords(IntPair pair){
        int n = 5*(pair.first - 1) + pair.second - 1;
        n += 'a';
        if(n > 'i')
            ++n;
        return (char)n;
    }

    //------------------------------
    // IntPair - prosty kontener na 2 inty.
    class IntPair{
        IntPair(int a, int b){
            first = a;
            second = b;
        }
        public int first, second;
    }
    //------------------------------
}
