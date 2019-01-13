package GIS;

import Geom.Point3D;

public class Ghost {
	
	private Point3D p;
	private double speed;
	private double radius;
	
	
	
	public Ghost(Point3D point , double speed, double radius) {
	
		this.p = point;
		this.speed = speed;
		this.radius = radius;
		
	}


	public double getRad() {
		return radius;
	}


	public void setRad(double radius) {
		this.radius = radius;
	}


	public Point3D getP() {
		return p;
	}



	public void setPoint(Point3D point) {
		this.p = point;
	}



	public double getSpeed() {
		return speed;
	}



	public void setSpeed(double speed) {
		this.speed = speed;
	}



	@Override
	public String toString() {
		return "Ghost [point=" + p + ", speed=" + speed + "]";
	}

	
	
}
