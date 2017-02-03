package collision;

import edu.princeton.cs.algs4.StdDraw;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.princeton.cs.algs4.MinPQ;

/**
 *
 * @author bryanplant
 */
public class CollisionSystem {
	private static double drawFreq = 2;
	private MinPQ<Event> pq;
	private Particle[] particles;
	private double t = 0;

	public CollisionSystem(Particle[] particles){
		this.particles = particles;
	}

	private void updateEvents(Particle a){
		if(a == null) return;

		for(int i = 0; i < particles.length; i++){
			double dt = a.collides(particles[i]);
			if(dt != Double.POSITIVE_INFINITY)
				pq.insert(new Event(t + dt, a, particles[i]));
		}

		pq.insert(new Event(t + a.collidesX(), a, null));
		pq.insert(new Event(t + a.collidesY(), null, a));
	}

	private void draw(){
		StdDraw.clear();
		for(int i = 0; i < particles.length; i++){
			particles[i].draw();
		}
		StdDraw.show();
		StdDraw.pause(20);
		pq.insert(new Event(t + 1.0/drawFreq, null, null));

		System.out.println(t);
	}

	private void simulate(){
		pq = new MinPQ<Event>();
		for(int i = 0; i < particles.length; i++){
			updateEvents(particles[i]);
		}
		pq.insert(new Event(0, null, null));

		while(true){
			Event e = pq.delMin();

			if(!e.wasSuperveningEvent()){
				Particle a = e.getParticle1();
				Particle b = e.getParticle2();
				for(int i = 0; i < particles.length; i++){
					particles[i].update(e.getTime() - t);
				}
				t = e.getTime();

				if(a != null && b != null)
					a.bounce(b);
				else if(a != null && b == null)
					a.bounceX();
				else if(a == null && b != null)
					b.bounceY();
				else if(a == null && b == null)
					draw();

				updateEvents(a);
				updateEvents(b);
			}
		}
	}

    public static void main(String[] args) {
        StdDraw.setCanvasSize(800, 800);
        StdDraw.enableDoubleBuffering();

        Scanner sc = null;
		try {
			sc = new Scanner(new File(args[0]));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		Particle[] particles;

	    int size = sc.nextInt();
        particles = new Particle[size];
        for (int i = 0; i < size; i++) {
        	double rx     = sc.nextDouble();
        	double ry     = sc.nextDouble();
        	double vx     = sc.nextDouble();
        	double vy     = sc.nextDouble();
	        double radius = sc.nextDouble();
	        double mass   = sc.nextDouble();
	        int r         = sc.nextInt();
	        int g         = sc.nextInt();
	        int b         = sc.nextInt();
	        Color color   = new Color(r, g, b);
	        particles[i] = new Particle(rx, ry, vx, vy, radius, mass, color);
        }
        CollisionSystem cs = new CollisionSystem(particles);
        cs.simulate();
    }

}

