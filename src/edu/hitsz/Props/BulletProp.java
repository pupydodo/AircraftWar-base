package edu.hitsz.Props;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.strategy.ScatterShootStrategy;

public class BulletProp extends AbstractProp {

	public BulletProp(int locationX, int locationY, int speedX, int speedY) {
		super(locationX, locationY, speedX, speedY);
	}

	@Override
	public void activate(HeroAircraft heroAircraft) {
		heroAircraft.applyBulletSupply(new ScatterShootStrategy(), 3, HeroAircraft.BULLET_SUPPLY_DURATION_TICKS);
		System.out.println("FireSupply active!");
	}
}
