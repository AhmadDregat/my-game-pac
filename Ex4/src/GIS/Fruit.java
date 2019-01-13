package GIS;

import Geom.Point3D;
/**
 * This Class represents the Fruits of the Pacman Gam.
 * To remind him the fruits give us points in the official game
 * @author Mimoun Shimon and Omer Paz
 *
 */
public class Fruit {
	
	
	public Point3D Furit;
	private double Weight;
/**
 * Constractor
 * @param p Receiv Point(x,y)
 * @param Weight Receiv Weight
 */
	public Fruit(Point3D p, double Weight) {
		
		this.Furit = p;
		this.Weight = Weight;
	}
	/**
	 * Copy Constractor
	 * @param f Receiv new Fruit to Copy
	 */ 
	public Fruit(Fruit f) {
		this(f.Furit, f.Weight);
	}

/**
 * Getter Method
 * @return the Weight
 */
	public double getWeight() {
		return Weight;
	}
/**
 * Geter Methode 
 * @return the Point of Fruit 
 */
	public Point3D getFruit() {
		return Furit;
	}
	public void setFruit(Point3D p) {
		this.Furit = p;
	}


	@Override
	public String toString() {
		return "Furit [FuritPoint=" + Furit.toString() + ", Weight=" + Weight + "]";
	}


}
