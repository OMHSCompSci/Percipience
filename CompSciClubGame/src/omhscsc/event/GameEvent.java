package omhscsc.event;

/**
 * Event is an abstract class for when anything happens. This allows in game to be modified without strict code. This may, however, not even be used.
 * @author Zach
 * 
 */
public abstract class GameEvent {

	private final long timeOfOccurance;
	protected static final long DEFAULT_TIMEOUT = (long)2e9; //2 seconds (Real world time, affected by lag);
	protected boolean cancelled;
	/**
	 * Event constructor
	 * @param time Time of event.
	 */
	public GameEvent(long time) {
		this.timeOfOccurance = time;
		cancelled = false;
	}
	
	protected void setCancelled(boolean b) {
		this.cancelled = b;
	}
	
	public boolean isCancelled() {
		return this.cancelled;
	}
	
	public long getTimeOfOccurance() {
		return this.timeOfOccurance;
	}
	
	public abstract boolean cancel();
	
}
