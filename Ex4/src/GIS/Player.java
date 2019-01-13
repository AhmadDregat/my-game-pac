package GIS;

import Geom.Point3D;

public class Player {
	
	private Point3D player;
	private double speed;
	private double radius;
	
	
	public Player(Point3D p1, double speed, double radius) {
		
		this.player = p1;
		this.speed = speed;
		this.radius = radius;
	}
	
	public Point3D get_player() {
		return player;
	}



	public void setPoint_player(Point3D point_player) {
		player = point_player;
	}

	public Point3D nextPoint (double dir) {
		double fixDir = (dir*Math.PI/180);

	
		double t = 100.0D;
		double x = this.get_player().x()+this.getSpeed()*Math.sin(fixDir)*t;
		double y = this.get_player().y()+this.getSpeed()*Math.cos(fixDir)*t;		
		
		
		return new Point3D(x,y);
	}



	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public double getRad() {
		return radius;
	}

	public void setRad(double radius) {
		this.radius = radius;
	}

}
