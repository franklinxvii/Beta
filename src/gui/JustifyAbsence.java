package gui;

import javax.swing.*;

import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.util.*;
import model.*;
import dao.*;
import com.jgoodies.forms.layout.*;

public class JustifyAbsence {

	private JFrame frmStudentjustifyAbsence;
	private Proof proof;
	private JTextField proofPath;
	private Student student;

	/**
	 * Create the application.
	 */
	public JustifyAbsence(Student student){
		this.student = student;
		frmStudentjustifyAbsence = new JFrame();
		frmStudentjustifyAbsence.setTitle("Student|Justify absence");
		
		frmStudentjustifyAbsence.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				@SuppressWarnings("unused")
				StudentMenu window = new StudentMenu(student);
				frmStudentjustifyAbsence.dispose();
			}
		});
		frmStudentjustifyAbsence.setBounds(100, 100, 1122, 673);
		frmStudentjustifyAbsence.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		frmStudentjustifyAbsence.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("Justify absence");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel, BorderLayout.NORTH);
		
		JLabel lblNewLabel_1 = new JLabel("Complete the following form");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_1, BorderLayout.CENTER);
		
		JPanel panJustify = new JPanel();
		panJustify.setBackground(Color.WHITE);
		frmStudentjustifyAbsence.getContentPane().add(panJustify, BorderLayout.SOUTH);
		
		JPanel panAbsences = new JPanel();
		panAbsences.setBackground(Color.WHITE);
		ArrayList<JCheckBox> list = initListAbsences(panAbsences);
		
		JButton justify = new JButton("Justify absence(s)");
		frmStudentjustifyAbsence.setVisible(true);
		
		justify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ArrayList<Absence> aListAbsence = new ArrayList<>();
				ArrayList<Absence> listAbsences = new AbsenceDAO().getAbsenceForStudent(student);
				JCheckBox another = null;
				for(JCheckBox absence : list) {
					if(absence.isSelected()) {
						for(Absence anAbsence : listAbsences) {
							if((DayOfWeek.of(anAbsence.getLesson().getDay()) + " of week " + anAbsence.getLesson().getWeek() + " "
									+ String.valueOf(anAbsence.getLesson().getStartTime()).substring(0, String.valueOf(anAbsence.getLesson().getStartTime()).indexOf(".")) + "H" + String.valueOf(anAbsence.getLesson().getStartTime()).substring(String.valueOf(anAbsence.getLesson().getStartTime()).indexOf(".") + 1) + "0"
					    			+ "-" + String.valueOf(anAbsence.getLesson().getEndTime()).substring(0, String.valueOf(anAbsence.getLesson().getEndTime()).indexOf(".")) + "H" + String.valueOf(anAbsence.getLesson().getEndTime()).substring(String.valueOf(anAbsence.getLesson().getEndTime()).indexOf(".") + 1) + "0").equals(absence.getText()))
									aListAbsence.add(anAbsence);
									another = absence;
						}
					}
				}
				int returnValue = new ProofDAO().add(new Proof(proofPath.getText()), student, aListAbsence);
				if(returnValue != 0) {
					JOptionPane.showMessageDialog(null, "Proof added");
					if(another != null)
						panAbsences.remove(another);
				}
				else {
					JOptionPane.showMessageDialog(null, "Proof not added");
				}
				panAbsences.revalidate();
				panAbsences.repaint();
			}
		});
		justify.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panJustify.add(justify);
	
		JPanel panForm = new JPanel();
		panForm.setBackground(Color.WHITE);
		frmStudentjustifyAbsence.getContentPane().add(panForm, BorderLayout.CENTER);
		panForm.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("center:default:grow"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		JLabel lblNewLabel_2 = new JLabel("Reason for absence");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panForm.add(lblNewLabel_2, "4, 4");
		
		JComboBox<AbsenceType> selectReason = new JComboBox<AbsenceType>();
		selectReason.setFont(new Font("Tahoma", Font.PLAIN, 14));
		selectReason.setModel(new DefaultComboBoxModel<AbsenceType>(new AbsenceTypeDAO().getList().toArray(new AbsenceType[0])));
		panForm.add(selectReason, "10, 4, fill, default");
		
		JLabel lblNewLabel_3 = new JLabel("Select absences");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panForm.add(lblNewLabel_3, "4, 8");
		
		
		panForm.add(panAbsences, "10, 8, 1, 19, fill, fill");
		panAbsences.setLayout(new BoxLayout(panAbsences, BoxLayout.Y_AXIS));
		
		JButton btnNewButton = new JButton("Select proof");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser choice = new JFileChooser();
				choice.setFileFilter(new FileNameExtensionFilter("JPEG file", "jpg", "jpeg"));
				choice.setAcceptAllFileFilterUsed(false);
				int selected=choice.showOpenDialog(frmStudentjustifyAbsence);
				if(selected == JFileChooser.APPROVE_OPTION) {
					proof = new Proof(choice.getSelectedFile().getAbsolutePath());
				}
				proofPath.setText(proof.getProofPath());
			}
		});
		panForm.add(btnNewButton, "4, 30");
		
		proofPath = new JTextField();
		proofPath.setFont(new Font("Tahoma", Font.PLAIN, 14));
		proofPath.setEditable(false);
		panForm.add(proofPath, "10, 30, fill, default");
		proofPath.setColumns(10);
	}
	
	/**
	 * Return from the database the list of absences you can select in the panel in parameter
	 * @param panAbsences
	 * @return the list of absences
	 */
	ArrayList<JCheckBox> initListAbsences(JPanel panAbsences) {
		panAbsences.removeAll();
		ArrayList<JCheckBox> list = new ArrayList<>();
		ArrayList<Absence> absences = new AbsenceDAO().getAbsencesToJustify(student);
		for(Absence absence: absences) {
			JCheckBox box = new JCheckBox(DayOfWeek.of(absence.getLesson().getDay()) + " of week " + absence.getLesson().getWeek() + " "
					+ String.valueOf(absence.getLesson().getStartTime()).substring(0, String.valueOf(absence.getLesson().getStartTime()).indexOf(".")) + "H" + String.valueOf(absence.getLesson().getStartTime()).substring(String.valueOf(absence.getLesson().getStartTime()).indexOf(".") + 1) + "0"
	    			+ "-" + String.valueOf(absence.getLesson().getEndTime()).substring(0, String.valueOf(absence.getLesson().getEndTime()).indexOf(".")) + "H" + String.valueOf(absence.getLesson().getEndTime()).substring(String.valueOf(absence.getLesson().getEndTime()).indexOf(".") + 1) + "0");
		    list.add(box);
		    panAbsences.add(box);
		}
		return list;
	}
	
	/**
	 * When a proof is selected, show his list of absences
	 * @param panAbsence
	 * @param aList
	 * @param list
	 */
	void initListAbsences(JPanel panAbsence, ArrayList<JCheckBox> list) {
		panAbsence.removeAll();
		ArrayList<Absence> aList = new AbsenceDAO().getAbsenceForProof(proof);
		if(aList.isEmpty()) {
			for(JCheckBox anAbsence : list) {
				anAbsence.setSelected(false);
				panAbsence.add(anAbsence);
			}
		}
		else {
			for(JCheckBox anAbsence : list) {
				anAbsence.setSelected(false);
				panAbsence.add(anAbsence);
			}
			for(JCheckBox anAbsence : list) {
				for(Absence another : aList) {
					if(anAbsence.getText().equals(DayOfWeek.of(another.getLesson().getDay()) + " of week " + another.getLesson().getWeek()
							+ String.valueOf(another.getLesson().getStartTime()).substring(0, String.valueOf(another.getLesson().getStartTime()).indexOf(".")) + "H" + String.valueOf(another.getLesson().getStartTime()).substring(String.valueOf(another.getLesson().getStartTime()).indexOf(".") + 1) + "0"
			    			+ "-" + String.valueOf(another.getLesson().getEndTime()).substring(0, String.valueOf(another.getLesson().getEndTime()).indexOf(".")) + "H" + String.valueOf(another.getLesson().getEndTime()).substring(String.valueOf(another.getLesson().getEndTime()).indexOf(".") + 1) + "0")) {
						anAbsence.setSelected(true);
					}
					anAbsence.setEnabled(false);
				}
				panAbsence.add(anAbsence);
			}
		}
		panAbsence.repaint();
	}
}
