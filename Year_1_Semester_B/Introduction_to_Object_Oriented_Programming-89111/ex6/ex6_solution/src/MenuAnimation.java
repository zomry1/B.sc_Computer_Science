import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import animations.AnimationRunner;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

public class MenuAnimation<T> implements Menu<T> {
	private List<T> tasks;
	private List<String> keys;
	private List<String> messages;
	private AnimationRunner ar;
	private KeyboardSensor ks;
	private T status;
	private boolean choosed;
	
	public MenuAnimation(AnimationRunner ar,KeyboardSensor ks) {
		this.ar = ar;
		this.ks = ks;
		this.tasks = new ArrayList<T>();
		this.keys = new ArrayList<String>();
		this.messages = new ArrayList<String>();
	}
	
	
	public void doOneFrame(DrawSurface d, double dt) {
		//
		String title = "menu";
		d.setColor(Color.LIGHT_GRAY);
	    d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
	    

	    d.setColor(Color.BLACK);
	    d.drawText(51, 50, title, 32);
	    d.drawText(49, 50, title, 32);
	    d.drawText(50, 51, title, 32);
	    d.drawText(50, 49, title, 32);
	    d.setColor(Color.YELLOW);
	    d.drawText(50, 50, title, 32);
	    

	    for (int i = 0; i < this.messages.size(); i++) {
	      int optionX = 100;
	      int optionY = 120 + i * 40;
	      String optionText = "(" + (String)this.keys.get(i) + ") " + (String)this.messages.get(i);
	      
	      d.setColor(Color.BLACK);
	      d.drawText(optionX + 1, optionY, optionText, 24);
	      d.drawText(optionX - 1, optionY, optionText, 24);
	      d.drawText(optionX, optionY + 1, optionText, 24);
	      d.drawText(optionX, optionY - 1, optionText, 24);
	      
	      d.setColor(Color.GREEN);
	      d.drawText(optionX, optionY, optionText, 24);
		
		//Draw the menu
		for(String key: this.keys) {
			if(this.ks.isPressed(key)) {
				this.status = this.tasks.get(this.keys.indexOf(key));
				this.choosed = true;
				break;
			}
		}
	}
	}

	public boolean shouldStop() {
		if(this.choosed) {
			this.choosed = false;
			return true;
		}
		return false;
	}

	
	public void addSelection(String key, String message, T returnVal) {
		this.tasks.add(returnVal);
		this.keys.add(key);
		this.messages.add(message);
	}


	public T getStatus() {
		return this.status;
	}

}
