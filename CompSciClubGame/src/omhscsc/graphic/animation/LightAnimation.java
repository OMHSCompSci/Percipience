package omhscsc.graphic.animation;

import java.awt.Graphics;

import omhscsc.RenderableGameObject;
import omhscsc.util.Hitbox;

public class LightAnimation extends DrawnAnimation {

	private LightCircle[] circles;
	
	public LightAnimation(int amount) {
		super("Light_Animation", -1);
		circles = new LightCircle[amount];
		for (int i = 0; i < amount; i++) {
			circles[i] = new LightCircle();
		}
	}

	@Override
	public void render(Graphics g, int xoff, int yoff, float scale) {
		
	}

	@Override
	/**
	 * This shouldn't be used because the light animation is used for the main menu
	 */
	public Hitbox getHitbox() {
		// TODO Auto-generated method stub
		return null;
	}

}
