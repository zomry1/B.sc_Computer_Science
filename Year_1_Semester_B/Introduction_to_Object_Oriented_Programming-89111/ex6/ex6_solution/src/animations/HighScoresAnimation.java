package animations;
import java.awt.Color;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import score.HighScoresTable;
import score.ScoreInfo;

public class HighScoresAnimation implements Animation {
	private HighScoresTable scores;
	
	public HighScoresAnimation(HighScoresTable scores) {
		this.scores = scores;
	}

	
	public void doOneFrame(DrawSurface d, double dt) {
		//Background and frame
        d.setColor(new Color(105, 105, 105));
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        for (int i = 12; i > 0; i--) {
            d.setColor(new Color(255 - i * 5, 255 - i * 5, 255 - i * 5));
            d.drawRectangle(0, 0, d.getWidth() - i, d.getHeight() - i);
        }
		
		//Draw the table
		int i = 0;
		for(ScoreInfo score: this.scores.getHighScores()) {
			d.drawText(200, 150 + i, score.getName(), 20);
			d.drawText(260, 150 + i, String.valueOf(score.getScore()), 20);
			i += 30;
		}
	}

	
	public boolean shouldStop() {
		return true;
	}

}
