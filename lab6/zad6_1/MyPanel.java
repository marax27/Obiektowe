import java.awt.*;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Shapes.Pos2D;

public class MyPanel extends JPanel implements MouseListener {
	public MyPanel() {
		start_mouse_pos = null;
		addMouseListener(this);

		setBackground(new Color(0,0,0));  //kolor tla panelu
		shape_list = new LinkedList<ListNode>();
	}
	
	// Dodaj nowy Shape do listy.
	public void addShape(Shapes.Shape shape, Pos2D position) {
		shape_list.add(new ListNode(shape, position));
	}
	
	// Rysowanie wszystkich ksztaltow na ekranie.
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);  //czyszczenie ekranu

		g.setColor(new Color(0x44, 0x44, 0x77));  //arbitralny kolor figur
		
		for(ListNode node : shape_list)
			node.shape.draw(g, node.pos);
	}
	
	// --------------------------------------------------
	class ListNode{
		public ListNode(Shapes.Shape _shape, Pos2D _pos) {
			shape = _shape;
			pos = _pos;
		}
		public Shapes.Shape shape;
		public Pos2D pos;
	}
	
	// Lista ksztaltow do wyrysowania na ekranie.
	private LinkedList<ListNode> shape_list;
	// --------------------------------------------------

	private Pos2D start_mouse_pos;

	@Override
	public void mousePressed(MouseEvent e){
		if(e.getButton() == MouseEvent.BUTTON1){
			start_mouse_pos = new Pos2D(e.getX(), e.getY());
		}
	}

	// Zaktualizuj pozycje figur na podstawie przemieszczenia myszy.
	@Override
	public void mouseReleased(MouseEvent e){
		if(e.getButton() == MouseEvent.BUTTON1){
			Pos2D current_pos = new Pos2D(e.getX(), e.getY());
			Pos2D final_offset = Pos2D.diff(current_pos, start_mouse_pos);
			start_mouse_pos = null;

			for(Iterator<ListNode> i = shape_list.iterator(); i.hasNext();){
				ListNode ln = i.next();
				ln.pos = Pos2D.sum(ln.pos, final_offset);
			}

			removeAll();
			updateUI();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
}
