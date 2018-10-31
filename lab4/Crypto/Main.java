package Crypto;

import java.lang.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        // * * *
        // UZYCIE: ./app IN_FILE OUT_FILE
        // * * *

        if (args.length != 2) {
            System.out.format("Wymagana liczba argumentów: 2 (podano %d).\n", args.length);
            return;
        }
        String input = args[0];
        String output = args[1];

        Scanner sc = new Scanner(System.in);

        // Wybierz tryb: encode/decode
        String option = "";
        while(!option.equals("encode") && !option.equals("decode")){
            System.out.print("encode/decode?: ");
            option = sc.nextLine();
            option = option.toLowerCase();
        }

        // Wybierz typ algorytmu.
        String chosen_algo = "";
        int rot_offset = 0;
        while(!chosen_algo.equals("rot") && !chosen_algo.equals("polibiusz")){
            System.out.print("Algorytm (ROT/Polibiusz)?: ");
            chosen_algo = sc.nextLine().toLowerCase();
            if(chosen_algo.equals("rot")){
                // Dopytaj o przesuniecie ROT.
                System.out.print("(ROT) Przesuniecie?: ");
                rot_offset = sc.nextInt();
            }
        }

        /*System.out.format("Files: %s --> %s\nGoal: %s with algorithm: %s (%d)\n",
                input, output, option, chosen_algo, rot_offset);*/

        Algorithm algorithm = chosen_algo.equals("rot") ? new ROT(rot_offset) : new Polibiusz();

        try {
            if(option.equals("encode"))
                Cryptographer.cryptfile(input, output, algorithm);
            else
                Cryptographer.decryptfile(input, output, algorithm);

            System.out.println("\nOperacja zakonczona powodzeniem.");
        }catch(Cryptographer.CryptoException cex){
            System.out.println(cex);
        }
    }
}
