package omhscsc.sound;

public enum Sound {

	/*
	 * All default sounds will be an enum here
	 */
	TITLE("TITLE_MUSIC","/sound/main_title.wav");
	
	private final String name, path;
	Sound(String name, String path)
	{
		this.name= name;
		this.path = path;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public String getPath()
	{
		return this.path;
	}
	
	@Override
	public String toString()
	{
		return "Enum Sound: " + this.getName() + " loc: " + this.getPath();
	}
}
