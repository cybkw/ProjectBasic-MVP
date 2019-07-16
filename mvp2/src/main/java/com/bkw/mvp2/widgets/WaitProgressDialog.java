package com.bkw.mvp2.widgets;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * 等待提示dialog
 */

public class WaitProgressDialog extends ProgressDialog {

    public WaitProgressDialog(Context context) {
        this(context, 0);
    }

    public WaitProgressDialog(Context context, int theme) {
        super(context, theme);
        setCanceledOnTouchOutside(false);
    }
}
