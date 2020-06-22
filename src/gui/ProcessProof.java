package gui;

import javax.swing.*;

import javax.swing.tree.*;
import dao.*;
import model.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.GroupLayout.Alignment;

public class ProcessProof {

	private JFrame frmManagerprocessProof;
	private Proof proof;

	/**
	 * Create the application.
	 */
	public ProcessProof(){
		frmManagerprocessProof = new JFrame();
		frmManagerprocessProof.setTitle("Manager|Process proof");
		frmManagerprocessProof.setBounds(100, 100, 989, 757);
		frmManagerprocessProof.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmManagerprocessProof.setResizable(false);
		
		JPanel panel = new JPanel();
		frmManagerprocessProof.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_1 = new JLabel("Process proof");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_1, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane();
		frmManagerprocessProof.getContentPane().add(scrollPane, BorderLayout.WEST);
		
		JPanel panTree = new JPanel();
		scrollPane.setViewportView(panTree);
		panTree.setLayout(new BorderLayout(0, 0));
		
		JTree tree = new JTree();
		tree.setModel(new DefaultTreeModel(findNextTreeNode("Proofs")));
		panTree.add(tree);
		
		JPanel panel_1 = new JPanel();
		frmManagerprocessProof.getContentPane().add(panel_1, BorderLayout.CENTER);
		
		JPanel panProof = new JPanel();
		panProof.setLayout(new BorderLayout(0, 0));
		
		JLabel proofLabel = new JLabel("");
		panProof.add(proofLabel, BorderLayout.NORTH);
		
		JPanel panValidate = new JPanel();
		panValidate.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				RowSpec.decode("25px"),
				RowSpec.decode("25px"),
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
		
		JButton approve = new JButton("Approve");
		approve.setEnabled(false);
		approve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!new AbsenceDAO().getAbsenceForProof(proof).isEmpty()) {
					int returnValue = new AbsenceDAO().validateProof(proof);
					if(returnValue != 0)
						JOptionPane.showMessageDialog(frmManagerprocessProof, "Proof validated");
					else
						JOptionPane.showMessageDialog(frmManagerprocessProof, "Proof not validated");
				}
				tree.setModel(new DefaultTreeModel(findNextTreeNode("Proofs")));
			}
		});
		panValidate.add(approve, "1, 24, left, center");
		
		JButton reject = new JButton("Reject");
		reject.setEnabled(false);
		panValidate.add(reject, "1, 28, fill, center");
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addComponent(panProof, GroupLayout.DEFAULT_SIZE, 890, Short.MAX_VALUE)
					.addComponent(panValidate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addComponent(panProof, GroupLayout.DEFAULT_SIZE, 694, Short.MAX_VALUE)
				.addComponent(panValidate, GroupLayout.PREFERRED_SIZE, 694, GroupLayout.PREFERRED_SIZE)
		);
		panel_1.setLayout(gl_panel_1);
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String s = tree.getLastSelectedPathComponent().toString();
				s = s.substring(0, s.indexOf("@"));
				proof = new ProofDAO().get(Integer.parseInt(s));
				ImageIcon imageIcon = new ImageIcon(proof.getProofPath());
				Image image = imageIcon.getImage(); // transform it 
				Image newimg = image.getScaledInstance(800, 800,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
				imageIcon = new ImageIcon(newimg);  // transform it back
				proofLabel.setIcon(imageIcon);
				approve.setEnabled(true);
				reject.setEnabled(true);
			}
		});
		frmManagerprocessProof.setVisible(true);
	}
	
	/**
	 * List modules and courses from the DB in the tree.
	 *
	 */
	private DefaultMutableTreeNode findNextTreeNode(String nodeName) {
		DefaultMutableTreeNode node1 = new DefaultMutableTreeNode(nodeName);
		DefaultMutableTreeNode node2 = null;
		ArrayList<Proof> proofs = new ProofDAO().getList();
        if (proofs != null) {
            for (Proof p : proofs) {
            	node2 = new DefaultMutableTreeNode(p.getId() + "@");
    			node1.add(node2);
            }
        }
		return node1;
	}

}
