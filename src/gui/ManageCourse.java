package gui;

import javax.swing.*;

import java.awt.*;
import javax.swing.tree.*;

import dao.*;
import model.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.GroupLayout.*;


public class ManageCourse {

	private JFrame frmManagermanageCourse;
	private JTextField idModule;
	private JTextField nameModule;
	private JTextField hourlyMass;
	private JTextField idCourse;
	private JTextField nameCourse;
	private boolean newModule;
	private boolean newCourse;
	int added; 

	/**
	 * Create the application.
	 */
	public ManageCourse(){
		frmManagermanageCourse = new JFrame();
		frmManagermanageCourse.setTitle("Manager|Manage course");
		frmManagermanageCourse.getContentPane().setBackground(Color.WHITE);
		frmManagermanageCourse.setBounds(100, 100, 1047, 567);
		frmManagermanageCourse.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		newModule = false;
		
		frmManagermanageCourse.addWindowListener( new WindowAdapter(){
		    @Override
			public void windowClosed(WindowEvent e) {
		    	@SuppressWarnings("unused")
				ManagerMenu window = new ManagerMenu();
		    	frmManagermanageCourse.dispose();
		    }
		});
		
		JPanel panel_1 = new JPanel();
		
		idModule = new JTextField();
		idModule.setEditable(false);
		idModule.setColumns(10);
		
		JButton addModule = new JButton("Add");
		
		JButton updateModule = new JButton("Update");
		updateModule.setEnabled(false);
		
		JButton deleteModule = new JButton("Delete");
		deleteModule.setEnabled(false);
		
		hourlyMass = new JTextField();
		hourlyMass.setColumns(3);
		
		idCourse = new JTextField();
		idCourse.setEditable(false);
		idCourse.setColumns(10);
		
		nameCourse = new JTextField();
		nameCourse.setColumns(10);
		
		JButton addCourse = new JButton("Add");
		
		JButton updateCourse = new JButton("Update");
		updateCourse.setEnabled(false);
		
		JButton deleteCourse = new JButton("Delete");
		deleteCourse.setEnabled(false);
		
		nameModule = new JTextField();
		nameModule.setColumns(10);
			
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(ManageCourse.class.getResource("/gui/ESIGELEC.png")));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		
		JScrollPane scrollPane = new JScrollPane();
			
		JTree tree = new JTree();
		scrollPane.setViewportView(tree);
		tree.setModel(new DefaultTreeModel(findNextTreeNode("Modules")));
		
		JLabel lblIdentifiant = new JLabel("Identifier");
			
		JLabel lblLibell = new JLabel("Name");
			
		JLabel lblIdentifiant_1 = new JLabel("Identifier");
			
		JLabel lblNom = new JLabel("Name");
			
		JLabel lblMasseHoraire = new JLabel("Hourly Mass");
			
		//We list the module from database into the JCombobox 
		JComboBox<Module> selectModule = new JComboBox<>();
		selectModule.setModel(new DefaultComboBoxModel<Module>(initListModule().toArray(new Module[0])));
			
