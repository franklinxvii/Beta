package gui;

import model.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.tree.*;
import dao.*;
import javax.swing.GroupLayout.*;

public class ManageStudent {

	private JFrame frmManagermanageStudent;
	private JTextField idStudent;
	private JTextField firstName;
	private JTextField email;
	private JPasswordField password;
	private JTextField lastName;
	private boolean newStudent;
	private int returnValue;

	/**
	 * Create the application.
	 */
	public ManageStudent() {
		frmManagermanageStudent = new JFrame();
		frmManagermanageStudent.setTitle("Manager|Manage student");
		frmManagermanageStudent.setBounds(100, 100, 996, 478);
		frmManagermanageStudent.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel panel = new JPanel();
		
		JPanel pantree = new JPanel();
		pantree.setLayout(new GridLayout(1, 0, 0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		pantree.add(scrollPane);
		
		JTree tree = new JTree();
		scrollPane.setViewportView(tree);
		tree.setModel(new DefaultTreeModel(findNextTreeNode("Students")));
		
		JLabel lbl = new JLabel("");
		lbl.setIcon(new ImageIcon(ManageStudent.class.getResource("/gui/ESIGELEC.png")));
		
		JLabel lblIdentifieant = new JLabel("Identifier");
		
		idStudent = new JTextField();
		idStudent.setEditable(false);
		idStudent.setColumns(10);
		
		JLabel lblPrnom = new JLabel("First Name");
		
		firstName = new JTextField();
		firstName.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email");
		
		email = new JTextField();
		email.setColumns(10);
		
		JLabel lblPasswrd = new JLabel("Password");
		
		password = new JPasswordField();
		password.setColumns(10);
		
		lastName = new JTextField();
		lastName.setColumns(10);
		
		JLabel lblNom = new JLabel("Last Name");
		
		JButton addStudent = new JButton("Add");
		
		JButton updateStudent = new JButton("Update");
		
		JButton deleteStudent = new JButton("Delete");
		
		frmManagermanageStudent.addWindowListener( new WindowAdapter(){
		    @Override
			public void windowClosed(WindowEvent e) {
		    	@SuppressWarnings("unused")
				ManagerMenu window = new ManagerMenu();
		    	frmManagermanageStudent.dispose();
		    }
		});
		
		JComboBox<String> faculty = new JComboBox<>();
		faculty.setMaximumRowCount(2);
		faculty.setModel(new DefaultComboBoxModel<String>(new String[] {"Classique", "Apprentissage"}));
		
		JLabel lblFaculty = new JLabel("Faculty");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(pantree, GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
					.addGap(44)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblIdentifieant)
						.addComponent(lblNom)
						.addComponent(lblPrnom)
						.addComponent(lblEmail)
						.addComponent(lblPasswrd)
						.addComponent(lblFaculty, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
					.addGap(42)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(37)
							.addComponent(lbl))
						.addComponent(idStudent, GroupLayout.PREFERRED_SIZE, 307, GroupLayout.PREFERRED_SIZE)
						.addComponent(lastName, GroupLayout.PREFERRED_SIZE, 307, GroupLayout.PREFERRED_SIZE)
						.addComponent(firstName, GroupLayout.PREFERRED_SIZE, 307, GroupLayout.PREFERRED_SIZE)
						.addComponent(email, GroupLayout.PREFERRED_SIZE, 307, GroupLayout.PREFERRED_SIZE)
						.addComponent(password, GroupLayout.PREFERRED_SIZE, 307, GroupLayout.PREFERRED_SIZE)
						.addComponent(faculty, GroupLayout.PREFERRED_SIZE, 307, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(126)
							.addComponent(addStudent, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(126)
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(updateStudent, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(deleteStudent, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE))))
					.addGap(57))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(96)
							.addComponent(lblIdentifieant)
							.addGap(20)
							.addComponent(lblNom)
							.addGap(22)
							.addComponent(lblPrnom)
							.addGap(22)
							.addComponent(lblEmail)
							.addGap(22)
							.addComponent(lblPasswrd)
							.addGap(19)
							.addComponent(lblFaculty))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(13)
							.addComponent(lbl, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
							.addGap(26)
							.addComponent(idStudent, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(17)
							.addComponent(lastName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(firstName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(email, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(password, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(12)
							.addComponent(faculty, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(12)
							.addComponent(addStudent)
							.addGap(12)
							.addComponent(updateStudent)
							.addGap(12)
							.addComponent(deleteStudent))
						.addComponent(pantree, GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE))
					.addGap(12))
		);
		panel.setLayout(gl_panel);
		GroupLayout groupLayout = new GroupLayout(frmManagermanageStudent.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE)
					.addGap(0))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
					.addGap(0))
		);
		frmManagermanageStudent.getContentPane().setLayout(groupLayout);
		
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				initForms();
				String s = tree.getLastSelectedPathComponent().toString();
				if (s.contains("@")) {
					s = s.substring(0, s.indexOf('@'));
					// affichage student
					newStudent = false;
					StudentDAO studentDAO = new StudentDAO();
					Student student = studentDAO.get(Integer.parseInt(s));
					idStudent.setText(String.valueOf(student.getId()));
					firstName.setText(student.getFirstName());
					lastName.setText(student.getLastName());
					email.setText(student.getEmail());
					password.setText(student.getPassword());
					password.setEditable(false);
					faculty.setSelectedItem(student.getFaculty());
				}
			}
		});
		
		/*
		 *  When the button add student is pressed we have two cases, 
		 *  if newStudent is false the user shall enter informations
		 *  if newStudent is true we call the method add from StudentDAO to add the student to the database
		 */
		addStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!newStudent) {
					idStudent.setText("");
					idStudent.setEditable(false);
					firstName.setText("");
					lastName.setText("");
					email.setText("");
					password.setText("");
					password.setEditable(true);
					faculty.setSelectedIndex(0);
					updateStudent.setEnabled(false);
					deleteStudent.setEnabled(false);
					newStudent = true;
				}
				else {				
					returnValue = new StudentDAO().add(new Student(0, lastName.getText(), firstName.getText(), String.valueOf(password.getPassword()), email.getText(), (String) faculty.getSelectedItem()));
					if(returnValue != 0) {
						JOptionPane.showMessageDialog(frmManagermanageStudent, "Student added to database");
					}
					else
						JOptionPane.showMessageDialog(frmManagermanageStudent, "Student not added to database");
					idStudent.setEditable(false);
					password.setEditable(false);
					updateStudent.setEnabled(true);
					deleteStudent.setEnabled(true);
					newStudent = false;
					tree.setModel(new DefaultTreeModel(findNextTreeNode("Students")));
					}
				}
		});
		
		// When the button update student is pressed, we call the method update from StudentDAO to update the student
		updateStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnValue = new StudentDAO().update(new Student(Integer.parseInt(idStudent.getText()), lastName.getText(), firstName.getText(), String.valueOf(password.getPassword()), email.getText(), (String) faculty.getSelectedItem()));
				if(returnValue != 0) {
					JOptionPane.showMessageDialog(frmManagermanageStudent, "Student's information updated");
				}
				else 
					JOptionPane.showMessageDialog(frmManagermanageStudent, "Student's information not updated");
				idStudent.setEditable(false);
				password.setEditable(false);
				updateStudent.setEnabled(true);
				deleteStudent.setEnabled(true);
				newStudent = false;
				initForms();
				faculty.setSelectedIndex(0);
				tree.setModel(new DefaultTreeModel(findNextTreeNode("Students")));
			}
		});
		
		/* 
		 * When the button delete student is pressed, we call the method delete from StudentDAO to delete the student
		 */
		
		deleteStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnValue = new StudentDAO().delete(new Student(Integer.parseInt(idStudent.getText()), lastName.getText(), firstName.getText(), String.valueOf(password.getPassword()), email.getText(), (String) faculty.getSelectedItem()));
				if(returnValue != 0) {
					JOptionPane.showMessageDialog(frmManagermanageStudent, "Student deleted from database.");
				}
				else
					JOptionPane.showMessageDialog(frmManagermanageStudent, "Student not deleted from database");
				idStudent.setEditable(false);
				password.setEditable(false);
				updateStudent.setEnabled(true);
				deleteStudent.setEnabled(true);
				newStudent = false;
				initForms();
				faculty.setSelectedIndex(0);
				tree.setModel(new DefaultTreeModel(findNextTreeNode("Students")));
			}
		});
		frmManagermanageStudent.setVisible(true);
	}
	
	/**
	 * List modules and courses from the DB in the tree.
	 *
	 *@param nodeName the name of the node
	 *@return the jtree node
	 */
	private DefaultMutableTreeNode findNextTreeNode(String nodeName) {
		StudentDAO studentDAO = new StudentDAO();
		DefaultMutableTreeNode node1 = new DefaultMutableTreeNode(nodeName);
		DefaultMutableTreeNode node2 = null;
		ArrayList<Student> students = studentDAO.getList();
        if (students != null) {
            for (Student s : students) {
            	node2 = new DefaultMutableTreeNode(s.getId() + "@ " + s.getFirstName() + " " + s.getLastName());
				node1.add(node2);
            }
        }
		return node1;
	}
	
	/**
	 * Reinitialize the JTextField for the user
	 */
	void initForms() {
		idStudent.setText("");
		firstName.setText("");
		lastName.setText("");
		email.setText("");
		password.setText("");
	}
	
	/**
	 * Return from the database the list of courses you can select in the panel in parameter
	 * @param panCours
	 * @return the list of courses
	 */
	ArrayList<JCheckBox> initListCourses(JPanel panCours) {
		ArrayList<JCheckBox> listCourse = new ArrayList<>();
		ArrayList<Course> listCourses = new CourseDAO().getList();
		for(Course course: listCourses) {
			JCheckBox box = new JCheckBox(course.getName());
		    listCourse.add(box);
		    panCours.add(box);
		}
		return listCourse;
	}
}
