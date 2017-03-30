package fengyu.aoshen.topeakdemo.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import fengyu.aoshen.topeakdemo.R;
import fengyu.aoshen.topeakdemo.fighter.Shooter;

/**
 * Created by David on 2017/3/30.
 * <p/>
 * 戰鬥機
 */
public class Fighter extends ImageView {

    public final static String TAG = "Fighter";

    public final static float COORDINATE_NO_SET = -1;

    private int[] screenSize;

    private Shooter shooter;

    private float currentX;
    private float currentY;

    /**
     * For Touch Listener to use
     **/
    private float originalX;
    private float originalY;
    private float originalRawX;
    private float originalRawY;


    public Fighter(Context context, int[] screenSize) {
        super(context);
        this.screenSize = screenSize;
        init();
    }

    private void init() {
        initExterior();
        initFighterSize();
        initShooter(getContext());
        initDragListener();

        moveFighterToCenter();

    }

    private void initDragListener() {
        originalX = COORDINATE_NO_SET;
        originalY = COORDINATE_NO_SET;
        originalRawX = COORDINATE_NO_SET;
        originalRawY = COORDINATE_NO_SET;

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        originalX = v.getX();
                        originalY = v.getY();
                        originalRawX = event.getRawX();
                        originalRawY = event.getRawY();
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        moveFighter(
                                event.getRawX() - originalRawX,
                                event.getRawY() - originalRawY);
                        return true;

                    case MotionEvent.ACTION_UP:
                        originalX = 0;
                        originalY = 0;
                        return true;
                }

                return false;
            }
        });
    }

    private void moveFighter(float deltaX, float deltaY) {
        currentX = originalX + deltaX;
        currentY = originalY + deltaY;
        setX(currentX);
        setY(currentY);
    }

    private void moveFighterToCenter() {
        currentX = screenSize[0] / 2 - getContext().getResources().getDimensionPixelOffset(R.dimen.fighter_width) / 2;
        currentY = screenSize[1] / 2 - getContext().getResources().getDimensionPixelOffset(R.dimen.fighter_height) / 2;
        setX(currentX);
        setY(currentY);
    }

    private void initExterior() {
        setImageResource(R.drawable.picture_fighter);
        setScaleType(ScaleType.FIT_CENTER);
    }

    private void initFighterSize() {
        int width = getContext().getResources().getDimensionPixelOffset(R.dimen.fighter_width);
        int height = getContext().getResources().getDimensionPixelOffset(R.dimen.fighter_height);

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, height);
        setLayoutParams(layoutParams);
    }

    private void initShooter(Context context) {
        shooter = new Shooter(context, this);
    }

    public Shooter getShooter() {
        return shooter;
    }

    public boolean hasShooter() {
        if (shooter == null)
            return false;
        else
            return true;
    }

    public float getCurrentX() {
        return currentX;
    }

    public float getCurrentY() {
        return currentY;
    }

    public boolean setOnShootListener(Shooter.OnShootListener onShootListener) {
        if (shooter != null) {
            shooter.setOnShootListener(onShootListener);
            return true;
        } else
            return false;
    }

    public int getBulletCount() {
        if (shooter != null) {
            return shooter.getBulletCount();
        } else
            return 0;
    }
}
