import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
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
	DragBox dBox;
	BufferStrategy bs;
	Color Brown = new Color(200,100,0);
	Planter planter = new Planter(this);
	GameController game = this;
	Menu menus = new Menu(game);
	BufferedImage FullBLUEBERRY;
	BufferedImage Shop;
	BufferedImage Farm;
	BufferedImage SellThings;
	BufferedImage MoreLand;
	BufferedImage onethirdBLUEBERRY;
	BufferedImage twothirdBLUEBERRY;
	Button but = new Button(game);
	Collector collect = new Collector(this);
	int counter;
	int MouseX;
	int MouseY;
	boolean complete=false;
	int TotalMoney = 10000;
	int SpaceNum;
	int CELL_SIZE = 100;
	int starttime;
	int endtime=100;
	boolean noSeeds=false;
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
			Shop = ImageIO.read(new File("Shop.jpg"));
			Farm = ImageIO.read(new File("Farm.jpg"));
			MoreLand = ImageIO.read(new File("MoreLand.jpg"));
			SellThings = ImageIO.read(new File("SellThings.jpg"));
			onethirdBLUEBERRY = ImageIO.read(new File("onethirdBLUEBERRY.jpg"));
			twothirdBLUEBERRY = ImageIO.read(new File("twothirdBLUEBERRY.jpg"));
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
		menu.add(new Menu(this,100,100,100,20,0,"BLUEBERRY"));
		seeds.add(new Seeds(this,10,"BLUEBERRY"));
		buttons.add(but.DimButtonToShop);
		buttons.add(but.DimButtonsToFarm);
		buttons.add(but.PurchaseButtons);
		buttons.add(but.SellButtons);
	}
	public void step(){
		MouseControl(MouseX, MouseY);
		PlantControl();
		if(DragBoxes.size()==1){
			System.out.println("RAN DRAG");
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
		SpacesControl();

	}
	public void ButtonDraw(Graphics g, Button button){
		g.setColor(button.boxColor);
		if(button.price!=0){
			g.drawString(((Integer)button.price).toString() , button.xPos, button.yPos+button.Height/2);
		}
		g.fillRect(button.xPos-5,button.yPos-5,button.Width+10,button.Height+10);
		g.drawImage(button.getImage(), button.xPos, button.yPos, button.Width, button.Height, null);
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
//			}
//		 public void update(Graphics g) {
//			    Graphics offgc;
//			    Image offscreen = null;
//			    Dimension d = size();
//
//			    // create the offscreen buffer and associated Graphics
//			    offscreen = createImage(d.width, d.height);
//			    offgc = offscreen.getGraphics();
//			    // clear the exposed area
//			    offgc.setColor(getBackground());
//			    offgc.fillRect(0, 0, d.width, d.height);
//			    offgc.setColor(getForeground());
//			    // do normal redraw
//			    drawWhatEver(offgc);
//			    // transfer offscreen to window
//			    g.drawImage(offscreen, 0, 0, this);
//			    }
////		BufferStrategy bs = this.getBufferStrategy(); 
////        if (bs == null) {
////               this.createBufferStrategy(3); 
////               bs = this.getBufferStrategy(); 
////        }
////	
////		Graphics2D g = null;
////		do {
////		    try{
////		        System.out.println(bs);
////		        g = (Graphics2D) bs.getDrawGraphics() ;
//
//		
//	}
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
				for(DragBox x : DragBoxes){
					System.out.println("PAINTED");
					g.setColor(Color.BLUE);
					g.fillRect(x.OriX, x.OriY, x.Width, x.Height);
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
			for(Yields z : TotalYield){
				g.setColor(new Color(250,250,250));
				g.drawString(z.Type + " : " + z.Amount,800, 50);
			}
			for(Seeds c : seeds){
				g.setColor(new Color(250,250,250));
				g.drawString(c.Type + " seeds : " +c.Amount,800, 75);
			}
			g.drawString("Money :"+ TotalMoney,800,100);
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
	}
	class MyMouseListener implements MouseMotionListener{
		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
		}
		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			if(DragBoxes.size()==1){
				DragBoxes.get(0).Drag(e.getX(),e.getY(),game);
			}
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
			System.out.println(DragBoxes.size());
			System.out.println(DragBoxes.size());
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
			DragBoxes.add(new DragBox(game,e.getX(),e.getY(),e.getX(),e.getY(),0,0,true));
			// TODO Auto-generated method stub

		}
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
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
