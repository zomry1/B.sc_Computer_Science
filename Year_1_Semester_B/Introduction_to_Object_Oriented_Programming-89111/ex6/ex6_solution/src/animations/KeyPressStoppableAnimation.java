package animations;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

public class KeyPressStoppableAnimation implements Animation {
	KeyboardSensor ks;
	String keyString;
	Animation animation;
	boolean isAlreadyPressed;
	boolean stop;

	public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
		this.ks = sensor;
		this.keyString = key;
		this.animation = animation;
		if(this.ks.isPressed(this.keyString)) {
			this.isAlreadyPressed = true;
		}
	}
	public void doOneFrame(DrawSurface d, double dt) {
		this.animation.doOneFrame(d, dt);
		if(this.isAlreadyPressed == false) {
	        if (this.ks.isPressed(this.keyString)) {
	        	this.stop =  true;
	        	return;
	        }
	        this.stop = false;
		}
		else {
			if(this.ks.isPressed(this.keyString) == false) {
				this.isAlreadyPressed = false;
			}
		}
	}

	
	public boolean shouldStop() {
		return this.stop;
	}

}
