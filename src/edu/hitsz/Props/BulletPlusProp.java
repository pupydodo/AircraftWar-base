package edu.hitsz.Props;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.strategy.RingShootStrategy;

public class BulletPlusProp extends AbstractProp {
	private static class BulletPlusEffectTask implements Runnable {
		private final HeroAircraft heroAircraft;
		private final int effectVersion;

		private BulletPlusEffectTask(HeroAircraft heroAircraft, int effectVersion) {
			this.heroAircraft = heroAircraft;
			this.effectVersion = effectVersion;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(HeroAircraft.BULLET_SUPPLY_DURATION_MILLIS);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			heroAircraft.recoverDirectShoot(effectVersion);
		}
	}

	public BulletPlusProp(int locationX, int locationY, int speedX, int speedY) {
		super(locationX, locationY, speedX, speedY);
	}

	@Override
	public void activate(HeroAircraft heroAircraft) {
		int effectVersion = heroAircraft.applyBulletSupply(new RingShootStrategy(), 20);
		Thread thread = new Thread(new BulletPlusEffectTask(heroAircraft, effectVersion), "bullet-plus-prop-effect");
		thread.setDaemon(true);
		thread.start();
		System.out.println("SuperFireSupply active!");
	}

}
