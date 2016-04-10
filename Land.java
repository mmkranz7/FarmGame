import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;


public class Land {
	int xPos;
	int yPos;
	int Width;
	int Height;
	int Size;
	int openSpaces;
	public Land(GameController game){
		this(game,0,0,0,0,0,0);
	}
	public Land(GameController game,
			int xPos,
			int yPos,
			int Width,
			int Height,
			int Size,
			int openSpaces){
		this.xPos=xPos;
		this.yPos=yPos;
		this.Width=Width;
		this.Height=Height;
		this.Size=Size;
		this.openSpaces=openSpaces;
		
	}
}
