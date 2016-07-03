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
	String Type;
	String Dim;
	BufferedImage Image;
	public Button(GameController game){
		this(game,0,0,0,0,Color.red,"","","",null);
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
			BufferedImage Image){
		this.game=game;
		this.xPos=xPos;
		this.yPos=yPos;
		this.Height=Height;
		this.Width=Width;
		this.boxColor =boxColor;
		this.Name = Name;
		this.Type=Type;
		this.Image=Image;
		this.Dim=Dim;
	}
	public void ClickedOn(int MxPos, int MyPos,GameController game){
		System.out.println("RAN");
		for(Button x : game.buttons){
			if(x.Type.equals("DimTele")){
				System.out.println("BEFORE CHECKING IF INSIDE");
				if((MxPos>=x.xPos-5&&MxPos<=x.xPos-5+x.Width+10)&&(MyPos>=x.yPos-5&&MyPos<=x.yPos-5+x.Height+10)){
					System.out.println("AFTER CHECKING IF INSIDE");
					if(x.Name.equals("ToShop")&&game.Dimension=="Farm"){
						System.out.print("SUCESS");
						game.Dimension="Shop";
						break;
					}
					if(x.Name.equals("ToFarm")&&game.Dimension=="Shop"){
						game.Dimension="Farm";
						break;
					}
				}
			}
			if(Type.equals("Purchase")){
				
			}
		}
	}
}
