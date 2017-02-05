package collision;

import collision.Event;
import collision.Particle;

public class Event implements Comparable<Event>{
	private double t; //when the event will occur
	private Particle a; //particle 1
	private Particle b; //particle 2
	private int aCollisions; //how many collisions particle 1 has had
	private int bCollisions; //how many collisions particle 2 has had

	//constructor for event
	//requires two particles and when the event will occur
	public Event(double t, Particle a, Particle b){
		this.t = t;
		this.a = a;
		this.b = b;

		if(a!=null)
			aCollisions = a.getCollisionCount();
		else
			aCollisions = -1;
		if(b!=null)
			bCollisions = b.getCollisionCount();
		else
			bCollisions = -1;
	}

	//implementation of compareTo for Comparable interface
	@Override
	public int compareTo(Event x) {
		if(t > x.t)
			return 1;
		else if(t < x.t)
			return -1;
		else
			return 0;
	}

	/*
		determines if the event is valid based on how many collisions
		the particles have been in
	*/
	public boolean wasSuperveningEvent(){
		if(a!=null && a.getCollisionCount() != aCollisions)
			return true;
		else if(b!=null && b.getCollisionCount() != bCollisions)
			return true;

		return false;
	}

	public double getTime(){
		return t;
	}

	public Particle getParticle1(){
		return a;
	}

	public Particle getParticle2(){
		return b;
	}
}
