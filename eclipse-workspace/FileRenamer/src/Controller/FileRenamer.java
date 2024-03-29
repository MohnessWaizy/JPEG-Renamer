package Controller;

import static javax.swing.JOptionPane.showMessageDialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Tag;

import View.ConfimationWindow;

/**
 * @author Mohness Waizy
 *
 */
public class FileRenamer {
	
	private FileTraverser ft;
	private List <File> files;
	private boolean recursive;
	private ConfimationWindow cw;
	private File[] farray;
	private int arrayPointer;
	

	/**
	 * @param recursive
	 * @param path
	 */
	public FileRenamer(boolean recursive, File path) {
		 this.ft  = new FileTraverser("image/jpeg");
		 this.setRecursive(recursive);
		 
		 this.ft.getFiles(path,recursive);
		 this.files = ft.getFileHolder().getFileList();
			
		 if (!this.files.isEmpty()) { 
				this.farray = new File[files.size()];
				files.toArray(farray); // fill the array
		 }
		 this.arrayPointer = 0; 
	}
	
	/**
	 * Renames files farray
	 */
	public void renameGUI() {
		
		// Reaches end of farray
		if(arrayPointer ==  farray.length) {
			showMessageDialog(null, "Done! Files have been renamed");
			return;
		}
		
		// Create new ConfimationWindow object for renaming file on position==arrayPointer in farray
		if (!files.isEmpty()) {
			cw = new ConfimationWindow(farray[arrayPointer],this);
			cw.getFrame().setResizable(false);
			cw.getFrame().setVisible(true);
		}
		else {
			showMessageDialog(null, "No JPEG files could be found..");
		}
	}
	
	/**
	 * @param f
	 * @return
	 * Preview for renaming with console
	 */
	public boolean preview(File f) {
		Scanner myObj = new Scanner(System.in);
		System.out.println("Do you want to rename "+f.getName()+"?\t[y/n]");
		String decision = myObj.nextLine();
		if (decision.equals("y")) {
			return true;
		}
		else return false;
	}
	
	/**
	 * Rename with console
	 */
	public void rename() {
		for (int i=0;i<farray.length;i++) {
			if (!files.isEmpty()) {
				
				boolean decision = preview(farray[i]);
				
			    if (decision) {
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
						imageFile = new FileInputStream(farray[i]);
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
							Files.move(farray[i].toPath(), farray[i].toPath().resolveSibling(newDateString+".jpeg"));
						}
					} 
					catch (IOException e1) {
						e1.printStackTrace();
					}
					
					// No timestamp could be found in metadata of JPEG file
					if (newDateString == null) {
						System.out.println("No timestamp found.. Skipping "+farray[i].getName()+"\n\n");
					}
				}
				else {
					System.out.println("No JPEG files could be found..");
				}
			}
		    else continue;
			
		}
		System.out.println("\n\n Done! Files have been renamed");
	}

	/**
	 * @return ft
	 */
	public FileTraverser getFt() {
		return ft;
	}

	/**
	 * @param ft
	 */
	public void setFt(FileTraverser ft) {
		this.ft = ft;
	}
	
	/**
	 * @return files
	 */
	public List<File> getFiles() {
		return files;
	}

	/**
	 * @param files
	 */
	public void setFiles(List<File> files) {
		this.files = files;
	}
	
	/**
	 * @return recursive
	 */
	public boolean isRecursive() {
		return recursive;
	}

	/**
	 * @param recursive
	 */
	public void setRecursive(boolean recursive) {
		this.recursive = recursive;
	}
	
	/**
	 * @return cw
	 */
	public ConfimationWindow getCw() {
		return cw;
	}

	/**
	 * @param cw
	 */
	public void setCw(ConfimationWindow cw) {
		this.cw = cw;
	}
	
	/**
	 * @return farray
	 */
	public File[] getFarray() {
		return farray;
	}

	/**
	 * @param farray
	 */
	public void setFarray(File[] farray) {
		this.farray = farray;
	}
	
	/**
	 * @return arrayPointer
	 */
	public int getArrayPointer() {
		return this.arrayPointer;
	}
	
	/**
	 * @param value
	 */
	public void setArrayPointer(int value) {
		this.arrayPointer = value;
	}
}	












