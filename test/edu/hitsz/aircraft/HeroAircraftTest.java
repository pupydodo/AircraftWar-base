package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.strategy.DirectShootStrategy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HeroAircraftTest {

    private HeroAircraft hero;

    @BeforeEach
    void setUp() throws Exception {
        hero = HeroAircraft.getInstance();
        resetHeroState();
    }

    @AfterEach
    void tearDown() throws Exception {
        resetHeroState();
    }

    @Test
    void getInstance_shouldReturnSameObject() {
        HeroAircraft another = HeroAircraft.getInstance();
        assertSame(hero, another);
    }

    @Test
    void forward_shouldNotMoveHero() {
        hero.setLocation(300, 500);
        int beforeX = hero.getLocationX();
        int beforeY = hero.getLocationY();

        hero.forward();

        assertEquals(beforeX, hero.getLocationX());
        assertEquals(beforeY, hero.getLocationY());
    }

    @Test
    void increaseHp_shouldCapAtMaxHp_andIgnoreNonPositiveInput() {
        hero.decreaseHp(100);
        assertEquals(200, hero.getHp());

        hero.increaseHp(50);
        assertEquals(250, hero.getHp());

        hero.increaseHp(1000);
        assertEquals(300, hero.getHp());

        hero.increaseHp(0);
        assertEquals(300, hero.getHp());

        hero.increaseHp(-10);
        assertEquals(300, hero.getHp());
    }

    @Test
    void decreaseHp_shouldSetZeroAndInvalidWhenDead() {
        hero.decreaseHp(hero.getHp() + 1);

        assertEquals(0, hero.getHp());
        assertTrue(hero.notValid());
    }

    @Test
    void shoot_withDirectStrategy_shouldReturnHeroBullets() {
        hero.setShootNum(3);
        hero.setPower(35);
        hero.setShootStrategy(new DirectShootStrategy());

        List<BaseBullet> bullets = hero.shoot();

        assertNotNull(bullets);
        assertEquals(3, bullets.size());
        for (BaseBullet bullet : bullets) {
            assertTrue(bullet instanceof HeroBullet);
            assertEquals(35, bullet.getPower());
        }
    }

    @Test
    void shoot_withNullStrategy_shouldReturnEmptyList() {
        hero.setShootStrategy(null);

        List<BaseBullet> bullets = hero.shoot();

        assertNotNull(bullets);
        assertTrue(bullets.isEmpty());
    }

    private void resetHeroState() throws Exception {
        Field isValidField = AbstractFlyingObject.class.getDeclaredField("isValid");
        isValidField.setAccessible(true);
        isValidField.set(hero, true);

        hero.setLocation(
                Main.WINDOW_WIDTH / 2.0,
                Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight()
        );

        if (hero.getHp() < 300) {
            hero.increaseHp(300 - hero.getHp());
        } else if (hero.getHp() > 300) {
            hero.decreaseHp(hero.getHp() - 300);
            isValidField.set(hero, true);
        }

        hero.setShootNum(1);
        hero.setPower(30);
        hero.setDirection(-1);
        hero.setShootStrategy(new DirectShootStrategy());
    }
}

