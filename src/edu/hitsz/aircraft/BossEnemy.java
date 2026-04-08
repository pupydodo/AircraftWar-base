package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.strategy.RingShootStrategy;

public class BossEnemy extends AbstractAircraft {

    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.shootNum = 20;
        this.power = 30;
        this.direction = 1;
        this.shootStrategy = new RingShootStrategy();
    }

    @Override
    public void forward() {
        super.forward();
        if (locationX <= 0 || locationX >= Main.WINDOW_WIDTH) {
            speedX = -speedX;
        }
    }

}