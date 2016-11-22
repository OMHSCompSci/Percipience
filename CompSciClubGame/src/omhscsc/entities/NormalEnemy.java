package omhscsc.entities;

import java.awt.Graphics;
//Generic enemy; has mostly default methods from Enemy
import omhscsc.state.GameStateState;
import omhscsc.util.Hitbox;

public class NormalEnemy extends Enemy{
	public NormalEnemy(double mH, boolean b, Hitbox h) {
		super(mH,b,h);
	}
	public void tick(GameStateState g) {
	}
	public void render(Graphics g, int x, int y) {
		
	}
}
