package GIS;

import java.util.ArrayList;

import Coords.map;
import Geom.Point3D;
/**
 * This class represents the course of my Pacman according to the fruits present
 */
public class path{

	public double totalTimePath;
	map theMap = new map();
	
	private ArrayList<Fruit> thepath;
	private ArrayList<Packman> temp_path;

/**
 * Constractor 
 */
	public path() {
		this.totalTimePath = 0;	
		thepath = new ArrayList<>();

	}
	/**
	 * Getter Method
	 * @return  a Total Time from my Path
 	 */
	public double getTheTime() {
		return this.totalTimePath;
	}
	/**
	 * Setter Method
	 * @param total  Receiv a new time 
 	 */
	public void setTheTotalTime(double total) {
		this.totalTimePath = total;
	}
	/**
	 * Getter Method
	 * @return  a Path Under the form ArrayList of Packman from my Game 
 	 */
	public ArrayList<Packman> GetmyTest(){
		return this.temp_path;
	}
	/**
	 * Getter Method
	 * return return a Path of Packman ( return a List of Fruit depending on the route my PAC-Man will receive)
 	 */
	public ArrayList<Fruit> getCPath() {
		return this.thepath;
	}
	/**
	 * Calculations the time between a Pac-Man and a Fruit
	 * @param p The Pacman
	 * @param f The fruit
	 * @return The Time
	 */
	public double CalTime2Points(Packman p , Fruit f) {
		if (theMap.distPixels(p.getPack(), f.getFruit()) < p.getrad()) {
			
			return 0;
		}
		else {	
			return (theMap.distPixels(p.getPack(), f.getFruit())-p.getrad())/p.getSpeed();

		}
	}
	
	/**
	 * Calculate the total time of a Path
	 * @param packman Receiv Packman (with path inside)
	 * @return  The total Time of a Path
	 */
	public double PathTime(Packman packman) {
		
		double totalTime = 0;
		double cal = 0;
		Packman temp = new Packman(packman);
		for (int i = 0; i < packman.getPath().getCPath().size(); i++) {
			cal = CalTime2Points(packman,packman.getPath().getCPath().get(i));
			totalTime = totalTime+cal;
			packman.setPackLocation(packman.getPath().getCPath().get(i).getFruit());

		}
		packman.getPath().setTheTotalTime(totalTime);
		packman.setPackLocation(temp.getPack());

		return totalTime;
	}

	/**
	 * Find the next waypoints of my PAC-Man Path
	 * @param p1 Pacman on which I do my calculations
	 * @param f1 Fruit on which I do my calculations
	 * @param t The time
	 * @return a new Point of my path 
	 */
	public Point3D NextPoint(Packman p1 , Fruit f1, double t) {
		
	double dt = p1.getPath().getTheTime(); // the time from (x1,y1) to (x2,y2) example: 300.
	
	double Vx = p1.getPack().x()/dt+0.05;
	double Vy = p1.getPack().y()/dt+0.05;
	
	double xt = p1.getPack().x()+Vx*(f1.getFruit().x()-p1.getPack().x());
	double yt= p1.getPack().y()+Vy*(f1.getFruit().y()-p1.getPack().y());
	return new Point3D(xt,yt);
	
	}
	

	/**
	 * Setter Method
	 * @param p new ArrayList of Fruit
	 */
	public void setPath(ArrayList<Fruit> p) {
		this.thepath = p;
		
	}
	

}