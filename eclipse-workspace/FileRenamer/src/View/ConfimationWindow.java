package View;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Tag;

import Controller.FileRenamer;

public class ConfimationWindow {

	private JFrame frame;
	private File f;
	private FileRenamer fr;

	/**
	 * Create the application.
	 */
	public ConfimationWindow(File file, FileRenamer fr) {
		this.f = file;
		this.fr = fr;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("JPEG File Renamer");
		frame.setResizable(false);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton skipButton = new JButton("Skip");
		skipButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		skipButton.setBounds(70, 212, 95, 23);
		frame.getContentPane().add(skipButton);
		
		JButton renameButton = new JButton("Rename");
		renameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// For reference check: https://docs.oracle.com/javase/tutorial/i18n/format/simpleDateFormat.html
				// YYYY	-> Year
				// MM	-> Month in year
				// DD	-> Day in year
				// hh	-> Hour in am/pm (1-12)
				// ss	-> Second in minute
				
				//JPEG file as input stream
				InputStream imageFile = null;
				// All metadata of JPEG file
				com.drew.metadata.Metadata metadata = null;
				// Timestamp from JPEG file metadata
				String date = null;
				// Formated timestamp
				Date fdate = null;
				// Formater that formats date in fdate
		    	SimpleDateFormat formater = new SimpleDateFormat("YYYY-MM-DD hh"+'\uA789'+"ss");
		    	// New JPEG file name
		    	String newDateString = null;
				
				// Creates input stream from JPEG file f
				try {
					imageFile = new FileInputStream(f);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				
				// Reads metadata from input stream
				try {
					metadata = ImageMetadataReader.readMetadata(imageFile);
				} catch (ImageProcessingException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				// Extracts timestamp and saves it to date
			    for (Directory directory : metadata.getDirectories()) {
					for (Tag tag : directory.getTags()) {
				    	if (tag.getTagName().equals("Date/Time Original")) {
				    		date = tag.getDescription().toString();
				    	}
					}
			    }
		    	
		    	// Parses timestamp and saves it in fdate
		    	try {
		    		if (date != null) {
		    			fdate=new SimpleDateFormat("yyyy:MM:dd HH:mm:ss").parse(date);
		    		}
				} catch (ParseException e1) {
					e1.printStackTrace();
				}  
		    	
		    	// Creates new file name from fdate
				try {
					if (fdate != null) {
						newDateString = formater.format(fdate);
					}
				} catch (Exception e3) {
					e3.printStackTrace();
				}
		    	
				// Closes input stream
		    	try {
					imageFile.close();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				
		    	// Changes file name to value of newDateString
				try {
					if (newDateString != null) {
						Files.move(f.toPath(), f.toPath().resolveSibling(newDateString+".jpeg"));
					}
				} 
				catch (IOException e1) {
					e1.printStackTrace();
				}
				
				// No timestamp could be found in metadata of JPEG file
				if (newDateString == null) {
					showMessageDialog(null, "No timestamp found.. Skipping "+f.getName());
				}
				
				frame.dispose();
				
				// Rename next JPEG file
				fr.setArrayPointer(fr.getArrayPointer()+1);
				fr.renameGUI();
				
			}
		});
		renameButton.setBounds(270, 212, 95, 23);
		frame.getContentPane().add(renameButton);
		
		JLabel informationLabel = new JLabel("<html><div style='text-align: center;'>You are renaming: <br><br>"+f.getName()+"</div></html>", SwingConstants.CENTER);
		informationLabel.setBounds(10, 39, 428, 162);
		frame.getContentPane().add(informationLabel);
		
		frame.getRootPane().setDefaultButton(renameButton);
	}
	
	public JFrame getFrame() {
		return frame;
	}
}
