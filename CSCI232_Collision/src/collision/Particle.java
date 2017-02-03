package collision;

import java.awt.Color;

import edu.princeton.cs.algs4.StdDraw;

public class Particle {
	double rx, ry, vx, vy, m, r;
	Color color;
	int collisionCount;

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

	public void update(double dt){
		rx+=vx*dt;
		ry+=vy*dt;
	}

	public void draw(){
		StdDraw.setPenColor(color);
		StdDraw.filledCircle(rx, ry, r);
	}

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

	public void bounceX(){
		vx *= -1;
		collisionCount++;
	}

	public void bounceY(){
		vy *= -1;
		collisionCount++;
	}

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
