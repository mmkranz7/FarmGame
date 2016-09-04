
public class DragBox {
	GameController game;
	int OriX;
	int OriY;
	int xPos;
	int yPos;
	int Width;
	int Height;
	boolean Clicked;

	public DragBox(GameController game){
		this(game,0,0,0,0,0,0,false);
	}
	public DragBox(GameController game,
			int xPos,
			int yPos,
			int OriX,
			int OriY,
			int Width,
			int Height,
			boolean Clicked){
		this.xPos=xPos;
		this.yPos=yPos;
		this.OriX=OriX;
		this.OriY=OriY;
		this.Width=Width;
		this.Height=Height;
		this.Clicked=Clicked;
	}
	public void Drag(int MxPos, int MyPos, GameController game){
		if(OriX<=MxPos&&OriY<=MyPos){
			game.DragBoxes.get(0).xPos=MxPos;
			game.DragBoxes.get(0).yPos=MyPos;
			game.DragBoxes.get(0).Width=Math.abs(game.DragBoxes.get(0).OriX-game.DragBoxes.get(0).xPos);
			game.DragBoxes.get(0).Height=Math.abs(game.DragBoxes.get(0).OriY-game.DragBoxes.get(0).yPos);
		}else if(OriX>=MxPos&&OriY<=MyPos){
			game.DragBoxes.get(0).OriX=MxPos;
			game.DragBoxes.get(0).yPos=MyPos;
			game.DragBoxes.get(0).Width=Math.abs(game.DragBoxes.get(0).OriX-game.DragBoxes.get(0).xPos);
			game.DragBoxes.get(0).Height=Math.abs(game.DragBoxes.get(0).OriY-game.DragBoxes.get(0).yPos);
		}
		
	}
}
