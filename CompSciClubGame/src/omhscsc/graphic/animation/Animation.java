package omhscsc.graphic.animation;

import java.awt.Graphics;

import omhscsc.Game;
import omhscsc.graphic.Renderable;

public abstract class Animation implements Renderable {

	protected String animationName;
	protected int maxFrames;
	protected float animationSpeed;
	protected float currentFrame;
	protected final boolean continuous;
	
	//Why is currentFrame a float? Because if we want to play this at different speeds, there will need to be an in between
	
	public Animation(String name, int frames) {
		animationName = name;
		maxFrames = frames;
		animationSpeed = 1f;
		currentFrame = 0f;
		if(maxFrames == -1)
			continuous = true;
		else
			continuous = false;
	}
	
	public void renderAndIncrement(Graphics g, int xo, int yo, float scale) {
		render(g,xo,yo,scale);
		increment();
	}
	
	public void increment() {
		currentFrame+= (1f * animationSpeed * Game.getTimeRate());
		if(currentFrame > maxFrames && !continuous) {
			currentFrame=0;
		}
	}
	
	/**
	 * Asynchronous to the Game.TIME variable.
	 */
	public void incremenAsync() {
		currentFrame+= (1f * animationSpeed);
	}
	
	/**
	 * Ignores the animationSpeed variable
	 */
	public void incrementWithoutSpeed() {
		currentFrame+= (1f * Game.getTimeRate());
	}
	
	/**
	 * Ignores all time altering variables.
	 */
	public void forceNormalIncrement() {
		currentFrame+=1;
	}
	
	
	
}
