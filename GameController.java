import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.WindowConstants;


public class GameController extends JFrame{
	Land land;
	Spaces space;
	Color Brown = new Color(200,100,0);
	Planter planter = new Planter(this);
	GameController game = this;
	Menu menus = new Menu(game);
	Collector collect = new Collector(this);
	int counter;
	int MouseX;
	int MouseY;
	int SpaceNum;
	int CELL_SIZE = 100;
	int starttime;
	int endtime=100;
	boolean noSeeds=false;
	int start=0;
	int end=100;
	String Dimension = "Farm";
	boolean stopload=false;
	String Selected = "";
	ArrayList<Land> Lands = new ArrayList<>();
	ArrayList<Spaces> Spaces= new ArrayList<>();
	ArrayList<Plants> Plants= new ArrayList<>();
	ArrayList<Menu> menu= new ArrayList<>();
	ArrayList<Plants> PlantedPlants= new ArrayList<>();
	ArrayList<Yields> TotalYield= new ArrayList<>();
	ArrayList<Seeds> seeds= new ArrayList<>();
	Plants selectedPlant;
	BufferedImage FullBLUEBERRY;
	BufferedImage Shop;
	BufferedImage onethirdBLUEBERRY;
	BufferedImage twothirdBLUEBERRY;
	public GameController(){
		initGUI();
	}

	public void initGUI(){
		System.out.println("GUI");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		KeyListener listener = new MyKeyListener();
		addKeyListener(listener);
		MouseMotionListener mouselistener = new MyMouseListener();
		addMouseMotionListener(mouselistener);
		MouseListener mouselistener2 = new MyMouseListener2();
		addMouseListener(mouselistener2);
		MouseWheelListener Wheel = new MouseWheelEventDemo();
		addMouseWheelListener(Wheel);
		setVisible(true);
		setSize(1000,1000);
		initObj();
		try{
			FullBLUEBERRY = ImageIO.read(new File("FullBLUEBERRY.jpg"));
			Shop = ImageIO.read(new File("Shop.jpg"));
			onethirdBLUEBERRY = ImageIO.read(new File("onethirdBLUEBERRY.jpg"));
			twothirdBLUEBERRY = ImageIO.read(new File("twothirdBLUEBERRY.jpg"));
		}catch(IOException e){
		}
	}
	public void initObj(){
		System.out.println("Obj");
		Lands.add(new Land(this,0,0,0,0,9,9));
		if(Lands.get(0).Size==2){
			Lands.get(0).Width=1;
			Lands.get(0).Height=2;
		}else{
			Lands.get(0).Height=(int) Math.sqrt(Lands.get(0).Size);
			Lands.get(0).Width=(int) Math.sqrt(Lands.get(0).Size);
		}
		for(int x =0; x<Lands.get(0).Size;  x++){
			Spaces.add(new Spaces(this,Lands.get(0).xPos+(CELL_SIZE*x),Lands.get(0).yPos+(CELL_SIZE*x),CELL_SIZE-(CELL_SIZE/20),CELL_SIZE-(CELL_SIZE/20),""));
			System.out.println("HI");
		}
		Plants.add(new Plants(this,0,0,0,0, "BLUEBERRY", 0,30,10,false,false));
		menu.add(new Menu(this,100,100,100,20,0,"BLUEBERRY"));
		seeds.add(new Seeds(this,10,"BLUEBERRY"));
	}
	public void step(){
		MouseControl(MouseX, MouseY);
		PlantControl();
		if(Lands.get(0).Size==2){
			Lands.get(0).Width=1;
			Lands.get(0).Height=2;
		}else{
			Lands.get(0).Height=(int) Math.sqrt(Lands.get(0).Size);
			Lands.get(0).Width=(int) Math.sqrt(Lands.get(0).Size);
		}
		Lands.get(0).openSpaces=Lands.get(0).Height*Lands.get(0).Width;
		for(Spaces x : Spaces){
			x.xPos=Lands.get(0).xPos;
			x.yPos=Lands.get(0).yPos;
			x.Height = CELL_SIZE;
			x.Width = CELL_SIZE;
		}
		if(noSeeds==true){
			starttime++;
			if(starttime>=endtime){
				noSeeds=false;
				starttime=0;
			}
		}
		SpacesControl();

	}
	public void delay (int time){
		try{
			Thread.sleep(time);
		}
		catch (Exception e){	
		}
		//makes it appear more smooth
	}
	public void paint(Graphics g){
		g.drawImage(FullBLUEBERRY,10000,10000,50,50, null);
		g.drawImage(onethirdBLUEBERRY,10000,10000,50,50, null);
		g.drawImage(twothirdBLUEBERRY,10000,10000,50,50, null);
		g.drawImage(Shop,10000,10000,50,50, null);
		g.setColor(Color.gray);
		g.fillRect(0, 0, 10000,10000);
		if(Dimension=="Farm"){
		if (Lands.size() > 0) {
			g.setColor(new Color(25,150,50));
			g.fillRect(Lands.get(0).xPos,Lands.get(0).yPos, Lands.get(0).Width*CELL_SIZE, Lands.get(0).Height*CELL_SIZE);
		}
		for(Yields z : TotalYield){
			g.setColor(new Color(250,250,250));
			g.drawString(z.Type + " : " + z.Amount,800, 50);
		}
		for(Seeds c : seeds){
			g.setColor(new Color(250,250,250));
			g.drawString(c.Type + " : " +c.Amount,800, 75);
		}
		for(Spaces x : Spaces){
			if(x.plant==null){
				//brown
				g.setColor(new Color(200,100,0));
				g.fillRect(x.xPos, x.yPos, x.Width, x.Height);
				g.setColor(new Color(150,75,0));
				g.drawRect(x.xPos, x.yPos, x.Width, x.Height);
			}else if(x.plant.Harvestable==true){

				g.drawImage(FullBLUEBERRY,x.xPos,x.yPos,x.Width,x.Height, null);

			}else if(x.plant.CurrentGrowth/x.plant.GrowthTime>=(1.0/3.0)&&x.plant.CurrentGrowth/x.plant.GrowthTime<(2.0/3.0)){

				g.drawImage(onethirdBLUEBERRY,x.xPos,x.yPos,x.Width,x.Height, null);

			}else if(x.plant.CurrentGrowth/x.plant.GrowthTime>=(2.0/3.0)&&x.plant.CurrentGrowth/x.plant.GrowthTime<(1.0)){

				g.drawImage(twothirdBLUEBERRY,x.xPos,x.yPos,x.Width,x.Height, null);

			}
		}
		for(Menu x : menu){
			g.setColor(Color.BLUE);
			g.fillRect(x.xPos, x.yPos, x.Width, x.Height);
		}
		if(noSeeds==true){
			g.setColor(Color.WHITE);
			g.fillRect(475, 475, 120, 40);
			g.setColor(Color.BLACK);
			g.drawRect(475, 475, 120, 40);
			g.setColor(Color.red);
			g.drawString("NO SEEDS",500,500);

		}
		if(Selected.equals("Planter")){
			g.setColor(Color.WHITE);
			g.fillRect(375, 75, 120, 40);
			g.setColor(Color.BLACK);
			g.drawRect(375, 75, 120, 40);
			g.setColor(Color.BLACK);
			g.drawString("Planter", 400, 100);
		}
		if(Selected.equals("Collector")){
			g.setColor(Color.WHITE);
			g.fillRect(375, 75, 120, 40);
			g.setColor(Color.BLACK);
			g.drawRect(375, 75, 120, 40);
			g.setColor(Color.BLACK);
			g.drawString("Collector", 400, 100);
		}
		g.setColor(Color.ORANGE);
		g.fillRect(0,20,60,60);
		g.drawImage(Shop, 5, 25, 50, 50, null);
		}
		if(start>=end){
			stopload=true;
		}
		if(!stopload){
			start++;
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 10000, 10000);
			g.setColor(Color.white);
			g.drawString("LOADING", 500, 500);
		}
	
