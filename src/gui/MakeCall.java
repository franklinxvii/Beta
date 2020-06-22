package gui;


import javax.swing.*;

import java.awt.*;
import javax.swing.tree.*;

import dao.*;
import model.*;
import java.awt.event.*;
import java.time.DayOfWeek;
import java.util.*;

public class MakeCall {

	private JFrame frmTeachermakeTheCall;
	private JSplitPane splitPane;
	private JScrollPane scrollPane;
	private JTree tree;
	private JPanel panel;
	private Lesson lesson;
	private Teacher teacher;
	private ArrayList<JCheckBox> listStudent;
	private JPanel panCall;
	private JPanel panWeek;
	private JPanel panList;
	private JLabel lblNewLabel;
	private JComboBox<String> selectWeek;
	private JPanel panValidate;
	private JButton updateAbsence;
	private JButton validate;

	/**
	 * Initialize the contents of the frame.
	 */
	public MakeCall(Teacher teacher) {
		this.teacher = teacher;
		frmTeachermakeTheCall = new JFrame();
		frmTeachermakeTheCall.setTitle("Teacher|Make the call");
		frmTeachermakeTheCall.setBounds(100, 100, 760, 596);
		frmTeachermakeTheCall.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		listStudent = new ArrayList<>();
				
		JScrollPane scrollPaneList = new JScrollPane();
		
		frmTeachermakeTheCall.addWindowListener( new WindowAdapter(){
		    @Override
			public void windowClosed(WindowEvent e) {
		    	@SuppressWarnings("unused")
				TeacherMenu window = new TeacherMenu(teacher);
		    	frmTeachermakeTheCall.dispose();
		    }
		});
		
		splitPane = new JSplitPane();
		splitPane.setContinuousLayout(true);
		splitPane.setResizeWeight(0.2);
		frmTeachermakeTheCall.getContentPane().add(splitPane, BorderLayout.CENTER);
		
		scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);
		
		tree = new JTree();
		scrollPane.setViewportView(tree);
		tree.setModel(new DefaultTreeModel(findNextTreeNode("Courses")));
		
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		splitPane.setRightComponent(panel);
		panel.setLayout(new BorderLayout(0, 0));
			
		JLabel lblLogo = new JLabel("");
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogo.setIcon(new ImageIcon(ManageLessons.class.getResource("/gui/ESIGELEC.png")));
		panel.add(lblLogo, BorderLayout.NORTH);
		
		panCall = new JPanel();
		panel.add(panCall, BorderLayout.CENTER);
		panCall.setLayout(new BorderLayout(0, 0));
		
		panWeek = new JPanel();
		panWeek.setBackground(Color.WHITE);
		panCall.add(panWeek, BorderLayout.NORTH);
		panWeek.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		lblNewLabel = new JLabel("Semaine");
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panWeek.add(lblNewLabel);
		
		selectWeek = new JComboBox<String>();
		selectWeek.setModel(new DefaultComboBoxModel<String>(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
		selectWeek.setMaximumRowCount(9);
		selectWeek.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panWeek.add(selectWeek);
		
		validate = new JButton("Validate");
		validate.setEnabled(false);
		validate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// affichage group
				listStudent = initListStudents(panList, lesson.getId(), Integer.parseInt(selectWeek.getItemAt(selectWeek.getSelectedIndex())));
				updateAbsence.setEnabled(true);
			}
		});
		panWeek.add(validate);
		
		panList = new JPanel();
		panList.setBackground(Color.WHITE);
		panCall.add(scrollPaneList, BorderLayout.CENTER);
		panList.setLayout(new BoxLayout(panList, BoxLayout.Y_AXIS));
		
		scrollPaneList.setViewportView(panList);
		
		panValidate = new JPanel();
		panValidate.setBackground(Color.WHITE);
		panCall.add(panValidate, BorderLayout.SOUTH);
		
		updateAbsence = new JButton("Update");
		updateAbsence.setEnabled(false);
		updateAbsence.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ArrayList<Student> aListStudent = new ArrayList<>();
				ArrayList<Student> listStudents = new StudentDAO().getListForLesson(lesson.getId());
				for(int i = 0; i < listStudent.size(); i++) {
					if(listStudent.get(i).isSelected()) {
						aListStudent.add(listStudents.get(i));
					}
				}
				if(new AbsenceDAO().processed(lesson, Integer.parseInt(selectWeek.getItemAt(selectWeek.getSelectedIndex())))) {
					JOptionPane.showMessageDialog(frmTeachermakeTheCall, "These absences have already been processed");
				}
				else {
					int returnValue = new AbsenceDAO().update(lesson, Integer.parseInt(selectWeek.getItemAt(selectWeek.getSelectedIndex())), aListStudent, (double)aListStudent.size()/listStudents.size()*100);
					if(returnValue != 0)
						JOptionPane.showMessageDialog(null, "Absences updated");
					else
						JOptionPane.showMessageDialog(null, "Absences not updated");
				}
				for(int i = 0; i < listStudent.size(); i++) {
					listStudent.get(i).setSelected(false);
				}
				validate.setEnabled(false);
				updateAbsence.setEnabled(false);
			}
		});
		
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				initForms();
				String s = tree.getLastSelectedPathComponent().toString();
				if(s.startsWith("8")) {
					s = s.substring(0, s.indexOf("@"));
					lesson = new LessonDAO().get(Integer.parseInt(s));
					validate.setEnabled(true);
				}
			}
		});
		
		panValidate.add(updateAbsence);
		frmTeachermakeTheCall.setVisible(true);
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
		ArrayList<Course> courses = new CourseDAO().getListForTeacher(teacher.getId());
        if (courses != null) {
            for (Course c : courses) {
            	node2 = new DefaultMutableTreeNode(c.getName());
            	ArrayList<Group> groups = new GroupDAO().getList(teacher, c);
            	if (groups != null) {
            		for (Group g : groups) {
            			node3 = new DefaultMutableTreeNode(g.getId());
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
            	}
            	node1.add(node2);
            }
        }
		return node1;
	}
	
	void initForms() {
		selectWeek.setSelectedIndex(0);
		panList.removeAll();
		panList.revalidate();
		panList.repaint();
	}
	
	/**
	 * Return from the database the list of students you can select in the panel in parameter
	 * @param panStudents
	 * @return the list of students
	 */
	ArrayList<JCheckBox> initListStudents(JPanel panStudents, int idLesson, int idWeek) {
		panStudents.removeAll();
		ArrayList<JCheckBox> list = new ArrayList<>();
		ArrayList<Student> listStudents = new StudentDAO().getListForLesson(idLesson);
		for(Student student: listStudents) {
			JCheckBox box = new JCheckBox(student.getLastName() + " " + student.getFirstName());
			if(new AbsenceDAO().wasAbsent(student.getId(), idLesson, idWeek)) {
				box.setSelected(true);
			}
			else {
				box.setSelected(false);
			}
			box.setBackground(Color.WHITE);
			list.add(box);
			panStudents.add(box);
		}
	    panStudents.revalidate();
		panStudents.repaint();
		return list;
	}
}
