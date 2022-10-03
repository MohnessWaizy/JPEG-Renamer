package Controller;


import java.io.File;
import java.util.Scanner;

import View.MainWindow;

/**
 * @author Mohness Waizy
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		/*
		 * Starts application with GUI
		 */
		MainWindow w = new MainWindow();
		w.getFrame().setResizable(false);
		w.getFrame().setVisible(true);
		
	
		
		/*
		 * Start application with console
		 */
//		boolean recursive = false;
//		
//	    Scanner myObj = new Scanner(System.in);
//	    System.out.println("Please enter your directory path:");
//	    String path = myObj.nextLine();
//	    File f = new File(path);
//	    System.out.println("Do you want to rename recursivly?\t[y/n]");
//	    String recurS = myObj.nextLine();
//	    if (recurS.equals("y")) recursive = true;
//	    else if (recurS.equals("n")) recursive = false;
//	    else {
//	    	System.out.println("Wrong input..");
//	    	return;
//	    }
//	    
//		FileRenamer fr = new FileRenamer(recursive,f);
//		
//		fr.rename();
		
	}
}

