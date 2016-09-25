import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


public class Button {
	GameController game;
	String Name;
	int xPos;
	int yPos;
	int Height;
	int Width;
	Color boxColor;
	int LandPrice;
	String Type;
	String Dim;
	int price;
	public Button(GameController game){
		this(game,0,0,0,0,Color.red,"","","",0);
	}
	public Button(GameController game,
			int xPos,
			int yPos,
			int Height,
			int Width,
			Color boxColor,
			String Name,
			String Type,
			String Dim,
			int price){
		this.game=game;
		this.xPos=xPos;
		this.yPos=yPos;
		this.Height=Height;
		this.Width=Width;
		this.boxColor =boxColor;
		this.Name = Name;
		this.Type=Type;
		this.Dim=Dim;
		this.price=price;
	}
	public boolean ClickButton(Button x) {
		System.out.print("hi");
				return false;
	}
	public BufferedImage getImage(){
		return null;
	}
	
		
	public void ClickedOn(int MxPos, int MyPos,GameController game){
		System.out.println("RAN");
		for(Button x : game.buttons){
					if(game.IsIntersect(MxPos,MyPos,x.xPos,x.yPos,x.Width,x.Height)){
						if(x.ClickButton(x)){
							break;
						}
					}
				}
			}
	}

