package edu.hitsz.strategy;
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.bullet.EnemyBullet;
import java.util.LinkedList;
import java.util.List;
public class DirectShootStrategy implements ShootStrategy {
    @Override
    public List<BaseBullet> doShoot(AbstractAircraft aircraft) {
        List<BaseBullet> res = new LinkedList<>();
        int x = aircraft.getLocationX();
        int y = aircraft.getLocationY() + aircraft.getDirection() * 2;
        int speedX = 0;
        int speedY = aircraft.getSpeedY() + aircraft.getDirection() * 5;
        int shootNum = aircraft.getShootNum();
        int power = aircraft.getPower();
        BaseBullet bullet;
        for (int i = 0; i < shootNum; i++) {
            if (aircraft instanceof HeroAircraft) {
                bullet = new HeroBullet(x + (i * 2 - shootNum + 1) * 10, y, speedX, speedY, power);
            } else {
                bullet = new EnemyBullet(x + (i * 2 - shootNum + 1) * 10, y, speedX, speedY, power);
            }
            res.add(bullet);
        }
        return res;
    }
}