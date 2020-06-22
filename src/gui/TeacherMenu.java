package gui;


import java.awt.*;

import java.time.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import model.*;
import dao.*;
import java.awt.event.*;

public class TeacherMenu {
	@SuppressWarnings("unused")
	private Teacher teacher;
	private JFrame frmTeacherteachermenu;
	private JTable table;
	private boolean oldLesson[] = {false, false, false, false, false} ;
	private int first[] = {0,0,0,0,0};

	/**
	 * Initialize the contents of the frame.
	 */
	public TeacherMenu(Teacher teacher) {
		this.teacher = teacher;
		frmTeacherteachermenu = new JFrame();
		frmTeacherteachermenu.setResizable(false);
		frmTeacherteachermenu.setTitle("Teacher|Teacher menu");
		frmTeacherteachermenu.setBounds(100, 100, 1255, 814);
		frmTeacherteachermenu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmTeacherteachermenu.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setEnabled(false);
		splitPane.setResizeWeight(0.5);
		splitPane.setContinuousLayout(true);
		frmTeacherteachermenu.getContentPane().add(splitPane, BorderLayout.CENTER);
		
		JPanel panMenu = new JPanel();
		panMenu.setBackground(Color.WHITE);
		splitPane.setLeftComponent(panMenu);
		panMenu.setLayout(null);
		
		JButton makeCall = new JButton("Make call");
		makeCall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				@SuppressWarnings("unused")
				MakeCall window = new MakeCall(teacher);
				frmTeacherteachermenu.dispose();
			}
		});
		makeCall.setFont(new Font("Tahoma", Font.PLAIN, 14));
		makeCall.setBounds(74, 466, 238, 25);
		panMenu.add(makeCall);
		
		JButton summaryAbsence = new JButton("Summary of student's absence");
		summaryAbsence.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				@SuppressWarnings("unused")
				GetAbsencesSummaryCourses window = new GetAbsencesSummaryCourses(teacher);
				frmTeacherteachermenu.dispose();
			}
		});
		summaryAbsence.setFont(new Font("Tahoma", Font.PLAIN, 14));
		summaryAbsence.setBounds(74, 506, 238, 25);
		panMenu.add(summaryAbsence);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(43, 34, 300, 418);
		panMenu.add(scrollPane);
		
		JTextArea studentInfo = new JTextArea();
		studentInfo.setWrapStyleWord(true);
		scrollPane.setViewportView(studentInfo);
		studentInfo.setForeground(Color.BLACK);
		studentInfo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		studentInfo.setEditable(false);
		if(teacher.getPhoneNumber() != null)	{
			studentInfo.append("First name : " + teacher.getFirstName() + "\nLast name : " + teacher.getLastName() + 
					"\nPhone number : "+ teacher.getPhoneNumber() + "\n");
		}
		else {
			studentInfo.append("First name : " + teacher.getFirstName() + "\nLast name : " + teacher.getLastName() + 
					"\nPhone number : None\n");
		}
		studentInfo.append("Courses to teach\n");
		teacher.setCourses(new CourseDAO().getListForTeacher(teacher.getId()));
		for(Course course: teacher.getCourses()) {
			studentInfo.append(course.getName() + "\n");
		}
		
		JButton logOut = new JButton("Log out");
		logOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				@SuppressWarnings("unused")
				Connection window = new Connection();
				frmTeacherteachermenu.dispose();
			}
		});
		logOut.setBounds(74, 544, 238, 25);
		panMenu.add(logOut);
		
		JPanel panPlanning = new JPanel();
		panPlanning.setBackground(Color.WHITE);
		splitPane.setRightComponent(panPlanning);
		panPlanning.setLayout(new BorderLayout(0, 0));
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		table.setShowGrid(false);
		table.setShowHorizontalLines(false);
		@SuppressWarnings("serial")
		DefaultTableModel model = new DefaultTableModel(48, 6) {
	         @Override
	         public Class<?> getColumnClass(int columnIndex) {
	            return String.class;
	         }
	    };
	    for(int i = 0; i < model.getColumnCount(); i++) {
	    	int j = 0;
	    	if(i == 0 && j == 0)
	    		model.setValueAt("HEURE\\JOUR", j, i);
	    	else
	    		model.setValueAt(DayOfWeek.of(i).toString(), j, i);
	    }
	    int starts = 8;
	    for(int i = 1; i < model.getRowCount(); i++) {
	    	if(i % 2 != 0) {
	    		if(i == 1 || i == 5 || i == 9 || i == 13 || i == 17 || i == 21 || i == 25 || i == 29 || i == 33 || i == 37 || i == 41 || i == 45) {
	    			if(i != 1) {
	    				starts++;
	    			}
	    			model.setValueAt(starts + "H00", i, 0);
	    		}
	    		else if(i == 3 || i == 7 || i == 11 || i == 15 || i == 19 || i == 23 || i == 27 || i == 31 || i == 35 || i == 39 || i == 43 || i == 47)
	    			model.setValueAt(starts + "H30", i, 0);
	    	}
	    }
		for(int i = 1; i < model.getColumnCount(); i++ ) {
			ArrayList<Lesson> lessons = new LessonDAO().getListForDayTeacher(teacher.getId(), i);
			for (Lesson lesson: lessons) {
				Course course = new CourseDAO().getCourse(lesson.getId());
				int indexOfDecimal = String.valueOf(lesson.getStartTime()).indexOf(".");
				int ent = Integer.parseInt(String.valueOf(lesson.getStartTime()).substring(0, indexOfDecimal)) - 8;
				int dec = Integer.parseInt(String.valueOf(lesson.getStartTime()).substring(indexOfDecimal + 1));
				int start = (ent*4)+dec;
				indexOfDecimal = String.valueOf(lesson.getEndTime()).indexOf(".");
				ent = Integer.parseInt(String.valueOf(lesson.getEndTime()).substring(0, indexOfDecimal)) - 8;
				dec = Integer.parseInt(String.valueOf(lesson.getEndTime()).substring(indexOfDecimal + 1));
				int end = (ent*4)+dec;
				for(int j = start; j <= end; j++) {
					model.setValueAt(course.getName()+": "+lesson.getType(), j, i);
				}
			}
		}
	    table.setModel(model);
		table.setEnabled(false);
		table.setRowSelectionAllowed(false);
		table.setDefaultRenderer(String.class, new myCellRenderer());
		panPlanning.add(table, BorderLayout.CENTER);
		frmTeacherteachermenu.setVisible(true);
	}
	
	class myCellRenderer extends DefaultTableCellRenderer {

		   private static final long serialVersionUID = 1L;

		   public Component getTableCellRendererComponent(JTable table, Object value,
		            boolean isSelected, boolean hasFocus, int row, int col) {
		      Component c = super.getTableCellRendererComponent(table, value,
		               isSelected, hasFocus, row, col);
		      Object valueAt = table.getModel().getValueAt(row, col);
		      String s = "";
		      if (valueAt != null) {
		         s = valueAt.toString();
		      }
		      if (s.isEmpty() || row == 0 || col == 0 ) {
		   		  c.setForeground(Color.BLACK);
		   		  c.setBackground(Color.WHITE);
		   		  if(row  != 0 && col != 0)
		   			  oldLesson[col - 1] = false;
		   	  }
	    	  else {
	    		  boolean newLesson = true;
	    		  if(s.contains("Amphi")) {
		    		  c.setBackground(Color.GREEN);
		    	  }
		    	  else if(s.contains("TP")) {
		    		  c.setBackground(Color.PINK);
		    	  }
		    	  else if(s.contains("TD")) {
		    		  c.setBackground(Color.ORANGE);
		    	  }
		       	  if(oldLesson[col - 1] == true && (table.getModel().getValueAt(first[col - 1], col) != null && s.equals(table.getModel().getValueAt(first[col - 1], col).toString()))) {
		       		  c.setForeground(c.getBackground());
		    	  }
		   		  else {
		   			  c.setForeground(Color.WHITE);
		   			  first[col - 1] = row;
		   		  }
		   		  oldLesson[col - 1] = newLesson;
		   	  }
		      return c;
		   	}
	}
}



