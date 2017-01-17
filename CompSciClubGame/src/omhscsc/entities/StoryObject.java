package omhscsc.entities;
/**
 * An interface to add locations relevant to the story
 * @author Morgan
 *
 */
public interface StoryObject {
	/**
	 * Returns the serial of the story piece here
	 */
	public int getStorySerial();
	/**
	 * Returns the part of the story set at this location
	 */
	public String getStory();
}
