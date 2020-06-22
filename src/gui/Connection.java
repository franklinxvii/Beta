package gui;

import javax.swing.*;

import java.awt.*;
import dao.*;
import java.awt.event.*;

public class Connection {

	private JFrame frmConnection;
	private JPasswordField password;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					@SuppressWarnings("unused")
					Connection window = new Connection();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Connection() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmConnection = new JFrame();
		frmConnection.setTitle("Connection");
		frmConnection.setBounds(100, 100, 572, 381);
		frmConnection.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Panel panel = new Panel();
		panel.setBackground(new Color(255, 255, 255));
		frmConnection.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		Label label = new Label("Username");
		label.setBounds(120, 160, 73, 24);
		panel.add(label);
		
		Label label_1 = new Label("Password");
		label_1.setBounds(120, 200, 71, 24);
		panel.add(label_1);
		
		TextField id = new TextField();
		id.setBounds(261, 160, 163, 24);
		panel.add(id);
		
		password = new JPasswordField();
		password.setBounds(261, 202, 163, 22);
		panel.add(password);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Connection.class.getResource("/gui/ESIGELEC.png")));
		lblNewLabel.setBounds(198, 33, 137, 81);
		panel.add(lblNewLabel);
		
		//If the username and password entered is corrected we open the manager frame, else we show an error
		JButton logIn = new JButton("Log In");
		logIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(id.getText().equals("PDL") && String.valueOf(password.getPassword()).equals("PDL")) {
					@SuppressWarnings("unused")
					ManagerMenu window = new ManagerMenu();
					frmConnection.dispose();
				}
				else if(new TeacherDAO().get(id.getText(), String.valueOf(password.getPassword())) != null) {
					@SuppressWarnings("unused")
					TeacherMenu window = new TeacherMenu(new TeacherDAO().get(id.getText(), String.valueOf(password.getPassword())));
					frmConnection.dispose();
				}
				else if(new StudentDAO().get(id.getText(), String.valueOf(password.getPassword())) != null) {
					@SuppressWarnings("unused")
					StudentMenu window = new StudentMenu(new StudentDAO().get(id.getText(), String.valueOf(password.getPassword())));
					frmConnection.dispose();
				}
				
				else {
					JOptionPane.showMessageDialog(frmConnection, "Id/Username incorrect");
				}
			}
		});
		logIn.setBounds(198, 257, 137, 25);
		panel.add(logIn);
		frmConnection.setVisible(true);
	}
}
