package notebank.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtility {
	
	public static FileReader getFileReader(String filename) {
		FileReader fReader = null;
		try {
			fReader = new FileReader(filename);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // better error handling here
		return fReader;
	}
	//limit title/filename to 100chars?
	public static void saveFile() {
		//TODO
		//save file to computer before adding to database
	}
	
	//may not use this?
	public static void writeToFile(File file, String text) throws IOException {
		FileWriter fWriter = new FileWriter(file);
		
		fWriter.write(text);
		
		fWriter.close();
	}
}
