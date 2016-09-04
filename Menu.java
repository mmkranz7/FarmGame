
public class Menu {
	GameController game;
	int xPos;
	int yPos;
	int Width;
	int Height;
	int plantnum;
	String text;
	Planter planter = new Planter(game);

	public Menu(GameController game){
		this(game,0,0,0,0,0,"");
	}
	public Menu(GameController game,
			int xPos,
			int yPos,
			int Width,
			int Height,
			int plantnum,
			String text){
		this.xPos=xPos;
		this.yPos=yPos;
		this.Width=Width;
		this.Height=Height;
		this.text=text;
		this.plantnum=plantnum;

	}
	public void ClickonButton(int MxPos, int MyPos, GameController game){
		Plants plant = new Plants(game);
		for(Menu x : game.menu){
			System.out.println(plant.Type);
			
			if(game.IsIntersect(MxPos,MyPos,x.xPos,x.yPos,x.Width,x.Height)){
				plant= plant.Clone(game.Plants.get(x.plantnum));
				System.out.println(plant.Type);
				game.selectedPlant= plant;
				
			}
			if(plant!=null&&game.Selected.equals("Planter")){
				System.out.println(plant.Type);
//				planter.InsertPlant(MxPos, MyPos, game,plant);
			}
		}

	}
}
