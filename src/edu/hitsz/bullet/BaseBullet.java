package edu.hitsz.bullet;

import edu.hitsz.application.Main;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.observer.Observer;
// ...existing code... (removed unused import)

/**
 * 子弹基类
 * @author hitsz
 */
public abstract class BaseBullet extends AbstractFlyingObject implements Observer {

    private int power = 0;

    public BaseBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        super(locationX, locationY, speedX, speedY);
        this.power = power;
    }

    @Override
    public void forward() {
        super.forward();

        // 判定 x 轴出界
        if (locationX <= 0 || locationX >= Main.WINDOW_WIDTH) {
            vanish();
        }

        // 判定 y 轴出界
        if (speedY > 0 && locationY >= Main.WINDOW_HEIGHT ) {
            // 向下飞行出界
            vanish();
        }else if (locationY <= 0){
            // 向上飞行出界
            vanish();
        }
    }

    public int getPower() {
        return power;
    }

    @Override
    public void update(String propType) {
        if ("Bomb".equals(propType)) {
            // Bomb will only affect enemy bullets but to make it safe:
            if (this instanceof EnemyBullet) {
                this.vanish();
            }
        } else if ("Freeze".equals(propType)) {
            if (this instanceof EnemyBullet) {
                int originalSpeedX = this.speedX;
                int originalSpeedY = this.speedY;
                this.speedX = 0;
                this.speedY = 0;
                
                java.util.concurrent.ScheduledExecutorService executor = java.util.concurrent.Executors.newSingleThreadScheduledExecutor();
                // 按实验要求，敌机子弹被冰冻时静止 5s 后恢复
                long freezeSeconds = 5L;
                executor.schedule(() -> {
                    this.speedX = originalSpeedX;
                    this.speedY = originalSpeedY;
                    executor.shutdown();
                }, freezeSeconds, java.util.concurrent.TimeUnit.SECONDS);
            }
        }
    }
}
