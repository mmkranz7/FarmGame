public class Planter {
	Spaces space;
	int xPos;
	int yPos;
	GameController game;
	public Planter(GameController game){
		this(game,0,0);
	}
	public Planter(GameController game,
			int xPos,
			int yPos){
		this.xPos=xPos;
		this.yPos=yPos;
	}
	public void InsertPlant(int MxPos, int MyPos, GameController game, Plants plant){
		System.out.println(plant.Type);
		boolean seedallow=true;
		for(Spaces x : game.Spaces){
			if(game.IsIntersect(MxPos,MyPos,x.xPos,x.yPos,x.Width,x.Height)){
				if(plant!=null){
					for(Seeds t : game.seeds){
						if(x.plant==null){
							if(t.Type==plant.Type&&t.Amount>0){
								t.Amount--;
							}else{
								seedallow=false;
								game.noSeeds=true;

							}
						}
					}
					if(seedallow&&x.plant==null){
						x.plant=plant.Clone(plant);
						System.out.println("YES PLANT");
						System.out.println(plant.Type);
						x.plant.planted=true;
						game.PlantedPlants.add(x.plant);
					}
				}

			}
		}

	}
}
