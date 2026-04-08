package edu.hitsz.strategy;
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.bullet.EnemyBullet;
import java.util.LinkedList;
import java.util.List;
public class RingShootStrategy implements ShootStrategy {
    @Override
    public List<BaseBullet> doShoot(AbstractAircraft aircraft) {
        List<BaseBullet> res = new LinkedList<>();
        int x = aircraft.getLocationX();
        int y = aircraft.getLocationY() + aircraft.getDirection() * 2;
        int shootNum = aircraft.getShootNum();
        int power = aircraft.getPower();
        for (int i = 0; i < shootNum; i++) {
            double angle = 2 * Math.PI * i / shootNum;
            int speedX = (int) (5 * Math.sin(angle));
            int speedY = (int) (5 * Math.cos(angle));
            if (aircraft.getDirection() == -1) {
                speedX = -speedX;
                speedY = -speedY;
            }
            BaseBullet bullet;
            if (aircraft instanceof HeroAircraft) {
                bullet = new HeroBullet(x, y, speedX, speedY, power);
            } else {
                bullet = new EnemyBullet(x, y, speedX, speedY, power);
            }
            res.add(bullet);
        }
        return res;
    }
}