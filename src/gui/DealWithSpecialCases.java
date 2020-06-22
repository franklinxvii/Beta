package gui;

import java.util.*;

import javax.swing.*;
import java.awt.*;
import java.time.DayOfWeek;

import javax.swing.tree.*;
import model.*;
import dao.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DealWithSpecialCases {

	private JFrame frmDealWithSpecial;
	private JTree tree = new JTree();
	private JScrollPane scrollPane;
	private JSplitPane splitPane;
	private Lesson lesson;
	private JPanel panel;
	private JTextField nameTeacher;
	private JTextField week;


	/**
	 * Create the application.
	 */
	public DealWithSpecialCases(){
		frmDealWithSpecial = new JFrame();
		frmDealWithSpecial.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				@SuppressWarnings("unused")
				ManagerMenu window = new ManagerMenu();
				frmDealWithSpecial.dispose();
			}
		});
		frmDealWithSpecial.setTitle("Manager|Deal with special cases");
		frmDealWithSpecial.setBounds(100, 100, 1098, 690);
		frmDealWithSpecial.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		scrollPane = new JScrollPane();
		splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.9);
		frmDealWithSpecial.getContentPane().add(splitPane, BorderLayout.CENTER);
		splitPane.setLeftComponent(scrollPane);
		tree = new JTree();
		tree.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		splitPane.setRightComponent(panel);
		
		JLabel lblGroup = new JLabel("Group");
		lblGroup.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JTextField group = new JTextField();
		group.setFont(new Font("Tahoma", Font.PLAIN, 14));
		group.setEditable(false);
		
		JLabel lblCourse = new JLabel("Course");
		lblCourse.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JTextField course = new JTextField();
		course.setFont(new Font("Tahoma", Font.PLAIN, 14));
		course.setEditable(false);
		
		JLabel lblTeacher = new JLabel("Teacher");
		lblTeacher.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		nameTeacher = new JTextField();
		nameTeacher.setFont(new Font("Tahoma", Font.PLAIN, 14));
		nameTeacher.setEditable(false);
		
		JTextField selectType = new JTextField();
		selectType.setFont(new Font("Tahoma", Font.PLAIN, 14));
		selectType.setEditable(false);
		
		JLabel lblType = new JLabel("Type");
		lblType.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblDay = new JLabel("Day");
		lblDay.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JTextField day = new JTextField();
		day.setFont(new Font("Tahoma", Font.PLAIN, 14));
		day.setEditable(false);
		
		JTextField startHour = new JTextField();
		startHour.setFont(new Font("Tahoma", Font.PLAIN, 14));
		startHour.setEditable(false);
		
		JLabel lblStartTime = new JLabel("Start time");
		lblStartTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblEndTime = new JLabel("End time");
		lblEndTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JTextField endHour = new JTextField();
		endHour.setFont(new Font("Tahoma", Font.PLAIN, 14));
		endHour.setEditable(false);
		
		JLabel lblHour_1 = new JLabel("H");
		lblHour_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblHour = new JLabel("H");
		lblHour.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JTextField startMinute = new JTextField();
		startMinute.setFont(new Font("Tahoma", Font.PLAIN, 14));
		startMinute.setEditable(false);
		
		JTextField endMinute = new JTextField();
		endMinute.setFont(new Font("Tahoma", Font.PLAIN, 14));
		endMinute.setEditable(false);
		
		JLabel lblMin = new JLabel("MIN");
		lblMin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblMin_1 = new JLabel("MIN");
		lblMin_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLogo.setIcon(new ImageIcon(DealWithSpecialCases.class.getResource("/gui/ESIGELEC.png")));
		
		JButton approveAbsence = new JButton("Approve absence");
		approveAbsence.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int returnValue = new AbsenceDAO().maintainAbsence(lesson);
				if(returnValue != 0) {
					JOptionPane.showMessageDialog(null, "All absence for this lesson are maintained");
				}
				else {
					JOptionPane.showMessageDialog(null, "All absence for this lesson are not maintained");
				}
				tree.setModel(new DefaultTreeModel(findNextTreeNode("Lessons")));
			}
		});
		approveAbsence.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JButton removeAbsence = new JButton("Remove absence");
		removeAbsence.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int returnValue = new AbsenceDAO().removeAbsence(lesson);
				if(returnValue != 0) {
					JOptionPane.showMessageDialog(null, "All absence for this lesson have been deleted");
				}
				else {
					JOptionPane.showMessageDialog(null, "All absence for this lesson have not been deleted");
				}
				tree.setModel(new DefaultTreeModel(findNextTreeNode("Lessons")));
			}
		});
		removeAbsence.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblWeek = new JLabel("Week");
		lblWeek.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		week = new JTextField();
		week.setEditable(false);
		week.setColumns(10);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(262)
							.addComponent(lblLogo, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(107)
							.addComponent(lblGroup)
							.addGap(76)
							.addComponent(group, GroupLayout.PREFERRED_SIZE, 294, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(107)
							.addComponent(lblCourse, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
							.addGap(62)
							.addComponent(course, GroupLayout.PREFERRED_SIZE, 294, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(107)
							.addComponent(lblTeacher, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
							.addGap(58)
							.addComponent(nameTeacher, GroupLayout.PREFERRED_SIZE, 294, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(310)
							.addComponent(approveAbsence)
							.addGap(18)
							.addComponent(removeAbsence))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(107)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lblStartTime, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
									.addGap(49)
									.addComponent(startHour, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
									.addGap(12)
									.addComponent(lblHour, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
									.addGap(13)
									.addComponent(startMinute, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
									.addGap(12)
									.addComponent(lblMin, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lblEndTime, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
									.addGap(49)
									.addComponent(endHour, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
									.addGap(11)
									.addComponent(lblHour_1, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
									.addGap(13)
									.addComponent(endMinute, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
									.addGap(12)
									.addComponent(lblMin_1, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lblType, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
									.addGap(62)
									.addComponent(selectType, GroupLayout.PREFERRED_SIZE, 294, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(lblDay, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblWeek))
									.addGap(58)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(week, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(day, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE))))))
					.addGap(89))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(30)
					.addComponent(lblLogo, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
					.addGap(88)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(lblGroup))
						.addComponent(group, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(13)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(lblCourse))
						.addComponent(course, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(10)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(lblTeacher))
						.addComponent(nameTeacher, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(10)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(lblType))
						.addComponent(selectType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(26)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblWeek)
						.addComponent(week, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(lblDay))
						.addComponent(day, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(13)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(lblStartTime))
						.addComponent(startHour, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(lblHour))
						.addComponent(startMinute, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(lblMin)))
					.addGap(10)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(lblEndTime))
						.addComponent(endHour, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(lblHour_1))
						.addComponent(endMinute, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(lblMin_1)))
					.addGap(64)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(approveAbsence)
						.addComponent(removeAbsence)))
		);
		panel.setLayout(gl_panel);
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String s = tree.getLastSelectedPathComponent().toString();
				String p = s.substring(s.indexOf("@") + 1, s.indexOf("#"));
				s = s.substring(0, s.indexOf("@"));
				if (s.startsWith("8")) {
					// we show the module
					lesson = new LessonDAO().getSpecialCase(Integer.parseInt(s), Integer.parseInt(p));
					int id_group = new GroupDAO().getGroupId(Integer.parseInt(s));
					int id_course = new CourseDAO().getCourseId(Integer.parseInt(s));
					group.setText(String.valueOf(id_group));
					course.setText(String.valueOf(id_course));
					week.setText(String.valueOf(lesson.getWeek()));
					Teacher teacher = new TeacherDAO().get(lesson);
					nameTeacher.setText(teacher.getFirstName() + " " + teacher.getLastName());
					selectType.setText(lesson.getType());
					day.setText(DayOfWeek.of(lesson.getDay()).toString());
					startHour.setText(String.valueOf(lesson.getStartTime()).substring(0, String.valueOf(lesson.getStartTime()).indexOf(".")));
					startMinute.setText(String.valueOf(lesson.getStartTime()).substring(String.valueOf(lesson.getStartTime()).indexOf(".")+1).concat("0"));
					endHour.setText(String.valueOf(lesson.getEndTime()).substring(0, String.valueOf(lesson.getEndTime()).indexOf(".")));
					endMinute.setText(String.valueOf(lesson.getEndTime()).substring(String.valueOf(lesson.getEndTime()).indexOf(".")+1).concat("0"));
					approveAbsence.setEnabled(true);
					removeAbsence.setEnabled(true);
				}
			}
		});
		tree.setModel(new DefaultTreeModel(findNextTreeNode("Lessons")));
		scrollPane.setViewportView(tree);
		
		
		frmDealWithSpecial.setVisible(true);
	}
	
	/**
	 * List modules and courses from the DB in the tree.
	 *
	 */
	private DefaultMutableTreeNode findNextTreeNode(String nodeName) {
		DefaultMutableTreeNode node1 = new DefaultMutableTreeNode(nodeName);
		DefaultMutableTreeNode node2 = null;
		ArrayList<Lesson> lessons = new LessonDAO().getSpecialCasesList();
        if (lessons != null) {
        	for(Lesson l: lessons) {
        		node2= new DefaultMutableTreeNode(l.getId() + "@" + l.getWeek() + "#" + DayOfWeek.of(l.getDay()) 
    			+ "|" + String.valueOf(l.getStartTime()).substring(0, String.valueOf(l.getStartTime()).indexOf(".")) + "H" + String.valueOf(l.getStartTime()).substring(String.valueOf(l.getStartTime()).indexOf(".") + 1) + "0"
    			+ "-" + String.valueOf(l.getEndTime()).substring(0, String.valueOf(l.getEndTime()).indexOf(".")) + "H" + String.valueOf(l.getEndTime()).substring(String.valueOf(l.getEndTime()).indexOf(".") + 1) + 0);
        		node1.add(node2);
        	}
        }
		return node1;
	}
}
