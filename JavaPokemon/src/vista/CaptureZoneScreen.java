package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import KentHipos.Kensoft;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class CaptureZoneScreen extends JFrame {

	private JPanel contentPane;
	private JLabel CharLabel;

	/**
	 * Create the frame.
	 */
	public CaptureZoneScreen() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 450);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(153, 255, 102));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		CharLabel = new JLabel("");
		CharLabel.setBounds(201, 368, 44, 42);
		CharLabel.setFocusable(true);
		CharLabel.grabFocus();
		contentPane.add(CharLabel);
		ImageIcon icon = new ImageIcon(CaptureZoneScreen.class.getResource("/CharImg/still.png"));
		Image image = icon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(CharLabel.getHeight(), CharLabel.getWidth(), java.awt.Image.SCALE_DEFAULT); // scale it the smooth way  
		icon = new ImageIcon(newimg);
		CharLabel.setIcon(icon);
		CharLabel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_W) {
					ImageIcon icon = new ImageIcon(CaptureZoneScreen.class.getResource("/CharImg/up.gif"));
					Kensoft animate = new Kensoft();
					if(!CharLabel.getIcon().equals(icon)) {
					Image image = icon.getImage(); // transform it 
					Image newimg = image.getScaledInstance(CharLabel.getHeight(), CharLabel.getWidth(), java.awt.Image.SCALE_DEFAULT); // scale it the smooth way  
					icon = new ImageIcon(newimg);
					CharLabel.setIcon(icon);
					}
					animate.jLabelYUp(CharLabel.getY(), CharLabel.getY()-10, 10, 1, CharLabel);

				}
				else if(e.getKeyCode() == KeyEvent.VK_S) {
					ImageIcon icon = new ImageIcon(CaptureZoneScreen.class.getResource("/CharImg/down.gif"));
					Kensoft animate = new Kensoft();
					if(!CharLabel.getIcon().equals(icon)) {
					Image image = icon.getImage(); // transform it 
					Image newimg = image.getScaledInstance(CharLabel.getHeight(), CharLabel.getWidth(), java.awt.Image.SCALE_DEFAULT); // scale it the smooth way  
					icon = new ImageIcon(newimg);
					CharLabel.setIcon(icon);
					}
					animate.jLabelYDown(CharLabel.getY(), CharLabel.getY()+10, 10, 1, CharLabel);
					
				}
			}
		});
	}

	public JLabel getCharLabel() {
		return CharLabel;
	}

	public void setCharLabel(JLabel charLabel) {
		CharLabel = charLabel;
	}
}
