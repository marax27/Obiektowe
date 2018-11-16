
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.util.*;

/* Laboratorium 6 
 * Zadanie 1
 * Kacper Tonia
 * 
 * Program rysuje na ekranie figury podane poprzez funkcje MyPanel.addShape.
 * Istnieje mozliwosc przesuwania figur po ekranie poprzez przytrzymanie
 * lewego przycisku myszy i przesuniecie myszka po ekranie.
 * Uwaga: okno aktualizuje pozycje figur dopiero po pusczeniu przycisku myszy.
 */

public class Main extends JFrame {

	Main(){}

	public static void main(String[] args) {
		// Utworz przykladowe ksztalty.
		Shapes.Kwadrat kw = new Shapes.Kwadrat(321);
		Shapes.Circle cr = new Shapes.Circle(65);
		Shapes.Prostokat pr = new Shapes.Prostokat(80,  25);

		MyPanel mp = new MyPanel();
		
		// Dodaj ksztalty do panelu.
		mp.addShape(kw, new Shapes.Pos2D(10, 10));
		mp.addShape(cr, new Shapes.Pos2D(-2, 80));
		mp.addShape(pr, new Shapes.Pos2D(60, 60));

		// Utworz i przygotuj okno.
		Main main_frame = new Main();
		main_frame.setTitle("Zadanie 6.1 ~Kacper Tonia");
		main_frame.setSize(450, 450);
		main_frame.setVisible(true);
		main_frame.add(mp);

		main_frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
	}
}
