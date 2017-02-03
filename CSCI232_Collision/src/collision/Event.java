package collision;

import collision.Event;
import collision.Particle;

public class Event implements Comparable<Event>{
	private double t;
	private Particle a;
	private Particle b;
	private int aCollisions;
	private int bCollisions;

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

	@Override
	public int compareTo(Event x) {
		if(t > x.t)
			return 1;
		else if(t < x.t)
			return -1;
		else
			return 0;
	}

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
