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
import omhscsc.util.Hitbox;
import omhscsc.util.ImageLoader;

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
	
	

}
