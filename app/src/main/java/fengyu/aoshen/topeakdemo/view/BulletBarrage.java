package fengyu.aoshen.topeakdemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import fengyu.aoshen.topeakdemo.model.Bullet;
import fengyu.aoshen.topeakdemo.controller.BulletBarrageController;
import fengyu.aoshen.topeakdemo.controller.BulletInfoController;

/**
 * Created by David on 2017/3/30.
 * <p/>
 * 子彈彈幕的畫面
 */
public class BulletBarrage extends ImageView {

    public final static String TAG = "BulletContainer";

    private boolean isPause;
    private int[] screenSize;

    public BulletBarrage(Context context, int[] screenSize) {
        super(context);
        this.screenSize = screenSize;
        init();
    }

    private void init() {
        initValue();

        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        setLayoutParams(lp);
    }

    private void initValue() {
        isPause = false;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        BulletBarrageController.getInstance(getContext()).beforeDrawing(screenSize);

        for (Bullet bullet : BulletBarrageController.getInstance(getContext()).getBullets()) {
            drawBullet(canvas, bullet);
        }

        if (BulletBarrageController.getInstance(getContext()).getBulletCount() > 0) {
            postInvalidate();
        }

    }

    private void drawBullet(Canvas canvas, Bullet bullet) {

        Bitmap bitmap = BulletInfoController.getInstance(getContext()).getBulletBitmapByType(bullet.getBulletInfo().getType());
        if (bitmap != null)
            canvas.drawBitmap(bitmap, bullet.getX(), bullet.getY(), null);
    }

}