		JLabel lblModule = new JLabel("Module");
		GroupLayout groupLayout = new GroupLayout(frmManagermanageCourse.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 1029, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)
		);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
					.addGap(33)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(lblIdentifiant)
						.addComponent(lblLibell, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE))
					.addGap(12)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(123)
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(idModule, GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
							.addGap(26)
							.addComponent(lblIdentifiant_1, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
								.addComponent(deleteModule, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(addModule, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(nameModule, GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
								.addComponent(updateModule, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_panel_1.createSequentialGroup()
										.addGap(26)
										.addComponent(lblNom, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE))
									.addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
										.addGap(26)
										.addComponent(lblMasseHoraire, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)))
								.addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
									.addGap(26)
									.addComponent(lblModule, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)))))
					.addGap(12)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(selectModule, 0, 206, Short.MAX_VALUE)
						.addComponent(idCourse, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
						.addComponent(nameCourse, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
						.addComponent(hourlyMass, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
						.addComponent(addCourse, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
						.addComponent(updateCourse, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
						.addComponent(deleteCourse, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE))
					.addGap(73))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(176)
					.addComponent(lblIdentifiant)
					.addGap(19)
					.addComponent(lblLibell))
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(13)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
					.addGap(106)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(idModule, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(3)
							.addComponent(lblIdentifiant_1)))
					.addGap(13)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(nameModule, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(3)
							.addComponent(lblNom)))
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(17)
							.addComponent(lblMasseHoraire))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(13)
							.addComponent(addModule)))
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(17)
							.addComponent(lblModule))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(13)
							.addComponent(updateModule)))
					.addGap(13)
					.addComponent(deleteModule)
					.addGap(203))
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(173)
					.addComponent(idCourse, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(13)
					.addComponent(nameCourse, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(14)
					.addComponent(hourlyMass, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(16)
					.addComponent(selectModule, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(15)
					.addComponent(addCourse)
					.addGap(13)
					.addComponent(updateCourse)
					.addGap(13)
					.addComponent(deleteCourse)
					.addGap(128))
		);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)
		);
		panel.setLayout(gl_panel);
		panel_1.setLayout(gl_panel_1);
		frmManagermanageCourse.getContentPane().setLayout(groupLayout);
		
		// When the button delete course is pressed, we call the method delete from ModuleDAO to delete the module
		deleteModule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ModuleDAO().delete(new Module(Integer.parseInt(idModule.getText()), nameModule.getText()));
				tree.setModel(new DefaultTreeModel(findNextTreeNode("Modules")));
				selectModule.setModel(new DefaultComboBoxModel<Module>(initListModule().toArray(new Module[0])));
				initForms();
				updateCourse.setEnabled(false);
				deleteCourse.setEnabled(false);
			}
		});
		
		// When the button update course is pressed, we call the method update from ModuleDAO to update the module
		updateModule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ModuleDAO().update(new Module(Integer.parseInt(idModule.getText()), nameModule.getText()));
				tree.setModel(new DefaultTreeModel(findNextTreeNode("Modules")));
				selectModule.setModel(new DefaultComboBoxModel<Module>(initListModule().toArray(new Module[0])));
				updateCourse.setEnabled(false);
				deleteCourse.setEnabled(false);
			}
		});
		
		/*
		 *  When the button add module is pressed we have two cases, 
		 *  if newModule is false the user shall enter informations
		 *  if newModule is true we call the method add from ModuleDAO to add the module to the database
		 */
		addModule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!newModule) {
					idModule.setText("");
					nameModule.setText("");
					newModule = true;
					updateModule.setEnabled(false);
					deleteModule.setEnabled(false);
					updateCourse.setEnabled(false);
					deleteCourse.setEnabled(false);
				}
				else {
					try {
						new ModuleDAO().add(new Module(0, nameModule.getText()));
						newModule = false;
						tree.setModel(new DefaultTreeModel(findNextTreeNode("Modules")));
						selectModule.setModel(new DefaultComboBoxModel<Module>(initListModule().toArray(new Module[0])));
						initForms();
					}
					catch(NumberFormatException ee) {
						JOptionPane.showMessageDialog(frmManagermanageCourse, "You entered a string instead of a number");
					}
					catch(Exception ignore) {
					}
				}
			}
		});
		
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				initForms();
				String s = tree.getLastSelectedPathComponent().toString();
				if (s.contains("@")) {
					s = s.substring(0, s.indexOf('@'));
					if (s.startsWith("1")) {
						// we show the module
						newModule = false;
						ModuleDAO moduleDAO = new ModuleDAO();
						Module module = moduleDAO.get(Integer.parseInt(s));
						idModule.setText(String.valueOf(module.getId()));
						idModule.setEditable(false);
						nameModule.setText(module.getName());
						updateModule.setEnabled(true);
						deleteModule.setEnabled(true);
						updateCourse.setEnabled(false);
						deleteCourse.setEnabled(false);
					}
					else {
						// we show the course
						newCourse = false;
						CourseDAO coursDAO = new CourseDAO();
						Course course = coursDAO.get(Integer.parseInt(s));
						updateModule.setEnabled(false);
						deleteModule.setEnabled(false);
						updateCourse.setEnabled(true);
						deleteCourse.setEnabled(true);
						idCourse.setText(String.valueOf(course.getId()));
						idCourse.setEditable(false);
						nameCourse.setText(course.getName());
						hourlyMass.setText(String.valueOf(course.getHourlyMass()));
						for(int id = 0; id <= initListModule().size(); id++) {
							if(selectModule.getItemAt(id).getId() == course.getModule())
								selectModule.setSelectedIndex(id);
						}
					}
				}
			}
		});
		
		/*
		 *  When the button add course is pressed we have two cases, 
		 *  if newCourse is false the user shall enter informations
		 *  if newCourse is true we call the method add from CourseDAO to add the course to the database
		 */
		addCourse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!newCourse) {
					idCourse.setText("");
					nameCourse.setText("");
					hourlyMass.setText("");
					selectModule.setSelectedIndex(0);
					newCourse = true;
					updateModule.setEnabled(false);
					deleteModule.setEnabled(false);
					updateCourse.setEnabled(false);
					deleteCourse.setEnabled(false);
				}
				else {
					try{
						added = new CourseDAO().add(new Course(0, nameCourse.getText(), Integer.parseInt(hourlyMass.getText()), selectModule.getItemAt(selectModule.getSelectedIndex()).getId()));
						if(added == 0)
							JOptionPane.showMessageDialog(frmManagermanageCourse,"Course not added");
						else
							JOptionPane.showMessageDialog(frmManagermanageCourse,"Course successfully added");
						newCourse = false;
						tree.setModel(new DefaultTreeModel(findNextTreeNode("Modules")));
						initForms();
						selectModule.setModel(new DefaultComboBoxModel<Module>(initListModule().toArray(new Module[0])));
					}
					catch (NumberFormatException arg0){
						JOptionPane.showMessageDialog(frmManagermanageCourse, "Please enter a number");
					}
				}
			}
		});
		
		// When the button update course is pressed, we call the method update from CourseDAO to update the course
		updateCourse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CourseDAO().update(new Course(Integer.parseInt(idCourse.getText()), nameCourse.getText(), Integer.parseInt(hourlyMass.getText()), selectModule.getItemAt(selectModule.getSelectedIndex()).getId()));
				idCourse.setEditable(false);
				updateCourse.setEnabled(false);
				deleteCourse.setEnabled(false);
				newCourse = false;
				tree.setModel(new DefaultTreeModel(findNextTreeNode("Modules")));
				initForms();
				selectModule.setModel(new DefaultComboBoxModel<Module>(initListModule().toArray(new Module[0])));
			}
		});
		
		// When the button delete course is pressed, we call the method delete from CourseDAO to delete the course
		deleteCourse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CourseDAO().delete(new Course(Integer.parseInt(idCourse.getText()), nameCourse.getText(), Integer.parseInt(hourlyMass.getText()), selectModule.getItemAt(selectModule.getSelectedIndex()).getId()));
				idCourse.setEditable(false);
				updateCourse.setEnabled(false);
				deleteCourse.setEnabled(false);
				newCourse = false;
				tree.setModel(new DefaultTreeModel(findNextTreeNode("Modules")));
				initForms();
				selectModule.setModel(new DefaultComboBoxModel<Module>(initListModule().toArray(new Module[0])));
			}
		});
		
		frmManagermanageCourse.setVisible(true);
		
		
	}
	
	/**
	 * List modules and courses from the DB in the tree.
	 *
	 */
	private DefaultMutableTreeNode findNextTreeNode(String nodeName) {
		ModuleDAO moduleDAO = new ModuleDAO();
		CourseDAO coursDAO = new CourseDAO();
		DefaultMutableTreeNode node1 = new DefaultMutableTreeNode(nodeName);
		DefaultMutableTreeNode node2 = null;
		DefaultMutableTreeNode node3 = null;
		ArrayList<Module> modules = moduleDAO.getList();
		ArrayList<Course> courses = coursDAO.getList();
        if (modules != null) {
            for (Module s : modules) {
            	node2 = new DefaultMutableTreeNode(s.getId() + "@ " + s.getName());
            	for (Course course : courses) {
            		if(course.getModule() == s.getId()) {
            			node3 = new DefaultMutableTreeNode(course.getId() + "@ " + course.getName());
                		node2.add(node3);
            		}
            	}
				node1.add(node2);
            }
        }
		return node1;
	}
		
	boolean containsOnlyNumbers(String s) {
		return s.matches("[0-9]*");
	}
	
	/**
	 * Initialize the form for the user
	 */
	void initForms() {
		idModule.setText("");
		nameModule.setText("");
		idCourse.setText("");
		nameCourse.setText("");
		hourlyMass.setText("");
	}
	
	/**
	 * Get the list of module to return for the JComboBox
	 * @return the list of module
	 */
	ArrayList<Module> initListModule() {
		ArrayList<Module> list = new ModuleDAO().getList();
		return list;
	}
}
