package gui;

import java.awt.*;

import javax.swing.*;
import dao.*;
import model.*;
import javax.swing.tree.*;
import java.awt.event.*;
import java.time.DayOfWeek;
import java.util.*;

public class ManageLessons {

	private JFrame frmManagermanageLessons;
	private JTextField nameTeacher;
	private JSplitPane splitPane;
	private JScrollPane scrollPane;
	private JTree tree;
	private JPanel panel;
	private JComboBox<Group> selectGroup;
	private JComboBox<Course> selectCourse;
	private JButton validateSelection;
	private JComboBox<String> startHour;
	private JComboBox<String> startMinute;
	private JComboBox<String> endHour;
	private JComboBox<String> endMinute;
	private JButton addLesson;
	private JButton updateLesson;
	private JButton deleteLesson;
	private JComboBox<String> day;
	private JComboBox<String> selectType;
	private Lesson lesson;
	private boolean newLesson;


	/**
	 * Create the application.
	 */
	public ManageLessons(){
		frmManagermanageLessons = new JFrame();
		frmManagermanageLessons.setTitle("Manager|Manage lessons");
		frmManagermanageLessons.setBounds(100, 100, 760, 596);
		frmManagermanageLessons.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		frmManagermanageLessons.addWindowListener( new WindowAdapter(){
		    @Override
			public void windowClosed(WindowEvent e) {
		    	@SuppressWarnings("unused")
				ManagerMenu window = new ManagerMenu();
		    	frmManagermanageLessons.dispose();
		    }
		});
		
		splitPane = new JSplitPane();
		splitPane.setContinuousLayout(true);
		splitPane.setResizeWeight(0.2);
		frmManagermanageLessons.getContentPane().add(splitPane, BorderLayout.CENTER);
		
		scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);
		
