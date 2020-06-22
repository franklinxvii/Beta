package gui;

import java.awt.event.*;

import java.util.*;

import javax.swing.*;
import javax.swing.tree.*;

import dao.*;
import model.*;
import model.Group;

import javax.swing.GroupLayout.*;

import java.awt.*;

public class ManageGroup {

	private JFrame frmManagermanageGroup;
	private boolean newGroup;
	private int returnValue;
	private JTextField idGroup;
	private JTextField maxCapacity;
	@SuppressWarnings("unused")
	private ArrayList<ButtonGroup> choiceList;
	private boolean checked = false;

	/**
	 * Create the application.
	 */
	public ManageGroup(){
		frmManagermanageGroup = new JFrame();
		frmManagermanageGroup.setTitle("Manager|Manage group");
		frmManagermanageGroup.setBounds(100, 100, 1099, 841);
		frmManagermanageGroup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel panel = new JPanel();
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(ManageGroup.class.getResource("/gui/ESIGELEC.png")));
		
		JLabel lblIdentifier = new JLabel("Identifier");
		
		JLabel lblStdts = new JLabel("Students");
		
		JButton addGroup = new JButton("Add");
		
		JButton updateGroup = new JButton("Update");
		updateGroup.setEnabled(false);
		
		JButton deleteGroup = new JButton("Delete");
		deleteGroup.setEnabled(false);
		
		ArrayList<ButtonGroup> choiceList = new ArrayList<>();
		
		ArrayList<JRadioButton> listChoices = new ArrayList<>();
		
		frmManagermanageGroup.addWindowListener( new WindowAdapter(){
		    @Override
			public void windowClosed(WindowEvent e) {
		    	@SuppressWarnings("unused")
				ManagerMenu window = new ManagerMenu();
		    	frmManagermanageGroup.dispose();
		    }
		});
		
		//We add the list of students from database to the panel
		ArrayList<JCheckBox> listStudent = new ArrayList<>();
		
		JScrollPane scrollPaneTree = new JScrollPane();
		
		JPanel pantree = new JPanel();
		scrollPaneTree.setViewportView(pantree);
		pantree.setBackground(Color.WHITE);
		pantree.setLayout(new BorderLayout(0, 0));
		
		JTree tree = new JTree();
		pantree.add(tree);
		tree.setModel(new DefaultTreeModel(findNextTreeNode("Groups")));
		
		idGroup = new JTextField();
		idGroup.setEditable(false);
		idGroup.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Max Capacity");
		
		maxCapacity = new JTextField();
		maxCapacity.setColumns(2);
		
		JScrollPane scrollPaneTeacher = new JScrollPane();
		
		JPanel panTeacher = new JPanel();
		scrollPaneTeacher.setViewportView(panTeacher);
		panTeacher.setBackground(Color.WHITE);
		
		initChoiceList(panTeacher, choiceList, listChoices);
		panTeacher.setLayout(new BoxLayout(panTeacher, BoxLayout.Y_AXIS));
		
		JScrollPane scrollPaneStudent = new JScrollPane();
		
