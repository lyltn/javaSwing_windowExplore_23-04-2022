package window_Explore;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.io.BufferedInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.Color;
import java.io.BufferedReader;

public class ReadFile extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReadFile frame = new ReadFile();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ReadFile() {
		setBackground(new Color(240, 248, 255));
		setTitle("Read File");
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 744, 573);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(5, 10, 720, 516);
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 18));
		contentPane.add(textArea);
		textArea.setText("");
		
			
			String r = "";
			try {
				FileInputStream fin = new FileInputStream(Explore.nameFile());
				BufferedInputStream bin = new BufferedInputStream(fin);
				int i;
				while ((i = bin.read()) != -1) {
					r+=(char)i;
				}
				textArea.append(r);
				bin.close();
				fin.close();
			} catch (Exception e1) {
				System.out.println(e1);
			}
			setLocationRelativeTo(null);
	}

}
