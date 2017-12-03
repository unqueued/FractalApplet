import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.applet.Applet;
import java.awt.Graphics;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.RenderingHints;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JApplet;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.UIManager;
import javax.swing.SwingUtilities;


public class Fractal extends JApplet {
	
	FractalPane fractalPane;
	JPanel bottomPanel;
	JTextField txtXOffset;
	JTextField txtYOffset;
	JTextField txtZoom;
	
	public void init() {
		JPanel c = new MainContent();
		getContentPane().add(c);
		fractalPane.drawFractal();
		bottomPanel.requestFocusInWindow();
	}
	
	public class MainContent extends JPanel {
		public MainContent() {
			super();
			setFocusTraversalKeysEnabled(false);
			setPreferredSize(new Dimension(1024, 768));
			fractalPane = new FractalPane();
			bottomPanel = new BottomControls();
			add(fractalPane, BorderLayout.NORTH);
			add(bottomPanel, BorderLayout.SOUTH);
		}
	}
	
	public class BottomControls extends JPanel {
		public BottomControls() {
			super();
			
			JButton cButton = new JButton("Reset");
			cButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(final ActionEvent e) {
						fractalPane.reset();
					}
				}
			);

 			add(cButton);
 			
//  			JButton btnLeft = new JButton("L");
//  			btnLeft.addActionListener(
//  				new ActionListener() {
//  					public void actionPerformed(final ActionEvent e) {
//  						fractalPane.moveLeft();
//  					}
//  				}
//  			);
//  			
//  			add(btnLeft);
//  			
//   			JButton btnRight = new JButton("R");
//  			btnLeft.addActionListener(
//  				new ActionListener() {
//  					public void actionPerformed(final ActionEvent e) {
//  						fractalPane.moveRight();
//  					}
//  				}
//  			);
//  			
//  			add(btnRight);
//  			
//   			JButton btnUp = new JButton("U");
//  			btnUp.addActionListener(
//  				new ActionListener() {
//  					public void actionPerformed(final ActionEvent e) {
//  						fractalPane.moveUp();
//  					}
//  				}
//  			);
//  			
//  			add(btnUp);
//  			
//   			JButton btnDown = new JButton("D");
//  			btnDown.addActionListener(
//  				new ActionListener() {
//  					public void actionPerformed(final ActionEvent e) {
//  						fractalPane.moveDown();
//  					}
//  				}
//  			);
//  			
//  			add(btnDown);
 			
 			txtXOffset = new JTextField();
 			JLabel lblXOffset = new JLabel("X Offset");
 			lblXOffset.setLabelFor(txtXOffset);
  			txtXOffset.setPreferredSize(
 				new Dimension(100, txtXOffset.getPreferredSize().height)
 			);
 			add(lblXOffset);
 			add(txtXOffset);
			
			txtYOffset = new JTextField();
 			JLabel lblYOffset = new JLabel("Y Offset");
 			lblYOffset.setLabelFor(txtYOffset);
 			txtYOffset.setPreferredSize(
 				new Dimension(100, txtYOffset.getPreferredSize().height)
 			);
 			add(lblYOffset);
 			add(txtYOffset);
 			
 			txtZoom = new JTextField();
 			JLabel lblZoom = new JLabel("Zoom");
 			lblZoom.setLabelFor(txtZoom);
 			txtZoom.setPreferredSize(
				new Dimension(100, txtZoom.getPreferredSize().height)
 			);
 			add(lblZoom);
 			add(txtZoom);
			
			setFocusable(true);
			addKeyListener(
				new KeyAdapter() {
					public void keyPressed(KeyEvent e) {
						//System.out.println(e.getKeyChar());
						System.out.println("X Offset: "+fractalPane.offsetX);
						System.out.println("Y Offset: "+fractalPane.offsetY);
						System.out.println("Zoom factor: "+fractalPane.zoom);
						switch(e.getKeyChar()) {
							case 'z':
								System.out.println("Zooming in...");
								fractalPane.zoomIn();
								break;
							case 'x':
								System.out.println("Zooming out...");
								fractalPane.zoomOut();
								break;
						}
						if(e.getKeyCode() == e.VK_DOWN) {
							System.out.println("Moving down...");
							fractalPane.moveDown();
						}
						if(e.getKeyCode() == e.VK_UP) {
							System.out.println("Moving up...");
							fractalPane.moveUp();
						}
						if(e.getKeyCode() == e.VK_LEFT) {
							System.out.println("Moving left...");
							fractalPane.moveLeft();
						}
						if(e.getKeyCode() == e.VK_RIGHT) {
							System.out.println("Moving right...");
							fractalPane.moveRight();
						}
					}
				}
			);
		}
	}
	
	public class FractalPane extends JPanel {
		
		private int width = 600;
		private int height = 600;
		public Image buffer;
		protected double zoom = 4;	// This is the default range of the set
		protected double offsetX = 0;
		protected double offsetY = 0;
		private double offsetStep = .05;
		
		public FractalPane() {
			super();
			setPreferredSize(new Dimension(width, height));
		}
		
		public void reset() {
			zoom = 4;
			offsetX = 0;
			offsetY = 0;
			offsetStep = .2;
			drawFractal();
		}
		
		public void zoomIn() {
			zoom = zoom / 2;
			drawFractal();
		}
		
		public void zoomOut() {
			zoom = zoom  * 2;
			drawFractal();
		}
		
		public void moveDown() {
			offsetY = offsetY + offsetStep * zoom;
			drawFractal();
		}

		public void moveUp() {
			offsetY = offsetY - offsetStep * zoom;
			drawFractal();
		}
		
		public void moveLeft() {
			offsetX = offsetX - offsetStep * zoom;
			drawFractal();
		}
		
		public void moveRight() {
			offsetX = offsetX + offsetStep * zoom;
			drawFractal();
		}

		public void drawFractal() {
			if(buffer == null)
				buffer = createImage(width, height);
			
			Graphics g = buffer.getGraphics();
			drawFractal(zoom, offsetX, offsetY, g);
			
			repaint();
		}
		
		public void drawFractal(double zoom, double offsetX, double offsetY, Graphics g) {			
			g.translate(width/2,height/2);
			//First, find the ratio for the full range (-2, 2) to our full
			//graphical area.
			double step = (double)4/width;
			
			//step = (double)(Math.abs((xS*step)-(xE*step)))/width;
			step = zoom/width;
			
			for(int yi = -300; yi < 300; yi++) {
				for(int xi = -300; xi < 300; xi++) {
					g.setColor(
						Color.getHSBColor(
							countIterations(((xi)*step)+offsetX, ((yi)*step)+offsetY)/100.0F,1F,1F
							//countIterations((xi+(xS+xE))*step,(yi+(xS+xE))*step)/100.0F,1F,1F
						)
					);
					g.fillOval(xi,yi,2,2);
				}
			}
			
			txtXOffset.setText(""+offsetX);
			txtYOffset.setText(""+offsetY);
			txtZoom.setText(""+zoom);
			bottomPanel.requestFocusInWindow();
		}
		
		public void paintComponent(Graphics g) {
			g.drawImage(buffer, 0, 0, null);
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
	}
}