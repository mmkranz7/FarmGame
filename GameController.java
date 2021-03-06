import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
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
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.WindowConstants;


public class GameController extends JFrame{
	Land land;
	Spaces space;
	DragBox dBox;
	BufferStrategy bs;
	Color Brown = new Color(200,100,0);
	Planter planter = new Planter(this);
	GameController game = this;
	Menu menus = new Menu(game);
	BufferedImage FullBLUEBERRY;
	BufferedImage FullSTRAWBERRY;
	BufferedImage Shop;
	BufferedImage BuySeeds;
	BufferedImage StrawBerry;
	BufferedImage Planter_cursor;
	BufferedImage Collector_cursor;
	BufferedImage Farm;
	BufferedImage SellThings;
	BufferedImage MoreLand;
	BufferedImage onethirdBLUEBERRY;
	BufferedImage twothirdBLUEBERRY;
	BufferedImage onethirdSTRAWBERRY;
	BufferedImage twothirdSTRAWBERRY;
	BufferedImage SeedDrop;
	BufferedImage BlueBerry;
	Button but = new Button(game);
	Collector collect = new Collector(this);
	int counter;
	int MouseX;
	int MouseY;
	boolean complete=false;
	int TotalMoney = 0;
	int SpaceNum;
	int CELL_SIZE = 100;
	int starttime;
	int endtime=100;
	boolean noSeeds=false;
	boolean nothingToCollect=false;
	boolean noSeedSelected=false;
	int start=0;
	int end=100;
	private Image bufferImage;
	private Graphics bufferGraphics;
	private int bufferWidth;
	private int bufferHeight;
	String Dimension = "Farm";
	boolean stopload=false;
	String Selected = "";
	ArrayList<Land> Lands = new ArrayList<>();
	ArrayList<Spaces> Spaces= new ArrayList<>();
	ArrayList<Plants> Plants= new ArrayList<>();
	ArrayList<Menu> menu= new ArrayList<>();
	ArrayList<Button> buttons= new ArrayList<>();
	ArrayList<Plants> PlantedPlants= new ArrayList<>();
	ArrayList<Yields> TotalYield= new ArrayList<>();
	ArrayList<Seeds> seeds= new ArrayList<>();
	ArrayList<DragBox> DragBoxes= new ArrayList<>();
	Plants selectedPlant;
	BufferedImage selectedImage;
	Button DimButtonsToFarm = new Button(game,5, 25, 50, 50,Color.orange, "ToFarm","DimTele","Shop",0){
		public BufferedImage getImage(){
			return game.Farm;
		}
		@Override
		public boolean ClickButton(Button x){
			if(x.Type.equals("DimTele")){
				System.out.println("AFTER CHECKING IF INSIDE");
				if(x.Name.equals("ToFarm")&&game.Dimension=="Shop"){
					game.Dimension="Farm";
					return true;
				}
			}
			return false;
		}
	};
	Button BuySeed = new Button(game,1000, 25, 50, 50,Color.orange, "SeedBuy","Purchase","Shop",0){
		public BufferedImage getImage(){
			return game.BuySeeds;
		}
		@Override
		public boolean ClickButton(Button x){
			if(x.Type.equals("Purchase")){
				System.out.println("AFTER CHECKING IF INSIDE");
				if(x.Name.equals("SeedBuy")&&game.Dimension=="Shop"){
					for(Menu z : menu){
						if(z.Visible==true){
							z.Visible=false;
						}else{
							z.Visible=true;
						}
					}
					return true;
				}
			}
			return false;
		}
	};

	Button SeedDropdown = new Button(game,1000, 25, 50, 50,Color.orange, "SeedDropdown","Dropdown","Farm",0){
		public BufferedImage getImage(){
			return game.SeedDrop;
		}
		@Override
		public boolean ClickButton(Button x){
			if(x.Type.equals("Dropdown")){
				System.out.println("AFTER CHECKING IF INSIDE");
				if(x.Name.equals("SeedDropdown")&&game.Dimension=="Farm"){
					for(Menu z : menu){
						if(z.Visible==true){
							z.Visible=false;
						}else{
							z.Visible=true;
						}
					}
					return true;
				}
			}
			return false;
		}
	};


