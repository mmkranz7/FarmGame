
public class Spaces {
	GameController game;
	int xPos;
	int yPos;
	int Width;
	int Height;
	String Type;

	public Spaces(GameController game){
		this(game,0,0,0,0,"");
	}
	public Spaces(GameController game,
			int xPos,
			int yPos,
			int Width,
			int Height,
			String Type){
		this.xPos=xPos;
		this.yPos=yPos;
		this.Width=Width;
		this.Height=Height;
		this.Type=Type;

	}
}