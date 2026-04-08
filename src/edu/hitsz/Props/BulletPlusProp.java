package edu.hitsz.Props;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.strategy.RingShootStrategy;

public class BulletPlusProp extends AbstractProp {

	public BulletPlusProp(int locationX, int locationY, int speedX, int speedY) {
		super(locationX, locationY, speedX, speedY);
	}

	@Override
	public void activate(HeroAircraft heroAircraft) {
		heroAircraft.setShootStrategy(new RingShootStrategy());
		heroAircraft.setShootNum(20);
		System.out.println("SuperFireSupply active!");
	}

}