	Button DimButtonToShop = new Button(game,5, 25, 50, 50,Color.orange, "ToShop","DimTele","Farm",0){
		public BufferedImage getImage(){
			return game.Shop;
		}
		@Override
		public boolean ClickButton(Button x){
			if(x.Type.equals("DimTele")){
				System.out.println("AFTER CHECKING IF INSIDE");
				if(x.Name.equals("ToShop")&&game.Dimension=="Farm"){
					System.out.print("SUCESS");
					game.Dimension="Shop";
					return true;
				}
			}
			return false;
		}
	};
	Button SellButtons = new Button(game,275, 400, 50, 50,Color.orange, "SellStuff","Purchase","Shop",0){
		public BufferedImage getImage(){
			return game.SellThings;
		}
		@Override
		public boolean ClickButton(Button x){
			if(x.Name.equals("SellStuff")){
				for(Yields z : game.TotalYield){
					if(z.Type=="BLUEBERRY"){
						for(int i=0; i<z.Amount;i++){
							game.TotalMoney+=5;
						}
						z.Amount=0;
					}
					if(z.Type=="STRAWBERRY"){
						for(int i=0; i<z.Amount;i++){
							game.TotalMoney+=40;
						}
						z.Amount=0;
					}
				}
			}
			return false;
		}
	};
	Button PurchaseButtons = new Button(game,200, 400, 50, 50,Color.orange, "BuyLand","Purchase","Shop",10){
		public BufferedImage getImage(){
			return game.MoreLand;
		}
		@Override
		public boolean ClickButton(Button x){
			if(x.Type.equals("Purchase")&&game.Dimension.equals("Shop")){
				if(x.Name.equals("BuyLand")){
					BuyLand(x);
				}
			}
			return false;
		}
		public void BuyLand(Button x){
			int r=game.Lands.get(0).Size;
			x.price=(int) (40*Math.pow(Math.sqrt(r),2));
			if(game.TotalMoney>=x.price){
				game.TotalMoney-=x.price;
				r+= Math.sqrt(r)*2+1;
				game.Lands.get(0).Size=r;
				game.Lands.get(0).openSpaces=r;
				for(int z =0; z<Math.sqrt(r)*2-1;  z++){
					game.Spaces.add(new Spaces(game,game.Lands.get(0).xPos+(game.CELL_SIZE*z),game.Lands.get(0).yPos+(game.CELL_SIZE*z),game.CELL_SIZE-(game.CELL_SIZE/20),game.CELL_SIZE-(game.CELL_SIZE/20),""));
				}
			}
		}
	};

	public GameController(){
		super("FrameDemo");
		//	        addWindowListener(this);	
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setUndecorated(true);
		setSize(1000, 1000);
		setResizable(true);
		setVisible(true);

		//	        createBufferStrategy(2);
		initGUI();
		//	       
	}

