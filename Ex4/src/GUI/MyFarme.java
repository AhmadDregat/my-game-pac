package GUI;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import Algorithm.AlgoTest;
import Coords.map;
import Coords.MyCoords;
import GIS.Fruit;
import GIS.game;
import GIS.Ghost;
import GIS.Packman;
import GIS.path;
import GIS.Player;
import Geom.Box;
import Geom.Point3D;
import Robot.Play;

/**
 * This Class manages the graphical representation of the entire program.
 * the class is an implements of MouseListener is an extents of JFrame.
 * More: http://www.ntu.edu.sg/home/ehchua/programming/java/j4a_gui.html
 *
 */
public class MyFarme extends JFrame implements MouseListener , KeyListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean click = false;
	Image image;
	public boolean game_player=false;
	private double dir;
	boolean solo_game=false;
	private int isGamer=0;



	/***********Set images******************/
	public  Graphics dbg;
	public BufferedImage myImage;
	public BufferedImage packimage;
	public BufferedImage Fruitimage;
	public BufferedImage ghost;
	public BufferedImage box;
	public BufferedImage player;



	double radius = 1;
	int speed = 1;
	/***********************Setting the game Characters**************/

	public  ArrayList<Packman> Packman_arr = new ArrayList<>();
	public  ArrayList<Fruit> Fruits_arr = new ArrayList<>();
	public  ArrayList<Box> Boxs_arr = new ArrayList<>();
	public  ArrayList<Ghost> Ghost_arr = new ArrayList<>();
	private game myGame=new game(Packman_arr, Fruits_arr,Boxs_arr,Ghost_arr);
	public ArrayList<Packman> ArrayTemp=new ArrayList<>();
	public map theMap = new map();
	path TheCloserPackman;
	public Play startGame;
	private MyCoords coord = new MyCoords();


	MenuBar menuOption = new MenuBar();//menu Bar in the interface 






	game temp_run=new game(Packman_arr, Fruits_arr,Boxs_arr,Ghost_arr);

	public MyFarme() 
	{
		initGUI();		
		this.addMouseListener(this); 
		this.addKeyListener(this);

	}

	private void initGUI() {


		try {	myImage = ImageIO.read(new File(theMap.getMapDiractory())); } catch (IOException e) { e.printStackTrace();	}	
		try {	packimage = ImageIO.read(new File("Pictures&Icones/packman.png")); } catch (IOException e) { e.printStackTrace();	}
		try {	Fruitimage = ImageIO.read(new File("Pictures&Icones/fruit.png")); } catch (IOException e) { e.printStackTrace();	}
		try {	box = ImageIO.read(new File("Pictures&Icones/box.png")); } catch (IOException e) { e.printStackTrace();	}
		try {	ghost = ImageIO.read(new File("Pictures&Icones/ghost.png")); } catch (IOException e) { e.printStackTrace();	}
		try {	player = ImageIO.read(new File("Pictures&Icones/player.png")); } catch (IOException e) { e.printStackTrace();	}





		Menu OptionMenu = new Menu("File"); 
		Menu AddMenu = new Menu("Add"); 
		Menu Add_import=new Menu ("Import");



		menuOption.add(OptionMenu);
		menuOption.add(AddMenu);
		menuOption.add(Add_import);


		MenuItem runItem_Manual = new MenuItem("Run Manual");
		MenuItem runItem_Auto= new MenuItem("Run Automatic");
		MenuItem reload_item = new MenuItem("Reload");
		MenuItem exit = new MenuItem("Exit");	
		MenuItem Player_User_item = new MenuItem("Player Manual");	
		MenuItem Csv_read = new MenuItem("Csv");


		OptionMenu.add(runItem_Manual);
		OptionMenu.add(runItem_Auto);
		OptionMenu.add(reload_item);
		OptionMenu.add(exit);

		AddMenu.add(Player_User_item);
		Add_import.add(Csv_read);



		this.setMenuBar(menuOption);


		//Turn on the buttons

		runItem_Manual.addActionListener(new ActionListener() {
			@Override

			public void actionPerformed(ActionEvent e) {

				startGame.setIDs(208727354, 205441884,313332736);
				if (game_player==true) {
					if(myGame.player != null) {

						startGame.getBoard();
						isGamer = 4;
						click = true;
						startGame.start();

						Thread thread = new Thread(){
							ArrayList<String> board = startGame.getBoard();


							public void run(){ 


								while(startGame.isRuning()){ 

									try {sleep(200);} catch (InterruptedException e) {	e.printStackTrace();	}

									startGame.rotate(dir);
									board = startGame.getBoard();
									String info = startGame.getStatistics();
									System.out.println(info);


									try {myGame.makeGame(board);} catch (IOException e1) {e1.printStackTrace();}

									repaint();

								}
							}
						};

						thread.start();

					}

				}
				else 
					JOptionPane.showMessageDialog(null,"EROR: Enter Player to launch te Game ");

			}
		});






		runItem_Auto.addActionListener(new ActionListener() {
			@Override

			public void actionPerformed(ActionEvent e) {

				
				if (game_player==true) {
					JOptionPane.showMessageDialog(null,"The player will be written to a new random point close to a Fruit ");
				}
				
				startGame.setIDs(208727354, 205441884,313332736);
				click = false;

				int ran = (int)(Math.random()*myGame.fruits_arr.size());
				
				System.out.println(myGame.fruits_arr.get(ran).getFruit().x());
				Point3D temp_point_locat=theMap.Pixel2coord(myGame.fruits_arr.get(ran).getFruit().x(), myGame.fruits_arr.get(ran).getFruit().y());
					
				startGame.setInitLocation(temp_point_locat.x(),temp_point_locat.y());

				if(myGame.player != null) {
					startGame.getBoard();
					isGamer = 4;

					startGame.start();

					Thread thread = new Thread(){
						ArrayList<String> board_data = startGame.getBoard();


						public void run(){ 




							while(startGame.isRuning()){ 

								try {sleep(200);} catch (InterruptedException e) {e.printStackTrace();}

								startGame.rotate(dir);
								board_data = startGame.getBoard();
								String info = startGame.getStatistics();
								System.out.println(info);

								try {	temp_run.makeGame(board_data);
								Point3D covertedfromPixel2 = theMap.Pixel2coord(myGame.player.get_player().x(), myGame.player.get_player().y());
								Point3D covertedfromPixel3 = theMap.Pixel2coord(temp_run.player.get_player().x(), temp_run.player.get_player().y());

								AlgoTest algo = new AlgoTest(temp_run);

								temp_run.player.setPoint_player(covertedfromPixel3);
								myGame.player.setPoint_player(covertedfromPixel2);



								double theDir = algo.update_Game(temp_run.player , dir);

								dir = theDir;

								} catch (IOException e) {	e.printStackTrace();	}


								try {myGame.makeGame(board_data);} catch (IOException e1) {e1.printStackTrace();}
								repaint();						
							}
						}
					};
					thread.start();
				}
			}

		});
		
		
		

		reload_item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				solo_game=false;
				radius = 1;
				speed = 1;
				new MenuBar();
				new map();
				Packman_arr = new ArrayList<>();
				Fruits_arr = new ArrayList<>();
				Boxs_arr = new ArrayList<>();
				Ghost_arr = new ArrayList<>();
				myGame=new game(Packman_arr, Fruits_arr,Boxs_arr,Ghost_arr);
				isGamer=0;
				ArrayTemp=new ArrayList<>();
				game_player=false;
				TheCloserPackman=null;
				repaint();
			}
		});

		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});


		Player_User_item.addActionListener  (new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				isGamer = 2;

			}
		});

		Csv_read.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				fileChooser.setDialogTitle("Select an Csv File");
				fileChooser.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("csv","CSV");
				fileChooser.addChoosableFileFilter(filter);

				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					System.out.println(fileChooser.getSelectedFile().getPath())	;

					startGame = new Play(fileChooser.getSelectedFile().getPath());

					try {
						myGame = new game(startGame);
						solo_game=true;
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					isGamer = 4;
					myGame.setfile_directory(fileChooser.getSelectedFile().getPath());
					repaint();

				}
			}
		});



	}
	public void update(Graphics g){

		paint(g);
	}
	public void paint(Graphics g) {

		if(dbg==null){
			image = createImage(5000,5000);
			dbg = image.getGraphics();

		}


		dbg.drawImage(myImage, 8,50, this.getWidth()-17, this.getHeight()-60,null);


		double x1 = 0;
		double y1 = 0 ;
		double x2 = 0;
		double y2 = 0 ;


		if (isGamer!=0) {

			if(myGame.fruits_arr.size() > 0) {

				for (int j=0; j<myGame.Box_arr.size(); j++) {

					x1=(myGame.Box_arr.get(j).getP0().x()*getWidth());
					y1=(myGame.Box_arr.get(j).getP0().y()*getHeight());
					x2=(myGame.Box_arr.get(j).getP1().x()*getWidth());
					y2=(myGame.Box_arr.get(j).getP1().y()*getHeight());	
					double width = x2-x1;
					double height = y2-y1;
					dbg.drawImage(box, (int)x1,(int) y1,(int)width, (int)height, null);

				}
				for (int i=0; i<myGame.fruits_arr.size(); i++) 	{
					x1=(int)(myGame.fruits_arr.get(i).getFruit().x()*getWidth());
					y1=(int)(myGame.fruits_arr.get(i).getFruit().y()*getHeight());	

					dbg.drawImage(Fruitimage, (int)x1, (int)y1,20, 20, null);
				}

			}

			for (int j=0; j<myGame.Packmanarr.size(); j++) {

				x1=(myGame.Packmanarr.get(j).getPack().x()*getWidth());
				y1=(myGame.Packmanarr.get(j).getPack().y()*getHeight());	


				dbg.drawImage(packimage, (int)x1,(int) y1,20, 20, null);

			}

			for (int j=0; j<myGame.ghostarr.size(); j++) {
				x1=(myGame.ghostarr.get(j).getP().x()*getWidth());
				y1=(myGame.ghostarr.get(j).getP().y()*getHeight());	

				dbg.drawImage(ghost, (int)x1,(int) y1,20, 20, null);

			}
			// probleme Player icon 
			if(myGame.player!=null){
				x1=(myGame.player.get_player().x()*getWidth());
				y1=(myGame.player.get_player().y()*getHeight());	

				//dbg.drawImage(player,(int)x1,(int) y1,30, 30,null);
				dbg.setColor(Color.cyan);
				dbg.fillOval((int)x1,(int) y1, 10, 10);
			}
		}

		g.drawImage(image, 0, 0, this);

	}


	@Override
	public void mouseClicked(MouseEvent arg) {

		double x_temp=arg.getX();
		x_temp=x_temp/getWidth();

		double y_temp=arg.getY();
		y_temp=y_temp/getHeight();
		Point3D point_return=new Point3D(x_temp, y_temp, 0);

		Point3D covertedfromPixel = theMap.Pixel2coord(x_temp, y_temp);


		if(click == true) {
			Point3D playerConert = theMap.Pixel2coord(myGame.player.get_player().x(), myGame.player.get_player().y());
			double finalnum = coord.myDir(covertedfromPixel,playerConert);
			System.out.println(finalnum);


			dir = finalnum;
			startGame.rotate(dir);
		}


		if (isGamer==(1))
		{	
			myGame.fruits_arr.add(new Fruit(point_return,1));

			System.out.println("Fruit "+covertedfromPixel.toString());

			repaint();

		}else if (isGamer==(-1))
		{
			myGame.Packmanarr.add(new Packman(point_return, radius, speed));
			System.out.println("Packman "+covertedfromPixel.toString());

			repaint();
		}else if(isGamer==3)
		{
			myGame.ghostarr.add(new Ghost(point_return, radius, speed));
			System.out.println("Ghost "+covertedfromPixel.toString());
			repaint();
		}else if(isGamer==2)
		{
			myGame.player=new Player(point_return, speed,radius);

			System.out.println("Player "+covertedfromPixel.toString());
			if(solo_game==true)
			{startGame.setInitLocation(covertedfromPixel.x(), covertedfromPixel.y());
			game_player=true;
			repaint();
			}
		}

	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	public static void main(String[] args)
	{
		MyFarme window = new MyFarme();
		window.setVisible(true);
		
		
		window.setSize(900,500);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}



}
