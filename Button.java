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
	private boolean ClickButton(Button x) {
		// TODO Auto-generated method stub
		return false;
	}
	public BufferedImage getImage(){
		return null;
	}
	Button DimButtonToShop = new Button(game,5, 25, 50, 50,Color.orange, "ToShop","DimTele","Farm",0){
		public BufferedImage getImage(){
			return game.Shop;
		}
		public boolean ClickButton(Button x){
			if(x.Type.equals("DimTele")){
			System.out.println("AFTER CHECKING IF INSIDE");
				if(x.Name.equals("ToShop")&&game.Dimension=="Farm"){
					System.out.print("SUCESS");
						game.Dimension="Shop";
						return true;
					}
			}
			return false;
		}
	};
	Button DimButtonsToFarm = new Button(game,5, 25, 50, 50,Color.orange, "ToFarm","DimTele","Shop",0){
		public BufferedImage getImage(){
			return game.Farm;
		}
		public boolean ClickButton(Button x){
			if(x.Type.equals("DimTele")){
			System.out.println("AFTER CHECKING IF INSIDE");
				if(x.Name.equals("ToFarm")&&game.Dimension=="Shop"){
					game.Dimension="Farm";
					return true;
				}
			}
			return false;
		}
	};
	
	Button SellButtons = new Button(game,275, 400, 50, 50,Color.orange, "SellStuff","Purchase","Shop",0){
		public BufferedImage getImage(){
			return game.SellThings;
		}
		public boolean ClickButton(Button x){
			if(x.Name.equals("SellStuff")){
			for(Yields z : game.TotalYield){
				if(z.Type=="BLUEBERRY"){
					for(int i=0; i<z.Amount;i++){
						game.TotalMoney+=5;
					}
					z.Amount=0;
				}
			}
			}
			return false;
		}
	};
	Button PurchaseButtons = new Button(game,200, 400, 50, 50,Color.orange, "BuyLand","Purchase","Shop",0){
		public BufferedImage getImage(){
			return game.MoreLand;
		}
		public boolean ClickButton(Button x){
			if(x.Type.equals("Purchase")&&game.Dimension.equals("Shop")){
				if(x.Name.equals("BuyLand")){
					BuyLand(x);
				}
			}
			return false;
		}
		public void BuyLand(Button x){
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
	};
	
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

