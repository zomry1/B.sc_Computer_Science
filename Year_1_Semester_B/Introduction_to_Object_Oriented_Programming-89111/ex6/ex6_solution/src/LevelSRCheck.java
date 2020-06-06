import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class LevelSRCheck {

	public static void main(String[] args) {
		//new File ("D:\\\\Projects\\\\Intro OOP\\\\Assigment 6\\\\src\\\\highscores.ser")
		
		LevelSpecificationReader readMe = new LevelSpecificationReader();
		Reader reader = null;
		try {
			reader = new FileReader(new File("D:\\\\\\\\Projects\\\\\\\\Intro OOP\\\\\\\\Assigment 6\\\\\\\\src\\\\\\\\levels.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		readMe.fromReader(reader);

	}

}