		tree = new JTree();
		scrollPane.setViewportView(tree);
		tree.setModel(new DefaultTreeModel(findNextTreeNode("Lessons")));
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				newLesson = false;
				initForms();
				String s = tree.getLastSelectedPathComponent().toString();
				s = s.substring(0, s.indexOf("@"));
				// we show the lesson
				lesson = new LessonDAO().get(Integer.parseInt(s));
				int id_group = new GroupDAO().getGroupId(Integer.parseInt(s));
				int id_course = new CourseDAO().getCourseId(Integer.parseInt(s));
				for(int i = 0; i < selectGroup.getItemCount(); i++) {
					if(selectGroup.getItemAt(i).getId() == id_group)
						selectGroup.setSelectedIndex(i);
				}
				for(int i = 0; i < selectCourse.getItemCount(); i++) {
					if(selectCourse.getItemAt(i).getId() == id_course)
						selectCourse.setSelectedIndex(i);
				}
				nameTeacher.setText(new TeacherDAO().get(selectGroup.getItemAt(selectGroup.getSelectedIndex()), selectCourse.getItemAt(selectCourse.getSelectedIndex())).getFirstName() + " " + new TeacherDAO().get(selectGroup.getItemAt(selectGroup.getSelectedIndex()), selectCourse.getItemAt(selectCourse.getSelectedIndex())).getLastName() );
				selectType.setSelectedItem(lesson.getType());
				day.setSelectedIndex(lesson.getDay()-1);
				for(int i = 0; i < startHour.getItemCount(); i++) {
					if(startHour.getItemAt(i).equals(String.valueOf(lesson.getStartTime()).substring(0, String.valueOf(lesson.getStartTime()).indexOf("."))))
						startHour.setSelectedIndex(i);
				}
				for(int i = 0; i < startMinute.getItemCount(); i++) {
					if(startMinute.getItemAt(i).equals(String.valueOf(lesson.getStartTime()).substring(String.valueOf(lesson.getStartTime()).indexOf(".")+1).concat("0"))
							|| startMinute.getItemAt(i).equals(String.valueOf(lesson.getStartTime()).substring(String.valueOf(lesson.getStartTime()).indexOf(".")+1)))
						startMinute.setSelectedIndex(i);
				}
				for(int i = 0; i < endHour.getItemCount(); i++) {
					if(endHour.getItemAt(i).equals(String.valueOf(lesson.getEndTime()).substring(0, String.valueOf(lesson.getEndTime()).indexOf("."))))
						endHour.setSelectedIndex(i);
				}
				for(int i = 0; i < endMinute.getItemCount(); i++) {
					if(endMinute.getItemAt(i).equals(String.valueOf(lesson.getEndTime()).substring(String.valueOf(lesson.getEndTime()).indexOf(".")+1).concat("0"))
							|| endMinute.getItemAt(i).equals(String.valueOf(lesson.getEndTime()).substring(String.valueOf(lesson.getEndTime()).indexOf(".")+1)))
						endMinute.setSelectedIndex(i);
				}
				updateLesson.setEnabled(true);
				deleteLesson.setEnabled(true);
			}
		});
		
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		splitPane.setRightComponent(panel);
		panel.setLayout(null);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setBounds(197, 13, 145, 81);
		lblLogo.setIcon(new ImageIcon(ManageLessons.class.getResource("/gui/ESIGELEC.png")));
		panel.add(lblLogo);
		
		JLabel lblGroup = new JLabel("Group");
		lblGroup.setBounds(95, 159, 34, 16);
		panel.add(lblGroup);
		
		selectGroup = new JComboBox<>();
		selectGroup.setModel(new DefaultComboBoxModel<Group>(new GroupDAO().getList().toArray(new Group[0])));
		selectGroup.setBounds(209, 156, 294, 22);
		panel.add(selectGroup);
		
		JLabel lblCourse = new JLabel("Course");
		lblCourse.setBounds(95, 194, 52, 16);
		panel.add(lblCourse);
		
		selectCourse = new JComboBox<Course>();
		selectCourse.setModel(new DefaultComboBoxModel<Course>(new CourseDAO().getList().toArray(new Course[0])));
		selectCourse.setBounds(209, 191, 294, 22);
		panel.add(selectCourse);
		
		JLabel lblTeacher = new JLabel("Teacher");
		lblTeacher.setBounds(95, 267, 56, 16);
		panel.add(lblTeacher);
		
		nameTeacher = new JTextField();
		nameTeacher.setEditable(false);
		nameTeacher.setBounds(209, 264, 294, 22);
		panel.add(nameTeacher);
		nameTeacher.setColumns(10);
		
		validateSelection = new JButton("Validate");
		validateSelection.setEnabled(false);
		validateSelection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nameTeacher.setText(new TeacherDAO().get(selectGroup.getItemAt(selectGroup.getSelectedIndex()), selectCourse.getItemAt(selectCourse.getSelectedIndex())).getFirstName() + " " + new TeacherDAO().get(selectGroup.getItemAt(selectGroup.getSelectedIndex()), selectCourse.getItemAt(selectCourse.getSelectedIndex())).getLastName() );
			}
		});
		validateSelection.setBounds(298, 226, 97, 25);
		panel.add(validateSelection);
		
		JLabel lblStartTime = new JLabel("Start time");
		lblStartTime.setBounds(95, 372, 65, 16);
		panel.add(lblStartTime);
		
		startHour = new JComboBox<>();
		startHour.setModel(new DefaultComboBoxModel<String>(new String[] {"8", "9", "10", "11", "13", "14", "15", "16", "17", "18"}));
		startHour.setBounds(209, 369, 51, 22);
		panel.add(startHour);
		
		JLabel lblHour = new JLabel("H");
		lblHour.setBounds(272, 372, 13, 16);
		panel.add(lblHour);
		
		startMinute = new JComboBox<>();
		startMinute.setModel(new DefaultComboBoxModel<String>(new String[] {"00", "15", "30", "45"}));
		startMinute.setBounds(298, 369, 51, 22);
		panel.add(startMinute);
		
		JLabel lblMin = new JLabel("MIN");
		lblMin.setBounds(361, 372, 34, 16);
		panel.add(lblMin);
		
		JLabel lblEndTime = new JLabel("End time");
		lblEndTime.setBounds(95, 404, 65, 16);
		panel.add(lblEndTime);
		
		endHour = new JComboBox<>();
		endHour.setModel(new DefaultComboBoxModel<String>(new String[] {"9", "10", "11", "12", "14", "15", "16", "17", "18", "19"}));
		endHour.setBounds(209, 401, 52, 22);
		panel.add(endHour);
		
		JLabel lblHour_1 = new JLabel("H");
		lblHour_1.setBounds(272, 404, 13, 16);
		panel.add(lblHour_1);
		
		endMinute = new JComboBox<>();
		endMinute.setModel(new DefaultComboBoxModel<String>(new String[] {"00", "15", "30", "45"}));
		endMinute.setBounds(298, 401, 51, 22);
		panel.add(endMinute);
		
		JLabel lblMin_1 = new JLabel("MIN");
		lblMin_1.setBounds(361, 404, 34, 16);
		panel.add(lblMin_1);
		
		addLesson = new JButton("Add");
		addLesson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!newLesson) {
					initForms();
					validateSelection.setEnabled(true);
					updateLesson.setEnabled(false);
					deleteLesson.setEnabled(false);
					newLesson = true;
				}
				else {
					int totalMass = selectCourse.getItemAt(selectCourse.getSelectedIndex()).getHourlyMass() * 60;
					int timeLeft = timeLeft(selectGroup.getItemAt(selectGroup.getSelectedIndex()), selectCourse.getItemAt(selectCourse.getSelectedIndex()));
					int totalLesson = ((Integer.parseInt(endHour.getItemAt(endHour.getSelectedIndex())) - Integer.parseInt(startHour.getItemAt(startHour.getSelectedIndex())))*60) + Integer.parseInt(endMinute.getItemAt(endMinute.getSelectedIndex())) - Integer.parseInt(startMinute.getItemAt(startMinute.getSelectedIndex()));
					if(!selectType.getItemAt(selectType.getSelectedIndex()).equals("Exam")) {
						totalLesson*= 9;
					}
					if((totalMass - totalLesson - timeLeft) >= 0) {
						if(!(new LessonDAO().interferes(selectGroup.getItemAt(selectGroup.getSelectedIndex()), selectCourse.getItemAt(selectCourse.getSelectedIndex()), new Lesson(day.getSelectedIndex()+1, selectType.getItemAt(selectType.getSelectedIndex()), Double.valueOf(startHour.getItemAt(startHour.getSelectedIndex()).concat(".").concat(startMinute.getItemAt(startMinute.getSelectedIndex()))), Double.valueOf(endHour.getItemAt(endHour.getSelectedIndex()).concat(".").concat(endMinute.getItemAt(endMinute.getSelectedIndex())))), 0))) {
							new LessonDAO().add(selectGroup.getItemAt(selectGroup.getSelectedIndex()), selectCourse.getItemAt(selectCourse.getSelectedIndex()), new Lesson(day.getSelectedIndex()+1, selectType.getItemAt(selectType.getSelectedIndex()), Double.valueOf(startHour.getItemAt(startHour.getSelectedIndex()).concat(".").concat(startMinute.getItemAt(startMinute.getSelectedIndex()))), Double.valueOf(endHour.getItemAt(endHour.getSelectedIndex()).concat(".").concat(endMinute.getItemAt(endMinute.getSelectedIndex())))));
						}
						else {
							JOptionPane.showMessageDialog(null, "This time is already occupied");
						}
						initForms();
						newLesson = false;
						tree.setModel(new DefaultTreeModel(findNextTreeNode("Lessons")));
					}
					else if(selectType.getItemAt(selectType.getSelectedIndex()).equals("Exam"))
						JOptionPane.showMessageDialog(frmManagermanageLessons, "You don't have enough time for your exam");
					else
						JOptionPane.showMessageDialog(frmManagermanageLessons, "You don't have enough time for this lesson on 9 weeks");
				}
			}
		});
		addLesson.setBounds(267, 433, 97, 25);
		panel.add(addLesson);
		
		updateLesson = new JButton("Update");
		updateLesson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int totalMass = selectCourse.getItemAt(selectCourse.getSelectedIndex()).getHourlyMass() * 60;
				int timeLeft = timeLeftUpdated(selectGroup.getItemAt(selectGroup.getSelectedIndex()), selectCourse.getItemAt(selectCourse.getSelectedIndex()), lesson);
				int totalLesson = ((Integer.parseInt(endHour.getItemAt(endHour.getSelectedIndex())) - Integer.parseInt(startHour.getItemAt(startHour.getSelectedIndex())))*60) + Integer.parseInt(endMinute.getItemAt(endMinute.getSelectedIndex())) - Integer.parseInt(startMinute.getItemAt(startMinute.getSelectedIndex()));
				if(!selectType.getItemAt(selectType.getSelectedIndex()).equals("Exam")) {
					totalLesson*= 9;
				}
				if((totalMass - totalLesson - timeLeft) >= 0) {
					if(!(new LessonDAO().interferes(selectGroup.getItemAt(selectGroup.getSelectedIndex()), selectCourse.getItemAt(selectCourse.getSelectedIndex()), new Lesson(day.getSelectedIndex()+1, selectType.getItemAt(selectType.getSelectedIndex()), Double.valueOf(startHour.getItemAt(startHour.getSelectedIndex()).concat(".").concat(startMinute.getItemAt(startMinute.getSelectedIndex()))), Double.valueOf(endHour.getItemAt(endHour.getSelectedIndex()).concat(".").concat(endMinute.getItemAt(endMinute.getSelectedIndex())))), lesson.getId()))) {
						new LessonDAO().update(selectGroup.getItemAt(selectGroup.getSelectedIndex()), selectCourse.getItemAt(selectCourse.getSelectedIndex()), lesson, new Lesson(day.getSelectedIndex()+1, selectType.getItemAt(selectType.getSelectedIndex()), Double.valueOf(startHour.getItemAt(startHour.getSelectedIndex()).concat(".").concat(startMinute.getItemAt(startMinute.getSelectedIndex()))), Double.valueOf(endHour.getItemAt(endHour.getSelectedIndex()).concat(".").concat(endMinute.getItemAt(endMinute.getSelectedIndex())))));
					}
					else {
						JOptionPane.showMessageDialog(null, "This time is already occupied");
					}
					initForms();
					tree.setModel(new DefaultTreeModel(findNextTreeNode("Lessons")));
				}
				else if(selectType.getItemAt(selectType.getSelectedIndex()).equals("Exam"))
					JOptionPane.showMessageDialog(frmManagermanageLessons, "You don't have enough time for your exam");
				else
					JOptionPane.showMessageDialog(frmManagermanageLessons, "You don't have enough time for this lesson on 9 weeks");
			}
		});
		updateLesson.setEnabled(false);
		updateLesson.setBounds(267, 471, 97, 25);
		panel.add(updateLesson);
		
		deleteLesson = new JButton("Delete");
		deleteLesson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new LessonDAO().delete(lesson);
				tree.setModel(new DefaultTreeModel(findNextTreeNode("Lessons")));
			}
		});
		deleteLesson.setEnabled(false);
		deleteLesson.setBounds(267, 509, 97, 25);
		panel.add(deleteLesson);
		
		JLabel lblDay = new JLabel("Day");
		lblDay.setBounds(95, 337, 56, 16);
		panel.add(lblDay);
		
		day = new JComboBox<String>();
		day.setModel(new DefaultComboBoxModel<String>(new String[] {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY"}));
		day.setBounds(209, 334, 116, 22);
		panel.add(day);
		
		JLabel lblType = new JLabel("Type");
		lblType.setBounds(95, 302, 52, 16);
		panel.add(lblType);
		
		selectType = new JComboBox<String>();
		selectType.setModel(new DefaultComboBoxModel<String>(new String[] {"Amphi", "TD", "TP", "Exam"}));
		selectType.setBounds(209, 299, 294, 22);
		panel.add(selectType);
		frmManagermanageLessons.setVisible(true);
	}
	
	/**
	 * List modules and courses from the DB in the tree.
	 *
	 */
	private DefaultMutableTreeNode findNextTreeNode(String nodeName) {
		DefaultMutableTreeNode node1 = new DefaultMutableTreeNode(nodeName);
		DefaultMutableTreeNode node2 = null;
		DefaultMutableTreeNode node3 = null;
		DefaultMutableTreeNode node4 = null;
		ArrayList<Group> groups = new GroupDAO().getList();
		ArrayList<Course> courses = new CourseDAO().getList();
        if (groups != null) {
            for (Group g : groups) {
            	node2 = new DefaultMutableTreeNode(g.getId());
            	if (courses != null) {
            		for (Course c : courses) {
            			node3 = new DefaultMutableTreeNode(c.getName());
                		ArrayList<Lesson> lessons = new LessonDAO().getList(g.getId(), c.getId());
                		if (lessons != null) {
                    		for (Lesson l : lessons) {
                    			node4 = new DefaultMutableTreeNode(l.getId() + "@" + DayOfWeek.of(l.getDay()) 
                    			+ "|" + String.valueOf(l.getStartTime()).substring(0, String.valueOf(l.getStartTime()).indexOf(".")) + "H" + String.valueOf(l.getStartTime()).substring(String.valueOf(l.getStartTime()).indexOf(".") + 1)
                    			+ "-" + String.valueOf(l.getEndTime()).substring(0, String.valueOf(l.getEndTime()).indexOf(".")) + "H" + String.valueOf(l.getEndTime()).substring(String.valueOf(l.getEndTime()).indexOf(".") + 1));
                       			node3.add(node4);
                        	}
                		}
                		node2.add(node3);
                	}
    				node1.add(node2);
            	}
            }
        }
		return node1;
	}
	
	void initForms() {
		selectType.setSelectedIndex(0);
		selectCourse.setSelectedIndex(0);
		selectGroup.setSelectedIndex(0);
		nameTeacher.setText("");
		startHour.setSelectedIndex(0);
		startMinute.setSelectedIndex(0);
		endHour.setSelectedIndex(0);
		endMinute.setSelectedIndex(0);
		day.setSelectedIndex(0);
	}
	
	private int timeLeft(Group group, Course course) {
		
		ArrayList<Lesson> list = new LessonDAO().getList(group.getId(), course.getId());
		double n = 0;
		int totalMin = 0;
		for(int i = 0; i < list.size(); i++) {
			double totalPerLesson = 0;
			n = list.get(i).getEndTime()-list.get(i).getStartTime();
			int index = String.valueOf(n).indexOf(".");
			Double ent = Double.parseDouble(String.valueOf(n).substring(0, index));
			Double dec = n - Double.parseDouble(String.valueOf(n).substring(0, index));
			if((dec*100) > 60 ) {
				n-=0.40;
			}
			dec = n - Double.parseDouble(String.valueOf(n).substring(0, index));
			totalPerLesson = (ent*60)+(dec*100);
			if (!list.get(i).getType().equals("Exam")) {
				totalPerLesson*=9;
			}
			totalMin += (int)totalPerLesson;
		}
		return totalMin;
	}
	
	private int timeLeftUpdated(Group group, Course course, Lesson lesson) {
		ArrayList<Lesson> list = new LessonDAO().getList(group.getId(), course.getId());
		double n = 0;
		int totalMin = 0;
		for(int i = 0; i < list.size(); i++) {
			double totalPerLesson = 0;
			n = list.get(i).getEndTime()-list.get(i).getStartTime();
			int index = String.valueOf(n).indexOf(".");
			Double ent = Double.parseDouble(String.valueOf(n).substring(0, index));
			Double dec = n - Double.parseDouble(String.valueOf(n).substring(0, index));
			if((dec*100) > 60 ) {
				n-=0.40;
			}
			dec = n - Double.parseDouble(String.valueOf(n).substring(0, index));
			totalPerLesson = (ent*60)+(dec*100);
			if (!list.get(i).getType().equals("Exam")) {
				totalPerLesson*=9;
			}
			if (list.get(i).getId() == lesson.getId()) {
				totalPerLesson = 0;
			}
			totalMin += (int)totalPerLesson;
		}
		return totalMin;
	}
}
