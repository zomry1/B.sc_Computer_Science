import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileReader {
	
	private File filename;
	
	public FileReader(File filename) {
		this.filename = filename;
	}
	public List<String> readFile() {
		List<String> text = new ArrayList<String>();
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
			
			String line = reader.readLine();
			
			while(line != null) {
				text.add(line);
				
				line = reader.readLine();
			}
			return text;
		}catch(FileNotFoundException e) {
			System.err.println("Unable to find file: " + filename.getName());
		}catch(IOException e) {
			System.err.println("Failed reading file: " + filename.getName() + ", message: " + e.getMessage());
			e.printStackTrace(System.err);
		} finally {
			try {
				if(reader != null) {
					reader.close();
				}
			} catch(IOException e) {
				System.err.println("Failed closing file: " + filename.getName());
			}
		}
		return null;
	}
	
	public void blockDefinitions() {
		List<String> text = this.readFile();
		for(String line: text) {
			if(line.startsWith("#")) {
				text.remove(line);
			}
			if(line.startsWith("bdef")) {
				//Bdefinision = blockDefinision(line);
			}
			if(line.startsWith("sdef")) {
				//Sdefinistion = spacerDefinision(line);
			}
			
		}
	}
}
