import java.applet.Applet;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.*;

public class FractalApplet extends Applet implements MouseListener, Runnable {
	
	Thread t;
	boolean running = false;
	Graphics bufferGraphics;
	Image offscreen;
	
	int maxX = 500;
	int maxY = 500;
	int plainSize = 400;
	
	public void init() {
		System.out.println("Initialized...");
		offscreen = createImage(maxX, maxY);
		bufferGraphics = offscreen.getGraphics();
		addMouseListener(this);
		t = new Thread(this);
		t.start();
	}
	
	public void mouseClicked (MouseEvent me) {
		System.out.println("Clicked");
		running = !running;
	}
	
	public void iterate() {
		
	}
	
	public void drawImage() {
		double maxSize = 1;
		double startX = -1;
		double startY = -1;
		double endX = 1;
		double endY = 1;
		double width = maxX/plainSize;
		double height = width;
		double step = 2/(double)plainSize;
		
		int sequence = 0;
		int sequenceMax = maxX*maxY;
		
		for(int y = 0; y < plainSize; y++) {
			for(int x = 0; x < plainSize; x++) {
				bufferGraphics.setColor(
					Color.getHSBColor(
						countIterations(x*step,y*step)/100.0F,1F,1F
					)
				);
				bufferGraphics.fillOval((int)(x*width),(int)(y*width),(int)(width),(int)height);
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
	
	public void mouseEntered (MouseEvent me) {}
	public void mousePressed (MouseEvent me) {}
	public void mouseReleased (MouseEvent me) {} 
	public void mouseExited (MouseEvent me) {}  
}