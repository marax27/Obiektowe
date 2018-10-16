package Figury;

import java.io.*;
import java.util.*;

public class Test {

    public static void main(String[] args) {
        // Inicjalizacja listy figur.
        figury = new LinkedList<Prostokat>();

        Scanner sc = new Scanner(System.in);
        char choice;
        boolean is_open = true;

        // Petla glowna.
        do{
            printMenu();
            choice = sc.next().charAt(0);

            switch(choice){
                case '1':
                    if(!dodajProstokat())
                        System.out.println("[ BŁĄD ] Nie udało się dodać prostokąta.");
                    break;
                case '2':
                    wyswietlProstokaty();
                    break;
                case '3':
                    double sum = 0.0;
                    for(Prostokat i : figury)
                        sum += i.area();
                    System.out.printf("Suma pól: %s\n", sum);
                    break;
                case 'Q':
                case 'q':
                    is_open = false;
                    break;
                default:
                    System.out.println("[ BŁĄD ] Niepoprawny znak.");
                    break;
            }
        }while(is_open);
    }

    private static boolean dodajProstokat(){
        Scanner sc = new Scanner(System.in);
        double a, b;
        Prostokat p;

        while(true){
            System.out.print("Podaj a: ");
            a = sc.nextDouble();
            System.out.print("Podaj b: ");
            b = sc.nextDouble();

            try{
                p = new Prostokat(a, b);
                break;
            }
            catch(IllegalArgumentException exc){
                System.out.println("[ BŁĄD ] Nieprawidłowe dane. Spróbuj ponownie.");
            }
        }

        figury.add(p);
        return true;
    }

    private static void wyswietlProstokaty(){
        for(Prostokat i : figury){
            System.out.printf("Prostokąt: %s x %s\n", i.getA(), i.getB());
        }
    }

    private static void printMenu(){
        System.out.println("\n\n-=( Test klas Kwadrat i Prostokat )=-\n");

        System.out.println(" Menu opcji:");
        System.out.println("   1) Wczytaj prostokąt");
        System.out.println("   2) Wyświetl wszystkie prostokąty");
        System.out.println("   3) Wyznacz sumę pól wszystkich prostokątów");
        System.out.println("   Q) Zakończ\n");

        System.out.print(" Twój wybór: ");
    }

    static LinkedList<Prostokat> figury;
}
