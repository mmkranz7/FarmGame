
public class Menu {
	GameController game;
	int xPos;
	int yPos;
	int Width;
	int Height;
	String text;
	Planter planter;

	public Menu(GameController game){
		this(game,0,0,0,0,"");
	}
	public Menu(GameController game,
			int xPos,
			int yPos,
			int Width,
			int Height,
			String Type){
		this.xPos=xPos;
		this.yPos=yPos;
		this.Width=Width;
		this.Height=Height;
		this.text=text;

	}
	public void ClickonButton(int MxPos, int MyPos){
		for(Menu x : game.menu){
		if((MxPos>=x.xPos&&MxPos<=x.xPos+game.CELL_SIZE)&&(MyPos>=x.yPos&&MyPos<=x.yPos+x.Height)){
			planter.plant=game.Plants.get(0);
			}
		}
			
		}
	}
