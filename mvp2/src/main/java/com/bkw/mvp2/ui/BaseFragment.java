package com.bkw.mvp2.ui;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bkw.mvp2.R;
import com.bkw.mvp2.widgets.WaitProgressDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author bkw
 * @date 2018/3/15
 */

public abstract class BaseFragment extends SupportFragment {

    protected WaitProgressDialog mWaitProgressDialog;
    protected Activity mActivity;
    protected Unbinder binder;

    @Override
    public void onAttach(Activity activity) {
        mActivity = activity;
        super.onAttach(activity);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getLayoutView() != null) {
            return getLayoutView();
        } else {
            return inflater.inflate(getLayoutId(), container, false);
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binder = ButterKnife.bind(this, view);
        getBundle(getArguments());
        initData();
        initUI(view);
    }

    @LayoutRes
    public abstract int getLayoutId();

    public View getLayoutView() {
        return null;
    }

    /**
     * 初始化UI
     */
    public abstract void initUI(View view);

    private void initData() {
        mWaitProgressDialog = new WaitProgressDialog(mActivity, R.style.DialogStyle);
    }


    /**
     * 获得Activity传递的值
     */
    private void getBundle(Bundle bundle) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (binder != null) {
            binder.unbind();
        }
    }

    @Override
    public boolean onBackPressedSupport() {
        if (getFragmentManager().getBackStackEntryCount() > 1) {
            //如果当前存在fragment>1，当前fragment出栈
            pop();
        } else {
            //已经退栈到root fragment，交由Activity处理回退事件
            return false;
        }
        return true;
    }

    /**
     * 显示提示框
     *
     * @param msg 提示框内容字符串
     */
    protected void showProgressDialog(String msg) {
        if (mWaitProgressDialog.isShowing()) {
            mWaitProgressDialog.dismiss();
        }
        mWaitProgressDialog.setMessage(msg);
        mWaitProgressDialog.setCancelable(true);
        mWaitProgressDialog.setCanceledOnTouchOutside(true);
        mWaitProgressDialog.show();
    }

    /**
     * 显示提示框
     */
    protected void showProgressDialog() {
        if (mWaitProgressDialog.isShowing()) {
            mWaitProgressDialog.dismiss();
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
     * [页面跳转]
     *
     * @param clz 要跳转的Activity
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(mActivity, clz));
        mActivity.overridePendingTransition(R.anim.push_right_in, R.anim.alpha_out);
    }

    /**
     * [页面跳转]
     *
     * @param intent 要跳转的Activity
     */
    @Override
    public void startActivity(Intent intent) {
        mActivity.startActivity(intent);
        mActivity.overridePendingTransition(R.anim.push_right_in, R.anim.alpha_out);
    }

    public void startAcForResult(Intent intent, int resultCode) {
        startActivityForResult(intent, resultCode);
        mActivity.overridePendingTransition(R.anim.push_right_in, R.anim.alpha_out);
    }

    public void startAcBottom(Intent intent, int resultCode) {
        startActivityForResult(intent, resultCode);
        mActivity.overridePendingTransition(R.anim.push_bottom_in, R.anim.push_bottom_out);
    }


    /**
     * [携带数据的页面跳转]
     *
     * @param clz    要跳转的Activity
     * @param bundle bundel数据
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(mActivity, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        mActivity.overridePendingTransition(R.anim.push_right_in, R.anim.alpha_out);
    }

    private Toast toast;

    public void showToast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    /**
     * 弹出需要登录窗口
     */
    public void showLoginDialog(Context context) {
        new AlertDialog.Builder(context)
                .setMessage("此操作需要登录")
                .setNegativeButton("不了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("去登录", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        startActivityForResult(new Intent(getContext(), LoginActivity.class), LoginActivity.LOGIN_SUCESS);
                    }
                }).show();
    }


    /**
     * 提示窗口
     */
    public void showHint(Context context, String msg) {
        new AlertDialog.Builder(context)
                .setMessage(msg)
                .setNegativeButton("知道了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }
}
