package edu.hitsz.aircraft;

import edu.hitsz.application.Main;

/**
 * 普通敌机
 * 不可射击、不掉落道具
 * @author hitsz
 */
public class MobEnemy extends AbstractAircraft {

    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
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
            // 普通敌机：永久静止
            this.speedX = 0;
            this.speedY = 0;
        }
    }

}
