package omhscsc.items;

import omhscsc.GameObject;
import omhscsc.state.GameStateState;

public class Upgrade extends GameObject {
	private char type;
	public Upgrade(char t){
		type = t;
	}
	public void tick(GameStateState gs) {
		

	}
	public char type(){
		return type;
	}


}
