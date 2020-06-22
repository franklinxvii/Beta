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

public class ManageTeacher {

	private JFrame frmManagermanageTeacher;
	private JTextField idTeacher;
	private JTextField firstName;
	private JTextField phoneNumber;
	private JPasswordField password;
	private JTextField lastName;
	private boolean newTeacher;
	private int returnValue;

	/**
	 * Create the application.
	 */
	public ManageTeacher() {
		frmManagermanageTeacher = new JFrame();
		frmManagermanageTeacher.setTitle("Manager|Manage teacher");
		frmManagermanageTeacher.setBounds(100, 100, 996, 747);
		frmManagermanageTeacher.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel panel = new JPanel();
		
		JPanel pantree = new JPanel();
		pantree.setLayout(new GridLayout(1, 0, 0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		pantree.add(scrollPane);
		
		JTree tree = new JTree();
		scrollPane.setViewportView(tree);
		tree.setModel(new DefaultTreeModel(findNextTreeNode("Teachers")));
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(ManageTeacher.class.getResource("/gui/ESIGELEC.png")));
		
		JLabel lblIdentifieant = new JLabel("Identifier");
		
		idTeacher = new JTextField();
		idTeacher.setEditable(false);
		idTeacher.setColumns(10);
		
		JLabel lblPrnom = new JLabel("First Name");
		
		firstName = new JTextField();
		firstName.setColumns(10);
		
		JLabel lblTlphone = new JLabel("Phone Number");
		
		phoneNumber = new JTextField();
		phoneNumber.setColumns(10);
		
		JLabel lblPasswrd = new JLabel("Password");
		
		password = new JPasswordField();
		password.setColumns(10);
		
		JLabel lblCours = new JLabel("Courses");
		
		JPanel panCours = new JPanel();
		panCours.setLayout(new BoxLayout(panCours, BoxLayout.Y_AXIS));
		GroupLayout groupLayout = new GroupLayout(frmManagermanageTeacher.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 978, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
		);
		
		lastName = new JTextField();
		lastName.setColumns(10);
		
		JLabel lblNom = new JLabel("Last Name");
		
		JButton addTeacher = new JButton("Add");
		
		JButton updateTeacher = new JButton("Update");
		updateTeacher.setEnabled(false);
		
		JButton deleteTeacher = new JButton("Delete");
		deleteTeacher.setEnabled(false);
	
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(pantree, GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblIdentifieant, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblTlphone)
								.addComponent(lblPasswrd)
								.addComponent(lblCours, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(lblNom, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(lblPrnom, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)))
							.addGap(42)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(37)
									.addComponent(label))
								.addComponent(idTeacher, GroupLayout.PREFERRED_SIZE, 307, GroupLayout.PREFERRED_SIZE)
								.addComponent(lastName, GroupLayout.PREFERRED_SIZE, 307, GroupLayout.PREFERRED_SIZE)
								.addComponent(firstName, GroupLayout.PREFERRED_SIZE, 307, GroupLayout.PREFERRED_SIZE)
								.addComponent(phoneNumber, GroupLayout.PREFERRED_SIZE, 307, GroupLayout.PREFERRED_SIZE)
								.addComponent(password, GroupLayout.PREFERRED_SIZE, 307, GroupLayout.PREFERRED_SIZE)
								.addComponent(panCours, GroupLayout.PREFERRED_SIZE, 307, GroupLayout.PREFERRED_SIZE))
							.addGap(57))
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(addTeacher, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(updateTeacher, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(deleteTeacher, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGap(152))))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(pantree, GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(96)
					.addComponent(lblIdentifieant)
					.addGap(23)
					.addComponent(lblNom)
					.addGap(24)
					.addComponent(lblPrnom)
					.addGap(24)
					.addComponent(lblTlphone)
					.addGap(24)
					.addComponent(lblPasswrd)
					.addGap(21)
					.addComponent(lblCours))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(13)
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
					.addGap(26)
					.addComponent(idTeacher, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(17)
					.addComponent(lastName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(firstName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(phoneNumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(password, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(panCours, GroupLayout.PREFERRED_SIZE, 284, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(addTeacher)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(updateTeacher)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(deleteTeacher)
					.addContainerGap(22, Short.MAX_VALUE))
		);
		
		
		panel.setLayout(gl_panel);
		frmManagermanageTeacher.getContentPane().setLayout(groupLayout);
		
		frmManagermanageTeacher.addWindowListener( new WindowAdapter(){
		    @Override
			public void windowClosed(WindowEvent e) {
		    	@SuppressWarnings("unused")
				ManagerMenu window = new ManagerMenu();
		    	frmManagermanageTeacher.dispose();
		    }
		});
		
		//We add the list of courses from database to the panel
		ArrayList<JCheckBox> listCourse = initListCourses(panCours);
		
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				initForms();
				initListCourses(panCours);
				String s = tree.getLastSelectedPathComponent().toString();
				if (s.contains("@")) {
					s = s.substring(0, s.indexOf('@'));
					// we show the teacher
					newTeacher = false;
					TeacherDAO teacherDAO = new TeacherDAO();
					Teacher teacher = teacherDAO.get(Integer.parseInt(s));
					teacher.setCourses(new CourseDAO().getListForTeacher(teacher.getId()));
					idTeacher.setText(String.valueOf(teacher.getId()));
					idTeacher.setEditable(false);
					password.setEditable(false);
					firstName.setText(teacher.getFirstName());
					lastName.setText(teacher.getLastName());
					phoneNumber.setText(teacher.getPhoneNumber());
					password.setText(teacher.getPassword());
					initListCourses(panCours, teacher.getCourses(), listCourse);
					updateTeacher.setEnabled(true);
					deleteTeacher.setEnabled(true);
				}
			}
		});
		
		/*
		 *  When the button add teacher is pressed we have two cases, 
		 *  if newTeacher is false the user shall enter informations
		 *  if newTeacher is true we call the method add from TeacherDAO to add the teacher to the database
		 */
		addTeacher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!newTeacher) {
					idTeacher.setText("");
					firstName.setText("");
					lastName.setText("");
					phoneNumber.setText("");
					password.setText("");
					password.setEditable(true);
					initListCourses(panCours, listCourse);
					updateTeacher.setEnabled(false);
					deleteTeacher.setEnabled(false);
					newTeacher = true;
				}
				else {
					try {
						ArrayList<Course> aListCourse = new ArrayList<>();
						ArrayList<Course> listCourses = new CourseDAO().getList();
						for(JCheckBox course : listCourse) {
							if(course.isSelected())
								for(Course aCourse : listCourses) {
									if(aCourse.getName().equals(course.getText()))
											aListCourse.add(aCourse);
											course.setSelected(false);
								}
						}				
					returnValue = new TeacherDAO().add(new Teacher(0, lastName.getText(), firstName.getText(), String.valueOf(password.getPassword()), phoneNumber.getText(), aListCourse));
					if(returnValue != 0) {
						JOptionPane.showMessageDialog(frmManagermanageTeacher, "Teacher added to database and associated to his courses");
					}
					else
						JOptionPane.showMessageDialog(frmManagermanageTeacher, "Teacher not added to database");
					idTeacher.setEditable(false);
					password.setEditable(false);
					updateTeacher.setEnabled(false);
					deleteTeacher.setEnabled(false);
					newTeacher = false;
					tree.setModel(new DefaultTreeModel(findNextTreeNode("Teachers")));
					}
					catch(NumberFormatException ee) {
						JOptionPane.showMessageDialog(frmManagermanageTeacher, "You entered a string instead of a number");
					}
				}
				
			}
		});
		
		// When the button update teacher is pressed, we call the method update from TeacherDAO to update the teacher
		updateTeacher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Course> aListCourse = new ArrayList<>();
				ArrayList<Course> listCourses = new CourseDAO().getList();
				for(JCheckBox course : listCourse) {
					if(course.isSelected())
						for(Course aCourse : listCourses) {
							if(aCourse.getName().equals(course.getText()))
									aListCourse.add(aCourse);
									course.setSelected(false);
						}
				}
				returnValue = new TeacherDAO().update(new Teacher(Integer.parseInt(idTeacher.getText()), lastName.getText(), firstName.getText(), String.valueOf(password.getPassword()), phoneNumber.getText(), aListCourse));
				if(returnValue != 0) {
					new TeacherDAO().delete(Integer.parseInt(idTeacher.getText()));
					new TeacherDAO().associateTeacherCourse(Integer.parseInt(idTeacher.getText()), aListCourse);
					JOptionPane.showMessageDialog(frmManagermanageTeacher, "Teacher's information updated");
				}
				else 
					JOptionPane.showMessageDialog(frmManagermanageTeacher, "Teacher's information not updated");
				updateTeacher.setEnabled(false);
				deleteTeacher.setEnabled(false);
				newTeacher = false;
				tree.setModel(new DefaultTreeModel(findNextTreeNode("Teachers")));
				initListCourses(panCours, listCourse);
			}
		});
		
		/* When the button delete teacher is pressed, we call the method delete from TeacherDAO to delete the teacher
		 * if the teacher doesn
		 */
		deleteTeacher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnValue = new TeacherDAO().delete(new Teacher(Integer.parseInt(idTeacher.getText()), lastName.getText(), firstName.getText(),  String.valueOf(password.getPassword()), phoneNumber.getText(), null));
				if(returnValue != 0) {
					new TeacherDAO().delete(Integer.parseInt(idTeacher.getText()));
					JOptionPane.showMessageDialog(frmManagermanageTeacher, "Teacher deleted from database.");
				}
				else
					JOptionPane.showMessageDialog(frmManagermanageTeacher, "Teacher not deleted to database");
				idTeacher.setEditable(false);
				password.setEditable(false);
				updateTeacher.setEnabled(false);
				deleteTeacher.setEnabled(false);
				newTeacher = false;
				tree.setModel(new DefaultTreeModel(findNextTreeNode("Teachers")));
				initListCourses(panCours, listCourse);
			}
		});
		
		frmManagermanageTeacher.setVisible(true);
	}
	
	/**
	 * List modules and courses from the DB in the tree.
	 *
	 *@param nodeName the name of the node
	 *@return the jtree node
	 */
	private DefaultMutableTreeNode findNextTreeNode(String nodeName) {
		TeacherDAO teacherDAO = new TeacherDAO();
		DefaultMutableTreeNode node1 = new DefaultMutableTreeNode(nodeName);
		DefaultMutableTreeNode node2 = null;
		ArrayList<Teacher> teachers = teacherDAO.getList();
        if (teachers != null) {
            for (Teacher s : teachers) {
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
		idTeacher.setText("");
		firstName.setText("");
		lastName.setText("");
		phoneNumber.setText("");
		password.setText("");
	}
	
	/**
	 * Return from the database the list of courses you can select in the panel in parameter
	 * @param panCours
	 * @return the list of courses
	 */
	ArrayList<JCheckBox> initListCourses(JPanel panCours) {
		panCours.removeAll();
		ArrayList<JCheckBox> listCourse = new ArrayList<>();
		ArrayList<Course> listCourses = new CourseDAO().getList();
		for(Course course: listCourses) {
			JCheckBox box = new JCheckBox(course.getName());
		    listCourse.add(box);
		    panCours.add(box);
		}
		return listCourse;
	}
	
	/**
	 * Reinitialize the list of courses to check after an add, update or delete
	 * @param panCours
	 * @param list
	 */
	void initListCourses(JPanel panCours, ArrayList<JCheckBox> list) {
		panCours.removeAll();
		for(JCheckBox course: list) {
		   	course.setSelected(false);
		    panCours.add(course);
		}
		panCours.repaint();
	}
	
	/**
	 * When a teacher is selected, show his list of courses
	 * @param panCours
	 * @param aList
	 * @param list
	 */
	void initListCourses(JPanel panCours, ArrayList<Course> aList, ArrayList<JCheckBox> list) {
		panCours.removeAll();
		if(aList.isEmpty()) {
			for(JCheckBox aCourse : list) {
				aCourse.setSelected(false);
				panCours.add(aCourse);
			}
		}
		else {
			for(JCheckBox aCourse : list) {
				aCourse.setSelected(false);
				panCours.add(aCourse);
			}
			for(JCheckBox aCourse : list) {
				for(Course another : aList) {
					if(aCourse.getText().equals(another.getName())) {
						aCourse.setSelected(true);
					}	
				}
				panCours.add(aCourse);
			}
		}
		panCours.repaint();
	}
}
