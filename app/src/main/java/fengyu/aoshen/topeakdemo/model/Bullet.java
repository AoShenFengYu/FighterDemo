package fengyu.aoshen.topeakdemo.model;

import fengyu.aoshen.topeakdemo.model.BulletInfo;

/**
 * Created by David on 2017/3/30.
 */
public class Bullet {

    public final static String TAG = "Bullet";


    private BulletInfo bulletInfo;
    private int x;
    private int y;

    public Bullet(BulletInfo bulletInfo, float currentX, float currentY) {
        this.bulletInfo = bulletInfo;
        this.x = (int) currentX;
        this.y = (int) currentY;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return bulletInfo.getSpeed();
    }

    public BulletInfo getBulletInfo() {
        return bulletInfo;
    }
}
