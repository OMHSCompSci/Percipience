package omhscsc.ui;

import omhscsc.state.GameState;

public class UIButton {

	private UIAction action;
	private String title;
	
	public UIButton(String title, UIAction action)
	{
		this.title = title;
		this.action = action;
	}
	
	public void clicked(GameState gs)
	{
		action.act(gs);
	}
	
	public String getTitle()
	{
		return title;
	}
	
	
	
}
