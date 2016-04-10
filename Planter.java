public class Planter {
	Spaces space;
	GameController game;
	int xPos;
	int yPos;
	Plants plant;

	public Planter(GameController game){
		this(game,0,0,null);
	}
	public Planter(GameController game,
			int xPos,
			int yPos,
			Plants plant){
		this.xPos=xPos;
		this.yPos=yPos;
		this.plant = plant;

	}
	public void InsertPlant(int MxPos, int MyPos){
		for(Spaces x : game.Spaces){
		if((MxPos>=x.xPos&&MxPos<=x.xPos+game.CELL_SIZE)&&(MyPos>=x.yPos&&MyPos<=x.yPos+x.Height)){
			if(plant!=null){
				plant.planted=true;
				x.plant=plant;
			}
		}
			
		}
	}
}