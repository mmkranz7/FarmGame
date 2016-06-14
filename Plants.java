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
		this(game,0,0,0,0,"",0,30,0,false,false);
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
	public Plants Clone(Plants plantold){
		Plants plantnew;
		int xPos=plantold.xPos;
		int yPos=plantold.yPos;
		int Width= plantold.Width;
		int Height=plantold.Height;
		String Type=plantold.Type;
		float CurrentGrowth=plantold.CurrentGrowth;
		float GrowthTime=plantold.GrowthTime;
		int Yield=plantold.Yield;
		boolean Harvestable=plantold.Harvestable;
		boolean Planted=plantold.planted;
		plantnew = new Plants(game,xPos,yPos,Width,Height,Type,CurrentGrowth,GrowthTime,Yield,Harvestable,planted);
		return plantnew;
	}
}