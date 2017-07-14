package com.yanftch.keyboardutil.keyboard;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yanftch.keyboardutil.R;


/**
 * User : yanftch
 * Date : 2017/4/24
 * Time : 10:26
 * Desc : 分行居中显示文本
 */

public class TimeTextView extends LinearLayout {
    private TextView tv_date, tv_time;//日期&时间
    private Context context;

    private void init() {
        this.setGravity(Gravity.CENTER);
        this.setOrientation(VERTICAL);
        tv_date = new TextView(context);
        tv_time = new TextView(context);
        tv_date.setTextColor(getResources().getColor(R.color.color_999999));
        tv_time.setTextColor(getResources().getColor(R.color.color_999999));
        tv_date.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tv_time.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

    }

    public void setDTText(String dateText, String timeText) {
        tv_date.setText(TextUtils.isEmpty(dateText) ? "" : dateText);
        tv_time.setText(TextUtils.isEmpty(dateText) ? "" : dateText);
    }

    public TimeTextView(Context context) {
        super(context);
        this.context = context;
        init();
    }


    public TimeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public TimeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }
}
