package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.strategy.DirectShootStrategy;

import java.util.LinkedList;
import java.util.List;

public class ElitePlusEnemy extends  AbstractAircraft{
    public ElitePlusEnemy(int locationX, int locationY, int speedX, int speedY, int hp){
        super(locationX, locationY, speedX, speedY, hp);
        this.shootNum = 2;
        this.power = 30;
        this.direction = 1;
        this.shootStrategy = new DirectShootStrategy();
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    @Override
    public void update(String propType) {
        super.update(propType);
        if ("Freeze".equals(propType)) {
            // 精锐敌机：静止 3s 后恢复
            temporaryFreeze(3);
        }
    }
}
