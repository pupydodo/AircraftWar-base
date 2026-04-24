package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.strategy.DirectShootStrategy;
import edu.hitsz.strategy.ShootStrategy;

/**
 * 英雄飞机，游戏玩家操控
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {

    public static final int BULLET_SUPPLY_DURATION_TICKS = 300;
    private static final int DEFAULT_SHOOT_NUM = 1;
    private static final int DEFAULT_POWER = 30;

    private static final HeroAircraft instance = new HeroAircraft(Main.WINDOW_WIDTH / 2,
            Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight(),
            0,0, 300);

    private final ShootStrategy directShootStrategy = new DirectShootStrategy();
    private int bulletSupplyRemainTicks = 0;

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
        bulletSupplyRemainTicks = 0;
    }

    public void applyBulletSupply(ShootStrategy shootStrategy, int shootNum, int durationTicks) {
        this.shootStrategy = shootStrategy;
        this.shootNum = shootNum;
        this.bulletSupplyRemainTicks = Math.max(durationTicks, 0);
    }

    public void tickBulletSupply() {
        if (bulletSupplyRemainTicks <= 0) {
            return;
        }
        bulletSupplyRemainTicks--;
        if (bulletSupplyRemainTicks == 0) {
            recoverDirectShoot();
        }
    }

    public void recoverDirectShoot() {
        shootStrategy = directShootStrategy;
        shootNum = DEFAULT_SHOOT_NUM;
    }

    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }

}
