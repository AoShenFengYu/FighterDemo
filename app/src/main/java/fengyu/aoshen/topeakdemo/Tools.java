package fengyu.aoshen.topeakdemo;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.Display;

/**
 * Created by David on 2017/3/30.
 */
public class Tools {


    public final static String TAG = "Tools";

    /**
     * 取得螢幕大小
     **/
    public static int[] getScreenSize(Activity activity) {

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return new int[]{size.x, size.y - getStatusBarHeight(activity)};
    }


    /**
     * 取得Status Bar高度
     **/
    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int result = 0;
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId);
        }

        return result;
    }

    public static void getResourceBitmapWithSize(final Context context,
                                                 final int resId,
                                                 final int[] size,
                                                 final OnWorkFinishedListener<Bitmap> callback) {

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId);
                final Bitmap result = scaleBitmap(bitmap, size);

                if (callback != null)
                    new Handler(context.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onFinish(result);
                        }
                    });
            }
        });
    }

    private static Bitmap scaleBitmap(Bitmap bitmap, final int[] size) {
        float scale = ((float) size[0]) / ((float) bitmap.getWidth());
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        if (bitmap.getHeight() > size[1])
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), size[1], new Matrix(), true);

        return bitmap;
    }

    public interface OnWorkFinishedListener<T> {
        public void onFinish(T object);
    }
}
