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
				if(game.IsIntersect(MxPos,MyPos,x.xPos,x.yPos,x.Width,x.Height)){
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
			if(x.Type.equals("Purchase")){
				if(x.Name.equals("BuyLand")){
					if(game.IsIntersect(MxPos,MyPos,x.xPos,x.yPos,x.Width,x.Height)){
							int r=game.Lands.get(0).Size;
							if(game.TotalMoney>=(40*Math.sqrt(r)*2+1)){
								r+= Math.sqrt(r)*2+1;
								game.Lands.get(0).Size=r;
								game.Lands.get(0).openSpaces=r;
								for(int z =0; z<Math.sqrt(r)*2-1;  z++){
									game.Spaces.add(new Spaces(game,game.Lands.get(0).xPos+(game.CELL_SIZE*z),game.Lands.get(0).yPos+(game.CELL_SIZE*z),game.CELL_SIZE-(game.CELL_SIZE/20),game.CELL_SIZE-(game.CELL_SIZE/20),""));
									System.out.println("HI");
								}
						}
					}

				}
			}
		}
	}
}
