import java.io.File;
import java.io.IOException;

import score.HighScoresTable;
import score.ScoreInfo;

public class TableCheck {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		HighScoresTable table = new HighScoresTable(4);
		ScoreInfo one = new ScoreInfo("omry", 120);
		ScoreInfo two = new ScoreInfo("wazzzup", 130);
		ScoreInfo three = new ScoreInfo("so close!", 125);
		table.add(two);
		table.add(one);
		table.add(three);
		System.out.println("before save table: ");
		table.printTable();
		
		table.save(new File ("D:\\\\Projects\\\\Intro OOP\\\\Assigment 6\\\\src\\\\highscores.ser"));
		table.load(new File ("D:\\\\Projects\\\\Intro OOP\\\\Assigment 6\\\\src\\\\highscores.ser"));
		System.out.println("after load table: ");
		table.printTable();
	}

}
