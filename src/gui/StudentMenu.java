package gui;


import java.awt.*;

import java.time.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import model.*;
import dao.*;
import javax.swing.GroupLayout.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;

public class StudentMenu {
	@SuppressWarnings("unused")
	private Student student;
	private JFrame frmStudentstudentMenu;
	private boolean oldLesson[] = {false, false, false, false, false} ;
	private int first[] = {0,0,0,0,0};
	private JTable planExams;
	private JTable planCourses;
	/**
	 * Initialize the contents of the frame.
	 */
	public StudentMenu(Student student) {
		this.student = student;
		frmStudentstudentMenu = new JFrame();
		frmStudentstudentMenu.setResizable(false);
		frmStudentstudentMenu.setTitle("Student|Student menu");
		frmStudentstudentMenu.setBounds(100, 100, 1474, 945);
		frmStudentstudentMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmStudentstudentMenu.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setContinuousLayout(true);
		splitPane.setEnabled(false);
		frmStudentstudentMenu.getContentPane().add(splitPane, BorderLayout.CENTER);
		
		JPanel panMenu = new JPanel();
		panMenu.setBackground(Color.WHITE);
		splitPane.setLeftComponent(panMenu);
		
		JButton summaryAbsence = new JButton("Summary of Absences");
		summaryAbsence.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				@SuppressWarnings("unused")
				GetAbsencesSummary window = new GetAbsencesSummary(student);
				frmStudentstudentMenu.dispose();
			}
		});
		
		JButton anticipateAbsence = new JButton("Anticipate/Justify absence");
		anticipateAbsence.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unused")
				JustifyAbsence window = new JustifyAbsence(student);
				frmStudentstudentMenu.dispose();
			}
		});
		
		JTextArea studentInfo = new JTextArea();
		studentInfo.setLineWrap(true);
		studentInfo.setForeground(Color.BLACK);
		studentInfo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		studentInfo.setEditable(false);
		studentInfo.append("First name : " + student.getFirstName() + "\nLast name : " + student.getLastName() + 
					"\nFaculty : "+ student.getFaculty() + "\nEmail : "+ student.getEmail() + "\n");
		studentInfo.append("Courses for the session:\n");
		for(Course course: new CourseDAO().getListForGroup(student.getGroup())) {
			studentInfo.append(course.getName() + "\n");
		}
		
		JButton logOut = new JButton("Log out");
		logOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unused")
				Connection window = new Connection();
				frmStudentstudentMenu.dispose();
			}
		});
		GroupLayout gl_panMenu = new GroupLayout(panMenu);
		gl_panMenu.setHorizontalGroup(
			gl_panMenu.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panMenu.createSequentialGroup()
					.addGroup(gl_panMenu.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panMenu.createSequentialGroup()
							.addGap(108)
							.addGroup(gl_panMenu.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(logOut, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(anticipateAbsence, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(summaryAbsence, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						.addGroup(gl_panMenu.createSequentialGroup()
							.addGap(43)
							.addComponent(studentInfo, GroupLayout.PREFERRED_SIZE, 341, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(229, Short.MAX_VALUE))
		);
		gl_panMenu.setVerticalGroup(
			gl_panMenu.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panMenu.createSequentialGroup()
					.addGap(34)
					.addComponent(studentInfo, GroupLayout.PREFERRED_SIZE, 444, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 183, Short.MAX_VALUE)
					.addComponent(summaryAbsence)
					.addGap(18)
					.addComponent(anticipateAbsence)
					.addGap(18)
					.addComponent(logOut)
					.addGap(136))
		);
		panMenu.setLayout(gl_panMenu);
		
		JPanel panPlanning = new JPanel();
		panPlanning.setBackground(Color.WHITE);
		splitPane.setRightComponent(panPlanning);
		panPlanning.setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPlanning = new JSplitPane();
		splitPlanning.setBackground(Color.WHITE);
		splitPlanning.setResizeWeight(0.5);
		panPlanning.add(splitPlanning, BorderLayout.CENTER);
		
		JPanel panCourses = new JPanel();
		panCourses.setBackground(Color.WHITE);
		splitPlanning.setLeftComponent(panCourses);
		panCourses.setLayout(new BorderLayout(0, 0));
		
		planCourses = new JTable();
		planCourses.setEnabled(false);
		panCourses.add(planCourses, BorderLayout.CENTER);
		
		JLabel lblNewLabel = new JLabel("Course planning: Week 1-9");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panCourses.add(lblNewLabel, BorderLayout.NORTH);
		
		JPanel panExams = new JPanel();
		panExams.setBackground(Color.WHITE);
		splitPlanning.setRightComponent(panExams);
		panExams.setLayout(new BorderLayout(0, 0));
		
		planExams = new JTable();
		planExams.setShowVerticalLines(false);
		planExams.setShowHorizontalLines(false);
		planExams.setShowGrid(false);
		planExams.setRowSelectionAllowed(false);
		planExams.setEnabled(false);
		panExams.add(planExams, BorderLayout.CENTER);
		
		JLabel lblNewLabel_1 = new JLabel("Exam planning: Week 10");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBackground(Color.WHITE);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		panExams.add(lblNewLabel_1, BorderLayout.NORTH);
		@SuppressWarnings("serial")
		DefaultTableModel model = new DefaultTableModel(48, 6) {
	         @Override
	         public Class<?> getColumnClass(int columnIndex) {
	            return String.class;
	         }
	    };
	    @SuppressWarnings("serial")
		DefaultTableModel model2 = new DefaultTableModel(48, 6) {
	         @Override
	         public Class<?> getColumnClass(int columnIndex) {
	            return String.class;
	         }
	    };
	    for(int i = 0; i < model.getColumnCount(); i++) {
	    	int j = 0;
	    	if(i == 0 && j == 0) {
	    		model.setValueAt("HEURE\\JOUR", j, i);
	    		model2.setValueAt("HEURE\\JOUR", j, i);
	    	}
	    	else {
	    		model.setValueAt(DayOfWeek.of(i).toString(), j, i);
	    		model2.setValueAt(DayOfWeek.of(i).toString(), j, i);
	    	}
	    }
	    int starts = 8;
	    for(int i = 1; i < model.getRowCount(); i++) {
	    	if(i % 2 != 0) {
	    		if(i == 1 || i == 5 || i == 9 || i == 13 || i == 17 || i == 21 || i == 25 || i == 29 || i == 33 || i == 37 || i == 41 || i == 45) {
	    			if(i != 1) {
	    				starts++;
	    			}
	    			model.setValueAt(starts + "H00", i, 0);
	    			model2.setValueAt(starts + "H00", i, 0);
	    		}
	    		else if(i == 3 || i == 7 || i == 11 || i == 15 || i == 19 || i == 23 || i == 27 || i == 31 || i == 35 || i == 39 || i == 43 || i == 47) {
	    			model.setValueAt(starts + "H30", i, 0);
	    			model2.setValueAt(starts + "H30", i, 0);
	    		}
	    	}
	    }
		for(int i = 1; i < model.getColumnCount(); i++ ) {
			ArrayList<Lesson> lessons = new LessonDAO().getListForDayGroup(student.getGroup(), i);
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
					if(!lesson.getType().equals("Exam")) {
						model.setValueAt(course.getName()+": "+lesson.getType(), j, i);
					}
					else {
						model2.setValueAt(course.getName()+": "+lesson.getType(), j, i);
					}
				}
			}
		}
		planCourses.setDefaultRenderer(String.class, new myCellRenderer());
		planExams.setDefaultRenderer(String.class, new myCellRenderer());
		planCourses.setModel(model);
		planExams.setModel(model2);
		frmStudentstudentMenu.setVisible(true);
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
		    	  else if(s.contains("Exam")) {
		    		  c.setBackground(Color.RED);
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



