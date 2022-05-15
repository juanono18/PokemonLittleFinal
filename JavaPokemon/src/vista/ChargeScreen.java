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
import java.awt.Window.Type;
import java.util.concurrent.TimeUnit;

import javax.swing.JProgressBar;
import javax.swing.JLabel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.SwingConstants;

public class ChargeScreen extends JFrame {
	static JLabel RunningChar;
	private static JPanel contentPane;

	public ChargeScreen() {
		setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 450);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		RunningChar = new JLabel();
		RunningChar.setBounds(134, 120, 166, 161);
		contentPane.add(RunningChar);
		ImageIcon icon = new ImageIcon(ChargeScreen.class.getResource("/Golbat.gif"));
		
		Image image = icon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(RunningChar.getHeight(), RunningChar.getWidth(), java.awt.Image.SCALE_DEFAULT); // scale it the smooth way  
		icon = new ImageIcon(newimg);
		RunningChar.setIcon(icon);
		
		JLabel lblNewLabel = new JLabel("CARGANDO...");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(10, 284, 414, 41);
		contentPane.add(lblNewLabel);
	}
	public static void screenCharger() throws InterruptedException {
		Kensoft animate = new Kensoft();
		//animate.jLabelXRight(RunningChar.getX(), progressBar.getWidth()-40, 5, 1, RunningChar);
		
	}
}
