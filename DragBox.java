
public class DragBox {
	GameController game;
	int OriX;
	int OriY;
	int MinX;
	int MinY;
	int xPos;
	int yPos;
	int Width;
	int Height;
	boolean Clicked;
	boolean Dragging;

	public DragBox(GameController game){
		this(game,0,0,0,0,0,0,0,0,false,false);
	}
	public DragBox(GameController game,
			int xPos,
			int yPos,
			int OriX,
			int OriY,
			int MinX,
			int MinY,
			int Width,
			int Height,
			boolean Clicked,
			boolean Dragging){
		this.xPos=xPos;
		this.yPos=yPos;
		this.OriX=OriX;
		this.OriY=OriY;
		this.MinX=MinX;
		this.MinY=MinY;
		this.Width=Width;
		this.Height=Height;
		this.Clicked=Clicked;
		this.Dragging=Dragging;
		
	}
	public void CheckCont(GameController game){
		for(Spaces z : game.Spaces){
			if(
					((z.xPos>=MinX&&z.xPos<=MinX+Width)&&(z.yPos>=MinY&&z.yPos<=MinY+Height))||
					((z.xPos+z.Width>=MinX&&z.xPos+z.Width<=MinX+Width)&&(z.yPos+z.Height>=MinY&&z.yPos+z.Height<=MinY+Height))
					){
				System.out.print("HI I collided");
				if(game.Selected == "Planter"){
					if(game.selectedPlant!=null){
						game.planter.InsertPlant_bulk(game ,game.selectedPlant,z);
					}else{
						game.noSeedSelected=true;
					}
				}
				if(game.Selected == "Collector"){
					game.collect.CollectPlant_bulk( game,z);
				}
			}
		}
	}
	public void Drag(int MxPos, int MyPos, GameController game){
		xPos=MxPos;
		yPos=MyPos;
		MinX=Math.min(xPos, OriX);
		MinY=Math.min(yPos, OriY);
			Width=Math.max(xPos, OriX)-MinX;
			Height=Math.max(yPos, OriY)-MinY;
		
		
	}
}
