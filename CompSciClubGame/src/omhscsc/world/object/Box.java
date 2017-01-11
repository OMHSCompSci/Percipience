package omhscsc.world.object;

import java.awt.Color;
import java.awt.Graphics;

import omhscsc.state.GameStateState;
import omhscsc.world.WorldObject;

public class Box extends WorldObject {

	/**
	 * Create a box with the specified hitbox and color
	 * @param x x pos
	 * @param y y pos
	 * @param w width
	 * @param h height
	 * @param c color
	 */
	public Box(int x, int y, int w, int h, Color c) {
		super(x, y, w, h, c);	
	}
	
	/**
	 * Create a Box with the specified hitbox and image
	 * @param x x pos
	 * @param y y pos
	 * @param w width
	 * @param h height
	 * @param c Image path for {@link omhscsc.util.ImageLoader#getImage(String)}
	 */
	public Box(int x, int y, int w, int h, String c) {
		super(x, y, w, h, c);	
	}


	@Override
	public void tick(GameStateState s) {
		//Do nothing (Basic box)
	}

}
