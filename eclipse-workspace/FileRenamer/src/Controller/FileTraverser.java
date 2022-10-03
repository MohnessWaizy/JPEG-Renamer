package Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import Model.FileHolder;

/**
 * @author Mohness Waizy
 *
 * Traverses files in given directory path an saves <fileType> files in 
 * FileHolder object
 */
public class FileTraverser {
	
	private FileHolder fileHolder;
	private String fileType = "Undefined";

	/**
	 * @param fileType
	 */
	public FileTraverser(String fileType) {
		fileHolder = new FileHolder();
		this.fileType = fileType;
	}
	
	/**
	 * @param path
	 * @param recursive
	 * 
	 * Traverses files in given path and saves <fileType> files in FileHolder object
	 */
	public void getFiles(File path, boolean recursive) {
		
		if (path.listFiles() != null) {
		
			for (File file : path.listFiles()) {
				String probeFileType = "Undefined";
				
				// Gets type of file
				try {
					if (null != Files.probeContentType(file.toPath())) {
						probeFileType = Files.probeContentType(file.toPath());
					}
				} catch (IOException e) {
					continue;
				}
				
				// If file is a directory and recursive==true --> recursive call with path of directory
				if (file.isDirectory() && recursive) {
					getFiles(file, recursive);
				}
				
				// If file type == <fileType> add it to FileHolder object
				if (file.isFile() && probeFileType.equals(this.fileType)) {
			    	fileHolder.addFileToFileList(file);
			    }
			}
		}
			

	}
	
	/**
	 * @return fileHolder
	 */
	public FileHolder getFileHolder() {
		return this.fileHolder;
	}
}
