
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
		if((MxPos>=x.xPos&&MxPos<=x.xPos+x.Width)&&(MyPos>=x.yPos&&MyPos<=x.yPos+x.Height)){
			System.out.println("MENUSA");
			plant=game.Plants.get(x.plantnum);
			}
		if(plant!=null){
			planter.InsertPlant(MxPos, MyPos, game,plant);
		}
		}
			
		}
	}