	delay(25);
	step();
	repaint();
}
public void PlantControl(){
	for(Spaces x : Spaces){
		if(x.plant!=null&&x.plant.Harvestable==false){
			x.plant.CurrentGrowth+=.1;
			if(x.plant.CurrentGrowth>=x.plant.GrowthTime){
				x.plant.Harvestable=true;
				System.out.print("Harvestable : " + x.plant.Harvestable);
			}
		}
	}
}
public void SpacesControl(){
	Land l = Lands.get(0);
	int num =0;
	int numofdown=0;
	for(Spaces x : Spaces){
		if(num<l.Width){
			x.xPos=l.xPos+num*CELL_SIZE;
			x.yPos=l.yPos+numofdown*CELL_SIZE;
		}else{
			numofdown++;
			num=0;
			x.xPos=l.xPos+num*CELL_SIZE;
			x.yPos=l.yPos+numofdown*CELL_SIZE;
		}
		num++;
	}


}

public void MouseControl(int xPos,int yPos){
	if(yPos-60<=0){
		Lands.get(0).yPos+=2;
	}
	if(xPos-60<=0){
		Lands.get(0).xPos+=2;
	}
	if(yPos+100>=800){
		Lands.get(0).yPos-=2;
	}
	if(xPos+60>=1000){
		Lands.get(0).xPos-=2;
	}
}
class MyMouseListener implements MouseMotionListener{
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		MouseX=e.getX();
		MouseY = e.getY();
	}
}
class MyKeyListener implements KeyListener {

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		switch (e.getKeyCode()){
		case KeyEvent.VK_P:Selected="Planter";
		System.out.print("P");
		break;
		case KeyEvent.VK_C:Selected="Collector";	
		System.out.print("C");
		break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
public class MouseWheelEventDemo implements MouseWheelListener{

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub
		System.out.println(Lands.get(0).Height);
		CELL_SIZE-=e.getWheelRotation();
		System.out.println(Lands.get(0).Height);

	}

}
class MyMouseListener2 implements MouseListener{
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		menus.ClickonButton(e.getX(),e.getY(), game);
		if(Selected == "Collector"){
			collect.CollectPlant(e.getX(), e.getY(), game);
		}
		if(Selected == "Planter"){
			planter.InsertPlant(e.getX(), e.getY(), game, selectedPlant);

		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}



}
}
