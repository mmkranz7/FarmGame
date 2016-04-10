public class Plants {
	GameController game;
	int xPos;
	int yPos;
	int Width;
	int Height;
	String Type;
	float CurrentGrowth;
	float GrowthTime;
	int Yield;
	boolean Harvestable;
	boolean planted;

	public Plants(GameController game){
		this(game,0,0,0,0,"",0,0,0,false,false);
	}
	public Plants(GameController game,
			int xPos,
			int yPos,
			int Width,
			int Height,
			String Type,
			float CurrentGrowth,
			float GrowthTime,
			int Yield,
			boolean Harvestable,
			boolean planted){
		this.xPos=xPos;
		this.yPos=yPos;
		this.Width=Width;
		this.Height=Height;
		this.Type=Type;
		this.CurrentGrowth=CurrentGrowth;
		this.GrowthTime=GrowthTime;
		this.Yield=Yield;
		this.Harvestable=Harvestable;
		this.planted=planted;

	}
}