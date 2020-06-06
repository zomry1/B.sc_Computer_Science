import java.io.File;

public class FileReaderCheck {

	public static void main(String[] args) {
		File file = new File("D:\\\\\\\\Projects\\\\\\\\Intro OOP\\\\\\\\Assigment 6\\\\\\\\src\\\\\\\\level.txt");
		
		FileReader reader = new FileReader(file);
		reader.readFile();
	}

}
