package gui;

import java.awt.*;

import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.GroupLayout.*;
import javax.swing.LayoutStyle.*;
import javax.swing.tree.*;
import dao.*;
import model.*;

public class ManageTypeAbsence {

	private JFrame frmManagermanageAbsenceType;
	private JTextField id;
	private JTextField entitle;
	private boolean newTypeAbsence = false;
	private int returnValue;
	
	/**
	 * Create the application.
	 */
	public ManageTypeAbsence() {
		frmManagermanageAbsenceType = new JFrame();
		frmManagermanageAbsenceType.setTitle("Manager|Manage absence type");
		frmManagermanageAbsenceType.setBounds(100, 100, 750, 500);
		frmManagermanageAbsenceType.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		frmManagermanageAbsenceType.addWindowListener( new WindowAdapter(){
		    @Override
			public void windowClosed(WindowEvent e) {
		    	@SuppressWarnings("unused")
				ManagerMenu window = new ManagerMenu();
		    	frmManagermanageAbsenceType.dispose();
		    }
		});
		
		JPanel panel = new JPanel();
		
		JPanel pantree = new JPanel();
		pantree.setLayout(new GridLayout(1, 0, 0, 0));
		
		
		JScrollPane scrollPane = new JScrollPane();
		pantree.add(scrollPane);
		
		
		JTree tree = new JTree();
		scrollPane.setViewportView(tree);
		tree.setModel(new DefaultTreeModel(findNextTreeNode("Absence types")));
		
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(ManageTypeAbsence.class.getResource("/gui/ESIGELEC.png")));
		
		
		JLabel lblId = new JLabel("Id");
		
		id = new JTextField();
		id.setEditable(false);
		id.setColumns(10);
		
		JLabel lblentitle = new JLabel("Entitle");
		
		entitle = new JTextField();
		entitle.setColumns(10);
		
		//Organizing the panel
		GroupLayout groupLayout = new GroupLayout(frmManagermanageAbsenceType.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 978, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
		);
		
		JButton addTypeAbsence = new JButton("Add");
		
		JButton updateTypeAbsence = new JButton("Update");
		updateTypeAbsence.setEnabled(false);
		
		JButton deleteTypeAbsence = new JButton("Delete");
		deleteTypeAbsence.setEnabled(false);
	
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(pantree, GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED, 47, GroupLayout.PREFERRED_SIZE)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
							.addComponent(label)
							.addGap(125))
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(updateTypeAbsence, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(deleteTypeAbsence, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
								.addComponent(addTypeAbsence, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGap(132))
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lblId, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
									.addComponent(id, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lblentitle)
									.addPreferredGap(ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
									.addComponent(entitle, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)))
							.addGap(34))))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(pantree, GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(30)
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
					.addGap(38)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(id, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblId))
					.addGap(24)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(entitle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblentitle))
					.addGap(59)
					.addComponent(addTypeAbsence)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(updateTypeAbsence, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(deleteTypeAbsence)
					.addContainerGap(103, Short.MAX_VALUE))
		);
		
		panel.setLayout(gl_panel);
		frmManagermanageAbsenceType.getContentPane().setLayout(groupLayout);
		
		//Detecting mouse
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				initForms();
				String s = tree.getLastSelectedPathComponent().toString();
				if (s.contains("@")) {
					s = s.substring(0, s.indexOf('@'));
					if (containsOnlyNumbers(s)) {
						// Show Type_absence
						newTypeAbsence = false;
						updateTypeAbsence.setEnabled(true);
						deleteTypeAbsence.setEnabled(true);
						AbsenceTypeDAO type_absenceDAO = new AbsenceTypeDAO();
						AbsenceType type_absence = type_absenceDAO.get(Integer.parseInt(s));
						id.setText(String.valueOf(type_absence.getId()));
						entitle.setText(type_absence.getEntitle());
					}
				}
			}
		});
		
		/*
		 *  When the button add type_absence is pressed we have two cases, 
		 *  if newType_absence is false the user shall enter informations
		 *  if newType_absence is true we call the method add from Type_absenceDAO to add the type_absence to the database
		 */
		addTypeAbsence.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!newTypeAbsence) {
					initForms();
					updateTypeAbsence.setEnabled(false);
					deleteTypeAbsence.setEnabled(false);
					newTypeAbsence = true;
				}
				else {
					returnValue = new AbsenceTypeDAO().add(new AbsenceType(entitle.getText()));
					if(returnValue != 0)
						JOptionPane.showMessageDialog(null, "Type of absence added");
					else
						JOptionPane.showMessageDialog(null, "Type of absence not added");
					newTypeAbsence = false;
					tree.setModel(new DefaultTreeModel(findNextTreeNode("Absence types")));
				}
				
			}
		});
		
		// When the button update type_absence is pressed, we call the method update from Type_absenceDAO to update the type_absence
		updateTypeAbsence.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnValue = new AbsenceTypeDAO().update(new AbsenceType(Integer.parseInt(id.getText()), entitle.getText()));
				if(returnValue != 0)
					JOptionPane.showMessageDialog(null, "Type of absence updated");
				else
					JOptionPane.showMessageDialog(null, "Type of absence not updated");
				tree.setModel(new DefaultTreeModel(findNextTreeNode("Absence types")));
			}
		});
		
		// When the button delete type_absence is pressed, we call the method delete from Type_absenceDAO to delete the course
		deleteTypeAbsence.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnValue = new AbsenceTypeDAO().delete(new AbsenceType(Integer.parseInt(id.getText()), entitle.getText()));
				if(returnValue != 0)
					JOptionPane.showMessageDialog(null, "Type of absence deleted");
				else
					JOptionPane.showMessageDialog(null, "Type of absence not deleted");
				tree.setModel(new DefaultTreeModel(findNextTreeNode("Absence types")));
			}
		});
		
		frmManagermanageAbsenceType.setVisible(true);
		
	}
	
	/**
	 * List modules and courses from the DB in the tree.
	 *
	 */
	private DefaultMutableTreeNode findNextTreeNode(String nodeName) {
		AbsenceTypeDAO type_absenceDAO = new AbsenceTypeDAO();
		DefaultMutableTreeNode node1 = new DefaultMutableTreeNode(nodeName);
		DefaultMutableTreeNode node2 = null;
		ArrayList<AbsenceType> type_absences = type_absenceDAO.getList();
        if (type_absences != null) {
            for (AbsenceType s : type_absences) {
            	node2 = new DefaultMutableTreeNode(s.getId() + "@ " + s.getEntitle());
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
		entitle.setText("");
	}
}

