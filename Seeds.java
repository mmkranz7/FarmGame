import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;


public class Seeds {
	GameController game;
	int Amount;
	int Price;
	String Type;
	public Seeds(GameController game){
		this(game,0,0,"");
	}
	public Seeds(GameController game,
			int Amount,
			int Price,
			String Type){
		this.Amount=Amount;
		this.Type=Type;
		this.Price=Price;
		
	}
}
