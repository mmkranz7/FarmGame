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
	BufferedImage Image;
	int price;
	public Button(GameController game){
		this(game,0,0,0,0,Color.red,"","","",null,0);
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
			BufferedImage Image,
			int price){
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
		this.price=price;
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
			if(x.Type.equals("Purchase")&&game.Dimension.equals("Shop")){
				if(x.Name.equals("BuyLand")){
					if(game.IsIntersect(MxPos,MyPos,x.xPos,x.yPos,x.Width,x.Height)){
							int r=game.Lands.get(0).Size;
							x.price=(int) (40*Math.sqrt(r)*2+1);
							if(game.TotalMoney>=x.price){
								game.TotalMoney-=x.price;
								r+= Math.sqrt(r)*2+1;
								game.Lands.get(0).Size=r;
								game.Lands.get(0).openSpaces=r;
								for(int z =0; z<Math.sqrt(r)*2-1;  z++){
									game.Spaces.add(new Spaces(game,game.Lands.get(0).xPos+(game.CELL_SIZE*z),game.Lands.get(0).yPos+(game.CELL_SIZE*z),game.CELL_SIZE-(game.CELL_SIZE/20),game.CELL_SIZE-(game.CELL_SIZE/20),""));
								}
						}
					}

				}else if(x.Name.equals("SellStuff")){
					if(game.IsIntersect(MxPos,MyPos,x.xPos,x.yPos,x.Width,x.Height)){
						for(Yields z : game.TotalYield){
							if(z.Type=="BLUEBERRY"){
								for(int i=0; i<z.Amount;i++){
									game.TotalMoney+=5;
								}
								z.Amount=0;
							}
						}
					}
				}
			}
		}
	}
}
