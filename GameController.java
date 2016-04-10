import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.WindowConstants;


public class GameController extends JFrame{
	Land land;
	Spaces space;
	int counter;
	int MouseX;
	int MouseY;
	int SpaceNum;
	int CELL_SIZE = 100;
	ArrayList<Land> Lands = new ArrayList<>();
	ArrayList<Spaces> Spaces= new ArrayList<>();

	public GameController(){
		initGUI();
	}

	public void initGUI(){
		System.out.println("GUI");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		MouseMotionListener mouselistener = new MyMouseListener();
		addMouseMotionListener(mouselistener);
		MouseWheelListener Wheel = new MouseWheelEventDemo();
		addMouseWheelListener(Wheel);
		setVisible(true);
		setSize(1000,1000);
		initObj();
	}
	public void initObj(){
		System.out.println("Obj");
		Lands.add(new Land(this,0,0,0,0,4,4));
		if(Lands.get(0).Size==2){
			Lands.get(0).Width=1;
			Lands.get(0).Height=2;
		}else{
			Lands.get(0).Height=(int) Math.sqrt(Lands.get(0).Size);
			Lands.get(0).Width=(int) Math.sqrt(Lands.get(0).Size);
		}
		for(int x =0; x<Lands.get(0).Size;  x++){
			Spaces.add(new Spaces(this,Lands.get(0).xPos+(CELL_SIZE*x),Lands.get(0).yPos+(CELL_SIZE*x),CELL_SIZE-(CELL_SIZE/20),CELL_SIZE-(CELL_SIZE/20),""));
		}


	}
	public void step(){
		MouseControl(MouseX, MouseY);
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
		g.setColor(Color.gray);
		g.fillRect(0, 0, 10000,10000);
		if (Lands.size() > 0) {
			g.setColor(new Color(25,150,50));
			g.fillRect(Lands.get(0).xPos,Lands.get(0).yPos, Lands.get(0).Width*CELL_SIZE, Lands.get(0).Height*CELL_SIZE);
		}
		g.setColor(new Color(200,100,0));
		for(Spaces x : Spaces){
			g.fillRect(x.xPos, x.yPos, x.Width, x.Height);
		}
		delay(25);
		step();
		repaint();
	}
	public void SpacesControl(){
		Land l = Lands.get(0);
		int num =0;
		int numofdown=0;
		for(Spaces x : Spaces){
			System.out.println("HI");
			if(num<l.Width){
				x.xPos=l.xPos+num*CELL_SIZE;
			}else{
				numofdown++;
				x.yPos=l.yPos+numofdown*CELL_SIZE;
				num=0;
			}
			num++;
		}
		break;

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
