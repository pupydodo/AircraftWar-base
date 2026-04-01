package edu.hitsz.Props;

import edu.hitsz.aircraft.HeroAircraft;

public class BloodProp extends AbstractProp {

	private final int healHp;

	public BloodProp(int locationX, int locationY, int speedX, int speedY, int healHp) {
		super(locationX, locationY, speedX, speedY);
		this.healHp = healHp;
	}

	@Override
	public void activate(HeroAircraft heroAircraft) {
		heroAircraft.increaseHp(healHp);
	}
}
