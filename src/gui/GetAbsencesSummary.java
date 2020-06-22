package gui;

import java.awt.*;

import java.time.DayOfWeek;
import java.util.*;
import javax.swing.*;
import model.*;
import dao.*;
import javax.swing.table.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GetAbsencesSummary {
	@SuppressWarnings("unused")
	private Student student;
	private JFrame frmStudentgetAbsenceSummary;
	private JScrollPane scrollPane;
	private JPanel panel;
	private JTable table;

	/**
	 * Initialize the contents of the frame.
	 */
	public GetAbsencesSummary(Student student) {
		this.student = student;
		frmStudentgetAbsenceSummary = new JFrame();
		frmStudentgetAbsenceSummary.setTitle("Student|Get absence summary");
		frmStudentgetAbsenceSummary.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				@SuppressWarnings("unused")
				StudentMenu window = new StudentMenu(student);
				frmStudentgetAbsenceSummary.dispose();
			}
		});
		frmStudentgetAbsenceSummary.setBounds(100, 100, 942, 601);
		frmStudentgetAbsenceSummary.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JLabel lblNewLabel = new JLabel("Absence summary");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		frmStudentgetAbsenceSummary.getContentPane().add(lblNewLabel, BorderLayout.NORTH);
		
		scrollPane = new JScrollPane();
		frmStudentgetAbsenceSummary.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		table = new JTable();
		table.setEnabled(false);
		table.setFillsViewportHeight(true);
		panel.add(table, BorderLayout.CENTER);
		
		ArrayList<Absence> absences= new AbsenceDAO().getAbsenceForStudent(student);
		DefaultTableModel model = new DefaultTableModel(absences.size() + 1, 8);
	    model.setValueAt("Week", 0, 0);
	    model.setValueAt("Day", 0, 1);
	    model.setValueAt("Absence type", 0, 2);
	    model.setValueAt("Duration", 0, 3);
	    model.setValueAt("Timetable", 0, 4);
	    model.setValueAt("Course", 0, 5);
	    model.setValueAt("Teacher", 0, 6);
	    model.setValueAt("Lesson type", 0, 7);
	    
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
	    			+ "-" + String.valueOf(absences.get(i-1).getLesson().getEndTime()).substring(0, String.valueOf(absences.get(i-1).getLesson().getEndTime()).indexOf(".")) + "H" + String.valueOf(absences.get(i-1).getLesson().getEndTime()).substring(String.valueOf(absences.get(i-1).getLesson().getEndTime()).indexOf(".") + 1) + "0", i, j);
	    			break;
	    		case 5:
	    			model.setValueAt(new CourseDAO().get(new CourseDAO().getCourseId(absences.get(i-1).getLesson().getId())).getName(), i, j);
	    			break;
	    		case 6:
	    			model.setValueAt(new TeacherDAO().get(absences.get(i-1).getLesson()).getFirstName() + " " + new TeacherDAO().get(absences.get(i-1).getLesson()).getLastName(), i, j);
	    			break;
	    		case 7:
	    			model.setValueAt(absences.get(i-1).getLesson().getType(), i, j);
	    			break;
	    		}	    			
	    	}
	    }
	    frmStudentgetAbsenceSummary.setVisible(true);
	    table.setModel(model);
	}

}
