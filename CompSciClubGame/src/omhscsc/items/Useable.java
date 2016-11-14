package omhscsc.items;

import omhscsc.Game;
import omhscsc.GameObject;
import omhscsc.state.GameStateState;

public class Useable extends GameObject {
	private char type;

	public Useable(char t){
		type = t;
	}
	public void tick(GameStateState gs) {
		

	}
	public char getType(){
		return type;
	}


}
