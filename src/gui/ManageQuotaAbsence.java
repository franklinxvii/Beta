package gui;

import model.*;


import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.tree.*;
import dao.*;
import javax.swing.GroupLayout.*;
import javax.swing.LayoutStyle.*;

public class ManageQuotaAbsence {

	private JFrame frmManagermanageAbsenceQuota;
	private JTextField id;
	private JTextField hours;
	private boolean newQuotaAbsence = false;
	private int returnValue;


	/**
	 * Create the application.
	 */
	public ManageQuotaAbsence(){
		frmManagermanageAbsenceQuota = new JFrame();
		frmManagermanageAbsenceQuota.setTitle("Manager|Manage absence quota");
		frmManagermanageAbsenceQuota.setBounds(100, 100, 750, 500);
		frmManagermanageAbsenceQuota.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel panel = new JPanel();
		
		JPanel pantree = new JPanel();
		pantree.setLayout(new GridLayout(1, 0, 0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		pantree.add(scrollPane);
		
		JTree tree = new JTree();
		scrollPane.setViewportView(tree);
		tree.setModel(new DefaultTreeModel(findNextTreeNode("QuotaAbsences")));
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(ManageQuotaAbsence.class.getResource("/gui/ESIGELEC.png")));
		
		JLabel lblId = new JLabel("Id");
		
		id = new JTextField();
		id.setEditable(false);
		id.setColumns(10);
		
		JLabel lblHours = new JLabel("Hours");
		
		hours = new JTextField();
		hours.setColumns(3);
		GroupLayout groupLayout = new GroupLayout(frmManagermanageAbsenceQuota.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 978, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
		);
		
		JButton addQuotaAbsence = new JButton("Add");
		
		JButton updateQuotaAbsence = new JButton("Update");
		
		JButton deleteQuotaAbsence = new JButton("Delete");
	
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(pantree, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel.createSequentialGroup()
								.addPreferredGap(ComponentPlacement.RELATED, 194, Short.MAX_VALUE)
								.addComponent(label)
								.addGap(189))
							.addGroup(gl_panel.createSequentialGroup()
								.addGap(39)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_panel.createSequentialGroup()
										.addComponent(lblId, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
										.addGap(57))
									.addComponent(lblHours, GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
									.addComponent(id, GroupLayout.PREFERRED_SIZE, 307, GroupLayout.PREFERRED_SIZE)
									.addComponent(hours, GroupLayout.PREFERRED_SIZE, 307, GroupLayout.PREFERRED_SIZE))
								.addGap(57)))
						.addGroup(gl_panel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(addQuotaAbsence, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(updateQuotaAbsence, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(deleteQuotaAbsence, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGap(157))))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(pantree, GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(32)
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
					.addGap(32)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblId)
							.addGap(24)
							.addComponent(lblHours))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(id, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(hours, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(97)
					.addComponent(addQuotaAbsence)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(updateQuotaAbsence)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(deleteQuotaAbsence)
					.addContainerGap(93, Short.MAX_VALUE))
		);
		
		
		panel.setLayout(gl_panel);
		frmManagermanageAbsenceQuota.getContentPane().setLayout(groupLayout);
		
		frmManagermanageAbsenceQuota.addWindowListener( new WindowAdapter(){
		    @Override
			public void windowClosed(WindowEvent e) {
		    	@SuppressWarnings("unused")
				ManagerMenu window = new ManagerMenu();
		    	frmManagermanageAbsenceQuota.dispose();
		    }
		});
		
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				initForms();
				String s = tree.getLastSelectedPathComponent().toString();
				if (s.contains("@")) {
					s = s.substring(0, s.indexOf('@'));
					if (containsOnlyNumbers(s)) {
						// affichage teacher
						newQuotaAbsence = false;
						QuotaAbsenceDAO QuotaAbsenceDAO = new QuotaAbsenceDAO();
						QuotaAbsence QuotaAbsence = QuotaAbsenceDAO.get(Integer.parseInt(s));
						id.setText(String.valueOf(QuotaAbsence.getId()));
						hours.setText(String.valueOf(QuotaAbsence.getHours()));
					}
				}
			}
		});
		
		/*
		 *  When the button add Quota_absence is pressed we have two cases, 
		 *  if newQuotaAbsence is false the user shall enter informations
		 *  if newQuotaAbsence is true we call the method add from QuotaAbsenceDAO to add the teacher to the database
		 */
		addQuotaAbsence.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!newQuotaAbsence) {
					id.setText("");
					hours.setText("");
					updateQuotaAbsence.setEnabled(false);
					deleteQuotaAbsence.setEnabled(false);
					newQuotaAbsence = true;
				}
				else {
					try {
						returnValue = new QuotaAbsenceDAO().add(new QuotaAbsence(Integer.parseInt(hours.getText())));
						if(returnValue != 0) {
							JOptionPane.showMessageDialog(frmManagermanageAbsenceQuota, "Quota added");
						}
						else {
							JOptionPane.showMessageDialog(frmManagermanageAbsenceQuota, "Quota not added");
						}
						updateQuotaAbsence.setEnabled(true);
						deleteQuotaAbsence.setEnabled(true);
						newQuotaAbsence = false;
						tree.setModel(new DefaultTreeModel(findNextTreeNode("QuotaAbsences")));
					}
					catch(NumberFormatException arg0) {
						JOptionPane.showMessageDialog(frmManagermanageAbsenceQuota, "Please enter a string");
					}
				}
				
			}
		});
		
		// When the button update teacher is pressed, we call the method update from QuotaAbsenceDAO to update the teacher
		updateQuotaAbsence.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					returnValue = new QuotaAbsenceDAO().update(new QuotaAbsence(Integer.parseInt(id.getText()), Integer.parseInt(hours.getText())));
					if(returnValue != 0) {
						JOptionPane.showMessageDialog(frmManagermanageAbsenceQuota, "Quota updated");
					}
					else {
						JOptionPane.showMessageDialog(frmManagermanageAbsenceQuota, "Quota not updated");
					}
					updateQuotaAbsence.setEnabled(true);
					deleteQuotaAbsence.setEnabled(true);
					newQuotaAbsence = false;
					tree.setModel(new DefaultTreeModel(findNextTreeNode("QuotaAbsences")));
				}
				catch(NumberFormatException arg0) {
					JOptionPane.showMessageDialog(frmManagermanageAbsenceQuota, "Please enter a string");
				}
			}
		});
		
		// When the button delete teacher is pressed, we call the method delete from QuotaAbsenceDAO to delete the course
		deleteQuotaAbsence.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					returnValue = new QuotaAbsenceDAO().delete(new QuotaAbsence(Integer.parseInt(id.getText()), Integer.parseInt(hours.getText())));
					if(returnValue != 0) {
						JOptionPane.showMessageDialog(frmManagermanageAbsenceQuota, "Quota deleted");
					}
					else {
						JOptionPane.showMessageDialog(frmManagermanageAbsenceQuota, "Quota not deleted");
					}
					updateQuotaAbsence.setEnabled(true);
					deleteQuotaAbsence.setEnabled(true);
					newQuotaAbsence = false;
					tree.setModel(new DefaultTreeModel(findNextTreeNode("QuotaAbsences")));
				}
				catch(NumberFormatException arg0) {
					JOptionPane.showMessageDialog(frmManagermanageAbsenceQuota, "Please enter a string");
				}
			}
		});
		
		frmManagermanageAbsenceQuota.setVisible(true);
	}
	
	/**
	 * List modules and courses from the DB in the tree.
	 *
	 */
	private DefaultMutableTreeNode findNextTreeNode(String nodeName) {
		QuotaAbsenceDAO QuotaAbsenceDAO = new QuotaAbsenceDAO();
		DefaultMutableTreeNode node1 = new DefaultMutableTreeNode(nodeName);
		DefaultMutableTreeNode node2 = null;
		ArrayList<QuotaAbsence> QuotaAbsences = QuotaAbsenceDAO.getList();
        if (QuotaAbsences != null) {
            for (QuotaAbsence s : QuotaAbsences) {
            	node2 = new DefaultMutableTreeNode(s.getId() + "@ " + s.getHours());
				node1.add(node2);
            }
        }
		return node1;
	}
	
	boolean containsOnlyNumbers(String s) {
		return s.matches("[0-9]*");
	}
	
	void initForms() {
		id.setText("");
		hours.setText("");
	}
}
