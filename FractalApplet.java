import java.applet.Applet;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.*;

public class FractalApplet extends Applet implements 
	MouseListener, MouseMotionListener, Runnable {
	
	Thread t;
	boolean running = false;
	Graphics g;
	Image offscreen;
	
	int width = 400;
	int height = 400;
	
	public void init() {
		System.out.println("Initialized...");
		offscreen = createImage(width, height);
		g = offscreen.getGraphics();
		g.translate(width/2,height/2);
		addMouseListener(this);
		addMouseMotionListener(this);
		t = new Thread(this);
		//t.start();
		drawImage();
		repaint();
	}
	
	public void mouseClicked (MouseEvent e) {
		e.translatePoint(-width/2, -height/2);
		//drawImage();
		//repaint();
		//running = !running;
		double startX = -2;
		double endX = 2;
		double startY = startX;
		double endY = endX;
		double step = (Math.abs(startX)+Math.abs(endX))/(double)width;
		startX = (double)(e.getX() - 10) * step;
		endX = (double)(e.getX() + 10) * step;
		startY = startX;
		endY = endX;
		step = (Math.abs(startX)+Math.abs(endX))/(double)width;
		
		System.out.println(e);
		System.out.println(startX);
		System.out.println(endX);
		System.out.println(step);
		
		
		for(int yi = -250; yi < 250; yi++) {
			for(int xi = -250; xi < 250; xi++) {
				g.setColor(
					Color.getHSBColor(
						countIterations(xi*step,yi*step)/100.0F,1F,1F
					)
				);
				g.fillOval(xi,yi,1,1);
			}
		}
		
		repaint();
	}
	
	public void drawImage() {
		
		double startX = -2;
		double endX = 2;
		double startY = startX;
		double endY = endX;
		double step = (Math.abs(startX)+Math.abs(endX))/(double)width;
		
		for(int yi = -250; yi < 250; yi++) {
			for(int xi = -250; xi < 250; xi++) {
				g.setColor(
					Color.getHSBColor(
						countIterations(xi*step,yi*step)/100.0F,1F,1F
					)
				);
				g.fillOval(xi,yi,1,1);
			}
		}
	}
	
	int countIterations(double x, double y) {
		int count = 0;
		double zx = x;
		double zy = y;
		while (count < 80 && Math.abs(x) < 100 && Math.abs(zy) < 100) {
			double new_zx = zx*zx - zy*zy + x;
			zy = 2*zx*zy + y;
			zx = new_zx;
			count++;
		}
		return count;
	}

	public void run() {
		while(true) {
			if(running) {
				drawImage();
				
				repaint();
				try
				{
					t.sleep(0);
				}
				catch (InterruptedException e)
				{}
			}
		}
	}
	
	public void paint(Graphics g) {
		g.drawImage(offscreen,0,0,this);
	}
	
	public void update(Graphics g) {
		paint(g);
	}
	
	public void mouseDragged(MouseEvent e) {
		//System.out.println(e);
// 		System.out.println(e.getX());
// 		g.setColor(Color.black);
// 		g.fillRect(e.getX() - 250, e.getY() - 250, 20, 20);
// 		repaint();
	}
	
	public void mouseMoved(MouseEvent e) {}
	public void mouseEntered (MouseEvent me) {}
	public void mousePressed (MouseEvent me) {}
	public void mouseReleased (MouseEvent me) {} 
	public void mouseExited (MouseEvent me) {}  
}