	public void initGUI(){
		System.out.println("GUI");
		KeyListener listener = new MyKeyListener();
		addKeyListener(listener);
		MouseMotionListener mouselistener = new MyMouseListener();
		addMouseMotionListener(mouselistener);
		MouseListener mouselistener2 = new MyMouseListener2();
		addMouseListener(mouselistener2);
		MouseWheelListener Wheel = new MouseWheelEventDemo();
		addMouseWheelListener(Wheel);
		//		 BufferStrategy bs = this.getBufferStrategy(); 
		//	        if (bs == null) {
		//	               this.createBufferStrategy(3); 
		//	               bs = this.getBufferStrategy(); 
		//	        }
		//		
		try{
			FullBLUEBERRY = ImageIO.read(new File("FullBLUEBERRY.jpg"));
			FullSTRAWBERRY = ImageIO.read(new File("FullSTRAWBERRY.jpg"));
			Shop = ImageIO.read(new File("Shop.jpg"));
			Collector_cursor = ImageIO.read(new File("Collector.png"));
			Planter_cursor = ImageIO.read(new File("Planter.png"));
			BuySeeds = ImageIO.read(new File("BuySeeds.png"));
			Farm = ImageIO.read(new File("Farm.jpg"));
			MoreLand = ImageIO.read(new File("MoreLand.jpg"));
			SellThings = ImageIO.read(new File("SellThings.jpg"));
			SeedDrop = ImageIO.read(new File("SeedDrop.jpg"));
			BlueBerry = ImageIO.read(new File("BlueBerry.jpg"));
			StrawBerry = ImageIO.read(new File("StrawBerry.jpg"));
			onethirdBLUEBERRY = ImageIO.read(new File("onethirdBLUEBERRY.jpg"));
			twothirdBLUEBERRY = ImageIO.read(new File("twothirdBLUEBERRY.jpg"));
			onethirdSTRAWBERRY = ImageIO.read(new File("onethirdSTRAWBERRY.jpg"));
			twothirdSTRAWBERRY = ImageIO.read(new File("twothirdSTRAWBERRY.jpg"));
		}catch(IOException e){
		}
		initObj();
	}
	public boolean IsIntersect(int MxPos, int MyPos,int xPos, int yPos, int Width, int Height){
		if((MxPos>=xPos&&MxPos<=xPos+Width)&&(MyPos>=yPos&&MyPos<=yPos+Height)){
			return true;
		}
		return false;

	}
	public void InitSpaces(){
		for(int x =0; x<Lands.get(0).Size;  x++){
			Spaces.add(new Spaces(this,Lands.get(0).xPos+(CELL_SIZE*x),Lands.get(0).yPos+(CELL_SIZE*x),CELL_SIZE-(CELL_SIZE/20),CELL_SIZE-(CELL_SIZE/20),""));
			System.out.println("HI");
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
		InitSpaces();
		Plants.add(new Plants(this,0,0,0,0, "BLUEBERRY", 0,30,10,false,false));
		Plants.add(new Plants(this,0,0,0,0, "STRAWBERRY", 0,100,40,false,false));
		menu.add(new Menu(this,1000,100,75,75,0,"BLUEBERRY",BlueBerry,false));
		menu.add(new Menu(this,1000,200,75,75,1,"STRAWBERRY",StrawBerry,false));
		seeds.add(new Seeds(this,10,20,"BLUEBERRY"));
		seeds.add(new Seeds(this,10,40,"STRAWBERRY"));
		buttons.add(DimButtonToShop);
		buttons.add(DimButtonsToFarm);
		buttons.add(PurchaseButtons);
		buttons.add(SellButtons);
		buttons.add(SeedDropdown);
		buttons.add(BuySeed);
	}
	public void step(){
		MouseControl(MouseX, MouseY);
		PlantControl();
		if(DragBoxes.size()==1){
			System.out.println("RAN DRAG");
			if(DragBoxes.size()==1){
				DragBoxes.get(0).Drag(MouseX,MouseY,game);
			}
		}
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
		if(nothingToCollect==true){
			starttime++;
			if(starttime>=endtime){
				nothingToCollect=false;
				starttime=0;
			}
		}
		if(noSeedSelected==true){
			starttime++;
			if(starttime>=endtime){
				noSeedSelected=false;
				starttime=0;
			}
		}
		SpacesControl();

	}
	public void ButtonDraw(Graphics g, Button button){
		g.setColor(button.boxColor);
		if(button.price!=0){
			g.drawString(((Integer)button.price).toString() , button.xPos, button.yPos+button.Height+40);
		}
		g.fillRect(button.xPos-5,button.yPos-5,button.Width+10,button.Height+10);
		g.drawImage(button.getImage(), button.xPos, button.yPos, button.Width, button.Height, null);
	}
	public String NumManage(Integer Number){
		String FinalString=Number.toString();
		if(Number>=1000){
			if(Number>=1000000000){
				System.out.print(Number);
				Number=Number/1000000000;
				System.out.print(Number);
				FinalString=(Number.toString() + 'B');
			}else if(Number>=1000000){
				System.out.print(Number);
				Number=Number/1000000;
				System.out.print(Number);
				FinalString=(Number.toString() + 'm');
			}else{
				System.out.print(Number);
				Number=Number/1000;
				System.out.print(Number);
				FinalString=(Number.toString() + 'K');
			}
		}
		return FinalString;

	}
	public String Capitalize(String string){
		String string1 = string.substring(0, 1);
		string1.toUpperCase();

		String string2 = string.substring(1).toLowerCase();
		return string1+string2;
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
		if(bufferWidth!=getSize().width ||
				bufferHeight!=getSize().height ||
				bufferImage==null || bufferGraphics==null){
			resetBuffer();
		}
		update(g);
		step();
		repaint();
	}
	public void update(Graphics g){
		resetBuffer();

		if(bufferGraphics!=null){
			//this clears the offscreen image, not the onscreen one
			bufferGraphics.clearRect(0,0,bufferWidth,bufferHeight);

			//calls the paintbuffer method with 
			//the offscreen graphics as a param
			paintBuffer(bufferGraphics);

			//we finaly paint the offscreen image onto the onscreen image
			g.drawImage(bufferImage,0,0,this);
		}
	}

	private void resetBuffer(){
		// always keep track of the image size
		bufferWidth=getSize().width;
		bufferHeight=getSize().height;

		//    clean up the previous image
		if(bufferGraphics!=null){
			bufferGraphics.dispose();
			bufferGraphics=null;
		}
		if(bufferImage!=null){
			bufferImage.flush();
			bufferImage=null;
		}
		System.gc();

		//    create the new image with the size of the panel
		bufferImage=createImage(bufferWidth,bufferHeight);
		bufferGraphics=bufferImage.getGraphics();
	}
	private void paintBuffer(Graphics g){
		g.drawImage(FullBLUEBERRY,10000,10000,50,50, null);
		g.drawImage(onethirdBLUEBERRY,10000,10000,50,50, null);
		g.drawImage(twothirdBLUEBERRY,10000,10000,50,50, null);
		g.drawImage(Shop,10000,10000,50,50, null);
		g.drawImage(Farm,10000,10000,50,50, null);
		g.setColor(Color.gray);
		g.fillRect(0, 0, 10000,10000);
		if(Dimension=="Farm"){
			if (Lands.size() > 0) {
				g.setColor(new Color(25,150,50));
				g.fillRect(Lands.get(0).xPos,Lands.get(0).yPos, Lands.get(0).Width*CELL_SIZE, Lands.get(0).Height*CELL_SIZE);
			}
			for(Spaces x : Spaces){
				if(x.plant==null){
					//brown
					g.setColor(new Color(200,100,0));
					g.fillRect(x.xPos, x.yPos, x.Width, x.Height);
					g.setColor(new Color(150,75,0));
					g.drawRect(x.xPos, x.yPos, x.Width, x.Height);
				}else if(x.plant.Harvestable==true){
					if(x.plant.Type.equals("BLUEBERRY")){
						g.drawImage(FullBLUEBERRY,x.xPos,x.yPos,x.Width,x.Height, null);
					}
					if(x.plant.Type.equals("STRAWBERRY")){
						g.drawImage(FullSTRAWBERRY,x.xPos,x.yPos,x.Width,x.Height, null);
					}

				}else if(x.plant.CurrentGrowth/x.plant.GrowthTime>=(1.0/3.0)&&x.plant.CurrentGrowth/x.plant.GrowthTime<(2.0/3.0)){
					if(x.plant.Type.equals("BLUEBERRY")){
						g.drawImage(onethirdBLUEBERRY,x.xPos,x.yPos,x.Width,x.Height, null);
					}
					if(x.plant.Type.equals("STRAWBERRY")){
						g.drawImage(onethirdSTRAWBERRY,x.xPos,x.yPos,x.Width,x.Height, null);
					}
				}else if(x.plant.CurrentGrowth/x.plant.GrowthTime>=(2.0/3.0)&&x.plant.CurrentGrowth/x.plant.GrowthTime<(1.0)){
					if(x.plant.Type.equals("BLUEBERRY")){
						g.drawImage(twothirdBLUEBERRY,x.xPos,x.yPos,x.Width,x.Height, null);
					}
					if(x.plant.Type.equals("STRAWBERRY")){
						g.drawImage(twothirdSTRAWBERRY,x.xPos,x.yPos,x.Width,x.Height, null);
					}
				}
			}
			for(DragBox x : DragBoxes){
				System.out.println("PAINTED");
				g.setColor(new Color(0,191,255,25));
				g.fillRect(x.MinX, x.MinY, x.Width, x.Height);
				g.setColor(new Color(0,191,255));
				g.drawRect(x.MinX, x.MinY, x.Width, x.Height);
			}
			if(noSeeds==true){
				g.setColor(Color.WHITE);
				g.fillRect(475, 475, 120, 40);
				g.setColor(Color.BLACK);
				g.drawRect(475, 475, 120, 40);
				g.setColor(Color.red);
				g.drawString("NO SEEDS",500,500);

			}
			if(nothingToCollect==true){
				g.setColor(Color.WHITE);
				g.fillRect(475, 475, 200, 40);
				g.setColor(Color.BLACK);
				g.drawRect(475, 475, 200, 40);
				g.setColor(Color.red);
				g.drawString("NOTHING TO COLLECT",500,500);
			}
			if(noSeedSelected==true){
				g.setColor(Color.WHITE);
				g.fillRect(475, 475, 180, 40);
				g.setColor(Color.BLACK);
				g.drawRect(475, 475, 180, 40);
				g.setColor(Color.red);
				g.drawString("NO SEED SELECTED",500,500);
			}
			if(Selected.equals("Planter")){
				g.setColor(Color.WHITE);
				g.fillRect(375, 75, 120, 40);
				g.setColor(Color.BLACK);
				g.drawRect(375, 75, 120, 40);
				g.setColor(Color.BLACK);
				g.drawString("Planter", 400, 100);
				g.drawImage(selectedImage,450,80,25,25,null);
			}
			if(Selected.equals("Collector")){
				g.setColor(Color.WHITE);
				g.fillRect(375, 75, 120, 40);
				g.setColor(Color.BLACK);
				g.drawRect(375, 75, 120, 40);
				g.setColor(Color.BLACK);
				g.drawString("Collector", 400, 100);
			}
			for(Button x : buttons){
				if(x.Dim.equals("Farm")){
					ButtonDraw(g,x);
				}
			}
		}
		if(Dimension=="Shop"){ 
			g.setColor(Color.gray);
			g.fillRect(0, 0, 10000, 100000);
			for(Button x : buttons){
				if(x.Dim.equals("Shop")){
					ButtonDraw(g,x);
				}
			}

		}
		for(Menu x : menu){
			if(x.Visible==true){
				g.setColor(Color.BLUE);
				g.fillRect(x.xPos, x.yPos, x.Width, x.Height);
				g.drawImage(x.Image, x.xPos, x.yPos, x.Width, x.Height,null);
			}
		}
		if(Selected.equals("Planter")){
			setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
					new ImageIcon("Planter_cursor").getImage(),
					new Point(0,0),"custom cursor"));
			g.drawImage(Planter_cursor, MouseX, MouseY, 25, 25, null);
		}
		if(Selected.equals("Collector")){
			setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
					new ImageIcon("Collector_cursor").getImage(),
					new Point(0,0),"custom cursor"));
			g.drawImage(Collector_cursor, MouseX, MouseY, 25, 25, null);
		}
		for(int z= 0; z<TotalYield.size(); z++){
			g.setColor(new Color(250,250,250));
			g.drawString(Capitalize(TotalYield.get(z).Type) + "s : " + NumManage(TotalYield.get(z).Amount),850, 50+(z*20));
		}
		for(int c =0; c<seeds.size(); c++){
			g.setColor(new Color(250,250,250));
			g.drawString(Capitalize(seeds.get(c).Type) + " seeds : " +NumManage(seeds.get(c).Amount),700, 50+ (c*20));
		}
		g.drawString("Money :"+ NumManage(TotalMoney),600,50);
		//			if(start>=end){
		//				stopload=true;
		//			}
		//			if(!stopload){
		//				start++;
		//				g.setColor(Color.BLACK);
		//				g.fillRect(0, 0, 10000, 10000);
		//				g.setColor(Color.white);
		//				g.drawString("LOADING", 500, 500);
		//			}
		//	        		bs.getDrawGraphics();
		//			        complete=true;
		//			    } finally{
		//				    if(complete) {
		//				           g.dispose();
		//				           complete=false;
		//				    }
		//			    }
		//			    bs.show();
		//			} while (bs.contentsLost());
		//			update(g);
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
		if(Dimension=="Farm"){
			if(yPos-30<=0){
				Lands.get(0).yPos+=2;
			}
			if(xPos-30<=0){
				Lands.get(0).xPos+=2;
			}
			if(yPos+100>=800){
				Lands.get(0).yPos-=2;
			}
			if(xPos+60>=1300){
				Lands.get(0).xPos-=2;
			}
		}
	}
	class MyMouseListener implements MouseMotionListener{
		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			MouseX=e.getX();
			MouseY = e.getY();
		}
		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			System.out.println("PRINTED");
			System.out.println(DragBoxes.size());
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
				if(CELL_SIZE<=0){
					CELL_SIZE=1;
				}
			System.out.println(Lands.get(0).Height);

		}

	}
	class MyMouseListener2 implements MouseListener{
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			menus.ClickonButton(e.getX(),e.getY(), game);
			but.ClickedOn(e.getX(), e.getY(), game);
			if(Selected == "Collector"){
				collect.CollectPlant(e.getX(), e.getY(), game);
			}
			if(Selected == "Planter"){
				planter.InsertPlant(e.getX(), e.getY(), game, selectedPlant);
			}
		}
		@Override
		public void mousePressed(MouseEvent e) {
			System.out.println("ADDED");
			System.out.println(DragBoxes.size());
			System.out.println(DragBoxes.size());
			DragBoxes.add(new DragBox(game,e.getX(),e.getY(),e.getX(),e.getY(),0,0,0,0,true,true));
			// TODO Auto-generated method stub

		}
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			DragBoxes.get(0).CheckCont(game);
			DragBoxes.get(0).Dragging=false;
			if(DragBoxes.size()>=1){
				System.out.println("REMOVED");
				DragBoxes.get(0).Clicked=false;
				DragBoxes.clear();
			}
		}

		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}



	}
}
