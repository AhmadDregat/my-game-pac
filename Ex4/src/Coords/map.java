package Coords;

import Geom.Point3D;

/**
 * This class contains the GPS coordinates of the ends of the photo enter my software
 * She know how to do all kinds of conversion calculations between GPS coordinates and pixels
 *
 */
public class map {
	
									
	private Point3D left_up;
	private Point3D RightDown;
	private Point3D right_down;
	private Point3D RightUp;

	private double x_length;
	private double y_length;
	private String Diractroymap;
	
	/**
	 * Default Constractor of my Class 
	 */
	public map() {
		//32.101898,35.202369,0.0, // left down
		//32.105728,35.212416,0.0 // right up
		
		this.left_up = new Point3D(32.105728,  35.202369);
		this.RightDown = new Point3D(32.101898, 35.212416);
		
		this.x_length = this.RightDown.y()-this.left_up.y();
		this.y_length = this.RightDown.x()-this.left_up.x();
		this.Diractroymap = "Pictures&Icones/Ariel.png";
		

	}
	/**
	 * Contractor of Map : Receiv 3 Parameters
	 * @param LeftUpPointGps Point3D in the Lef tUp Coordinates 
	 * @param RightDownGps Point3D in the Right Down Coordinates 
	 * @param MapPath The Directory of Pictures 
	 */
	public map(Point3D rotation_right_down, Point3D rotation_left_up, String pic) {
		super();
		this.right_down = rotation_right_down;
		this.left_up = rotation_left_up;
		this.Diractroymap = pic;
		this.x_length = this.right_down.y()-this.left_up.y();
		this.y_length = this.right_down.x()-this.left_up.x();
	}
	
/**
 * Change the Directory of Picture 
 * @param PathPic Receiv the  new Directory 
 */
	public void setMap(String PathPic) {
		this.Diractroymap = PathPic;
	}
	/**
	 * Getter of directory picture
	 * @return a Directory actual 
	 */
	public String getMapDiractory() {
		return this.Diractroymap;
	}
	

	/**
	 * Convert a Pixel Coordinate to GPS Coordinates.
	 * @param Dx Wight of Picture
	 * @param Dy Hight Of Picture
	 * @return The Point3D in the Coordinate GPS corresponding to the pixel
	 */
	public  Point3D Pixel2coord(double Dx , double Dy) {
		
		//return (new point (lon_x  ,lat_y )

		
		
		return new Point3D(Dy * y_length+left_up.x(),Dx * x_length+left_up.y());
	}
	
	/**
	 * Convert a Gps Coordinates to Pixel Coordinates
	 * @param p Receiv Point3D in GPS Corrdinates 
	 * @return Point3D in Pixel Coordinates
	 */
	public Point3D coordtoPixel(Point3D p) {//return(dx,dy)
		
		
		
	return new Point3D((p.y()-left_up.y())/x_length,(p.x()-left_up.x())/y_length);
			
	}
	/**
	 * Calculate the Distance Between 2 Points Pixels
	 * @param p1 Receiv the First Pixel Point 
	 * @param p2 Receiv the Secnd Pixel Point
	 * @return Distance Between the Points 
	 */
	public double distPixels(Point3D p1, Point3D p2) {
		
		MyCoords m = new MyCoords();
		double result = m.distance3d(Pixel2coord(p1.x(),p1.y()), Pixel2coord(p2.x(),p2.y()));
		return result;

	}
	

}