		JPanel panStudents = new JPanel();
		scrollPaneStudent.setViewportView(panStudents);
		panStudents.setBackground(Color.WHITE);
		panStudents.setLayout(new BoxLayout(panStudents, BoxLayout.Y_AXIS));
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(scrollPaneTree, GroupLayout.PREFERRED_SIZE, 480, GroupLayout.PREFERRED_SIZE)
					.addGap(63)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblIdentifier, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblStdts, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
					.addGap(40)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPaneStudent, GroupLayout.PREFERRED_SIZE, 323, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(47)
							.addComponent(label))
						.addComponent(idGroup, GroupLayout.PREFERRED_SIZE, 325, GroupLayout.PREFERRED_SIZE)
						.addComponent(maxCapacity, GroupLayout.PREFERRED_SIZE, 325, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPaneTeacher, GroupLayout.PREFERRED_SIZE, 323, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(124)
							.addComponent(addGroup, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(124)
							.addComponent(updateGroup, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(124)
							.addComponent(deleteGroup, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE))))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPaneTree, GroupLayout.DEFAULT_SIZE, 794, Short.MAX_VALUE)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(96)
					.addComponent(lblIdentifier, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
					.addGap(16)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
					.addGap(19)
					.addComponent(lblStdts, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(13)
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
					.addGap(27)
					.addComponent(idGroup, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addComponent(maxCapacity, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
					.addGap(17)
					.addComponent(scrollPaneStudent, GroupLayout.PREFERRED_SIZE, 240, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(scrollPaneTeacher, GroupLayout.PREFERRED_SIZE, 245, GroupLayout.PREFERRED_SIZE)
					.addGap(13)
					.addComponent(addGroup)
					.addGap(13)
					.addComponent(updateGroup)
					.addGap(13)
					.addComponent(deleteGroup))
		);
		panel.setLayout(gl_panel);
		GroupLayout groupLayout = new GroupLayout(frmManagermanageGroup.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 1081, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		);
		frmManagermanageGroup.getContentPane().setLayout(groupLayout);
		
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				newGroup = false;
				initForms();
				String s = tree.getLastSelectedPathComponent().toString();
				// affichage group
				GroupDAO groupDAO = new GroupDAO();
				Group group = groupDAO.get(Integer.parseInt(s));
				group.setStudentList(new StudentDAO().getListForGroup(group.getId()));
				initListStudents(panStudents, group.getId(), listStudent);
				initChoiceList(panTeacher, group.getId(), choiceList, listChoices);
				idGroup.setText(String.valueOf(group.getId()));
				idGroup.setEditable(false);
				maxCapacity.setText(String.valueOf(group.getMaxCapacity()));
				addGroup.setEnabled(true);
				updateGroup.setEnabled(true);
				deleteGroup.setEnabled(true);
			}
		});
		
		/*
		 *  When the button add group is pressed we have two cases, 
		 *  if newGroup is false the user shall enter informations
		 *  if newGroup is true we call the method add from GroupDAO to add the group to the database
		 */
		addGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!newGroup) {
					initForms();
					initListStudents(panStudents, listStudent);
					for(ButtonGroup group : choiceList)
						group.clearSelection();
					updateGroup.setEnabled(false);
					deleteGroup.setEnabled(false);
					newGroup = true;
				}
				else {
					try {
						ArrayList<Student> aListStudent = new ArrayList<>();
						ArrayList<Student> listStudents = new StudentDAO().getListOfStudentsWithoutGroup();
						for(JCheckBox student : listStudent) {
							if(student.isSelected()) {
								for(Student aStudent : listStudents) {
									if((aStudent.getLastName() + " " + aStudent.getFirstName()).equals(student.getText())) {
											aListStudent.add(aStudent);
											student.setSelected(false);
									}
								}
							}
						}
						ArrayList<Course> listCourse= new CourseDAO().getList();
						for (Course course : listCourse) {
							course.setList(new TeacherDAO().getListForCourse(course.getId()));
						}
						ArrayList<Integer> idTeacher = new ArrayList<>();
						ArrayList<Integer> idCourse = new ArrayList<>();
						for(int i = 0; i < choiceList.size(); i++) {
							idCourse.add(listCourse.get(i).getId());
							if(choiceList.get(i).getSelection() != null) {
								for(Teacher teacher : listCourse.get(i).getList()){
									if((teacher.getLastName() + " " + teacher.getFirstName()).equals(choiceList.get(i).getSelection().getActionCommand())){
										idTeacher.add(teacher.getId());
									}
								}
							}
							else {
								idTeacher.add(0);
							}
						}
						if(aListStudent.size() <= Integer.parseInt(maxCapacity.getText()))	
							returnValue = new GroupDAO().add(new Group(0, Integer.parseInt(maxCapacity.getText()), aListStudent), idCourse, idTeacher);
						else
							JOptionPane.showMessageDialog(null, "Group capacity exceeded. Reduce students or increase max capacity.");
						if(returnValue != 0)
							JOptionPane.showMessageDialog(frmManagermanageGroup, "Group added to database and associated to his students");
						else
							JOptionPane.showMessageDialog(frmManagermanageGroup, "Group not added to database");
						initForms();
						for(ButtonGroup group : choiceList)
							group.clearSelection();
						addGroup.setEnabled(true);
						updateGroup.setEnabled(false);
						deleteGroup.setEnabled(false);
						newGroup = false;
						tree.setModel(new DefaultTreeModel(findNextTreeNode("Groups")));
					}
					catch(NumberFormatException ee) {
						JOptionPane.showMessageDialog(frmManagermanageGroup, "You entered a string instead of a number");
					}
				}
				
			}
		});
		
		// When the button update group is pressed, we call the method update from GroupDAO to update the group
		updateGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Student> aListStudent = new ArrayList<>();
				ArrayList<Student> listStudents = new StudentDAO().getList();
				for(int i = 0; i < listStudent.size(); i++) {
					if(listStudent.get(i).isSelected()) {
						aListStudent.add(listStudents.get(i));
						listStudent.get(i).setSelected(false);
					}
				}
				ArrayList<Course> listCourse= new CourseDAO().getList();
				for (Course course : listCourse) {
					course.setList(new TeacherDAO().getListForCourse(course.getId()));
				}
				ArrayList<Integer> idTeacher = new ArrayList<>();
				ArrayList<Integer> idCourse = new ArrayList<>();
				for(int i = 0; i < choiceList.size(); i++) {
					idCourse.add(listCourse.get(i).getId());
					if(choiceList.get(i).getSelection() != null) {
						for(Teacher teacher : listCourse.get(i).getList()){
							if((teacher.getLastName() + " " + teacher.getFirstName()).equals(choiceList.get(i).getSelection().getActionCommand())){
								idTeacher.add(teacher.getId());
							}
						}
					}
					else {
						idTeacher.add(0);
					}
				}
				if(aListStudent.size() <= Integer.parseInt(maxCapacity.getText()))
					returnValue = new GroupDAO().update(new Group(Integer.parseInt(idGroup.getText()), Integer.parseInt(maxCapacity.getText()), aListStudent), idCourse, idTeacher);
				else
					JOptionPane.showMessageDialog(null, "Group capacity exceeded. Reduce students or increase max capacity.");
				if(returnValue != 0) {
					JOptionPane.showMessageDialog(frmManagermanageGroup, "Group's information updated");
				}
				else 
					JOptionPane.showMessageDialog(frmManagermanageGroup, "Group's information not updated");
				addGroup.setEnabled(true);
				updateGroup.setEnabled(false);
				deleteGroup.setEnabled(false);
				initForms();
				for(ButtonGroup group : choiceList)
					group.clearSelection();
				tree.setModel(new DefaultTreeModel(findNextTreeNode("Groups")));
			}
		});
		
		/* When the button delete group is pressed, we call the method delete from GroupDAO to delete the group
		 * if the group doesn
		 */
		deleteGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnValue = new GroupDAO().delete(Integer.parseInt(idGroup.getText()));
				if(returnValue != 0) {
					JOptionPane.showMessageDialog(frmManagermanageGroup, "Group deleted from database.");
				}
				else
					JOptionPane.showMessageDialog(frmManagermanageGroup, "Group not deleted from database");
				addGroup.setEnabled(true);
				updateGroup.setEnabled(false);
				deleteGroup.setEnabled(false);
				newGroup = false;
				for(ButtonGroup group : choiceList)
					group.clearSelection();
				initForms();
				tree.setModel(new DefaultTreeModel(findNextTreeNode("Groups")));
			}
		});
		
		frmManagermanageGroup.setVisible(true);
	}
	
	/**
	 * List modules and students from the DB in the tree.
	 *
	 *@param nodeName the name of the node
	 *@return the jtree node
	 */
	private DefaultMutableTreeNode findNextTreeNode(String nodeName) {
		GroupDAO groupDAO = new GroupDAO();
		DefaultMutableTreeNode node1 = new DefaultMutableTreeNode(nodeName);
		DefaultMutableTreeNode node2 = null;
		ArrayList<Group> groups = groupDAO.getList();
        if (groups != null) {
            for (Group s : groups) {
            	node2 = new DefaultMutableTreeNode(s.getId());
				node1.add(node2);
            }
        }
		return node1;
	}
	

	/**
	 * Reinitialize the JTextField for the user
	 */
	void initForms() {
		idGroup.setText("");
		maxCapacity.setText("");
	}
	
	/**
	 * Return from the database the list of students you can select in the panel in parameter
	 * @param panStudents
	 * @return the list of students
	 */
	ArrayList<JCheckBox> initListStudents(JPanel panStudents, int id, ArrayList<JCheckBox> listStudent) {
		panStudents.removeAll();
		listStudent.clear();
		ArrayList<Student> listStudents = new StudentDAO().getListForGroupEdit(id);
		for(Student student: listStudents) {
			if(student.getGroup() != 0) {
				JCheckBox box = new JCheckBox(student.getLastName() + " " + student.getFirstName());
				box.setSelected(true);
				box.setBackground(Color.WHITE);
				listStudent.add(box);
			    panStudents.add(box);
			}
			else {
				JCheckBox box = new JCheckBox(student.getLastName() + " " + student.getFirstName());
				box.setSelected(false);
				box.setBackground(Color.WHITE);
				listStudent.add(box);
			    panStudents.add(box);
			}
		}
	    panStudents.revalidate();
		panStudents.repaint();
		return listStudent;
	}
	
	/**
	 * Return from the database the list of students without group
	 * @param panStudents
	 * @return the list of students
	 */
	void initListStudents(JPanel panStudents, ArrayList<JCheckBox> listStudent) {
		panStudents.removeAll();
		listStudent.clear();
		ArrayList<Student> listStudents = new StudentDAO().getListOfStudentsWithoutGroup();
		for(Student student: listStudents) {
			JCheckBox box = new JCheckBox(student.getLastName() + " " + student.getFirstName());
			box.setBackground(Color.WHITE);
			listStudent.add(box);
			panStudents.add(box);
		}
	    panStudents.revalidate();
		panStudents.repaint();
	}
	
	void initChoiceList(JPanel panTeacher, ArrayList<ButtonGroup> list, ArrayList<JRadioButton> aList) {
		ArrayList<Course> listCourse= new CourseDAO().getList();
		for (Course course : listCourse) {
			course.setList(new TeacherDAO().getListForCourse(course.getId()));
		}
		for (Course course : listCourse) {
			JLabel label = new JLabel(course.getName());
			panTeacher.add(label);
			ButtonGroup teachers = new ButtonGroup();
			for (Teacher teacher : course.getList()) {
				JRadioButton button = new JRadioButton(teacher.getLastName() + " " + teacher.getFirstName());
				button.setBackground(Color.WHITE);
				button.setActionCommand(teacher.getLastName() + " " + teacher.getFirstName());
				teachers.add(button);
				aList.add(button);
				panTeacher.add(button);
			}
			list.add(teachers);
		}
	}
	
	void initChoiceList(JPanel panTeacher, int id, ArrayList<ButtonGroup> list, ArrayList<JRadioButton> aList) {
		ArrayList<Integer> count = new ArrayList<>();
		for(ButtonGroup button : list) {
			button.clearSelection();
			count.add(button.getButtonCount());
		}
		ArrayList<JRadioButton> listChoices;
		ArrayList<Teacher> listTeacher= new TeacherDAO().getListForGroup(id);
		int i = 0, j = 0, k = 0;
		if(!listTeacher.isEmpty()) {
			while(i < listTeacher.size()) {
				if(list.get(j).getButtonCount() != 0) {
					if(j == 0) {
						listChoices = new ArrayList<JRadioButton>(aList.subList(0, list.get(j).getButtonCount()));
					}
					else {
						listChoices = new ArrayList<JRadioButton>(aList.subList(k, k + list.get(j).getButtonCount()));
					}
					for(JRadioButton button: listChoices) {
						if(button.getActionCommand().equals(listTeacher.get(i).getLastName() + " " + listTeacher.get(i).getFirstName())){
							button.setSelected(true);
							checked = true;
						}
					}
					if(checked) {
						i++;
						checked = false;
					}
					k += list.get(j).getButtonCount();
				}
				j++;	
			}
		}
		panTeacher.revalidate();
		panTeacher.repaint();
	}
}	

