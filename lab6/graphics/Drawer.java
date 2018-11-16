/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author szymon
 */

import java.awt.*;
import java.awt.geom.Ellipse2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Drawer extends JPanel{

    public  void drawCircle(Graphics arg0, int x, int y, int r){
        Graphics2D g2 = (Graphics2D) arg0;
        Ellipse2D e = new Ellipse2D.Double(x,y,r,r);
        g2.draw(e);

        GradientPaint gp = new GradientPaint(x-r/2, y-r/2, Color.red,x+r/2, y+r/2, Color.blue, false);
        // Fill with a gradient.
        g2.setPaint(gp);
        g2.fill(e);

        g2.draw(e);

    }

    private  void drawString(Graphics arg0, int x, int y, String s){
        Graphics g = arg0;
        g.setColor(Color.red);

        g.setFont(new Font("SansSerif",Font.BOLD,30));
        g.drawString(s, x, y);

    }

    public void drawRect(Graphics arg0,int x, int y, int w, int h){
        Graphics g = arg0;
        g.drawRect(x, y, w, h);

    }

    @Override
    public void paint(Graphics arg0) {
        drawRect(arg0,100,100,200,200);
        drawCircle(arg0,100, 100, 200);
        drawString(arg0,200, 200, "A");

    }





}
