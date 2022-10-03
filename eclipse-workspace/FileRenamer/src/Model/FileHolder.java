package Model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mohness Waizy
 *
 */
public class FileHolder {
	
	private List <File> fileList;
	
	/**
	 * Holds <fileType> files
	 */
	public FileHolder() {
		fileList = new ArrayList<File>();
	}
	
	/**
	 * @return fileList
	 */
	public List <File> getFileList() {
		return fileList;
	}
	
	/**
	 * @param file
	 * 
	 * Adds given file to fileList
	 */
	public void addFileToFileList(File file) {
		this.fileList.add(file);
	}
}
