package edu.hitsz.strategy;
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.bullet.EnemyBullet;
import java.util.LinkedList;
import java.util.List;
public class ScatterShootStrategy implements ShootStrategy {
    @Override
    public List<BaseBullet> doShoot(AbstractAircraft aircraft) {
        List<BaseBullet> res = new LinkedList<>();
        int x = aircraft.getLocationX();
        int y = aircraft.getLocationY() + aircraft.getDirection() * 2;
        int speedY = aircraft.getSpeedY() + aircraft.getDirection() * 5;
        int shootNum = aircraft.getShootNum();
        int power = aircraft.getPower();
        for (int i = 0; i < shootNum; i++) {
            int speedX = (i - shootNum / 2) * 2;
            BaseBullet bullet;
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