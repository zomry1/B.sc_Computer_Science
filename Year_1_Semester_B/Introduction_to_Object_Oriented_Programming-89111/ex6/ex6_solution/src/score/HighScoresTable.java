package score;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HighScoresTable implements Serializable {
	private int size;
	private List<ScoreInfo> scores;

	/**
	 * Create an empty high-scores table with the specified size.
	 * @param size - means that the table holds up to size top scores.
	 */
	public HighScoresTable(int size) {
		this.scores = new ArrayList<ScoreInfo>();
		this.size = size;
	}
	
	/**
	 * Add a new score to the table.
	 * @param score - the score we want to add
	 */
	public void add(ScoreInfo score) {
		if(this.scores.isEmpty()) {
			this.scores.add(score);
			return;
		}
		int place = this.getRank(score.getScore());
		this.scores.add(place, score);
		
		if(this.scores.size() > this.size) {
			this.scores.remove(this.size);
		}

	}
	
	
	/**
	 * @return - the size of the table.
	 */
	public int size() {
		return this.size;
	}
	
	/**
	 *  The list is sorted such that the highest scores come first.
	 * @return -  Return the current high scores.
	 */
	public List<ScoreInfo> getHighScores(){
		return this.scores;
	}
	
	/**
	 * Return the rank of the given score (where will it be on the list.
	 * @param score - the score we want to check
	 * @return  - the rank of the given score (where will it be on the list
	 * 1 - this is the highest score, size - the lowest score. size + 1 not on the list
	 */
	public int getRank(int score) {
		int i = 1;
		for (ScoreInfo currScore: this.scores) {
			if(score > currScore.getScore()) {
				return i;
			}
		}
		return i;
	}
	
	/**
	 * Clear the table.
	 */
	public void Clear() {
		this.scores.clear();
	}
	
	public void save(File filename) throws IOException {
		ObjectOutputStream  objectOut = null;
		
		try {
			objectOut = new ObjectOutputStream(
										new FileOutputStream(filename));
			objectOut.writeObject(this);
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if(objectOut != null) {
					objectOut.close();
				}
			} catch (IOException e) {
				throw e;
			}
		}
	}
	
	public void load(File filename) throws IOException{
		this.Clear();
		
		ObjectInputStream objectIn = null;
		HighScoresTable temp = null;
		
		try {
			objectIn = new ObjectInputStream(
										new FileInputStream(filename));
			
			temp = (HighScoresTable) objectIn.readObject();
			this.scores = temp.getHighScores();
			this.size = temp.size;
		} catch (IOException e) {
			throw e;
		} catch (ClassNotFoundException e) {
			System.err.println("Unable to find calss for object in the file: " + filename);
			e.printStackTrace();
		} finally {
			try {
				if(objectIn != null) {
					objectIn.close();
				}
			} catch (IOException e) {
				throw e;
			}
		}
		System.out.println("load is done");
	}
	
	public void printTable() {
		int i = 1;
		for (ScoreInfo currScore: this.scores) {
			System.out.println("RANK(" + i + ") - name: " + currScore.getName() + " score: " + currScore.getScore());
			i++;
		}
	}
	
	public static HighScoresTable loadFromFile(File filename) {
		HighScoresTable temp = new HighScoresTable(5);
		try {
			temp.load(filename);
			return temp;
		} catch (IOException e) {
			return temp;
		}
	}
	
}
