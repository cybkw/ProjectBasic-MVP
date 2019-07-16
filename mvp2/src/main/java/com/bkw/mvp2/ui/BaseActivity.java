package com.bkw.mvp2.ui;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bkw.mvp2.R;
import com.bkw.mvp2.global.GlobalApplication;
import com.bkw.mvp2.utils.DisplayUtils;
import com.bkw.mvp2.utils.StatusBarUtils;
import com.bkw.mvp2.widgets.WaitProgressDialog;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * 基类Activity
 *
 * @author bkw
 * @date 2018/3/14
 */
public abstract class BaseActivity extends SupportActivity {


    /**
     * 全局上下文对象
     */
    protected Context mContext;

    private WaitProgressDialog mWaitProgressDialog;
    private boolean isTransAnim;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        DisplayUtils.setCustomDensity(this, getApplication());
    }

    /**
     * 初始化
     */
    private void init() {
        mContext = GlobalApplication.getContext();
        isTransAnim = true;
    }


    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
        finishAcMove();
    }

    protected void setLayoutMargingTop(Activity activity, LinearLayout layout) {
        int top = StatusBarUtils.getStatusBarHeight(activity);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) layout.getLayoutParams();
        lp.setMargins(0, top, 0, 0);
        layout.setLayoutParams(lp);
    }

    protected void setFRayoutMargingTop(Activity activity, RelativeLayout layout) {
        int top = StatusBarUtils.getStatusBarHeight(activity);
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) layout.getLayoutParams();
        lp.setMargins(0, top, 0, 0);
        layout.setLayoutParams(lp);
    }

    protected void setFLayoutMargingTop(Activity activity, LinearLayout layout) {
        int top = StatusBarUtils.getStatusBarHeight(activity);
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) layout.getLayoutParams();
        lp.setMargins(0, top, 0, 0);
        layout.setLayoutParams(lp);
    }

    /**
     * 检测系统版本并使状态栏全透明
     */
    protected void transparentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS |
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * 显示toast
     */
    private Toast toast;

    public void showToast(String msg) {
        if (TextUtils.isEmpty(msg)) {
            msg = "好像出了点小问题~";
        }
        if (toast == null) {
            toast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }


    /**
     * 显示等待提示框
     * (点击不可取消的)
     *
     * @param msg 提示字符串
     */
    protected void showProgressDialog(String msg) {
        if (mWaitProgressDialog == null) {
            mWaitProgressDialog = new WaitProgressDialog(this, R.style.DialogStyle);
        }
        mWaitProgressDialog.setMessage(msg);
        mWaitProgressDialog.setCanceledOnTouchOutside(false);
        mWaitProgressDialog.setCancelable(false);
        mWaitProgressDialog.show();
    }

    protected void showProgressDialog() {
        if (mWaitProgressDialog == null) {
            mWaitProgressDialog = new WaitProgressDialog(this, R.style.DialogStyle);
        }
        mWaitProgressDialog.setMessage(getString(R.string.loading_str));
        mWaitProgressDialog.setCanceledOnTouchOutside(false);
        mWaitProgressDialog.setCancelable(false);
        mWaitProgressDialog.show();
    }

    protected void showProgressDialogCloseable(String msg) {
        if (mWaitProgressDialog == null) {
            mWaitProgressDialog = new WaitProgressDialog(this, R.style.DialogStyle);
        }
        mWaitProgressDialog.setMessage(msg);
        mWaitProgressDialog.show();
    }

    protected void showProgressDialogCloseable() {
        if (mWaitProgressDialog == null) {
            mWaitProgressDialog = new WaitProgressDialog(this, R.style.DialogStyle);
        }
        mWaitProgressDialog.setMessage(getString(R.string.loading_str));
        mWaitProgressDialog.show();
    }

    /**
     * 隐藏提示框
     */
    protected void hideProgressDialog() {
        if (mWaitProgressDialog != null) {
            mWaitProgressDialog.dismiss();
        }
    }


    /**
     * 平移动画跳转页面,透明退出
     */
    public void startAcMove(Intent intent) {
        startActivity(intent);
        overridePendingTransition(R.anim.push_right_in, R.anim.alpha_out);
    }

    public void startAcMove(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.push_right_in, R.anim.alpha_out);
    }

    public void startAcBottom(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.push_bottom_in, R.anim.alpha_out);
    }

    public void startAcBottom(Intent intent) {
        startActivity(intent);
        overridePendingTransition(R.anim.push_bottom_in, R.anim.alpha_out);
    }

    protected void finishAcAlpha() {
        finish();
        overridePendingTransition(0, R.anim.slide_out_right);
    }

    /**
     * finish当前activity 移动动画
     */
    protected void finishAcMove() {
        finish();
        overridePendingTransition(R.anim.alpha_in, R.anim.slide_out_right);
    }

    /**
     * finish 当前Activity 从底部退出
     */
    protected void finishAcBottom() {
        finish();
        overridePendingTransition(R.anim.alpha_in, R.anim.push_bottom_out);
    }

    /**
     * 隐藏键盘
     *
     * @param editText 说明:在华为CLT ALOO 系统版本Android 9,level 28发生了view.getWindowToken异常,
     *                 原因是找不到所依附的View,所以建议传递一个当前页面的View
     * @return 隐藏键盘结果
     * <p>
     * true:隐藏成功
     * <p>
     * false:隐藏失败
     */
    protected boolean hiddenKeyboard(View editText) {
        //点击空白位置 隐藏软键盘
        InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService
                (INPUT_METHOD_SERVICE);
        return mInputMethodManager != null && mInputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }


    /**
     * 显示软键盘
     */
    protected void showSoftInputMethod(View v) {
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(v, 0);
        }
    }

    /**
     * 是否使用overridePendingTransition过度动画
     *
     * @return 是否使用overridePendingTransition过度动画，默认使用
     */
    protected boolean isTransAnim() {
        return isTransAnim;
    }

    /**
     * 设置是否使用overridePendingTransition过度动画
     */
    protected void setIsTransAnim(boolean b) {
        isTransAnim = b;
    }

    /**
     * 提示窗口
     */
    public void showHint(Context context, String msg) {
        new AlertDialog.Builder(context)
                .setMessage(msg)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

}
