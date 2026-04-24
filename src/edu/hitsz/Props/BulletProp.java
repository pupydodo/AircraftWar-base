package edu.hitsz.Props;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.strategy.ScatterShootStrategy;

public class BulletProp extends AbstractProp {
	private static class BulletEffectTask implements Runnable {
		private final HeroAircraft heroAircraft;
		private final int effectVersion;

		private BulletEffectTask(HeroAircraft heroAircraft, int effectVersion) {
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

	public BulletProp(int locationX, int locationY, int speedX, int speedY) {
		super(locationX, locationY, speedX, speedY);
	}

	@Override
	public void activate(HeroAircraft heroAircraft) {
		int effectVersion = heroAircraft.applyBulletSupply(new ScatterShootStrategy(), 3);
		Thread thread = new Thread(new BulletEffectTask(heroAircraft, effectVersion), "bullet-prop-effect");
		thread.setDaemon(true);
		thread.start();
		System.out.println("FireSupply active!");
	}
}
