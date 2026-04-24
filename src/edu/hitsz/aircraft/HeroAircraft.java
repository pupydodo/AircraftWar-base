package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.strategy.DirectShootStrategy;
import edu.hitsz.strategy.ShootStrategy;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 英雄飞机，游戏玩家操控
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {

    public static final int BULLET_SUPPLY_DURATION_TICKS = 300;
    public static final int GAME_TICK_MILLIS = 40;
    public static final long BULLET_SUPPLY_DURATION_MILLIS = (long) BULLET_SUPPLY_DURATION_TICKS * GAME_TICK_MILLIS;
    private static final int DEFAULT_SHOOT_NUM = 1;
    private static final int DEFAULT_POWER = 30;

    private static final HeroAircraft instance = new HeroAircraft(Main.WINDOW_WIDTH / 2,
            Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight(),
            0,0, 300);

    private final ShootStrategy directShootStrategy = new DirectShootStrategy();
    private final AtomicInteger bulletSupplyVersion = new AtomicInteger(0);

    public HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.shootNum = DEFAULT_SHOOT_NUM;
        this.power = DEFAULT_POWER;
        this.direction = -1;
        this.shootStrategy = directShootStrategy;
    }
    public static HeroAircraft getInstance() {
        return instance;
    }

    public void reset() {
        isValid = true;
        hp = maxHp;
        setLocation(Main.WINDOW_WIDTH / 2.0,
                Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight());
        shootNum = DEFAULT_SHOOT_NUM;
        power = DEFAULT_POWER;
        direction = -1;
        shootStrategy = directShootStrategy;
        bulletSupplyVersion.incrementAndGet();
    }

    public synchronized int applyBulletSupply(ShootStrategy shootStrategy, int shootNum) {
        this.shootStrategy = shootStrategy;
        this.shootNum = shootNum;
        return bulletSupplyVersion.incrementAndGet();
    }

    public synchronized void recoverDirectShoot(int expectedVersion) {
        if (bulletSupplyVersion.get() != expectedVersion) {
            return;
        }
        shootStrategy = directShootStrategy;
        shootNum = DEFAULT_SHOOT_NUM;
    }

    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }

}
