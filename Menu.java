import java.awt.image.BufferedImage;


public class Menu {
	GameController game;
	int xPos;
	int yPos;
	int Width;
	int Height;
	int plantnum;
	boolean Visible;
	String text;
	BufferedImage Image;
	Planter planter = new Planter(game);

	public Menu(GameController game){
		this(game,0,0,0,0,0,"",null,false);
	}
	public Menu(GameController game,
			int xPos,
			int yPos,
			int Width,
			int Height,
			int plantnum,
			String text,
			BufferedImage Image,
			boolean Visible){
		this.xPos=xPos;
		this.yPos=yPos;
		this.Width=Width;
		this.Height=Height;
		this.text=text;
		this.plantnum=plantnum;
		this.Visible=Visible;
		this.Image=Image;
	}
	public void ClickonButton(int MxPos, int MyPos, GameController game){
		Plants plant = new Plants(game);
		for(Menu x : game.menu){
			System.out.println(plant.Type);
			
			if(game.IsIntersect(MxPos,MyPos,x.xPos,x.yPos,x.Width,x.Height)&&game.Dimension.equals("Farm")){
				plant= plant.Clone(game.Plants.get(x.plantnum));
				System.out.println(plant.Type);
				game.selectedPlant= plant;
				System.out.print(plant.Type);
				game.selectedImage=x.Image;			
			}
			if(game.IsIntersect(MxPos,MyPos,x.xPos,x.yPos,x.Width,x.Height)&&game.Dimension.equals("Shop")){
				if(game.TotalMoney>game.seeds.get(x.plantnum).Price){
					game.seeds.get(x.plantnum).Amount=game.seeds.get(x.plantnum).Amount+20;
					game.
					TotalMoney-=game.seeds.get(x.plantnum).Price;
				}
			
			}
			
			if(plant!=null&&game.Selected.equals("Planter")){
				System.out.println(plant.Type);
//				planter.InsertPlant(MxPos, MyPos, game,plant);
			}
		}

	}
}
