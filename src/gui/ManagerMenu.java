package gui;


import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import dao.*;

public class ManagerMenu {

	private JFrame frmManagermanagerMenu;
	private JPanel panel;
	private JLabel label;
	private JButton manageCourse;
	private JButton manageTeacher;
	private JButton manageStudent;
	private JButton manageGroup;
	private JButton createAbsence;
	private JButton quotaAbsence;
	private JButton processProof;
	private JButton logOut;
	private JLabel menu;
	
	/**
	 * Create the application.
	 */
	public ManagerMenu(){
		frmManagermanagerMenu = new JFrame();
		frmManagermanagerMenu.setTitle("Manager|Manager menu");
		frmManagermanagerMenu.setBounds(100, 100, 434, 516);
		frmManagermanagerMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		frmManagermanagerMenu.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		label = new JLabel("");
		label.setIcon(new ImageIcon(ManagerMenu.class.getResource("/gui/ESIGELEC.png")));
		label.setBounds(136, 13, 157, 36);
		panel.add(label);
		
		//if manage course is selected we open the manage course page
		manageCourse = new JButton("Manage course");
		manageCourse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unused")
				ManageCourse window = new ManageCourse();
				frmManagermanagerMenu.dispose();
			}
		});
		manageCourse.setBounds(118, 89, 175, 25);
		panel.add(manageCourse);
		
		//if manager teacher is selected we open the manage teacher page
		manageTeacher = new JButton("Manage teacher");
		manageTeacher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unused")
				ManageTeacher window = new ManageTeacher();
				frmManagermanagerMenu.dispose();
			}
		});
		manageTeacher.setBounds(118, 127, 175, 25);
		panel.add(manageTeacher);
		
		manageStudent = new JButton("Manage student");
		manageStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unused")
				ManageStudent window = new ManageStudent();
				frmManagermanagerMenu.dispose();
			}
		});
		manageStudent.setBounds(118, 165, 175, 25);
		panel.add(manageStudent);
		
		manageGroup = new JButton("Manage group");
		manageGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unused")
				ManageGroup window = new ManageGroup();
				frmManagermanagerMenu.dispose();
			}
		});
		manageGroup.setBounds(118, 203, 175, 25);
		panel.add(manageGroup);
		
		createAbsence = new JButton("Create absence type");
		createAbsence.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unused")
				ManageTypeAbsence window = new ManageTypeAbsence();
				frmManagermanagerMenu.dispose();
			}
		});
		createAbsence.setBounds(118, 241, 175, 25);
		panel.add(createAbsence);
		
		quotaAbsence = new JButton("Define absence quota");
		quotaAbsence.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unused")
				ManageQuotaAbsence window = new ManageQuotaAbsence();
				frmManagermanagerMenu.dispose();
			}
		});
		quotaAbsence.setBounds(118, 279, 175, 25);
		panel.add(quotaAbsence);
		
		processProof = new JButton("Process proof");
		processProof.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unused")
				ProcessProof window = new ProcessProof();
				frmManagermanagerMenu.dispose();
			}
		});
		processProof.setBounds(118, 355, 175, 25);
		panel.add(processProof);
		
		// if  log out is pressed we open the connection page
		logOut = new JButton("Log Out");
		logOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unused")
				Connection window = new Connection();
				frmManagermanagerMenu.dispose();
			}
		});
		logOut.setBounds(118, 431, 175, 25);
		panel.add(logOut);
		
		menu = new JLabel("Menu");
		menu.setBounds(185, 55, 46, 24);
		panel.add(menu);
		
		JButton btnNewButton = new JButton("Manage lesson");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				@SuppressWarnings("unused")
				ManageLessons window = new ManageLessons();
				frmManagermanagerMenu.dispose();
			}
		});
		btnNewButton.setBounds(118, 317, 175, 25);
		panel.add(btnNewButton);
		
		JButton dealSpecial = new JButton("Deal with special cases");
		dealSpecial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				@SuppressWarnings("unused")
				DealWithSpecialCases window = new DealWithSpecialCases();
				frmManagermanagerMenu.dispose();
			}
		});
		dealSpecial.setBounds(118, 393, 175, 25);
		if(!new AbsenceDAO().getSpecialCases())
			dealSpecial.setEnabled(false);
		else {
			JOptionPane.showMessageDialog(null, "You have a special case to deal with.");
			dealSpecial.setEnabled(true);
		}
		panel.add(dealSpecial);
		frmManagermanagerMenu.setVisible(true);
	}
}
