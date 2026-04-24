package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.strategy.ShootStrategy;
import java.util.List;
import java.util.LinkedList;

/**
 * 所有种类飞机的抽象父类
 * @author hitsz
 */
public abstract class AbstractAircraft extends AbstractFlyingObject {

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

}
