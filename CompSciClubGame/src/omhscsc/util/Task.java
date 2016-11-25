package omhscsc.util;

import omhscsc.GameObject;
import omhscsc.state.GameStateState;

/**
 * A <em>Task</em> is something that needs to be done, but maybe not immediately. Maybe, without a Task, an exception will occur.
 * The point of this class is to perform some "Tasks" that wouldn't be able to be completed otherwise in the current state of the game engine.
 * After a set delay of ticks, the {@link #run()} method will be called.
 * @author xDestx
 *
 */
public class Task extends GameObject {

	private int ticksToWait,currentTicksWaited;
	
	public Task(int ticksToWait)
	{
		this.ticksToWait = ticksToWait;
		this.currentTicksWaited = 0;
	}
	
	@Override
	public void tick(GameStateState s) {
		if(currentTicksWaited >= ticksToWait)
		{
			run(s);
		}
		currentTicksWaited++;
	}
	
	/**
	 * This method is to be overridden with the specific code wanted to be executed.
	 * 
	 */
	public void run(GameStateState s)
	{
		
	}
	
	
	public boolean isTaskComplete() {
		return ticksToWait - currentTicksWaited <= 0;
	}

}
