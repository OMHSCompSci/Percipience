package omhscsc.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import omhscsc.GameObject;
import omhscsc.graphic.Renderable;
import omhscsc.items.Upgrade;
import omhscsc.items.Useable;
import omhscsc.state.GameStateState;
import omhscsc.util.ImageLoader;
import omhscsc.util.geometry.Hitbox;

public abstract class Enemy extends LivingEntity {
	
	//private boolean isBoss;
	
	//private double tickCounter;
	//What is that for again^
	
	public Enemy(double mH, Hitbox h){
		super(h);
		maxHealth = mH;
		health = maxHealth;
	}
	public void takeDmg(double dmg) {
		health-=dmg;
	}
	
	public double getCurrentHp(){
		return health;
	}
	public double getMaxHp(){
		return maxHealth;
	}
	
	@Override
	public void render(Graphics g, int xoff, int yoff)
	{
		//Placeholder rendering
		try {
			g.drawImage(ImageIO.read(new File("source/image/ghost.png")), xoff, yoff, 50, 50, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//super.render(g, xoff, yoff);
	}

}
