package com.bkw.mvp2.utils;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.View;

import com.bkw.mvp2.global.GlobalApplication;

import org.jetbrains.annotations.NotNull;

/**
 * 今日头条适配方案
 *
 * @author Horrarndoo
 * @date 2017/8/31
 * <p>
 * 显示相关工具类
 */
public class DisplayUtils {

    /**
     * 适配：修改设备密度
     */
    private static float sNoncompatDensity;
    private static float sNoncompatScaledDensity;

    public static void setCustomDensity(@NotNull Activity activity, @NonNull final Application application) {
        //获取当前设备显示密度
        DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();

        if (sNoncompatDensity == 0) {
            //获得当前设备的密度与缩放密度
            sNoncompatDensity = appDisplayMetrics.density;
            sNoncompatScaledDensity = appDisplayMetrics.scaledDensity;
            //防止系统切换后不起效果。横竖屏切换时重新获取一次缩放密度
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        sNoncompatScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }

        //定义缩放目标宽度
        float targetDensity = appDisplayMetrics.widthPixels / 360;
        //防止字体变小
        float targetScaleDensity = targetDensity * (sNoncompatScaledDensity / sNoncompatDensity);
        int targetDensityDpi = (int) (160 * targetDensity);

        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.scaledDensity = targetScaleDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;

        //将当前activity的屏幕密度与缩放密度dpi更改为目标值
        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaleDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }


    /**
     * 将px值转换为dp值
     */
    public static int px2dp(float pxValue) {
        final float scale = GlobalApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dp值转换为px值
     */
    public static int dp2px(float dpValue) {
        final float scale = GlobalApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * DP转PX
     */
    public static int dpToPx(Context context, float dpSize) {
        return (int) (context.getResources().getDisplayMetrics().density * dpSize);
    }

    /**
     * @param backgroundTop
     * @param backgroundBottom
     * @param paint
     * @return paint绘制居中文字时，获取文本底部坐标
     */
    public static float getTextBaseLine(float backgroundTop, float backgroundBottom, Paint paint) {
        final Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return (backgroundTop + backgroundBottom - fontMetrics.bottom - fontMetrics.top) / 2;
    }

    /**
     * 将px值转换为sp值
     */
    public static int px2sp(float pxValue) {
        final float scale = GlobalApplication.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将sp值转换为px值
     */
    public static int sp2px(float dpValue) {
        final float scale = GlobalApplication.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidthPixels(Activity context) {
        DisplayMetrics metric = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }

    /**
     * 获取屏幕高度
     */
    public static int getScreenHeightPixels(Activity context) {
        DisplayMetrics metric = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.heightPixels;
    }

    /**
     * 将一个view转换成bitmap位图
     *
     * @param view 要转换的View
     * @return view转换的bitmap
     */
    public static Bitmap viewToBitmap(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(bitmap));
        return bitmap;
    }

    /**
     * 获取模糊虚化的bitmap
     *
     * @param context
     * @param bitmap  要模糊的图片
     * @param radius  模糊等级 >=0 && <=25
     * @return
     */
    public static Bitmap getBlurBitmap(Context context, Bitmap bitmap, int radius) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return blurBitmap(context, bitmap, radius);
        }
        return bitmap;
    }

    /**
     * android系统的模糊方法
     *
     * @param bitmap 要模糊的图片
     * @param radius 模糊等级 >=0 && <=25
     */
    public static Bitmap blurBitmap(Context context, Bitmap bitmap, int radius) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            //Let's create an empty bitmap with the same size of the bitmap we want to blur
            Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap
                    .Config.ARGB_8888);
            //Instantiate a new Renderscript
            RenderScript rs = RenderScript.create(context);
            //Create an Intrinsic Blur Script using the Renderscript
            ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            //Create the Allocations (in/out) with the Renderscript and the in/out bitmaps
            Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
            Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);
            //Set the radius of the blur
            blurScript.setRadius(radius);
            //Perform the Renderscript
            blurScript.setInput(allIn);
            blurScript.forEach(allOut);
            //Copy the final bitmap created by the out Allocation to the outBitmap
            allOut.copyTo(outBitmap);
            //recycle the original bitmap
            bitmap.recycle();
            //After finishing everything, we destroy the Renderscript.
            rs.destroy();
            return outBitmap;
        } else {
            return bitmap;
        }
    }

    /**
     * 显示网络虚化图片
     *
     * @param context   context
     * @param imgUrl    图片url
     * @param imageView 要显示的imageview

    public static void displayBlurImg(Context context, final String imgUrl, ImageView imageView) {
    // "23":模糊度；"4":图片缩放4倍后再进行模糊
    Glide.with(context)
    .load(imgUrl)
    .error(R.drawable.stackblur_default)
    .placeholder(R.drawable.stackblur_default)
    .crossFade(300)
    .bitmapTransform(new BlurTransformation(context, 23, 4))
    .into(imageView);
    }
     */
    /**
     * 显示本地虚化图片
     *
     * @param context   context
     * @param file      本地图片file
     * @param imageView 要显示的imageview

    public static void displayBlurImg(Context context, File file, ImageView imageView) {
    // "23":模糊度；"4":图片缩放4倍后再进行模糊
    Glide.with(context)
    .load(file)
    .error(R.drawable.stackblur_default)
    .placeholder(R.drawable.stackblur_default)
    .crossFade(300)
    .bitmapTransform(new BlurTransformation(context, 23, 4))
    .into(imageView);
    }
     */
    /**
     * 显示资源虚化图片
     *
     * @param context    context
     * @param resourceId 图片资源id
     * @param imageView  要显示的imageview

    public static void displayBlurImg(Context context, Integer resourceId, ImageView imageView) {
    // "23":模糊度；"4":图片缩放4倍后再进行模糊
    Glide.with(context)
    .load(resourceId)
    .error(R.drawable.stackblur_default)
    .placeholder(R.drawable.stackblur_default)
    .crossFade(300)
    .bitmapTransform(new BlurTransformation(context, 23, 4))
    .into(imageView);
    }     */
}
