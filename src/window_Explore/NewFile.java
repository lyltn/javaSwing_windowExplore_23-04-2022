package window_Explore;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.TextArea;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NewFile extends JFrame {
	
	private TextArea textArea;

	private JPanel contentPane;
	private boolean checkOk = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewFile frame = new NewFile();
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
	public NewFile() {
		setTitle("NewFile");
		setBackground(new Color(248, 248, 255));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 768, 565);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(248, 248, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textArea = new TextArea();
		textArea.setFont(new Font("Dialog", Font.PLAIN, 16));
		textArea.setBounds(10, 10, 734, 406);
		contentPane.add(textArea);
		
		JButton btnNewButton = new JButton("save\r\n");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCheckOk(true);
				setVisible(false);
				
			}
		});
		btnNewButton.setBackground(new Color(176, 196, 222));
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnNewButton.setBounds(286, 454, 163, 42);
		contentPane.add(btnNewButton);
	}
	public String content() {
		return textArea.getText();
	}
	public boolean isCheckOk() {
		return checkOk;
	}

	public void setCheckOk(boolean checkOk) {
		this.checkOk = checkOk;
	}
	public void stay() {
		textArea.setText("");
	}
}
