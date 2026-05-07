package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
// ...existing code... (no extra imports needed from same package)
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.strategy.ShootStrategy;
import java.util.List;
import java.util.LinkedList;
import edu.hitsz.observer.Observer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 所有种类飞机的抽象父类
 * @author hitsz
 */
public abstract class AbstractAircraft extends AbstractFlyingObject implements Observer {

    //最大生命值
    protected int maxHp;
    protected int hp;

    protected int shootNum = 1;
    protected int power = 30;
    protected int direction = 1;
    protected ShootStrategy shootStrategy;

    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
    }

    public void decreaseHp(int decrease){
        hp -= decrease;
        if(hp <= 0){
            hp=0;
            vanish();
        }
    }

    public void increaseHp(int increase) {
        if (increase <= 0) {
            return;
        }
        hp += increase;
        if (hp > maxHp) {
            hp = maxHp;
        }
    }

    public void setHp(int hp) {
        this.maxHp = hp;
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }

    public int getShootNum() { return shootNum; }
    public void setShootNum(int shootNum) { this.shootNum = shootNum; }

    public int getPower() { return power; }
    public void setPower(int power) { this.power = power; }

    public int getDirection() { return direction; }
    public void setDirection(int direction) { this.direction = direction; }

    public void setShootStrategy(ShootStrategy strategy) { this.shootStrategy = strategy; }
    public ShootStrategy getShootStrategy() { return this.shootStrategy; }

    /**
     * 飞机射击方法
     * @return
     *  可射击对象需实现，返回子弹列表
     *  非可射击对象空实现，返回空列表
     */
    public List<BaseBullet> shoot() {
        if (shootStrategy != null) {
            return shootStrategy.doShoot(this);
        }
        return new LinkedList<>();
    }

    @Override
    public void update(String propType) {
        if ("Bomb".equals(propType)) {
            // Default behavior for Bomb: vanish
            this.vanish();
        } else if ("Freeze".equals(propType)) {
            // 冰冻道具：暂时冻结飞机（速度置零），若需要可由子类覆盖此行为
            System.out.println("FreezeSupply active! Aircraft frozen at (" + locationX + "," + locationY + ")");
            // 使用 HeroAircraft 中的道具持续时间作为冻结时长（向下兼容）
            int freezeSeconds = (int) (HeroAircraft.BULLET_SUPPLY_DURATION_MILLIS / 1000L);
            if (freezeSeconds <= 0) {
                freezeSeconds = 5; // 后备值
            }
            temporaryFreeze(freezeSeconds);
        }
    }

    protected void temporaryFreeze(int seconds) {
        int originalSpeedX = this.speedX;
        int originalSpeedY = this.speedY;
        this.speedX = 0;
        this.speedY = 0;

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(() -> {
            this.speedX = originalSpeedX;
            this.speedY = originalSpeedY;
            executor.shutdown();
        }, seconds, TimeUnit.SECONDS);
    }

    protected void temporarySlowDown(int seconds, double factor) {
        int originalSpeedX = this.speedX;
        int originalSpeedY = this.speedY;
        this.speedX = (int) (this.speedX * factor);
        this.speedY = (int) (this.speedY * factor);

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(() -> {
            this.speedX = originalSpeedX;
            this.speedY = originalSpeedY;
            executor.shutdown();
        }, seconds, TimeUnit.SECONDS);
    }

}
