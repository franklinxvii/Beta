package gui;

import java.awt.*;

import java.time.DayOfWeek;
import java.util.*;
import javax.swing.*;
import model.*;
import dao.*;
import javax.swing.table.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GetAbsencesSummaryCourses {
	@SuppressWarnings("unused")
	private Teacher teacher;
	private JFrame frmTeachergetAbsenceSummary;
	private JScrollPane scrollPane;
	private JPanel panel;
	private JTable table;
	private JScrollPane scrollPane2;
	private JPanel panelTree;
	private JTree tree;

	/**
	 * Initialize the contents of the frame.
	 */
	public GetAbsencesSummaryCourses(Teacher teacher) {
		this.teacher = teacher;
		frmTeachergetAbsenceSummary = new JFrame();
		frmTeachergetAbsenceSummary.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				@SuppressWarnings("unused")
				TeacherMenu window = new TeacherMenu(teacher);
				frmTeachergetAbsenceSummary.dispose();
			}
		});
		frmTeachergetAbsenceSummary.setTitle("Teacher|Get absence summary for a student");
		frmTeachergetAbsenceSummary.setBounds(100, 100, 942, 601);
		frmTeachergetAbsenceSummary.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JLabel lblNewLabel = new JLabel("Absence summary");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		frmTeachergetAbsenceSummary.getContentPane().add(lblNewLabel, BorderLayout.NORTH);
		
		scrollPane = new JScrollPane();
		frmTeachergetAbsenceSummary.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
	    scrollPane2 = new JScrollPane();
	    frmTeachergetAbsenceSummary.getContentPane().add(scrollPane2, BorderLayout.WEST);
	    
	    panelTree = new JPanel();
	    scrollPane2.setViewportView(panelTree);
	    panelTree.setLayout(new BorderLayout(0, 0));
	    
	    tree = new JTree();
	    tree.setModel(new DefaultTreeModel(findNextTreeNode("Groups")));
	    tree.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseClicked(MouseEvent arg0) {
	    		String s = tree.getLastSelectedPathComponent().toString();
	    		s = s.substring(0, s.indexOf('@'));
	    		Student student = new StudentDAO().get(Integer.parseInt(s));
	    		ArrayList<Absence> absences= new AbsenceDAO().getAbsenceForCourses(student, teacher);
	    		DefaultTableModel model = new DefaultTableModel(absences.size() + 1, 7);
	    		panel.removeAll();
	    		table = new JTable();
	    		table.setEnabled(false);
	    		table.setFillsViewportHeight(true);
	    		panel.add(table, BorderLayout.CENTER);
	    	    model.setValueAt("Week", 0, 0);
	    	    model.setValueAt("Day", 0, 1);
	    	    model.setValueAt("Absence type", 0, 2);
	    	    model.setValueAt("Duration", 0, 3);
	    	    model.setValueAt("Timetable", 0, 4);
	    	    model.setValueAt("Course", 0, 5);
	    	    model.setValueAt("Teacher", 0, 6);
	    	    for(int i = 1; i < model.getRowCount(); i++) {
	    	    	for(int j = 0; j < model.getColumnCount(); j++) {
	    	    		switch(j) {
	    	    		case 0:
	    	    			model.setValueAt(absences.get(i-1).getLesson().getWeek(), i, j);
	    	    			break;
	    	    		case 1:
	    	    			model.setValueAt(DayOfWeek.of(absences.get(i-1).getLesson().getDay()), i, j);
	    	    			break;
	    	    		case 2:
	    	    			AbsenceType type = new AbsenceTypeDAO().get(absences.get(i-1).getLesson(), student);
	    	    			if(type == null)
	    	    				model.setValueAt("NULL", i, j);
	    	    			else
	    	    				model.setValueAt(type.getEntitle(), i, j);
	    	    			break;
	    	    		case 3:
	    	    			if((Integer.parseInt(String.valueOf(absences.get(i-1).getLesson().getEndTime()).substring(String.valueOf(absences.get(i-1).getLesson().getEndTime()).indexOf(".") + 1)) - Integer.parseInt(String.valueOf(absences.get(i-1).getLesson().getStartTime()).substring(String.valueOf(absences.get(i-1).getLesson().getStartTime()).indexOf(".") + 1))) < 0 )
	    	    				model.setValueAt(String.valueOf(absences.get(i-1).getLesson().getEndTime() - absences.get(i-1).getLesson().getStartTime() - 0.4).replace(".", ":"), i, j);
	    	    			else
	    	    				model.setValueAt(String.valueOf(absences.get(i-1).getLesson().getEndTime() - absences.get(i-1).getLesson().getStartTime()).replace(".", ":"), i, j);	
	    	    			break;
	    	    		case 4:
	    	    			model.setValueAt(String.valueOf(absences.get(i-1).getLesson().getStartTime()).substring(0, String.valueOf(absences.get(i-1).getLesson().getStartTime()).indexOf(".")) + "H" + String.valueOf(absences.get(i-1).getLesson().getStartTime()).substring(String.valueOf(absences.get(i-1).getLesson().getStartTime()).indexOf(".") + 1) + "0"
	    	    			+ "-" + String.valueOf(absences.get(i-1).getLesson().getEndTime()).substring(0, String.valueOf(absences.get(i-1).getLesson().getEndTime()).indexOf(".")) + "H" + String.valueOf(absences.get(i-1).getLesson().getEndTime()).substring(String.valueOf(absences.get(i-1).getLesson().getEndTime()).indexOf(".") + 1) + 0, i, j);;
	    	    			break;
	    	    		case 5:
	    	    			model.setValueAt(new CourseDAO().get(new CourseDAO().getCourseId(absences.get(i-1).getLesson().getId())).getName(), i, j);
	    	    			break;
	    	    		case 6:
	    	    			model.setValueAt(new TeacherDAO().get(absences.get(i-1).getLesson()).getFirstName() + " " + new TeacherDAO().get(absences.get(i-1).getLesson()).getLastName(), i, j);
	    	    			break;
	    	    		}	    			
	    	    	}
	    	    }
	    	    table.setModel(model);
	    	    table.revalidate();
	    	    table.repaint();
	    	    panel.revalidate();
	    	    panel.repaint();
	    	}
	    });
	    panelTree.add(tree, BorderLayout.CENTER);
	    frmTeachergetAbsenceSummary.setVisible(true);
	}
	
	/**
	 * List modules and courses from the DB in the tree.
	 *
	 */
	private DefaultMutableTreeNode findNextTreeNode(String nodeName) {
		DefaultMutableTreeNode node1 = new DefaultMutableTreeNode(nodeName);
		DefaultMutableTreeNode node2 = null;
		DefaultMutableTreeNode node3 = null;
		ArrayList<Group> groups = new GroupDAO().getList();
        if (groups != null) {
            for (Group g : groups) {
            	node2 = new DefaultMutableTreeNode(g.getId());
            	ArrayList<Student> students = new StudentDAO().getListForGroup(g.getId()); 
            	if (students != null) {
            		for (Student s : students) {
            			node3 = new DefaultMutableTreeNode(s.getId() + "@" + s.getFirstName() + " " + s.getLastName());
                		node2.add(node3);
                	}
    				node1.add(node2);
            	}
            }
        }
		return node1;
	}

}
