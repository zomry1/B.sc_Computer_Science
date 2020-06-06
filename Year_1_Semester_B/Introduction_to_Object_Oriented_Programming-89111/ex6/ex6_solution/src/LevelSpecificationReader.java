import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

import game.mechanics.Velocity;
import levels.LevelInformation;

public class LevelSpecificationReader {

	public List<LevelInformation> fromReader(java.io.Reader reader) {
		List<String> text = new ArrayList<String>();
		List<LevelInformation> levels = new ArrayList<LevelInformation>();
		try {
			BufferedReader lineReader = new BufferedReader(reader);
			String line = lineReader.readLine();
			
			while(line != null) {
				text.add(line);
				
				line = lineReader.readLine();
			}
		}
		catch(FileNotFoundException e) {
			System.err.println("Unable to find file.");
		}
		catch(IOException e) {
            System.err.println("Failed reading file" + ", message:" + e.getMessage());
            e.printStackTrace(System.err);
		}
		int lineStartLevel = 0;
		int lineEndLevel = 0;
		int currLine = 0;
		boolean startFlag = false;
		boolean endFlag = false;
		for(String line: text) {
			if(line.equals("START_LEVEL")) {
				lineStartLevel = currLine;
				startFlag = true;
			}
			if(line.equals("END_LEVEL")) {
				lineEndLevel = currLine;
				endFlag = true;
			}
			
			if(startFlag && endFlag) {
				//LevelInformation level = this.levelReader(text.subList(lineStartLevel, lineEndLevel));
				this.levelReader(text.subList(lineStartLevel, lineEndLevel));
				System.out.println("end of level\n");
				//levels.add(level);
				startFlag = false;
				endFlag = false;
			}
			
			currLine++;
		}
		
		return levels;

	}
	
	private void levelReader(List<String> text) {
		
		String levelName = "";
		List<Velocity> velocities = new ArrayList<>();
		int paddleSpeed = -1;
		int paddleWidth = -1;
		int blockX = -1;
		int blockY = -1;
		int blockRow = -1;
		int blockNum = -1;
		String blocksText = "";
		boolean isImage = false;
		String background = "";
		
		for(String line: text) {
			
			//For level name
			if(line.startsWith("level_name:")) {
				levelName = line.substring(line.indexOf(":"));
			}
			
			//For Ball Velocities
			if(line.startsWith("ball_velocities:")) {
				velocities = this.velocitiesText(line.substring(line.indexOf(":") + 1));
			}

			//Paddle speed
			if(line.startsWith("paddle_speed:")) {
				paddleSpeed = Integer.parseInt(line.substring(line.indexOf(":") + 1));
			}

			if(line.startsWith("paddle_width:")){
				paddleWidth = Integer.parseInt(line.substring(line.indexOf(":") + 1));
			}

			if(line.startsWith("blocks_start_x:")){
				blockX = Integer.parseInt(line.substring(line.indexOf(":") + 1));
			}

			if(line.startsWith("blocks_start_y:")){
				blockY = Integer.parseInt(line.substring(line.indexOf(":") + 1));
			}

			if(line.startsWith("row_height:")){
				blockRow = Integer.parseInt(line.substring(line.indexOf(":") + 1));
			}

			if(line.startsWith("num_blocks:")){
				blockNum = Integer.parseInt(line.substring(line.indexOf(":") + 1));
			}

			if(line.startsWith("block_definitions:")){
				blocksText = line.substring(line.indexOf(":") + 1);
			}
			
			if(line.startsWith("background:")) {
				if(line.startsWith("image", 11)) {
					isImage = true;
					background = line.substring(line.indexOf("(") + 1, line.indexOf(")"));
				}
				if(line.startsWith("color", 11)) {
					isImage = false;
					background = line.substring(line.indexOf("(") + 1, line.indexOf(")"));
				}
			}
		}

		if(paddleSpeed + paddleWidth + blockX + blockY + blockRow + blockNum < 0 || blocksText.isEmpty() || background.isEmpty() || levelName.isEmpty() ) {
			//Return error
		}
		
		System.out.println("name: " + levelName);
		System.out.println("paddle speed: " + paddleSpeed);
		System.out.println("paddleWidth: " + paddleWidth);
		System.out.println("block x: " + blockX);
		System.out.println("block y: " + blockY);
		System.out.println("row height: " + blockRow);
		System.out.println("block num: " + blockNum);
		System.out.println("block text: " + blocksText);
		if(isImage) {
			System.out.println("image background: " + background);
		} else {
			System.out.println("color background: " + background);
		}
		int i = 1;
		for(Velocity vel: velocities) {
			System.out.println("velocity " + i + ": " + vel.getSpeed());
			i++;
		}
	}
	
    private  List<Velocity> velocitiesText (String velocities) {
    	List<Velocity> velocitiesReturn = new ArrayList<Velocity>();
    	String angle;
    	String speed;
    	String[] vels = velocities.split(" ");
    	for(String vel: vels) {
    		angle = vel.substring(0, vel.indexOf(","));
    		speed = vel.substring(vel.indexOf(",") + 1);
    		
    		velocitiesReturn.add(Velocity.fromAngleAndSpeed(Double.parseDouble(angle), Double.parseDouble(speed)));
    	}
    	return velocitiesReturn;
    }
}
