package View;

import static javax.swing.JOptionPane.showMessageDialog;

import javax.swing.JFrame;
import javax.swing.JTextField;

import Controller.FileRenamer;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JCheckBox;

public class MainWindow {

	private JFrame frame;
	private JTextField textFieldPath;
	
	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("JPEG File Renamer");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textFieldPath = new JTextField();
		textFieldPath.setToolTipText("");
		textFieldPath.setBounds(10, 143, 418, 20);
		frame.getContentPane().add(textFieldPath);
		textFieldPath.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Please enter folder path:");
		lblNewLabel.setBounds(10, 109, 418, 23);
		frame.getContentPane().add(lblNewLabel);
		
		JCheckBox checkBoxRecursive = new JCheckBox("Recursive");
		checkBoxRecursive.setBounds(10, 170, 99, 23);
		frame.getContentPane().add(checkBoxRecursive);
		
		JButton renameButton = new JButton("Rename");
		renameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Gets directory path
				File path = new File(textFieldPath.getText());
				
				// Initiate first renaming 
				if (path.exists()){
					FileRenamer fr = new FileRenamer(checkBoxRecursive.isSelected(), path);
					fr.renameGUI();
				}
				else {
					showMessageDialog(null, "Please enter a valid path..");
				}
				frame.dispose();
				
			}
		});
		renameButton.setBounds(339, 231, 89, 23);
		frame.getContentPane().add(renameButton);
		
		JLabel lblNewLabel_1 = new JLabel("JPEG File Renamer");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblNewLabel_1.setBounds(126, 11, 190, 34);
		frame.getContentPane().add(lblNewLabel_1);
	
	frame.getRootPane().setDefaultButton(renameButton);
	}
	
	public JFrame getFrame() {
		return frame;
	}
}
