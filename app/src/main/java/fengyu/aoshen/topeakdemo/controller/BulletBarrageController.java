package fengyu.aoshen.topeakdemo.controller;

import android.content.Context;

import java.util.ArrayList;

import fengyu.aoshen.topeakdemo.R;
import fengyu.aoshen.topeakdemo.model.Bullet;
import fengyu.aoshen.topeakdemo.view.BulletBarrage;
import fengyu.aoshen.topeakdemo.model.BulletInfo;
import fengyu.aoshen.topeakdemo.view.Fighter;

/**
 * Created by David on 2017/3/30.
 */
public class BulletBarrageController {

    public final static String TAG = "BulletBarrageController";

    private static BulletBarrageController sharedInstance;

    private Context context;
    private BulletBarrage bulletBarrage;

    private ArrayList<Bullet> bullets;
    private ArrayList<BulletInfo> bulletInfos; // 記住目前所有子彈的種類

    private BulletBarrageController(Context context) {
        this.context = context;
        bullets = new ArrayList<>();
    }


    public static BulletBarrageController getInstance(Context context) {
        if (sharedInstance == null)
            sharedInstance = new BulletBarrageController(context);

        return sharedInstance;
    }

    // 告訴Manager, 哪台戰鬥機，要射哪種子彈?
    public void createBullet(Fighter fighter, BulletInfo bulletInfo) {

        int x = (int) (fighter.getCurrentX() +

                (context.getResources().getDimensionPixelOffset(R.dimen.fighter_width) -
                        context.getResources().getDimensionPixelOffset(R.dimen.bullet_width)) / 2);

        int y = (int) (fighter.getCurrentY() +

                (context.getResources().getDimensionPixelOffset(R.dimen.fighter_height) -
                        context.getResources().getDimensionPixelOffset(R.dimen.bullet_height)) / 2);

        Bullet bullet = new Bullet(
                bulletInfo,
                x,
                y);
        bullets.add(bullet);

        if (bulletBarrage != null)
            bulletBarrage.postInvalidate();
    }

    // bind
    public void setBulletBarrage(BulletBarrage bulletBarrage) {
        this.bulletBarrage = bulletBarrage;
    }

    // set Bullet Types
    public void setBulletInfos(ArrayList<BulletInfo> bulletInfos) {
        this.bulletInfos = bulletInfos;
    }


    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public int getBulletCount() {
        return bullets.size();
    }

    // TODO 想個好名字?
    public void beforeDrawing(int[] screenSize) {
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);

            bullet.setY(bullet.getY() - bullet.getSpeed());

            if (bullet.getY() < -context.getResources().getDimensionPixelOffset(R.dimen.bullet_height)) {
                bullets.remove(i);
                i--;
            }
        }
    }

    public void clearAllBullts() {
        bullets.clear();
    }

}
