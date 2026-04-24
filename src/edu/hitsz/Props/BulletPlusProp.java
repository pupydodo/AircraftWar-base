package edu.hitsz.Props;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.strategy.RingShootStrategy;

public class BulletPlusProp extends AbstractProp {

	public BulletPlusProp(int locationX, int locationY, int speedX, int speedY) {
		super(locationX, locationY, speedX, speedY);
	}

	@Override
	public void activate(HeroAircraft heroAircraft) {
		heroAircraft.applyBulletSupply(new RingShootStrategy(), 20, HeroAircraft.BULLET_SUPPLY_DURATION_TICKS);
		System.out.println("SuperFireSupply active!");
	}

}
