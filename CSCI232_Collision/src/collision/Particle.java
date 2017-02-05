package collision;

import java.awt.Color;

import edu.princeton.cs.algs4.StdDraw;

public class Particle {
	double rx, ry, vx, vy, m, r; //x-coordinate, y-coordinate, x-velocity, y-velocity, mass, radius
	Color color; //color of the particle
	int collisionCount; //number of collisions this particle has been in

	//constructor for particle class
	public Particle(double rx, double ry, double vx, double vy, double r, double m, Color color){
		this.rx = rx;
		this.ry = ry;
		this.r = r;
		this.vx = vx;
		this.vy = vy;
		this.m = m;
		this.r = r;
		this.color = color;

		collisionCount = 0;
	}

	//updates the position of the particle based on how much time has passed since the last update
	public void update(double dt){
		rx+=vx*dt;
		ry+=vy*dt;
	}

	//draws the particle to the screen
	public void draw(){
		StdDraw.setPenColor(color);
		StdDraw.filledCircle(rx, ry, r);
	}

	//returns when the particle will collide with a vertical wall
	public double collidesX(){
		if(vx > 0){
			return (1-r-rx)/vx;
		}
		else if(vx < 0){
			return (r-rx)/vx;
		}
		else
			return 10000000;
	}

	//returns when the particle will collide with a horizontal wall
	public double collidesY(){
		if(vy > 0){
			return (1-r-ry)/vy;
		}
		else if(vy < 0){
			return (r-ry)/vy;
		}
		else
			return Double.POSITIVE_INFINITY;
	}

	//returns when the particle will collide with particle b
	public double collides(Particle b){
		if(this == b)
			return Double.POSITIVE_INFINITY;

		double dx = b.getRx() - rx;
		double dy = b.getRy() - ry;
		double dvx = b.getVx() - vx;
		double dvy = b.getVy() - vy;
		double dRdV = dx*dvx + dy*dvy;
		if(dRdV >= 0){
			return Double.POSITIVE_INFINITY;
		}

		double dRdR = dx*dx + dy*dy;
		double dVdV = dvx*dvx + dvy*dvy;
		double dist = r + b.getR();
		double d = (dRdV*dRdV) - dVdV * (dRdR - (dist*dist));

		if(d < 0){
			return Double.POSITIVE_INFINITY;
		}

		return -(dRdV + Math.sqrt(d))/dVdV;
	}

	//reverses x-velocity
	public void bounceX(){
		vx *= -1;
		collisionCount++;
	}

	//reverses y-velocity
	public void bounceY(){
		vy *= -1;
		collisionCount++;
	}

	//alters velocity of this particle and particle b when they bounce off eachother
	public void bounce(Particle b){
		double dx = b.getRx() - rx;
		double dy = b.getRy() - ry;
		double dvx = b.getVx() - vx;
		double dvy = b.getVy() - vy;
		double dRdV = dx*dvx + dy*dvy;
		double dist = r + b.getR();
		double j = (2*m*b.getM()*dRdV)/(dist*(m+b.getM()));
		double jx = (j*dx)/dist;
		double jy = (j*dy)/dist;

		vx = vx + jx/m;
		vy = vy + jy/m;
		b.setVx(b.getVx() - jx/b.getM());
		b.setVy(b.getVy() - jy/b.getM());

		collisionCount++;
		b.incCollisions(1);
	}

	public int getCollisionCount(){
		return collisionCount;
	}

	public double getRx() {
		return rx;
	}

	public double getRy() {
		return ry;
	}

	public double getVx() {
		return vx;
	}

	public double getVy() {
		return vy;
	}

	public double getM() {
		return m;
	}

	public double getR() {
		return r;
	}

	public void setVx(double vx){
		this.vx = vx;
	}

	public void setVy(double vy){
		this.vy = vy;
	}

	public void incCollisions(double value){
		collisionCount += value;
	}
}
