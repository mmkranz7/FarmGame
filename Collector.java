public class Collector {
	Spaces space;
	int xPos;
	int yPos;
	GameController game;
	public Collector(GameController game){
		this(game,0,0);
	}
	public Collector(GameController game,
			int xPos,
			int yPos){
		this.xPos=xPos;
		this.yPos=yPos;
	}
	public void CollectPlant(int MxPos, int MyPos, GameController game){
		for(Spaces x : game.Spaces){
			if((MxPos>=x.xPos&&MxPos<=x.xPos+x.Width)
					&&(MyPos>=x.yPos&&MyPos<=x.yPos+x.Height)){
				if(x.plant.Harvestable==true){
					System.out.println("Harvested Plant");
					int Plantnum = 0;
					System.out.println("");
					System.out.println("Plant type  " + x.plant.Type);
					System.out.println("");
					for(Yields z : game.TotalYield){
						System.out.println("");
						System.out.println("Yield type  " + z.Type);
						System.out.println("");
						if(z.Type==x.plant.Type){
							z.Amount+=x.plant.Yield;
							Plantnum++;
							System.out.println("Added1Yield");
						}
					}
					if(Plantnum==0){
						game.TotalYield.add(new Yields(game,x.plant.Yield,x.plant.Type));
						System.out.println("CreatedNew");
					}
					game.PlantedPlants.remove(x.plant);
					x.plant=null;
				}
			}
		}

	}
}
